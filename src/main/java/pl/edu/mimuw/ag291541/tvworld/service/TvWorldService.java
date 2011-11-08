package pl.edu.mimuw.ag291541.tvworld.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.Person;

/**
 * Classes implementing this interface are intended to serve as a transactional
 * service for managing the entities of the TvWorld. This is the only class
 * (along the entities classes) provided for the use by the third-party
 * applications.
 * 
 * @author adas
 * 
 */
public interface TvWorldService {
	public Person create(String name, String surname, String pesel);

	public void delete(Person p);

	public void update(Person p);

	public List<Person> findPerson(DetachedCriteria criteria);
}
