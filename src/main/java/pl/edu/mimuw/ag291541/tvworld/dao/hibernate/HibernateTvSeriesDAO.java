package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import pl.edu.mimuw.ag291541.tvworld.dao.TvSeriesDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.TvSeries;

public class HibernateTvSeriesDAO implements TvSeriesDAO {

	@Override
	public TvSeries create(String productionName, String title) {
		TvSeries tvSeries = new TvSeries(productionName, title);
		HibernateUtil.getSessionFactory().getCurrentSession().save(tvSeries);
		return tvSeries;
	}

	@Override
	public void delete(TvSeries tvSeries) {
		HibernateUtil.getSessionFactory().getCurrentSession().delete(tvSeries);
	}

	@Override
	public TvSeries get(Long id) {
		return (TvSeries) HibernateUtil.getSessionFactory().getCurrentSession()
				.get(TvSeries.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TvSeries> find(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(
				HibernateUtil.getSessionFactory().getCurrentSession()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TvSeries> findLongestByEpisodes() {
		Criteria crit = HibernateUtil.getSessionFactory().getCurrentSession()
				.createCriteria(TvSeries.class);
		crit.setProjection(Projections.projectionList().add(
				Projections.count("episodes")));
		long max = (Long) crit.uniqueResult();
		return HibernateUtil
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from TvSeries a where size(a.episodes) = :maxsize")
				.setLong("maxsize", max).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TvSeries> findLongestBySeasons() {
		Criteria crit = HibernateUtil.getSessionFactory().getCurrentSession()
				.createCriteria(TvSeries.class)
				.setProjection(Projections.property("episodes"));
		crit.setProjection(Projections.projectionList().add(
				Projections.countDistinct("season")));
		long max = (Long) crit.uniqueResult();
		return HibernateUtil
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from TvSeries a where size(a.episodes) = :maxsize")
				.setLong("maxsize", max).list();
	}

}
