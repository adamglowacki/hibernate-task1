package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import pl.edu.mimuw.ag291541.tvworld.dao.ActorDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.DAOFactory;
import pl.edu.mimuw.ag291541.tvworld.dao.NewsDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.PersonDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.ReportageDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.ReporterDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.TvProductionDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.TvStationDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.TvWorkerDAO;

public class HibernateDAOFactory extends DAOFactory {

	@Override
	public PersonDAO getPersonDAO() {
		return new HibernatePersonDAO();
	}

	@Override
	public TvProductionDAO getTvProductionDAO() {
		return new HibernateTvProductionDAO();
	}

	@Override
	public TvWorkerDAO getTvWorkerDAO() {
		return new HibernateTvWorkerDAO();
	}

	@Override
	public TvStationDAO getTvStationDAO() {
		return new HibernateTvStationDAO();
	}

	@Override
	public ReporterDAO getReporterDAO() {
		return new HibernateReporterDAO();
	}

	@Override
	public ActorDAO getActorDAO() {
		return new HibernateActorDAO();
	}

	@Override
	public NewsDAO getNewsDAO() {
		return new HibernateNewsDAO();
	}

	@Override
	public ReportageDAO getReportageDAO() {
		return new HibernateReportageDAO();
	}
}
