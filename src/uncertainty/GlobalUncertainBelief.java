package uncertainty;

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
import agentspeak.logical_expressions.terminals.primitives.Contradiction;
import agentspeak.logical_expressions.terminals.primitives.Tautology;

public abstract class GlobalUncertainBelief {
	
	public Unifier entails(LogicalExpression f) throws Exception {
		return this.entails(f, new Unifier());
	}
	
	public Unifier entails(LogicalExpression f, Unifier u) throws Exception {
		if(f instanceof Contradiction) {
			return this.entails((Contradiction)f, u);
		} else if(f instanceof Tautology) {
			return this.entails((Tautology)f, u);
		} else if(f instanceof BeliefAtom) {
			return this.entails((BeliefAtom)f, u);
		} else if(f instanceof BeliefLiteral) {
			return this.entails((BeliefLiteral)f, u);
		} else if(f instanceof Conjunction) {
			return this.entails((Conjunction)f, u);
		} else if(f instanceof Disjunction) {
			return this.entails((Disjunction)f, u);
		} else if(f instanceof PlausibilityGE) {
			return this.entails((PlausibilityGE)f, u);
		} else if(f instanceof PlausibilityGT) {
			return this.entails((PlausibilityGT)f, u);
		} else if(f instanceof StrongNegation) {
			return this.entails((StrongNegation)f, u);
		} else if(f instanceof NegationAsFailure) {
			return this.entails((NegationAsFailure)f, u);
		} else if(f instanceof Equal) {
			return this.entails((Equal)f, u);
		} else if(f instanceof NotEqual) {
			return this.entails((NotEqual)f, u);
		} else {
			throw new UnsupportedOperationException("formula must be normalized");
		}
	}
	
	public Unifier entails(Contradiction f, Unifier u) throws Exception {
		return null;
	}
	
	public Unifier entails(Tautology f, Unifier u) throws Exception {
		return u;
	}
	
	public abstract Unifier entails(BeliefAtom f, Unifier u) throws Exception;
	
	public abstract Unifier entails(BeliefLiteral f, Unifier u) throws Exception;
	
	public abstract Unifier entails(Conjunction f, Unifier u) throws Exception;
	
	public abstract Unifier entails(Disjunction f, Unifier u) throws Exception;
	
	public abstract Unifier entails(PlausibilityGE f, Unifier u) throws Exception;
	
	public abstract Unifier entails(PlausibilityGT f, Unifier u) throws Exception;
	
	public abstract Unifier entails(StrongNegation f, Unifier u) throws Exception;
	
	public abstract Unifier entails(NegationAsFailure f, Unifier u) throws Exception;
	
	public abstract Unifier entails(Equal f, Unifier u) throws Exception;
	
	public abstract Unifier entails(NotEqual f, Unifier u) throws Exception;
	
}
