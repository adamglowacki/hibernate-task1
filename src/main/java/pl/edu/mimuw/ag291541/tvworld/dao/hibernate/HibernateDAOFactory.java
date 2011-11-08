package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import pl.edu.mimuw.ag291541.tvworld.dao.DAOFactory;
import pl.edu.mimuw.ag291541.tvworld.dao.PersonDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.TvProductionDAO;

public class HibernateDAOFactory extends DAOFactory {

	@Override
	public PersonDAO getPersonDAO() {
		return new HibernatePersonDAO();
	}

	@Override
	public TvProductionDAO getTvProductionDAO() {
		return new HibernateTvProductionDAO();
	}
}
