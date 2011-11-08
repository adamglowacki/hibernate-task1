package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Set;
import java.util.TreeSet;

import pl.edu.mimuw.ag291541.tvworld.entity.dto.ReporterDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ReporterSpeciality;

/**
 * A type of a TV worker. One TV worker can be of a one type at most.
 * 
 * @author adas
 * 
 */
public class Reporter extends TvWorker {
	private static final long serialVersionUID = -8030420216135307085L;
	private ReporterSpeciality speciality;
	/**
	 * All the reportages that the reported has produced.
	 */
	private Set<Reportage> reportages = new TreeSet<Reportage>();

	public Reporter() {

	}

	public Reporter(Person person, ReporterSpeciality speciality,
			TvStation employer) {
		super(person, employer);
		this.speciality = speciality;
	}

	public ReporterSpeciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(ReporterSpeciality speciality) {
		this.speciality = speciality;
	}

	public Set<Reportage> getReportages() {
		return reportages;
	}

	public void setReportages(Set<Reportage> reportages) {
		this.reportages = reportages;
	}

	public void update(ReporterDTO dto) {
		if (!getSpeciality().equals(dto))
			setSpeciality(dto.getSpeciality());
	}
}
