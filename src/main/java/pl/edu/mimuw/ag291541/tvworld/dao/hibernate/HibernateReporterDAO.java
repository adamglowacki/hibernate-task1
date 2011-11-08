package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import pl.edu.mimuw.ag291541.tvworld.dao.ReporterDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.Reporter;
import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ReporterSpeciality;

public class HibernateReporterDAO implements ReporterDAO {

	@Override
	public Reporter create(Person identity, ReporterSpeciality speciality,
			TvStation employer) {
		Reporter reporter = new Reporter(identity, speciality, employer);
		HibernateUtil.getSessionFactory().getCurrentSession().save(reporter);
		return reporter;
	}

	@Override
	public void delete(Reporter p) {
		HibernateUtil.getSessionFactory().getCurrentSession().delete(p);
	}

	@Override
	public Reporter get(Person identity, TvStation employer) {
		return (Reporter) HibernateUtil.getSessionFactory().getCurrentSession()
				.createCriteria(Reporter.class)
				.add(Property.forName("identity").eq(identity))
				.add(Property.forName("employer").eq(employer)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reporter> find(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(
				HibernateUtil.getSessionFactory().getCurrentSession()).list();
	}

}
