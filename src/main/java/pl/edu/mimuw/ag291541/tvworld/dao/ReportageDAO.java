package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.Reportage;

public interface ReportageDAO {
	public Reportage create(String subject, String content);

	public void delete(Reportage reportage);

	public Reportage get(Long id);

	public List<Number> getVersionsNumbers(Reportage reportage);

	public Reportage getSpecificVersionOfReportage(Reportage reportage,
			Number version);

	public List<Reportage> find(DetachedCriteria criteria);
}
