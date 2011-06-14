package dan.jasic.eval;

/**
 * @author Alexander Dovzhikov
 */
public class Variant {

	private final long value;
	private final ValueType type;

	private Variant(ValueType type, long value) {
		this.value = value;
		this.type = type;
	}

	public boolean booleanValue() {
		checkType(ValueType.BOOLEAN);

		return (value != 0L);
	}

	public double doubleValue() {
		checkType(ValueType.DOUBLE);

		return Double.longBitsToDouble(value);
	}

	public static Variant valueOf(boolean b) {
		return new Variant(ValueType.BOOLEAN, (b) ? 1L : 0L);
	}

	public static Variant valueOf(double d) {
		return new Variant(ValueType.DOUBLE, Double.doubleToRawLongBits(d));
	}

	@Override
	public String toString() {
		switch (type) {
			case BOOLEAN:
				return Boolean.toString(booleanValue());
			case DOUBLE:
				return Double.toString(doubleValue());
			default:
				throw new UnsupportedOperationException("Variant.toString(): " + type);
		}
	}

	Variant add(Variant v) {
		return Variant.valueOf(doubleValue() + v.doubleValue());
	}

	Variant sub(Variant v) {
		return Variant.valueOf(doubleValue() - v.doubleValue());
	}

	Variant mul(Variant v) {
		return Variant.valueOf(doubleValue() * v.doubleValue());
	}

	Variant div(Variant v) {
		return Variant.valueOf(doubleValue() / v.doubleValue());
	}

	Variant pow(Variant v) {
		return Variant.valueOf(Math.pow(doubleValue(), v.doubleValue()));
	}

	Variant neg() {
		return Variant.valueOf(-doubleValue());
	}

	public Variant lt(Variant v1) {
		return Variant.valueOf(doubleValue() < v1.doubleValue());
	}

	public Variant gt(Variant v1) {
		return Variant.valueOf(doubleValue() > v1.doubleValue());
	}

	public Variant le(Variant v1) {
		return Variant.valueOf(doubleValue() <= v1.doubleValue());
	}

	public Variant ge(Variant v1) {
		return Variant.valueOf(doubleValue() >= v1.doubleValue());
	}

	private void checkType(ValueType type) {
		// todo: implement conversion
		if (this.type != type)
			throw new SemanticException("Cannot convert " + this.type + " to " + type);
	}

	enum ValueType { DOUBLE, BOOLEAN }
}
