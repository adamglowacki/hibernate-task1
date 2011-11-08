package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.dao.PersonDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.Person;

public class HibernatePersonDAO implements PersonDAO {

	@Override
	public Person create(String name, String surname, String pesel) {
		Person p = new Person(name, surname, pesel);
		HibernateUtil.getSessionFactory().getCurrentSession().save(p);
		return p;
	}

	@Override
	public void delete(Person p) {
		HibernateUtil.getSessionFactory().getCurrentSession().delete(p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> find(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(
				HibernateUtil.getSessionFactory().getCurrentSession()).list();
	}

	@Override
	public Person get(Long id) {
		return (Person) HibernateUtil.getSessionFactory().getCurrentSession()
				.get(Person.class, id);
	}

}
