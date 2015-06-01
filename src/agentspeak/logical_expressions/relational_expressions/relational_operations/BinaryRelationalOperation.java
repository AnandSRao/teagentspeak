package agentspeak.logical_expressions.relational_expressions.relational_operations;

import agentspeak.Term;
import agentspeak.logical_expressions.relational_expressions.RelationalOperation;

public abstract class BinaryRelationalOperation extends RelationalOperation {
	
	private Term left;
	private Term right;
	
	public BinaryRelationalOperation(Term l, Term r) {
		left = l;
		right = r;
	}
	
	public Term getLeft() {
		return left;
	}
	
	public Term getRight() {
		return right;
	}
	
	@Override
	public boolean isGround() {
		return left.isGround() && right.isGround();
	}
	
	@Override
	public boolean inNNF() {
		return true;
	}
	
}
