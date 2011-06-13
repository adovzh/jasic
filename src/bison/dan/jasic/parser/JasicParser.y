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

%token NUMBER NL LBRACE RBRACE

%left PLUS MINUS
%left MUL DIV
%right POW
%right NEG

%lex-param {InputStream is}

%code lexer {
	private final Iterator<Token> it;
	private Token token;

	private YYLexer(InputStream is) {
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader r = new BufferedReader(new InputStreamReader(is));

			char[] buf = new char[1024];
			int n;

			while ((n = r.read(buf)) != -1)
                sb.append(buf, 0, n);

			JasicScanner scanner = new JasicScanner();
			TokenList tokens = scanner.scan(sb.toString());
			it = tokens.iterator();
		} catch (IOException e) {
			throw new RuntimeException("INIT ERROR: " + e);
		}
	}

	public Double getLVal() {
		if (token != null) {
			if (token.getType() == Token.NUMBER) {
				try {
					return Double.valueOf(token.getLexeme());
				} catch (NumberFormatException e) {
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

		switch (token.getType()) {
			case Token.NUMBER: return NUMBER;
			case Token.PLUS: return PLUS;
			case Token.MINUS: return MINUS;
			case Token.ASTERISK: return MUL;
			case Token.SLASH: return DIV;
			case Token.POWER: return POW;
			case Token.NEWLINE: return NL;
			case Token.LBRACE: return LBRACE;
			case Token.RBRACE: return RBRACE;
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
	| expr MINUS expr { $$ = $1 - $3; }
	| expr MUL expr { $$ = $1 * $3; }
	| expr DIV expr { $$ = $1 / $3; }
	| expr POW expr { $$ = Math.pow($1, $3); }
	| LBRACE expr RBRACE { $$ = $2; }
	| MINUS expr %prec NEG { $$ = -$2; }
	| NUMBER
	;

%%
