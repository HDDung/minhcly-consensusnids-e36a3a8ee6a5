package edu.vgu.nids.output;

public final class OutputFlags {
	
	public boolean stat;
	public boolean detail;
	public boolean image;
	public boolean nodelog;
	
	public static OutputFlags Normal() {
		OutputFlags flags = new OutputFlags();
		flags.stat = flags.detail = flags.image = flags.nodelog = true;
		return flags;
	}
	
	public static OutputFlags NoLog() {
		OutputFlags flags = new OutputFlags();
		flags.stat = flags.detail = flags.image = true;
		flags.nodelog = false;
		return flags;
	}
	
	public static OutputFlags NoImageAndLog() {
		OutputFlags flags = new OutputFlags();
		flags.stat = flags.detail = true;
		flags.nodelog = flags.image = false;
		return flags;
	}
}
