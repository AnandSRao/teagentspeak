package agentspeak.logical_expressions.terminals.belief_literals;

import agentspeak.Unifier;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.terminals.BeliefLiteral;

public class PositiveLiteral extends BeliefLiteral {
	
	public PositiveLiteral(BeliefAtom b) {
		super(b);
	}
	
	@Override
	public NegativeLiteral negation() {
		return new NegativeLiteral(this.getBeliefAtom());
	}
	
	@Override
	public boolean isPositive() {
		return true;
	}
	
	@Override
	public Unifier unify(NegativeLiteral l) {
		return null;
	}

	@Override
	public Unifier unify(PositiveLiteral l) {
		return this.getBeliefAtom().getTerm().unify(l.getBeliefAtom().getTerm());
	}
	
	@Override
	public PositiveLiteral substitute(Unifier u) {
		return new PositiveLiteral(this.getBeliefAtom().substitute(u));
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		return this.getBeliefAtom().toString();
//		return "[PositiveLiteral: " + this.getBeliefAtom().toString() + "]";
	}
	
}