package agentspeak.logical_expressions.terminals.belief_literals;

import agentspeak.Unifier;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.operations.unary_operations.StrongNegation;
import agentspeak.logical_expressions.terminals.BeliefLiteral;

public class NegativeLiteral extends BeliefLiteral {
	
	public NegativeLiteral(BeliefAtom b) {
		super(b);
	}
	
	@Override
	public PositiveLiteral negation() {
		return new PositiveLiteral(this.getBeliefAtom());
	}
	
	@Override
	public boolean isPositive() {
		return false;
	}
	
	@Override
	public Unifier unify(NegativeLiteral l) {
		return this.getBeliefAtom().getTerm().unify(l.getBeliefAtom().getTerm());
	}

	@Override
	public Unifier unify(PositiveLiteral l) {
		return null;
	}
	
	@Override
	public NegativeLiteral substitute(Unifier u) {
		return new NegativeLiteral(this.getBeliefAtom().substitute(u));
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		return StrongNegation.SYMBOL_STRONG_NEGATION + this.getBeliefAtom().toString();
//		return "[NegativeLiteral: " + this.getBeliefAtom().toString() + "]";
	}
	
}