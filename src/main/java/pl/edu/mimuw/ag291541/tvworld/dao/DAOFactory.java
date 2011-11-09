package pl.edu.mimuw.ag291541.tvworld.dao;

import org.slf4j.LoggerFactory;

import pl.edu.mimuw.ag291541.tvworld.dao.hibernate.HibernateDAOFactory;

public abstract class DAOFactory {
	public static enum DAOFactoryType {
		HIBERNATE_DAO_FACTORY
	}

	public static DAOFactory getDAOFactory(DAOFactoryType factoryType) {
		switch (factoryType) {
		case HIBERNATE_DAO_FACTORY:
			return new HibernateDAOFactory();

		default:
			LoggerFactory.getLogger(DAOFactory.class).error(
					"DAO factory of an unknown type requested.");
			return null;
		}
	}

	public abstract PersonDAO getPersonDAO();

	public abstract TvProductionDAO getTvProductionDAO();

	public abstract TvWorkerDAO getTvWorkerDAO();

	public abstract TvStationDAO getTvStationDAO();

	public abstract ReporterDAO getReporterDAO();

	public abstract ActorDAO getActorDAO();

	public abstract NewsDAO getNewsDAO();

	public abstract ReportageDAO getReportageDAO();

	public abstract TvSeriesDAO getTvSeriesDAO();

	public abstract EpisodeDAO getEpisodeDAO();
}
