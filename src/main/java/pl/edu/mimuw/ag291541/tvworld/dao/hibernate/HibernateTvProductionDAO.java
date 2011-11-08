package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.dao.TvProductionDAO;
import pl.edu.mimuw.ag291541.tvworld.entity.TvProduction;

public class HibernateTvProductionDAO implements TvProductionDAO {

	@Override
	public TvProduction create(String productionName, Set<Date> airingDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(TvProduction tvProduction) {
		// TODO Auto-generated method stub

	}

	@Override
	public TvProduction get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TvProduction> find(DetachedCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
