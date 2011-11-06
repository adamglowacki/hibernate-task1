package pl.edu.mimuw.ag291541.tvworld.entity;

import java.io.Serializable;

/**
 * The worker hired by a specific TV station. It is connected to a physical
 * person. It is not a subclass of <code>Person</code> because we want to make
 * persons able to work for many TV stations at the same time. Because reportes
 * and actors are made by subclassing, if some person works for the TV station
 * as an actor and a reporter simultaneously, it is treated as two separate
 * workers.
 * 
 * @author adas
 * 
 */
public class TVWorker implements Serializable, Comparable<TVWorker> {
	private static final long serialVersionUID = -6590695431871353445L;
	private Person identity;
	private TVStation employer;

	public TVWorker() {

	}

	public TVWorker(Person identity, TVStation employer) {
		this.identity = identity;
		this.employer = employer;
	}

	public Person getIdentity() {
		return identity;
	}

	public void setIdentity(Person identity) {
		this.identity = identity;
	}

	public TVStation getEmployer() {
		return employer;
	}

	public void setEmployer(TVStation employer) {
		this.employer = employer;
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
		if (getClass() != obj.getClass())
			return false;
		TVWorker other = (TVWorker) obj;
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

	@Override
	public int compareTo(TVWorker o) {
		int idCmp = getIdentity().compareTo(o.getIdentity());
		int empCmp = getEmployer().compareTo(o.getEmployer());
		if (idCmp != 0) /* non-equal identities */
			return idCmp;
		else
			/* identical identities, compare by employers */
			return empCmp;
	}
}
