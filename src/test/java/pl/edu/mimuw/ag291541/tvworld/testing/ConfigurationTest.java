package pl.edu.mimuw.ag291541.tvworld.testing;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Property;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.ag291541.tvworld.entity.News;
import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.Reportage;
import pl.edu.mimuw.ag291541.tvworld.entity.Reporter;
import pl.edu.mimuw.ag291541.tvworld.entity.TVStation;
import pl.edu.mimuw.ag291541.tvworld.entity.TVWorker;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ReporterSpeciality;

public class ConfigurationTest {
	private static SessionFactory sessionFactory;
	private Session session;
	private static Logger logger;

	@BeforeClass
	public static void obtainLogger() {
		logger = LoggerFactory.getLogger(ConfigurationTest.class.getName());
	}

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

	@Test
	public void simpleEnversTest() {
		Reportage reportage1 = new Reportage(
				"Zielone marchewki",
				"Wszyscy uważają, że zielone marchewki są niezdrowe. Pewnie dlatego nikt ich nie je.");
		Reportage reportage2 = new Reportage(
				"Dlaczego zielone marchewki są niezdrowe?", "Któż to wie?");
		final String andrewName = "Andrew";
		final String andrewSurname = "Bolognese";
		final String joannaName = "Joanna";
		final String joannaSurname = "Flejfo";
		Person andrew = new Person(andrewName, andrewSurname);
		Person joanna = new Person(joannaName, joannaSurname);
		TVStation oldStation = new TVStation("OldStation");
		Reporter andrewReporter = new Reporter(andrew,
				ReporterSpeciality.GARDENING_SHOW, oldStation);
		Reporter joannaReporter = new Reporter(joanna,
				ReporterSpeciality.TALK_SHOW, oldStation);
		andrewReporter.getReportages().add(reportage1);
		joannaReporter.getReportages().add(reportage2);
		andrewReporter.getReportages().add(reportage2);
		session.save(oldStation);
		session.save(andrew);
		session.save(andrewReporter);
		session.save(joanna);
		session.save(joannaReporter);
		session.save(reportage1);
		Long rep2id = (Long) session.save(reportage2);
		/* reopening transaction */
		commitTransaction();
		getSessionAndBeginTransaction();
		/* --- */
		@SuppressWarnings("unchecked")
		List<Reportage> reportages = (List<Reportage>) session.createCriteria(
				Reportage.class).add(Property.forName("id").eq(rep2id));
		for (Reportage r : reportages) {
			StringBuilder reporters = new StringBuilder("|");
			for (Reporter rr : r.getReporters())
				reporters.append(rr.getIdentity().getName()).append(" ")
						.append(rr.getIdentity().getSurname()).append("|");
			StringBuilder occurrences = new StringBuilder("|");
			for (News n : r.getOccurrences())
				occurrences.append(n.getAudience()).append("|");
			logger.info(r.getSubject() + " by " + reporters.toString() + " at "
					+ occurrences.toString());
		}
	}
}
