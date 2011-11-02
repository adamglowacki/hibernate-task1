package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Set;
import java.util.TreeSet;

public class News extends TVProduction {
	/**
	 * A number of people who watched the news.
	 */
	private long audience;
	private Set<Reportage> reportages = new TreeSet<Reportage>();

	public News() {

	}

	public News(long audience) {
		this.audience = audience;
	}

	public long getAudience() {
		return audience;
	}

	public void setAudience(long audience) {
		this.audience = audience;
	}

	public Set<Reportage> getReportages() {
		return reportages;
	}

	public void setReportages(Set<Reportage> reportages) {
		this.reportages = reportages;
	}
}
