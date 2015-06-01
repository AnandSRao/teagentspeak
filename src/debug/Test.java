package debug;

import java.util.LinkedList;
import java.util.List;

import uncertainty.epistemic_states.CompactEpistemicState;
import uncertainty.epistemic_states.SemanticEpistemicState;
import uncertainty.epistemic_states.World;
import uncertainty.epistemic_states.compact_epistemic_states.PossibilisticCompactEpistemicState;
import uncertainty.epistemic_states.compact_epistemic_states.ProbabilisticCompactEpistemicState;
import uncertainty.epistemic_states.semantic_epistemic_states.PossibilisticSemanticEpistemicState;
import uncertainty.epistemic_states.semantic_epistemic_states.ProbabilisticSemanticEpistemicState;
import uncertainty.global_uncertain_beliefs.CompactGlobalUncertainBelief;
import uncertainty.global_uncertain_beliefs.SemanticGlobalUncertainBelief;
import utilities.Utilities;
import data_structures.AdvancedSet;
import agentspeak.LogicalExpression;
import agentspeak.Interpreter;
import agentspeak.Parser;
import agentspeak.event_triggers.belief_event_triggers.ReviseBeliefEventTrigger;
import agentspeak.event_triggers.goal_event_triggers.AddGoalEventTrigger;
import agentspeak.events.ExternalEvent;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.terminals.belief_literals.NegativeLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.PositiveLiteral;

public class Test {
	
