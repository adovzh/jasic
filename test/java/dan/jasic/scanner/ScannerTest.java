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

    @Test
    public void expression1() {
        String code = "3.4*(-5+9.3e-2)/2.4*.12";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "3.4"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ASTERISK, "*"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.LBRACE, "("), token);

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
        Assert.assertEquals(new Token(Token.NUMBER, "9.3e-2"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.RBRACE, ")"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.SLASH, "/"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "2.4"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ASTERISK, "*"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, ".12"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }

    @Test
    public void expression2() {
        String code = "3*X - Y^2";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "3"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ASTERISK, "*"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "X"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.MINUS, "-"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "Y"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.POWER, "^"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "2"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }

    @Test
    public void expression3() {
        String code = "cost*quantity + overhead";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "cost"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ASTERISK, "*"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "quantity"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.PLUS, "+"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "overhead"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }

    @Test
    public void expression4() {
        String code = "2^(-X)";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "2"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.POWER, "^"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.LBRACE, "("), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.MINUS, "-"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "X"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.RBRACE, ")"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }

    @Test
    public void expression5() {
        String code = "SQR(X^2+Y^2)";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "SQR"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.LBRACE, "("), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "X"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.POWER, "^"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "2"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.PLUS, "+"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "Y"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.POWER, "^"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "2"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.RBRACE, ")"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }

    @Test
    public void expression6() {
        String code = "value(X, Y, a$)";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "value"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.LBRACE, "("), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "X"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.COMMA, ","), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "Y"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.COMMA, ","), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "a$"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.RBRACE, ")"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }

    @Test
    public void keyword() {
        String code = "Let Print";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Keyword(Token.LET, "Let"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Keyword(Token.PRINT, "Print"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }

    @Test
    public void greetingCode() {
        String code = "10 INPUT \"Enter your name: \"; Name$\r\n" +
                "20 PRINT \"Hello, \"; Name$";
        Scanner scanner = new Scanner(code);
        Token token;

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "10"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Keyword(Token.INPUT, "INPUT"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.QUOTE, "\"Enter your name: \""), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.SEMICOLON, ";"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "Name$"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NEWLINE, "\r\n"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.NUMBER, "20"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Keyword(Token.PRINT, "PRINT"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.QUOTE, "\"Hello, \""), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.SEMICOLON, ";"), token);

        token = scanner.getToken();
        Assert.assertNotNull(token);
        Assert.assertEquals(new Token(Token.ID, "Name$"), token);

        token = scanner.getToken();
        Assert.assertNull(token);
    }
}
