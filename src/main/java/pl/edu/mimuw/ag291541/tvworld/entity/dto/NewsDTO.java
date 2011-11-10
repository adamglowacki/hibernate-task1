package pl.edu.mimuw.ag291541.tvworld.entity.dto;

import java.util.ArrayList;
import java.util.List;

import pl.edu.mimuw.ag291541.tvworld.entity.News;

public class NewsDTO extends TvProductionDTO {
	private List<Long> audience;

	public NewsDTO(News news) {
		super(news);
		this.audience = new ArrayList<Long>(news.getAudience());
	}

	public List<Long> getAudience() {
		return audience;
	}

	public void setAudience(List<Long> audience) {
		this.audience = audience;
	}
}
