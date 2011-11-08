package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.TvProduction;

public interface TvProductionDAO {
	public TvProduction create(String productionName, Set<Date> airingDate);

	public void delete(TvProduction tvProduction);

	public TvProduction get(Long id);

	public List<TvProduction> find(DetachedCriteria criteria);
}
