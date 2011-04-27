package dan.jasic.scanner.token;

/**
 * @author Alexander Dovzhikov
 */
public class Newline extends Token {
    public Newline(String lexeme) {
        super(Token.NEWLINE, lexeme);
    }

    @Override
    public String toString() {
        return "NEWLINE";
    }
}
