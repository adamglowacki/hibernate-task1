package pl.edu.mimuw.ag291541.tvworld.entity;

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
}
