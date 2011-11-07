package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public abstract class TVProduction implements Comparable<TVProduction> {
	private Long id;
	/**
	 * Name for the whole production. It is a unique business key.
	 */
	private String productionName;
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

	public String getProductionName() {
		return productionName;
	}

	public void setProductionName(String productionName) {
		this.productionName = productionName;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((productionName == null) ? 0 : productionName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TVProduction other = (TVProduction) obj;
		if (productionName == null) {
			if (other.productionName != null)
				return false;
		} else if (!productionName.equals(other.productionName))
			return false;
		return true;
	}

	@Override
	public int compareTo(TVProduction o) {
		return getProductionName().compareTo(o.getProductionName());
	}
}
