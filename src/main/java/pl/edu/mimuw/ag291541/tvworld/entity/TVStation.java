package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Set;
import java.util.TreeSet;

/**
 * A company that employers TV workers. Notice that only workers are assigned to
 * TV stations. Other entities such as TV productions are not. They can be
 * associated with workers from many different stations.
 * 
 * @author adas
 * 
 */
public class TVStation {
	private Long id;
	private String name;
	private Set<TVWorker> workers = new TreeSet<TVWorker>();
	
	public TVStation() {
		
	}

	public TVStation(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<TVWorker> getWorkers() {
		return workers;
	}

	public void setWorkers(Set<TVWorker> workers) {
		this.workers = workers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TVStation other = (TVStation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
