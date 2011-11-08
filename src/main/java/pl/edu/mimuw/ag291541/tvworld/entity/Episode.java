package pl.edu.mimuw.ag291541.tvworld.entity;

public class Episode implements Comparable<Episode> {
	private Long id;
	/**
	 * All the fields <code>tvSeries</code>, <code>season</code> and
	 * <code>number</code> are the business key.
	 */
	private TvSeries tvSeries;
	private long season;
	private long number;

	public Episode() {

	}

	public Episode(TvSeries tvSeries, long season, long number) {
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

	public TvSeries getTvSeries() {
		return tvSeries;
	}

	public void setTvSeries(TvSeries tvSeries) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (number ^ (number >>> 32));
		result = prime * result + (int) (season ^ (season >>> 32));
		result = prime * result
				+ ((tvSeries == null) ? 0 : tvSeries.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Episode))
			return false;
		Episode other = (Episode) obj;
		if (number != other.number)
			return false;
		if (season != other.season)
			return false;
		if (tvSeries == null) {
			if (other.tvSeries != null)
				return false;
		} else if (!tvSeries.equals(other.tvSeries))
			return false;
		return true;
	}

	@Override
	public int compareTo(Episode o) {
		int seriesCmp = getTvSeries().compareTo(o.getTvSeries());
		int seasonCmp = new Long(getSeason())
				.compareTo(new Long(o.getSeason()));
		int numberCmp = new Long(getNumber())
				.compareTo(new Long(o.getNumber()));
		if (seriesCmp != 0)
			return seriesCmp;
		else if (seasonCmp != 0)
			return seasonCmp;
		else
			return numberCmp;
	}
}
