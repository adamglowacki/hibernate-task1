package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.dao.EpisodeDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.Episode;
import pl.edu.mimuw.ag291541.tvworld.entity.TvSeries;

public class HibernateEpisodeDAO implements EpisodeDAO {

	@Override
	public Episode create(TvSeries tvSeries, long season, long number) {
		Episode episode = new Episode(tvSeries, season, number);
		HibernateUtil.getSessionFactory().getCurrentSession().save(episode);
		return episode;
	}

	@Override
	public void delete(Episode episode) {
		HibernateUtil.getSessionFactory().getCurrentSession().delete(episode);
	}

	@Override
	public Episode get(Long id) {
		return (Episode) HibernateUtil.getSessionFactory().getCurrentSession()
				.get(Episode.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Episode> find(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(
				HibernateUtil.getSessionFactory().getCurrentSession()).list();
	}

}
