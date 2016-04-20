package edu.vgu.nids;

import java.util.Locale;

import edu.vgu.nids.consensus.Module;
import edu.vgu.nids.consensus.weight.BestConstantEdgeWeight;
import edu.vgu.nids.consensus.weight.IConsensusWeight;
import edu.vgu.nids.consensus.weight.LocalDegreeWeight;
import edu.vgu.nids.consensus.weight.MaxDegreeWeight;
import edu.vgu.nids.core.Evaluator;
import edu.vgu.nids.core.Network;
import edu.vgu.nids.core.PacketGenerator;
import edu.vgu.nids.core.TestPack;
import edu.vgu.nids.graph.model.PetersenGraph;
import edu.vgu.nids.graph.model.RandomGraph;
import edu.vgu.nids.graph.model.RingGraph;
import edu.vgu.nids.graph.model.TorusGraph;
import edu.vgu.nids.input.Data;
import edu.vgu.nids.math.PriorNaiveBayes;
import edu.vgu.nids.output.OutputFlags;
import edu.vgu.nids.output.OutputPack;

/**
 * <p>The entry class of the project</p> 
 * <p>Project name: <b>Consensus based Network Intrusion Detection System</b></p> 
 * @author Michel Toulouse<br>Nguyen Linh Giang<br>Philip Curtis<br>Bui Quang Minh
 */
public final class Program {

	public static OutputPack out;

	/**
	 * The entry point of the project
	 * @param args An array of strings which contains command-line arguments
	 */
	public static void main(String[] args) {
		try {			
			Locale.setDefault(Locale.ENGLISH);
			
			// Modify this will change the accuracy of the consensus system
			// The default is 1% (1e-2)
			Module.SetConsensusLimit(1e-3);
			
			// Modify this will change the attack ratio of the attack
			// 0.6 means if an attack is perform, only 60% the nodes will receive the anomalous data  
			PacketGenerator.SetAttackDataRatio(.6);
			
			// Modify this will change the Tau in decision phase
			// 9.5 is the best value that I found for the system, it also depends on the AttackRatio above
			// 9.5 meens that p(normal)/p(attack) must greater than 10^9.5 to be confirm as a Normal state
			Evaluator.SetAdjustment(9.5);

			out = OutputPack.Create();

			out.logln("Start filtering");
			Data.Initialize();

			out.logln("Start training");
			PriorNaiveBayes.Default.buildClassifier(Data.Default.GetTrainingData());

			out.logln("Start testing");
			
			// The list of weight models will be simulated
			IConsensusWeight[] weights = new IConsensusWeight[]	{
					new LocalDegreeWeight(),
					new MaxDegreeWeight(),
					new BestConstantEdgeWeight() };
			
			// Grid graph and Ring graph of the size of 9, 25, 49, 81, 121
			for (IConsensusWeight weight: weights)
				for (int i = 3; i <= 11; i += 2) {

					// Test Ring graph 1000 times with no Node Log output
					new Evaluator(new TestPack(
							new Network(new RingGraph(i*i)), weight), 1000,
							OutputFlags.NoLog())
					.Evaluate();
					
					// Test Grid graph 1000 times
					new Evaluator(new TestPack(
							new Network(new TorusGraph(i, i)), weight), 1000)
					.Evaluate();				
				}
			
			// Peterson graph and 10 other Random graphs 10-15
			RandomGraph[] randomGraphs = new RandomGraph[10];
			
			out.logln("\nRandom Graph:");			
			for (int i = 0; i < randomGraphs.length; i++) {
				randomGraphs[i] = new RandomGraph(10, 15);
				out.logln(i + "\t" + randomGraphs[i].toString());
			}
			
			for (IConsensusWeight weight: weights) {
				
				// Test Petersen graph 1000 times
				new Evaluator(new TestPack(
						new Network(new PetersenGraph()), weight), 1000)
				.Evaluate();
				
				for (int i = 0; i < 10; i++)
					// Test each Random graph 1000 times
					new Evaluator(new TestPack(
							new Network(randomGraphs[i]), weight), 1000)
					.Evaluate();
			}
			
			out.logln("\nSimulation done");			
			out.Close();

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

}
