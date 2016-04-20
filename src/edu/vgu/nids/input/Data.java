package edu.vgu.nids.input;

import java.io.File;
import java.io.FileReader;

import edu.vgu.nids.Program;
import weka.core.Instances;

/**
 * Class which prepares data for the simulation. This class returns filtered data from files.
 */
public final class Data {

	public static final String TrainFileName = "Data\\KDDTrain+.arff";
	public static final String TrainSignName = "Data\\KDDTrain+.txt";
	public static final String TestFileName = "Data\\KDDTest+.arff";
	public static final String TestSignName = "Data\\KDDTest+.txt";
	
	public static Data Default;
	
	private Instances trainAll, testNormal, testAttack;

	public Data(File trainFile, File trainingSignFile,
			File testFile, File testingSignFile)
					throws Exception {
		
		DataFilter trainFilter = new DataFilter(new Instances(new FileReader(trainFile)), trainingSignFile);
		DataFilter testFilter = new DataFilter(new Instances(new FileReader(testFile)), testingSignFile);

		trainAll = trainFilter.GetData(DataFilterCriteria.ALL);
		Program.out.logln("Filtered " + trainFilter.GetLastCount() +
				" to " + trainFilter.GetLastAcceptingCount() + " for training");
		
		testNormal = testFilter.GetData(DataFilterCriteria.NORMAL);
		Program.out.logln("Filtered " + testFilter.GetLastCount() +
				" to " + testFilter.GetLastAcceptingCount() + " for normal testing");
		
		testAttack = testFilter.GetData(DataFilterCriteria.ATTACK);
		Program.out.logln("Filtered " + testFilter.GetLastCount() +
				" to " + testFilter.GetLastAcceptingCount() + " for attack testing");
	}

	public static void Initialize() throws Exception {
		Default = new Data(
				new File(TrainFileName), new File(TrainSignName),
				new File(TestFileName), new File(TestSignName));
	}
	
	public Instances GetTrainingData() {
		return trainAll;
	}
	public Instances GetNormalData() {
		return testNormal;
	}
	public Instances GetAttackData() {
		return testAttack;
	}
}
