package dan.jasic.scanner;

/**
 * @author Alexander Dovzhikov
 */
public class Token {
    public static final int WHITESPACE = 1;
    public static final int NEWLINE = 2;
    public static final int ID = 3;
    public static final int NUMBER = 4;
    public static final int QUOTE = 5;
    public static final int EQ = 6;
    public static final int SEMICOLON = 7;
    public static final int PLUS = 8;
    public static final int MINUS = 9;
    public static final int ASTERISK = 10;
    public static final int SLASH = 11;
    public static final int LBRACE = 12;
    public static final int RBRACE = 13;

    public static final int PRINT = 40;
    public static final int LET = 41;

    private final int type;
    private final String lexeme;

    public Token(int type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    public boolean isWhitespace() {
        return type == WHITESPACE;
    }

    public int getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (type != token.type) return false;
        if (!lexeme.equals(token.lexeme)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + lexeme.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", lexeme='" + lexeme + '\'' +
                '}';
    }
}
