package evaluation;

import uncertainty.epistemic_states.CompactEpistemicState;
import uncertainty.epistemic_states.compact_epistemic_states.PossibilisticCompactEpistemicState;
import uncertainty.epistemic_states.compact_epistemic_states.ProbabilisticCompactEpistemicState;
import agentspeak.Interpreter;
import agentspeak.Parser;
import agentspeak.event_triggers.goal_event_triggers.AddGoalEventTrigger;
import agentspeak.events.ExternalEvent;
import agentspeak.logical_expressions.BeliefAtom;
import data_structures.AdvancedSet;

public class Scenario {
	
	public static void run() {
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			
			AdvancedSet<BeliefAtom> itemsDomain = new AdvancedSet<BeliefAtom>();
			itemsDomain.add(p.parseBelief("hasItems(location(1))"));
			itemsDomain.add(p.parseBelief("hasItems(location(2))"));
			itemsDomain.add(p.parseBelief("hasItems(location(3))"));
			i.getBeliefBase().addLocalEpistemicState(new PossibilisticCompactEpistemicState(itemsDomain));
			i.getBeliefBase().revise(p.parseLiteral("~hasItems(location(1))"), 0.9);
			i.getBeliefBase().revise(p.parseLiteral("hasItems(location(2))"), 0.8);
			i.getBeliefBase().revise(p.parseLiteral("hasItems(location(3))"), 0.7);
			
//			AdvancedSet<BeliefAtom> locationDomain1 = new AdvancedSet<BeliefAtom>();
//			locationDomain1.add(p.parseBelief("at(location(1))"));
//			i.getBeliefBase().addLocalEpistemicState(new ProbabilisticCompactEpistemicState(locationDomain1));
//			i.getBeliefBase().revise(p.parseLiteral("at(location(1))"), 0.9);
//			
//			AdvancedSet<BeliefAtom> locationDomain2 = new AdvancedSet<BeliefAtom>();
//			locationDomain2.add(p.parseBelief("at(location(2))"));
//			i.getBeliefBase().addLocalEpistemicState(new ProbabilisticCompactEpistemicState(locationDomain2));
//			i.getBeliefBase().revise(p.parseLiteral("at(location(2))"), 0.06);
//			
//			AdvancedSet<BeliefAtom> locationDomain3 = new AdvancedSet<BeliefAtom>();
//			locationDomain3.add(p.parseBelief("at(location(3))"));
//			i.getBeliefBase().addLocalEpistemicState(new ProbabilisticCompactEpistemicState(locationDomain3));
//			i.getBeliefBase().revise(p.parseLiteral("at(location(3))"), 0.03);
//			
//			AdvancedSet<BeliefAtom> locationDomain4 = new AdvancedSet<BeliefAtom>();
//			locationDomain4.add(p.parseBelief("at(location(4))"));
//			i.getBeliefBase().addLocalEpistemicState(new ProbabilisticCompactEpistemicState(locationDomain4));
//			i.getBeliefBase().revise(p.parseLiteral("at(location(4))"), 0.01);
			
			AdvancedSet<BeliefAtom> locationDomain = new AdvancedSet<BeliefAtom>();
			locationDomain.add(p.parseBelief("at(location(1))"));
			locationDomain.add(p.parseBelief("at(location(2))"));
			locationDomain.add(p.parseBelief("at(location(3))"));
			locationDomain.add(p.parseBelief("at(location(4))"));
			i.getBeliefBase().addLocalEpistemicState(new ProbabilisticCompactEpistemicState(locationDomain));
			i.getBeliefBase().revise(p.parseLiteral("at(location(1))"), 0.9);
			i.getBeliefBase().revise(p.parseLiteral("at(location(2))"), 0.06);
			i.getBeliefBase().revise(p.parseLiteral("at(location(3))"), 0.03);
			i.getBeliefBase().revise(p.parseLiteral("at(location(4))"), 0.01);
			
			AdvancedSet<BeliefAtom> batteryDomain = new AdvancedSet<BeliefAtom>();
			batteryDomain.add(p.parseBelief("sufficientBattery(location(1),location(2),location(4))"));
			batteryDomain.add(p.parseBelief("sufficientBattery(location(1),location(3),location(4))"));
			batteryDomain.add(p.parseBelief("sufficientBattery(location(2),location(1),location(4))"));
			batteryDomain.add(p.parseBelief("sufficientBattery(location(2),location(3),location(4))"));
			batteryDomain.add(p.parseBelief("sufficientBattery(location(3),location(1),location(4))"));
			batteryDomain.add(p.parseBelief("sufficientBattery(location(3),location(2),location(4))"));
			i.getBeliefBase().addLocalEpistemicState(new CompactEpistemicState(batteryDomain));
			i.getBeliefBase().revise(p.parseLiteral("sufficientBattery(location(1),location(2),location(4))"), 1);
			i.getBeliefBase().revise(p.parseLiteral("sufficientBattery(location(1),location(3),location(4))"), 1);
			i.getBeliefBase().revise(p.parseLiteral("sufficientBattery(location(2),location(1),location(4))"), 0);
			i.getBeliefBase().revise(p.parseLiteral("sufficientBattery(location(2),location(3),location(4))"), 0);
			i.getBeliefBase().revise(p.parseLiteral("sufficientBattery(location(3),location(1),location(4))"), 0);
			i.getBeliefBase().revise(p.parseLiteral("sufficientBattery(location(3),location(2),location(4))"), 0);
			
			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!completeMission"))));
			
			i.getPlanLibrary().add(p.parsePlan("+!completeMission : at(location(X)) >= at(location(Y)) & at(location(X)) >= at(location(Z)) & at(location(X)) >= at(location(4)) & (hasItems(location(Y)) | hasItems(location(Z))) & hasItems(location(Y)) >= hasItems(location(Z)) & sufficientBattery(location(X),location(Y),location(4)) & X \\== 4 & X \\== Y & X \\== Z & Y \\== Z <- !proceed(location(X), location(Y))"));
			i.getPlanLibrary().add(p.parsePlan("+!completeMission : at(location(X)) >= at(location(Y)) & at(location(X)) >= at(location(Z)) & at(location(X)) >= at(location(4)) & (hasItems(location(Y)) | hasItems(location(Z))) & hasItems(location(Y)) >= hasItems(location(Z)) & not sufficientBattery(location(X),location(Y),location(4)) & X \\== 4 & X \\== Y & X \\== Z & Y \\== Z <- broadcast(try(location(Y))); !proceed(location(X), location(4))"));
			i.getPlanLibrary().add(p.parsePlan("+!completeMission : at(location(X)) >= at(location(Y)) & at(location(X)) >= at(location(Z)) & at(location(X)) >= at(location(4)) & ~hasItems(location(Y)) & ~hasItems(location(Z)) & X \\== 4 & X \\== Y & X \\== Z & Y \\== Z <- !proceed(location(X), location(4))"));
			i.getPlanLibrary().add(p.parsePlan("+!completeMission : at(location(4)) >= at(location(X)) & at(location(4)) >= at(location(Y)) & at(location(4)) >= at(location(Z)) & X \\== 4 & Y \\== 4 & Z \\== 4 & X \\== Y & X \\== Z & Y \\== Z <- depositItems"));
			
			i.getPlanLibrary().add(p.parsePlan("+!proceed(location(X), location(Y)) : true <- collectItems; *(~hasItems(location(X)), 1); *(hasItems(location(X)), 0); !travel(location(X), location(Y)); !completeMission"));
			i.getPlanLibrary().add(p.parsePlan("+!travel(location(X), location(Y)) : true <- *(at(location(X)), 0.01); *(at(location(Y)), 0.99)"));
			
			i.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		run();
	}
	
}
