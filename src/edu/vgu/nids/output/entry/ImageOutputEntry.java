package edu.vgu.nids.output.entry;

import edu.vgu.nids.consensus.Belief;
import edu.vgu.nids.consensus.NetworkImage;

public final class ImageOutputEntry implements IOutputEntry {

	private NetworkImage image;
	private int loop;
	
	public ImageOutputEntry(int loop, NetworkImage image) {
		this.loop = loop;
		this.image = image;
	}
	
	@Override
	public String GetCSVOutput() {
		String str = "";
		
		/*
		 * Structure of an entry
		 * 		Loop
		 * 		Node index
		 * 		N Prior
		 * 		A Prior
		 */
		
		Belief[] data = image.GetImage();
		
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
				"Node",
				"N Prior",
				"A Prior"
		};
	}

	@Override
	public String GetTextOutput() {
		String str = "";
		
		Belief[] data = image.GetImage();
		for (int i = 0; i < data.length; i++)
			str += i + "\tN: " + data[i].GetNormal() + "\tA: " + data[i].GetAttack() + "\n";
		
		return str;
	}
	
	@Override
	public String toString() {
		return GetTextOutput();
	}

}
