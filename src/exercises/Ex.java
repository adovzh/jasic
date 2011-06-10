public class Ex {
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("Exercise number required");
			System.exit(-1);
		}

		try {
			int number = Integer.parseInt(args[0]);	
			ParserObject p = ParserLoader.load(number);
			p.parse();
		} catch (NumberFormatException e) {
			System.out.println("Wrong exercise number: " + args[0]);
		}
	}
}
