package dan.jasic.scanner;

import dan.jasic.scanner.token.Keyword;
import dan.jasic.scanner.token.Token;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public class KeywordsTest {

    private final Keywords keywords = new Keywords();

    @Test
    public void basicKeywords() {
        Assert.assertEquals(new Keyword(Token.LET, "Let"), keywords.checkLexeme("Let"));
        Assert.assertEquals(new Keyword(Token.LET, "LET"), keywords.checkLexeme("LET"));
        Assert.assertEquals(new Keyword(Token.PRINT, "Print"), keywords.checkLexeme("Print"));
        Assert.assertEquals(new Keyword(Token.PRINT, "PRINT"), keywords.checkLexeme("PRINT"));
        Assert.assertEquals(new Keyword(Token.INPUT, "Input"), keywords.checkLexeme("Input"));
        Assert.assertEquals(new Keyword(Token.INPUT, "INPUT"), keywords.checkLexeme("INPUT"));

        Assert.assertNull(keywords.checkLexeme("A"));
        Assert.assertNull(keywords.checkLexeme("NaMME"));
    }
}
