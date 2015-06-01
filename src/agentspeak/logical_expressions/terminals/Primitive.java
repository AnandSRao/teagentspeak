package agentspeak.logical_expressions.terminals;

import data_structures.AdvancedSet;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.Terminal;

public abstract class Primitive extends Terminal {
	
	@Override
	public abstract Primitive negation();
	
	@Override
	public boolean isGround() {
		return true;
	}
	
	@Override
	public AdvancedSet<BeliefAtom> getBeliefAtoms() {
		return new AdvancedSet<BeliefAtom>();
	}
	
	@Override
	public AdvancedSet<BeliefLiteral> getBeliefLiterals() {
		return new AdvancedSet<BeliefLiteral>();
	}
	
	@Override
	public Primitive toNNF(boolean propagateStrongNegation) {
		if(propagateStrongNegation) {
			return this.negation();
		} else {
			return this;
		}
	}
	
}
