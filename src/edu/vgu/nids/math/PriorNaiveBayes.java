package edu.vgu.nids.math;

import edu.vgu.nids.input.AttributeFilter;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Attribute;
import weka.core.Instance;

/**
 * A extended version of NaiveBayesUpdateable
 * which return the raw prior distrubution. 
 */
@SuppressWarnings("serial")
public class PriorNaiveBayes extends NaiveBayesUpdateable {

	private final double BOTTOM_LIMIT = 1e-10;
	private static Vector<String> allowedAttribute;
	
	public static PriorNaiveBayes Default; 

	static {
		allowedAttribute = new Vector<String>(Arrays.asList(AttributeFilter.Get()));
		Default = new PriorNaiveBayes();
	}
	
	public PriorNaiveBayes() {
		super();
	}

	public PreciseNumber[] GetPriorDistribution(Instance instance) {

		if (m_UseDiscretization) {
			m_Disc.input(instance);
			instance = m_Disc.output();
		}

		PreciseNumber[] prior = new PreciseNumber[m_NumClasses];
		for (int i = 0; i < prior.length; i++)
			prior[i] = new PreciseNumber(1);

		Enumeration<?> enumAtts = instance.enumerateAttributes();

		for(int attIndex = 0; enumAtts.hasMoreElements(); attIndex++) {			
			Attribute attribute = (Attribute) enumAtts.nextElement();

			if (!instance.isMissing(attribute) && allowedAttribute.contains(attribute.name())) {				
				for (int i = 0; i < m_NumClasses; i++) {

					double temp = Math.max(BOTTOM_LIMIT,
							Math.pow(m_Distributions[attIndex][i].getProbability(instance.value(attribute)), 
									m_Instances.attribute(attIndex).weight()));
					prior[i] = prior[i].mul(new PreciseNumber(temp));
					
				}
			}
		}

		return prior;
	}

}
