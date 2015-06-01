package agentspeak;

import uncertainty.Clause;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.Terminal;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import data_structures.AdvancedSet;
import exceptions.UnsupportedOperationException;

public abstract class LogicalExpression {
	
	public abstract boolean isClassical();
	
	public abstract boolean isConjunctive();
	
	public abstract boolean isDisjunctive();
	
	public abstract boolean isGround();
	
	public abstract AdvancedSet<BeliefAtom> getBeliefAtoms();
	
	public abstract AdvancedSet<BeliefLiteral> getBeliefLiterals() throws UnsupportedOperationException;
	
	public LogicalExpression toNNF() throws UnsupportedOperationException {
		return this.toNNF(false);
	}
	
	public abstract LogicalExpression toNNF(boolean propagateStrongNegation) throws UnsupportedOperationException;
	
	public abstract boolean inNNF();
	
	public abstract LogicalExpression toCNF() throws UnsupportedOperationException;
	
	public abstract AdvancedSet<Terminal> getTerminals() throws UnsupportedOperationException;
	
	public abstract AdvancedSet<Clause> getClauses() throws UnsupportedOperationException;
	
	public abstract LogicalExpression substitute(Unifier u) throws UnsupportedOperationException;
	
}
