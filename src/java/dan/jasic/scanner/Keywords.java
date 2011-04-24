package dan.jasic.scanner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Dovzhikov
 */
public class Keywords {
    // todo: re-implement using tries
    private final Map<String, Integer> keywords;

    public Keywords() {
        Map<String, Integer> kw = new HashMap<String, Integer>();
        kw.put("ACCESS", Token.ACCESS);
        kw.put("LET", Token.LET);
        kw.put("INPUT", Token.INPUT);
        kw.put("PRINT", Token.PRINT);
        kw.put("LET", Token.LET);
        kw.put("INPUT", Token.INPUT);

        keywords = Collections.unmodifiableMap(kw);
    }


    public Token checkLexeme(String lexeme) {
        Integer keywordType = keywords.get(lexeme.toUpperCase());
        return (keywordType != null) ? new Token(keywordType, lexeme) : null;
    }
}
