package agentspeak.events;

import agentspeak.Event;
import agentspeak.EventTrigger;
import agentspeak.Intention;

public class ExternalEvent extends Event {
	
	public ExternalEvent(EventTrigger e) {
		super(e, new Intention());
	}
	
}
