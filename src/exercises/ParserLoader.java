public class ParserLoader {
	public static ParserObject load(int exercise) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (exercise <= 0 || exercise >= 100)
			throw new IllegalArgumentException("Illegal exercise number: " + exercise);

		String className = String.format("Ex%02dParser", exercise);
		return (ParserObject)Class.forName(className).newInstance();
	}
}
