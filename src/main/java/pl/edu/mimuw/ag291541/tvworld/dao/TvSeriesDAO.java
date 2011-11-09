package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.TvSeries;

public interface TvSeriesDAO {
	public TvSeries create(String productionName, String title);

	public void delete(TvSeries tvSeries);

	public TvSeries get(Long id);

	public List<TvSeries> find(DetachedCriteria criteria);
}
