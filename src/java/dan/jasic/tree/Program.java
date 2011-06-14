package dan.jasic.tree;

import java.util.*;

public class Program extends Node {
	private final List<Statement> statements = new ArrayList<Statement>();

	public Program(Statement stm) {
		statements.add(stm);
	}

	public Program addStatement(Statement stm) {
		statements.add(stm);
		return this;
	}
}
