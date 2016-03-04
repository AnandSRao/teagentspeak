package agentspeak.logical_expressions.operations.unary_operations;

import uncertainty.Clause;
import data_structures.AdvancedSet;
import exceptions.UnsupportedOperationException;
import agentspeak.LogicalExpression;
import agentspeak.Unifier;
import agentspeak.logical_expressions.Terminal;
import agentspeak.logical_expressions.operations.UnaryOperation;
import agentspeak.logical_expressions.terminals.BeliefLiteral;

public class StrongNegation extends UnaryOperation {
	
	public static final String SYMBOL_STRONG_NEGATION = "~";
	public static final int PRECEDENCE_STRONG_NEGATION = 3;
	
	public StrongNegation(LogicalExpression c) {
		super(c);
	}
	
	@Override
	public boolean isClassical() {
		return this.getChild().isClassical();
	}

	@Override
	public AdvancedSet<BeliefLiteral> getBeliefLiterals() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("literals only have meaning after normalization");
	}
	
	@Override
	public LogicalExpression toNNF(boolean propagateStrongNegation) throws UnsupportedOperationException {
		if(propagateStrongNegation) {
			return this.getChild().toNNF(false);
		} else {
			return this.getChild().toNNF(true);
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
		throw new UnsupportedOperationException("formula must be NNF");
	}
	
	@Override
	public StrongNegation substitute(Unifier u) throws UnsupportedOperationException {
		return new StrongNegation(this.getChild().substitute(u));
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		return SYMBOL_STRONG_NEGATION + this.getChild().toString();
//		return "[StrongNegation: " + this.getChild().toString() + "]";
	}
	
}
