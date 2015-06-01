package agentspeak.logical_expressions;

import uncertainty.Clause;
import agentspeak.LogicalExpression;
import agentspeak.Term;
import agentspeak.Unifier;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.NegativeLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.PositiveLiteral;
import data_structures.AdvancedSet;
import exceptions.UnsupportedOperationException;

public class BeliefAtom extends LogicalExpression {
	
	private Term term;
	
	public BeliefAtom(Term t) {
		term = t;
	}
	
	public Term getTerm() {
		return term;
	}
	
	@Override
	public boolean isClassical() {
		return true;
	}

	@Override
	public boolean isConjunctive() {
		return true;
	}

	@Override
	public boolean isDisjunctive() {
		return true;
	}
	
	@Override
	public boolean isGround() {
		return term.isGround();
	}

	@Override
	public AdvancedSet<BeliefAtom> getBeliefAtoms() {
		return new AdvancedSet<BeliefAtom>(this);
	}

	@Override
	public AdvancedSet<BeliefLiteral> getBeliefLiterals() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("literals only have meaning after normalization");
	}

	@Override
	public BeliefLiteral toNNF(boolean propagateStrongNegation) {
		if(propagateStrongNegation) {
			return new NegativeLiteral(this);
		} else {
			return new PositiveLiteral(this);
		}
	}
	
	@Override
	public boolean inNNF() {
		return false;
	}
	
	@Override
	public LogicalExpression toCNF() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("formula must be in NNF");
	}
	
	@Override
	public AdvancedSet<Terminal> getTerminals() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("formula must be in NNF");
	}
	
	@Override
	public AdvancedSet<Clause> getClauses() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("formula must be in NNF");
	}
	
	@Override
	public BeliefAtom substitute(Unifier u) {
		return new BeliefAtom(term.substitute(u));
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		return this.getTerm().toString();
//		return "[Belief: " + this.getTerm().toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((term == null) ? 0 : term.hashCode());
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
		BeliefAtom other = (BeliefAtom) obj;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}
	
}
