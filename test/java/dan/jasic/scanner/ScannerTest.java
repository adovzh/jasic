package dan.jasic.scanner;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public class ScannerTest {

    @Test
    public void whitespace() {
        String code = "  \t\t \t";
        Scanner scanner = new Scanner(code);
        Token token = scanner.getToken();

        Assert.assertNull(token);
    }

    @Test
    public void newline() {
        String code = "\t\n \r\n\t\r";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NEWLINE, "\n"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NEWLINE, "\r\n"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NEWLINE, "\r"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }

    @Test
    public void identifier() {
        String code = "A Bat Mid$ Init create_instance C$D";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "A"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "Bat"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "Mid$"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "Init"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "create_instance"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "C$"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "D"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }

    @Test
    public void quote() {
        String code = " \"Some text\" \"\"\"Hello!\"\", I said\"";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.QUOTE, "\"Some text\""), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.QUOTE, "\"\"\"Hello!\"\", I said\""), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }

    @Test
    public void number() {
        String code = "5 23 .203 86.146 64. 93.2e-3 .8E+7 13.e2";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "5"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "23"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, ".203"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "86.146"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "64."), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "93.2e-3"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, ".8E+7"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "13.e2"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }

    @Test
    public void signedNumber() {
        String code = "-5 +6.3";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.MINUS, "-"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "5"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.PLUS, "+"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "6.3"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }
}
