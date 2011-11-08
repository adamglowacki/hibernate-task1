package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import pl.edu.mimuw.ag291541.tvworld.dao.TvWorkerDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;
import pl.edu.mimuw.ag291541.tvworld.entity.TvWorker;

public class HibernateTvWorkerDAO implements TvWorkerDAO {

	@Override
	public TvWorker get(Person identity, TvStation employer) {
		return (TvWorker) HibernateUtil.getSessionFactory().getCurrentSession()
				.createCriteria(TvWorker.class)
				.add(Property.forName("identity").eq(identity))
				.add(Property.forName("employer").eq(employer)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TvWorker> find(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(
				HibernateUtil.getSessionFactory().getCurrentSession()).list();
	}

	@Override
	public TvWorker create(Person identity, TvStation employer) {
		TvWorker tvWorker = new TvWorker(identity, employer);
		HibernateUtil.getSessionFactory().getCurrentSession().save(tvWorker);
		return tvWorker;
	}

	@Override
	public void delete(TvWorker tvWorker) {
		HibernateUtil.getSessionFactory().getCurrentSession().delete(tvWorker);
	}

}
