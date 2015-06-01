package agentspeak.event_triggers;

import agentspeak.EventTrigger;
import agentspeak.logical_expressions.terminals.BeliefLiteral;

public abstract class BeliefEventTrigger extends EventTrigger {
	
	private BeliefLiteral belief;
	
	public BeliefEventTrigger(BeliefLiteral l) {
		belief = l;
	}
	
	public BeliefLiteral getBeliefLiteral() {
		return belief;
	}
	
}
