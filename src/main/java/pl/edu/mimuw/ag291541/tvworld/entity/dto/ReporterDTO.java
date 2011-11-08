package pl.edu.mimuw.ag291541.tvworld.entity.dto;

import pl.edu.mimuw.ag291541.tvworld.entity.Reporter;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ReporterSpeciality;

public class ReporterDTO extends TvWorkerDTO {
	private ReporterSpeciality speciality;

	public ReporterDTO(Reporter reporter) {
		super(reporter);
		this.setSpeciality(reporter.getSpeciality());
	}

	public ReporterSpeciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(ReporterSpeciality speciality) {
		this.speciality = speciality;
	}

}
