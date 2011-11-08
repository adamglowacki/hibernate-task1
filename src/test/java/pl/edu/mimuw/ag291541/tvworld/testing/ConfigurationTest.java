package pl.edu.mimuw.ag291541.tvworld.testing;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.Reportage;
import pl.edu.mimuw.ag291541.tvworld.entity.Reporter;
import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;
import pl.edu.mimuw.ag291541.tvworld.entity.TvWorker;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ReporterSpeciality;

public class ConfigurationTest {
	private static SessionFactory sessionFactory;
	private static Logger logger;

	@BeforeClass
	public static void obtainLogger() {
		logger = LoggerFactory.getLogger(ConfigurationTest.class.getName());
	}

	@BeforeClass
	public static void acquireSessionFactory() {
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	@Before
	public void getSessionAndBeginTransaction() {
		sessionFactory.getCurrentSession().beginTransaction();
	}

	@After
	public void commitTransaction() {
		sessionFactory.getCurrentSession().getTransaction().commit();
	}

	@Test
	public void addPeopleManually() {
		Person normalPerson = new Person();
		normalPerson.setName("Jan");
		normalPerson.setSurname("Kowalski");
		TvStation station1 = new TvStation();
		station1.setName("TVP1");
		TvStation station2 = new TvStation();
		station2.setName("TVP2");
		TvWorker worker1 = new TvWorker();
		worker1.setIdentity(normalPerson);
		worker1.setEmployer(station1);
		TvWorker worker2 = new TvWorker();
		Person abnormalPerson = new Person("Jerzy", "Nowak", "blabla");
		worker2.setEmployer(station2);
		worker2.setIdentity(abnormalPerson);
		TvWorker role1 = new TvWorker();
		role1.setEmployer(station1);
		TvWorker role2 = new TvWorker();
		role2.setEmployer(station2);
		Person undecidedPerson = new Person("Joanna", "Niezdecydowana",
				"90909020912");
		role1.setIdentity(undecidedPerson);
		role2.setIdentity(undecidedPerson);
		sessionFactory.getCurrentSession().save(station1);
		sessionFactory.getCurrentSession().save(station2);
		sessionFactory.getCurrentSession().save(normalPerson);
		sessionFactory.getCurrentSession().save(abnormalPerson);
		sessionFactory.getCurrentSession().save(undecidedPerson);
		sessionFactory.getCurrentSession().save(worker1);
		LoggerFactory.getLogger(ConfigurationTest.class).info(
				worker2.toString());
		sessionFactory.getCurrentSession().save(worker2);
		sessionFactory.getCurrentSession().save(role1);
		sessionFactory.getCurrentSession().save(role2);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void simpleEnversTest() {
		final String INITIAL_REPORTAGE2_CONTENT = "Któż to wie?";
		final String SECOND_REPORTAGE2_CONTENT = INITIAL_REPORTAGE2_CONTENT
				+ " Tu byłem.";
		Reportage reportage1 = new Reportage(
				"Zielone marchewki",
				"Wszyscy uważają, że zielone marchewki są niezdrowe. Pewnie dlatego nikt ich nie je.");
		Reportage reportage2 = new Reportage(
				"Dlaczego zielone marchewki są niezdrowe?",
				INITIAL_REPORTAGE2_CONTENT);
		final String andrewName = "Andrew";
		final String andrewSurname = "Bolognese";
		final String andrewPesel = "12345678910";
		final String joannaName = "Joanna";
		final String joannaSurname = "Flejfo";
		final String joannaPesel = "01234567891";
		Person andrew = new Person(andrewName, andrewSurname, andrewPesel);
		Person joanna = new Person(joannaName, joannaSurname, joannaPesel);
		TvStation oldStation = new TvStation("OldStation");
		Reporter andrewReporter = new Reporter(andrew,
				ReporterSpeciality.GARDENING_SHOW, oldStation);
		Reporter joannaReporter = new Reporter(joanna,
				ReporterSpeciality.TALK_SHOW, oldStation);
		sessionFactory.getCurrentSession().save(oldStation);
		sessionFactory.getCurrentSession().save(andrew);
		sessionFactory.getCurrentSession().save(andrewReporter);
		sessionFactory.getCurrentSession().save(joanna);
		sessionFactory.getCurrentSession().save(joannaReporter);
		sessionFactory.getCurrentSession().save(reportage1);
		Long rep2id = (Long) sessionFactory.getCurrentSession()
				.save(reportage2);
		andrewReporter.getReportages().add(reportage1);
		joannaReporter.getReportages().add(reportage2);
		andrewReporter.getReportages().add(reportage2);

		tryToReopenTransaction();

		logInfo(sessionFactory.getCurrentSession()
				.createCriteria(Reportage.class)
				.add(Property.forName("id").eq(rep2id)).list());
		logInfo(sessionFactory.getCurrentSession()
				.createCriteria(Reportage.class).list());
		Reportage rep2 = (Reportage) sessionFactory.getCurrentSession()
				.createCriteria(Reportage.class)
				.add(Property.forName("id").eq(rep2id)).uniqueResult();
		rep2.setContent(SECOND_REPORTAGE2_CONTENT);

		tryToReopenTransaction();

		AuditReader auditReader = AuditReaderFactory.get(sessionFactory
				.getCurrentSession());
		List<Number> rep2revs = auditReader.getRevisions(Reportage.class,
				rep2id);
		Assert.assertTrue("Not enough reportage revisions found.",
				rep2revs.size() >= 2);
		Reportage rep2v1 = auditReader.find(Reportage.class, rep2id,
				rep2revs.get(rep2revs.size() - 2));
		Reportage rep2v2 = auditReader.find(Reportage.class, rep2id,
				rep2revs.get(rep2revs.size() - 1));
		Assert.assertTrue("First reportage revision has an invalid content.",
				rep2v1.getContent().equals(INITIAL_REPORTAGE2_CONTENT));
		Assert.assertTrue("Second reportage revision has an invalid content.",
				rep2v2.getContent().equals(SECOND_REPORTAGE2_CONTENT));
	}

	private void tryToReopenTransaction() {
		commitTransaction();
		getSessionAndBeginTransaction();
	}

	private void logInfo(List<Reportage> reportages) {
		for (Reportage reportage : reportages) {
			logger.info(reportage.getSubject() + " : " + reportage.getContent());
		}
	}
}
