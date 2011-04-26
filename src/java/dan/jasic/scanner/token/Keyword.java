package dan.jasic.scanner.token;

/**
 * @author Alexander Dovzhikov
 */
public class Keyword extends Token {

    public Keyword(int type, String lexeme) {
        super(type, lexeme);
    }

    @Override
    public String toString() {
        return "Keyword " + getLexeme().toUpperCase();
    }
}
