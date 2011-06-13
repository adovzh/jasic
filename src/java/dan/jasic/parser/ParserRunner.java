package dan.jasic.parser;

/**
 * @author Alexander Dovzhikov
 */
public class ParserRunner {

	public static void main(String[] args) throws Exception {
		Parser parser = ParserFactory.createParser();
		boolean result = parser.parse();
		System.out.println("Result: " + result);
	}
}
