package edu.vgu.nids.math;

/**
 * Represent a number with detached mantissa and exponent
 */
public final class PreciseNumber implements Comparable<PreciseNumber> {

	private Double m_mantissa;
	private int m_exponent;
	
	public PreciseNumber(double mantissa, int exponent) {
		m_mantissa = mantissa;
		m_exponent = exponent;
		Normalize();
	}
	public PreciseNumber() {
		this(0, 0);
	}
	public PreciseNumber(double mantissa) {
		this(mantissa, 0);
	}
	
	private void Normalize() {
		if (m_mantissa == 0) {
			m_exponent = 0;
			return;
		}
		while (m_mantissa >= 10) {
			m_mantissa /= 10;
			m_exponent++;
		}
		while (m_mantissa < 1) {
			m_mantissa *= 10;
			m_exponent--;
		}
	}
	
	private double GetDoubleExponent(int exp) {
		// Basis case
		if (exp == 0)
			return 1;
		
		// Binary decomposition
		boolean remain = (exp % 2) > 0;
		double result = GetDoubleExponent(exp / 2);
		
		// Square and multiply algorithm 
		result *= result;
		if (remain) result *= 10;
		return result;
	}
	public double ToDouble() {
		return m_mantissa * GetDoubleExponent(m_exponent);
	}	

	public static int compare(PreciseNumber n1, PreciseNumber n2) {
		if (n1.m_exponent > n2.m_exponent)
			return 1;
		if (n1.m_exponent < n2.m_exponent)
			return -1;
		return Double.compare(n1.m_mantissa, n2.m_mantissa);
	}
	@Override
	public int compareTo(PreciseNumber number) {
		return compare(this, number);
	}
	@Override
	public boolean equals(Object number) {
		return compareTo((PreciseNumber)number) == 0;
	}
	
	public static PreciseNumber mul(PreciseNumber n1, PreciseNumber n2) {
		return new PreciseNumber(n1.m_mantissa*n2.m_mantissa, n1.m_exponent+n2.m_exponent);
	}
	public PreciseNumber mul(PreciseNumber number) {
		return mul(this, number);
	}

	@Override
	public String toString() {
		return m_mantissa + "E" + m_exponent;
	}
	
	public double Log10() {
		return Math.log10(m_mantissa) + m_exponent;
	}
	public static PreciseNumber Exp10(double log) {
		int exponent = (int) Math.floor(log);
		double mantissa = Math.exp(log - exponent);
		
		return new PreciseNumber(mantissa, exponent);
	}
}
