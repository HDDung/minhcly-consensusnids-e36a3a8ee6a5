package edu.vgu.nids.core;

import edu.vgu.nids.math.PreciseNumber;

public final class Report {
	
	private State state;
	private int cost;
	private PreciseNumber normal;
	private PreciseNumber attack;
	private int iteration;
	
	public Report(State state, int cost, PreciseNumber[] priors) {
		this(state, cost, priors, 1);
	}
	public Report(State state, int cost, PreciseNumber[] priors, int iteration) {
		this.state = state;
		this.cost = cost;
		normal = priors[0];
		attack = priors[1];
		this.iteration = iteration;
	}
	
	public State GetState() {
		return state;
	}
	public int GetCost() {
		return cost;
	}
	public PreciseNumber GetNormalPrior() {
		return normal;
	}
	public PreciseNumber GetAttackPrior() {
		return attack;
	}
	public int GetNumberOfIterations() {
		return iteration;
	}
}
