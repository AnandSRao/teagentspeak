package agentspeak.logical_expressions;

import uncertainty.Clause;
import data_structures.AdvancedSet;
import exceptions.UnsupportedOperationException;
import agentspeak.LogicalExpression;
import agentspeak.Unifier;
import agentspeak.logical_expressions.terminals.BeliefLiteral;

public abstract class RelationalExpression extends LogicalExpression {

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
	public AdvancedSet<BeliefAtom> getBeliefAtoms() {
		return new AdvancedSet<BeliefAtom>();
	}

	@Override
	public AdvancedSet<BeliefLiteral> getBeliefLiterals() throws UnsupportedOperationException {
		return new AdvancedSet<BeliefLiteral>();
	}
	
	@Override
	public AdvancedSet<Terminal> getTerminals() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("formula must be pared");
	}
	
	@Override
	public AdvancedSet<Clause> getClauses() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("formula must be pared");
	}
	
	@Override
	public abstract RelationalExpression substitute(Unifier u) throws UnsupportedOperationException;
	
}
