package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.dao.TvStationDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;

public class HibernateTvStationDAO implements TvStationDAO {

	@Override
	public TvStation create(String name) {
		TvStation tvStation = new TvStation(name);
		HibernateUtil.getSessionFactory().getCurrentSession().save(tvStation);
		return tvStation;
	}

	@Override
	public void delete(TvStation tvStation) {
		HibernateUtil.getSessionFactory().getCurrentSession().delete(tvStation);
	}

	@Override
	public TvStation get(Long id) {
		return (TvStation) HibernateUtil.getSessionFactory()
				.getCurrentSession().get(TvStation.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TvStation> find(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(
				HibernateUtil.getSessionFactory().getCurrentSession()).list();
	}

}
