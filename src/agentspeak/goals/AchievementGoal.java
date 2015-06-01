package agentspeak.goals;

import agentspeak.Goal;
import agentspeak.Term;
import agentspeak.Unifier;

public class AchievementGoal extends Goal {
	
	public static final String SYMBOL_ACHIEVEMENT_GOAL = "!";
	
	public AchievementGoal(Term t) {
		super(t);
	}
	
	@Override
	public AchievementGoal substitute(Unifier u) {
		return new AchievementGoal(super.getTerm().substitute(u));
	}
	
	@Override
	public String toString() {
		return SYMBOL_ACHIEVEMENT_GOAL + super.getTerm().toString();
	}
	
}