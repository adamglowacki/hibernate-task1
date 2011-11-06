package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Collection;
import java.util.TreeSet;

import pl.edu.mimuw.ag291541.tvworld.entity.type.ReporterSpeciality;

/**
 * A type of a TV worker. One TV worker can be of a one type at most.
 * 
 * @author adas
 * 
 */
public class Reporter extends TVWorker {
	private static final long serialVersionUID = -8030420216135307085L;
	private ReporterSpeciality speciality;
	/**
	 * All the reportages that the reported has produced.
	 */
	private Collection<Reportage> reportages = new TreeSet<Reportage>();

	public Reporter() {

	}

	public Reporter(Person person, ReporterSpeciality speciality,
			TVStation employer) {
		super(person, employer);
		this.speciality = speciality;
	}

	public ReporterSpeciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(ReporterSpeciality speciality) {
		this.speciality = speciality;
	}

	public Collection<Reportage> getReportages() {
		return reportages;
	}

	public void setReportages(Collection<Reportage> reportages) {
		this.reportages = reportages;
	}
}
