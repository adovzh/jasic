package dan.jasic.scanner;

/**
 * @author Alexander Dovzhikov
 */
public class JasicScanner extends Scanner {
    private final Keywords keywords = new Keywords();

    public JasicScanner() {
        initStates();
    }

    protected Token createToken(int tokenType, String lexeme) {
        if (tokenType == Token.ID) {
            Token keyword = keywords.checkLexeme(lexeme);

            if (keyword != null)
                return keyword;
        }

        return super.createToken(tokenType, lexeme);
    }

    private void initStates() {
        State initState = new State();
        State whitespaceState = new State();
        State newlineState = new State();
        State idState = new State();
        State quoteState = new State();
        State quoteEndState = new State();
        State number1State = new State();
        State number2State = new State();
        State number3State = new State();
        State number4State = new State();
        State number5State = new State();
        State number6State = new State();

        setStartState(initState);

        Transition transition;
        Transition number1Transition = Transition.next(number1State);
        Transition number2Transition = Transition.next(number2State);
        Transition number3Transition = Transition.next(number3State);
        Transition number4Transition = Transition.next(number4State);
        Transition number5Transition = Transition.next(number5State);
        Transition number6Transition = Transition.next(number6State);

        // STATE_INIT
        initState.initNonAccepting();
        initState.setTransition(State.EOF, Transition.EXIT);
        initState.setLetterState(idState);
        initState.setNextState('"', quoteState);
        initState.acceptState('=', Token.EQ);
        initState.acceptState(';', Token.SEMICOLON);
        initState.setWhitespaceState(whitespaceState);
        initState.setNextState('\r', newlineState);
        initState.acceptState('\n', Token.NEWLINE);
        initState.acceptState('+', Token.PLUS);
        initState.acceptState('-', Token.MINUS);
        initState.acceptState('*', Token.ASTERISK);
        initState.acceptState('/', Token.SLASH);
        initState.acceptState('^', Token.POWER);
        initState.acceptState('(', Token.LBRACE);
        initState.acceptState(')', Token.RBRACE);
        initState.acceptState(',', Token.COMMA);
        initState.setDigitTransition(number1Transition);
        initState.setTransition('.', number2Transition);

        // STATE_WHITESPACE
        whitespaceState.initAcceptingPushBack(Token.WHITESPACE);
        whitespaceState.setWhitespaceState(whitespaceState);

        // STATE_NEWLINE
        newlineState.initAcceptingPushBack(Token.NEWLINE);
        newlineState.acceptState('\n', Token.NEWLINE);

        // STATE_ID
        idState.initAcceptingPushBack(Token.ID);
        transition = Transition.next(idState);
        idState.setLetterTransition(transition);
        idState.setTransition('_', transition);
        idState.setDigitTransition(transition);
        idState.acceptState('$', Token.ID);

        // STATE_QUOTE
        quoteState.initNonAccepting();
        transition = Transition.next(quoteState);
        quoteState.setQuotedCharacterTransition(transition);
        quoteState.setNextState('"', quoteEndState);

        // STATE_QUOTE_END
        quoteEndState.initAcceptingPushBack(Token.QUOTE);
        quoteEndState.setNextState('"', quoteState);

        // STATE_NUMBER1 (K)
        number1State.initAcceptingPushBack(Token.NUMBER);
        number1State.setDigitTransition(number1Transition);
        number1State.setTransition('.', number3Transition);
        number1State.setTransition('E', number4Transition);
        number1State.setTransition('e', number4Transition);

        // STATE_NUMBER2 (L)
        number2State.initNonAccepting();
        number2State.setDigitTransition(number3Transition);

        // STATE_NUMBER3 (M)
        number3State.initAcceptingPushBack(Token.NUMBER);
        number3State.setDigitTransition(number3Transition);
        number3State.setTransition('E', number4Transition);
        number3State.setTransition('e', number4Transition);

        // STATE_NUMBER4 (N)
        number4State.initNonAccepting();
        number4State.setTransition('+', number5Transition);
        number4State.setTransition('-', number5Transition);
        number4State.setDigitTransition(number6Transition);

        // STATE_NUMBER5 (P)
        number5State.initNonAccepting();
        number5State.setDigitTransition(number6Transition);

        // STATE_NUMBER6 (Q)
        number6State.initAcceptingPushBack(Token.NUMBER);
        number6State.setDigitTransition(number6Transition);
    }
}
