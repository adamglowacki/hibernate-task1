package pl.edu.mimuw.ag291541.tvworld.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.dto.PersonDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvProductionDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvWorkerDTO;

/**
 * Classes implementing this interface are intended to serve as a transactional
 * service for managing the entities of the TvWorld. This is the only class
 * (along the entities classes) provided for the use by the third-party
 * applications.
 * 
 * @author adas
 * 
 */
public interface TvWorldService {
	public PersonDTO createPerson(String name, String surname, String pesel);

	public void deletePerson(PersonDTO p);

	public void updatePerson(PersonDTO p);

	public List<PersonDTO> findPerson(DetachedCriteria criteria);

	public TvProductionDTO createTvProduction(String productionName,
			Set<Date> airingDate);

	public void deleteTvProduction(TvProductionDTO tvProduction);

	public void updateTvProduction(TvProductionDTO tvProduction);

	public List<TvProductionDTO> findTvProduction(DetachedCriteria criteria);

	public void addStaffMemberToTvProduction(TvProductionDTO tvProduction,
			TvWorkerDTO staffMember);

	public void removeStaffMemberFromTvProduction(TvProductionDTO tvProduction,
			TvWorkerDTO staffMember);

	public void getStaffFromTvProduction(TvProductionDTO tvProduction);
}
