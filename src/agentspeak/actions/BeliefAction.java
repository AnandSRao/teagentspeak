package agentspeak.actions;

import data_structures.AdvancedSet;
import agentspeak.Action;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import agentspeak.terms.Variable;

public abstract class BeliefAction extends Action {
	
	private BeliefLiteral belief;
	
	public BeliefAction(BeliefLiteral b) {
		belief = b;
	}
	
	public BeliefLiteral getBeliefLiteral() {
		return belief;
	}

	@Override
	public AdvancedSet<Variable> getVariables() {
		return belief.getBeliefAtom().getTerm().getVariables();
	}
	
}
