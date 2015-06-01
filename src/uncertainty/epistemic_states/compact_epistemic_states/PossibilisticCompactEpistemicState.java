package uncertainty.epistemic_states.compact_epistemic_states;

import uncertainty.epistemic_states.CompactEpistemicState;
import uncertainty.epistemic_states.Weight;
import utilities.Utilities;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.operations.binary_operations.Disjunction;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGE;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGT;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import agentspeak.logical_expressions.terminals.Primitive;
import agentspeak.logical_expressions.terminals.belief_literals.NegativeLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.PositiveLiteral;
import agentspeak.logical_expressions.terminals.primitives.Contradiction;
import agentspeak.logical_expressions.terminals.primitives.Tautology;
import data_structures.AdvancedSet;

public class PossibilisticCompactEpistemicState extends CompactEpistemicState {
	
	public PossibilisticCompactEpistemicState(AdvancedSet<BeliefAtom> atoms) throws Exception {
		super(atoms);
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
		BeliefAtom a = l.getBeliefAtom();
		
		if(!super.getDomain().contains(a)) {
			throw new Exception("atom not in domain");
		}
		
		if(this.getWeightedBeliefBase().containsKey(a)) {
			Weight weight = this.getWeightedBeliefBase().get(a);
			if(l.isPositive()) {
				weight.setPositive(w);
			} else {
				weight.setNegative(w);
			}
			if(weight.equals(this.getInitialWeight())) {
				this.getWeightedBeliefBase().remove(a);
			} else {
				this.getWeightedBeliefBase().put(a, weight);
			}
		} else {
			Weight weight = this.getInitialWeight().copy();
			if(l.isPositive()) {
				weight.setPositive(w);
			} else {
				weight.setNegative(w);
			}
			this.getWeightedBeliefBase().put(a, weight);
		}
	}
	
	public Weight getLowerNecessityBound(BeliefAtom a) throws Exception {
		return this.getWeight(a);
	}
	
	public double getLowerNecessityBound(BeliefLiteral l) throws Exception {
		return this.getWeight(l);
	}
	
	public Weight getUpperPossibilityBound(BeliefAtom a) throws Exception {
		Weight necessity = getLowerNecessityBound(a);
		double negatedPositive = 1 - necessity.getNegative();
		double negatedNegative = 1 - necessity.getPositive();
		return new Weight(negatedPositive, negatedNegative);
	}
	
	public double getMinLowerNecessityBound(BeliefAtom a) throws Exception {
		Weight weight = this.getLowerNecessityBound(a);
		if(weight.getPositive() <= weight.getNegative()) {
			return weight.getPositive();
		} else {
			return weight.getNegative();
		}
	}
	
	public double getNecessityMeasure(BeliefLiteral l) throws Exception {
		AdvancedSet<Double> values = new AdvancedSet<Double>();
		values.add(this.getLowerNecessityBound(l));
		for(BeliefAtom a : this.getDomain()) {
			if(!a.equals(l.getBeliefAtom())) {
				values.add(this.getMinLowerNecessityBound(a));
			}
		}
		return Utilities.max(values);
	}
	
	@Override
	public double lambda(Contradiction f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		AdvancedSet<Double> values = new AdvancedSet<Double>();
		for(BeliefAtom a : this.getDomain()) {
			values.add(this.getMinLowerNecessityBound(a));
		}
		return Utilities.max(values);
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
		return this.getNecessityMeasure(f);
	}
	
	@Override
	public double lambda(Disjunction f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		throw new UnsupportedOperationException("cannot compute exact necessity measure for disjunction");
	}
	
	@Override
	public Primitive pare(PlausibilityGE f) throws Exception {
		if(this.lambda(f.getLeft()) >= this.lambda(f.getRight())) {
			return new Tautology();
		} else {
			return new Contradiction();
		}
	}
	
	@Override
	public Primitive pare(PlausibilityGT f) throws Exception {
		if(this.lambda(f.getLeft()) > this.lambda(f.getRight())) {
			return new Tautology();
		} else {
			return new Contradiction();
		}
	}
	
	@Override
	public String toString() {
		String output = "{";
		try {
			String delim = "";
			for(BeliefAtom a : this.getDomain()) {
				Weight w = this.getLowerNecessityBound(a);
				if(w.getPositive() > 0) {
					output += delim + "N(" + new PositiveLiteral(a).toString() + ")>=" + Utilities.format(w.getPositive());
					delim = ", ";
				}
				if(w.getNegative() > 0) {
					output += delim + "N(" + new NegativeLiteral(a).toString() + ")>=" + Utilities.format(w.getNegative());
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
