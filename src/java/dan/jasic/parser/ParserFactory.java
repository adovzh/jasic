package dan.jasic.parser;

/**
 * @author Alexander Dovzhikov
 */
public class ParserFactory {
	public static Parser createParser() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		return (Parser) Class.forName("dan.jasic.parser.JasicParser").newInstance();
	}
}
