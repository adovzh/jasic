%language "Java"
%name-prefix "Ex02"
%define public
%define final
%define implements "ParserObject"

%code imports {
import java.io.InputStream;
import java.io.IOException;
}

%define stype "String"

%token SYM

%code lexer {
	private final InputStream in = System.in;
	private int symbol;

	public String getLVal() {
		return String.valueOf((char)symbol);
	}

	public int yylex() throws IOException {
		symbol = in.read();

		if (symbol == -1)
			return EOF;

		if (Character.isLetter((char)symbol))
			return SYM;

		return symbol;
	}

	public void yyerror(String s) {
		System.out.println("ERROR: " + s);
	}
}

%%

input : S { System.out.println("Flattened: " + $1); $$ = $1; }
	;

S : '(' L ')' { $$ = $2; }
	| SYM
	;

L : L ',' S { $$ = $1 + $3; }
	| S

%%
