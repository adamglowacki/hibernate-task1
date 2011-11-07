package pl.edu.mimuw.ag291541.tvworld.dao.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final String HIBERNATE_CFG_XML = "hibernate.cfg.xml";
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		return new Configuration().configure(HIBERNATE_CFG_XML)
				.buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
