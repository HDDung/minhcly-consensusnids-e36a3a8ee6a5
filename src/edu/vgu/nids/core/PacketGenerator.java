package edu.vgu.nids.core;

import java.util.Random;

import weka.core.Instances;

/**
 * A class which generates data <b>Packet</b> for the <b>Network</b>. <br>
 * Feed the <b>Audit(Packet)</b> function in <b>Network</b> by the <b>Packet</b> returned from <b>GetPacket(State).</b> 
 */
public class PacketGenerator {

	private Instances m_normal, m_attack;
	private static double attackDataRatio;
	private int m_size;
	private Random rnd;
	
	public PacketGenerator(int size, Instances normal, Instances attack) {
		m_size = size;
		m_normal = normal;
		m_attack = attack;
		
		attackDataRatio = 1;
		rnd = new Random();
	}
	
	public static void SetAttackDataRatio(double ratio) {
		if (attackDataRatio < 0 || attackDataRatio > 1)
			throw new IllegalArgumentException("Attack Data Ratio must be between 0 and 1");
			
		attackDataRatio = ratio;
	}
	
	public Packet GetPacket(State state) {
		Packet packet = new Packet();
		
		// Normal state
		if (state == State.NORMAL) {
			// Pick normal instances randomly and add them to the packet 
			for (int i = 0; i < m_size; i++)
				packet.Add(m_normal.instance(rnd.nextInt(m_normal.numInstances())));
		}
		
		// Attack state
		else {
			int numAttack = (int)Math.round(attackDataRatio * m_size);
			
			// Add attack instances randomly
			for (int i = 0; i < numAttack; i++)
				packet.Add(m_attack.instance(rnd.nextInt(m_attack.numInstances())));
			
			// Add normal instances randomly
			for (int i = 0; i < m_size - numAttack; i++)
				packet.Add(m_normal.instance(rnd.nextInt(m_normal.numInstances())));
		}
		
		return packet;
	}
}
