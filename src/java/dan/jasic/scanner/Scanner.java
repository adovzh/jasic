package dan.jasic.scanner;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static dan.jasic.scanner.State.*;

/**
 * @author Alexander Dovzhikov
 */
public class Scanner {

    static {
        Transition transition;
        Transition number1Transition = Transition.next(STATE_NUMBER1);
        Transition number2Transition = Transition.next(STATE_NUMBER2);
        Transition number3Transition = Transition.next(STATE_NUMBER3);
        Transition number4Transition = Transition.next(STATE_NUMBER4);
        Transition number5Transition = Transition.next(STATE_NUMBER5);
        Transition number6Transition = Transition.next(STATE_NUMBER6);

        // STATE_INIT
        STATE_INIT.initNonAccepting();
        STATE_INIT.setTransition(State.EOF, Transition.EXIT);
        STATE_INIT.setLetterState(STATE_ID);
        STATE_INIT.setNextState('"', STATE_QUOTE);
        STATE_INIT.acceptState('=', Token.EQ);
        STATE_INIT.acceptState(';', Token.SEMICOLON);
        STATE_INIT.setWhitespaceState(STATE_WHITESPACE);
        STATE_INIT.setNextState('\r', STATE_NEWLINE);
        STATE_INIT.acceptState('\n', Token.NEWLINE);
        STATE_INIT.acceptState('+', Token.PLUS);
        STATE_INIT.acceptState('-', Token.MINUS);
        STATE_INIT.acceptState('*', Token.ASTERISK);
        STATE_INIT.acceptState('/', Token.SLASH);
        STATE_INIT.acceptState('^', Token.POWER);
        STATE_INIT.acceptState('(', Token.LBRACE);
        STATE_INIT.acceptState(')', Token.RBRACE);
        STATE_INIT.acceptState(',', Token.COMMA);
        STATE_INIT.setDigitTransition(number1Transition);
        STATE_INIT.setTransition('.', number2Transition);

        // STATE_WHITESPACE
        STATE_WHITESPACE.initAcceptingPushBack(Token.WHITESPACE);
        STATE_WHITESPACE.setWhitespaceState(STATE_WHITESPACE);

        // STATE_NEWLINE
        STATE_NEWLINE.initAcceptingPushBack(Token.NEWLINE);
        STATE_NEWLINE.acceptState('\n', Token.NEWLINE);

        // STATE_ID
        STATE_ID.initAcceptingPushBack(Token.ID);
        transition = Transition.next(STATE_ID);
        STATE_ID.setLetterTransition(transition);
        STATE_ID.setTransition('_', transition);
        STATE_ID.setDigitTransition(transition);
        STATE_ID.acceptState('$', Token.ID);

        // STATE_QUOTE
        STATE_QUOTE.initNonAccepting();
        transition = Transition.next(STATE_QUOTE);
        STATE_QUOTE.setQuotedCharacterTransition(transition);
        STATE_QUOTE.setNextState('"', STATE_QUOTE_END);

        // STATE_QUOTE_END
        STATE_QUOTE_END.initAcceptingPushBack(Token.QUOTE);
        STATE_QUOTE_END.setNextState('"', STATE_QUOTE);

        // STATE_NUMBER1 (K)
        STATE_NUMBER1.initAcceptingPushBack(Token.NUMBER);
        STATE_NUMBER1.setDigitTransition(number1Transition);
        STATE_NUMBER1.setTransition('.', number3Transition);
        STATE_NUMBER1.setTransition('E', number4Transition);
        STATE_NUMBER1.setTransition('e', number4Transition);

        // STATE_NUMBER2 (L)
        STATE_NUMBER2.initNonAccepting();
        STATE_NUMBER2.setDigitTransition(number3Transition);

        // STATE_NUMBER3 (M)
        STATE_NUMBER3.initAcceptingPushBack(Token.NUMBER);
        STATE_NUMBER3.setDigitTransition(number3Transition);
        STATE_NUMBER3.setTransition('E', number4Transition);
        STATE_NUMBER3.setTransition('e', number4Transition);

        // STATE_NUMBER4 (N)
        STATE_NUMBER4.initNonAccepting();
        STATE_NUMBER4.setTransition('+', number5Transition);
        STATE_NUMBER4.setTransition('-', number5Transition);
        STATE_NUMBER4.setDigitTransition(number6Transition);

        // STATE_NUMBER5 (P)
        STATE_NUMBER5.initNonAccepting();
        STATE_NUMBER5.setDigitTransition(number6Transition);

        // STATE_NUMBER6 (Q)
        STATE_NUMBER6.initAcceptingPushBack(Token.NUMBER);
        STATE_NUMBER6.setDigitTransition(number6Transition);
    }

    private final Keywords keywords = new Keywords();

    private Token createToken(int tokenType, String lexeme) {
        if (tokenType == Token.ID) {
            Token keyword = keywords.checkLexeme(lexeme);

            if (keyword != null)
                return keyword;
        }

        return new Token(tokenType, lexeme);
    }

    public TokenList scan(String code) {
        final char[] text = prepareCode(code);

        return new TokenList() {
            public Iterator<Token> iterator() {
                return new TokenIterator(text);
            }
        };
    }

    private char[] prepareCode(String code) {
        int len = code.length();
        final char[] text = new char[len + 1];
        code.getChars(0, len, text, 0);
        text[len] = State.EOF;
        return text;
    }

    private class TokenIterator implements Iterator<Token> {
        private final char[] text;

        private int lexemeStart = 0;
        private int pos = 0;
        private State state = STATE_INIT;
        private Token token;

        private TokenIterator(char[] text) {
            this.text = text;
            lexemeStart = 0;
            pos = 0;
            state = STATE_INIT;

            token = getToken();
        }

        public boolean hasNext() {
            return (token != null);
        }

        public Token next() {
            if (token == null)
                throw new NoSuchElementException();

            Token tk = token;
            token = getToken();

            return tk;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private Token getToken() {
            Token token;
            do {
                token = getTokenInternal();
            } while (token != null && token.isWhitespace());

            return token;
        }

        private Token getTokenInternal() {
            Transition transition;
            Token token;
            String lexeme;

            do {
                char c = text[pos++];
                transition = state.getTransition(c);
                state = transition.nextState;
            } while (transition.isNext());

            switch (transition.type) {
                case ACCEPT:
                    lexeme = new String(text, lexemeStart, pos - lexemeStart);
                    token = createToken(transition.value, lexeme);
                    lexemeStart = pos;
                    state = STATE_INIT;
                    return token;
                case ACCEPT_PB:
                    lexeme = new String(text, lexemeStart, pos - lexemeStart - 1);
                    token = createToken(transition.value, lexeme);
                    lexemeStart = --pos;
                    state = STATE_INIT;
                    return token;
                case ERROR:
                    throw new IllegalStateException("Unexpected symbol: " + text[pos - 1]);
                case EXIT:
                    return null;
                default:
                    throw new IllegalStateException("Impossible situation!");
            }
        }
    }
}
