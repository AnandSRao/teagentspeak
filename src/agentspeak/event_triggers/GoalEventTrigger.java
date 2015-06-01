package agentspeak.event_triggers;

import agentspeak.EventTrigger;
import agentspeak.Goal;

public abstract class GoalEventTrigger extends EventTrigger {
	
	private Goal goal;
	
	public GoalEventTrigger(Goal g) {
		goal = g;
	}
	
	public Goal getGoal() {
		return goal;
	}
	
}
