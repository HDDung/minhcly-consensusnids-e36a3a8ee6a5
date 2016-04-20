package edu.vgu.nids.core;

/**
 * A class counts the number of true and false results.
 */
public class Accountant {

	private int truepos;
	private int falsepos;
	private int trueneg;
	private int falseneg;
	
	private State lastState;
	
	public void SetInput(State state) {
		lastState = state;
	}
	
	public void SetOutput(State state) {
		if (state == lastState) {
			if (state == State.NORMAL)
				truepos++;
			else
				trueneg++;
		} else {
			if (state == State.ATTACK)
				falsepos++;
			else
				falseneg++;
		}
	}

	public int GetTruePositiveCount() {
		return truepos;
	}
	public int GetTrueNegativeCount() {
		return trueneg;
	}
	public int GetFalsePositiveCount() {
		return falsepos;
	}
	public int GetFalseNegativeCount() {
		return falseneg;
	}
	
	public int GetNormalInputCount() {
		return truepos + falsepos;
	}
	public int GetAttackInputCount() {
		return trueneg + falseneg;
	}
	
	public int GetTrueOutputCount() {
		return truepos + trueneg;
	}
	public int GetFalseOutputCount() {
		return falsepos + falseneg;
	}
	
	public int GetCount() {
		return truepos + falsepos + trueneg + falseneg;
	}
	
	public double GetAccuracy() {
		return (double)GetTrueOutputCount() / GetCount();
	}
}
