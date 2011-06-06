%language "Java"
%name-prefix "Ex01"
%define public
%define final

%code imports {
	import java.util.HashMap;
	import java.util.Map;
	import java.util.Scanner;
}

%define stype "Boolean"

%token OR AND NOT TOK

%left OR
%left AND
%right NOT

%code lexer {
	private Map<String, Integer> keywords = new HashMap<String, Integer>();

	{
		keywords.put("OR", OR);
		keywords.put("AND", AND);
		keywords.put("NOT", NOT);
	}

	private Scanner scanner = new Scanner(System.in);
	private String lexeme;
	private Boolean lval;

	public Boolean getLVal() {
		return lval;
	}

	public int yylex() throws java.io.IOException {
		if (!scanner.hasNext())
			return EOF;

		lexeme = scanner.next();

		Integer code = keywords.get(lexeme.toUpperCase());

		if (code != null)
			return code;
		
		if (lexeme.equalsIgnoreCase("TRUE")) {
			lval = Boolean.TRUE;
			return TOK;
		} else if (lexeme.equalsIgnoreCase("FALSE")) {
			lval = Boolean.FALSE;
			return TOK;
		} else if (lexeme.equals("(") || lexeme.equals(")")) {
			return lexeme.charAt(0);
		}
		
		return 0;
	}

	public void yyerror(String s) {
		System.out.println("Error: " + s);
	}
}

%%

input : bexpr { System.out.println("Expression is " + $1); }

bexpr : bexpr OR bterm { $$ = $1 || $3; }
	| bterm
	;

bterm : bterm AND bfactor { $$ = $1 && $3; }
	| bfactor
	;

bfactor : NOT bfactor { $$ = ! $2; }
	| '(' bexpr ')' { $$ = $2;  }
	| TOK

%%
