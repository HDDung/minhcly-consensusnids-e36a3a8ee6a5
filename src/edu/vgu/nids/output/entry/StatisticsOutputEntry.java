package edu.vgu.nids.output.entry;

import edu.vgu.nids.consensus.ConsensusAccountant;
import edu.vgu.nids.core.Accountant;
import edu.vgu.nids.core.TestPack;

public final class StatisticsOutputEntry implements IOutputEntry {

	private TestPack testPack;
	private Accountant centralizedAcc;
	private ConsensusAccountant consensusAcc;
	
	public StatisticsOutputEntry(TestPack testPack, Accountant centralized, ConsensusAccountant consensus) {
		this.testPack = testPack;
		centralizedAcc = centralized;
		consensusAcc = consensus;
	}
	
	@Override
	public String GetCSVOutput() {
		String str = "";
		
		/*
		 * Structure of an entry
		 * 		Index
		 * 		Name
		 * 		Weight Name
		 * 		Centralized True Positive
		 * 		Centralized True Negative
		 * 		Centralized False Positive
		 * 		Centralized False Negative
		 * 		Centralized Accuracy
		 * 		Consensus True Positive
		 * 		Consensus True Negative
		 * 		Consensus False Positive
		 * 		Consensus False Negative
		 * 		Consensus Accuracy
		 * 		Consensus Average Iteration
		 */

		str += testPack.GetIndex() + ",";
		str += testPack.GetName() + ",";
		str += testPack.GetWeightName() + ",";
		str += centralizedAcc.GetTruePositiveCount() + ",";
		str += centralizedAcc.GetTrueNegativeCount() + ",";
		str += centralizedAcc.GetFalsePositiveCount() + ",";
		str += centralizedAcc.GetFalseNegativeCount() + ",";
		str += centralizedAcc.GetAccuracy() + ",";
		str += consensusAcc.GetTruePositiveCount() + ",";
		str += consensusAcc.GetTrueNegativeCount() + ",";
		str += consensusAcc.GetFalsePositiveCount() + ",";
		str += consensusAcc.GetFalseNegativeCount() + ",";
		str += consensusAcc.GetAccuracy() + ",";
		str += consensusAcc.GetAverageIteration();
		
		return str;
	}
	public static String[] GetHeader() {
		return new String[] {
				"Index",
				"Name",
				"Weight",
				"Cen TP",
				"Cen TN",
				"Cen FP",
				"Cen FN",
				"Cen Acc",
				"Con TP",
				"Con TN",
				"Con FP",
				"Con FN",
				"Con Acc",
				"Con AvgItr"
		};
	}

	@Override
	public String GetTextOutput() {
		String str = testPack.GetName() + "\t" + testPack.GetWeightName() + "\n";
		
		str += "Centralized system:\tAcc " + centralizedAcc.GetAccuracy() + "\n";
		str += "TP: " + centralizedAcc.GetTruePositiveCount() + "\t";
		str += "TN: " + centralizedAcc.GetTrueNegativeCount() + "\t";
		str += "FP: " + centralizedAcc.GetFalsePositiveCount() + "\t";
		str += "FN: " + centralizedAcc.GetFalseNegativeCount() + "\n";
		
		str += "Consensus system:\tAcc " + consensusAcc.GetAccuracy()  + "\t";
		str += "AvgItr " + consensusAcc.GetAverageIteration() + "\n";
		str += "TP: " + consensusAcc.GetTruePositiveCount() + "\t";
		str += "TN: " + consensusAcc.GetTrueNegativeCount() + "\t";
		str += "FP: " + consensusAcc.GetFalsePositiveCount() + "\t";
		str += "FN: " + consensusAcc.GetFalseNegativeCount();
		
		return str;
	}
	
	@Override
	public String toString() {
		return GetTextOutput();
	}

}
