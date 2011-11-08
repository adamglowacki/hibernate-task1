package pl.edu.mimuw.ag291541.tvworld.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.dao.DAOFactory;
import pl.edu.mimuw.ag291541.tvworld.dao.DAOFactory.DAOFactoryType;
import pl.edu.mimuw.ag291541.tvworld.dao.PersonDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.TvProductionDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.TvProduction;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.PersonDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvProductionDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvWorkerDTO;

public class TvWorldServiceImpl implements TvWorldService {
	private static TvWorldServiceImpl instance = new TvWorldServiceImpl();
	private PersonDAO personDao;
	private TvProductionDAO tvProductionDao;

	private TvWorldServiceImpl() {
		DAOFactory daoFactory = DAOFactory
				.getDAOFactory(DAOFactoryType.HIBERNATE_DAO_FACTORY);
		personDao = daoFactory.getPersonDAO();
		tvProductionDao = daoFactory.getTvProductionDAO();
	}

	public static TvWorldServiceImpl getInstance() {
		return instance;
	}

	@Override
	public PersonDTO createPerson(final String name, final String surname,
			final String pesel) {
		return callInTransaction(new CallableWithResultInTransaction<PersonDTO>() {
			@Override
			public PersonDTO call() {
				return new PersonDTO(personDao.create(name, surname, pesel));
			}
		});
	}

	@Override
	public void deletePerson(final PersonDTO p) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				Person entityPerson = personDao.get(p.getId());
				personDao.delete(entityPerson);
			}
		});
	}

	@Override
	public void updatePerson(final PersonDTO p) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				Person entityPerson = personDao.get(p.getId());
				p.update(entityPerson);
			}
		});
	}

	@Override
	public List<PersonDTO> findPerson(final DetachedCriteria criteria) {
		return callInTransaction(new CallableWithResultInTransaction<List<PersonDTO>>() {
			@Override
			public List<PersonDTO> call() {
				List<Person> entityPeople = personDao.find(criteria);
				List<PersonDTO> people = new ArrayList<PersonDTO>(
						entityPeople.size());
				for (Person p : entityPeople)
					people.add(new PersonDTO(p));
				return people;
			}
		});
	}

	@Override
	public TvProductionDTO createTvProduction(String productionName,
			Set<Date> airingDate) {
		TvProduction tvProduction = tvProductionDao.create(productionName,
				new TreeSet<Date>(airingDate));
		return new TvProductionDTO(tvProduction);
	}

	@Override
	public void deleteTvProduction(TvProductionDTO tvProduction) {
		tvProductionDao.delete(tvProductionDao.get(tvProduction.getId()));
	}

	@Override
	public void updateTvProduction(TvProductionDTO tvProduction) {
		tvProduction.update(tvProductionDao.get(tvProduction.getId()));
	}

	@Override
	public List<TvProductionDTO> findTvProduction(DetachedCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStaffMemberToTvProduction(TvProductionDTO tvProduction,
			TvWorkerDTO staffMember) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeStaffMemberFromTvProduction(TvProductionDTO tvProduction,
			TvWorkerDTO staffMember) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getStaffFromTvProduction(TvProductionDTO tvProduction) {
		// TODO Auto-generated method stub

	}

	/* these are the internal helpers */

	private <R> R callInTransaction(CallableWithResultInTransaction<R> callable) {
		R result;
		try {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.beginTransaction();
			result = callable.call();
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().commit();
		} catch (RuntimeException e) {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().rollback();
			throw e;
		}
		return result;
	}

	private void callInTransaction(CallableInTransaction callable) {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.beginTransaction();
			callable.call();
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().commit();
		} catch (RuntimeException e) {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().rollback();
			throw e;
		}
	}

	private interface CallableWithResultInTransaction<R> {
		public R call();
	}

	private interface CallableInTransaction {
		public void call();
	}
}
