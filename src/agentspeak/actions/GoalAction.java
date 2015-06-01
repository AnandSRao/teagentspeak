package agentspeak.actions;

import data_structures.AdvancedSet;
import agentspeak.Action;
import agentspeak.Goal;
import agentspeak.terms.Variable;

public abstract class GoalAction extends Action {
	
	public abstract Goal getGoal();
	
	@Override
	public AdvancedSet<Variable> getVariables() {
		return this.getGoal().getTerm().getVariables();
	}
	
}
