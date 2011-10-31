package pl.edu.mimuw.ag291541.tvworld.entity;

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
public class TVWorker {
	private Person identity;
	private TVStation employer;

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
}
