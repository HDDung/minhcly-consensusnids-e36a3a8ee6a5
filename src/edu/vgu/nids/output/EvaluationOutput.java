package edu.vgu.nids.output;

import java.io.File;
import java.io.IOException;

import edu.vgu.nids.output.entry.EvaluatingOutputEntry;
import edu.vgu.nids.output.entry.ImageOutputEntry;
import edu.vgu.nids.output.entry.NodeLogOutputEntry;

public final class EvaluationOutput {

	private CSVWriter csvDetail;
	private CSVWriter csvImage;
	private CSVWriter csvNodeLog;
	
	public CSVWriter GetCSVDetailWriter() {
		return csvDetail;
	}
	public CSVWriter GetCSVImageWriter() {
		return csvImage;
	}
	public CSVWriter GetCSVNodeLogWriter() {
		return csvNodeLog;
	}
	
	public void Close() throws IOException {
		if (csvDetail != null)
			csvDetail.Close();
		if (csvImage != null)
			csvImage.Close();
		if (csvNodeLog != null)
			csvNodeLog.Close();
	}
	
	EvaluationOutput(String folder, OutputFlags flags) throws IOException {

		new File(folder).mkdirs();
		
		if (flags.detail)
			csvDetail = new CSVWriter(new File(folder + "\\Detail.csv"),
					EvaluatingOutputEntry.GetHeader());
		if (flags.image)
			csvImage = new CSVWriter(new File(folder + "\\Image.csv"),
					ImageOutputEntry.GetHeader());
		if (flags.nodelog)
			csvNodeLog = new CSVWriter(new File(folder + "\\NodeLog.csv"),
					NodeLogOutputEntry.GetHeader());
	}
}
