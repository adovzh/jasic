%language "Java"
%name-prefix "Jasic"
%define package "dan.jasic.parser"
%define public
%define final

%define stype "Double"

%token NUMBER

%left '+'
%left '*'
%right NEG

/* %code lexer { System.out.println("Lexer!"); } */

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
