package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.News;

public interface NewsDAO {
	public News create(String productionName);

	public void delete(News news);

	public News get(Long id);

	public List<News> getMostPopular();

	public List<News> find(DetachedCriteria criteria);

	public List<News> getMostPopularInAverage();
}
