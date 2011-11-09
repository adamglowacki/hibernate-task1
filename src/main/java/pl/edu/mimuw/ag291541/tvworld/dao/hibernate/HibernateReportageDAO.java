package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import pl.edu.mimuw.ag291541.tvworld.dao.ReportageDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.Reportage;

public class HibernateReportageDAO implements ReportageDAO {

	@Override
	public Reportage create(String subject, String content) {
		Reportage reportage = new Reportage(subject, content);
		HibernateUtil.getSessionFactory().getCurrentSession().save(reportage);
		return reportage;
	}

	@Override
	public void delete(Reportage reportage) {
		HibernateUtil.getSessionFactory().getCurrentSession().delete(reportage);
	}

	@Override
	public Reportage get(Long id) {
		return (Reportage) HibernateUtil.getSessionFactory()
				.getCurrentSession().get(Reportage.class, id);
	}

	@Override
	public List<Number> getVersionsNumbers(Reportage reportage) {
		AuditReader auditReader = AuditReaderFactory.get(HibernateUtil
				.getSessionFactory().getCurrentSession());
		return auditReader.getRevisions(Reportage.class, reportage.getId());
	}

	@Override
	public Reportage getSpecificVersionOfReportage(Reportage reportage,
			Number version) {
		AuditReader auditReader = AuditReaderFactory.get(HibernateUtil
				.getSessionFactory().getCurrentSession());
		return auditReader.find(Reportage.class, reportage.getId(), version);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reportage> find(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(
				HibernateUtil.getSessionFactory().getCurrentSession()).list();
	}

}
