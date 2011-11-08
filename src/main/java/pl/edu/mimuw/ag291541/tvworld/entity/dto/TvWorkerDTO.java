package pl.edu.mimuw.ag291541.tvworld.entity.dto;

import pl.edu.mimuw.ag291541.tvworld.entity.TvWorker;

/**
 * It is an empty class because all its fields are returned by a service. But it
 * is to keep the structure clean.
 * 
 * @author adas
 * 
 */
public class TvWorkerDTO implements Comparable<TvWorkerDTO> {
	/*
	 * these fields are not lazily loaded because they are needed to provide
	 * equality and ordering.
	 */
	private final PersonDTO identity;
	private final TvStationDTO employer;

	public TvWorkerDTO(TvWorker tvWorker) {
		this.identity = new PersonDTO(tvWorker.getIdentity());
		this.employer = new TvStationDTO(tvWorker.getEmployer());
	}

	public PersonDTO getIdentity() {
		return identity;
	}

	public TvStationDTO getEmployer() {
		return employer;
	}

	@Override
	public int compareTo(TvWorkerDTO o) {
		int idCmp = getIdentity().compareTo(o.getIdentity());
		int emCmp = getEmployer().compareTo(o.getEmployer());
		if (idCmp != 0)
			return idCmp;
		else
			return emCmp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((employer == null) ? 0 : employer.hashCode());
		result = prime * result
				+ ((identity == null) ? 0 : identity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TvWorkerDTO))
			return false;
		TvWorkerDTO other = (TvWorkerDTO) obj;
		if (employer == null) {
			if (other.employer != null)
				return false;
		} else if (!employer.equals(other.employer))
			return false;
		if (identity == null) {
			if (other.identity != null)
				return false;
		} else if (!identity.equals(other.identity))
			return false;
		return true;
	}
}
