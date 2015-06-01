package uncertainty.epistemic_states.compact_epistemic_states;

import uncertainty.SATSolver;
import uncertainty.epistemic_states.CompactEpistemicState;
import uncertainty.epistemic_states.Weight;
import utilities.Utilities;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.operations.binary_operations.Conjunction;
import agentspeak.logical_expressions.operations.binary_operations.Disjunction;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGE;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGT;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import agentspeak.logical_expressions.terminals.Primitive;
import agentspeak.logical_expressions.terminals.belief_literals.PositiveLiteral;
import agentspeak.logical_expressions.terminals.primitives.Contradiction;
import agentspeak.logical_expressions.terminals.primitives.Tautology;
import data_structures.AdvancedSet;

public class ProbabilisticCompactEpistemicState extends CompactEpistemicState {
	
	public ProbabilisticCompactEpistemicState(AdvancedSet<BeliefAtom> atoms) throws Exception {
		super(atoms);
	}
	
	@Override
	public Weight getInitialWeight() {
		return new Weight(0.5, 0.5);
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
				weight.setNegative(this.getMaxWeight() - w);
			} else {
				weight.setPositive(this.getMaxWeight() - w);
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
				weight.setNegative(this.getMaxWeight() - w);
			} else {
				weight.setPositive(this.getMaxWeight() - w);
				weight.setNegative(w);
			}
			this.getWeightedBeliefBase().put(a, weight);
		}
	}
	
	public double getProbability(BeliefLiteral l) throws Exception {
		return this.getWeight(l);
	}
	
	@Override
	public double lambda(BeliefLiteral f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		return this.getProbability(f);
	}
	
	@Override
	public double lambda(Conjunction f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		SATSolver solver = new SATSolver();
		if(solver.isSatisfiable(f)) {
			return (this.lambda(f.getLeft()) * this.lambda(f.getRight()));
		} else {
			return this.getMinWeight();
		}
	}
	
	@Override
	public double lambda(Disjunction f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		return this.lambda(f.getLeft()) + this.lambda(f.getRight()) - this.lambda(new Conjunction(f.getLeft(), f.getRight()));
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
				Weight w = this.getWeight(a);
				if(w.getPositive() != getInitialWeight().getPositive()) {
					output += delim + "P(" + new PositiveLiteral(a).toString() + ")=" + Utilities.format(w.getPositive());
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
