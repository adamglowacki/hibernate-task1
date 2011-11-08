package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.dao.TvProductionDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.TvProduction;

public class HibernateTvProductionDAO implements TvProductionDAO {

	@Override
	public void delete(TvProduction tvProduction) {
		HibernateUtil.getSessionFactory().getCurrentSession()
				.delete(tvProduction);
	}

	@Override
	public TvProduction get(Long id) {
		return (TvProduction) HibernateUtil.getSessionFactory()
				.getCurrentSession().get(TvProduction.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TvProduction> find(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(
				HibernateUtil.getSessionFactory().getCurrentSession()).list();
	}

}
