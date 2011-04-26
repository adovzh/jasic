package dan.jasic.scanner;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alexander Dovzhikov
 */
public class Scanner {
    private static final int STATE_MAX = 20;
    private static final int CHAR_MAX = 256;
    private static final int EOF = CHAR_MAX - 1;

    private static final int STATE_INIT = 0;
    private static final int STATE_WHITESPACE = 1;
    private static final int STATE_NEWLINE = 2;
    private static final int STATE_ID = 3;
    private static final int STATE_QUOTE = 4;
    private static final int STATE_QUOTE_END = 5;
    private static final int STATE_NUMBER1 = 6;
    private static final int STATE_NUMBER2 = 7;
    private static final int STATE_NUMBER3 = 8;
    private static final int STATE_NUMBER4 = 9;
    private static final int STATE_NUMBER5 = 10;
    private static final int STATE_NUMBER6 = 11;

    private static Transition[][] TABLE = new Transition[STATE_MAX][CHAR_MAX];

    static {
        Transition transition;
        Transition number1Transition = Transition.next(STATE_NUMBER1);
        Transition number2Transition = Transition.next(STATE_NUMBER2);
        Transition number3Transition = Transition.next(STATE_NUMBER3);
        Transition number4Transition = Transition.next(STATE_NUMBER4);
        Transition number5Transition = Transition.next(STATE_NUMBER5);
        Transition number6Transition = Transition.next(STATE_NUMBER6);

        // STATE_INIT
        initNonAcceptingState(STATE_INIT);
        setTransition(STATE_INIT, EOF, Transition.EXIT);
        setLetterState(STATE_INIT, STATE_ID);
        setNextState(STATE_INIT, '"', STATE_QUOTE);
        acceptState(STATE_INIT, '=', Token.EQ);
        acceptState(STATE_INIT, ';', Token.SEMICOLON);
        setWhitespaceState(STATE_INIT, STATE_WHITESPACE);
        setNextState(STATE_INIT, '\r', STATE_NEWLINE);
        acceptState(STATE_INIT, '\n', Token.NEWLINE);
        acceptState(STATE_INIT, '+', Token.PLUS);
        acceptState(STATE_INIT, '-', Token.MINUS);
        acceptState(STATE_INIT, '*', Token.ASTERISK);
        acceptState(STATE_INIT, '/', Token.SLASH);
        acceptState(STATE_INIT, '^', Token.POWER);
        acceptState(STATE_INIT, '(', Token.LBRACE);
        acceptState(STATE_INIT, ')', Token.RBRACE);
        acceptState(STATE_INIT, ',', Token.COMMA);
        setDigitTransition(STATE_INIT, number1Transition);
        setTransition(STATE_INIT, '.', number2Transition);

        // STATE_WHITESPACE
        initAcceptingStatePushBack(STATE_WHITESPACE, Token.WHITESPACE);
        setWhitespaceState(STATE_WHITESPACE, STATE_WHITESPACE);

        // STATE_NEWLINE
        initAcceptingStatePushBack(STATE_NEWLINE, Token.NEWLINE);
        acceptState(STATE_NEWLINE, '\n', Token.NEWLINE);

        // STATE_ID
        initAcceptingStatePushBack(STATE_ID, Token.ID);
        transition = Transition.next(STATE_ID);
        setLetterTransition(STATE_ID, transition);
        setTransition(STATE_ID, '_', transition);
        setDigitTransition(STATE_ID, transition);
        acceptState(STATE_ID, '$', Token.ID);

        // STATE_QUOTE
        initNonAcceptingState(STATE_QUOTE);
        transition = Transition.next(STATE_QUOTE);
        setQuotedCharacterTransition(STATE_QUOTE, transition);
        setNextState(STATE_QUOTE, '"', STATE_QUOTE_END);

        // STATE_QUOTE_END
        initAcceptingStatePushBack(STATE_QUOTE_END, Token.QUOTE);
        setNextState(STATE_QUOTE_END, '"', STATE_QUOTE);

        // STATE_NUMBER1 (K)
        initAcceptingStatePushBack(STATE_NUMBER1, Token.NUMBER);
        setDigitTransition(STATE_NUMBER1, number1Transition);
        setTransition(STATE_NUMBER1, '.', number3Transition);
        setTransition(STATE_NUMBER1, 'E', number4Transition);
        setTransition(STATE_NUMBER1, 'e', number4Transition);

        // STATE_NUMBER2 (L)
        initNonAcceptingState(STATE_NUMBER2);
        setDigitTransition(STATE_NUMBER2, number3Transition);

        // STATE_NUMBER3 (M)
        initAcceptingStatePushBack(STATE_NUMBER3, Token.NUMBER);
        setDigitTransition(STATE_NUMBER3, number3Transition);
        setTransition(STATE_NUMBER3, 'E', number4Transition);
        setTransition(STATE_NUMBER3, 'e', number4Transition);

        // STATE_NUMBER4 (N)
        initNonAcceptingState(STATE_NUMBER4);
        setTransition(STATE_NUMBER4, '+', number5Transition);
        setTransition(STATE_NUMBER4, '-', number5Transition);
        setDigitTransition(STATE_NUMBER4, number6Transition);

        // STATE_NUMBER5 (P)
        initNonAcceptingState(STATE_NUMBER5);
        setDigitTransition(STATE_NUMBER5, number6Transition);

        // STATE_NUMBER6 (Q)
        initAcceptingStatePushBack(STATE_NUMBER6, Token.NUMBER);
        setDigitTransition(STATE_NUMBER6, number6Transition);
    }

