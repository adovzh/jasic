package dan.jasic.scanner;

/**
 * @author Alexander Dovzhikov
 */
public class Token {
    public static final int WHITESPACE = 1;
    public static final int NEWLINE = 2;
    public static final int ID = 3;
    public static final int QUOTE = 4;
    public static final int EQ = 5;
    public static final int SEMICOLON = 6;
    public static final int NUMBER = 7;

    private final int type;
    private final String lexeme;

    public Token(int type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    public boolean isWhitespace() {
        return type == WHITESPACE;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", lexeme='" + lexeme + '\'' +
                '}';
    }
}
