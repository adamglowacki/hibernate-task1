package pl.edu.mimuw.ag291541.tvworld.entity.dto;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import pl.edu.mimuw.ag291541.tvworld.entity.TvProduction;

public class TvProductionDTO implements Comparable<TvProductionDTO> {
	private final Long id;
	private String productionName;
	private Set<Date> airingDate;

	public TvProductionDTO(TvProduction tvProduction) {
		this.id = tvProduction.getId();
		this.productionName = tvProduction.getProductionName();
		this.airingDate = new TreeSet<Date>(tvProduction.getAiringDate());
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

	public Long getId() {
		return id;
	}

	@Override
	public int compareTo(TvProductionDTO o) {
		return getProductionName().compareTo(o.getProductionName());
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
		if (!(obj instanceof TvProductionDTO))
			return false;
		TvProductionDTO other = (TvProductionDTO) obj;
		if (productionName == null) {
			if (other.productionName != null)
				return false;
		} else if (!productionName.equals(other.productionName))
			return false;
		return true;
	}
}
