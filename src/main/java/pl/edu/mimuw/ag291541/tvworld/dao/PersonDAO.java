package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.Person;

public interface PersonDAO {
	public Person create(String name, String surname, String pesel);

	public void delete(Person p);

	public Person get(Long id);

	public List<Person> find(DetachedCriteria criteria);
}
