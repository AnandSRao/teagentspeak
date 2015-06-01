package agentspeak;

import java.util.LinkedList;
import java.util.Queue;

public class Interpreter {
	
	private BeliefBase beliefBase;
	private PlanLibrary planLibrary;
	private EventSet eventSet;
	private IntentionSet intentionSet;
	
	public Interpreter() {
		beliefBase = new BeliefBase();
		planLibrary = new PlanLibrary();
		eventSet = new EventSet();
		intentionSet = new IntentionSet();
	}
	
	public BeliefBase getBeliefBase() {
		return beliefBase;
	}
	
	public PlanLibrary getPlanLibrary() {
		return planLibrary;
	}
	
	public EventSet getEventSet() {
		return eventSet;
	}
	
	public void run() throws Exception {
		System.out.println("                     P = " + planLibrary);
		
		while(!(eventSet.isEmpty() && intentionSet.isEmpty())) {
//			try {
//			    Thread.sleep(1000);
//			} catch(InterruptedException ex) {
//			    Thread.currentThread().interrupt();
//			}
			
			System.out.println("                     B = " + beliefBase);
			System.out.println("                     E = " + eventSet);
			
			if(!eventSet.isEmpty()) {
				Event e = eventSet.selectEvent();
				if(e != null) {
					System.out.println("         event selected: " + e);
					IntendedMeans im = selectPlan(e);
					if(im != null) {
						System.out.println("intended means selected: " + im);
						intentionSet.adoptIntention(e, im);
					}
				}
			}
			
			System.out.println("                     I = " + intentionSet);
			if(!intentionSet.isEmpty()) {
				Intention i = intentionSet.selectIntention();
				if(i != null) {
					System.out.println("     intention selected: " + i);
					i.executeIntention(beliefBase, eventSet, intentionSet);
				}
			}
		}
	}
	
	public IntendedMeans selectPlan(Event e) throws Exception {
		Queue<IntendedMeans> relevant = selectRelevantPlans(e);
		if(!relevant.isEmpty()) {
			Queue<IntendedMeans> applicable = selectApplicablePlans(relevant);
			if(!applicable.isEmpty()) {
				return selectPlan(applicable);
			}
		}
		return null;
	}
	
	public Queue<IntendedMeans> selectRelevantPlans(Event e) throws Exception {
		Queue<IntendedMeans> relevant = new LinkedList<IntendedMeans>();
		for(Plan p : planLibrary) {
			Unifier u = e.getEventTrigger().unify(p.getEventTrigger());
			if(u != null) {
				relevant.add(new IntendedMeans(p, u));
			}
		}
		return relevant;
	}
	
	public Queue<IntendedMeans> selectApplicablePlans(Queue<IntendedMeans> r) throws Exception {
		Queue<IntendedMeans> copy = new LinkedList<IntendedMeans>(r);
		Queue<IntendedMeans> applicable = new LinkedList<IntendedMeans>();
		while(!copy.isEmpty()) {
			IntendedMeans up = copy.poll();
			LogicalExpression context = up.getPlan().getContext();
			Unifier u = beliefBase.entails(context.toNNF(), up.getUnifier());
			System.err.println("GUB \\models <" + context + ", " + up.getUnifier() + "> = " + u);
			if(u != null) {
				applicable.add(new IntendedMeans(up.getPlan(), u));
			}
		}
		return applicable;
	}
	
	public IntendedMeans selectPlan(Queue<IntendedMeans> a) {
		return a.peek();
	}
	
}
