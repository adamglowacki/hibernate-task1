package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Set;
import java.util.TreeSet;

import pl.edu.mimuw.ag291541.tvworld.entity.dto.NewsDTO;

public class News extends TvProduction {
	/**
	 * A number of people who watched the news.
	 */
	private long audience;
	private Set<Reportage> reportages = new TreeSet<Reportage>();

	public News() {

	}

	public News(String productionName, long audience) {
		super(productionName);
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

	public void update(NewsDTO dto) {
		super.update(dto);
		if (!(getAudience() != dto.getAudience()))
			setAudience(dto.getAudience());
	}
}
