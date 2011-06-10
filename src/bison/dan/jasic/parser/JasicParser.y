%language "Java"
%name-prefix "Jasic"
%define package "dan.jasic.parser"
%define public
%define final

%code imports {
import java.io.*;
import dan.jasic.scanner.token.Token;
}

%define stype "Double"

%token NUMBER

%left PLUS MINUX
%left MUL DIV
%right POW
%right NEG

%code lexer {
	// private String expr = new BufferedReader(System.in).readLine();
	private Iterator<Token> it = new JasicScanner().scan(new BufferedReader(System.in).readLine());

	public Double getLVal() {
		return null;
	}

	public int yylex() throws IOException {
		if (!it.hasNext())
			return EOF;

		Token token = it.next();

		switch (token.getType()) {
			case Token.NUMBER: return NUMBER; break;
			case Token.PLUS: return PLUS; break;
			case Token.MINUX: return MINUS; break;
			case Token.ASTERISK: return MUL; break;
			case Token.SLASH: return DIV; break;
			case Token.POWER: return POW; break;
			default: throw new RuntimeException("Token not supported: " + token);
		}
	}
}

%%

lines : /* empty */
	| lines line
	;

line : expr '\n'
	;

expr : NUMBER
	| expr '+' expr { $$ = $1 + $3; }
	| expr '*' expr { $$ = $1 * $3; }
	| '-' expr %prec NEG { $$ = -$2; }

%%
