package pl.edu.mimuw.ag291541.tvworld.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.dao.ActorDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.DAOFactory;
import pl.edu.mimuw.ag291541.tvworld.dao.DAOFactory.DAOFactoryType;
import pl.edu.mimuw.ag291541.tvworld.dao.EpisodeDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.NewsDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.PersonDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.ReportageDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.ReporterDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.TvProductionDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.TvSeriesDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.TvStationDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.TvWorkerDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.Actor;
import pl.edu.mimuw.ag291541.tvworld.entity.Episode;
import pl.edu.mimuw.ag291541.tvworld.entity.News;
import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.Reportage;
import pl.edu.mimuw.ag291541.tvworld.entity.Reporter;
import pl.edu.mimuw.ag291541.tvworld.entity.TvProduction;
import pl.edu.mimuw.ag291541.tvworld.entity.TvSeries;
import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;
import pl.edu.mimuw.ag291541.tvworld.entity.TvWorker;
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

public class TvWorldServiceImpl implements TvWorldService {
	private static TvWorldServiceImpl instance = new TvWorldServiceImpl();
	private PersonDAO personDao;
	private TvProductionDAO tvProductionDao;
	private TvWorkerDAO tvWorkerDao;
	private ReporterDAO reporterDao;
	private TvStationDAO tvStationDao;
	private ActorDAO actorDao;
	private NewsDAO newsDao;
	private ReportageDAO reportageDao;
	private TvSeriesDAO tvSeriesDao;
	private EpisodeDAO episodeDao;

