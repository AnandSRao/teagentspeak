package agentspeak;

import java.util.List;

public class Plan {
	
	public static final String SYMBOL_CONTEXT = ":";
	public static final String SYMBOL_ACTIONS = "<-";
	public static final String SYMBOL_ACTION_SEPARATOR = ";";
	
	private EventTrigger trigger;
	private LogicalExpression context;
	private List<Action> actions;
	
	public Plan(EventTrigger e, LogicalExpression c, List<Action> a) {
		trigger = e;
		context = c;
		actions = a;
	}
	
	public EventTrigger getEventTrigger() {
		return trigger;
	}
	
	public LogicalExpression getContext() {
		return context;
	}
	
	public List<Action> getActions() {
		return actions;
	}
	
	@Override
	public String toString() {
		String output = trigger.toString() + " " + SYMBOL_CONTEXT + " ";
		output += context.toString();
		output += " " + SYMBOL_ACTIONS + " ";
		if(actions.isEmpty()) {
			output += "true";
		} else {
			String delim = "";
			for(Action a : actions) {
				output += delim + a.toString();
				delim = SYMBOL_ACTION_SEPARATOR + " ";
			}
		}
		return output;
	}
	
}
