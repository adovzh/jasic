%language "Java"
%name-prefix "Jasic"
%define package "dan.jasic.parser"
%define implements "Parser"
%define public
%define final

%code imports {
import java.io.*;
import java.util.*;
import dan.jasic.eval.Variant;
import dan.jasic.parser.Parser;
import dan.jasic.scanner.*;
import dan.jasic.scanner.token.Token;
import dan.jasic.tree.*;

import static dan.jasic.eval.Eval.*;
}

%define stype "Variant"

%token NUMBER NL LBRACE RBRACE

%nonassoc LT GT LE GE
%left PLUS MINUS
%left MUL DIV
%right POW
%right NEG

%type <Node> lines
%type <Statement> statement
%type <Variant> expr clause

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

	public Variant getLVal() {
		if (token != null) {
			if (token.getType() == Token.NUMBER) {
				try {
					return Variant.valueOf(Double.parseDouble(token.getLexeme()));
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
			case Token.LT: return LT;
			case Token.GT: return GT;
			case Token.LE: return LE;
			case Token.GE: return GE;
			default: throw new RuntimeException("Token not supported: " + token);
		}
	}

	public void yyerror(String s) {
		System.out.println("ERROR: " + s);
	}
}

%%

/* lines : lines statement NL { System.out.println("Expr: " + $2); }
	| lines NL
	| 
	;
*/

input : lines
	;

lines : lines NL statement { $$ = $1.addStatement($3); }
	| lines NL
	| statement { $$ = new Program($1); }
	;

statement : expr { $$ = new ExpressionStatement($1); }
	| clause { $$ = new ExpressionStatement($1);  }
    ;

clause : expr LT expr { $$ = lt($1, $3); }
	| expr GT expr { $$ = gt($1, $3); }
	| expr LE expr { $$ = le($1, $3); }
	| expr GE expr { $$ = ge($1, $3); }
	;

expr : expr PLUS expr { $$ = add($1, $3); }
	| expr MINUS expr { $$ = sub($1, $3); }
	| expr MUL expr { $$ = mul($1, $3); }
	| expr DIV expr { $$ = div($1, $3); }
	| expr POW expr { $$ = pow($1, $3); }
	| LBRACE expr RBRACE { $$ = $2; }
	| MINUS expr %prec NEG { $$ = neg($2); }
	| NUMBER
	;

%%
