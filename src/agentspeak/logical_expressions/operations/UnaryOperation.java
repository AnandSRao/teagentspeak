package agentspeak.logical_expressions.operations;

import data_structures.AdvancedSet;
import agentspeak.LogicalExpression;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.Operation;

public abstract class UnaryOperation extends Operation {
	
	private LogicalExpression child;
	
	public UnaryOperation(LogicalExpression c) {
		child = c;
	}
	
	public LogicalExpression getChild() {
		return child;
	}
	
	@Override
	public boolean isConjunctive() {
		return child.isConjunctive();
	}

	@Override
	public boolean isDisjunctive() {
		return child.isDisjunctive();
	}
	
	@Override
	public boolean isGround() {
		return child.isGround();
	}
	
	@Override
	public AdvancedSet<BeliefAtom> getBeliefAtoms() {
		return child.getBeliefAtoms();
	}
	
}
