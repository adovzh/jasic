package dan.jasic.scanner;

/**
 * @author Alexander Dovzhikov
 */
public class Token {
    public static final int WHITESPACE = 1;
    public static final int NEWLINE = 2;
    public static final int ID = 3;
    public static final int NUMBER = 4;
    public static final int QUOTE = 5;
    public static final int EQ = 6;
    public static final int SEMICOLON = 7;
    public static final int PLUS = 8;
    public static final int MINUS = 9;
    public static final int ASTERISK = 10;
    public static final int SLASH = 11;
    public static final int LBRACE = 12;
    public static final int RBRACE = 13;

    public static final int ACCESS = 40;
    public static final int AND = 40;
    public static final int ANGLE = 40;
    public static final int AREA = 40;
    public static final int ARITHMETIC = 40;
    public static final int ARRAY = 40;
    public static final int ASK = 40;
    public static final int AT = 40;
    public static final int BASE = 40;
    public static final int BEGIN = 40;
    public static final int BREAK = 40;
    public static final int CALL = 40;
    public static final int CAUSE = 40;
    public static final int CHAIN = 40;
    public static final int CLEAR = 40;
    public static final int CLIP = 40;
    public static final int CLOSE = 40;
    public static final int COLLATE = 40;
    public static final int COLOR = 40;
    public static final int DATA = 40;
    public static final int DATUM = 40;
    public static final int DEBUG = 40;
    public static final int DECIMAL = 40;
    public static final int DECLARE = 40;
    public static final int DEF = 40;
    public static final int DEGREES = 40;
    public static final int DELETE = 40;
    public static final int DEVICE = 40;
    public static final int DIM = 40;
    public static final int DISPLAY = 40;
    public static final int DO = 40;
    public static final int ELAPSED = 40;
    public static final int ELSE = 40;
    public static final int ELSEIF = 40;
    public static final int END = 40;
    public static final int ERASE = 40;
    public static final int ERASABLE = 40;
    public static final int EXIT = 40;
    public static final int EXLINE = 40;
    public static final int EXTERNAL = 40;
    public static final int EXTYPE = 40;
    public static final int FILETYPE = 40;
    public static final int FIXED = 40;
    public static final int FOR = 40;
    public static final int FUNCTION = 40;
    public static final int GO = 40;
    public static final int GOSUB = 40;
    public static final int GOTO = 40;
    public static final int HANDLER = 40;
    public static final int IF = 40;
    public static final int IMAGE = 40;
    public static final int IN = 40;
    public static final int INPUT = 40;
    public static final int INTERNAL = 40;
    public static final int IS = 40;
    public static final int KEY = 40;
    public static final int KEYED = 40;
    public static final int LENGTH = 40;
    public static final int LET = 40;
    public static final int LINE = 40;
    public static final int LINES = 40;
    public static final int LOOP = 40;
    public static final int MARGIN = 40;
    public static final int MAT = 40;
    public static final int MISSING = 40;
    public static final int NAME = 40;
    public static final int NATIVE = 40;
    public static final int NEXT = 40;
    public static final int NOT = 40;
    public static final int NUMERIC = 40;
    public static final int OF = 40;
    public static final int OFF = 40;
    public static final int ON = 40;
    public static final int OPEN = 40;
    public static final int OPTION = 40;
    public static final int OR = 40;
    public static final int ORGANIZATION = 40;
    public static final int OUTIN = 40;
    public static final int OUTPUT = 40;
    public static final int POINT = 40;
    public static final int POINTER = 40;
    public static final int POINTS = 40;
    public static final int PRINT = 40;
    public static final int PROGRAM = 40;
    public static final int PROMPT = 40;
    public static final int RADIANS = 40;
    public static final int RANDOMIZE = 40;
    public static final int READ = 40;
    public static final int RECORD = 40;
    public static final int RECSIZE = 40;
    public static final int RECTYPE = 40;
    public static final int RELATIVE = 40;
    public static final int REM = 40;
    public static final int REST = 40;
    public static final int RESTORE = 40;
    public static final int RETRY = 40;

    private final int type;
    private final String lexeme;

    public Token(int type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    public boolean isWhitespace() {
        return type == WHITESPACE;
    }

    public int getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (type != token.type) return false;
        if (!lexeme.equals(token.lexeme)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + lexeme.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", lexeme='" + lexeme + '\'' +
                '}';
    }
}
