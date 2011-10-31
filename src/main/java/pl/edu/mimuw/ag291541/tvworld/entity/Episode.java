package pl.edu.mimuw.ag291541.tvworld.entity;

public class Episode {
	private Long id;
	private TVSeries series;
	private long season;
	private long number;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TVSeries getSeries() {
		return series;
	}

	public void setSeries(TVSeries series) {
		this.series = series;
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
}
