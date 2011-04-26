package dan.jasic.scanner;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Alexander Dovzhikov
 */
public class ScannerTest {

    @Test
    public void whitespace() {
        Assert.assertFalse(Scanner.scan("  \t\t \t").iterator().hasNext());
    }

    @Test
    public void newline() {
        String code = "\t\n \r\n\t\r";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.NEWLINE, "\n"), it.next());
        assertEquals(new Token(Token.NEWLINE, "\r\n"), it.next());
        assertEquals(new Token(Token.NEWLINE, "\r"), it.next());
        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void identifier() {
        String code = "A Bat Mid$ Init create_instance C$D";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.ID, "A"), it.next());
        assertEquals(new Token(Token.ID, "Bat"), it.next());
        assertEquals(new Token(Token.ID, "Mid$"), it.next());
        assertEquals(new Token(Token.ID, "Init"), it.next());
        assertEquals(new Token(Token.ID, "create_instance"), it.next());
        assertEquals(new Token(Token.ID, "C$"), it.next());
        assertEquals(new Token(Token.ID, "D"), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void quote() {
        String code = " \"Some text\" \"\"\"Hello!\"\", I said\"";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.QUOTE, "\"Some text\""), it.next());
        assertEquals(new Token(Token.QUOTE, "\"\"\"Hello!\"\", I said\""), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void number() {
        String code = "5 23 .203 86.146 64. 93.2e-3 .8E+7 13.e2";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.NUMBER, "5"), it.next());
        assertEquals(new Token(Token.NUMBER, "23"), it.next());
        assertEquals(new Token(Token.NUMBER, ".203"), it.next());
        assertEquals(new Token(Token.NUMBER, "86.146"), it.next());
        assertEquals(new Token(Token.NUMBER, "64."), it.next());
        assertEquals(new Token(Token.NUMBER, "93.2e-3"), it.next());
        assertEquals(new Token(Token.NUMBER, ".8E+7"), it.next());
        assertEquals(new Token(Token.NUMBER, "13.e2"), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void signedNumber() {
        String code = "-5 +6.3";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.MINUS, "-"), it.next());
        assertEquals(new Token(Token.NUMBER, "5"), it.next());
        assertEquals(new Token(Token.PLUS, "+"), it.next());
        assertEquals(new Token(Token.NUMBER, "6.3"), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void expression1() {
        String code = "3.4*(-5+9.3e-2)/2.4*.12";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.NUMBER, "3.4"), it.next());
        assertEquals(new Token(Token.ASTERISK, "*"), it.next());
        assertEquals(new Token(Token.LBRACE, "("), it.next());
        assertEquals(new Token(Token.MINUS, "-"), it.next());
        assertEquals(new Token(Token.NUMBER, "5"), it.next());
        assertEquals(new Token(Token.PLUS, "+"), it.next());
        assertEquals(new Token(Token.NUMBER, "9.3e-2"), it.next());
        assertEquals(new Token(Token.RBRACE, ")"), it.next());
        assertEquals(new Token(Token.SLASH, "/"), it.next());
        assertEquals(new Token(Token.NUMBER, "2.4"), it.next());
        assertEquals(new Token(Token.ASTERISK, "*"), it.next());
        assertEquals(new Token(Token.NUMBER, ".12"), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void expression2() {
        String code = "3*X - Y^2";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.NUMBER, "3"), it.next());
        assertEquals(new Token(Token.ASTERISK, "*"), it.next());
        assertEquals(new Token(Token.ID, "X"), it.next());
        assertEquals(new Token(Token.MINUS, "-"), it.next());
        assertEquals(new Token(Token.ID, "Y"), it.next());
        assertEquals(new Token(Token.POWER, "^"), it.next());
        assertEquals(new Token(Token.NUMBER, "2"), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void expression3() {
        String code = "cost*quantity + overhead";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.ID, "cost"), it.next());
        assertEquals(new Token(Token.ASTERISK, "*"), it.next());
        assertEquals(new Token(Token.ID, "quantity"), it.next());
        assertEquals(new Token(Token.PLUS, "+"), it.next());
        assertEquals(new Token(Token.ID, "overhead"), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void expression4() {
        String code = "2^(-X)";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.NUMBER, "2"), it.next());
        assertEquals(new Token(Token.POWER, "^"), it.next());
        assertEquals(new Token(Token.LBRACE, "("), it.next());
        assertEquals(new Token(Token.MINUS, "-"), it.next());
        assertEquals(new Token(Token.ID, "X"), it.next());
        assertEquals(new Token(Token.RBRACE, ")"), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void expression5() {
        String code = "SQR(X^2+Y^2)";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.ID, "SQR"), it.next());
        assertEquals(new Token(Token.LBRACE, "("), it.next());
        assertEquals(new Token(Token.ID, "X"), it.next());
        assertEquals(new Token(Token.POWER, "^"), it.next());
        assertEquals(new Token(Token.NUMBER, "2"), it.next());
        assertEquals(new Token(Token.PLUS, "+"), it.next());
        assertEquals(new Token(Token.ID, "Y"), it.next());
        assertEquals(new Token(Token.POWER, "^"), it.next());
        assertEquals(new Token(Token.NUMBER, "2"), it.next());
        assertEquals(new Token(Token.RBRACE, ")"), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void expression6() {
        String code = "value(X, Y, a$)";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.ID, "value"), it.next());
        assertEquals(new Token(Token.LBRACE, "("), it.next());
        assertEquals(new Token(Token.ID, "X"), it.next());
        assertEquals(new Token(Token.COMMA, ","), it.next());
        assertEquals(new Token(Token.ID, "Y"), it.next());
        assertEquals(new Token(Token.COMMA, ","), it.next());
        assertEquals(new Token(Token.ID, "a$"), it.next());
        assertEquals(new Token(Token.RBRACE, ")"), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void keyword() {
        String code = "Let Print Input If Else";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Keyword(Token.LET, "Let"), it.next());
        assertEquals(new Keyword(Token.PRINT, "Print"), it.next());
        assertEquals(new Keyword(Token.INPUT, "Input"), it.next());
        assertEquals(new Keyword(Token.IF, "If"), it.next());
        assertEquals(new Keyword(Token.ELSE, "Else"), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void greetingCode() {
        String code = "10 INPUT \"Enter your name: \"; Name$\r\n" +
                "20 PRINT \"Hello, \"; Name$";
        Iterator<Token> it = Scanner.scan(code).iterator();

        assertEquals(new Token(Token.NUMBER, "10"), it.next());
        assertEquals(new Keyword(Token.INPUT, "INPUT"), it.next());
        assertEquals(new Token(Token.QUOTE, "\"Enter your name: \""), it.next());
        assertEquals(new Token(Token.SEMICOLON, ";"), it.next());
        assertEquals(new Token(Token.ID, "Name$"), it.next());
        assertEquals(new Token(Token.NEWLINE, "\r\n"), it.next());
        assertEquals(new Token(Token.NUMBER, "20"), it.next());
        assertEquals(new Keyword(Token.PRINT, "PRINT"), it.next());
        assertEquals(new Token(Token.QUOTE, "\"Hello, \""), it.next());
        assertEquals(new Token(Token.SEMICOLON, ";"), it.next());
        assertEquals(new Token(Token.ID, "Name$"), it.next());
        assertFalse(it.hasNext());
    }
}
