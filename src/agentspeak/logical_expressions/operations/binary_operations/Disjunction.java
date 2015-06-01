package agentspeak.logical_expressions.operations.binary_operations;

import uncertainty.Clause;
import data_structures.AdvancedSet;
import agentspeak.LogicalExpression;
import agentspeak.Unifier;
import agentspeak.logical_expressions.operations.BinaryOperation;
import exceptions.UnsupportedOperationException;

public class Disjunction extends BinaryOperation {
	
	public static final String SYMBOL_DISJUNCTION = "||";
	public static final int PRECEDENCE_DISJUNCTION = 4;
	
	public Disjunction(LogicalExpression l, LogicalExpression r) {
		super(l, r);
	}
	
	@Override
	public boolean isClassical() {
		return this.getLeft().isClassical() && this.getRight().isClassical();
	}
	
	@Override
	public boolean isConjunctive() {
		return false;
	}
	
	@Override
	public boolean isDisjunctive() {
		return this.getLeft().isDisjunctive() && this.getRight().isDisjunctive();
	}
	
	@Override
	public BinaryOperation toNNF(boolean propagateStrongNegation) throws UnsupportedOperationException {
		if(propagateStrongNegation) {
			return new Conjunction(this.getLeft().toNNF(true), this.getRight().toNNF(true));
		} else {
			return new Disjunction(this.getLeft().toNNF(false), this.getRight().toNNF(false));
		}
	}
	
	@Override
	public BinaryOperation toCNF() throws UnsupportedOperationException {
		if(this.getLeft() instanceof Conjunction && this.getRight() instanceof Conjunction) {
			Conjunction left = (Conjunction)this.getLeft();
			Conjunction right = (Conjunction)this.getRight();
			return new Conjunction(
					new Conjunction(
							new Disjunction(
									left.getLeft().toCNF(), 
									right.getLeft().toCNF()
							), 
							new Disjunction(
									left.getLeft().toCNF(), 
									right.getRight().toCNF()
							)
					),
					new Conjunction(
							new Disjunction(
									left.getRight().toCNF(), 
									right.getLeft().toCNF()
							), 
							new Disjunction(
									left.getRight().toCNF(), 
									right.getRight().toCNF()
							)
					)
			).toCNF();
		} else if(this.getLeft() instanceof Conjunction && !(this.getRight() instanceof Conjunction)) {
			Conjunction left = (Conjunction)this.getLeft();
			return new Conjunction(
					new Disjunction(
							left.getLeft().toCNF(), 
							this.getRight().toCNF()
					), 
					new Disjunction(
							left.getRight().toCNF(), 
							this.getRight().toCNF()
					)
			).toCNF();
		} else if(!(this.getLeft() instanceof Conjunction) && this.getRight() instanceof Conjunction) {
			Conjunction right = (Conjunction)this.getRight();
			return new Conjunction(
					new Disjunction(
							this.getLeft().toCNF(), 
							right.getLeft().toCNF()
					), 
					new Disjunction(
							this.getLeft().toCNF(), 
							right.getRight().toCNF()
					)
			).toCNF();
		} else {
			return new Disjunction(
					this.getLeft().toCNF(), 
					this.getRight().toCNF()
			);
		}
	}
	
	@Override
	public AdvancedSet<Clause> getClauses() throws UnsupportedOperationException {
		AdvancedSet<Clause> clauses = new AdvancedSet<Clause>();
		Clause clause = new Clause();
		clause.addAll(this.getTerminals());
		clauses.add(clause);
		return clauses;
	}
	
	@Override
	public Disjunction substitute(Unifier u) throws UnsupportedOperationException {
		return new Disjunction(this.getLeft().substitute(u), this.getRight().substitute(u));
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		return "(" + this.getLeft().toString() + " " + SYMBOL_DISJUNCTION + " " + this.getRight().toString() + ")";
	}

}
