package pl.edu.mimuw.ag291541.tvworld.entity.dto;

import pl.edu.mimuw.ag291541.tvworld.entity.Person;

public class PersonDTO implements Comparable<PersonDTO> {
	final Long id;
	String name;
	String surname;
	String pesel;

	public PersonDTO(Person p) {
		this.id = p.getId();
		this.name = p.getName();
		this.surname = p.getSurname();
		this.pesel = p.getPesel();
	}

	public Long getId() {
		return id;
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
	public int compareTo(PersonDTO o) {
		return getPesel().compareTo(o.getPesel());
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
		if (!(obj instanceof PersonDTO))
			return false;
		PersonDTO other = (PersonDTO) obj;
		if (pesel == null) {
			if (other.pesel != null)
				return false;
		} else if (!pesel.equals(other.pesel))
			return false;
		return true;
	}
}
