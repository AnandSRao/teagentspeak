package uncertainty;

import java.util.HashMap;
import java.util.Map;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import agentspeak.LogicalExpression;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.Terminal;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import agentspeak.logical_expressions.terminals.primitives.Contradiction;
import agentspeak.logical_expressions.terminals.primitives.Tautology;
import data_structures.AdvancedSet;
import exceptions.UnsupportedOperationException;

public class SATSolver {
	
	private ISolver solver;
	
	public SATSolver() {
		solver = SolverFactory.newLight();
		solver.setTimeout(5);
	}
	
	public boolean isSatisfiable(LogicalExpression f) throws UnsupportedOperationException, TimeoutException {
		try {
			AdvancedSet<Clause> clauses = f.toNNF().toCNF().getClauses();
			Map<BeliefAtom, Integer> labels = new HashMap<BeliefAtom, Integer>();
			final int tautologyLabel = 1;
			int nextLabel = 2;
			for(Clause clause : clauses) {
				if(clause.size() == 1 && clause.contains(new Contradiction())) {
					solver.reset();
					return false; // trivially unsatisfiable
				}
				VecInt labelledClause = new VecInt();
				for(Terminal terminal : clause) {
					if(terminal instanceof BeliefLiteral) {
						BeliefLiteral literal = (BeliefLiteral)terminal;
						BeliefAtom atom = literal.getBeliefAtom();
						int label;
						if(labels.containsKey(atom)) {
							label = labels.get(atom);
						} else {
							label = nextLabel;
							labels.put(atom, label);
							nextLabel++;
						}
						if(literal.isPositive()) {
							labelledClause.push(label);
						} else {
							labelledClause.push(-label);
						}
					} else if(terminal instanceof Tautology) {
						labelledClause.push(tautologyLabel);
						labelledClause.push(-tautologyLabel);
					}
				}
				solver.addClause(labelledClause);
			}
			IProblem problem = solver;
			boolean satisfiable = problem.isSatisfiable();
			solver.reset();
			return satisfiable;
		} catch(ContradictionException e) {
			solver.reset();
			return false; // trivially unsatisfiable
		}
	}
	
}
