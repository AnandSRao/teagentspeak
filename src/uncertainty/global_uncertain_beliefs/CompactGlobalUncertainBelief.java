package uncertainty.global_uncertain_beliefs;

import java.util.HashMap;
import java.util.Map;

import uncertainty.GlobalUncertainBelief;
import uncertainty.epistemic_states.CompactEpistemicState;
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
		if(this.someLanguageContains(f)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(f)) {
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
		if(this.someLanguageContains(f)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(f)) {
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
		if(this.someLanguageContains(f)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(f)) {
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
		if(this.someLanguageContains(f)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(f)) {
				Unifier unifier = ces.entails(f, u);
				if(unifier != null) {
					return unifier;
				}
			}
			return null;
		} else {
			throw new Exception("unsupported operator");
		}
	}
	
	@Override
	public Unifier entails(PlausibilityGE f, Unifier u) throws Exception {
		if(this.someLanguageContains(f)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(f)) {
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
	public Unifier entails(PlausibilityGT f, Unifier u) throws Exception {
		if(this.someLanguageContains(f)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(f)) {
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
	public Unifier entails(StrongNegation f, Unifier u) throws Exception {
		if(this.someLanguageContains(f)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(f)) {
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
		if(this.someLanguageContains(f)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(f)) {
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
		if(this.someLanguageContains(f)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(f)) {
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
		if(this.someLanguageContains(f)) {
			for(CompactEpistemicState ces : this.getLocalEpistemicStates(f)) {
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
