package uncertainty.epistemic_states.compact_epistemic_states;

import uncertainty.epistemic_states.CompactEpistemicState;
import uncertainty.epistemic_states.Weight;
import utilities.Utilities;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.NegativeLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.PositiveLiteral;
import agentspeak.logical_expressions.terminals.primitives.Contradiction;
import data_structures.AdvancedSet;

public class PossibilisticCompactEpistemicState extends CompactEpistemicState {
	
	public PossibilisticCompactEpistemicState(AdvancedSet<BeliefAtom> atoms) throws Exception {
		super(atoms);
	}
	
	@Override
	public Weight getInitialWeight() {
		return new Weight(1, 1);
	}
	
	@Override
	public double getMinWeight() {
		return 0;
	}
	
	@Override
	public double getMaxWeight() {
		return 1;
	}
	
	@Override
	public void revise(BeliefLiteral l, double w) throws Exception {
		Weight atomsPreviousWeight = this.getWeight(l.getBeliefAtom());
		double positiveAlpha = Utilities.max(new AdvancedSet<Double>(atomsPreviousWeight.getPositive(), 1-w));
		double negativeAlpha = Utilities.max(new AdvancedSet<Double>(1-w, atomsPreviousWeight.getNegative()));
		
		if(!super.getDomain().contains(l.getBeliefAtom())) {
			throw new Exception("atom not in domain");
		}
		
		//System.err.println("*(" + l + ", " + w + ")");
		for(BeliefAtom other : this.getDomain()) {
			Weight weight;
			if(this.getWeightedBeliefBase().containsKey(other)) {
				weight = this.getWeightedBeliefBase().get(other);
			} else {
				weight = this.getInitialWeight().copy();
			}
			if(other.equals(l.getBeliefAtom())) {
				if(l.isPositive()) {
					weight.setPositive(Utilities.min(new AdvancedSet<Double>(weight.getPositive(), positiveAlpha)));
					weight.setNegative(Utilities.min(new AdvancedSet<Double>(weight.getNegative(), 1-w, positiveAlpha)));
				} else {
					weight.setPositive(Utilities.min(new AdvancedSet<Double>(weight.getPositive(), 1-w, negativeAlpha)));
					weight.setNegative(Utilities.min(new AdvancedSet<Double>(weight.getNegative(), negativeAlpha)));
				}
			} else {
				if(l.isPositive()) {
					weight.setPositive(Utilities.min(new AdvancedSet<Double>(weight.getPositive(), positiveAlpha)));
					weight.setNegative(Utilities.min(new AdvancedSet<Double>(weight.getNegative(), positiveAlpha)));
				} else {
					weight.setPositive(Utilities.min(new AdvancedSet<Double>(weight.getPositive(), negativeAlpha)));
					weight.setNegative(Utilities.min(new AdvancedSet<Double>(weight.getNegative(), negativeAlpha)));
				}
			}
			
			//System.err.println("W(" + other + ") = " + weight);
			if(weight.equals(this.getInitialWeight())) {
				this.getWeightedBeliefBase().remove(other);
			} else {
				this.getWeightedBeliefBase().put(other, weight);
			}
		}
	}
	
	public Weight getPossibilityMeasure(BeliefAtom a) throws Exception {
		return this.getWeight(a);
	}
	
	public double getPossibilityMeasure(BeliefLiteral l) throws Exception {
		return this.getWeight(l);
	}
	
	@Override
	public double lambda(BeliefLiteral f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		AdvancedSet<BeliefLiteral> copy = bounded.copy();
		copy.add(f);
		for(BeliefLiteral lprime : copy) {
			if(copy.contains(lprime.negation())) {
				return this.lambda(new Contradiction(), copy);
			}
		}
		return this.getPossibilityMeasure(f);
	}
	
	@Override
	public String toString() {
		String output = "{";
		try {
			String delim = "";
			for(BeliefAtom a : this.getDomain()) {
				Weight w = this.getPossibilityMeasure(a);
				if(w.getPositive() != this.getInitialWeight().getPositive()) {
					output += delim + "\\Pi(" + new PositiveLiteral(a).toString() + ")=" + Utilities.format(w.getPositive());
					delim = ", ";
				}
				if(w.getNegative() != this.getInitialWeight().getNegative()) {
					output += delim + "\\Pi(" + new NegativeLiteral(a).toString() + ")=" + Utilities.format(w.getNegative());
					delim = ", ";
				}
			}
			output += "}";
		} catch(Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
}
