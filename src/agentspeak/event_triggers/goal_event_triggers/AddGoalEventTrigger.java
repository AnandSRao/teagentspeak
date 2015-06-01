package agentspeak.event_triggers.goal_event_triggers;

import agentspeak.Goal;
import agentspeak.Unifier;
import agentspeak.event_triggers.GoalEventTrigger;
import agentspeak.event_triggers.belief_event_triggers.ReviseBeliefEventTrigger;

public class AddGoalEventTrigger extends GoalEventTrigger {
	
	public static final String SYMBOL_ADD_GOAL = "+";
	
	public AddGoalEventTrigger(Goal g) {
		super(g);
	}
	
	@Override
	public Unifier unify(ReviseBeliefEventTrigger t) {
		return null;
	}

	@Override
	public Unifier unify(AddGoalEventTrigger t) {
		return super.getGoal().getTerm().unify(t.getGoal().getTerm());
	}

	@Override
	public Unifier unify(DeleteGoalEventTrigger t) {
		return null;
	}
	
	@Override
	public AddGoalEventTrigger substitute(Unifier u) {
		return new AddGoalEventTrigger(super.getGoal().substitute(u));
	}
	
	@Override
	public String toString() {
		return SYMBOL_ADD_GOAL + super.getGoal();
	}
	
}
