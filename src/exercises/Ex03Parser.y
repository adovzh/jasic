%language "Java"
%name-prefix "Ex03"
%define public
%define final
%define implements "ParserObject"

%code imports {
	import java.io.*;
}

%token <Integer> SYM
%type <Boolean> expr

%code lexer {
	private InputStream in = System.in;
	private int symbol;

	public Integer getLVal() {
		return symbol;
	}

	public int yylex() throws IOException {
		symbol = in.read();
		System.out.println("sym: " + symbol);

		return (symbol != -1) ? SYM : EOF;
	}

	public void yyerror(String s) {
		System.out.println("Error: " + s);
	}
}

%%

input : expr { System.out.println("Expression is " + (($1.booleanValue()) ? "" : "not ") + "a palindrome"); }
	;

expr : SYM expr SYM { $$ = ($2 && ($1.intValue() == $3.intValue())); }
	| SYM { $$ = true; }
	| /* empty */ { $$ = true; }
	;

%%
 
