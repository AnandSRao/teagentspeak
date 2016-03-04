package uncertainty.global_uncertain_beliefs;

import java.util.HashMap;
import java.util.Map;

import uncertainty.GlobalUncertainBelief;
import uncertainty.epistemic_states.CompactEpistemicState;
import uncertainty.epistemic_states.compact_epistemic_states.ProbabilisticCompactEpistemicState;
import agentspeak.LogicalExpression;
import agentspeak.Unifier;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.operations.binary_operations.Conjunction;
import agentspeak.logical_expressions.operations.binary_operations.Disjunction;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGE;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGT;
import agentspeak.logical_expressions.operations.unary_operations.NegationAsFailure;
import agentspeak.logical_expressions.operations.unary_operations.StrongNegation;
import agentspeak.logical_expressions.relational_expressions.relational_operations.binary_relational_operations.Equal;
import agentspeak.logical_expressions.relational_expressions.relational_operations.binary_relational_operations.NotEqual;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import data_structures.AdvancedSet;
import exceptions.NotGroundException;

public class CompactGlobalUncertainBelief extends GlobalUncertainBelief {
	
	private Map<AdvancedSet<BeliefAtom>, CompactEpistemicState> localEpistemicStates;
	
	public CompactGlobalUncertainBelief() {
		localEpistemicStates = new HashMap<AdvancedSet<BeliefAtom>, CompactEpistemicState>();
	}
	
	public void revise(BeliefLiteral l, double d) throws Exception {
		if(!l.isGround()) {
			throw new NotGroundException(l + " is not ground");
		}
		for(Map.Entry<AdvancedSet<BeliefAtom>, CompactEpistemicState> entry : localEpistemicStates.entrySet()) {
			AdvancedSet<BeliefAtom> domain = entry.getKey();
			BeliefAtom a = l.getBeliefAtom();
			if(domain.contains(a)) {
				CompactEpistemicState es = entry.getValue();
				es.revise(l, d);
				localEpistemicStates.put(domain, es);
				return;
			}
		}
		throw new Exception("no valid local epistemic state for " + l);
	}
	
	public void addLocalEpistemicState(CompactEpistemicState es) throws Exception {
		for(Map.Entry<AdvancedSet<BeliefAtom>, CompactEpistemicState> entry : localEpistemicStates.entrySet()) {
			if(es.getDomain().intersects(entry.getKey())) {
				throw new Exception("overlaps with existing local epistemic state");
			}
		}
		this.addToDomain(es.getDomain());
		localEpistemicStates.put(es.getDomain(), es);
	}
	
	public boolean someLanguageContains(LogicalExpression formula) throws Exception {
		for(Map.Entry<AdvancedSet<BeliefAtom>, CompactEpistemicState> entry : localEpistemicStates.entrySet()) {
			CompactEpistemicState es = entry.getValue();
			if(es.languageContains(formula)) {
				return true;
			}
		}
		return false;
	}
	
	public AdvancedSet<CompactEpistemicState> getLocalEpistemicStates(LogicalExpression formula) {
		AdvancedSet<CompactEpistemicState> relevant = new AdvancedSet<CompactEpistemicState>();
		for(Map.Entry<AdvancedSet<BeliefAtom>, CompactEpistemicState> entry : localEpistemicStates.entrySet()) {
			CompactEpistemicState es = entry.getValue();
			if(es.languageContains(formula)) {
				relevant.add(es);
			}
		}
		return relevant;
	}
	
