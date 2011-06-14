package dan.jasic.eval;

/**
 * @author Alexander Dovzhikov
 */
public class Eval {

	private Eval() {
	}

	public static Variant add(Variant v1, Variant v2) {
		return v1.add(v2);
	}

	public static Variant sub(Variant v1, Variant v2) {
		return v1.sub(v2);
	}

	public static Variant mul(Variant v1, Variant v2) {
		return v1.mul(v2);
	}

	public static Variant div(Variant v1, Variant v2) {
		return v1.div(v2);
	}

	public static Variant pow(Variant v1, Variant v2) {
		return v1.pow(v2);
	}

	public static Variant neg(Variant v) {
		return v.neg();
	}

	public static Variant lt(Variant v1, Variant v2) {
		return v1.lt(v2);
	}

	public static Variant gt(Variant v1, Variant v2) {
		return v1.gt(v2);
	}

	public static Variant le(Variant v1, Variant v2) {
		return v1.le(v2);
	}

	public static Variant ge(Variant v1, Variant v2) {
		return v1.ge(v2);
	}
}
