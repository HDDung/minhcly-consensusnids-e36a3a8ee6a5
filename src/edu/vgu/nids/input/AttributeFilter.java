package edu.vgu.nids.input;

/**
 * A class contains a list of attributes which are used in <b>PriorNaiveBayesUpdateable</b> class
 */
public final class AttributeFilter {
	public static String[] Get() {
		return new String[] {
				"src_bytes",
				"diff_srv_rate",
				"service",
				"same_srv_rate",
				"flag",
				"dst_bytes",
				"count",
				"dst_host_same_srv_rate",
				"dst_host_diff_srv_rate",
				"dst_host_serror_rate",
				"dst_host_srv_serror_rate",
				"serror_rate",
				"srv_serror_rate",
				"dst_host_srv_count",
				//"logged_in",
				//"dst_host_count",
				//"dst_host_srv_diff_host_rate",
				//"dst_host_same_src_port_rate",
				//"srv_count",
				//"srv_diff_host_rate",
		};
	}
}
