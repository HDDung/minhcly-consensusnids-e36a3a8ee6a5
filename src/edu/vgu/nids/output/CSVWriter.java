package edu.vgu.nids.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.vgu.nids.output.entry.IOutputEntry;

public final class CSVWriter {

	private FileWriter outputWriter;
	
	public CSVWriter(File outputFile, String[] attributes) throws IOException {
		outputWriter = new FileWriter(outputFile);
		
		for (int i = 0; i < attributes.length - 1; i++) {
			outputWriter.write(attributes[i] + ", ");
		}
		outputWriter.write(attributes[attributes.length - 1] + "\n");
	}
	
	public void Write(IOutputEntry entry) throws IOException {
		outputWriter.write(entry.GetCSVOutput() + "\n");
	}
	
	public void Close() throws IOException {
		outputWriter.close();
	}
}
