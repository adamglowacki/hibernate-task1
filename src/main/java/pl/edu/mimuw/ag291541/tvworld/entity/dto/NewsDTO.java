package pl.edu.mimuw.ag291541.tvworld.entity.dto;

import pl.edu.mimuw.ag291541.tvworld.entity.News;

public class NewsDTO extends TvProductionDTO {
	private long audience;

	public NewsDTO(News news) {
		super(news);
		this.audience = news.getAudience();
	}

	public long getAudience() {
		return audience;
	}

	public void setAudience(long audience) {
		this.audience = audience;
	}
}
