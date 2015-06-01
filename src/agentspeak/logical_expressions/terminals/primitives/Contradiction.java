package agentspeak.logical_expressions.terminals.primitives;

import agentspeak.Unifier;
import agentspeak.logical_expressions.terminals.Primitive;

public class Contradiction extends Primitive {
	
	public static final String SYMBOL_CONTRADICTION = "false";
	
	private final boolean value = false;

	public Contradiction() {
		
	}
	
	@Override
	public Tautology negation() {
		return new Tautology();
	}
	
	@Override
	public Contradiction substitute(Unifier u) {
		return this;
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		return SYMBOL_CONTRADICTION;
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
		Contradiction other = (Contradiction) obj;
		if (value != other.value)
			return false;
		return true;
	}
	
}
