package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Set;
import java.util.TreeSet;

public class TVSeries extends TVProduction {
	private String title;
	private Set<Episode> episodes = new TreeSet<Episode>();
	
	public TVSeries() {
		
	}

	public TVSeries(String title) {
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
}
