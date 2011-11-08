package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;
import pl.edu.mimuw.ag291541.tvworld.entity.TvWorker;

public interface TvWorkerDAO {
	public TvWorker get(Person identity, TvStation employer);

	public List<TvWorker> find(DetachedCriteria criteria);
}
