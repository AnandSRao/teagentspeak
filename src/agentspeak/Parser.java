package agentspeak;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import exceptions.UnsupportedOperationException;
import agentspeak.actions.EnvironmentAction;
import agentspeak.actions.belief_actions.ReviseBeliefAction;
import agentspeak.actions.goal_actions.AchievementGoalAction;
import agentspeak.actions.goal_actions.TestGoalAction;
import agentspeak.event_triggers.belief_event_triggers.ReviseBeliefEventTrigger;
import agentspeak.event_triggers.goal_event_triggers.AddGoalEventTrigger;
import agentspeak.event_triggers.goal_event_triggers.DeleteGoalEventTrigger;
import agentspeak.goals.AchievementGoal;
import agentspeak.goals.TestGoal;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.RelationalExpression;
import agentspeak.logical_expressions.operations.binary_operations.Conjunction;
import agentspeak.logical_expressions.operations.binary_operations.Disjunction;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGE;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGT;
import agentspeak.logical_expressions.operations.unary_operations.NegationAsFailure;
import agentspeak.logical_expressions.operations.unary_operations.StrongNegation;
import agentspeak.logical_expressions.relational_expressions.relational_operations.binary_relational_operations.Equal;
import agentspeak.logical_expressions.relational_expressions.relational_operations.binary_relational_operations.NotEqual;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.NegativeLiteral;
import agentspeak.logical_expressions.terminals.belief_literals.PositiveLiteral;
import agentspeak.logical_expressions.terminals.primitives.Contradiction;
import agentspeak.logical_expressions.terminals.primitives.Tautology;
import agentspeak.terms.Structure;
import agentspeak.terms.Variable;
import agentspeak.terms.constants.Atom;
import agentspeak.terms.constants.Number;
import agentspeak.terms.constants.Text;
import agentspeak.terms.constants.numbers.DoubleNumber;
import agentspeak.terms.constants.numbers.IntegerNumber;

public class Parser {
	
	private Queue<String> input;
	
	public LogicalExpression parseFormula(String s) throws Exception {
		input = tokenize(s);
		
		LogicalExpression f = formula();
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return f;
	}
	
	public BeliefLiteral parseLiteral(String s) throws ParseException {
		input = tokenize(s);
		
		BeliefLiteral b = literal();
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return b;
	}
	
	public BeliefAtom parseBelief(String s) throws ParseException {
		input = tokenize(s);
		
		BeliefAtom b = belief();
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return b;
	}
	
	public Term parseTerm(String s) throws ParseException {
		input = tokenize(s);
		
		Term t = term();
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return t;
	}
	
	public Queue<String> tokenize(String s) {
		Queue<String> input = new LinkedList<String>();
		
		s = s.replace(StrongNegation.SYMBOL_STRONG_NEGATION, " $1$ ");
		s = s.replace(Disjunction.SYMBOL_DISJUNCTION, " $2$ ");
		s = s.replace(Conjunction.SYMBOL_CONJUNCTION, " $3$ ");
		s = s.replace(PlausibilityGE.SYMBOL_PLAUSIBILITY_GE, " $4$ ");
		s = s.replace(PlausibilityGT.SYMBOL_PLAUSIBILITY_GT, " $5$ ");
		s = s.replace(Plan.SYMBOL_CONTEXT, " $6$ ");
		s = s.replace(Plan.SYMBOL_ACTIONS, " $7$ ");
		s = s.replace(Plan.SYMBOL_ACTION_SEPARATOR, " $8$ ");
		s = s.replace(AddGoalEventTrigger.SYMBOL_ADD_GOAL, " $9$ ");
		s = s.replace(DeleteGoalEventTrigger.SYMBOL_DELETE_GOAL, " $10$ ");
		s = s.replace(AchievementGoal.SYMBOL_ACHIEVEMENT_GOAL, " $11$ ");
		s = s.replace(TestGoal.SYMBOL_TEST_GOAL, " $12$ ");
		s = s.replace(ReviseBeliefAction.SYMBOL_REVISE_BELIEF, " $13$ ");
		s = s.replace(NotEqual.SYMBOL_NOT_EQUAL, " $14$ ");
		s = s.replace(Equal.SYMBOL_EQUAL, " $15$ ");
		
		s = s.replace("$15$", Equal.SYMBOL_EQUAL);
		s = s.replace("$14$", NotEqual.SYMBOL_NOT_EQUAL);
		s = s.replace("$13$", ReviseBeliefAction.SYMBOL_REVISE_BELIEF);
		s = s.replace("$12$", TestGoal.SYMBOL_TEST_GOAL);
		s = s.replace("$11$", AchievementGoal.SYMBOL_ACHIEVEMENT_GOAL);
		s = s.replace("$10$", DeleteGoalEventTrigger.SYMBOL_DELETE_GOAL);
		s = s.replace("$9$", AddGoalEventTrigger.SYMBOL_ADD_GOAL);
		s = s.replace("$8$", Plan.SYMBOL_ACTION_SEPARATOR);
		s = s.replace("$7$", Plan.SYMBOL_ACTIONS);
		s = s.replace("$6$", Plan.SYMBOL_CONTEXT);
		s = s.replace("$5$", PlausibilityGT.SYMBOL_PLAUSIBILITY_GT);
		s = s.replace("$4$", PlausibilityGE.SYMBOL_PLAUSIBILITY_GE);
		s = s.replace("$3$", Conjunction.SYMBOL_CONJUNCTION);
		s = s.replace("$2$", Disjunction.SYMBOL_DISJUNCTION);
		s = s.replace("$1$", StrongNegation.SYMBOL_STRONG_NEGATION);
		
		s = s.replace("(", " ( ");
		s = s.replace(")", " ) ");
		s = s.replace(",", " , ");
		
		s = s.trim();
		
		String[] tokens = s.split("\\s+");
		for(String token : tokens) {
			input.add(token);
		}
		return input;
	}
	
