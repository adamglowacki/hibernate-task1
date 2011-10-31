package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Collection;

public class News extends TVProduction {
	/**
	 * A number of people who watched the news.
	 */
	private long audience;
	private Collection<Reportage> reportages;

	public long getAudience() {
		return audience;
	}

	public void setAudience(long audience) {
		this.audience = audience;
	}

	public Collection<Reportage> getReportages() {
		return reportages;
	}

	public void setReportages(Collection<Reportage> reportages) {
		this.reportages = reportages;
	}
}
