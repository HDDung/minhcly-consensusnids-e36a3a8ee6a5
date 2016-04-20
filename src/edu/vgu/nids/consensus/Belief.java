package edu.vgu.nids.consensus;

public final class Belief {

	private double normal;
	private double attack;
	
	public Belief(double normal, double attack) {
		this.normal = normal;
		this.attack = attack;
	}
	public Belief(Belief instance) {
		this.normal = instance.normal;
		this.attack = instance.attack;
	}
	
	public Belief add(Belief belief) {
		return new Belief(this.normal + belief.normal, this.attack + belief.attack);
	}
	public Belief substract(Belief belief) {
		return new Belief(this.normal - belief.normal, this.attack - belief.attack);
	}
	public Belief mul(double num) {
		return new Belief(this.normal * num, this.attack * num);
	}
	
	public boolean IsBoundedBy(Belief belief) {
		return (Math.abs(normal) < Math.abs(belief.normal) && Math.abs(attack) < Math.abs(belief.attack));
	}
	
	public double GetNormal() {
		return normal;
	}
	public double GetAttack() {
		return attack;
	}
}
