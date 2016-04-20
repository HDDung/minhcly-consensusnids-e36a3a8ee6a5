package edu.vgu.nids.output;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.vgu.nids.core.TestPack;
import edu.vgu.nids.output.entry.StatisticsOutputEntry;

/**
 * A factory generates folders to store log and data.
 */
public final class OutputPack {
	
	private CSVWriter csvStat;
	private Logger logger;
	private String folder;
	
	public CSVWriter GetCSVStatisticsWriter() {
		return csvStat;
	}
	public Logger GetLogger() {
		return logger;
	}
	
	public void Close() throws IOException {
		csvStat.Close();
		logger.Close();
	}
	
	public static OutputPack Create() throws IOException {
		String folder = "Log\\" + new SimpleDateFormat("yy.MM.dd-HH.mm.ss").format(new Date());
		new File(folder).mkdirs();
		
		OutputPack pack = new OutputPack();
		
		pack.folder = folder;
		pack.csvStat = new CSVWriter(new File(folder + "\\Stat.csv"),
				StatisticsOutputEntry.GetHeader());
		pack.logger = new Logger(new File(folder + "\\Log.txt"), true);
		
		return pack;
	}
	
	public EvaluationOutput Make(TestPack pack, OutputFlags flags) throws IOException {
		return new EvaluationOutput(folder + "\\" + pack.GetIndex() + "." + pack.GetName() + "." + pack.GetWeightName(), flags);
	}
	
	public void log(String str) throws IOException {
		logger.Log(str);
	}
	public void logln(String str) throws IOException {
		logger.LogLine(str);
	}
}
