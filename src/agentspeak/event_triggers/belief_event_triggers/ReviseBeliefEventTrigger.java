package agentspeak.event_triggers.belief_event_triggers;

import agentspeak.Term;
import agentspeak.Unifier;
import agentspeak.actions.belief_actions.ReviseBeliefAction;
import agentspeak.event_triggers.BeliefEventTrigger;
import agentspeak.event_triggers.goal_event_triggers.AddGoalEventTrigger;
import agentspeak.event_triggers.goal_event_triggers.DeleteGoalEventTrigger;
import agentspeak.logical_expressions.terminals.BeliefLiteral;

public class ReviseBeliefEventTrigger extends BeliefEventTrigger {
	
	private Term weight;
	
	public ReviseBeliefEventTrigger(BeliefLiteral l, Term w) {
		super(l);
		weight = w;
	}

	public Term getWeight() {
		return weight;
	}
	
	@Override
	public Unifier unify(ReviseBeliefEventTrigger t) {
		Unifier ub = super.getBeliefLiteral().unify(t.getBeliefLiteral());
		if(ub != null) {
			Unifier uw = weight.substitute(ub).unify(t.getWeight().substitute(ub));
			if(uw != null) {
				ub.putAll(uw);
				return ub;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public Unifier unify(AddGoalEventTrigger t) {
		return null;
	}

	@Override
	public Unifier unify(DeleteGoalEventTrigger t) {
		return null;
	}
	
	@Override
	public ReviseBeliefEventTrigger substitute(Unifier u) {
		return new ReviseBeliefEventTrigger(super.getBeliefLiteral().substitute(u), weight.substitute(u));
	}
	
	@Override
	public String toString() {
		return ReviseBeliefAction.SYMBOL_REVISE_BELIEF + "(" + super.getBeliefLiteral() + "," + weight + ")";
	}
	
}
