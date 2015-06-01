package agentspeak.actions.goal_actions;

import agentspeak.BeliefBase;
import agentspeak.EventSet;
import agentspeak.Intention;
import agentspeak.Unifier;
import agentspeak.actions.GoalAction;
import agentspeak.event_triggers.goal_event_triggers.AddGoalEventTrigger;
import agentspeak.events.InternalEvent;
import agentspeak.goals.TestGoal;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.terminals.belief_literals.PositiveLiteral;

public class TestGoalAction extends GoalAction {
	
	private TestGoal testGoal;
	
	public TestGoalAction(TestGoal tg) {
		testGoal = tg;
	}

	@Override
	public TestGoal getGoal() {
		return testGoal;
	}

	@Override
	public boolean executeAction(Unifier u, Intention i, BeliefBase bb, EventSet es) throws Exception {
		Unifier unifier = bb.entails(new PositiveLiteral(new BeliefAtom(testGoal.getTerm())), u);
		if(unifier == null) {
			/*
			 * Should the goal (partially) instantiated when generating a new event?
			 */
			TestGoal ground = testGoal.substitute(u);
			es.add(new InternalEvent(new AddGoalEventTrigger(ground), i));
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return testGoal.toString();
	}
	
}
