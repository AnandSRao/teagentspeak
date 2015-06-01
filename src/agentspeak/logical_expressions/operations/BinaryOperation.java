package agentspeak.logical_expressions.operations;

import data_structures.AdvancedSet;
import exceptions.UnsupportedOperationException;
import agentspeak.LogicalExpression;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.Operation;
import agentspeak.logical_expressions.Terminal;
import agentspeak.logical_expressions.terminals.BeliefLiteral;

public abstract class BinaryOperation extends Operation {
	
	private LogicalExpression left;
	private LogicalExpression right;
	
	public BinaryOperation(LogicalExpression l, LogicalExpression r) {
		left = l;
		right = r;
	}
	
	public LogicalExpression getLeft() {
		return left;
	}
	
	public LogicalExpression getRight() {
		return right;
	}
	
	@Override
	public boolean isGround() {
		return left.isGround() && right.isGround();
	}
	
	@Override
	public AdvancedSet<BeliefAtom> getBeliefAtoms() {
		AdvancedSet<BeliefAtom> atoms = left.getBeliefAtoms().copy();
		atoms.addAll(right.getBeliefAtoms());
		return atoms;
	}
	
	@Override
	public AdvancedSet<BeliefLiteral> getBeliefLiterals() throws UnsupportedOperationException {
		AdvancedSet<BeliefLiteral> literals = left.getBeliefLiterals().copy();
		literals.addAll(right.getBeliefLiterals());
		return literals;
	}
	
	@Override
	public boolean inNNF() {
		return this.getLeft().inNNF() && this.getRight().inNNF();
	}
	
	@Override
	public AdvancedSet<Terminal> getTerminals() throws UnsupportedOperationException {
		AdvancedSet<Terminal> terminals = left.getTerminals().copy();
		terminals.addAll(right.getTerminals());
		return terminals;
	}

}
