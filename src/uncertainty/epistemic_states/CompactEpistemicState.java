package uncertainty.epistemic_states;

import java.util.HashMap;
import java.util.Map;

import uncertainty.EpistemicState;
import agentspeak.LogicalExpression;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.operations.binary_operations.Conjunction;
import agentspeak.logical_expressions.operations.binary_operations.Disjunction;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGE;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGT;
import agentspeak.logical_expressions.operations.unary_operations.NegationAsFailure;
import agentspeak.logical_expressions.relational_expressions.relational_operations.binary_relational_operations.Equal;
import agentspeak.logical_expressions.relational_expressions.relational_operations.binary_relational_operations.NotEqual;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.NegativeLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.PositiveLiteral;
import agentspeak.logical_expressions.terminals.primitives.Contradiction;
import agentspeak.logical_expressions.terminals.primitives.Tautology;
import data_structures.AdvancedSet;
import exceptions.NotGroundException;

public class CompactEpistemicState extends EpistemicState {
	
	private Map<BeliefAtom, Weight> weightedBeliefBase;
	private double totalWeight;
	
	private final Weight initialWeight = new Weight(0, 0);
	
	public CompactEpistemicState(AdvancedSet<BeliefAtom> atoms) throws Exception {
		super(atoms);
		weightedBeliefBase = new HashMap<BeliefAtom, Weight>();
		totalWeight = 0;
	}
	
	public Weight getInitialWeight() {
		return initialWeight;
	}
	
	public Map<BeliefAtom, Weight> getWeightedBeliefBase() {
		return weightedBeliefBase;
	}
	
	@Override
	public double getMinWeight() {
		return Double.NEGATIVE_INFINITY;
	}
	
	@Override
	public double getMaxWeight() {
		return totalWeight;
	}
	
	public void revise(BeliefLiteral l, double w) throws Exception {
		BeliefAtom a = l.getBeliefAtom();
		
		if(!super.getDomain().contains(a)) {
			throw new Exception("atom not in domain");
		}
		
		if(w == 0) {
			return;
		}
		
		if(weightedBeliefBase.containsKey(a)) {
			Weight existingWeight = weightedBeliefBase.get(a);
			totalWeight -= existingWeight.max();
			if(l.isPositive()) {
				existingWeight.addPositive(w);
			} else {
				existingWeight.addNegative(w);
			}
			if(existingWeight.equals(getInitialWeight())) {
				weightedBeliefBase.remove(a);
			} else {
				weightedBeliefBase.put(a, existingWeight);
				totalWeight += existingWeight.max();
			}
		} else {
			Weight newWeight = getInitialWeight().copy();
			if(l.isPositive()) {
				newWeight.addPositive(w);
			} else {
				newWeight.addNegative(w);
			}
			weightedBeliefBase.put(a, newWeight);
			totalWeight += newWeight.max();
		}
	}
	
	public Weight getWeight(BeliefAtom a) throws Exception {
		if(super.getDomain().contains(a)) {
			if(weightedBeliefBase.containsKey(a)) {
				return weightedBeliefBase.get(a);
			} else {
				return getInitialWeight().copy();
			}
		} else {
			throw new Exception("atom not in domain");
		}
	}
	
	public double getWeight(BeliefLiteral l) throws Exception {
		if(l.isPositive()) {
			return getWeight((PositiveLiteral)l);
		} else {
			return getWeight((NegativeLiteral)l);
		}
	}
	
	public double getWeight(PositiveLiteral l) throws Exception {
		BeliefAtom a = l.getBeliefAtom();
		if(super.getDomain().contains(a)) {
			if(weightedBeliefBase.containsKey(a)) {
				Weight w = weightedBeliefBase.get(a);
				return w.getPositive();
			} else {
				return getInitialWeight().getPositive();
			}
		} else {
			throw new Exception("atom not in domain");
		}
	}
	
	public double getWeight(NegativeLiteral l) throws Exception {
		BeliefAtom a = l.getBeliefAtom();
		if(super.getDomain().contains(a)) {
			if(weightedBeliefBase.containsKey(a)) {
				Weight w = weightedBeliefBase.get(a);
				return w.getNegative();
			} else {
				return getInitialWeight().getNegative();
			}
		} else {
			throw new Exception("atom not in domain");
		}
	}
	
