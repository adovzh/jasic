package dan.jasic.scanner;

import com.sun.org.apache.xml.internal.security.Init;

/**
 * @author Alexander Dovzhikov
 */
public class Scanner {
    private static final int STATE_MAX = 20;
    private static final int CHAR_MAX = 256;
    private static final int EOF = CHAR_MAX - 1;

    private static final int STATE_INIT = 0;
    private static final int STATE_ID = 1;
    private static final int STATE_QUOTE = 2;
    private static final int STATE_WHITESPACE = 3;
    private static final int STATE_NEWLINE = 4;
    private static final int STATE_QUOTE_END = 5;

    private static Transition[][] TABLE = new Transition[STATE_MAX][CHAR_MAX];

    static {
        Transition transition;

        // STATE_INIT
        initNonAcceptingState(STATE_INIT);
        setLetterState(STATE_INIT, STATE_ID);
        setNextState(STATE_INIT, '"', STATE_QUOTE);
        acceptState(STATE_INIT, '=', Token.EQ);
        acceptState(STATE_INIT, ';', Token.SEMICOLON);
        setWhitespaceState(STATE_INIT, STATE_WHITESPACE);
        setNextState(STATE_INIT, '\r', STATE_NEWLINE);
        acceptState(STATE_INIT, '\n', Token.NEWLINE);
        setTransition(STATE_INIT, EOF, new Transition(Transition.TR_EXIT));

        initAcceptingStatePushBack(STATE_ID, Token.ID);
        transition = new Transition(Transition.TR_NEXT, STATE_ID);
        setLetterTransition(STATE_ID, transition);
        setTransition(STATE_ID, '_', transition);
        setDigitTransition(STATE_ID, transition);
        acceptState(STATE_ID, '$', Token.ID);

        // STATE_QUOTE
        initNonAcceptingState(STATE_QUOTE);
        transition = new Transition(Transition.TR_NEXT, STATE_QUOTE);
        setQuotedCharacterTransition(STATE_QUOTE, transition);
        setNextState(STATE_QUOTE, '"', STATE_QUOTE_END);

        initAcceptingStatePushBack(STATE_WHITESPACE, Token.WHITESPACE);
        setWhitespaceState(STATE_WHITESPACE, STATE_WHITESPACE);

        initAcceptingStatePushBack(STATE_NEWLINE, Token.NEWLINE);
        acceptState(STATE_NEWLINE, '\n', Token.NEWLINE);

        initAcceptingStatePushBack(STATE_QUOTE_END, Token.QUOTE);
        setNextState(STATE_QUOTE_END, '"', STATE_QUOTE);
    }

    private static void initNonAcceptingState(int state) {
        initState(state, new Transition(Transition.TR_ERROR));
    }

    private static void initAcceptingState(int state, int tokenType) {
        initState(state, new Transition(Transition.TR_ACCEPT, tokenType));
    }

    private static void initAcceptingStatePushBack(int state, int tokenType) {
        initState(state, new Transition(Transition.TR_ACCEPT_PB, tokenType));
    }

    private static void initState(int state, Transition transition) {
        for (int c = 0; c < CHAR_MAX; c++) {
            TABLE[state][c] = transition;
        }
    }

    private static void setNextState(int state, char c, int nextState) {
        setTransition(state, c, new Transition(Transition.TR_NEXT, nextState));
    }

    private static void acceptState(int state, int c, int tokenType) {
        setTransition(state, c, new Transition(Transition.TR_ACCEPT, tokenType));
    }

    private static void acceptStatePushBack(int state, int c, int tokenType) {
        setTransition(state, c, new Transition(Transition.TR_ACCEPT_PB, tokenType));
    }

    private static void setTransition(int state, int c, Transition transition) {
        TABLE[state][c] = transition;
    }

    private static void setLetterState(int state, int nextState) {
        setLetterTransition(state, new Transition(Transition.TR_NEXT, nextState));
    }

    private static void setLetterTransition(int state, Transition transition) {
        for (char c = 'A'; c <= 'Z'; c++) {
            setTransition(state, c, transition);
        }

        for (char c = 'a'; c <= 'z'; c++) {
            setTransition(state, c, transition);
        }
    }

    private static void setDigitState(int state, int nextState) {
        setDigitTransition(state, new Transition(Transition.TR_NEXT, nextState));
    }

    private static void setDigitTransition(int state, Transition transition) {
        for (char c = '0'; c <= '9'; c++) {
            setTransition(state, c, transition);
        }
    }

    private static void setWhitespaceState(int state, int nextState) {
        setWhitespaceTransition(state, new Transition(Transition.TR_NEXT, nextState));
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

    private final char[] text;

    private int lexemeStart = 0;
    private int pos = 0;
    private int state = STATE_INIT;

    public Scanner(String text) {
        this(text.toCharArray());
    }

    public Scanner(char[] text) {
        this.text = new char[text.length + 1];
        System.arraycopy(text, 0, this.text, 0, text.length);
        this.text[text.length] = 255;
    }

    public Token getToken() {
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
        } while (transition.type == Transition.TR_NEXT);

        switch (transition.type) {
            case Transition.TR_ACCEPT:
                lexeme = new String(text, lexemeStart, pos - lexemeStart);
                token = new Token(transition.value, lexeme);
                lexemeStart = pos;
                state = STATE_INIT;
                return token;
            case Transition.TR_ACCEPT_PB:
                lexeme = new String(text, lexemeStart, pos - lexemeStart - 1);
                token = new Token(transition.value, lexeme);
                lexemeStart = --pos;
                state = STATE_INIT;
                return token;
            case Transition.TR_ERROR:
                throw new IllegalStateException("Unexpected symbol: " + text[pos - 1]);
            case Transition.TR_EXIT:
                return null;
            default:
                throw new IllegalStateException("Impossible situation!");
        }
    }

    private static class Transition {
        static final int TR_ERROR = 0;
        static final int TR_NEXT = 1;
        static final int TR_ACCEPT = 2;
        static final int TR_ACCEPT_PB = 3;
        static final int TR_EXIT = 4;

        int type;
        int value;

        Transition(int type) {
            this.type = type;
        }

        Transition(int type, int value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Transition{" +
                    "type=" + type +
                    ", value=" + value +
                    '}';
        }
    }
}
