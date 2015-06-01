package uncertainty;

import agentspeak.LogicalExpression;
import agentspeak.Unifier;
import agentspeak.logical_expressions.BeliefAtom;
import agentspeak.logical_expressions.operations.BinaryOperation;
import agentspeak.logical_expressions.operations.UnaryOperation;
import agentspeak.logical_expressions.operations.binary_operations.Conjunction;
import agentspeak.logical_expressions.operations.binary_operations.Disjunction;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGE;
import agentspeak.logical_expressions.operations.binary_operations.PlausibilityGT;
import agentspeak.logical_expressions.operations.unary_operations.NegationAsFailure;
import agentspeak.logical_expressions.operations.unary_operations.StrongNegation;
import agentspeak.logical_expressions.relational_expressions.relational_operations.BinaryRelationalOperation;
import agentspeak.logical_expressions.relational_expressions.relational_operations.binary_relational_operations.Equal;
import agentspeak.logical_expressions.relational_expressions.relational_operations.binary_relational_operations.NotEqual;
import agentspeak.logical_expressions.terminals.BeliefLiteral;
import agentspeak.logical_expressions.terminals.Primitive;
import agentspeak.logical_expressions.terminals.primitives.Contradiction;
import agentspeak.logical_expressions.terminals.primitives.Tautology;
import data_structures.AdvancedSet;
import exceptions.NotGroundException;

public abstract class EpistemicState {
	
	private AdvancedSet<BeliefAtom> domain;
	
	public EpistemicState(AdvancedSet<BeliefAtom> d) throws NotGroundException {
		for(BeliefAtom b : d) {
			if(!b.getTerm().isGround()) {
				throw new NotGroundException("belief atoms in domain must be ground");
			}
		}
		domain = d;
	}
	
	public AdvancedSet<BeliefAtom> getDomain() {
		return domain;
	}
	
	private boolean languageContains(BeliefAtom a) {
		for(BeliefAtom d : domain) {
			Unifier u = a.getTerm().unify(d.getTerm());
			if(u != null) {
				return true;
			}
		}
		return false;
	}
	
	public boolean languageContains(LogicalExpression formula) {
		for(BeliefAtom a : formula.getBeliefAtoms()) {
			if(!this.languageContains(a)) {
				return false;
			}
		}
		return true;
	}
	
	public abstract double getMinWeight();
	
	public abstract double getMaxWeight();
	
	public AdvancedSet<Unifier> possibleUnifiers(LogicalExpression f, Unifier u) throws Exception {
		if(f instanceof Primitive) {
			return this.possibleUnifiers((Primitive)f, u);
		} else if(f instanceof BeliefAtom) {
			return this.possibleUnifiers((BeliefAtom)f, u);
		} else if(f instanceof BeliefLiteral) {
			return this.possibleUnifiers((BeliefLiteral)f, u);
		} else if(f instanceof BinaryOperation) {
			return this.possibleUnifiers((BinaryOperation)f, u);
		} else if(f instanceof UnaryOperation) {
			return this.possibleUnifiers((UnaryOperation)f, u);
		} else if(f instanceof BinaryRelationalOperation) {
			return this.possibleUnifiers((BinaryRelationalOperation)f, u);
		} else {
			throw new UnsupportedOperationException("formula must be normalized");
		}
	}
	
	public AdvancedSet<Unifier> possibleUnifiers(Primitive f, Unifier u) throws Exception {
		AdvancedSet<Unifier> unifiers = new AdvancedSet<Unifier>();
		unifiers.add(u);
		return unifiers;
	}
	
	public AdvancedSet<Unifier> possibleUnifiers(BeliefAtom f, Unifier u) throws Exception {
		AdvancedSet<Unifier> unifiers = new AdvancedSet<Unifier>();
		for(BeliefAtom atom : this.getDomain()) {
			Unifier newUnifier = f.substitute(u).getTerm().unify(atom.getTerm());
			if(newUnifier != null) {
				newUnifier.putAll(u);
				unifiers.add(newUnifier);
			}
		}
		return unifiers;
	}
	
	public AdvancedSet<Unifier> possibleUnifiers(BeliefLiteral f, Unifier u) throws Exception {
		return this.possibleUnifiers(f.getBeliefAtom(), u);
	}
	
	public AdvancedSet<Unifier> possibleUnifiers(BinaryOperation f, Unifier u) throws Exception {
		AdvancedSet<Unifier> leftUnifiers = this.possibleUnifiers(f.getLeft(), u);
		AdvancedSet<Unifier> rightUnifiers = new AdvancedSet<Unifier>();
		if(!leftUnifiers.isEmpty()) {
			for(Unifier lu : leftUnifiers) {
				rightUnifiers.addAll(this.possibleUnifiers(f.getRight(), lu));
			}
		}
		return rightUnifiers;
	}
	
