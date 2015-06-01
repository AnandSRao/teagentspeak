package agentspeak.logical_expressions.operations.binary_operations;

import uncertainty.Clause;
import data_structures.AdvancedSet;
import agentspeak.LogicalExpression;
import agentspeak.Unifier;
import agentspeak.logical_expressions.operations.BinaryOperation;
import exceptions.UnsupportedOperationException;

public class PlausibilityGE extends BinaryOperation {
	
	public static final String SYMBOL_PLAUSIBILITY_GE = ">=";
	public static final int PRECEDENCE_PLAUSIBILITY_GE = 1;
	
	public PlausibilityGE(LogicalExpression l, LogicalExpression r) throws UnsupportedOperationException {
		super(l, r);
		if(!this.getLeft().isClassical() || !this.getRight().isClassical()) {
			throw new UnsupportedOperationException("operator can only be applied to classical formulae");
		}
	}
	
	@Override
	public boolean isClassical() {
		return false;
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
	public BinaryOperation toNNF(boolean propagateStrongNegation) throws UnsupportedOperationException {
		if(propagateStrongNegation) {
			return new PlausibilityGT(this.getRight().toNNF(false), this.getLeft().toNNF(false));
		} else {
			return new PlausibilityGE(this.getLeft().toNNF(false), this.getRight().toNNF(false));
		}
	}
	
	@Override
	public PlausibilityGE toCNF() throws UnsupportedOperationException {
		return new PlausibilityGE(this.getLeft().toCNF(), this.getRight().toCNF());
	}
	
	@Override
	public PlausibilityGE substitute(Unifier u) throws UnsupportedOperationException {
		return new PlausibilityGE(this.getLeft().substitute(u), this.getRight().substitute(u));
	}
	
	@Override
	public AdvancedSet<Clause> getClauses() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("formula must be pared");
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		return "(" + this.getLeft().toString() + " " + SYMBOL_PLAUSIBILITY_GE + " " + this.getRight().toString() + ")";
	}

}
