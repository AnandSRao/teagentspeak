package agentspeak.actions.belief_actions;

import agentspeak.BeliefBase;
import agentspeak.EventSet;
import agentspeak.Intention;
import agentspeak.Term;
import agentspeak.Unifier;
import agentspeak.actions.BeliefAction;
import agentspeak.event_triggers.belief_event_triggers.ReviseBeliefEventTrigger;
import agentspeak.events.ExternalEvent;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import agentspeak.terms.constants.Number;

public class ReviseBeliefAction extends BeliefAction {
	
	public static final String SYMBOL_REVISE_BELIEF = "*";
	
	private Term weight;
	
	public ReviseBeliefAction(BeliefLiteral b, Term w) {
		super(b);
		weight = w;
	}

	@Override
	public String toString() {
		return SYMBOL_REVISE_BELIEF + "(" + super.getBeliefLiteral().toString() + "," + weight + ")";
	}
	
	@Override
	public boolean executeAction(Unifier u, Intention i, BeliefBase bb, EventSet es) throws Exception {
		BeliefLiteral l = this.getBeliefLiteral().substitute(u);
		Number w = (Number)weight.substitute(u);
		bb.revise(l, w.getDoubleValue());
		System.out.println("           belief revised: *(" + l + "," + w + ")");
		es.add(new ExternalEvent(new ReviseBeliefEventTrigger(l, weight)));
		return false;
	}
	
}
