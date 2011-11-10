package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pl.edu.mimuw.ag291541.tvworld.entity.dto.NewsDTO;

public class News extends TvProduction {
	/**
	 * A number of people who watched the news.
	 */
	private List<Long> audience = new ArrayList<Long>();
	private Set<Reportage> reportages = new TreeSet<Reportage>();

	public News() {

	}

	public News(String productionName) {
		super(productionName);
	}

	public List<Long> getAudience() {
		return audience;
	}

	public void setAudience(List<Long> audience) {
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
		if (!getAudience().equals(dto.getAudience()))
			setAudience(new ArrayList<Long>(dto.getAudience()));
	}
}
