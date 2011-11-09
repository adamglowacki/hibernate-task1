package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.Episode;
import pl.edu.mimuw.ag291541.tvworld.entity.TvSeries;

public interface EpisodeDAO {
	public Episode create(TvSeries tvSeries, long season, long number);

	public void delete(Episode episode);

	public Episode get(Long id);

	public List<Episode> find(DetachedCriteria criteria);
}
