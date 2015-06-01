package agentspeak.goals;

import agentspeak.Goal;
import agentspeak.Term;
import agentspeak.Unifier;

public class TestGoal extends Goal {
	
	public static final String SYMBOL_TEST_GOAL = "?";
	
	public TestGoal(Term t) {
		super(t);
	}
	
	@Override
	public TestGoal substitute(Unifier u) {
		return new TestGoal(super.getTerm().substitute(u));
	}
	
	@Override
	public String toString() {
		return SYMBOL_TEST_GOAL + super.getTerm().toString();
	}
	
}
