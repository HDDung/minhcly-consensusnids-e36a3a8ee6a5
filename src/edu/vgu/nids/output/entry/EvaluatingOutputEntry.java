package edu.vgu.nids.output.entry;

import edu.vgu.nids.core.Report;
import edu.vgu.nids.core.State;

public final class EvaluatingOutputEntry implements IOutputEntry {

	private State actual;
	private Report centralizedReport;
	private Report consensusReport;
	private int loop;
	
	public EvaluatingOutputEntry(int loop, State actual, Report centralized, Report consensus) {
		this.loop = loop;
		this.actual = actual;
		centralizedReport = centralized;
		consensusReport = consensus;
	}

	@Override
	public String GetCSVOutput() {
		String str = "";
		
		/*
		 * Structure of an entry
		 * 		Loop
		 * 		Actual State
		 * 		Centralized State
		 * 		Centralized Cost
		 * 		Centralized Normal Prior
		 * 		Centralized Attack Prior
		 * 		Consensus State
		 * 		Consensus Cost
		 * 		Consensus Iteration
		 * 		Consensus Normal Prior
		 * 		Consensus Attack Prior
		 */
		
		str += loop + ",";
		str += actual + ",";
		str += centralizedReport.GetState() + ",";
		str += centralizedReport.GetCost() + ",";
		str += centralizedReport.GetNormalPrior().Log10() + ",";
		str += centralizedReport.GetAttackPrior().Log10() + ",";
		str += consensusReport.GetState() + ",";
		str += consensusReport.GetCost() + ",";
		str += consensusReport.GetNumberOfIterations() + ",";
		str += consensusReport.GetNormalPrior().Log10() + ",";
		str += consensusReport.GetAttackPrior().Log10() + ",";
		
		return str;
	}
	public static String[] GetHeader() {
		return new String[] {
				"Loop",
				"Actual",
				"Cen State",
				"Cen Cost",
				"Cen N Prior",
				"Cen A Prior",
				"Con State",
				"Con Cost",
				"Con Iter",
				"Con N Prior",
				"Con A Prior"
		};
	}
	
	@Override
	public String GetTextOutput() {
		String str = "";

		str += "Centralized: " + centralizedReport.GetState() + "\t";
		str += "C: " + centralizedReport.GetCost() + "\t";
		str += "N: " + centralizedReport.GetNormalPrior().Log10() + "\t";
		str += "A: " + centralizedReport.GetAttackPrior().Log10() + "\n";
		
		str += "Consensus: " + consensusReport.GetState() + "\t";
		str += "C: " + consensusReport.GetCost() + "\t";
		str += "N: " + consensusReport.GetNormalPrior().Log10() + "\t";
		str += "A: " + consensusReport.GetAttackPrior().Log10() + "\t";
		str += "I: " + consensusReport.GetNumberOfIterations() + "\n";
		
		return str;
	}

	@Override
	public String toString() {
		return GetTextOutput();
	}
}
