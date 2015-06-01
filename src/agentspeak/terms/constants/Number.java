package agentspeak.terms.constants;

import agentspeak.terms.Constant;
import agentspeak.terms.constants.numbers.DoubleNumber;
import agentspeak.terms.constants.numbers.IntegerNumber;

public abstract class Number extends Constant {
	
	public static boolean isValid(String s) {
		return IntegerNumber.isValid(s) || DoubleNumber.isValid(s);
	}
	
	public abstract int getIntegerValue();
	
	public abstract double getDoubleValue();
	
}
