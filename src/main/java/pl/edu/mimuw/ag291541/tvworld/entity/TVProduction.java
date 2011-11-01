package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Collection;
import java.util.Date;

public abstract class TVProduction {
	private Long id;
	private Collection<Date> airingDate;
	/**
	 * TV workers related to the production, besides actors and reporters.
	 */
	private Collection<TVWorker> staff;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<Date> getAiringDate() {
		return airingDate;
	}

	public void setAiringDate(Collection<Date> airingDate) {
		this.airingDate = airingDate;
	}

	public Collection<TVWorker> getStaff() {
		return staff;
	}

	public void setStaff(Collection<TVWorker> staff) {
		this.staff = staff;
	}
}
