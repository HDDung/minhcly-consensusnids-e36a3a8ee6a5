package edu.vgu.nids.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Vector;

import weka.core.Instances;

public final class DataFilter {

	private static Vector<String> attackSignature;
	private static String normalSignature = "normal";

	static {
		attackSignature = new Vector<String>(Arrays.asList(SignatureFilter.Get()));
	}

	private Instances m_instance;
	private File m_signatureFile;
	private int count = 0;
	private int accepted = 0;

	public DataFilter(Instances rawInstance, File signatureFile) {
		m_instance = rawInstance;
		m_signatureFile = signatureFile;
	}

	public Instances GetData(DataFilterCriteria criteria)
			throws Exception {

		Instances filtered = new Instances(m_instance, 0);
		filtered.setClassIndex(filtered.numAttributes() - 1);

		BufferedReader rawReader = new BufferedReader(new FileReader(m_signatureFile));

		Vector<String> acceptedSignature = new Vector<String>();
		switch (criteria) {
		case ALL:
			acceptedSignature.addAll(attackSignature);
			acceptedSignature.add(normalSignature);
			break;
		case ATTACK:
			acceptedSignature.addAll(attackSignature);
			break;
		case NORMAL:
			acceptedSignature.add(normalSignature);
			break;
		}
		
		String line = null;
		
		accepted = 0;
		count = 0;
		
		while ((line = rawReader.readLine()) != null) {
			String[] result = line.split(",");
			String signature = result[result.length - 2];
			
			if (acceptedSignature.contains(signature)) {
				filtered.add(m_instance.instance(count));
				accepted++;
			}
			
			count++;
		}
		
		rawReader.close();

		return filtered;
	}
	public int GetLastCount() {
		return count;
	}
	public int GetLastAcceptingCount() {
		return accepted;
	}
	
}