	public AdvancedSet<Unifier> possibleUnifiers(UnaryOperation f, Unifier u) throws Exception {
		return this.possibleUnifiers(f.getChild(), u);
	}
	
	public AdvancedSet<Unifier> possibleUnifiers(BinaryRelationalOperation f, Unifier u) throws Exception {
		AdvancedSet<Unifier> unifiers = new AdvancedSet<Unifier>();
		unifiers.add(u);
		return unifiers;
	}
	
	public abstract double lambda(LogicalExpression f) throws Exception;
	
	public LogicalExpression pare(LogicalExpression f) throws Exception {
		if(f instanceof Contradiction) {
			return this.pare((Contradiction)f);
		} else if(f instanceof Tautology) {
			return this.pare((Tautology)f);
		} else if(f instanceof BeliefAtom) {
			return this.pare((BeliefAtom)f);
		} else if(f instanceof BeliefLiteral) {
			return this.pare((BeliefLiteral)f);
		} else if(f instanceof Conjunction) {
			return this.pare((Conjunction)f);
		} else if(f instanceof Disjunction) {
			return this.pare((Disjunction)f);
		} else if(f instanceof PlausibilityGE) {
			return this.pare((PlausibilityGE)f);
		} else if(f instanceof PlausibilityGT) {
			return this.pare((PlausibilityGT)f);
		} else if(f instanceof StrongNegation) {
			return this.pare((StrongNegation)f);
		} else if(f instanceof NegationAsFailure) {
			return this.pare((NegationAsFailure)f);
		} else if(f instanceof Equal) {
			return this.pare((Equal)f);
		} else if(f instanceof NotEqual) {
			return this.pare((NotEqual)f);
		} else {
			throw new UnsupportedOperationException("formula must be normalized");
		}
	}
	
	public Contradiction pare(Contradiction f) throws Exception {
		return f;
	}
	
	public Tautology pare(Tautology f) throws Exception {
		return f;
	}
	
	public BeliefAtom pare(BeliefAtom f) throws Exception {
		return f;
	}
	
	public BeliefLiteral pare(BeliefLiteral f) throws Exception {
		return f;
	}
	
	public Conjunction pare(Conjunction f) throws Exception {
		return new Conjunction(this.pare(f.getLeft()), this.pare(f.getRight()));
	}
	
	public Disjunction pare(Disjunction f) throws Exception {
		return new Disjunction(this.pare(f.getLeft()), this.pare(f.getRight()));
	}
	
	public Primitive pare(PlausibilityGE f) throws Exception {
		if(this.lambda(new StrongNegation(f.getLeft())) <= this.lambda(new StrongNegation(f.getRight()))) {
			return new Tautology();
		} else {
			return new Contradiction();
		}
	}
	
	public Primitive pare(PlausibilityGT f) throws Exception {
		if(this.lambda(new StrongNegation(f.getLeft())) < this.lambda(new StrongNegation(f.getRight()))) {
			return new Tautology();
		} else {
			return new Contradiction();
		}
	}
	
	public StrongNegation pare(StrongNegation f) throws Exception {
		return new StrongNegation(this.pare(f.getChild()));
	}
	
	public Primitive pare(NegationAsFailure f) throws Exception {
		if(this.lambda(new StrongNegation(f.getChild())) >= this.lambda(f.getChild())) {
			return new Tautology();
		} else {
			return new Contradiction();
		}
	}
	
	public Primitive pare(Equal f) throws Exception {
		if(f.getLeft().equals(f.getRight())) {
			return new Tautology();
		} else {
			return new Contradiction();
		}
	}
	
	public Primitive pare(NotEqual f) throws Exception {
		if(!f.getLeft().equals(f.getRight())) {
			return new Tautology();
		} else {
			return new Contradiction();
		}
	}
	
	public Unifier entails(LogicalExpression f) throws Exception {
		return this.entails(f, new Unifier());
	}
	
	public Unifier entails(LogicalExpression f, Unifier u) throws Exception {
		AdvancedSet<Unifier> possibleUnifiers = this.possibleUnifiers(f, u);
		for(Unifier pu : possibleUnifiers) {
			LogicalExpression ground = f.substitute(pu);
			if(this.lambda(ground) > this.lambda(new StrongNegation(this.pare(ground)))) {
				return pu;
			}
		}
		return null;
	}
	
}
