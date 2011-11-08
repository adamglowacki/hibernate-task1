package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;

public interface TvStationDAO {
	public TvStation create(String name);

	public void delete(TvStation tvStation);

	public TvStation get(Long id);

	public List<TvStation> find(DetachedCriteria criteria);
}
