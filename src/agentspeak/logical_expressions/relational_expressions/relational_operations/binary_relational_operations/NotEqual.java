package agentspeak.logical_expressions.relational_expressions.relational_operations.binary_relational_operations;

import exceptions.UnsupportedOperationException;
import agentspeak.Term;
import agentspeak.Unifier;
import agentspeak.logical_expressions.relational_expressions.relational_operations.BinaryRelationalOperation;

public class NotEqual extends BinaryRelationalOperation {
	
	public static final String SYMBOL_NOT_EQUAL = "\\==";

	public NotEqual(Term l, Term r) {
		super(l, r);
	}
	
	@Override
	public BinaryRelationalOperation toNNF(boolean propagateStrongNegation) {
		if(propagateStrongNegation) {
			return new Equal(this.getLeft(), this.getRight());
		} else {
			return this;
		}
	}
	
	@Override
	public BinaryRelationalOperation toCNF() {
		return this;
	}

	@Override
	public NotEqual substitute(Unifier u) throws UnsupportedOperationException {
		return new NotEqual(this.getLeft().substitute(u), this.getRight().substitute(u));
	}
	
	@Override
	public String toString() {
		return this.getLeft() + " " + SYMBOL_NOT_EQUAL + " " + this.getRight();
	}

}
