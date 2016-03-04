package utilities;

import java.text.DecimalFormat;

import data_structures.AdvancedSet;

public class Utilities {
	
	private static DecimalFormat formatter = new DecimalFormat("#.###");
	
	public static String format(double d) {
		return formatter.format(d);
	}
	
	public static double max(AdvancedSet<Double> values) {
		Double max = Double.NEGATIVE_INFINITY;
		for(Double v : values) {
			if(v > max) {
				max = v;
			}
		}
		return max;
	}
	
	public static double min(AdvancedSet<Double> values) {
		Double min = Double.POSITIVE_INFINITY;
		for(Double v : values) {
			if(v < min) {
				min = v;
			}
		}
		return min;
	}
	
}
