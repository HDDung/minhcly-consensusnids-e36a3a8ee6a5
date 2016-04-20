package edu.vgu.nids.consensus;

import weka.core.Instance;
import edu.vgu.nids.math.PreciseNumber;
import edu.vgu.nids.math.PriorNaiveBayes;

/**
 * This class is the data for <b>GraphNode</b> in Consensus based NIDS.
 */
public final class Module {

	private static double consensusLimit = 1e-2;
	
	private PreciseNumber[] priors;
	private Belief priorsLog;
	private Belief consensusLog;
	
	public Module() { }
	
	public PreciseNumber[] GetPriors() {
		return priors;
	}
	
	/**
	 * Make the belief for consensus algorithm based on the log of priors.
	 */
	public void MakeBelief() {
		priorsLog = new Belief(priors[0].Log10(), priors[1].Log10());
		consensusLog = new Belief(priorsLog);
	}
	/**
	 * Get the current belief of the module.
	 */
	public Belief GetBelief() {
		return priorsLog;
	}
	
	/**
	 * Exchange belief between modules.
	 * @param beliefLog Input belief
	 * @param weight Weight for consensus algorithm
	 */
	public void ExchangeBelief(Module module, double weight) {		
		consensusLog = consensusLog.add(
				module.priorsLog.substract(priorsLog).mul(weight));
		module.consensusLog = module.consensusLog.add(
				priorsLog.substract(module.priorsLog).mul(weight));
	}
	/**
	 * Update belief after an iteration.
	 */
	public boolean UpdateBelief() {
		boolean strong = consensusLog.substract(priorsLog).IsBoundedBy(priorsLog.mul(consensusLimit));
		
		priorsLog = new Belief(consensusLog);
		
		return strong;
	}
	
	/**
	 * Convert the belief back to priors.
	 */
	public void SetBelief() {
		priors[0] = PreciseNumber.Exp10(priorsLog.GetNormal());
		priors[1] = PreciseNumber.Exp10(priorsLog.GetAttack());
	}
	
	public void Audit(Instance instance) {
		priors = PriorNaiveBayes.Default.GetPriorDistribution(instance);
	}
	
	public static void SetConsensusLimit(double value) {
		consensusLimit = value;
	}
}
