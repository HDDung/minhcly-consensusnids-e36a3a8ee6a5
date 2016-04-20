package edu.vgu.nids.core;

import java.io.IOException;
import java.util.Random;

import edu.vgu.nids.Program;
import edu.vgu.nids.consensus.ConsensusAccountant;
import edu.vgu.nids.consensus.ConsensusSystem;
import edu.vgu.nids.input.Data;
import edu.vgu.nids.output.CSVWriter;
import edu.vgu.nids.output.EvaluationOutput;
import edu.vgu.nids.output.OutputFlags;
import edu.vgu.nids.output.entry.EvaluatingOutputEntry;
import edu.vgu.nids.output.entry.IOutputEntry;
import edu.vgu.nids.output.entry.ImageOutputEntry;
import edu.vgu.nids.output.entry.NodeLogOutputEntry;
import edu.vgu.nids.output.entry.StatisticsOutputEntry;

public final class Evaluator {

	private TestPack testPack;
	private int loops;
	private OutputFlags flags;
	
	private CentralizedSystem cenSys;
	private ConsensusSystem conSys;
	private PacketGenerator generator;
	private Random rnd;
	
	private static double adjustment;
	
	public Evaluator(TestPack testPack, int loops, OutputFlags flags) {
		this.testPack = testPack;
		this.loops = loops;
		this.flags = flags;
		
		rnd = new Random();
		
		cenSys = new CentralizedSystem(testPack.GetNetwork(), adjustment);
		conSys = new ConsensusSystem(testPack.GetNetwork(), testPack.GetWeightModel(), adjustment);
		
		generator = new PacketGenerator(testPack.GetSize(),
				Data.Default.GetNormalData(), Data.Default.GetAttackData());
	}
	public Evaluator(TestPack testPack, int loops) {
		this(testPack, loops, OutputFlags.Normal());
	}
	
	public void Evaluate() throws IOException {
		CSVWriter statWriter = Program.out.GetCSVStatisticsWriter();
		EvaluationOutput output = Program.out.Make(testPack, flags);
		CSVWriter detailWriter = output.GetCSVDetailWriter();
		CSVWriter imageWriter = output.GetCSVImageWriter();
		CSVWriter nodeLogWriter = output.GetCSVNodeLogWriter();
		
		Accountant centralizedAcc = new Accountant();
		ConsensusAccountant consensusAcc = new ConsensusAccountant();
		
		Program.out.logln("-------------------------------------------");
		Program.out.logln("Evaluating model " + testPack.GetIndex());
		
		// Evaluate
		for (int i = 0; i < loops; i++) {
			
			// Get a random state
			State input;
			if (rnd.nextBoolean()) input = State.ATTACK;
			else input = State.NORMAL;
			
			// Audit the data
			centralizedAcc.SetInput(input);
			consensusAcc.SetInput(input);
			testPack.GetNetwork().Audit(generator.GetPacket(input));
			
			// Get the result
			Report centralizedReport = cenSys.Evaluate();
			centralizedAcc.SetOutput(centralizedReport.GetState());
			
			Report consensusReport = conSys.Evaluate();
			consensusAcc.SetOutput(consensusReport.GetState(), consensusReport.GetNumberOfIterations());
			
			// Print the result to file
			if (flags.detail)
				detailWriter.Write(new EvaluatingOutputEntry(i, input, centralizedReport, consensusReport));

			if (flags.image)
				imageWriter.Write(new ImageOutputEntry(i, conSys.GetInitImage()));

			if (flags.nodelog)
				nodeLogWriter.Write(new NodeLogOutputEntry(i, conSys.GetNodeLog()));
		}
		
		output.Close();
		
		// Print statistics results
		IOutputEntry stat = new StatisticsOutputEntry(testPack, centralizedAcc, consensusAcc);
		if (flags.stat)			
			statWriter.Write(stat);
		
		Program.out.logln(stat.GetTextOutput());
	}
	
	public static void SetAdjustment(double value) {
		adjustment = value;
	}
}
