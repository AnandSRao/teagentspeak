package agentspeak;

import agentspeak.Term;
import agentspeak.Unifier;

public abstract class Goal {
	
	private Term term;
	
	public Goal(Term t) {
		term = t;
	}
	
	public Term getTerm() {
		return term;
	}
	
	public abstract Goal substitute(Unifier u);
	
}
