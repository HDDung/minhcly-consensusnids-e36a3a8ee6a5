package edu.vgu.nids.input;

/**
 * A class which contains a list of signatures which is specified by a DoS attack, used in <b>DataFilter</b> class.
 */
public final class SignatureFilter {
	public static String[] Get() {
		return new String[] {
				"apache2",
				"back",
				"land",
				"mailbomb",
				"pod",
				"processtable",
				"smurf",
				"neptune",
				"teardrop",
				"udpstorm"
		};
	}
}