	private String next() throws ParseException {
		if(input.isEmpty()) {
			throw new ParseException("unexpected end of input", input.size());
		}
		
		while(input.peek().isEmpty()) {
			consume();
		}
		
		return input.peek();
	}
	
	private void consume() {
		input.poll();
	}
	
	private void expect(String s) throws ParseException {
		if(next().equals(s)) {
			consume();
		} else {
			throw new ParseException("unexpected symbol: " + s, input.size());
		}
	}
	
	private boolean isUnary(String s) {
		return s.equals(StrongNegation.SYMBOL_STRONG_NEGATION)
				|| s.equals(NegationAsFailure.SYMBOL_NEGATION_AS_FAILURE);
	}
	
	private boolean isBinary(String s) {
		return s.equals(Conjunction.SYMBOL_CONJUNCTION)
				|| s.equals(Disjunction.SYMBOL_DISJUNCTION)
				|| s.equals(PlausibilityGT.SYMBOL_PLAUSIBILITY_GT)
				|| s.equals(PlausibilityGE.SYMBOL_PLAUSIBILITY_GE);
	}
	
	private LogicalExpression makeUnary(String o, LogicalExpression t) throws ParseException, UnsupportedOperationException {
		if(o.equals(StrongNegation.SYMBOL_STRONG_NEGATION)) {
			return new StrongNegation(t);
		} else if(o.equals(NegationAsFailure.SYMBOL_NEGATION_AS_FAILURE)) {
			return new NegationAsFailure(t);
		} else {
			throw new ParseException("unrecognised operator: " + o, input.size());
		}
	}
	
	private LogicalExpression makeBinary(String o, LogicalExpression t1, LogicalExpression t2) throws ParseException, UnsupportedOperationException {
		if(o.equals(Conjunction.SYMBOL_CONJUNCTION)) {
			return new Conjunction(t1, t2);
		} else if(o.equals(Disjunction.SYMBOL_DISJUNCTION)) {
			return new Disjunction(t1, t2);
		} else if(o.equals(PlausibilityGT.SYMBOL_PLAUSIBILITY_GT)) {
			return new PlausibilityGT(t1, t2);
		} else if(o.equals(PlausibilityGE.SYMBOL_PLAUSIBILITY_GE)) {
			return new PlausibilityGE(t1, t2);
		} else {
			throw new ParseException("unrecognised operator: " + o, input.size());
		}
	}
	
	private int precedence(String o) throws ParseException {
		if(o.equals(StrongNegation.SYMBOL_STRONG_NEGATION)) {
			return StrongNegation.PRECEDENCE_STRONG_NEGATION;
		} else if(o.equals(NegationAsFailure.SYMBOL_NEGATION_AS_FAILURE)) {
			return NegationAsFailure.PRECEDENCE_NEGATION_AS_FAILURE;
		} else if(o.equals(Conjunction.SYMBOL_CONJUNCTION)) {
			return Conjunction.PRECEDENCE_CONJUNCTION;
		} else if(o.equals(Disjunction.SYMBOL_DISJUNCTION)) {
			return Disjunction.PRECEDENCE_DISJUNCTION;
		} else if(o.equals(PlausibilityGT.SYMBOL_PLAUSIBILITY_GT)) {
			return PlausibilityGT.PRECEDENCE_PLAUSIBILITY_GT;
		} else if(o.equals(PlausibilityGE.SYMBOL_PLAUSIBILITY_GE)) {
			return PlausibilityGE.PRECEDENCE_PLAUSIBILITY_GE;
		} else {
			throw new ParseException("unrecognised operator: " + o, input.size());
		}
	}
	
