package edu.vgu.nids.output.entry;

import edu.vgu.nids.consensus.Belief;
import edu.vgu.nids.consensus.NodeLog;

public final class NodeLogOutputEntry implements IOutputEntry {

	private NodeLog nodeLog;
	private int loop;
	
	public NodeLogOutputEntry(int loop, NodeLog nodeLog) {
		this.loop = loop;
		this.nodeLog = nodeLog;
	}
	
	@Override
	public String GetCSVOutput() {
		String str = "";
		
		/*
		 * Structure of an entry
		 * 		Loop
		 * 		Iteration
		 * 		N Prior
		 * 		A Prior
		 */
		
		Belief[] data = nodeLog.GetLog();
		
		for (int i = 0; i < data.length; i++) {
			str += loop + ",";
			str += i + ",";
			str += data[i].GetNormal() + ",";
			str += data[i].GetAttack();
			if (i < data.length - 1)
				str += "\n";
		}
		
		return str;
	}
	public static String[] GetHeader() {
		return new String[] {
				"Loop",
				"Iteration",
				"N Prior",
				"A Prior"
		};
	}

	@Override
	public String GetTextOutput() {
		String str = "";
		
		Belief[] data = nodeLog.GetLog();
		for (int i = 0; i < data.length; i++)
			str += i + "\tN: " + data[i].GetNormal() + "\tA: " + data[i].GetAttack() + "\n";
		
		return str;
	}
	
	@Override
	public String toString() {
		return GetTextOutput();
	}

}
