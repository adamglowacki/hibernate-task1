package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Collection;

import pl.edu.mimuw.ag291541.tvworld.entity.type.ReporterSpeciality;


/**
 * A type of a TV worker.
 * 
 * @author adas
 * 
 */
public class Reporter extends TVWorker {
	private ReporterSpeciality speciality;
	/**
	 * All the reportages that the reported has produced.
	 */
	private Collection<Reportage> reportages;

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
