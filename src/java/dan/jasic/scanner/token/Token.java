package dan.jasic.scanner.token;

/**
 * @author Alexander Dovzhikov
 */
public class Token {
    public static final int WHITESPACE = 1;
    public static final int NEWLINE = 2;
    public static final int ID = 3;
    public static final int NUMBER = 4;
    public static final int QUOTE = 5;

    public static final int AMPERSAND = 6;
    public static final int APOSTROPHE = 7;
    public static final int ASTERISK = 8;
    public static final int COLON = 9;
    public static final int COMMA = 10;
    public static final int DOLLAR = 11;
    public static final int EQ = 12;
    public static final int EXCLAMATION = 13;
    public static final int GT = 14;
    public static final int LT = 15;
    public static final int LBRACE = 16;
    public static final int RBRACE = 17;
    public static final int MINUS = 18;
    public static final int PERCENT = 19;
    public static final int PLUS = 20;
    public static final int POWER = 21;
    public static final int QUESTION = 22;
    public static final int SHARP = 23;
    public static final int SEMICOLON = 24;
    public static final int SLASH = 25;

    public static final int ACCESS = 40;
    public static final int AND = 41;
    public static final int ANGLE = 42;
    public static final int AREA = 43;
    public static final int ARITHMETIC = 44;
    public static final int ARRAY = 45;
    public static final int ASK = 46;
    public static final int AT = 47;
    public static final int BASE = 48;
    public static final int BEGIN = 49;
    public static final int BREAK = 50;
    public static final int CALL = 51;
    public static final int CAUSE = 52;
    public static final int CHAIN = 53;
    public static final int CLEAR = 54;
    public static final int CLIP = 55;
    public static final int CLOSE = 56;
    public static final int COLLATE = 57;
    public static final int COLOR = 58;
    public static final int DATA = 59;
    public static final int DATUM = 60;
    public static final int DEBUG = 61;
    public static final int DECIMAL = 62;
    public static final int DECLARE = 63;
    public static final int DEF = 64;
    public static final int DEGREES = 65;
    public static final int DELETE = 66;
    public static final int DEVICE = 67;
    public static final int DIM = 68;
    public static final int DISPLAY = 69;
    public static final int DO = 70;
    public static final int ELAPSED = 71;
    public static final int ELSE = 72;
    public static final int ELSEIF = 73;
    public static final int END = 74;
    public static final int ERASE = 75;
    public static final int ERASABLE = 76;
    public static final int EXIT = 77;
    public static final int EXLINE = 78;
    public static final int EXTERNAL = 79;
    public static final int EXTYPE = 80;
    public static final int FILETYPE = 81;
    public static final int FIXED = 82;
    public static final int FOR = 83;
    public static final int FUNCTION = 84;
    public static final int GO = 85;
    public static final int GOSUB = 86;
    public static final int GOTO = 87;
    public static final int HANDLER = 88;
    public static final int IF = 89;
    public static final int IMAGE = 90;
    public static final int IN = 91;
    public static final int INPUT = 92;
    public static final int INTERNAL = 93;
    public static final int IS = 94;
    public static final int KEY = 95;
    public static final int KEYED = 96;
    public static final int LENGTH = 97;
    public static final int LET = 98;
    public static final int LINE = 99;
    public static final int LINES = 100;
    public static final int LOOP = 101;
    public static final int MARGIN = 102;
    public static final int MAT = 103;
    public static final int MISSING = 104;
    public static final int NAME = 105;
    public static final int NATIVE = 106;
    public static final int NEXT = 107;
    public static final int NOT = 108;
    public static final int NUMERIC = 109;
    public static final int OF = 110;
    public static final int OFF = 111;
    public static final int ON = 112;
    public static final int OPEN = 113;
    public static final int OPTION = 114;
    public static final int OR = 115;
    public static final int ORGANIZATION = 116;
    public static final int OUTIN = 117;
    public static final int OUTPUT = 118;
    public static final int POINT = 119;
    public static final int POINTER = 120;
    public static final int POINTS = 121;
    public static final int PRINT = 122;
    public static final int PROGRAM = 123;
    public static final int PROMPT = 124;
    public static final int RADIANS = 125;
    public static final int RANDOMIZE = 126;
    public static final int READ = 127;
    public static final int RECORD = 128;
    public static final int RECSIZE = 129;
    public static final int RECTYPE = 130;
    public static final int RELATIVE = 131;
    public static final int REM = 132;
    public static final int REST = 133;
    public static final int RESTORE = 134;
    public static final int RETRY = 135;
    public static final int RETURN = 136;
    public static final int REWRITE = 137;
    public static final int SAME = 138;
    public static final int SELECT = 139;
    public static final int SEQUENTIAL = 140;
    public static final int SET = 141;
    public static final int SETTER = 142;
    public static final int SIZE = 143;
    public static final int SKIP = 144;
    public static final int STANDARD = 145;
    public static final int STATUS = 146;
    public static final int STEP = 147;
    public static final int STOP = 148;
    public static final int STRING = 149;
    public static final int STYLE = 150;
    public static final int SUB = 151;
    public static final int TAB = 152;
    public static final int TEMPLATE = 153;
    public static final int TEXT = 154;
    public static final int THEN = 155;
    public static final int THERE = 156;
    public static final int TIME = 157;
    public static final int TIMEOUT = 158;
    public static final int TO = 159;
    public static final int TRACE = 160;
    public static final int UNTIL = 161;
    public static final int USE = 162;
    public static final int USING = 163;
    public static final int VARIABLE = 164;
    public static final int VIEWPORT = 165;
    public static final int WHEN = 166;
    public static final int WHILE = 167;
    public static final int WINDOW = 168;
    public static final int WITH = 169;
    public static final int WRITE = 170;
    public static final int ZONEWIDTH = 171;

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

        return type == token.type && lexeme.equals(token.lexeme);

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
