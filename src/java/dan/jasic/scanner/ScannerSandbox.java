package dan.jasic.scanner;

/**
 * @author Alexander Dovzhikov
 */
public class ScannerSandbox {
    public static void main(String[] args) {
        String code = "LET A$ = \"Hello!\"";
        Scanner scanner = new Scanner(code);
        Token token;

        while ((token = scanner.getToken()) != null) {
            System.out.println(token);
        }
    }
}
