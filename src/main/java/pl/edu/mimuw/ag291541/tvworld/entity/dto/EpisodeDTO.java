package pl.edu.mimuw.ag291541.tvworld.entity.dto;

import pl.edu.mimuw.ag291541.tvworld.entity.Episode;

public class EpisodeDTO implements Comparable<EpisodeDTO> {
	private final Long id;
	/*
	 * the ordering and equality depend upon this field so this is not lazily
	 * loaded
	 */
	private TvSeriesDTO tvSeries;
	private long season;
	private long number;

	public EpisodeDTO(Episode episode) {
		this.id = episode.getId();
		this.tvSeries = new TvSeriesDTO(episode.getTvSeries());
		this.season = episode.getSeason();
		this.number = episode.getNumber();
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

	public Long getId() {
		return id;
	}

	public TvSeriesDTO getTvSeries() {
		return tvSeries;
	}

	public void setTvSeries(TvSeriesDTO tvSeries) {
		this.tvSeries = tvSeries;
	}

	@Override
	public int compareTo(EpisodeDTO o) {
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
		if (!(obj instanceof EpisodeDTO))
			return false;
		EpisodeDTO other = (EpisodeDTO) obj;
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
}
