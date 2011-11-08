package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.dao.NewsDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.News;

public class HibernateNewsDAO implements NewsDAO {

	@Override
	public News create(String productionName, long audience) {
		News news = new News(productionName, audience);
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
	public List<News> find(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(
				HibernateUtil.getSessionFactory().getCurrentSession()).list();
	}

}
