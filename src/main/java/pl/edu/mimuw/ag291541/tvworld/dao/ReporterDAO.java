package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.Reporter;
import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ReporterSpeciality;

public interface ReporterDAO {
	public Reporter create(Person identity, ReporterSpeciality speciality,
			TvStation employer);

	public void delete(Reporter p);

	public Reporter get(Person identity, TvStation employer);

	public List<Reporter> find(DetachedCriteria criteria);
}
