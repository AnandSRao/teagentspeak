package uncertainty.epistemic_states;

import java.util.HashMap;
import java.util.Map;

import uncertainty.EpistemicState;
import agentspeak.LogicalExpression;
import agentspeak.logical_expressions.BeliefAtom;
import data_structures.AdvancedSet;
import exceptions.NotGroundException;

public class SemanticEpistemicState extends EpistemicState {
	
	private Map<World, Double> beliefBase;
	
	public SemanticEpistemicState(AdvancedSet<BeliefAtom> a) throws NotGroundException {
		super(a);
		beliefBase = new HashMap<World, Double>();
	}
	
	public Map<World, Double> getBeliefBase() {
		return beliefBase;
	}
	
	@Override
	public double getMinWeight() {
		return Double.NEGATIVE_INFINITY;
	}
	
	@Override
	public double getMaxWeight() {
		double max = Double.NEGATIVE_INFINITY;
		for(Map.Entry<World, Double> entry : beliefBase.entrySet()) {
			double weight = entry.getValue();
			if(weight > max) {
				max = weight;
			}
        }
		return max;
	}
	
	public void revise(World world, double weight) throws Exception {
		if(!super.getDomain().supersetOf(world.getTrueAtoms())) {
			throw new Exception("incompatible domain");
		}
		
		if(weight == 0) {
			return;
		}
		
		if(weight == this.getMinWeight()) {
			beliefBase.remove(world);
		} else {
			beliefBase.put(world, weight);
		}
	}
	
	public double getWeight(World world) {
        if(beliefBase.containsKey(world)) {
        	return beliefBase.get(world);
        } else {
        	return this.getMinWeight();
        }
    }
	
	@Override
	public double lambda(LogicalExpression f) throws Exception {
		if(!f.isGround()) {
			throw new NotGroundException("formula must be ground");
		}
		LogicalExpression formula;
		if(f.inNNF()) {
			formula = this.pare(f);
		} else {
			formula = this.pare(f.toNNF());
		}
		double max = Double.NEGATIVE_INFINITY;
		for(Map.Entry<World, Double> entry : beliefBase.entrySet()) {
			World world = entry.getKey();
			if(world.models(formula)) {
				double weight = entry.getValue();
				if(weight > max) {
					max = weight;
				}
			}
		}
		return max;
	}
	
	/*
	 * Standard.
	 */
	@Override
	public String toString() {
		String output = "{";
		String delim = "";
        for(Map.Entry<World, Double> entry : beliefBase.entrySet()) {
        	output += delim + "\\lambda(" + entry.getKey().toString() + ")=" + String.format("%.2f", entry.getValue());
        	delim = ", ";
        }
        output += "}";
        return output;
	}
	
}
