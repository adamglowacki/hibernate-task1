package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.Person;

public interface PersonDAO {
	public Person create(String name, String surname, String pesel);

	public void delete(Person p);

	public void update(Person p);

	public List<Person> find(DetachedCriteria criteria);
}