	@Override
	public Unifier entails(BeliefAtom f, Unifier u) throws Exception {
		BeliefAtom ground = f.substitute(u);
		if(this.someLanguageContains(ground)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(ground)) {
				Unifier unifier = ces.entails(f, u);
				if(unifier != null) {
					return unifier;
				}
			}
			return null;
		} else {
			return null;
		}
	}
	
	@Override
	public Unifier entails(BeliefLiteral f, Unifier u) throws Exception {
		BeliefLiteral ground = f.substitute(u);
		if(this.someLanguageContains(ground)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(ground)) {
				Unifier unifier = ces.entails(f, u);
				if(unifier != null) {
					return unifier;
				}
			}
			return null;
		} else {
			return null;
		}
	}
	
	@Override
	public Unifier entails(Conjunction f, Unifier u) throws Exception {
		Conjunction ground = f.substitute(u);
		if(this.someLanguageContains(ground)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(ground)) {
				Unifier unifier = ces.entails(f, u);
				if(unifier != null) {
					return unifier;
				}
			}
			return null;
		} else {
			Unifier leftUnifier = this.entails(f.getLeft(), u);
			Unifier rightUnifier = null;
			if(leftUnifier != null) {
				rightUnifier = this.entails(f.getRight(), leftUnifier);
			}
			return rightUnifier;
		}
	}
	
	@Override
	public Unifier entails(Disjunction f, Unifier u) throws Exception {
		Disjunction ground = f.substitute(u);
		if(this.someLanguageContains(ground)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(ground)) {
				Unifier unifier = ces.entails(f, u);
				if(unifier != null) {
					return unifier;
				}
			}
			return null;
		} else {
			if(ground.isGround()) {
				if(this.entails(f.getLeft(), u) != null 
						|| this.entails(f.getRight(), u) != null) {
					return u;
				} else {
					return null;
				}
			} else {
				throw new Exception("operator only supported for ground formulae");
			}
		}
	}
	
	@Override
	public Unifier entails(PlausibilityGE f, Unifier u) throws Exception {
		PlausibilityGE ground = f.substitute(u);
		if(this.someLanguageContains(ground)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(ground)) {
				Unifier unifier = ces.entails(f, u);
				if(unifier != null) {
					return unifier;
				}
			}
			return null;
		} else if(ground.isGround() && this.someLanguageContains(ground.getLeft()) && this.someLanguageContains(ground.getRight())) {
			LogicalExpression left = new StrongNegation(ground.getLeft());
			double leftLambda = 0;
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(left)) {
				if(!(ces instanceof ProbabilisticCompactEpistemicState)) {
					return null;
				}
				leftLambda = ces.lambda(left);
				break;
			}
			LogicalExpression right = new StrongNegation(ground.getRight());
			double rightLambda = 0;
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(right)) {
				if(!(ces instanceof ProbabilisticCompactEpistemicState)) {
					return null;
				}
				rightLambda = ces.lambda(right);
				break;
			}
			System.err.println("[9] \\lambda(" + left + ") <= \\lambda(" + right + ") = " + leftLambda + " <= " + rightLambda);
			if(leftLambda <= rightLambda) {
				return u;
			}
			return null;
		} else {
			throw new Exception("operator is only supported across multiple epistemic states if they are probabilistic");
		}
	}
	
	@Override
	public Unifier entails(PlausibilityGT f, Unifier u) throws Exception {
		PlausibilityGT ground = f.substitute(u);
		if(this.someLanguageContains(ground)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(ground)) {
				Unifier unifier = ces.entails(f, u);
				if(unifier != null) {
					return unifier;
				}
			}
			return null;
		} else if(ground.isGround() && this.someLanguageContains(ground.getLeft()) && this.someLanguageContains(ground.getRight())) {
			LogicalExpression left = new StrongNegation(ground.getLeft());
			double leftLambda = 0;
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(left)) {
				if(!(ces instanceof ProbabilisticCompactEpistemicState)) {
					return null;
				}
				leftLambda = ces.lambda(left);
				break;
			}
			LogicalExpression right = new StrongNegation(ground.getRight());
			double rightLambda = 0;
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(right)) {
				if(!(ces instanceof ProbabilisticCompactEpistemicState)) {
					return null;
				}
				rightLambda = ces.lambda(right);
				break;
			}
			System.err.println("[10] \\lambda(" + left + ") < \\lambda(" + right + ") = " + leftLambda + " < " + rightLambda);
			if(leftLambda < rightLambda) {
				return u;
			}
			return null;
		} else {
			throw new Exception("operator is only supported across multiple epistemic states if they are probabilistic");
		}
	}
	
	@Override
	public Unifier entails(StrongNegation f, Unifier u) throws Exception {
		StrongNegation ground = f.substitute(u);
		if(this.someLanguageContains(ground)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(ground)) {
				Unifier unifier = ces.entails(f, u);
				if(unifier != null) {
					return unifier;
				}
			}
			return null;
		} else {
			return null;
		}
	}
	
	@Override
	public Unifier entails(NegationAsFailure f, Unifier u) throws Exception {
		NegationAsFailure ground = f.substitute(u);
		if(this.someLanguageContains(ground)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(ground)) {
				Unifier unifier = ces.entails(f, u);
				if(unifier != null) {
					return unifier;
				}
			}
			return null;
		} else {
			return null;
		}
	}
	
	@Override
	public Unifier entails(Equal f, Unifier u) throws Exception {
		Equal ground = f.substitute(u);
		if(this.someLanguageContains(ground)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(ground)) {
				Unifier unifier = ces.entails(f, u);
				if(unifier != null) {
					return unifier;
				}
			}
			return null;
		} else {
			return null;
		}
	}
	
	@Override
	public Unifier entails(NotEqual f, Unifier u) throws Exception {
		NotEqual ground = f.substitute(u);
		if(this.someLanguageContains(ground)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(ground)) {
				Unifier unifier = ces.entails(f, u);
				if(unifier != null) {
					return unifier;
				}
			}
			return null;
		} else {
			return null;
		}
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		String output = "{";
		String delim = "";
        for(Map.Entry<AdvancedSet<BeliefAtom>, CompactEpistemicState> entry : localEpistemicStates.entrySet()) {
        	output += delim + entry.getValue();
        	delim = ", ";
        }
        output += "}";
        return output;
	}
	
}
