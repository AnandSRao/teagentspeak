package agentspeak;

import agentspeak.event_triggers.belief_event_triggers.ReviseBeliefEventTrigger;
import agentspeak.event_triggers.goal_event_triggers.AddGoalEventTrigger;
import agentspeak.event_triggers.goal_event_triggers.DeleteGoalEventTrigger;

public abstract class EventTrigger {
	
	public Unifier unify(EventTrigger t) {
		if(t instanceof ReviseBeliefEventTrigger) {
			return this.unify((ReviseBeliefEventTrigger)t);
		} else if(t instanceof AddGoalEventTrigger) {
			return this.unify((AddGoalEventTrigger)t);
		} else if(t instanceof DeleteGoalEventTrigger) {
			return this.unify((DeleteGoalEventTrigger)t);
		} else {
			return null;
		}
	}
	public abstract Unifier unify(ReviseBeliefEventTrigger t);
	public abstract Unifier unify(AddGoalEventTrigger t);
	public abstract Unifier unify(DeleteGoalEventTrigger t);
	
	public abstract EventTrigger substitute(Unifier u);
	
}
