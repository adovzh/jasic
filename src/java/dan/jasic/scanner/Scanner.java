package dan.jasic.scanner;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alexander Dovzhikov
 */
public abstract class Scanner {
    private State startState;

    public State getStartState() {
        return startState;
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public TokenList scan(String code) {
        if (startState == null) {
            throw new IllegalStateException("Scanner is not initialised");
        }

        final char[] text = prepareCode(code);

        return new TokenList() {
            public Iterator<Token> iterator() {
                return new TokenIterator(text);
            }
        };
    }

    protected Token createToken(int tokenType, String lexeme) {
        return new Token(tokenType, lexeme);
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

        private int lexemeStart;
        private int pos;
        private State state;
        private Token token;

        private TokenIterator(char[] text) {
            this.text = text;
            lexemeStart = 0;
            pos = 0;
            resetState();

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
                    resetState();
                    return token;
                case ACCEPT_PB:
                    lexeme = new String(text, lexemeStart, pos - lexemeStart - 1);
                    token = createToken(transition.value, lexeme);
                    lexemeStart = --pos;
                    resetState();
                    return token;
                case ERROR:
                    throw new IllegalStateException("Unexpected symbol: " + text[pos - 1]);
                case EXIT:
                    return null;
                default:
                    throw new IllegalStateException("Impossible situation!");
            }
        }

        private void resetState() {
            state = getStartState();
        }
    }
}
