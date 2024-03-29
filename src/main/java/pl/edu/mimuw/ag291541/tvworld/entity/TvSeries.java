package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Set;
import java.util.TreeSet;

import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvSeriesDTO;

public class TvSeries extends TvProduction {
	private String title;
	private Set<Episode> episodes = new TreeSet<Episode>();

	public TvSeries() {

	}

	public TvSeries(String productionName, String title) {
		super(productionName);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Set<Episode> episodes) {
		this.episodes = episodes;
	}

	public void update(TvSeriesDTO dto) {
		super.update(dto);
		if (!getTitle().equals(dto.getTitle()))
			setTitle(dto.getTitle());
	}
}