	private TvWorldServiceImpl() {
		DAOFactory daoFactory = DAOFactory
				.getDAOFactory(DAOFactoryType.HIBERNATE_DAO_FACTORY);
		personDao = daoFactory.getPersonDAO();
		tvProductionDao = daoFactory.getTvProductionDAO();
		tvWorkerDao = daoFactory.getTvWorkerDAO();
		reporterDao = daoFactory.getReporterDAO();
		tvStationDao = daoFactory.getTvStationDAO();
		actorDao = daoFactory.getActorDAO();
		newsDao = daoFactory.getNewsDAO();
		reportageDao = daoFactory.getReportageDAO();
		tvSeriesDao = daoFactory.getTvSeriesDAO();
		episodeDao = daoFactory.getEpisodeDAO();
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
				personDao.delete(getPerson(p));
			}
		});
	}

	@Override
	public void updatePerson(final PersonDTO p) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getPerson(p).update(p);
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
	public void deleteTvProduction(final TvProductionDTO tvProduction) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				tvProductionDao.delete(getTvProduction(tvProduction));
			}
		});
	}

	@Override
	public void updateTvProduction(final TvProductionDTO tvProduction) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getTvProduction(tvProduction).update(tvProduction);
			}
		});
	}

	@Override
	public List<TvProductionDTO> findTvProduction(
			final DetachedCriteria criteria) {
		return callInTransaction(new CallableWithResultInTransaction<List<TvProductionDTO>>() {
			@Override
			public List<TvProductionDTO> call() {
				List<TvProduction> entityTvProductions = tvProductionDao
						.find(criteria);
				List<TvProductionDTO> tvProductions = new ArrayList<TvProductionDTO>(
						entityTvProductions.size());
				for (TvProduction tp : entityTvProductions)
					tvProductions.add(new TvProductionDTO(tp));
				return tvProductions;
			}
		});
	}

	@Override
	public void addStaffMemberToTvProduction(
			final TvProductionDTO tvProduction, final TvWorkerDTO staffMember) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getTvProduction(tvProduction).getStaff().add(
						getTvWorker(staffMember));
			}
		});
	}

	@Override
	public void removeStaffMemberFromTvProduction(
			final TvProductionDTO tvProduction, final TvWorkerDTO staffMember) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getTvProduction(tvProduction).getStaff().remove(
						getTvWorker(staffMember));
			}
		});
	}

	@Override
	public Set<TvWorkerDTO> getStaffFromTvProduction(
			final TvProductionDTO tvProduction) {
		return callInTransaction(new CallableWithResultInTransaction<Set<TvWorkerDTO>>() {
			@Override
			public Set<TvWorkerDTO> call() {
				Set<TvWorker> entityStaff = getTvProduction(tvProduction)
						.getStaff();
				Set<TvWorkerDTO> staff = new TreeSet<TvWorkerDTO>();
				for (TvWorker tw : entityStaff)
					staff.add(new TvWorkerDTO(tw));
				return staff;
			}
		});
	}

	@Override
	public TvStationDTO createTvStation(final String name) {
		return callInTransaction(new CallableWithResultInTransaction<TvStationDTO>() {
			@Override
			public TvStationDTO call() {
				TvStation ts = tvStationDao.create(name);
				return new TvStationDTO(ts);
			}
		});
	}

	@Override
	public void deleteTvStation(final TvStationDTO tvStation) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				tvStationDao.delete(getTvStation(tvStation));
			}
		});
	}

	@Override
	public List<TvStationDTO> findTvStation(final DetachedCriteria criteria) {
		return callInTransaction(new CallableWithResultInTransaction<List<TvStationDTO>>() {
			@Override
			public List<TvStationDTO> call() {
				List<TvStation> entityTvStations = tvStationDao.find(criteria);
				List<TvStationDTO> tvStations = new ArrayList<TvStationDTO>(
						entityTvStations.size());
				for (TvStation tv : entityTvStations)
					tvStations.add(new TvStationDTO(tv));
				return tvStations;
			}
		});
	}

	@Override
	public Set<TvWorkerDTO> getTvWorkersFromTvStation(
			final TvStationDTO tvStation) {
		return callInTransaction(new CallableWithResultInTransaction<Set<TvWorkerDTO>>() {
			@Override
			public Set<TvWorkerDTO> call() {
				Set<TvWorker> entityTvWorkers = getTvStation(tvStation)
						.getWorkers();
				Set<TvWorkerDTO> tvWorkers = new TreeSet<TvWorkerDTO>();
				for (TvWorker tw : entityTvWorkers)
					tvWorkers.add(new TvWorkerDTO(tw));
				return tvWorkers;
			}
		});
	}

	@Override
	public TvWorkerDTO createTvWorker(final PersonDTO identity,
			final TvStationDTO employer) {
		return callInTransaction(new CallableWithResultInTransaction<TvWorkerDTO>() {
			@Override
			public TvWorkerDTO call() {
				return new TvWorkerDTO(tvWorkerDao.create(getPerson(identity),
						getTvStation(employer)));
			}
		});
	}

	@Override
	public void deleteTvWorker(final TvWorkerDTO tvWorker) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				tvWorkerDao.delete(getTvWorker(tvWorker));
			}
		});
	}

	@Override
	public List<TvWorkerDTO> findTvWorker(final DetachedCriteria criteria) {
		return callInTransaction(new CallableWithResultInTransaction<List<TvWorkerDTO>>() {
			@Override
			public List<TvWorkerDTO> call() {
				List<TvWorker> entityWorkers = tvWorkerDao.find(criteria);
				List<TvWorkerDTO> workers = new ArrayList<TvWorkerDTO>(
						entityWorkers.size());
				for (TvWorker tw : entityWorkers)
					workers.add(new TvWorkerDTO(tw));
				return workers;
			}
		});
	}

	@Override
	public ReporterDTO createReporter(final PersonDTO identity,
			final ReporterSpeciality speciality, final TvStationDTO employer) {
		return callInTransaction(new CallableWithResultInTransaction<ReporterDTO>() {
			@Override
			public ReporterDTO call() {
				return new ReporterDTO(reporterDao.create(getPerson(identity),
						speciality, getTvStation(employer)));
			}
		});
	}

	@Override
	public void deleteReporter(final ReporterDTO reporter) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				reporterDao.delete(getReporter(reporter));
			}
		});
	}

	@Override
	public void updateReporter(final ReporterDTO reporter) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getReporter(reporter).update(reporter);
			}
		});
	}

	@Override
	public void addReportageToReporter(final ReporterDTO reporter,
			final ReportageDTO reportage) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getReporter(reporter).getReportages().add(
						getReportage(reportage));
			}
		});
	}

	@Override
	public Set<ReportageDTO> getReportagesFromReporter(
			final ReporterDTO reporter) {
		return callInTransaction(new CallableWithResultInTransaction<Set<ReportageDTO>>() {
			@Override
			public Set<ReportageDTO> call() {
				Set<Reportage> entityReportages = getReporter(reporter)
						.getReportages();
				Set<ReportageDTO> reportages = new TreeSet<ReportageDTO>();
				for (Reportage r : entityReportages)
					reportages.add(new ReportageDTO(r));
				return reportages;
			}
		});
	}

	@Override
	public void removeReportageFromReporter(final ReporterDTO reporter,
			final ReportageDTO reportage) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getReporter(reporter).getReportages().remove(getReportage(reportage));
			}
		});
	}

	@Override
	public List<ReporterDTO> findReporter(final DetachedCriteria criteria) {
		return callInTransaction(new CallableWithResultInTransaction<List<ReporterDTO>>() {
			@Override
			public List<ReporterDTO> call() {
				List<Reporter> entityReporters = reporterDao.find(criteria);
				List<ReporterDTO> reporters = new ArrayList<ReporterDTO>(
						entityReporters.size());
				for (Reporter r : entityReporters)
					reporters.add(new ReporterDTO(r));
				return reporters;
			}
		});
	}

	@Override
	public ActorDTO createActor(final PersonDTO identity,
			final ActorRating rating, final TvStationDTO employer) {
		return callInTransaction(new CallableWithResultInTransaction<ActorDTO>() {
			@Override
			public ActorDTO call() {
				return new ActorDTO(actorDao.create(getPerson(identity),
						rating, getTvStation(employer)));
			}
		});
	}

	@Override
	public void deleteActor(final ActorDTO actor) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				actorDao.delete(getActor(actor));
			}
		});
	}

	@Override
	public void updateActor(final ActorDTO actor) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getActor(actor).update(actor);
			}
		});
	}

	@Override
	public List<ActorDTO> findActor(DetachedCriteria criteria) {
		List<Actor> entityActors = actorDao.find(criteria);
		List<ActorDTO> actors = new ArrayList<ActorDTO>(entityActors.size());
		for (Actor a : entityActors)
			actors.add(new ActorDTO(a));
		return actors;
	}

	@Override
	public NewsDTO createNews(final String productionName) {
		return callInTransaction(new CallableWithResultInTransaction<NewsDTO>() {
			@Override
			public NewsDTO call() {
				return new NewsDTO(newsDao.create(productionName));
			}
		});
	}

	@Override
	public void deleteNews(final NewsDTO news) {
		deleteTvProduction(news);
	}

	@Override
	public void updateNews(final NewsDTO news) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getNews(news).update(news);
			}
		});
	}

	@Override
	public List<NewsDTO> findNews(final DetachedCriteria criteria) {
		return callInTransaction(new CallableWithResultInTransaction<List<NewsDTO>>() {
			@Override
			public List<NewsDTO> call() {
				List<News> entityNews = newsDao.find(criteria);
				List<NewsDTO> news = new ArrayList<NewsDTO>();
				for (News n : entityNews)
					news.add(new NewsDTO(n));
				return news;
			}
		});
	}

	@Override
	public Set<ReportageDTO> getReportagesFromNews(final NewsDTO news) {
		return callInTransaction(new CallableWithResultInTransaction<Set<ReportageDTO>>() {
			@Override
			public Set<ReportageDTO> call() {
				Set<Reportage> entityReportages = getNews(news).getReportages();
				Set<ReportageDTO> reportages = new TreeSet<ReportageDTO>();
				for (Reportage r : entityReportages)
					reportages.add(new ReportageDTO(r));
				return reportages;
			}
		});
	}

	@Override
	public void addReportageToNews(final NewsDTO news,
			final ReportageDTO reportage) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getNews(news).getReportages().add(getReportage(reportage));
			}
		});
	}

	@Override
	public void removeReportageFromNews(final NewsDTO news,
			final ReportageDTO reportage) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getNews(news).getReportages().remove(getReportage(reportage));
			}
		});
	}

	@Override
	public Map<NewsDTO, Set<ReportageDTO>> presentAllNews() {
		return callInTransaction(new CallableWithResultInTransaction<Map<NewsDTO, Set<ReportageDTO>>>() {
			@Override
			public Map<NewsDTO, Set<ReportageDTO>> call() {
				List<News> entityNews = newsDao.find(DetachedCriteria
						.forClass(News.class));
				Map<NewsDTO, Set<ReportageDTO>> news = new TreeMap<NewsDTO, Set<ReportageDTO>>();
				for (News n : entityNews) {
					Set<ReportageDTO> reportages = new TreeSet<ReportageDTO>();
					for (Reportage r : n.getReportages())
						reportages.add(new ReportageDTO(r));
					news.put(new NewsDTO(n), reportages);
				}
				return news;
			}
		});
	}

	@Override
	public ReportageDTO createReportage(final String subject,
			final String content) {
		return callInTransaction(new CallableWithResultInTransaction<ReportageDTO>() {
			@Override
			public ReportageDTO call() {
				return new ReportageDTO(reportageDao.create(subject, content));
			}
		});
	}

	@Override
	public void deleteReportage(final ReportageDTO reportage) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				reportageDao.delete(getReportage(reportage));
			}
		});
	}

	@Override
	public List<ReportageDTO> findReportage(final DetachedCriteria criteria) {
		return callInTransaction(new CallableWithResultInTransaction<List<ReportageDTO>>() {
			@Override
			public List<ReportageDTO> call() {
				List<Reportage> entityReportages = reportageDao.find(criteria);
				List<ReportageDTO> reportages = new ArrayList<ReportageDTO>();
				for (Reportage r : entityReportages)
					reportages.add(new ReportageDTO(r));
				return reportages;
			}
		});
	}

	@Override
	public void updateReportage(final ReportageDTO reportage) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getReportage(reportage).update(reportage);
			}
		});
	}

	@Override
	public List<Number> getVersionsNumbersOfReportage(
			final ReportageDTO reportage) {
		return callInTransaction(new CallableWithResultInTransaction<List<Number>>() {
			@Override
			public List<Number> call() {
				return reportageDao.getVersionsNumbers(getReportage(reportage));
			}
		});
	}

	@Override
	public ReportageDTO getSpecificVersionOfReportage(
			final ReportageDTO reportage, final Number version) {
		return callInTransaction(new CallableWithResultInTransaction<ReportageDTO>() {
			@Override
			public ReportageDTO call() {
				return new ReportageDTO(
						reportageDao.getSpecificVersionOfReportage(
								getReportage(reportage), version));
			}
		});
	}

	@Override
	public TvSeriesDTO createTvSeries(final String productionName,
			final String title) {
		return callInTransaction(new CallableWithResultInTransaction<TvSeriesDTO>() {
			@Override
			public TvSeriesDTO call() {
				return new TvSeriesDTO(
						tvSeriesDao.create(productionName, title));
			}
		});
	}

	@Override
	public void deleteTvSeries(final TvSeriesDTO tvSeries) {
		/*
		 * But <code>TvSeriesDTO</code> is <code>TvProductionDTO</code>. Let's
		 * make use of it.
		 */
		deleteTvProduction(tvSeries);
	}

	@Override
	public void updateTvSeries(final TvSeriesDTO tvSeries) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getTvSeries(tvSeries).update(tvSeries);
			}
		});
		updateTvProduction(tvSeries);
	}

	@Override
	public Set<EpisodeDTO> getEpisodesFromTvSeries(final TvSeriesDTO tvSeries) {
		return callInTransaction(new CallableWithResultInTransaction<Set<EpisodeDTO>>() {
			@Override
			public Set<EpisodeDTO> call() {
				Set<Episode> entityEpisodes = getTvSeries(tvSeries)
						.getEpisodes();
				Set<EpisodeDTO> episodes = new TreeSet<EpisodeDTO>();
				for (Episode e : entityEpisodes)
					episodes.add(new EpisodeDTO(e));
				return episodes;
			}
		});
	}

	@Override
	public void addEpisodeToTvSeries(final TvSeriesDTO tvSeries,
			final EpisodeDTO episode) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getTvSeries(tvSeries).getEpisodes().add(getEpisode(episode));
			}
		});
	}

	@Override
	public void removeEpisodeFromTvSeries(final TvSeriesDTO tvSeries,
			final EpisodeDTO episode) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getTvSeries(tvSeries).getEpisodes().remove(getEpisode(episode));
			}
		});
	}

	@Override
	public List<TvSeriesDTO> getLongestByEpisodesTvSeries() {
		return callInTransaction(new CallableWithResultInTransaction<List<TvSeriesDTO>>() {
			@Override
			public List<TvSeriesDTO> call() {
				List<TvSeries> entityTvSeries = tvSeriesDao
						.findLongestByEpisodes();
				List<TvSeriesDTO> tvSeries = new ArrayList<TvSeriesDTO>(
						entityTvSeries.size());
				for (TvSeries ts : entityTvSeries)
					tvSeries.add(new TvSeriesDTO(ts));
				return tvSeries;
			}
		});
	}

	@Override
	public List<TvSeriesDTO> getLongestBySeasonsTvSeries() {
		return callInTransaction(new CallableWithResultInTransaction<List<TvSeriesDTO>>() {
			@Override
			public List<TvSeriesDTO> call() {
				List<TvSeries> entityTvSeries = tvSeriesDao
						.findLongestBySeasons();
				List<TvSeriesDTO> tvSeries = new ArrayList<TvSeriesDTO>(
						entityTvSeries.size());
				for (TvSeries ts : entityTvSeries)
					tvSeries.add(new TvSeriesDTO(ts));
				return tvSeries;
			}
		});
	}

	@Override
	public List<NewsDTO> getMostPopularNews() {
		return callInTransaction(new CallableWithResultInTransaction<List<NewsDTO>>() {
			@Override
			public List<NewsDTO> call() {
				List<News> entityNews = newsDao.getMostPopular();
				List<NewsDTO> news = new ArrayList<NewsDTO>(entityNews.size());
				for (News n : entityNews)
					news.add(new NewsDTO(n));
				return news;
			}
		});
	}

	@Override
	public List<NewsDTO> getMostPopularInAverageNews() {
		return callInTransaction(new CallableWithResultInTransaction<List<NewsDTO>>() {
			@Override
			public List<NewsDTO> call() {
				List<News> entityNews = newsDao.getMostPopularInAverage();
				List<NewsDTO> news = new ArrayList<NewsDTO>(entityNews.size());
				for (News n : entityNews)
					news.add(new NewsDTO(n));
				return news;
			}
		});
	}

	@Override
	public List<TvSeriesDTO> findTvSeries(final DetachedCriteria criteria) {
		return callInTransaction(new CallableWithResultInTransaction<List<TvSeriesDTO>>() {
			@Override
			public List<TvSeriesDTO> call() {
				List<TvSeries> entityTvSeries = tvSeriesDao.find(criteria);
				List<TvSeriesDTO> tvSeries = new ArrayList<TvSeriesDTO>(
						entityTvSeries.size());
				for (TvSeries ts : entityTvSeries)
					tvSeries.add(new TvSeriesDTO(ts));
				return null;
			}
		});
	}

	@Override
	public EpisodeDTO createEpisode(final TvSeriesDTO tvSeries,
			final long season, final long number) {
		return callInTransaction(new CallableWithResultInTransaction<EpisodeDTO>() {
			@Override
			public EpisodeDTO call() {
				return new EpisodeDTO(episodeDao.create(getTvSeries(tvSeries),
						season, number));
			}
		});
	}

	@Override
	public void deleteEpisode(final EpisodeDTO episode) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				episodeDao.delete(getEpisode(episode));
			}
		});
	}

	@Override
	public void updateEpisode(final EpisodeDTO episode) {
		callInTransaction(new CallableInTransaction() {
			@Override
			public void call() {
				getEpisode(episode).update(episode, tvSeriesDao);
			}
		});
	}

	@Override
	public List<EpisodeDTO> findEpisode(final DetachedCriteria criteria) {
		return callInTransaction(new CallableWithResultInTransaction<List<EpisodeDTO>>() {
			@Override
			public List<EpisodeDTO> call() {
				List<Episode> entityEpisodes = episodeDao.find(criteria);
				List<EpisodeDTO> episodes = new ArrayList<EpisodeDTO>(
						entityEpisodes.size());
				for (Episode e : entityEpisodes)
					episodes.add(new EpisodeDTO(e));
				return episodes;
			}
		});
	}

	/* these are the internal helpers */

	private Person getPerson(PersonDTO dto) {
		return personDao.get(dto.getId());
	}

	private TvProduction getTvProduction(TvProductionDTO dto) {
		return tvProductionDao.get(dto.getId());
	}

	private TvStation getTvStation(TvStationDTO dto) {
		return tvStationDao.get(dto.getId());
	}

	private TvWorker getTvWorker(TvWorkerDTO dto) {
		return tvWorkerDao.get(getPerson(dto.getIdentity()),
				getTvStation(dto.getEmployer()));
	}

	private Reporter getReporter(ReporterDTO dto) {
		return reporterDao.get(getPerson(dto.getIdentity()),
				getTvStation(dto.getEmployer()));
	}

	private Actor getActor(ActorDTO dto) {
		return actorDao.get(getPerson(dto.getIdentity()),
				getTvStation(dto.getEmployer()));
	}

	private News getNews(NewsDTO dto) {
		return newsDao.get(dto.getId());
	}

	private Reportage getReportage(ReportageDTO dto) {
		return reportageDao.get(dto.getId());
	}

	private TvSeries getTvSeries(TvSeriesDTO dto) {
		return tvSeriesDao.get(dto.getId());
	}

	private Episode getEpisode(EpisodeDTO dto) {
		return episodeDao.get(dto.getId());
	}

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
