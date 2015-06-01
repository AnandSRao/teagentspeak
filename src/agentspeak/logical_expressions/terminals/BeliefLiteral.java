package agentspeak.logical_expressions.terminals;

import data_structures.AdvancedSet;
import exceptions.UnsupportedOperationException;
import agentspeak.Unifier;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.Terminal;
import agentspeak.logical_expressions.terminals.belief_literals.NegativeLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.PositiveLiteral;

public abstract class BeliefLiteral extends Terminal {

	private BeliefAtom atom;
	
	public BeliefLiteral(BeliefAtom a) {
		atom = a;
	}
	
	public BeliefAtom getBeliefAtom() {
		return atom;
	}
	
	@Override
	public abstract BeliefLiteral negation();
	
	public abstract boolean isPositive();
	
	public Unifier unify(BeliefLiteral l) {
		if(l instanceof NegativeLiteral) {
			return this.unify((NegativeLiteral)l);
		} else if(l instanceof PositiveLiteral) {
			return this.unify((PositiveLiteral)l);
		} else {
			return null;
		}
	}
	public abstract Unifier unify(NegativeLiteral l);
	public abstract Unifier unify(PositiveLiteral l);
	
	@Override
	public boolean isGround() {
		return atom.isGround();
	}
	
	@Override
	public AdvancedSet<BeliefAtom> getBeliefAtoms() {
		return new AdvancedSet<BeliefAtom>(atom);
	}
	
	@Override
	public AdvancedSet<BeliefLiteral> getBeliefLiterals() {
		return new AdvancedSet<BeliefLiteral>(this);
	}
	
	@Override
	public BeliefLiteral toNNF(boolean propagateStrongNegation) throws UnsupportedOperationException {
		if(propagateStrongNegation) {
			return this.negation();
		} else {
			return this;
		}
	}
	
	@Override
	public abstract BeliefLiteral substitute(Unifier u);
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atom == null) ? 0 : atom.hashCode());
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
		BeliefLiteral other = (BeliefLiteral) obj;
		if (atom == null) {
			if (other.atom != null)
				return false;
		} else if (!atom.equals(other.atom))
			return false;
		return true;
	}
	
}
