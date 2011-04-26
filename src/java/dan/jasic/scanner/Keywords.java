package dan.jasic.scanner;

import dan.jasic.scanner.token.Keyword;
import dan.jasic.scanner.token.Token;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Dovzhikov
 */
public class Keywords {
    // todo: re-implement using tries
    private final Map<String, Integer> keywords;

    public Keywords() {
        Map<String, Integer> kw = new HashMap<String, Integer>();
        kw.put("ACCESS", Token.ACCESS);
        kw.put("AND", Token.AND);
        kw.put("ANGLE", Token.ANGLE);
        kw.put("AREA", Token.AREA);
        kw.put("ARITHMETIC", Token.ARITHMETIC);
        kw.put("ARRAY", Token.ARRAY);
        kw.put("ASK", Token.ASK);
        kw.put("AT", Token.AT);
        kw.put("BASE", Token.BASE);
        kw.put("BEGIN", Token.BEGIN);
        kw.put("BREAK", Token.BREAK);
        kw.put("CALL", Token.CALL);
        kw.put("CAUSE", Token.CAUSE);
        kw.put("CHAIN", Token.CHAIN);
        kw.put("CLEAR", Token.CLEAR);
        kw.put("CLIP", Token.CLIP);
        kw.put("CLOSE", Token.CLOSE);
        kw.put("COLLATE", Token.COLLATE);
        kw.put("COLOR", Token.COLOR);
        kw.put("DATA", Token.DATA);
        kw.put("DATUM", Token.DATUM);
        kw.put("DEBUG", Token.DEBUG);
        kw.put("DECIMAL", Token.DECIMAL);
        kw.put("DECLARE", Token.DECLARE);
        kw.put("DEF", Token.DEF);
        kw.put("DEGREES", Token.DEGREES);
        kw.put("DELETE", Token.DELETE);
        kw.put("DEVICE", Token.DEVICE);
        kw.put("DIM", Token.DIM);
        kw.put("DISPLAY", Token.DISPLAY);
        kw.put("DO", Token.DO);
        kw.put("ELAPSED", Token.ELAPSED);
        kw.put("ELSE", Token.ELSE);
        kw.put("ELSEIF", Token.ELSEIF);
        kw.put("END", Token.END);
        kw.put("ERASE", Token.ERASE);
        kw.put("ERASABLE", Token.ERASABLE);
        kw.put("EXIT", Token.EXIT);
        kw.put("EXLINE", Token.EXLINE);
        kw.put("EXTERNAL", Token.EXTERNAL);
        kw.put("EXTYPE", Token.EXTYPE);
        kw.put("FILETYPE", Token.FILETYPE);
        kw.put("FIXED", Token.FIXED);
        kw.put("FOR", Token.FOR);
        kw.put("FUNCTION", Token.FUNCTION);
        kw.put("GO", Token.GO);
        kw.put("GOSUB", Token.GOSUB);
        kw.put("GOTO", Token.GOTO);
        kw.put("HANDLER", Token.HANDLER);
        kw.put("IF", Token.IF);
        kw.put("IMAGE", Token.IMAGE);
        kw.put("IN", Token.IN);
        kw.put("INPUT", Token.INPUT);
        kw.put("INTERNAL", Token.INTERNAL);
        kw.put("IS", Token.IS);
        kw.put("KEY", Token.KEY);
        kw.put("KEYED", Token.KEYED);
        kw.put("LENGTH", Token.LENGTH);
        kw.put("LET", Token.LET);
        kw.put("LINE", Token.LINE);
        kw.put("LINES", Token.LINES);
        kw.put("LOOP", Token.LOOP);
        kw.put("MARGIN", Token.MARGIN);
        kw.put("MAT", Token.MAT);
        kw.put("MISSING", Token.MISSING);
        kw.put("NAME", Token.NAME);
        kw.put("NATIVE", Token.NATIVE);
        kw.put("NEXT", Token.NEXT);
        kw.put("NOT", Token.NOT);
        kw.put("NUMERIC", Token.NUMERIC);
        kw.put("OF", Token.OF);
        kw.put("OFF", Token.OFF);
        kw.put("ON", Token.ON);
        kw.put("OPEN", Token.OPEN);
        kw.put("OPTION", Token.OPTION);
        kw.put("OR", Token.OR);
        kw.put("ORGANIZATION", Token.ORGANIZATION);
        kw.put("OUTIN", Token.OUTIN);
        kw.put("OUTPUT", Token.OUTPUT);
        kw.put("POINT", Token.POINT);
        kw.put("POINTER", Token.POINTER);
        kw.put("POINTS", Token.POINTS);
        kw.put("PRINT", Token.PRINT);
        kw.put("PROGRAM", Token.PROGRAM);
        kw.put("PROMPT", Token.PROMPT);
        kw.put("RADIANS", Token.RADIANS);
        kw.put("RANDOMIZE", Token.RANDOMIZE);
        kw.put("READ", Token.READ);
        kw.put("RECORD", Token.RECORD);
        kw.put("RECSIZE", Token.RECSIZE);
        kw.put("RECTYPE", Token.RECTYPE);
        kw.put("RELATIVE", Token.RELATIVE);
        kw.put("REM", Token.REM);
        kw.put("REST", Token.REST);
        kw.put("RESTORE", Token.RESTORE);
        kw.put("RETRY", Token.RETRY);
        kw.put("RETURN", Token.RETURN);
        kw.put("REWRITE", Token.REWRITE);
        kw.put("SAME", Token.SAME);
        kw.put("SELECT", Token.SELECT);
        kw.put("SEQUENTIAL", Token.SEQUENTIAL);
        kw.put("SET", Token.SET);
        kw.put("SETTER", Token.SETTER);
        kw.put("SIZE", Token.SIZE);
        kw.put("SKIP", Token.SKIP);
        kw.put("STANDARD", Token.STANDARD);
        kw.put("STATUS", Token.STATUS);
        kw.put("STEP", Token.STEP);
        kw.put("STOP", Token.STOP);
        kw.put("STRING", Token.STRING);
        kw.put("STYLE", Token.STYLE);
        kw.put("SUB", Token.SUB);
        kw.put("TAB", Token.TAB);
        kw.put("TEMPLATE", Token.TEMPLATE);
        kw.put("TEXT", Token.TEXT);
        kw.put("THEN", Token.THEN);
        kw.put("THERE", Token.THERE);
        kw.put("TIME", Token.TIME);
        kw.put("TIMEOUT", Token.TIMEOUT);
        kw.put("TO", Token.TO);
        kw.put("TRACE", Token.TRACE);
        kw.put("UNTIL", Token.UNTIL);
        kw.put("USE", Token.USE);
        kw.put("USING", Token.USING);
        kw.put("VARIABLE", Token.VARIABLE);
        kw.put("VIEWPORT", Token.VIEWPORT);
        kw.put("WHEN", Token.WHEN);
        kw.put("WHILE", Token.WHILE);
        kw.put("WINDOW", Token.WINDOW);
        kw.put("WITH", Token.WITH);
        kw.put("WRITE", Token.WRITE);
        kw.put("ZONEWIDTH", Token.ZONEWIDTH);

        keywords = Collections.unmodifiableMap(kw);
    }

    public Token checkLexeme(String lexeme) {
        Integer keywordType = keywords.get(lexeme.toUpperCase());
        return (keywordType != null) ? new Keyword(keywordType, lexeme) : null;
    }
}