	private LogicalExpression formula() throws ParseException, UnsupportedOperationException {
		return node(0);
	}
	
	private LogicalExpression node(int p) throws ParseException, UnsupportedOperationException {
		LogicalExpression t = leaf();
		
		while(!input.isEmpty() && isBinary(next()) && precedence(next()) >= p) {
			String op = next();
			consume();
			int q = precedence(op) + 1;
			LogicalExpression t1 = node(q);
			t = makeBinary(op, t, t1);
		}
		
		return t;
	}
	
	private LogicalExpression leaf() throws ParseException, UnsupportedOperationException {
		LogicalExpression t;
		
		if(isUnary(next())) {
			String op = next();
			consume();
			int q = precedence(op);
			t = node(q);
			return makeUnary(op, t);
		} else if(next().equals("(")) {
			consume();
			t = node(0);
			expect(")");
			return t;
		} else if(next().equals("true")) {
			t = new Tautology();
			consume();
			return t;
		} else if(next().equals("false")) {
			t = new Contradiction();
			consume();
			return t;
		} else {
			Term t1 = term();
			if(!input.isEmpty() && next().equals(Equal.SYMBOL_EQUAL)) {
				consume();
				return new Equal(t1, term());
			} if(!input.isEmpty() && next().equals(NotEqual.SYMBOL_NOT_EQUAL)) {
				consume();
				return new NotEqual(t1, term());
			} else {
				if(Atom.isValid(t1.toString())) {
					return new BeliefAtom(t1);
				} else {
					throw new ParseException("parsing error at: " + next(), input.size());
				}
			}
		}
	}
	
	private BeliefAtom belief() throws ParseException {
		if(Atom.isValid(next())) {
			return new BeliefAtom(term());
		} else {
			throw new ParseException("parsing error at: " + next(), input.size());
		}
	}
	
	private Term term() throws ParseException {
		if(Variable.isValid(next())) {
			return variable();
		} else if(Atom.isValid(next())) {
			Atom a = atom();
			
			if(!input.isEmpty() && next().equals("(")) {
				List<Term> arguments = new ArrayList<Term>();
				consume();
				Term t1 = term();
				arguments.add(t1);
				while(!next().equals(")")) {
					expect(",");
					Term t2 = term();
					arguments.add(t2);
				}
				consume();
				return new Structure(a, arguments);
			} else {
				return a;
			}
		} else if(Number.isValid(next())) {
			return number();
		} else if(Text.isValid(next())) {
			return text();
		} else {
			throw new ParseException("parsing error at: " + next(), input.size());
		}
	}
	
	private Text text() throws ParseException {
		if(Text.isValid(next())) {
			Text t = new Text(next());
			consume();
			return t;
		} else {
			throw new ParseException("parsing error at: " + next(), input.size());
		}
	}
	
	private Term number() throws ParseException {
		String number = "";
		if(next().equals("-")) {
			number = "-";
			consume();
		}
		number += next();
		if(IntegerNumber.isValid(number)) {
			IntegerNumber n = new IntegerNumber(number);
			consume();
			return n;
		} else if(DoubleNumber.isValid(number)) {
			DoubleNumber n = new DoubleNumber(number);
			consume();
			return n;
		} else if(Variable.isValid(number)) {
			Variable v = new Variable(number);
			consume();
			return v;
		} else {
			throw new ParseException("parsing error at: " + next(), input.size());
		}
	}
	
	private Atom atom() throws ParseException {
		if(Atom.isValid(next())) {
			Atom a = new Atom(next());
			consume();
			return a;
		} else {
			throw new ParseException("parsing error at: " + next(), input.size());
		}
	}
	
	private Term variable() throws ParseException {
		if(Variable.isValid(next())) {
			Variable v = new Variable(next());
			consume();
			return v;
		} else {
			throw new ParseException("parsing error at: " + next(), input.size());
		}
	}
	
	public Plan parsePlan(String s) throws ParseException, UnsupportedOperationException {
		input = tokenize(s);
		
		Plan p = plan();
		
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return p;
	}
	
