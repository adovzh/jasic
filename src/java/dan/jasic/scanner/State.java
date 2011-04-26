package dan.jasic.scanner;

/**
 * @author Alexander Dovzhikov
 */
public enum State {
    STATE_INIT,
    STATE_WHITESPACE,
    STATE_NEWLINE,
    STATE_ID,
    STATE_QUOTE,
    STATE_QUOTE_END,
    STATE_NUMBER1,
    STATE_NUMBER2,
    STATE_NUMBER3,
    STATE_NUMBER4,
    STATE_NUMBER5,
    STATE_NUMBER6;

    static final int CHAR_MAX = 256;
    static final int EOF = CHAR_MAX - 1;
    
    private final Transition[] transitions = new Transition[CHAR_MAX];

    public Transition getTransition(int c) {
        return transitions[c];
    }

    public void initNonAccepting() {
        init(Transition.ERROR);
    }

    public void initAcceptingPushBack(int tokenType) {
        init(Transition.acceptPushBack(tokenType));
    }

    private void init(Transition transition) {
        for (int c = 0; c < State.CHAR_MAX; c++) {
            transitions[c] = transition;
        }
    }

    public void setNextState(char c, State state) {
        setTransition(c, Transition.next(state));
    }

    public void acceptState(char c, int tokenType) {
        setTransition(c, Transition.accept(tokenType));
    }

    public void setTransition(int c, Transition transition) {
        transitions[c] = transition;
    }

    public void setLetterState(State state) {
        setLetterTransition(Transition.next(state));
    }

    public  void setLetterTransition(Transition transition) {
        for (char c = 'A'; c <= 'Z'; c++) {
            setTransition(c, transition);
        }

        for (char c = 'a'; c <= 'z'; c++) {
            setTransition(c, transition);
        }
    }

    public void setWhitespaceState(State state) {
        setWhitespaceTransition(Transition.next(state));
    }

    public void setWhitespaceTransition(Transition transition) {
        setTransition(' ', transition);
        setTransition('\t', transition);
    }

    public void setDigitTransition(Transition transition) {
        for (int c = '0'; c <= '9'; c++) {
            setTransition(c, transition);
        }
    }

    public void setQuotedCharacterTransition(Transition transition) {
        for (char c = 32; c <= 126; c++) {
            setTransition(c, transition);
        }
    }
}
