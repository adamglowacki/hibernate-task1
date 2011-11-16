package pl.edu.mimuw.ag291541.tvworld.testing;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Property;

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

/**
 * There are methods that instantiate data in the database and remove them after
 * every test.
 * 
 * @author adas
 * 
 */
public class DatabaseFixture {
	private static final DatabaseFixture instance = new DatabaseFixture();
	public final String johnnyName = "Johnny";
	public final String johnnySurname = "Red";
	public final String johnnyPesel = "AFGHIJK";
	public final String jamesName = "James";
	public final String jamesSurname = "Tableboard";
	public final String jamesPesel = "19121200007";
	public final String winnieName = "Winnie";
	public final String winnieSurname = "The Pooh";
	public final String winniePesel = "3243242";
	public final String eeyoreName = "Eeyore";
	public final String eeyoreSurname = "Slowy";
	public final String eeyorePesel = "5345454";
	public PersonDTO johnny;
	public PersonDTO james;
	public PersonDTO winnie;
	public PersonDTO eeyore;
	public ReporterDTO winnieReporter;
	public ReporterDTO jamesReporter;
	public ActorDTO eeyoreActor;
	public TvWorkerDTO jamesWorker;
	public final String bestStationName = "BestSTATION";
	public final String worstStationName = "TheWorst";
	public final String unusedStationName = "TheLeastUsed";
	public TvStationDTO bestStation;
	public TvStationDTO worstStation;
	public TvStationDTO unusedStation;
	public final String forestTheGreatName = "Forest The Great";
	public final String latestNewsName = "LatestNEWS";
	public final String fullNewsName = "We are not empty!";
	public final String hefalumpsName = "Really moving series";
	public final String hefalumpsTitle = "Winnie in dire straits";
	public final String veryLongStoryName = "The most expensive series";
	public final String veryLongStoryTitle = "Do not come back!";
	public TvProductionDTO forestTheGreat;
	public NewsDTO latestNews;
	public NewsDTO fullNews;
	public TvSeriesDTO hefalumps;
	public TvSeriesDTO veryLongStory;
	public final String greenApplesReportageSubject = "Why green apples are not red";
	public final String greenApplesReportageContent = "For they can't be mixed";
	public final long lastEpisodeSeason = 999;
	public final long lastEpisodeNumber = 2671;
	public ReportageDTO greenApplesReportage;
	public EpisodeDTO lastEpisode;

	private DatabaseFixture() {
	}

	public static DatabaseFixture getInstance() {
		return instance;
	}

