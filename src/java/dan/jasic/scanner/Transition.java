package dan.jasic.scanner;

/**
* @author Alexander Dovzhikov
*/
class Transition {
    enum Type {
        ERROR, NEXT, ACCEPT, ACCEPT_PB, EXIT
    }

    static final Transition ERROR = new Transition(Type.ERROR);
    static final Transition EXIT = new Transition(Type.EXIT);

    static Transition next(State state) {
        return new Transition(state);
    }

    static Transition accept(int tokenType) {
        return new Transition(Type.ACCEPT, tokenType);
    }

    static Transition acceptPushBack(int tokenType) {
        return new Transition(Type.ACCEPT_PB, tokenType);
    }

    // todo: strange design
    final Type type;
    int value;
    State nextState;

    Transition(Type type) {
        this.type = type;
        this.value = 0;
    }

    Transition(Type type, int value) {
        this.type = type;
        this.value = value;
    }

    Transition(State nextState) {
        this.type = Type.NEXT;
        this.nextState = nextState;
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
