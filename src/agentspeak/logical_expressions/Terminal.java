package agentspeak.logical_expressions;

import uncertainty.Clause;
import data_structures.AdvancedSet;
import exceptions.UnsupportedOperationException;
import agentspeak.LogicalExpression;

public abstract class Terminal extends LogicalExpression {
	
	public abstract Terminal negation();
	
	@Override
	public boolean isClassical() {
		return true;
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
	public boolean inNNF() {
		return true;
	}
	
	@Override
	public LogicalExpression toCNF() {
		return this;
	}
	
	@Override
	public AdvancedSet<Terminal> getTerminals() throws UnsupportedOperationException {
		AdvancedSet<Terminal> terminals = new AdvancedSet<Terminal>();
		terminals.add(this);
		return terminals;
	}
	
	@Override
	public AdvancedSet<Clause> getClauses() throws UnsupportedOperationException {
		AdvancedSet<Clause> clauses = new AdvancedSet<Clause>();
		Clause clause = new Clause();
		clause.add(this);
		clauses.add(clause);
		return clauses;
	}
	
}
