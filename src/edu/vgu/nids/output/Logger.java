package edu.vgu.nids.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class Logger {

	private FileWriter outputWriter;
	private boolean printToScreen;
	
	public Logger(File outputFile, boolean printToScreen) throws IOException {
		outputWriter = new FileWriter(outputFile);
		this.printToScreen = printToScreen;
	}
	
	public void Log(String str) throws IOException {
		outputWriter.write(str);
		if (printToScreen) System.out.print(str);
	}
	public void LogLine(String str) throws IOException {
		Log(str + "\n");
	}
	
	public void Close() throws IOException {
		outputWriter.close();
	}
}
