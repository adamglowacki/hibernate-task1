package pl.edu.mimuw.ag291541.tvworld.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.dao.DAOFactory;
import pl.edu.mimuw.ag291541.tvworld.dao.DAOFactory.DAOFactoryType;
import pl.edu.mimuw.ag291541.tvworld.dao.PersonDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.Person;

public class TvWorldServiceImpl implements TvWorldService {
	private static TvWorldServiceImpl instance = new TvWorldServiceImpl();
	private PersonDAO personDao;

	private TvWorldServiceImpl() {
		DAOFactory daoFactory = DAOFactory
				.getDAOFactory(DAOFactoryType.HIBERNATE_DAO_FACTORY);
		personDao = daoFactory.getPersonDAO();
	}

	public static TvWorldServiceImpl getInstance() {
		return instance;
	}

	@Override
	public Person create(String name, String surname, String pesel) {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.beginTransaction();
			Person p = personDao.create(name, surname, pesel);
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().commit();
			return p;
		} catch (RuntimeException e) {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public void delete(Person p) {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.beginTransaction();
			personDao.delete(p);
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().commit();
		} catch (RuntimeException e) {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public void update(Person p) {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.beginTransaction();
			personDao.update(p);
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().commit();
		} catch (RuntimeException e) {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public List<Person> findPerson(DetachedCriteria criteria) {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.beginTransaction();
			List<Person> people = personDao.find(criteria);
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().commit();
			return people;
		} catch (RuntimeException e) {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().rollback();
			throw e;
		}
	}

}
