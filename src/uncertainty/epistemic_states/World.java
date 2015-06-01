package uncertainty.epistemic_states;

import agentspeak.LogicalExpression;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.operations.binary_operations.Conjunction;
import agentspeak.logical_expressions.operations.binary_operations.Disjunction;
import agentspeak.logical_expressions.operations.unary_operations.StrongNegation;
import agentspeak.logical_expressions.terminals.belief_literals.NegativeLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.PositiveLiteral;
import agentspeak.logical_expressions.terminals.primitives.Contradiction;
import agentspeak.logical_expressions.terminals.primitives.Tautology;
import data_structures.AdvancedSet;

public class World {
	
	private AdvancedSet<BeliefAtom> trues;
	
	public World() {
		trues = new AdvancedSet<BeliefAtom>();
	}
	
	public World(BeliefAtom... atoms) {
		this();
		
		for(int i = 0; i < atoms.length; i++) {
			trues.add(atoms[i]);
		}
	}
	
	public AdvancedSet<BeliefAtom> getTrueAtoms() {
		return trues;
	}
	
	public void setTrue(BeliefAtom atom) {
		trues.add(atom);
	}
	
	public boolean isTrue(BeliefAtom atom) {
		return trues.contains(atom);
	}
	
	public boolean models(LogicalExpression f) throws Exception {
		if(f instanceof Contradiction) {
			return this.models((Contradiction)f);
		} else if(f instanceof Tautology) {
			return this.models((Tautology)f);
		} else if(f instanceof BeliefAtom) {
			return this.models((BeliefAtom)f);
		} else if(f instanceof PositiveLiteral) {
			return this.models((PositiveLiteral)f);
		} else if(f instanceof NegativeLiteral) {
			return this.models((NegativeLiteral)f);
		} else if(f instanceof Conjunction) {
			return this.models((Conjunction)f);
		} else if(f instanceof Disjunction) {
			return this.models((Disjunction)f);
		} else if(f instanceof StrongNegation) {
			return this.models((StrongNegation)f);
		} else {
			throw new UnsupportedOperationException("");
		}
	}
	
	public boolean models(Contradiction f) throws Exception {
		return false;
	}
	
	public boolean models(Tautology f) throws Exception {
		return true;
	}
	
	public boolean models(BeliefAtom f) throws Exception {
		return trues.contains(f);
	}
	
	public boolean models(PositiveLiteral f) throws Exception {
		return trues.contains(f.getBeliefAtom());
	}
	
	public boolean models(NegativeLiteral f) throws Exception {
		return !trues.contains(f.getBeliefAtom());
	}
	
	public boolean models(Conjunction f) throws Exception {
		return this.models(f.getLeft()) && this.models(f.getRight());
	}
	
	public boolean models(Disjunction f) throws Exception {
		return this.models(f.getLeft()) || this.models(f.getRight());
	}
	
	public boolean models(StrongNegation f) throws Exception {
		return !this.models(f.getChild());
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
        return trues.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trues == null) ? 0 : trues.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		World other = (World) obj;
		if (trues == null) {
			if (other.trues != null)
				return false;
		} else if (!trues.equals(other.trues))
			return false;
		return true;
	}
	
}
