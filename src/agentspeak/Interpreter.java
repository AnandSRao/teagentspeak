package agentspeak;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

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
		System.out.println("                       P = " + planLibrary);
		
		int counter = 0;
		Scanner s = new Scanner(System.in);
		while(!eventSet.isEmpty() || !intentionSet.isEmpty()) {
//			try {
//			    Thread.sleep(1000);
//			} catch(InterruptedException ex) {
//			    Thread.currentThread().interrupt();
//			}
			
			System.out.print("continue [y/N]? ");
			String input = "";
			while(!input.equals("y")) {
				input = s.nextLine();
			}
			
			counter++;
			System.out.println();
			System.out.println("reasoning cycle " + counter + "...");
			System.out.println();
			
			System.out.println("                       B = " + beliefBase);
			System.out.println("                       E = " + eventSet);
			System.out.println();
			
			if(!eventSet.isEmpty()) {
				Event e = eventSet.selectEvent();
				if(e != null) {
					System.out.println("           event selected: " + e);
					IntendedMeans im = selectPlan(e);
					if(im != null) {
						intentionSet.adoptIntention(e, im);
					}
				}
			}
			
			System.out.println();
			System.out.println("                       I = " + intentionSet);
			System.out.println();
			if(!intentionSet.isEmpty()) {
				Intention i = intentionSet.selectIntention();
				if(i != null) {
					System.out.println("       intention selected: " + i);
					i.executeIntention(beliefBase, eventSet, intentionSet);
				}
			}
		}
		s.close();
	}
	
	public IntendedMeans selectPlan(Event e) throws Exception {
		Queue<IntendedMeans> relevant = selectRelevantPlans(e);
		System.out.println("  relevant plans selected: " + relevant);
		if(!relevant.isEmpty()) {
			Queue<IntendedMeans> applicable = selectApplicablePlans(relevant);
			System.out.println("applicable plans selected: " + applicable);
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
			System.err.println("B \\models <" + context + ", " + up.getUnifier() + "> = " + u);
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
