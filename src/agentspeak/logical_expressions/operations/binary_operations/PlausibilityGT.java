package agentspeak.logical_expressions.operations.binary_operations;

import uncertainty.Clause;
import data_structures.AdvancedSet;
import agentspeak.LogicalExpression;
import agentspeak.Unifier;
import agentspeak.logical_expressions.operations.BinaryOperation;
import exceptions.UnsupportedOperationException;

public class PlausibilityGT extends BinaryOperation {
	
	public static final String SYMBOL_PLAUSIBILITY_GT = ">";
	public static final int PRECEDENCE_PLAUSIBILITY_GT = 1;
	
	public PlausibilityGT(LogicalExpression l, LogicalExpression r) throws UnsupportedOperationException {
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
			return new PlausibilityGE(this.getRight().toNNF(false), this.getLeft().toNNF(false));
		} else {
			return new PlausibilityGT(this.getLeft().toNNF(false), this.getRight().toNNF(false));
		}
	}
	
	@Override
	public PlausibilityGT toCNF() throws UnsupportedOperationException {
		return new PlausibilityGT(this.getLeft().toCNF(), this.getRight().toCNF());
	}
	
	@Override
	public AdvancedSet<Clause> getClauses() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("formula must be pared");
	}
	
	@Override
	public PlausibilityGT substitute(Unifier u) throws UnsupportedOperationException {
		return new PlausibilityGT(this.getLeft().substitute(u), this.getRight().substitute(u));
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		return "(" + this.getLeft().toString() + " " + SYMBOL_PLAUSIBILITY_GT + " " + this.getRight().toString() + ")";
	}

}
