package pl.edu.mimuw.ag291541.tvworld.entity;

public class Episode implements Comparable<Episode> {
	private Long id;
	private TVSeries tvSeries;
	private long season;
	private long number;

	public Episode() {

	}

	public Episode(TVSeries tvSeries, long season, long number) {
		this.tvSeries = tvSeries;
		this.season = season;
		this.number = number;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TVSeries getTvSeries() {
		return tvSeries;
	}

	public void setTvSeries(TVSeries tvSeries) {
		this.tvSeries = tvSeries;
	}

	public long getSeason() {
		return season;
	}

	public void setSeason(long season) {
		this.season = season;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	@Override
	public int compareTo(Episode o) {
		return getId().compareTo(o.getId());
	}
}
