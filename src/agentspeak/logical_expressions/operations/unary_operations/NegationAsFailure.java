package agentspeak.logical_expressions.operations.unary_operations;

import uncertainty.Clause;
import data_structures.AdvancedSet;
import exceptions.UnsupportedOperationException;
import agentspeak.LogicalExpression;
import agentspeak.Unifier;
import agentspeak.logical_expressions.Terminal;
import agentspeak.logical_expressions.operations.UnaryOperation;
import agentspeak.logical_expressions.terminals.BeliefLiteral;

public class NegationAsFailure extends UnaryOperation {
	
	public static final String SYMBOL_NEGATION_AS_FAILURE = "not";
	public static final int PRECEDENCE_NEGATION_AS_FAILURE = 3;
	
	public NegationAsFailure(LogicalExpression c) throws UnsupportedOperationException {
		super(c);
		if(!this.getChild().isClassical()) {
			throw new UnsupportedOperationException("operator can only be applied to classical formulae");
		}
	}
	
	@Override
	public boolean isClassical() {
		return false;
	}

	@Override
	public AdvancedSet<BeliefLiteral> getBeliefLiterals() throws UnsupportedOperationException {
		return this.getChild().getBeliefLiterals();
	}
	
	@Override
	public UnaryOperation toNNF(boolean propagateStrongNegation) throws UnsupportedOperationException {
		if(propagateStrongNegation) {
			throw new UnsupportedOperationException("cannot apply strong negation to negation as failure");
		} else {
			return new NegationAsFailure(this.getChild().toNNF(false));
		}
	}
	
	@Override
	public boolean inNNF() {
		return this.getChild().inNNF();
	}
	
	@Override
	public LogicalExpression toCNF() throws UnsupportedOperationException {
		return new NegationAsFailure(this.getChild().toCNF());
	}
	
	@Override
	public AdvancedSet<Terminal> getTerminals() throws UnsupportedOperationException {
		return this.getChild().getTerminals();
	}
	
	@Override
	public AdvancedSet<Clause> getClauses() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("formula must be pared");
	}
	
	@Override
	public NegationAsFailure substitute(Unifier u) throws UnsupportedOperationException {
		return new NegationAsFailure(this.getChild().substitute(u));
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		return SYMBOL_NEGATION_AS_FAILURE + " " + this.getChild().toString();
//		return "[NAF: " + this.getChild().toString() + "]";
	}
	
}
