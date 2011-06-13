package dan.jasic.parser;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Alexander Dovzhikov
 */
public class ParserFactory {
	public static Parser createParser(InputStream is) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		Class<Parser> clazz = (Class<Parser>)Class.forName("dan.jasic.parser.JasicParser");
		Constructor<Parser> constructor = clazz.getConstructor(InputStream.class);
		return constructor.newInstance(is);
	}
}