	public Goal parseGoal(String s) throws ParseException, UnsupportedOperationException {
		input = tokenize(s);
		
		Goal p = goal();
		
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return p;
	}
	
	private Plan plan() throws ParseException, UnsupportedOperationException {
		EventTrigger e = eventTrigger();
		expect(Plan.SYMBOL_CONTEXT);
		LogicalExpression context = node(0);
		expect(Plan.SYMBOL_ACTIONS);
		List<Action> actions = new ArrayList<Action>();
		if(next().equals(Tautology.SYMBOL_TAUTOLOGY)) {
			consume();
		} else {
			actions.add(action());
		}
		while(!input.isEmpty() && next().equals(Plan.SYMBOL_ACTION_SEPARATOR)) {
			consume();
			if(next().equals(Tautology.SYMBOL_TAUTOLOGY)) {
				consume();
			} else {
				actions.add(action());
			}
		}
		return new Plan(e, context, actions);
	}
	
	private Action action() throws ParseException {
		if(next().equals(AchievementGoal.SYMBOL_ACHIEVEMENT_GOAL)) {
			return new AchievementGoalAction(achievementGoal());
		} else if(next().equals(TestGoal.SYMBOL_TEST_GOAL)) {
			return new TestGoalAction(testGoal());
		} else if(next().equals(ReviseBeliefAction.SYMBOL_REVISE_BELIEF)) {
			consume();
			expect("(");
			BeliefLiteral literal = literal();
			expect(",");
			Term weight = number();
			expect(")");
			return new ReviseBeliefAction(literal, weight);
		} else {
			return new EnvironmentAction(term());
		}
	}
	
	private BeliefLiteral literal() throws ParseException {
		if(next().equals(StrongNegation.SYMBOL_STRONG_NEGATION)) {
			consume();
			return new NegativeLiteral(belief());
		} else {
			return new PositiveLiteral(belief());
		}
	}
	
	private EventTrigger eventTrigger() throws ParseException {
		if(next().equals(AddGoalEventTrigger.SYMBOL_ADD_GOAL)) {
			consume();
			return new AddGoalEventTrigger(goal());
		} else if(next().equals(DeleteGoalEventTrigger.SYMBOL_DELETE_GOAL)) {
			consume();
			return new DeleteGoalEventTrigger(goal());
		} else if(next().equals(ReviseBeliefAction.SYMBOL_REVISE_BELIEF)) {
			consume();
			expect("(");
			BeliefLiteral literal = literal();
			expect(",");
			Term weight;
			if(next().equals("-") || Number.isValid(next())) {
				weight = number();
			} else if(Variable.isValid(next())) {
				weight = variable();
			} else {
				throw new ParseException("number or variable expected at: " + next(), input.size());
			}
			expect(")");
			return new ReviseBeliefEventTrigger(literal, weight);
		} else {
			throw new ParseException("event operator expected at: " + next(), input.size());
		}
	}
	
	private Goal goal() throws ParseException {
		if(next().equals(AchievementGoal.SYMBOL_ACHIEVEMENT_GOAL)) {
			return achievementGoal();
		} else if(next().equals(TestGoal.SYMBOL_TEST_GOAL)) {
			return testGoal();
		} else {
			throw new ParseException("goal operator expected at: " + next(), input.size());
		}
	}
	
	private TestGoal testGoal() throws ParseException {
		if(next().equals(TestGoal.SYMBOL_TEST_GOAL)) {
			consume();
			return new TestGoal(term());
		} else {
			throw new ParseException("goal operator expected at: " + next(), input.size());
		}
	}
	
	private AchievementGoal achievementGoal() throws ParseException {
		if(next().equals(AchievementGoal.SYMBOL_ACHIEVEMENT_GOAL)) {
			consume();
			return new AchievementGoal(term());
		} else {
			throw new ParseException("goal operator expected at: " + next(), input.size());
		}
	}
	
	public RelationalExpression parseRelationalExpression(String s) throws ParseException, UnsupportedOperationException {
		input = tokenize(s);
		
		RelationalExpression p = relationalExpression();
		
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return p;
	}
	
	private RelationalExpression relationalExpression() throws ParseException {
		Term t = term();
		if(next().equals(Equal.SYMBOL_EQUAL)) {
			consume();
			return new Equal(t, term());
		} if(next().equals(NotEqual.SYMBOL_NOT_EQUAL)) {
			consume();
			return new NotEqual(t, term());
		} else {
			throw new ParseException("relational expression expected at: " + next(), input.size());
		}
	}
	
}