package dan.jasic.parser;

import java.io.IOException;

/**
 * @author Alexander Dovzhikov
 */
public interface Parser {
	boolean parse() throws IOException;
}
