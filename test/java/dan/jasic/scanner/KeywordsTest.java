package dan.jasic.scanner;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public class KeywordsTest {

    private final Keywords keywords = new Keywords();

    @Test
    public void basicKeywords() {
        Assert.assertEquals(new Token(Token.LET, "Let"), keywords.checkLexeme("Let"));
        Assert.assertEquals(new Token(Token.LET, "LET"), keywords.checkLexeme("LET"));
        Assert.assertEquals(new Token(Token.PRINT, "Print"), keywords.checkLexeme("Print"));
        Assert.assertEquals(new Token(Token.PRINT, "PRINT"), keywords.checkLexeme("PRINT"));
        Assert.assertEquals(new Token(Token.INPUT, "Input"), keywords.checkLexeme("Input"));
        Assert.assertEquals(new Token(Token.INPUT, "INPUT"), keywords.checkLexeme("INPUT"));

        Assert.assertNull(keywords.checkLexeme("A"));
        Assert.assertNull(keywords.checkLexeme("NaME"));
    }
}
