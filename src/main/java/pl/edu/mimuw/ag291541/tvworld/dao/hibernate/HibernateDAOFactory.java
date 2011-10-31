package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import pl.edu.mimuw.ag291541.tvworld.dao.DAOFactory;
import pl.edu.mimuw.ag291541.tvworld.dao.PersonDAO;

public class HibernateDAOFactory extends DAOFactory {

	@Override
	public PersonDAO getPersonDAO() {
		return new HibernatePersonDAO();
	}

}
