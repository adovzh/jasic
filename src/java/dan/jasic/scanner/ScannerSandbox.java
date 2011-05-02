package dan.jasic.scanner;

import dan.jasic.scanner.token.Token;

/**
 * @author Alexander Dovzhikov
 */
public class ScannerSandbox {
    public static void main(String[] args) {
        String code = "LET A$ = \"John\"\nPRINT \"Hello, \"; A$; \"!\"\nLET B = -8.001e-7\r\nPRINT B";
        Scanner scanner = new JasicScanner();
        TokenList tokenList = scanner.scan(code);

        for (Token token : tokenList) {
            System.out.println(token);
        }
    }
}
