package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.dao.NewsDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.News;

public class HibernateNewsDAO implements NewsDAO {

	@Override
	public News create(String productionName) {
		News news = new News(productionName);
		HibernateUtil.getSessionFactory().getCurrentSession().save(news);
		return news;
	}

	@Override
	public void delete(News news) {
		HibernateUtil.getSessionFactory().getCurrentSession().delete(news);
	}

	@Override
	public News get(Long id) {
		return (News) HibernateUtil.getSessionFactory().getCurrentSession()
				.get(News.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> getMostPopular() {
		long max = (Long) HibernateUtil.getSessionFactory().getCurrentSession()
				.createQuery("select max(c) from News b join b.audience c")
				.uniqueResult();
		return HibernateUtil
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from News n where (select max(d) from News c join c.audience d where c.id = n.id) = :max")
				.setLong("max", max).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> getMostPopularInAverage() {
		double avg = (Double) HibernateUtil
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"select avg(c) from News b join b.audience c group by b order by 1 desc")
				.setMaxResults(1).uniqueResult();
		return HibernateUtil
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from News n where (select avg(d) from News c join c.audience d where c.id = n.id) = :avg")
				.setDouble("avg", avg).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> find(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(
				HibernateUtil.getSessionFactory().getCurrentSession()).list();
	}

}
