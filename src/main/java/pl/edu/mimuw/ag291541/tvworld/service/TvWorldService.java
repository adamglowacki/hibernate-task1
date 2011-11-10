package pl.edu.mimuw.ag291541.tvworld.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.dto.ActorDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.EpisodeDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.NewsDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.PersonDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.ReportageDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.ReporterDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvProductionDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvSeriesDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvStationDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvWorkerDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ActorRating;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ReporterSpeciality;

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

	public void deleteTvProduction(TvProductionDTO tvProduction);

	public void updateTvProduction(TvProductionDTO tvProduction);

	public List<TvProductionDTO> findTvProduction(DetachedCriteria criteria);

	public void addStaffMemberToTvProduction(TvProductionDTO tvProduction,
			TvWorkerDTO staffMember);

	public void removeStaffMemberFromTvProduction(TvProductionDTO tvProduction,
			TvWorkerDTO staffMember);

	public Set<TvWorkerDTO> getStaffFromTvProduction(
			TvProductionDTO tvProduction);

	public TvStationDTO createTvStation(String name);

	public void deleteTvStation(TvStationDTO tvStation);

	public List<TvStationDTO> findTvStation(DetachedCriteria criteria);

	/**
	 * Returns a set of all the workers associated with the station. It is not
	 * allowed to add/remove workers from the station just by adding/removing
	 * them from this collection. The workers are <i>de facto</i> join table
	 * between persons and stations. So to make somebody work for a station you
	 * need to create an appropriate worker object. You also fire people by
	 * removing the worker objects.
	 * 
	 * @param tvStation
	 *            The station that hires the workers.
	 * @return The set of all workers hired by the <code>tvStation</code>.
	 */
	public Set<TvWorkerDTO> getTvWorkersFromTvStation(TvStationDTO tvStation);

	public TvWorkerDTO createTvWorker(PersonDTO identity, TvStationDTO employer);

	public void deleteTvWorker(TvWorkerDTO tvWorker);

	public List<TvWorkerDTO> findTvWorker(DetachedCriteria criteria);

	public ReporterDTO createReporter(PersonDTO identity,
			ReporterSpeciality speciality, TvStationDTO employer);

	public void deleteReporter(ReporterDTO reporter);

	public void updateReporter(ReporterDTO reporter);

	public Set<ReportageDTO> getReportagesFromReporter(ReporterDTO reporter);

	public void addReportageToReporter(ReporterDTO reporter,
			ReportageDTO reportage);

	public void removeReportageFromReporter(ReporterDTO reporter,
			ReportageDTO reportage);

	public List<ReporterDTO> findReporter(DetachedCriteria criteria);

	public ActorDTO createActor(PersonDTO identity, ActorRating rating,
			TvStationDTO employer);

	public void deleteActor(ActorDTO actor);

	public void updateActor(ActorDTO actor);

	public List<ActorDTO> findActor(DetachedCriteria criteria);

	public NewsDTO createNews(String productionName);

	public void deleteNews(NewsDTO news);

	public void updateNews(NewsDTO news);

	/**
	 * Presents all the news with the current versions of their reportages.
	 * 
	 * @return A map from all the news objects to their current reportages.
	 */
	public Map<NewsDTO, Set<ReportageDTO>> presentAllNews();

	public List<NewsDTO> getMostPopularNews();

	public List<NewsDTO> getMostPopularInAverageNews();

	public List<NewsDTO> findNews(DetachedCriteria criteria);

	public Set<ReportageDTO> getReportagesFromNews(NewsDTO news);

	public void addReportageToNews(NewsDTO news, ReportageDTO reportage);

	public void removeReportageFromNews(NewsDTO news, ReportageDTO reportage);

	public ReportageDTO createReportage(String subject, String content);

	public void deleteReportage(ReportageDTO reportage);

	public void updateReportage(ReportageDTO reportage);

	public List<Number> getVersionsNumbersOfReportage(ReportageDTO reportage);

	public ReportageDTO getSpecificVersionOfReportage(ReportageDTO reportage,
			Number version);

	public List<ReportageDTO> findReportage(DetachedCriteria criteria);

	public TvSeriesDTO createTvSeries(String productionName, String title);

	public void deleteTvSeries(TvSeriesDTO tvSeries);

	public void updateTvSeries(TvSeriesDTO tvSeries);

	public Set<EpisodeDTO> getEpisodesFromTvSeries(TvSeriesDTO tvSeries);

	public void moveEpisodeToTvSeries(TvSeriesDTO tvSeries, EpisodeDTO episode);

	public void removeEpisodeFromTvSeries(TvSeriesDTO tvSeries,
			EpisodeDTO episode);

	public List<TvSeriesDTO> getLongestBySeasonsTvSeries();

	public List<TvSeriesDTO> getLongestByEpisodesTvSeries();

	public List<TvSeriesDTO> findTvSeries(DetachedCriteria criteria);

	public EpisodeDTO createEpisode(TvSeriesDTO tvSeries, long season,
			long number);

	public void deleteEpisode(EpisodeDTO episode);

	public void updateEpisode(EpisodeDTO episode);

	public List<EpisodeDTO> findEpisode(DetachedCriteria criteria);
}
