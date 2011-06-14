package dan.jasic.parser;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Alexander Dovzhikov
 */
public class ParserRunner {

	public static void main(String[] args) throws Exception {
		InputStream is = (args.length > 0) ? new FileInputStream(args[0]) : System.in;
		Parser parser = ParserFactory.createParser(is);
		boolean result = parser.parse();
		System.out.println((result) ? "OK" : "FAILED");
	}
}
