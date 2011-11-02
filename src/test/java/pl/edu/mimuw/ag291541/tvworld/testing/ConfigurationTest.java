package pl.edu.mimuw.ag291541.tvworld.testing;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.TVStation;
import pl.edu.mimuw.ag291541.tvworld.entity.TVWorker;

public class ConfigurationTest {
	private static SessionFactory sessionFactory;
	private Session session;

	@BeforeClass
	public static void acquireSessionFactory() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		sessionFactory = cfg.buildSessionFactory();
	}

	@AfterClass
	public static void closeSessionFactory() {
		sessionFactory.close();
	}

	@Before
	public void getSessionAndBeginTransaction() {
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
	}

	@After
	public void commitTransaction() {
		session.getTransaction().commit();
	}

	@Test
	public void addPeople() {
		Person normalPerson = new Person();
		normalPerson.setName("Jan");
		normalPerson.setSurname("Kowalski");
		TVStation station1 = new TVStation();
		station1.setName("TVP1");
		TVStation station2 = new TVStation();
		station2.setName("TVP2");
		TVWorker worker1 = new TVWorker();
		worker1.setIdentity(normalPerson);
		worker1.setEmployer(station1);
		TVWorker worker2 = new TVWorker();
		Person abnormalPerson = new Person("Jerzy", "Nowak");
		worker2.setEmployer(station2);
		worker2.setIdentity(abnormalPerson);
		TVWorker role1 = new TVWorker();
		role1.setEmployer(station1);
		TVWorker role2 = new TVWorker();
		role2.setEmployer(station2);
		Person undecidedPerson = new Person("Joanna", "Niezdecydowana");
		role1.setIdentity(undecidedPerson);
		role2.setIdentity(undecidedPerson);
		session.save(station1);
		session.save(station2);
		session.save(normalPerson);
		session.save(abnormalPerson);
		session.save(undecidedPerson);
		session.save(worker1);
		LoggerFactory.getLogger(ConfigurationTest.class).info(
				worker2.toString());
		session.save(worker2);
		session.save(role1);
		session.save(role2);
	}
}
