package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

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
		int max = (Integer) HibernateUtil
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"select size(a.episodes) from TvSeries a group by a order by 1 desc")
				.setMaxResults(1).uniqueResult();
		return HibernateUtil
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from TvSeries a where size(a.episodes) = :maxsize")
				.setInteger("maxsize", max).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TvSeries> findLongestBySeasons() {
		String maxCount = "select count(distinct e.season) from TvSeries ts join ts.episodes e group by ts order by 1 desc";
		long max = (Long) HibernateUtil.getSessionFactory().getCurrentSession()
				.createQuery(maxCount).setMaxResults(1).uniqueResult();
		return HibernateUtil
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from TvSeries a where (select count(distinct b.season) from a.episodes b) = :maxsize")
				.setLong("maxsize", max).list();
	}

}
