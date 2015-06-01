package agentspeak.terms.constants.numbers;

import utilities.Utilities;
import agentspeak.terms.constants.Number;

public class DoubleNumber extends Number {
	
	private double value;
	
	public DoubleNumber(double v) {
		value = v;
	}
	
	public DoubleNumber(String s) {
		this(Double.parseDouble(s));
	}
	
	public static boolean isValid(String s) {
		try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	@Override
	public int getIntegerValue() {
		return (int)value;
	}
	
	@Override
	public double getDoubleValue() {
		return value;
	}

	@Override
	public String toString() {
		return Utilities.format(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		DoubleNumber other = (DoubleNumber) obj;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
			return false;
		return true;
	}
	
}
