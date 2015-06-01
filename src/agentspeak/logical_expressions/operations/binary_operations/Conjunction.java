package agentspeak.logical_expressions.operations.binary_operations;

import uncertainty.Clause;
import data_structures.AdvancedSet;
import agentspeak.LogicalExpression;
import agentspeak.Unifier;
import agentspeak.logical_expressions.operations.BinaryOperation;
import exceptions.UnsupportedOperationException;

public class Conjunction extends BinaryOperation {
	
	public static final String SYMBOL_CONJUNCTION = "&&";
	public static final int PRECEDENCE_CONJUNCTION = 4;
	
	public Conjunction(LogicalExpression l, LogicalExpression r) {
		super(l, r);
	}
	
	@Override
	public boolean isClassical() {
		return this.getLeft().isClassical() && this.getRight().isClassical();
	}
	
	@Override
	public boolean isConjunctive() {
		return this.getLeft().isConjunctive() && this.getRight().isConjunctive();
	}

	@Override
	public boolean isDisjunctive() {
		return false;
	}
	
	@Override
	public BinaryOperation toNNF(boolean propagateStrongNegation) throws UnsupportedOperationException {
		if(propagateStrongNegation) {
			return new Disjunction(this.getLeft().toNNF(true), this.getRight().toNNF(true));
		} else {
			return new Conjunction(this.getLeft().toNNF(false), this.getRight().toNNF(false));
		}
	}
	
	@Override
	public Conjunction substitute(Unifier u) throws UnsupportedOperationException {
		return new Conjunction(this.getLeft().substitute(u), this.getRight().substitute(u));
	}
	
	@Override
	public Conjunction toCNF() throws UnsupportedOperationException {
		return new Conjunction(this.getLeft().toCNF(), this.getRight().toCNF());
	}
	
	@Override
	public AdvancedSet<Clause> getClauses() throws UnsupportedOperationException {
		AdvancedSet<Clause> clauses = new AdvancedSet<Clause>();
		clauses.addAll(this.getLeft().getClauses());
		clauses.addAll(this.getRight().getClauses());
		return clauses;
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		return "(" + this.getLeft().toString() + " " + SYMBOL_CONJUNCTION + " " + this.getRight().toString() + ")";
	}
	
}
