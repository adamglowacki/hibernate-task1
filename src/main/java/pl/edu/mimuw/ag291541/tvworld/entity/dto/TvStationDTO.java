package pl.edu.mimuw.ag291541.tvworld.entity.dto;

import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;

public class TvStationDTO implements Comparable<TvStationDTO> {
	private final Long id;
	private final String name;

	public TvStationDTO(TvStation station) {
		this.id = station.getId();
		this.name = station.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(TvStationDTO o) {
		return getName().compareTo(o.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TvStationDTO))
			return false;
		TvStationDTO other = (TvStationDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