	public static void parseFormula() {
		try {
			List<String> formulae = new LinkedList<String>();
			
//			formulae.add("a");
//			formulae.add("belief(a)");
//			formulae.add("belief(X)");
//			formulae.add("belief(1)");
//			formulae.add("belief('hello_world')");
//			
//			formulae.add("~a");
//			formulae.add("~belief(a)");
//			formulae.add("~belief(X)");
//			formulae.add("~belief(1)");
//			formulae.add("~belief('hello_world')");
//			
//			formulae.add("a && b");
//			formulae.add("belief(a) && belief(b)");
//			formulae.add("belief(X) && belief(Y)");
//			formulae.add("belief(1) && belief(1)");
//			formulae.add("belief('hello_world') && belief('goodbye_world')");
//			
//			formulae.add("a || b");
//			formulae.add("belief(a) || belief(b)");
//			formulae.add("belief(X) || belief(Y)");
//			formulae.add("belief(1) || belief(1)");
//			formulae.add("belief('hello_world') || belief('goodbye_world')");
//			
//			formulae.add("a > b");
//			formulae.add("belief(a) > belief(b)");
//			formulae.add("belief(X) > belief(Y)");
//			formulae.add("belief(1) > belief(1)");
//			formulae.add("belief('hello_world') > belief('goodbye_world')");
//			
//			formulae.add("a >= b");
//			formulae.add("belief(a) >= belief(b)");
//			formulae.add("belief(X) >= belief(Y)");
//			formulae.add("belief(1) >= belief(1)");
//			formulae.add("belief('hello_world') >= belief('goodbye_world')");
//			
//			formulae.add("not a");
//			formulae.add("not belief(a)");
//			formulae.add("not belief(X)");
//			formulae.add("not belief(1)");
//			formulae.add("not belief('hello_world')");
//			
//			formulae.add("not ~a");
//			formulae.add("not ~belief(a)");
//			formulae.add("not ~belief(X)");
//			formulae.add("not ~belief(1)");
//			formulae.add("not ~belief('hello_world')");
//			
//			formulae.add("a && not b");
//			formulae.add("belief(a) && not belief(b)");
//			formulae.add("belief(X) && not belief(Y)");
//			formulae.add("belief(1) && not belief(1)");
//			formulae.add("belief('hello_world') && not belief('goodbye_world')");
//			
//			formulae.add("a || not b");
//			formulae.add("belief(a) || not belief(b)");
//			formulae.add("belief(X) || not belief(Y)");
//			formulae.add("belief(1) || not belief(1)");
//			formulae.add("belief('hello_world') || not belief('goodbye_world')");
//			
//			formulae.add("a > not b");
//			formulae.add("belief(a) > not belief(b)");
//			formulae.add("belief(X) > not belief(Y)");
//			formulae.add("belief(1) > not belief(1)");
//			formulae.add("belief('hello_world') > not belief('goodbye_world')");
//			
//			formulae.add("a >= not b");
//			formulae.add("belief(a) >= not belief(b)");
//			formulae.add("belief(X) >= not belief(Y)");
//			formulae.add("belief(1) >= not belief(1)");
//			formulae.add("belief('hello_world') >= not belief('goodbye_world')");
			
			formulae.add("belief(a)");
			formulae.add("~belief(a)");
			formulae.add("not belief(a)");
			formulae.add("not ~belief(a)");
			formulae.add("~(belief(a) && belief(b))");
			formulae.add("~(~belief(a) && ~belief(b))");
			formulae.add("~(belief(a) || belief(b))");
			formulae.add("~(~belief(a) || ~belief(b))");
			formulae.add("not(belief(a) && belief(b))");
			formulae.add("not(~belief(a) && ~belief(b))");
			formulae.add("not(belief(a) || belief(b))");
			formulae.add("not(~belief(a) || ~belief(b))");
			formulae.add("not(~(belief(a) && belief(b)))");
			formulae.add("not(~(~belief(a) && ~belief(b)))");
			formulae.add("not(~(belief(a) || belief(b)))");
			formulae.add("not(~(~belief(a) || ~belief(b)))");
			formulae.add("not(~(belief(a) && belief(b))) && not(~(~belief(a) || ~belief(b)))");
			formulae.add("not(~(belief(a) && v)) || not(~(~belief(a) || ~belief(b)))");
			
			formulae.add("belief(a) > belief(b)");
			formulae.add("belief(a) >= belief(b)");
			formulae.add("~(belief(a) > belief(b))");
			formulae.add("~(belief(a) >= belief(b))");
			
			Parser p = new Parser();
			for(String f : formulae) {
				System.out.println(f + " :=");
				LogicalExpression formula = p.parseFormula(f);
				System.out.println("\t\t" + formula);
				System.out.println("\t==>\t" + formula.toNNF(false));
				System.out.println("\t==>\t" + formula.toNNF());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void compactESes() {
		try {
			Parser p = new Parser();
			
			BeliefAtom a = p.parseBelief("belief(a)");
			BeliefAtom b = p.parseBelief("belief(b)");
			BeliefAtom c = p.parseBelief("belief(c)");
			BeliefAtom d = p.parseBelief("belief(d)");
			
			AdvancedSet<BeliefAtom> domaina = new AdvancedSet<BeliefAtom>(a, b);
			CompactEpistemicState cesa = new CompactEpistemicState(domaina);
//			cesa.revise(a, new Weight(5, 4));
//			cesa.revise(b, new Weight(-3, 0));
			
			System.err.println(cesa);
			
			AdvancedSet<BeliefAtom> domainb = new AdvancedSet<BeliefAtom>(c, d);
			CompactEpistemicState cesb = new CompactEpistemicState(domainb);
//			cesb.revise(c, new Weight(2, 2));
//			cesb.revise(d, new Weight(-1, 5));
			
			System.err.println(cesb);
			
			List<String> formulae = new LinkedList<String>();
			
//			formulae.add("belief(a)");
//			formulae.add("belief(b)");
//			formulae.add("~belief(a)");
//			formulae.add("~belief(b)");
//			formulae.add("belief(a) && belief(b)");
//			formulae.add("belief(a) && ~belief(b)");
//			formulae.add("~belief(a) && belief(b)");
//			formulae.add("~belief(a) && ~belief(b)");
//			formulae.add("belief(a) || belief(b)");
//			formulae.add("belief(a) || ~belief(b)");
//			formulae.add("~belief(a) || belief(b)");
//			formulae.add("~belief(a) || ~belief(b)");
//			formulae.add("belief(a) > belief(b)");
//			formulae.add("belief(a) > ~belief(b)");
//			formulae.add("~belief(a) > belief(b)");
//			formulae.add("~belief(a) > ~belief(b)");
//			formulae.add("belief(a) >= belief(b)");
//			formulae.add("belief(a) >= ~belief(b)");
//			formulae.add("~belief(a) >= belief(b)");
//			formulae.add("~belief(a) >= ~belief(b)");
			
			formulae.add("belief(c)");
			formulae.add("belief(d)");
			formulae.add("~belief(c)");
			formulae.add("~belief(d)");
			formulae.add("belief(c) && belief(d)");
			formulae.add("belief(c) && ~belief(d)");
			formulae.add("~belief(c) && belief(d)");
			formulae.add("~belief(c) && ~belief(d)");
			formulae.add("belief(c) || belief(d)");
			formulae.add("belief(c) || ~belief(d)");
			formulae.add("~belief(c) || belief(d)");
			formulae.add("~belief(c) || ~belief(d)");
			formulae.add("belief(c) > belief(d)");
			formulae.add("belief(c) > ~belief(d)");
			formulae.add("~belief(c) > belief(d)");
			formulae.add("~belief(c) > ~belief(d)");
			formulae.add("belief(c) >= belief(d)");
			formulae.add("belief(c) >= ~belief(d)");
			formulae.add("~belief(c) >= belief(d)");
			formulae.add("~belief(c) >= ~belief(d)");
			
			for(String f : formulae) {
				LogicalExpression formula = p.parseFormula(f);
				System.out.print("val(" + formula + ") = ");
				System.out.print(cesb.entails(formula));
				System.out.println();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void compactGUB() {
		try {
			Parser p = new Parser();
			
			BeliefAtom a = p.parseBelief("belief(a)");
			BeliefAtom b = p.parseBelief("belief(b)");
			BeliefAtom c = p.parseBelief("belief(c)");
			
			AdvancedSet<BeliefAtom> domainab = new AdvancedSet<BeliefAtom>(a, b);
			AdvancedSet<BeliefAtom> domainc = new AdvancedSet<BeliefAtom>(c);
			
			CompactGlobalUncertainBelief cgub = new CompactGlobalUncertainBelief();
			cgub.addLocalEpistemicState(new CompactEpistemicState(domainab));
			cgub.addLocalEpistemicState(new CompactEpistemicState(domainc));
			
			cgub.revise(new NegativeLiteral(c), 2);
			cgub.revise(new PositiveLiteral(a), 4);
			cgub.revise(new PositiveLiteral(b), -3);
			cgub.revise(new PositiveLiteral(a), 1);
			cgub.revise(new PositiveLiteral(c), 2);
			cgub.revise(new NegativeLiteral(a), 4);
			cgub.revise(new NegativeLiteral(b), 4);
			
			System.out.println(cgub);
			
//			List<String> formulae = new LinkedList<String>();
//			
//			formulae.add("belief(a)");
//			formulae.add("belief(b)");
//			formulae.add("~belief(a)");
//			formulae.add("~belief(b)");
//			formulae.add("belief(a) && belief(b)");
//			formulae.add("belief(a) && ~belief(b)");
//			formulae.add("~belief(a) && belief(b)");
//			formulae.add("~belief(a) && ~belief(b)");
//			formulae.add("belief(a) || belief(b)");
//			formulae.add("belief(a) || ~belief(b)");
//			formulae.add("~belief(a) || belief(b)");
//			formulae.add("~belief(a) || ~belief(b)");
//			formulae.add("belief(a) > belief(b)");
//			formulae.add("belief(a) > ~belief(b)");
//			formulae.add("~belief(a) > belief(b)");
//			formulae.add("~belief(a) > ~belief(b)");
//			formulae.add("belief(a) >= belief(b)");
//			formulae.add("belief(a) >= ~belief(b)");
//			formulae.add("~belief(a) >= belief(b)");
//			formulae.add("~belief(a) >= ~belief(b)");
//			
//			formulae.add("belief(c)");
//			formulae.add("~belief(c)");
//			formulae.add("belief(c) && belief(a)");
//			formulae.add("belief(c) && ~belief(a)");
//			formulae.add("belief(c) && belief(b)");
//			formulae.add("belief(c) && ~belief(b)");
//			formulae.add("~belief(c) && belief(a)");
//			formulae.add("~belief(c) && ~belief(a)");
//			formulae.add("~belief(c) && belief(b)");
//			formulae.add("~belief(c) && ~belief(b)");
//			formulae.add("belief(c) || belief(a)");
//			formulae.add("belief(c) || ~belief(a)");
//			formulae.add("belief(c) || belief(b)");
//			formulae.add("belief(c) || ~belief(b)");
//			formulae.add("~belief(c) || belief(a)");
//			formulae.add("~belief(c) || ~belief(a)");
//			formulae.add("~belief(c) || belief(b)");
//			formulae.add("~belief(c) || ~belief(b)");
//			formulae.add("belief(c) > belief(a)");
//			formulae.add("belief(c) > ~belief(a)");
//			formulae.add("belief(c) > belief(b)");
//			formulae.add("belief(c) > ~belief(b)");
//			formulae.add("~belief(c) > belief(a)");
//			formulae.add("~belief(c) > ~belief(a)");
//			formulae.add("~belief(c) > belief(b)");
//			formulae.add("~belief(c) > ~belief(b)");
//			formulae.add("belief(c) >= belief(a)");
//			formulae.add("belief(c) >= ~belief(a)");
//			formulae.add("belief(c) >= belief(b)");
//			formulae.add("belief(c) >= ~belief(b)");
//			formulae.add("~belief(c) >= belief(a)");
//			formulae.add("~belief(c) >= ~belief(a)");
//			formulae.add("~belief(c) >= belief(b)");
//			formulae.add("~belief(c) >= ~belief(b)");
//			
//			for(String f : formulae) {
//				Formula formula = p.parseFormula(f);
//				System.out.print("val(" + formula + ") = ");
//				System.out.print(formula.normalize().entailedBy(cgub));
//				System.out.println();
//			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void parsingPlans() {
		
		try {
			Parser p = new Parser();
			
			System.out.println(p.parsePlan("+!saveHuman:human(X)&&human(Y)<-?kill(X);?save(Y);hide(X);*(safe(Y),2)"));
			System.out.println(p.parsePlan("-!saveHuman:human(X)&&human(Y)<-?kill(X);!save(Y);hide(X);*(safe(Y),-2)"));
			System.out.println(p.parsePlan("+?saveHuman:human(X)&&human(Y)<-!kill(X);?save(Y);hide(X);*(~safe(Y),2)"));
			System.out.println(p.parsePlan("-?saveHuman:human(X)&&human(Y)<-!kill(X);!save(Y);hide(X);*(~safe(Y),-2)"));
			
			System.out.println(p.parsePlan("*(save(Y),2):human(X)&&human(Y)<-kill(X);save(Y);hide(X)"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void example() {
		
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			
//			AdvancedSet<BeliefAtom> domain = new AdvancedSet<BeliefAtom>();
//			domain.add(p.parseBelief("location(a)"));
//			domain.add(p.parseBelief("location(b)"));
//			domain.add(p.parseBelief("location(c)"));
//			domain.add(p.parseBelief("location(d)"));
//			domain.add(p.parseBelief("closest(a, b)"));
//			domain.add(p.parseBelief("closest(c, d)"));
//			domain.add(p.parseBelief("flight(b, c)"));
//			i.getBeliefBase().addLocalEpistemicState(new ProbabilisticCompactEpistemicState(domain));
//			
//			i.getBeliefBase().revise(p.parseLiteral("location(a)"), 0.55);
//			i.getBeliefBase().revise(p.parseLiteral("closest(a, b)"), 0.65);
//			i.getBeliefBase().revise(p.parseLiteral("closest(c, d)"), 0.75);
//			i.getBeliefBase().revise(p.parseLiteral("flight(b, c)"), 0.85);
			
			AdvancedSet<BeliefAtom> domain1 = new AdvancedSet<BeliefAtom>();
			domain1.add(p.parseBelief("location(a)"));
			domain1.add(p.parseBelief("location(b)"));
			domain1.add(p.parseBelief("location(c)"));
			domain1.add(p.parseBelief("location(d)"));
			i.getBeliefBase().addLocalEpistemicState(new ProbabilisticCompactEpistemicState(domain1));
			
			AdvancedSet<BeliefAtom> domain2 = new AdvancedSet<BeliefAtom>();
			domain2.add(p.parseBelief("closest(a, b)"));
			domain2.add(p.parseBelief("closest(c, d)"));
			i.getBeliefBase().addLocalEpistemicState(new PossibilisticCompactEpistemicState(domain2));
			
			AdvancedSet<BeliefAtom> domain3 = new AdvancedSet<BeliefAtom>();
			domain3.add(p.parseBelief("flight(b, c)"));
			i.getBeliefBase().addLocalEpistemicState(new ProbabilisticCompactEpistemicState(domain3));
			
			i.getBeliefBase().revise(p.parseLiteral("location(a)"), 0.75);
			
			i.getBeliefBase().revise(p.parseLiteral("closest(a, b)"), 0.75);
			i.getBeliefBase().revise(p.parseLiteral("closest(c, d)"), 0.75);
			
			i.getBeliefBase().revise(p.parseLiteral("flight(b, c)"), 1);
			
			i.getPlanLibrary().add(p.parsePlan("+!travel(a, d) : true <- !travel_taxi(a, X2); !travel_flight(X2, Y2); !travel_bus(Y2, d); print('arrived')"));
			i.getPlanLibrary().add(p.parsePlan("+!travel_taxi(X3, Y3) : location(X3) <- ?airport(Y3); taxi(X3, Y3); *(location(X3),0); *(location(Y3),1)"));
			i.getPlanLibrary().add(p.parsePlan("+?airport(X4) : location(Y4) && closest(Y4, X4) <- true"));
			i.getPlanLibrary().add(p.parsePlan("+!travel_flight(X5, Y5) : location(X5) && flight(X5, Y5) <- *(location(X5),0); *(location(Y5),1)"));
			i.getPlanLibrary().add(p.parsePlan("+!travel_bus(Y6, d) : location(Y6) <- *(location(Y6),-1); *(location(d),2)"));
			
			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!travel(a, d)"))));
			
			i.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void exampleGround() {
		
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			
			AdvancedSet<BeliefAtom> domain = new AdvancedSet<BeliefAtom>();
			domain.add(p.parseBelief("location(a)"));
			domain.add(p.parseBelief("location(b)"));
			domain.add(p.parseBelief("location(c)"));
			domain.add(p.parseBelief("location(d)"));
			domain.add(p.parseBelief("closest(a, b)"));
			domain.add(p.parseBelief("closest(c, d)"));
			domain.add(p.parseBelief("flight(b, c)"));
			i.getBeliefBase().addLocalEpistemicState(new CompactEpistemicState(domain));
			
			i.getBeliefBase().revise(p.parseLiteral("location(a)"), 2);
			i.getBeliefBase().revise(p.parseLiteral("closest(a, b)"), 2);
			i.getBeliefBase().revise(p.parseLiteral("closest(c, d)"), 2);
			i.getBeliefBase().revise(p.parseLiteral("flight(b, c)"), 2);
			
			i.getPlanLibrary().add(p.parsePlan("+!travel(a, d) : true <- !travel_taxi(a, b); !travel_flight(b, c); !travel_bus(c, d); print('arrived')"));
			i.getPlanLibrary().add(p.parsePlan("+!travel_taxi(a, b) : location(a) <- ?airport(b); taxi(a, b); *(location(a),-1); *(location(b),2)"));
			i.getPlanLibrary().add(p.parsePlan("+?airport(b) : location(a) && closest(a, b) <- true"));
			i.getPlanLibrary().add(p.parsePlan("+!travel_flight(b, c) : location(b) && flight(b, c) <- *(location(b),-1); *(location(c),2)"));
			i.getPlanLibrary().add(p.parsePlan("+!travel_bus(c, d) : location(c) <- *(location(c),-1); *(location(d),2)"));
			i.getPlanLibrary().add(p.parsePlan("*(location(X),Y) : true <- print(X, Y)"));
			
			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!travel(a, d)"))));
			
			i.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void context() {
		
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			
			AdvancedSet<BeliefAtom> domain = new AdvancedSet<BeliefAtom>();
			domain.add(p.parseBelief("location(a)"));
			domain.add(p.parseBelief("location(b)"));
			domain.add(p.parseBelief("location(c)"));
			i.getBeliefBase().addLocalEpistemicState(new CompactEpistemicState(domain));
			
			i.getBeliefBase().revise(p.parseLiteral("location(a)"), 1);
//			i.getBeliefBase().revise(p.parseLiteral("~location(a)"), 0);
			i.getBeliefBase().revise(p.parseLiteral("location(b)"), 2);
//			i.getBeliefBase().revise(p.parseLiteral("~location(b)"), 0);
			i.getBeliefBase().revise(p.parseLiteral("location(c)"), 3);
//			i.getBeliefBase().revise(p.parseLiteral("~location(c)"), 0);
			
			i.getPlanLibrary().add(p.parsePlan("+!literal : location(X) <- print('location', X, 'is_plausible')"));
			i.getPlanLibrary().add(p.parsePlan("+!conjunction : location(X) && location(Y) <- print('locations', X, 'and', Y, 'are_plausible')"));
			i.getPlanLibrary().add(p.parsePlan("+!disjunction : location(X) || location(Y) <- print('location', X, 'or', Y, 'is_plausible')"));
			i.getPlanLibrary().add(p.parsePlan("+!plausibility_ge : location(X) >= location(Y) <- print('location', X, 'is_at_least_as_plausible_as', Y)"));
			i.getPlanLibrary().add(p.parsePlan("+!plausibility_gt : location(X) > location(Y) <- print('location', X, 'is_more_plausible_than', Y)"));
			
			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!literal"))));
			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!conjunction"))));
			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!disjunction"))));
			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!plausibility_ge"))));
			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!plausibility_gt"))));
			
			i.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void revision() {
		
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			
			AdvancedSet<BeliefAtom> domain = new AdvancedSet<BeliefAtom>();
			domain.add(p.parseBelief("location(a)"));
			domain.add(p.parseBelief("location(b)"));
			domain.add(p.parseBelief("location(c)"));
			i.getBeliefBase().addLocalEpistemicState(new CompactEpistemicState(domain));
			
			i.getBeliefBase().revise(p.parseLiteral("location(a)"), 1);
			i.getBeliefBase().revise(p.parseLiteral("location(b)"), 2);
			i.getBeliefBase().revise(p.parseLiteral("location(c)"), 3);
			
			i.getPlanLibrary().add(p.parsePlan("+!start : true <- *(location(b), 1)"));
			i.getPlanLibrary().add(p.parsePlan("*(location(X), Y) : true <- true"));
			
			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!start"))));
			
			i.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void negationAsFailure() {
		
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			
			AdvancedSet<BeliefAtom> domain = new AdvancedSet<BeliefAtom>();
			domain.add(p.parseBelief("location(a)"));
			domain.add(p.parseBelief("location(b)"));
			domain.add(p.parseBelief("location(c)"));
			domain.add(p.parseBelief("location(d)"));
			i.getBeliefBase().addLocalEpistemicState(new CompactEpistemicState(domain));
			
			i.getBeliefBase().revise(p.parseLiteral("location(a)"), 1);
			i.getBeliefBase().revise(p.parseLiteral("location(b)"), -1);
			i.getBeliefBase().revise(p.parseLiteral("~location(c)"), 1);
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(d) <- print('done')")); // false
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(X) <- print('done')")); // {X=a}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(d) <- print('done')")); // false
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(X) <- print('done')")); // {X=c}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not location(b) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not location(c) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not location(d) <- print('done')")); // true
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not location(X) <- print('done')")); // {X=d}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not ~location(a) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not ~location(b) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not ~location(c) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not ~location(d) <- print('done')")); // true
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not ~location(X) <- print('done')")); // {X=d}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) && location(b) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) && location(c) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) && location(d) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) && location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) && location(c) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) && location(d) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) && location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) && location(b) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) && location(d) <- print('done')")); // false
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(X) && location(Y) <- print('done')")); // {X=a, Y=a}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) && ~location(b) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) && ~location(c) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) && ~location(d) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) && ~location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) && ~location(c) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) && ~location(d) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) && ~location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) && ~location(b) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) && ~location(d) <- print('done')")); // false
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(X) && ~location(Y) <- print('done')")); // {X=c, Y=b}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) || location(b) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) || location(c) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) || location(d) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) || location(a) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) || location(c) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) || location(d) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) || location(a) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) || location(b) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) || location(d) <- print('done')")); // false
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(X) || location(Y) <- print('done')")); // {X=d, Y=a}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) || ~location(b) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) || ~location(c) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) || ~location(d) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) || ~location(a) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) || ~location(c) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) || ~location(d) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) || ~location(a) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) || ~location(b) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) || ~location(d) <- print('done')")); // true
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(X) || ~location(Y) <- print('done')")); // {X=d, Y=b}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(a) && location(b)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(a) && location(c)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(a) && location(d)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(b) && location(a)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(b) && location(c)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(b) && location(d)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(c) && location(a)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(c) && location(b)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(c) && location(d)) <- print('done')")); // true
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(X) && location(Y)) <- print('done')")); // {X=d, Y=a}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(a) && ~location(b)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(a) && ~location(c)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(a) && ~location(d)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(b) && ~location(a)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(b) && ~location(c)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(b) && ~location(d)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(c) && ~location(a)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(c) && ~location(b)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(c) && ~location(d)) <- print('done')")); // true
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(X) && ~location(Y)) <- print('done')")); // {X=d, Y=a}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(a) || location(b)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(a) || location(c)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(a) || location(d)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(b) || location(a)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(b) || location(c)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(b) || location(d)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(c) || location(a)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(c) || location(b)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(c) || location(d)) <- print('done')")); // true
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (location(X) || location(Y)) <- print('done')")); // {X=d, Y=b}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(a) || ~location(b)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(a) || ~location(c)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(a) || ~location(d)) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(b) || ~location(a)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(b) || ~location(c)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(b) || ~location(d)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(c) || ~location(a)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(c) || ~location(b)) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(c) || ~location(d)) <- print('done')")); // false
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : not (~location(X) || ~location(Y)) <- print('done')")); // {X=d, Y=a}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) > location(b) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) > location(c) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) > location(d) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) > location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) > location(c) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) > location(d) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) > location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) > location(b) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) > location(d) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(d) > location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(d) > location(b) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(d) > location(c) <- print('done')")); // false
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(X) > location(Y) <- print('done')")); // {X=a, Y=b}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) >= location(b) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) >= location(c) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) >= location(d) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) >= location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) >= location(c) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) >= location(d) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) >= location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) >= location(b) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) >= location(d) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(d) >= location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(d) >= location(b) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(d) >= location(c) <- print('done')")); // true
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(X) > location(Y) <- print('done')")); // {X=a, Y=b}
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) >= ~location(a) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) >= location(a) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) >= ~location(b) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) >= location(b) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) >= ~location(c) <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) >= location(c) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(d) >= ~location(d) <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(d) >= location(d) <- print('done')")); // true
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(X) >= ~location(X) <- print('done')")); // {X=d}
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(X) >= location(X) <- print('done')")); // {X=d}
			
			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!start"))));
			
			i.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void relationalExpression() {
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			
			AdvancedSet<BeliefAtom> domain = new AdvancedSet<BeliefAtom>();
			domain.add(p.parseBelief("location(a)"));
			domain.add(p.parseBelief("location(b)"));
			domain.add(p.parseBelief("location(c)"));
			domain.add(p.parseBelief("location(d)"));
			i.getBeliefBase().addLocalEpistemicState(new CompactEpistemicState(domain));
			
			i.getBeliefBase().revise(p.parseLiteral("location(a)"), 1);
			i.getBeliefBase().revise(p.parseLiteral("location(b)"), -1);
			i.getBeliefBase().revise(p.parseLiteral("~location(c)"), 1);
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) && location(a) && a \\== a <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) && location(b) && a \\== b <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) && location(c) && a \\== c <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(a) && location(d) && a \\== d <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) && location(a) && b \\== a <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) && location(b) && b \\== b <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) && location(c) && b \\== c <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(b) && location(d) && b \\== d <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) && location(a) && c \\== a <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) && location(b) && c \\== b <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) && location(c) && c \\== c <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(c) && location(d) && c \\== d <- print('done')")); // false
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : location(X) && location(Y) && X \\== Y <- print('done')")); // null
			
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) && ~location(a) && a \\== a <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) && ~location(b) && a \\== b <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) && ~location(c) && a \\== c <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(a) && ~location(d) && a \\== d <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) && ~location(a) && b \\== a <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) && ~location(b) && b \\== b <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) && ~location(c) && b \\== c <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(b) && ~location(d) && b \\== d <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) && ~location(a) && c \\== a <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) && ~location(b) && c \\== b <- print('done')")); // true
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) && ~location(c) && c \\== c <- print('done')")); // false
//			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(c) && ~location(d) && c \\== d <- print('done')")); // false
			
			i.getPlanLibrary().add(p.parsePlan("+!start : ~location(X) && ~location(Y) && X \\== Y <- *(location(d),2); *(location(d),1); *(location(d),-3); print('done')")); // {X=c, Y=b}
			
			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!start"))));
			
			i.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void semanticEpistemicState() {
		try {
			Parser p = new Parser();
			
			BeliefAtom a = p.parseBelief("a");
			BeliefAtom b = p.parseBelief("b");
			BeliefAtom c = p.parseBelief("c");
			
			AdvancedSet<BeliefAtom> domainabc = new AdvancedSet<BeliefAtom>();
			domainabc.add(a);
			domainabc.add(b);
			domainabc.add(c);
			
			SemanticEpistemicState ses = new SemanticEpistemicState(domainabc);
			
			ses.revise(new World(a, b, c), Double.NEGATIVE_INFINITY);
			ses.revise(new World(a, b), Double.NEGATIVE_INFINITY);
			ses.revise(new World(a, c), 7);
			ses.revise(new World(a), 6);
			ses.revise(new World(b, c), 10);
			ses.revise(new World(b), -2);
			ses.revise(new World(c), 7);
			ses.revise(new World(), -2);
			
			List<String> formulae = new LinkedList<String>();
			
			formulae.add("a");
			formulae.add("~a");
			formulae.add("b");
			formulae.add("~b");
			formulae.add("c");
			formulae.add("~c");
			
			formulae.add("true");
			formulae.add("false");
			
			formulae.add("a && b");
			formulae.add("~a && ~b");
			formulae.add("~a && c");
			formulae.add("a && ~c");
			
			formulae.add("a || b");
			formulae.add("~a || ~b");
			formulae.add("~a || c");
			formulae.add("a || ~c");
			
			formulae.add("(a && b) > (~a && ~b)");
			formulae.add("(~a && ~b) > (a && b)");
			
			formulae.add("a && ~a");
			formulae.add("a || ~a");
			
			formulae.add("not a");
			formulae.add("not ~a");
			formulae.add("not b");
			formulae.add("not ~b");
			formulae.add("not c");
			formulae.add("not ~c");
			
			System.out.println("B = " + ses);
			for(String s : formulae) {
				LogicalExpression f = p.parseFormula(s);
				System.out.print("\\lambda(" + f + ") = ");
				System.out.print(ses.lambda(f));
				System.out.println(", B \\models " + f + " = " + ses.entails(f));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void probabilisticSemanticEpistemicState() {
		try {
			Parser p = new Parser();
			
			BeliefAtom a = p.parseBelief("a");
			BeliefAtom b = p.parseBelief("b");
			BeliefAtom c = p.parseBelief("c");
			
			AdvancedSet<BeliefAtom> domainabc = new AdvancedSet<BeliefAtom>();
			domainabc.add(a);
			domainabc.add(b);
			domainabc.add(c);
			
			ProbabilisticSemanticEpistemicState pses = new ProbabilisticSemanticEpistemicState(domainabc);
			
			pses.revise(new World(a, b, c), 0.119799);
			pses.revise(new World(a, b), 0.028101);
			pses.revise(new World(a, c), 0.115101);
			pses.revise(new World(a), 0.026999);
			pses.revise(new World(b, c), 0.293301);
			pses.revise(new World(b), 0.068799);
			pses.revise(new World(c), 0.281799);
			pses.revise(new World(), 0.066101);
			
			List<String> formulae = new LinkedList<String>();
			
			formulae.add("a");
			formulae.add("~a");
			formulae.add("b");
			formulae.add("~b");
			formulae.add("c");
			formulae.add("~c");
			
			formulae.add("true");
			formulae.add("false");
			
			formulae.add("a && b");
			formulae.add("~a && ~b");
			formulae.add("~a && c");
			formulae.add("a && ~c");
			
			formulae.add("a || b");
			formulae.add("~a || ~b");
			formulae.add("~a || c");
			formulae.add("a || ~c");
			
			formulae.add("(a && b) > (~a && ~b)");
			formulae.add("(~a && ~b) > (a && b)");
			
			formulae.add("a && ~a");
			formulae.add("a || ~a");
			
			formulae.add("not a");
			formulae.add("not ~a");
			formulae.add("not b");
			formulae.add("not ~b");
			formulae.add("not c");
			formulae.add("not ~c");
			
			System.out.println("B = " + pses);
			for(String s : formulae) {
				LogicalExpression f = p.parseFormula(s);
				System.out.print("P(" + f + ") = ");
				System.out.print(Utilities.format(pses.lambda(f)));
				System.out.println(", B \\models " + f + " = " + pses.entails(f));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void possibilisticSemanticEpistemicState() {
		try {
			Parser p = new Parser();
			
			BeliefAtom a = p.parseBelief("a");
			BeliefAtom b = p.parseBelief("b");
			BeliefAtom c = p.parseBelief("c");
			
			AdvancedSet<BeliefAtom> domainabc = new AdvancedSet<BeliefAtom>();
			domainabc.add(a);
			domainabc.add(b);
			domainabc.add(c);
			
			PossibilisticSemanticEpistemicState pses = new PossibilisticSemanticEpistemicState(domainabc);
			
//			pses.revise(new World(a, b, c), 0.1);
//			pses.revise(new World(a, b), 0.6);
//			pses.revise(new World(a, c), 0.7);
//			pses.revise(new World(a), 0.3);
//			pses.revise(new World(b, c), 1);
//			pses.revise(new World(b), 0);
//			pses.revise(new World(c), 0.9);
//			pses.revise(new World(), 0.5);
			
			pses.revise(new World(a, b, c), 0.3);
			pses.revise(new World(a, b), 0.3);
			pses.revise(new World(a, c), 0.3);
			pses.revise(new World(a), 0.3);
			pses.revise(new World(b, c), 0.8);
			pses.revise(new World(b), 0.6);
			pses.revise(new World(c), 0.8);
			pses.revise(new World(), 0.6);
			
			List<String> formulae = new LinkedList<String>();
			
			formulae.add("a");
			formulae.add("~a");
			formulae.add("b");
			formulae.add("~b");
			formulae.add("c");
			formulae.add("~c");
			
			formulae.add("true");
			formulae.add("false");
			
			formulae.add("a && b");
			formulae.add("~a && ~b");
			formulae.add("~a && c");
			formulae.add("a && ~c");
			
			formulae.add("a || b");
			formulae.add("~a || ~b");
			formulae.add("~a || c");
			formulae.add("a || ~c");
			
			formulae.add("(a && b) > (~a && ~b)");
			formulae.add("(~a && ~b) > (a && b)");
			
			formulae.add("a && ~a");
			formulae.add("a || ~a");
			
			formulae.add("not a");
			formulae.add("not ~a");
			formulae.add("not b");
			formulae.add("not ~b");
			formulae.add("not c");
			formulae.add("not ~c");
			
			System.out.println("B = " + pses);
			for(String s : formulae) {
				LogicalExpression f = p.parseFormula(s);
				System.out.print("N(" + f + ") = ");
				System.out.print(Utilities.format(pses.lambda(f)));
				System.out.println(", B \\models " + f + " = " + pses.entails(f));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void semanticGlobalUncertainBelief() {
		try {
			Parser p = new Parser();
			
			SemanticGlobalUncertainBelief sgub = new SemanticGlobalUncertainBelief();
			
			BeliefAtom a = p.parseBelief("a");
			BeliefAtom b = p.parseBelief("b");
			BeliefAtom c = p.parseBelief("c");
			
			AdvancedSet<BeliefAtom> domainabc = new AdvancedSet<BeliefAtom>();
			domainabc.add(a);
			domainabc.add(b);
			domainabc.add(c);
			
			sgub.addLocalEpistemicState(new ProbabilisticSemanticEpistemicState(domainabc));
			
			sgub.revise(new World(a, b, c), 0.119799);
			sgub.revise(new World(a, b), 0.028101);
			sgub.revise(new World(a, c), 0.115101);
			sgub.revise(new World(a), 0.026999);
			sgub.revise(new World(b, c), 0.293301);
			sgub.revise(new World(b), 0.068799);
			sgub.revise(new World(c), 0.281799);
			sgub.revise(new World(), 0.066101);
			
			List<String> formulae = new LinkedList<String>();
			
			formulae.add("a");
			formulae.add("~a");
			formulae.add("b");
			formulae.add("~b");
			formulae.add("c");
			formulae.add("~c");
			
			formulae.add("true");
			formulae.add("false");
			
			formulae.add("a && b");
			formulae.add("~a && ~b");
			formulae.add("~a && c");
			formulae.add("a && ~c");
			
			formulae.add("a || b");
			formulae.add("~a || ~b");
			formulae.add("~a || c");
			formulae.add("a || ~c");
			
			formulae.add("(a && b) > (~a && ~b)");
			formulae.add("(~a && ~b) > (a && b)");
			
			formulae.add("a && ~a");
			formulae.add("a || ~a");
			
			formulae.add("not a");
			formulae.add("not ~a");
			formulae.add("not b");
			formulae.add("not ~b");
			formulae.add("not c");
			formulae.add("not ~c");
			
			BeliefAtom d = p.parseBelief("d");
			BeliefAtom e = p.parseBelief("e");
			BeliefAtom f = p.parseBelief("f");
			
			AdvancedSet<BeliefAtom> domaindef = new AdvancedSet<BeliefAtom>();
			domaindef.add(d);
			domaindef.add(e);
			domaindef.add(f);
			
			sgub.addLocalEpistemicState(new PossibilisticSemanticEpistemicState(domaindef));
			
//			sgub.revise(new World(a, b, c), 0.1);
//			sgub.revise(new World(a, b), 0.6);
//			sgub.revise(new World(a, c), 0.7);
//			sgub.revise(new World(a), 0.3);
//			sgub.revise(new World(b, c), 1);
//			sgub.revise(new World(b), 0);
//			sgub.revise(new World(c), 0.9);
//			sgub.revise(new World(), 0.5);
			
			sgub.revise(new World(d, e, f), 0.3);
			sgub.revise(new World(d, e), 0.3);
			sgub.revise(new World(d, f), 0.3);
			sgub.revise(new World(d), 0.3);
			sgub.revise(new World(e, f), 0.8);
			sgub.revise(new World(e), 0.6);
			sgub.revise(new World(f), 0.8);
			sgub.revise(new World(), 0.6);
			
			formulae.add("d");
			formulae.add("~d");
			formulae.add("e");
			formulae.add("~e");
			formulae.add("f");
			formulae.add("~f");
			
			formulae.add("true");
			formulae.add("false");
			
			formulae.add("d && b");
			formulae.add("~d && ~b");
			formulae.add("~d && c");
			formulae.add("d && ~c");
			
			formulae.add("d || e");
			formulae.add("~d || ~e");
			formulae.add("~d || f");
			formulae.add("d || ~f");
			
			formulae.add("(d && e) > (~d && ~e)");
			formulae.add("(~d && ~e) > (d && e)");
			
			formulae.add("d && ~d");
			formulae.add("d || ~d");
			
			formulae.add("not d");
			formulae.add("not ~d");
			formulae.add("not e");
			formulae.add("not ~e");
			formulae.add("not f");
			formulae.add("not ~f");
			
			formulae.add("c && d");
			formulae.add("c && ~d");
			formulae.add("~c && d");
			formulae.add("~c && ~d");
			formulae.add("c || d");
			formulae.add("c || ~d");
			formulae.add("~c || d");
			formulae.add("~c || ~d");
			
			formulae.add("c >= d");
			formulae.add("c >= ~d");
			formulae.add("~c >= d");
			formulae.add("~c >= ~d");
			formulae.add("d >= c");
			formulae.add("d >= ~c");
			formulae.add("~d >= c");
			formulae.add("~d >= ~c");
			formulae.add("c > d");
			formulae.add("c > ~d");
			formulae.add("~c > d");
			formulae.add("~c > ~d");
			formulae.add("d > c");
			formulae.add("d > ~c");
			formulae.add("~d > c");
			formulae.add("~d > ~c");
			
			formulae.add("(a >= b) && (d >= e)");
			formulae.add("(a >= b) && (~d >= e)");
			formulae.add("(~a >= b) && (d >= e)");
			formulae.add("(~a >= b) && (~d >= e)");
			formulae.add("(a >= b) || (d >= e)");
			formulae.add("(a >= b) || (~d >= e)");
			formulae.add("(~a >= b) || (d >= e)");
			formulae.add("(~a >= b) || (~d >= e)");
			formulae.add("(a > b) && (d > e)");
			formulae.add("(a > b) && (~d > e)");
			formulae.add("(~a > b) && (d > e)");
			formulae.add("(~a > b) && (~d > e)");
			formulae.add("(a > b) || (d > e)");
			formulae.add("(a > b) || (~d > e)");
			formulae.add("(~a > b) || (d > e)");
			formulae.add("(~a > b) || (~d > e)");
			
			formulae.add("not (a >= b)");
			formulae.add("not (~a >= b)");
			formulae.add("not (a > b)");
			formulae.add("not (~a > b)");
			
			System.out.println("B = " + sgub);
			for(String s : formulae) {
				LogicalExpression formula = p.parseFormula(s);
				System.out.println("B \\models " + formula + " = " + sgub.entails(formula));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void compactEpistemicState() {
		try {
			Parser p = new Parser();
			
			AdvancedSet<BeliefAtom> domainabc = new AdvancedSet<BeliefAtom>();
			domainabc.add(p.parseBelief("a"));
			domainabc.add(p.parseBelief("b"));
			domainabc.add(p.parseBelief("c"));
			
			CompactEpistemicState ces = new CompactEpistemicState(domainabc);
			
			ces.revise(p.parseLiteral("~c"), 2);
			ces.revise(p.parseLiteral("a"), 4);
			ces.revise(p.parseLiteral("b"), -3);
			ces.revise(p.parseLiteral("a"), 1);
			ces.revise(p.parseLiteral("c"), 2);
			ces.revise(p.parseLiteral("~a"), 4);
			
			ces.revise(p.parseLiteral("~b"), 4);
			
			List<String> formulae = new LinkedList<String>();
			
			formulae.add("a");
			formulae.add("~a");
			formulae.add("b");
			formulae.add("~b");
			formulae.add("c");
			formulae.add("~c");
			
			formulae.add("true");
			formulae.add("false");
			
			formulae.add("a && b");
			formulae.add("~a && ~b");
			formulae.add("~a && c");
			formulae.add("a && ~c");
			
			formulae.add("a || b");
			formulae.add("~a || ~b");
			formulae.add("~a || c");
			formulae.add("a || ~c");
			
			formulae.add("(a && b) > (~a && ~b)");
			formulae.add("(~a && ~b) > (a && b)");
			
			formulae.add("a && ~a");
			formulae.add("a || ~a");
			
			formulae.add("not a");
			formulae.add("not ~a");
			formulae.add("not b");
			formulae.add("not ~b");
			formulae.add("not c");
			formulae.add("not ~c");
			
			System.out.println("B = " + ces);
			for(String s : formulae) {
				LogicalExpression f = p.parseFormula(s);
				System.out.print("N(" + f + ") = ");
				System.out.print(ces.lambda(f));
				System.out.print(", B \\models " + f + " = " + ces.entails(f));
				System.out.println();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void probabilisticCompactEpistemicState() {
		try {
			Parser p = new Parser();
			
			AdvancedSet<BeliefAtom> domainabc = new AdvancedSet<BeliefAtom>();
			domainabc.add(p.parseBelief("a"));
			domainabc.add(p.parseBelief("b"));
			domainabc.add(p.parseBelief("c"));
			
			ProbabilisticCompactEpistemicState pces = new ProbabilisticCompactEpistemicState(domainabc);
			
			pces.revise(p.parseLiteral("a"), 0.29);
			pces.revise(p.parseLiteral("b"), 0.51);
			pces.revise(p.parseLiteral("c"), 0.81);
			
			List<String> formulae = new LinkedList<String>();
			
			formulae.add("a");
			formulae.add("~a");
			formulae.add("b");
			formulae.add("~b");
			formulae.add("c");
			formulae.add("~c");
			
			formulae.add("true");
			formulae.add("false");
			
			formulae.add("a && b");
			formulae.add("~a && ~b");
			formulae.add("~a && c");
			formulae.add("a && ~c");
			
			formulae.add("a || b");
			formulae.add("~a || ~b");
			formulae.add("~a || c");
			formulae.add("a || ~c");
			
			formulae.add("(a && b) > (~a && ~b)");
			formulae.add("(~a && ~b) > (a && b)");
			
//			formulae.add("(a || b) > (~a || ~b)");
//			formulae.add("(~a || ~b) > (a || b)");
			
			formulae.add("a && ~a");
			formulae.add("a || ~a");
			
			formulae.add("a && false");
			formulae.add("a && true");
			formulae.add("a || false");
			formulae.add("a || true");
			
			formulae.add("true && true");
			formulae.add("true && false");
			formulae.add("false && true");
			formulae.add("false && false");
			formulae.add("true || true");
			formulae.add("true || false");
			formulae.add("false || true");
			formulae.add("false || false");
			
			formulae.add("(a || b) && (a || ~b) && (~a || b) && (~a || ~b)");
			
			formulae.add("not a");
			formulae.add("not ~a");
			formulae.add("not b");
			formulae.add("not ~b");
			formulae.add("not c");
			formulae.add("not ~c");
			
//			formulae.add("(a >= b) && (b >= c)");
//			formulae.add("(a >= b) && (~b >= c)");
//			formulae.add("(~a >= b) && (b >= c)");
//			formulae.add("(~a >= b) && (~b >= c)");
//			formulae.add("(a >= b) || (b >= c)");
//			formulae.add("(a >= b) || (~b >= c)");
//			formulae.add("(~a >= b) || (b >= c)");
//			formulae.add("(~a >= b) || (~b >= c)");
//			formulae.add("(a > b) && (b > c)");
//			formulae.add("(a > b) && (~b > c)");
//			formulae.add("(~a > b) && (b > c)");
//			formulae.add("(~a > b) && (~b > c)");
//			formulae.add("(a > b) || (b > c)");
//			formulae.add("(a > b) || (~b > c)");
//			formulae.add("(~a > b) || (b > c)");
//			formulae.add("(~a > b) || (~b > c)");
//			
//			formulae.add("not (a && b)");
//			formulae.add("not (~a && b)");
//			formulae.add("not (a || b)");
//			formulae.add("not (~a || b)");
			
//			formulae.add("not (a >= b)");
//			formulae.add("not (~a >= b)");
//			formulae.add("not (a > b)");
//			formulae.add("not (~a > b)");
			
//			formulae.add("not a && not b");
//			formulae.add("not ~a && not b");
//			formulae.add("not a || not b");
//			formulae.add("not ~a || not b");
			
//			formulae.add("(not a) >= (not b)");
//			formulae.add("(not a) >= (not ~b)");
//			formulae.add("(not ~a) >= (not b)");
//			formulae.add("(not ~a) >= (not ~b)");
//			formulae.add("(not a) > (not b)");
//			formulae.add("(not a) > (not ~b)");
//			formulae.add("(not ~a) > (not b)");
//			formulae.add("(not ~a) > (not ~b)");
			
//			formulae.add("(a >= b) >= (b >= c)");
//			formulae.add("(a >= b) >= (~b >= c)");
//			formulae.add("(~a >= b) >= (b >= c)");
//			formulae.add("(~a >= b) >= (~b >= c)");
//			formulae.add("(a > b) > (b > c)");
//			formulae.add("(a > b) > (~b > c)");
//			formulae.add("(~a > b) > (b > c)");
//			formulae.add("(~a > b) > (~b > c)");
			
			System.out.println("B = " + pces);
			for(String s : formulae) {
				LogicalExpression f = p.parseFormula(s);
				System.out.print("P(" + f + ") = ");
//				if(!(f instanceof Disjunction)) {
					System.out.print(Utilities.format(pces.lambda(f)));
//					if(!(f instanceof Conjunction)) {
//						System.out.print(", B \\models " + f + " = " + pces.entails(f));
//					} else {
//						System.out.print(", B \\models " + f + " = UNSUPPORTED");
//					}
//				} else {
//					System.out.print("UNSUPPORTED, B \\models " + f + " = UNSUPPORTED");
//				}
				System.out.println();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void possibilisticCompactEpistemicState() {
		try {
			Parser p = new Parser();
			
			AdvancedSet<BeliefAtom> domainabc = new AdvancedSet<BeliefAtom>();
			domainabc.add(p.parseBelief("a"));
			domainabc.add(p.parseBelief("b"));
			domainabc.add(p.parseBelief("c"));
			
			PossibilisticCompactEpistemicState pces = new PossibilisticCompactEpistemicState(domainabc);
			
			pces.revise(p.parseLiteral("a"), 0.2);
			pces.revise(p.parseLiteral("~a"), 0.7);
			pces.revise(p.parseLiteral("b"), 0.1);
			pces.revise(p.parseLiteral("c"), 0.4);
			
			List<String> formulae = new LinkedList<String>();
			
			formulae.add("a");
			formulae.add("~a");
			formulae.add("b");
			formulae.add("~b");
			formulae.add("c");
			formulae.add("~c");
			
			formulae.add("true");
			formulae.add("false");
			
//			formulae.add("a && b");
//			formulae.add("~a && ~b");
//			formulae.add("~a && c");
//			formulae.add("a && ~c");
//			
//			formulae.add("a || b");
//			formulae.add("~a || ~b");
//			formulae.add("~a || c");
//			formulae.add("a || ~c");
			
			formulae.add("(a && b) > (~a && ~b)");
			formulae.add("(~a && ~b) > (a && b)");
			
//			formulae.add("(a || b) > (~a || ~b)");
//			formulae.add("(~a || ~b) > (a || b)");
//			
//			formulae.add("a && ~a");
//			formulae.add("a || ~a");
			
			formulae.add("not a");
			formulae.add("not ~a");
			formulae.add("not b");
			formulae.add("not ~b");
			formulae.add("not c");
			formulae.add("not ~c");
			
//			formulae.add("(a >= b) && (b >= c)");
//			formulae.add("(a >= b) && (~b >= c)");
//			formulae.add("(~a >= b) && (b >= c)");
//			formulae.add("(~a >= b) && (~b >= c)");
//			formulae.add("(a >= b) || (b >= c)");
//			formulae.add("(a >= b) || (~b >= c)");
//			formulae.add("(~a >= b) || (b >= c)");
//			formulae.add("(~a >= b) || (~b >= c)");
//			formulae.add("(a > b) && (b > c)");
//			formulae.add("(a > b) && (~b > c)");
//			formulae.add("(~a > b) && (b > c)");
//			formulae.add("(~a > b) && (~b > c)");
//			formulae.add("(a > b) || (b > c)");
//			formulae.add("(a > b) || (~b > c)");
//			formulae.add("(~a > b) || (b > c)");
//			formulae.add("(~a > b) || (~b > c)");
//			
//			formulae.add("not (a && b)");
//			formulae.add("not (~a && b)");
//			formulae.add("not (a || b)");
//			formulae.add("not (~a || b)");
			
//			formulae.add("not (a >= b)");
//			formulae.add("not (~a >= b)");
//			formulae.add("not (a > b)");
//			formulae.add("not (~a > b)");
			
//			formulae.add("not a && not b");
//			formulae.add("not ~a && not b");
//			formulae.add("not a || not b");
//			formulae.add("not ~a || not b");
			
//			formulae.add("(not a) >= (not b)");
//			formulae.add("(not a) >= (not ~b)");
//			formulae.add("(not ~a) >= (not b)");
//			formulae.add("(not ~a) >= (not ~b)");
//			formulae.add("(not a) > (not b)");
//			formulae.add("(not a) > (not ~b)");
//			formulae.add("(not ~a) > (not b)");
//			formulae.add("(not ~a) > (not ~b)");
			
//			formulae.add("(a >= b) >= (b >= c)");
//			formulae.add("(a >= b) >= (~b >= c)");
//			formulae.add("(~a >= b) >= (b >= c)");
//			formulae.add("(~a >= b) >= (~b >= c)");
//			formulae.add("(a > b) > (b > c)");
//			formulae.add("(a > b) > (~b > c)");
//			formulae.add("(~a > b) > (b > c)");
//			formulae.add("(~a > b) > (~b > c)");
			
			System.out.println("B = " + pces);
			for(String s : formulae) {
				LogicalExpression f = p.parseFormula(s);
				System.out.print("N(" + f + ") = ");
//				if(!(f instanceof Disjunction)) {
					System.out.print(pces.lambda(f));
//					if(!(f instanceof Conjunction)) {
//						System.out.print(", B \\models " + f + " = " + pces.entails(f));
//					} else {
//						System.out.print(", B \\models " + f + " = UNSUPPORTED");
//					}
//				} else {
//					System.out.print("UNSUPPORTED, B \\models " + f + " = UNSUPPORTED");
//				}
				System.out.println();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void compactGlobalUncertainBelief() {
		try {
			Parser p = new Parser();
			
			CompactGlobalUncertainBelief cgub = new CompactGlobalUncertainBelief();
			
			BeliefAtom a = p.parseBelief("a");
			BeliefAtom b = p.parseBelief("b");
			BeliefAtom c = p.parseBelief("c");
			
			AdvancedSet<BeliefAtom> domainabc = new AdvancedSet<BeliefAtom>();
			domainabc.add(a);
			domainabc.add(b);
			domainabc.add(c);
			
			cgub.addLocalEpistemicState(new ProbabilisticCompactEpistemicState(domainabc));
			
			cgub.revise(p.parseLiteral("a"), 0.29);
			cgub.revise(p.parseLiteral("b"), 0.51);
			cgub.revise(p.parseLiteral("c"), 0.81);
			
			List<String> formulae = new LinkedList<String>();
			
			formulae.add("a");
			formulae.add("~a");
			formulae.add("b");
			formulae.add("~b");
			formulae.add("c");
			formulae.add("~c");
			
			formulae.add("true");
			formulae.add("false");
			
			formulae.add("a && b");
			formulae.add("~a && ~b");
			formulae.add("~a && c");
			formulae.add("a && ~c");
			
			formulae.add("a || b");
			formulae.add("~a || ~b");
			formulae.add("~a || c");
			formulae.add("a || ~c");
			
			formulae.add("(a && b) > (~a && ~b)");
			formulae.add("(~a && ~b) > (a && b)");
			
			formulae.add("a && ~a");
			formulae.add("a || ~a");
			
			formulae.add("not a");
			formulae.add("not ~a");
			formulae.add("not b");
			formulae.add("not ~b");
			formulae.add("not c");
			formulae.add("not ~c");
			
			BeliefAtom d = p.parseBelief("d");
			BeliefAtom e = p.parseBelief("e");
			BeliefAtom f = p.parseBelief("f");
			
			AdvancedSet<BeliefAtom> domaindef = new AdvancedSet<BeliefAtom>();
			domaindef.add(d);
			domaindef.add(e);
			domaindef.add(f);
			
			cgub.addLocalEpistemicState(new PossibilisticCompactEpistemicState(domaindef));
			
			cgub.revise(p.parseLiteral("d"), 0.2);
			cgub.revise(p.parseLiteral("~d"), 0.7);
			cgub.revise(p.parseLiteral("e"), 0.1);
			cgub.revise(p.parseLiteral("f"), 0.4);
			
			formulae.add("d");
			formulae.add("~d");
			formulae.add("e");
			formulae.add("~e");
			formulae.add("f");
			formulae.add("~f");
			
			formulae.add("true");
			formulae.add("false");
			
//			formulae.add("d && b");
//			formulae.add("~d && ~b");
//			formulae.add("~d && c");
//			formulae.add("d && ~c");
//			
//			formulae.add("d || e");
//			formulae.add("~d || ~e");
//			formulae.add("~d || f");
//			formulae.add("d || ~f");
			
			formulae.add("(d && e) > (~d && ~e)");
			formulae.add("(~d && ~e) > (d && e)");
			
//			formulae.add("d && ~d");
//			formulae.add("d || ~d");
			
			formulae.add("not d");
			formulae.add("not ~d");
			formulae.add("not e");
			formulae.add("not ~e");
			formulae.add("not f");
			formulae.add("not ~f");
			
			formulae.add("c && d");
			formulae.add("c && ~d");
			formulae.add("~c && d");
			formulae.add("~c && ~d");
//			formulae.add("c || d");
//			formulae.add("c || ~d");
//			formulae.add("~c || d");
//			formulae.add("~c || ~d");
			
			formulae.add("c >= d");
			formulae.add("c >= ~d");
			formulae.add("~c >= d");
			formulae.add("~c >= ~d");
			formulae.add("d >= c");
			formulae.add("d >= ~c");
			formulae.add("~d >= c");
			formulae.add("~d >= ~c");
			formulae.add("c > d");
			formulae.add("c > ~d");
			formulae.add("~c > d");
			formulae.add("~c > ~d");
			formulae.add("d > c");
			formulae.add("d > ~c");
			formulae.add("~d > c");
			formulae.add("~d > ~c");
			
			formulae.add("(a >= b) && (d >= e)");
			formulae.add("(a >= b) && (~d >= e)");
			formulae.add("(~a >= b) && (d >= e)");
			formulae.add("(~a >= b) && (~d >= e)");
//			formulae.add("(a >= b) || (d >= e)");
//			formulae.add("(a >= b) || (~d >= e)");
//			formulae.add("(~a >= b) || (d >= e)");
//			formulae.add("(~a >= b) || (~d >= e)");
			formulae.add("(a > b) && (d > e)");
			formulae.add("(a > b) && (~d > e)");
			formulae.add("(~a > b) && (d > e)");
			formulae.add("(~a > b) && (~d > e)");
//			formulae.add("(a > b) || (d > e)");
//			formulae.add("(a > b) || (~d > e)");
//			formulae.add("(~a > b) || (d > e)");
//			formulae.add("(~a > b) || (~d > e)");
			
//			formulae.add("not (a >= b)");
//			formulae.add("not (~a >= b)");
//			formulae.add("not (a > b)");
//			formulae.add("not (~a > b)");
			
			System.out.println("B = " + cgub);
			for(String s : formulae) {
				LogicalExpression formula = p.parseFormula(s);
				System.out.println("B \\models " + formula + " = " + cgub.entails(formula));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cnf() {
		try {
			Parser p = new Parser();
			
			List<String> formulae = new LinkedList<String>();
			
			formulae.add("a1");
			
			formulae.add("a1 || b1");
			formulae.add("a1 && b1");
			
			formulae.add("(a1 || b1) && a2");
			formulae.add("(a1 && b1) || a2");
			formulae.add("a1 && (a2 || b2)");
			formulae.add("a1 || (a2 && b2)");
			
			formulae.add("(a1 || b1 || c1) && a2");
			formulae.add("(a1 && b1 && c1) || a2");
			formulae.add("a1 && (a2 || b2 || c2)");
			formulae.add("a1 || (a2 && b2 && c2)");
			
			formulae.add("(a1 || b1) && (a2 || b2)");
			formulae.add("(a1 && b1) || (a2 && b2)");
			
			formulae.add("(a1 || b1 || c1) && (a2 || b2 || c2)");
			formulae.add("(a1 && b1 && c1) || (a2 && b2 && c2)");
			
			formulae.add("(a1 || b1) && (a2 || b2) && (a3 || b3)");
			formulae.add("(a1 && b1) || (a2 && b2) || (a3 && b3)");
			
			formulae.add("(a1 || b1 || c1) && (a2 || b2 || c2) && (a3 || b3 || c3)");
			formulae.add("(a1 && b1 && c1) || (a2 && b2 && c2) || (a3 && b3 && c3)");
			
			formulae.add("a1 || a2 || a3 || a4");
			formulae.add("a1 && a2 && a3 && a4");
			
			formulae.add("(((a1 || b1) && (a2 || b2)) || ((a3 || b3) && (a4 || b4))) && (((a5 || b5) && (a6 || b6)) || ((a7 || b7) && (a8 || b8)))");
			formulae.add("(((a1 && b1) || (a2 && b2)) && ((a3 && b3) || (a4 && b4))) || (((a5 && b5) || (a6 && b6)) && ((a7 && b7) || (a8 && b8)))");
			
			formulae.add("(true || false) && (true || false)");
			formulae.add("(true && false) || (true && false)");
			
			for(String f : formulae) {
				LogicalExpression formula = p.parseFormula(f);
				System.out.println(formula + " := " + formula.toNNF().toCNF() + " := " + formula.toNNF().toCNF().getClauses());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void power() {
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			
			AdvancedSet<BeliefAtom> domaina = new AdvancedSet<BeliefAtom>();
			domaina.add(p.parseBelief("calibrated_transformer"));
			domaina.add(p.parseBelief("calibrated_voltSensors"));
			for(int j = 1; j <= 10; j++) {
				domaina.add(p.parseBelief("calibrated_voltSensor(sensor" + j + ")"));
			}
			domaina.add(p.parseBelief("switchedOn_transformer"));
			for(int j = 1; j <= 10; j++) {
				domaina.add(p.parseBelief("switchedOn_voltSensor(sensor" + j + ")"));
			}
			domaina.add(p.parseBelief("working_transformer"));
			domaina.add(p.parseBelief("supplying_powergrid"));
			
			i.getBeliefBase().addLocalEpistemicState(new ProbabilisticCompactEpistemicState(domaina));
			
			i.getBeliefBase().revise(p.parseLiteral("calibrated_transformer"), 0.79);
			i.getBeliefBase().revise(p.parseLiteral("calibrated_voltSensors"), 0.88);
//			i.getBeliefBase().revise(p.parseLiteral("calibrated_voltSensor(sensor3)"), 0.88);
//			i.getBeliefBase().revise(p.parseLiteral("calibrated_voltSensor(sensor6)"), 0.88);
//			i.getBeliefBase().revise(p.parseLiteral("calibrated_voltSensor(sensor9)"), 0.88);
			i.getBeliefBase().revise(p.parseLiteral("switchedOn_transformer"), 0.51);
//			i.getBeliefBase().revise(p.parseLiteral("~working_transformer"), 0.81);
//			i.getBeliefBase().revise(p.parseLiteral("~supplying_powergrid"), 0.89);
			
			i.getPlanLibrary().add(p.parsePlan("+!prepareToStartSubstation : true <- calibrate_transformer; calibrate_voltSensors; !startSubstation"));
			i.getPlanLibrary().add(p.parsePlan("+!startSubstation : calibrated_transformer && calibrated_voltSensors <- switchOn_transformer; switchOn_voltSensors; !runSubstation"));
			i.getPlanLibrary().add(p.parsePlan("+!runSubstation : switchedOn_transformer && switchedOn_voltSensors <- work_transformer; work_voltSensors; supply_powergrid"));
			i.getPlanLibrary().add(p.parsePlan("*(voltLow, X) : not working_transformer && not supplying_powergrid <- stop_transformer; maintain_transformer; !prepareToStartSubstation"));
			
//			i.getEventSet().add(new ExternalEvent(new AddGoalEventTrigger(p.parseGoal("!prepareToStartSubstation"))));
			i.getEventSet().add(new ExternalEvent(new ReviseBeliefEventTrigger(p.parseLiteral("voltLow"), p.parseTerm("0.8"))));
			
			i.run();
			
//			List<String> formulae = new LinkedList<String>();
//			formulae.add("calibrated_transformer && calibrated_voltSensor(X)");
//			formulae.add("switchedOn_transformer && switchedOn_voltSensor(X)");
//			formulae.add("not working_transformer && not supplying_powergrid");
//			formulae.add("~working_transformer && ~supplying_powergrid");
//			
//			System.out.println("B = " + i.getBeliefBase());
//			for(String s : formulae) {
//				LogicalExpression formula = p.parseFormula(s);
//				System.out.println("B \\models " + formula + " = " + i.getBeliefBase().entails(formula));
//			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		parseFormula();
//		compactESes();
//		compactGUB();
		
//		parsingPlans();
//		example();
//		exampleGround();
//		context();
//		revision();
//		negationAsFailure();
		
//		relationalExpression();
		
//		semanticEpistemicState();
//		System.out.println();
//		probabilisticSemanticEpistemicState();
//		System.out.println();
//		possibilisticSemanticEpistemicState();
//		System.out.println();
//		semanticGlobalUncertainBelief();
//		System.out.println();
//		
//		compactEpistemicState();
//		System.out.println();
//		probabilisticCompactEpistemicState();
//		System.out.println();
//		possibilisticCompactEpistemicState();
//		System.out.println();
//		compactGlobalUncertainBelief();
		
//		cnf();
		
		power();
	}

}
