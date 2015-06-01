package agentspeak.terms.constants.numbers;

import agentspeak.terms.constants.Number;

public class IntegerNumber extends Number {
	
	private int value;
	
	public IntegerNumber(int v) {
		value = v;
	}
	
	public IntegerNumber(String s) {
		this(Integer.parseInt(s));
	}
	
	public static boolean isValid(String s) {
		try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public int getIntegerValue() {
		return value;
	}
	
	@Override
	public double getDoubleValue() {
		return (double)value;
	}
	
	@Override
	public String toString() {
		return Integer.toString(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegerNumber other = (IntegerNumber) obj;
		if (value != other.value)
			return false;
		return true;
	}
	
}
