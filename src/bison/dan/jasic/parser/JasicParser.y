%language "Java"
%name-prefix "Jasic"
%define package "dan.jasic.parser"
%define implements "Parser"
%define public
%define final

%code imports {
import java.io.*;
import java.util.*;
import dan.jasic.parser.Parser;
import dan.jasic.scanner.*;
import dan.jasic.scanner.token.Token;
}

%define stype "Double"

%token NUMBER NL

%left PLUS MINUS
%left MUL DIV
%right POW
%right NEG

%code lexer {
	private final Iterator<Token> it;
	private Token token;

	{
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
			String line = r.readLine();
			JasicScanner scanner = new JasicScanner();
			TokenList tokens = scanner.scan(line);
			it = tokens.iterator();
		} catch (IOException e) {
			throw new RuntimeException("INIT ERROR: " + e);
		}
	}

	public Double getLVal() {
		if (token != null) {
			if (token.getType() == Token.NUMBER) {
				try {
					System.out.println("LVal: " + Double.valueOf(token.getLexeme()));
					return Double.valueOf(token.getLexeme());
				} catch (NumberFormatException e) {
					System.err.println("Cannot parse: " + token.getLexeme());
					throw e;
				}
			}
		}

		return null;
	}

	public int yylex() throws IOException {
		if (!it.hasNext())
			return EOF;

		token = it.next();
		System.out.println("token: " + token);

		switch (token.getType()) {
			case Token.NUMBER: return NUMBER;
			case Token.PLUS: return PLUS;
			case Token.MINUS: return MINUS;
			case Token.ASTERISK: return MUL;
			case Token.SLASH: return DIV;
			case Token.POWER: return POW;
			case Token.NEWLINE: return NL;
			default: throw new RuntimeException("Token not supported: " + token);
		}
	}

	public void yyerror(String s) {
		System.out.println("ERROR: " + s);
	}
}

%%

lines : lines expr NL { System.out.println("Expr: " + $2); }
	| lines NL
	| /* empty */
	;

expr : expr PLUS expr { $$ = $1 + $3; }
	| expr MUL expr { $$ = $1 * $3; }
	| MINUS expr %prec NEG { $$ = -$2; }
	| NUMBER
	;

%%
