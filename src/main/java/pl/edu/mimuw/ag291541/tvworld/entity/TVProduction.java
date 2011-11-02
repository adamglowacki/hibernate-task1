package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public abstract class TVProduction {
	private Long id;
	private Set<Date> airingDate = new TreeSet<Date>();
	/**
	 * TV workers related to the production, besides actors and reporters.
	 */
	private Set<TVWorker> staff = new TreeSet<TVWorker>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Date> getAiringDate() {
		return airingDate;
	}

	public void setAiringDate(Set<Date> airingDate) {
		this.airingDate = airingDate;
	}

	public Set<TVWorker> getStaff() {
		return staff;
	}

	public void setStaff(Set<TVWorker> staff) {
		this.staff = staff;
	}
}