	@Override
	public double lambda(LogicalExpression f) throws Exception {
		if(!f.isGround()) {
			throw new NotGroundException("formula must be ground");
		}
		LogicalExpression formula = this.pare(f);
		if(!formula.inNNF()) {
			formula = formula.toNNF();
		}
		return this.lambda(formula, new AdvancedSet<BeliefLiteral>());
	}
	
	private double lambda(LogicalExpression f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		if(f instanceof Contradiction) {
			return this.lambda((Contradiction)f, bounded);
		} else if(f instanceof Tautology) {
			return this.lambda((Tautology)f, bounded);
		} else if(f instanceof BeliefLiteral) {
			return this.lambda((BeliefLiteral)f, bounded);
		} else if(f instanceof Conjunction) {
			return this.lambda((Conjunction)f, bounded);
		} else if(f instanceof Disjunction) {
			return this.lambda((Disjunction)f, bounded);
		} else if(f instanceof PlausibilityGE) {
			return this.lambda((PlausibilityGE)f, bounded);
		} else if(f instanceof PlausibilityGT) {
			return this.lambda((PlausibilityGT)f, bounded);
		} else if(f instanceof NegationAsFailure) {
			return this.lambda((NegationAsFailure)f, bounded);
		} else if(f instanceof Equal) {
			return this.lambda((Equal)f, bounded);
		} else if(f instanceof NotEqual) {
			return this.lambda((NotEqual)f, bounded);
		} else {
			throw new UnsupportedOperationException("formula must be normalized");
		}
	}
	
	public double lambda(Contradiction f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		return this.getMinWeight();
	}
	
	private double lambda(Tautology f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		return this.getMaxWeight();
	}
	
	public double lambda(BeliefLiteral f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		AdvancedSet<BeliefLiteral> copy = bounded.copy();
		copy.add(f);
		double sum = 0;
		for(BeliefLiteral lprime : copy) {
			if(copy.contains(lprime.negation())) {
				return this.lambda(new Contradiction(), copy);
			} else {
				Weight weight = this.getWeight(lprime.getBeliefAtom());
				if(lprime.isPositive()) {
					sum += Math.abs(weight.getPositive() - weight.max());
				} else {
					sum += Math.abs(weight.getNegative() - weight.max());
				}
			}
		}
		return this.getMaxWeight() - sum;
	}
	
	public double lambda(Conjunction f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		AdvancedSet<BeliefLiteral> copy = bounded.copy();
		if(f.getLeft().isDisjunctive() && f.getRight().isConjunctive()) {
			copy.addAll(f.getRight().getBeliefLiterals());
			return this.lambda(f.getLeft(), copy);
		} else if(f.getLeft().isConjunctive() && f.getRight().isDisjunctive()) {
			copy.addAll(f.getLeft().getBeliefLiterals());
			return this.lambda(f.getRight(), copy);
		} else {
			throw new Exception("formula not in tractable language: " + this.toString());
		}
	}
	
	public double lambda(Disjunction f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		double lambdaLeft = this.lambda(f.getLeft(), bounded);
		double lambdaRight = this.lambda(f.getRight(), bounded);
		if(lambdaLeft >= lambdaRight) {
			return lambdaLeft;
		} else {
			return lambdaRight;
		}
	}
	
	private double lambda(PlausibilityGE f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		return this.lambda(this.pare(f), bounded);
	}
	
	private double lambda(PlausibilityGT f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		return this.lambda(this.pare(f), bounded);
	}
	
	private double lambda(NegationAsFailure f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		return this.lambda(this.pare(f), bounded);
	}
	
	private double lambda(Equal f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		return this.lambda(this.pare(f), bounded);
	}
	
	private double lambda(NotEqual f, AdvancedSet<BeliefLiteral> bounded) throws Exception {
		return this.lambda(this.pare(f), bounded);
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		String output = "{";
		String delim = "";
		for(Map.Entry<BeliefAtom, Weight> entry : weightedBeliefBase.entrySet()) {
			output += delim + "(" + entry.getKey().toString() + "," + entry.getValue().toString() + ")";
			delim = ", ";
		}
		output += "}";
		return output;
	}
	
}
