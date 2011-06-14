package dan.jasic.tree;

import dan.jasic.eval.Variant;

public class ExpressionStatement extends Statement {
	private final Variant value;

	public ExpressionStatement(Variant value) {
		this.value = value;
	}

	public Variant getValue() {
		return value;
	}
}
