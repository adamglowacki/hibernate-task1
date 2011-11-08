package pl.edu.mimuw.ag291541.tvworld.entity;

import pl.edu.mimuw.ag291541.tvworld.entity.dto.PersonDTO;

/**
 * Any man in the TV world.
 * 
 * @author adas
 * 
 */
public class Person implements Comparable<Person> {
	private Long id;
	private String name;
	private String surname;
	/**
	 * It must be unique. It is used as a business key of a person.
	 */
	private String pesel;

	public Person() {
	}

	public Person(String name, String surname, String pesel) {
		this.name = name;
		this.surname = surname;
		this.pesel = pesel;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pesel == null) ? 0 : pesel.hashCode());
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
		Person other = (Person) obj;
		if (pesel == null) {
			if (other.pesel != null)
				return false;
		} else if (!pesel.equals(other.pesel))
			return false;
		return true;
	}

	@Override
	public int compareTo(Person o) {
		return getPesel().compareTo(o.getPesel());
	}

	public void update(PersonDTO p) {
		if (!getName().equals(p.getName()))
			setName(p.getName());
		if (!getSurname().equals(p.getSurname()))
			setSurname(p.getSurname());
		if (!getPesel().equals(p.getPesel()))
			setPesel(getPesel());
	}
}
