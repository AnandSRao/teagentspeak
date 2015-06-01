package agentspeak.logical_expressions.terminals.primitives;

import agentspeak.Unifier;
import agentspeak.logical_expressions.terminals.Primitive;

public class Tautology extends Primitive {
	
	public static final String SYMBOL_TAUTOLOGY = "true";
	
	private final boolean value = true;

	public Tautology() {
		
	}
	
	@Override
	public Contradiction negation() {
		return new Contradiction();
	}
	
	@Override
	public Tautology substitute(Unifier u) {
		return this;
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		return SYMBOL_TAUTOLOGY;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (value ? 1231 : 1237);
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
		Tautology other = (Tautology) obj;
		if (value != other.value)
			return false;
		return true;
	}
	
}
