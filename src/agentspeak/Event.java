package agentspeak;

public abstract class Event {
	
	public EventTrigger trigger;
	public Intention intention;
	
	public Event(EventTrigger e, Intention i) {
		trigger = e;
		intention = i;
	}
	
	public EventTrigger getEventTrigger() {
		return trigger;
	}
	
	public Intention getIntention() {
		return intention;
	}

	@Override
	public String toString() {
		return trigger.toString();
	}
	
}