    private static void initNonAcceptingState(int state) {
        initState(state, Transition.ERROR);
    }

//    private static void initAcceptingState(int state, int tokenType) {
//        initState(state, Transition.accept(tokenType));
//    }

    private static void initAcceptingStatePushBack(int state, int tokenType) {
        initState(state, Transition.acceptPushBack(tokenType));
    }

    private static void initState(int state, Transition transition) {
        for (int c = 0; c < CHAR_MAX; c++) {
            TABLE[state][c] = transition;
        }
    }

    private static void setNextState(int state, char c, int nextState) {
        setTransition(state, c, Transition.next(nextState));
    }

    private static void acceptState(int state, int c, int tokenType) {
        setTransition(state, c, Transition.accept(tokenType));
    }

//    private static void acceptStatePushBack(int state, int c, int tokenType) {
//        setTransition(state, c, Transition.acceptPushBack(tokenType));
//    }

    private static void setTransition(int state, int c, Transition transition) {
        TABLE[state][c] = transition;
    }

    private static void setLetterState(int state, int nextState) {
        setLetterTransition(state, Transition.next(nextState));
    }

    private static void setLetterTransition(int state, Transition transition) {
        for (char c = 'A'; c <= 'Z'; c++) {
            setTransition(state, c, transition);
        }

        for (char c = 'a'; c <= 'z'; c++) {
            setTransition(state, c, transition);
        }
    }

//    private static void setDigitState(int state, int nextState) {
//        setDigitTransition(state, Transition.next(nextState));
//    }

    private static void setDigitTransition(int state, Transition transition) {
        for (char c = '0'; c <= '9'; c++) {
            setTransition(state, c, transition);
        }
    }

    private static void setWhitespaceState(int state, int nextState) {
        setWhitespaceTransition(state, Transition.next(nextState));
    }

    private static void setWhitespaceTransition(int state, Transition transition) {
        setTransition(state, ' ', transition);
        setTransition(state, '\t', transition);
    }

    private static void setQuotedCharacterTransition(int state, Transition transition) {
        for (char c = 32; c <= 126; c++) {
            setTransition(state, c, transition);
        }
    }

    public TokenList scan(String code) {
        int len = code.length();
        final char[] text = new char[len + 1];
        code.getChars(0, len, text, 0);
        text[len] = 255;

        return new TokenList() {
            public Iterator<Token> iterator() {
                return new TokenIterator(text);
            }
        };
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

    private static class Transition {
        enum Type {
            ERROR, NEXT, ACCEPT, ACCEPT_PB, EXIT
        }

        private static final Transition ERROR = new Transition(Type.ERROR);
        private static final Transition EXIT = new Transition(Type.EXIT);

        static Transition next(int state) {
            return new Transition(Type.NEXT, state);
        }

        static Transition accept(int tokenType) {
            return new Transition(Type.ACCEPT, tokenType);
        }

        static Transition acceptPushBack(int tokenType) {
            return new Transition(Type.ACCEPT_PB, tokenType);
        }

        final Type type;
        final int value;

        Transition(Type type) {
            this.type = type;
            this.value = 0;
        }

        Transition(Type type, int value) {
            this.type = type;
            this.value = value;
        }

        boolean isNext() {
            return type == Type.NEXT;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Transition that = (Transition) o;

            return value == that.value && type == that.type;

        }

        @Override
        public int hashCode() {
            int result = type.hashCode();
            result = 31 * result + value;
            return result;
        }

        @Override
        public String toString() {
            return "Transition{" +
                    "type=" + type +
                    ", value=" + value +
                    '}';
        }
    }

    private class TokenIterator implements Iterator<Token> {
        private final char[] text;

        private int lexemeStart = 0;
        private int pos = 0;
        private int state = STATE_INIT;
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
                transition = TABLE[state][c];
                state = transition.value;
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
