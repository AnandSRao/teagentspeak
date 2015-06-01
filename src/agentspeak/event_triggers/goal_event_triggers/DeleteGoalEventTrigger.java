package agentspeak.event_triggers.goal_event_triggers;

import agentspeak.Goal;
import agentspeak.Unifier;
import agentspeak.event_triggers.GoalEventTrigger;
import agentspeak.event_triggers.belief_event_triggers.ReviseBeliefEventTrigger;

public class DeleteGoalEventTrigger extends GoalEventTrigger {
	
	public static final String SYMBOL_DELETE_GOAL = "-";
	
	public DeleteGoalEventTrigger(Goal g) {
		super(g);
	}
	
	@Override
	public Unifier unify(ReviseBeliefEventTrigger t) {
		return null;
	}

	@Override
	public Unifier unify(AddGoalEventTrigger t) {
		return null;
	}

	@Override
	public Unifier unify(DeleteGoalEventTrigger t) {
		return super.getGoal().getTerm().unify(t.getGoal().getTerm());
	}
	
	@Override
	public DeleteGoalEventTrigger substitute(Unifier u) {
		return new DeleteGoalEventTrigger(super.getGoal().substitute(u));
	}
	
	@Override
	public String toString() {
		return SYMBOL_DELETE_GOAL + super.getGoal();
	}
	
}
