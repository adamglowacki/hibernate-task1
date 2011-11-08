package pl.edu.mimuw.ag291541.tvworld.entity.dto;

import pl.edu.mimuw.ag291541.tvworld.entity.TvSeries;

public class TvSeriesDTO extends TvProductionDTO {
	private String title;

	public TvSeriesDTO(TvSeries tvSeries) {
		super(tvSeries);
		this.title = tvSeries.getTitle();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
