package uncertainty.epistemic_states;

import java.text.DecimalFormat;

public class Weight {
	
	private double positive;
	private double negative;
	
	public Weight(double p, double n) {
		positive = p;
		negative = n;
	}
	
	public double getPositive() {
		return positive;
	}
	
	public double getNegative() {
		return negative;
	}
	
	public void setPositive(double p) {
		positive = p;
	}
	
	public void setNegative(double n) {
		negative = n;
	}
	
	public void addPositive(double w) {
		positive += w;
	}
	
	public void addNegative(double w) {
		negative += w;
	}
	
	public void add(Weight w) {
		positive += w.getPositive();
		negative += w.getNegative();
	}
	
	public double max() {
		if(positive >= negative) {
			return positive;
		} else {
			return negative;
		}
	}
	
	public Weight copy() {
		return new Weight(positive, negative);
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		return "(" + df.format(positive) + "," + df.format(negative) + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(negative);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(positive);
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
		Weight other = (Weight) obj;
		if (Double.doubleToLongBits(negative) != Double
				.doubleToLongBits(other.negative))
			return false;
		if (Double.doubleToLongBits(positive) != Double
				.doubleToLongBits(other.positive))
			return false;
		return true;
	}
	
}
