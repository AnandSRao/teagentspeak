package uncertainty;

import data_structures.AdvancedSet;
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

public abstract class GlobalUncertainBelief {
	
	private AdvancedSet<BeliefAtom> domain;
	
	public GlobalUncertainBelief() {
		domain = new AdvancedSet<BeliefAtom>();
	}
	
	public void addToDomain(AdvancedSet<BeliefAtom> d) {
		domain.addAll(d);
	}
	
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
		for(BeliefAtom atom : domain) {
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
	
	public Unifier entails(LogicalExpression f) throws Exception {
		return this.entails(f, new Unifier());
	}
	
//	public Unifier entails(LogicalExpression f, Unifier u) throws Exception {
//		if(f instanceof Contradiction) {
//			return this.entails((Contradiction)f, u);
//		} else if(f instanceof Tautology) {
//			return this.entails((Tautology)f, u);
//		} else if(f instanceof BeliefAtom) {
//			return this.entails((BeliefAtom)f, u);
//		} else if(f instanceof BeliefLiteral) {
//			return this.entails((BeliefLiteral)f, u);
//		} else if(f instanceof Conjunction) {
//			return this.entails((Conjunction)f, u);
//		} else if(f instanceof Disjunction) {
//			return this.entails((Disjunction)f, u);
//		} else if(f instanceof PlausibilityGE) {
//			return this.entails((PlausibilityGE)f, u);
//		} else if(f instanceof PlausibilityGT) {
//			return this.entails((PlausibilityGT)f, u);
//		} else if(f instanceof StrongNegation) {
//			return this.entails((StrongNegation)f, u);
//		} else if(f instanceof NegationAsFailure) {
//			return this.entails((NegationAsFailure)f, u);
//		} else if(f instanceof Equal) {
//			return this.entails((Equal)f, u);
//		} else if(f instanceof NotEqual) {
//			return this.entails((NotEqual)f, u);
//		} else {
//			throw new UnsupportedOperationException("formula must be normalized");
//		}
//	}
	
	public Unifier entails(LogicalExpression f, Unifier u) throws Exception {
		AdvancedSet<Unifier> possibleUnifiers = this.possibleUnifiers(f, u);
		for(Unifier pu : possibleUnifiers) {
			//System.err.println(pu + "...");
			Unifier unifier = null;
			if(f instanceof Contradiction) {
				//System.err.println("Contradiction: " + f);
				unifier = this.entails((Contradiction)f, pu);
			} else if(f instanceof Tautology) {
				//System.err.println("Tautology: " + f);
				unifier = this.entails((Tautology)f, pu);
			} else if(f instanceof BeliefAtom) {
				//System.err.println("BeliefAtom: " + f);
				unifier = this.entails((BeliefAtom)f, pu);
			} else if(f instanceof BeliefLiteral) {
				//System.err.println("BeliefLiteral: " + f);
				unifier = this.entails((BeliefLiteral)f, pu);
			} else if(f instanceof Conjunction) {
				//System.err.println("Conjunction: " + f);
				unifier = this.entails((Conjunction)f, pu);
			} else if(f instanceof Disjunction) {
				//System.err.println("Disjunction: " + f);
				unifier = this.entails((Disjunction)f, pu);
			} else if(f instanceof PlausibilityGE) {
				//System.err.println("PlausibilityGE: " + f);
				unifier = this.entails((PlausibilityGE)f, pu);
			} else if(f instanceof PlausibilityGT) {
				//System.err.println("PlausibilityGT: " + f);
				unifier = this.entails((PlausibilityGT)f, pu);
			} else if(f instanceof StrongNegation) {
				//System.err.println("StrongNegation: " + f);
				unifier = this.entails((StrongNegation)f, pu);
			} else if(f instanceof NegationAsFailure) {
				//System.err.println("NegationAsFailure: " + f);
				unifier = this.entails((NegationAsFailure)f, pu);
			} else if(f instanceof Equal) {
				//System.err.println("Equal: " + f);
				unifier = this.entails((Equal)f, pu);
			} else if(f instanceof NotEqual) {
				//System.err.println("NotEqual: " + f);
				unifier = this.entails((NotEqual)f, pu);
			} else {
				throw new UnsupportedOperationException("formula must be normalized");
			}
			if(unifier != null) {
				return unifier;
			}
		}
		return null;
	}
	
	public Unifier entails(Contradiction f, Unifier u) throws Exception {
		return null;
	}
	
	public Unifier entails(Tautology f, Unifier u) throws Exception {
		return u;
	}
	
	public abstract Unifier entails(BeliefAtom f, Unifier u) throws Exception;
	
	public abstract Unifier entails(BeliefLiteral f, Unifier u) throws Exception;
	
	public abstract Unifier entails(Conjunction f, Unifier u) throws Exception;
	
	public abstract Unifier entails(Disjunction f, Unifier u) throws Exception;
	
	public abstract Unifier entails(PlausibilityGE f, Unifier u) throws Exception;
	
	public abstract Unifier entails(PlausibilityGT f, Unifier u) throws Exception;
	
	public abstract Unifier entails(StrongNegation f, Unifier u) throws Exception;
	
	public abstract Unifier entails(NegationAsFailure f, Unifier u) throws Exception;
	
	public abstract Unifier entails(Equal f, Unifier u) throws Exception;
	
	public abstract Unifier entails(NotEqual f, Unifier u) throws Exception;
	
}