	public void loadTestData() {
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		/* Persons */
		johnny = createPerson(s, johnnyName, johnnySurname, johnnyPesel);
		james = createPerson(s, jamesName, jamesSurname, jamesPesel);
		winnie = createPerson(s, winnieName, winnieSurname, winniePesel);
		eeyore = createPerson(s, eeyoreName, eeyoreSurname, eeyorePesel);
		/* TvStations */
		bestStation = createTvStation(s, bestStationName);
		worstStation = createTvStation(s, worstStationName);
		unusedStation = createTvStation(s, unusedStationName);
		/* TvWorkers */
		jamesWorker = createTvWorker(s, james, bestStation);
		/* Actors */
		eeyoreActor = createActor(s, eeyore, worstStation,
				ActorRating.EXCELLENT);
		/* Reporters */
		winnieReporter = createReporter(s, winnie, worstStation,
				ReporterSpeciality.WILDLIFE);
		jamesReporter = createReporter(s, james, worstStation,
				ReporterSpeciality.GARDENING_SHOW);
		/* TvProductions */
		forestTheGreat = createNews(s, forestTheGreatName);
		/* News */
		latestNews = createNews(s, latestNewsName);
		fullNews = createNews(s, fullNewsName);
		/* TvSeries */
		hefalumps = createTvSeries(s, hefalumpsName, hefalumpsTitle);
		veryLongStory = createTvSeries(s, veryLongStoryName, veryLongStoryTitle);
		/* Reportages */
		greenApplesReportage = createReportage(s, greenApplesReportageSubject,
				greenApplesReportageContent);
		addReportageToNews(s, fullNews, greenApplesReportage);
		/* Episodes */
		lastEpisode = createEpisode(s, veryLongStory, lastEpisodeSeason,
				lastEpisodeNumber);

		/* - end - */
		s.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public void clearDatabase() {
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		List<TvProduction> tvProductions = s.createCriteria(TvProduction.class)
				.list();
		for (TvProduction tp : tvProductions)
			s.delete(tp);
		List<Episode> episodes = s.createCriteria(Episode.class).list();
		for (Episode e : episodes)
			s.delete(e);
		List<Reportage> reportages = s.createCriteria(Reportage.class).list();
		for (Reportage r : reportages)
			s.delete(r);
		List<TvWorker> workers = s.createCriteria(TvWorker.class).list();
		for (TvWorker w : workers)
			s.delete(w);
		List<TvStation> stations = s.createCriteria(TvStation.class).list();
		for (TvStation ts : stations)
			s.delete(ts);
		List<Person> persons = s.createCriteria(Person.class).list();
		for (Person p : persons)
			s.delete(p);
		s.getTransaction().commit();
	}

	private PersonDTO createPerson(Session s, String name, String surname,
			String pesel) {
		Person entity = new Person(name, surname, pesel);
		s.save(entity);
		return new PersonDTO(entity);
	}

	private TvStationDTO createTvStation(Session s, String name) {
		TvStation entity = new TvStation(name);
		s.save(entity);
		return new TvStationDTO(entity);
	}

	private TvWorkerDTO createTvWorker(Session s, PersonDTO person,
			TvStationDTO station) {
		TvWorker entity = new TvWorker(getPerson(s, person), getTvStation(s,
				station));
		s.save(entity);
		return new TvWorkerDTO(entity);
	}

	private ReporterDTO createReporter(Session s, PersonDTO person,
			TvStationDTO station, ReporterSpeciality speciality) {
		Reporter entity = new Reporter(getPerson(s, person), speciality,
				getTvStation(s, station));
		s.save(entity);
		return new ReporterDTO(entity);
	}

	private ActorDTO createActor(Session s, PersonDTO person,
			TvStationDTO station, ActorRating rating) {
		Actor entity = new Actor(getPerson(s, person), rating, getTvStation(s,
				station));
		s.save(entity);
		return new ActorDTO(entity);
	}

	private TvSeriesDTO createTvSeries(Session s, String name, String title) {
		TvSeries entity = new TvSeries(name, title);
		s.save(entity);
		return new TvSeriesDTO(entity);
	}

	private NewsDTO createNews(Session s, String name) {
		News entity = new News(name);
		s.save(entity);
		return new NewsDTO(entity);
	}

	private ReportageDTO createReportage(Session s, String subject,
			String content) {
		Reportage entity = new Reportage(subject, content);
		s.save(entity);
		return new ReportageDTO(entity);
	}

	private EpisodeDTO createEpisode(Session s, TvSeriesDTO tvSeries,
			long season, long number) {
		Episode entity = new Episode(getTvSeries(s, tvSeries), season, number);
		s.save(entity);
		return new EpisodeDTO(entity);
	}

	private TvStation getTvStation(Session s, TvStationDTO station) {
		return (TvStation) s.get(TvStation.class, station.getId());
	}

	private Person getPerson(Session s, PersonDTO person) {
		return (Person) s.get(Person.class, person.getId());
	}

	private TvWorker getTvWorker(Session s, TvWorkerDTO worker) {
		return (TvWorker) s
				.createCriteria(TvWorker.class)
				.add(Property.forName("employer").eq(
						getTvStation(s, worker.getEmployer())))
				.add(Property.forName("identity").eq(
						getPerson(s, worker.getIdentity()))).uniqueResult();
	}

	private Reporter getReporter(Session s, ReporterDTO reporter) {
		return (Reporter) getTvWorker(s, reporter);
	}

	private Actor getActor(Session s, ActorDTO actor) {
		return (Actor) getTvWorker(s, actor);
	}

	private TvProduction getTvProduction(Session s, TvProductionDTO production) {
		return (TvProduction) s.get(TvProduction.class, production.getId());
	}

	private News getNews(Session s, NewsDTO news) {
		return (News) getTvProduction(s, news);
	}

	private TvSeries getTvSeries(Session s, TvSeriesDTO tvSeries) {
		return (TvSeries) getTvProduction(s, tvSeries);
	}

	private Reportage getReportage(Session s, ReportageDTO reportage) {
		return (Reportage) s.get(Reportage.class, reportage.getId());
	}

	private void addReportageToNews(Session s, NewsDTO news,
			ReportageDTO reportage) {
		getNews(s, news).getReportages().add(getReportage(s, reportage));
	}
}
