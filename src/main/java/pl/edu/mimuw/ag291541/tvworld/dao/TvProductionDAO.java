package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.TvProduction;

public interface TvProductionDAO {
	public void delete(TvProduction tvProduction);

	public TvProduction get(Long id);

	public List<TvProduction> find(DetachedCriteria criteria);
}
