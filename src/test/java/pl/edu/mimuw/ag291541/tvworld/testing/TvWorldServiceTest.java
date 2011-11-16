package pl.edu.mimuw.ag291541.tvworld.testing;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import pl.edu.mimuw.ag291541.tvworld.service.TvWorldService;
import pl.edu.mimuw.ag291541.tvworld.service.TvWorldServiceFactory;

public class TvWorldServiceTest {
	private static TvWorldService service;
	private static final DatabaseFixture fixture = DatabaseFixture
			.getInstance();
	private static final Logger logger = LoggerFactory
			.getLogger(TvWorldServiceTest.class);

	@BeforeClass
	public static void obtainServiceObject() {
		service = TvWorldServiceFactory.getInstance().getService();
	}

	@Before
	public void loadTestData() {
		fixture.loadTestData();
	}

	@After
	public void clearDatabase() {
		fixture.clearDatabase();
	}

	@Test
	public void createAndRetrievePerson() {
		final String johnName = "John", johnSurname = "White", johnPesel = "1234";
		PersonDTO john = service.createPerson(johnName, johnSurname, johnPesel);
		PersonDTO retrievedJohn = getCurrentPerson(john);
		assertTrue(retrievedJohn.equals(john));
		logger.info("Creating persons works.");
	}

	@Test
	public void updatePerson() {
		fixture.james.setName(fixture.james.getName() + "45");
		service.updatePerson(fixture.james);
		PersonDTO jamesByService = getCurrentPerson(fixture.james);
		assertTrue(jamesByService.getName().equals(fixture.james.getName()));
		logger.info("Updating persons works.");
	}

	@Test
	public void deletePerson() {
		service.deletePerson(fixture.johnny);
		PersonDTO james2 = getCurrentPerson(fixture.johnny);
		assertTrue(james2 == null);
		logger.info("Deleting persons works.");
	}

	@Test
	public void deleteTvWorker() {
		service.deleteTvWorker(fixture.jamesWorker);
		assertTrue(getCurrentTvWorker(fixture.jamesWorker) == null);
		logger.info("Deleting TV workers works.");
	}

	@Test
	public void findPersonsByCriteria() {
		final Set<Long> people = new TreeSet<Long>();
		people.add(fixture.james.getId());
		people.add(fixture.johnny.getId());
		final DetachedCriteria criteria = DetachedCriteria.forClass(
				Person.class).add(Property.forName("id").in(people));
		List<PersonDTO> persons = service.findPerson(criteria);
		Assert.assertTrue(persons.size() == people.size());
		for (PersonDTO p : persons)
			Assert.assertTrue(people.contains(p.getId()));
		logger.info("Finding persons by criteria works.");
	}

	@Test
	public void createAndRetrieveTvStation() {
		final String name = "NewTV";
		TvStationDTO station = service.createTvStation(name);
		TvStationDTO retrievedStation = getCurrentTvStation(station);
		Assert.assertTrue(retrievedStation.equals(station));
		logger.info("Creating TV station works.");
	}

	@Test
	public void deleteTvStation() {
		service.deleteTvStation(fixture.unusedStation);
		assertTrue(getCurrentTvStation(fixture.unusedStation) == null);
		logger.info("Deleting TV stations works.");
	}

	@Test
	public void deleteTvProduction() {
		service.deleteTvProduction(fixture.forestTheGreat);
		assertTrue(getCurrentTvProduction(fixture.forestTheGreat) == null);
		logger.info("Deleting TV productions works.");
	}

	@Test
	public void deleteReporter() {
		service.deleteReporter(fixture.winnieReporter);
		assertTrue(getCurrentReporter(fixture.winnieReporter) == null);
		logger.info("Deleting reporters is ok.");
	}

	@Test
	public void updateReporter() {
		fixture.winnieReporter.setSpeciality(ReporterSpeciality.DOCUMENTARY);
		service.updateReporter(fixture.winnieReporter);
		assertTrue(getCurrentReporter(fixture.winnieReporter).getSpeciality()
				.equals(fixture.winnieReporter.getSpeciality()));
		logger.info("Updating reporters is ok.");
	}

	@Test
	public void deleteActor() {
		service.deleteActor(fixture.eeyoreActor);
		assertTrue(getCurrentActor(fixture.eeyoreActor) == null);
		logger.info("Deleting actors is ok.");
	}

	@Test
	public void updateActor() {
		fixture.eeyoreActor.setRating(ActorRating.GOOD);
		service.updateActor(fixture.eeyoreActor);
		assertTrue(getCurrentActor(fixture.eeyoreActor).getRating().equals(
				fixture.eeyoreActor.getRating()));
		logger.info("Updating actors is ok.");
	}

	@Test
	public void deleteNews() {
		service.deleteNews(fixture.latestNews);
		assertTrue(getCurrentNews(fixture.latestNews) == null);
		logger.info("Deleting news is ok.");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void updateNews() {
		fixture.latestNews.getAiringDate().add(new Date(2009, 7, 1));
		service.updateNews(fixture.latestNews);
		assertTrue(getCurrentNews(fixture.latestNews).getAiringDate().equals(
				fixture.latestNews.getAiringDate()));
		logger.info("Updating news is ok.");
	}

	@Test
	public void updateTvSeries() {
		fixture.hefalumps.setTitle(fixture.hefalumps.getTitle() + " 2");
		service.updateTvSeries(fixture.hefalumps);
		assertTrue(getCurrentTvSeries(fixture.hefalumps).getTitle().equals(
				fixture.hefalumps.getTitle()));
		logger.info("Updating TvSeries works.");
	}

	/**
	 * Here we try to delete a reportage that is contained by one of the news.
	 * We decided it should not pass.
	 */
	@Test(expected = RuntimeException.class)
	public void deleteReportageAssociatedWithNews() {
		service.deleteReportage(fixture.greenApplesReportage);
		assertTrue(false);
		logger.error("Deleting reportage contained by news succeeded!");
	}

	@Test
	public void deleteEpisode() {
		service.deleteEpisode(fixture.lastEpisode);
		assertTrue(getCurrentEpisode(fixture.lastEpisode) == null);
		logger.info("Deleting episodes is ok.");
	}

	@Test
	public void updateEpisode() {
		TvSeriesDTO tvSeries = service.createTvSeries("Produkcja DRRETEWER",
				"2344432");
		EpisodeDTO e = service.createEpisode(tvSeries, 11, 314);
		e.setNumber(e.getNumber() + 1);
		e.setSeason(e.getSeason() + 11);
		service.updateEpisode(e);
		DetachedCriteria getEpisode = DetachedCriteria.forClass(Episode.class)
				.add(Property.forName("id").eq(e.getId()));
		List<EpisodeDTO> episodes = service.findEpisode(getEpisode);
		Assert.assertTrue(episodes.size() == 1);
		EpisodeDTO eByService = episodes.get(0);
		Assert.assertTrue(e.getNumber() == eByService.getNumber());
		Assert.assertTrue(e.getSeason() == eByService.getSeason());
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Updating episodes is ok.");
	}

	@Test
	public void findEpisode() {
		TvSeriesDTO tvSeries = service.createTvSeries("Produkcja ADSASDADS",
				"0909000");
		EpisodeDTO e = service.createEpisode(tvSeries, 111, 3114);
		DetachedCriteria getEpisode = DetachedCriteria.forClass(Episode.class)
				.add(Property.forName("id").eq(e.getId()));
		List<EpisodeDTO> episodes = service.findEpisode(getEpisode);
		Assert.assertTrue(episodes.size() == 1);
		EpisodeDTO eByService = episodes.get(0);
		Assert.assertTrue(e.getNumber() == eByService.getNumber());
		Assert.assertTrue(e.getSeason() == eByService.getSeason());
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Finding episodes is ok.");
	}

	@Test
	public void retrieveWorkersFromTvStationSide() {
		TvStationDTO tdz = service.createTvStation("Telewizja Dla Zielonych");
		final String wincentyName = "Wincenty", wincentySurname = "Kapusta", wincentyPesel = "181920212322";
		PersonDTO wincenty = service.createPerson(wincentyName,
				wincentySurname, wincentyPesel);
		final String jacekName = "Jacek", jacekSurname = "Rybołówek", jacekPesel = "12AX798Y";
		PersonDTO jacek = service.createPerson(jacekName, jacekSurname,
				jacekPesel);
		final String joasiaName = "Joasia", joasiaSurname = "Brzęczyk", joasiaPesel = "007";
		PersonDTO joasia = service.createPerson(joasiaName, joasiaSurname,
				joasiaPesel);
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Persons created.");
		TvWorkerDTO wincenty4Tdz = service.createTvWorker(wincenty, tdz);
		TvWorkerDTO jacek4Tdz = service.createTvWorker(jacek, tdz);
		ReporterDTO joasia4Tdz = service.createReporter(joasia,
				ReporterSpeciality.WILDLIFE, tdz);
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Workers, actors and reporters created.");
		Set<TvWorkerDTO> tdzWorkersByService = service
				.getTvWorkersFromTvStation(tdz);
		Set<TvWorkerDTO> tdzWorkersByUs = new TreeSet<TvWorkerDTO>();
		tdzWorkersByUs.add(jacek4Tdz);
		tdzWorkersByUs.add(wincenty4Tdz);
		tdzWorkersByUs.add(joasia4Tdz);
		Assert.assertTrue(tdzWorkersByUs.equals(tdzWorkersByService));
		LoggerFactory
				.getLogger(TvWorldServiceTest.class)
				.info("Saving and retrieving workers from the TV station side works.");
	}

	@Test
	public void fireWorkerFromStation() {
		TvStationDTO tdf = service.createTvStation("Telewizja Dla Fioletowych");
		final String jackName = "Jack", jackSurname = "Ulrich", jackPesel = "112AX798Y";
		PersonDTO jack = service.createPerson(jackName, jackSurname, jackPesel);
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Persons created.");
		ReporterDTO jacek4Tdf = service.createReporter(jack,
				ReporterSpeciality.GARDENING_SHOW, tdf);
		service.deleteTvWorker(jacek4Tdf);
		Set<TvWorkerDTO> tdfWorkersByService = service
				.getTvWorkersFromTvStation(tdf);
		Set<TvWorkerDTO> tdfWorkersByUs = new TreeSet<TvWorkerDTO>();
		tdfWorkersByService = service.getTvWorkersFromTvStation(tdf);
		Assert.assertTrue(tdfWorkersByService.equals(tdfWorkersByUs));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Worker fired successfully.");
	}

	@Test
	public void testTvProductions() {
		TvStationDTO stat3 = service.createTvStation("Stat3");
		PersonDTO winnie = service.createPerson("Winnie", "the Pooh",
				"1209348756");
		ReporterDTO winnieReporter = service.createReporter(winnie,
				ReporterSpeciality.TALK_SHOW, stat3);
		PersonDTO kanga = service.createPerson("Kanga", "Longnecked",
				"12346789");
		ReporterDTO kangaReporter = service.createReporter(kanga,
				ReporterSpeciality.GARDENING_SHOW, stat3);
		PersonDTO eeyore = service.createPerson("Eeyore", "Grey-Donkey",
				"11111");
		ActorDTO eeyoreActor = service.createActor(eeyore,
				ActorRating.EXCELLENT, stat3);
		PersonDTO piglet = service.createPerson("Piglet", "Small", "8798");
		TvWorkerDTO pigletWorker = service.createTvWorker(piglet, stat3);
		NewsDTO news = service.createNews("Super-Express");
		ReportageDTO reportage1 = service
				.createReportage(
						"Trees in a forest",
						"Have you ever wondered how many trees grow in an average forest? Is it more than hundred...");
		ReportageDTO reportage2 = service
				.createReportage(
						"Honey jars",
						"The Java programmers have found out that all JARs before you look into are full of honey.");
		service.addReportageToReporter(kangaReporter, reportage1);
		service.addReportageToReporter(winnieReporter, reportage1);
		service.addReportageToReporter(winnieReporter, reportage2);
		Set<ReportageDTO> winnieReportagesByService = service
				.getReportagesFromReporter(winnieReporter);
		Set<ReportageDTO> winnieReportagesByUs = new TreeSet<ReportageDTO>();
		winnieReportagesByUs.add(reportage1);
		winnieReportagesByUs.add(reportage2);
		Assert.assertTrue(winnieReportagesByService
				.equals(winnieReportagesByUs));
		service.removeReportageFromReporter(winnieReporter, reportage2);
		winnieReportagesByUs.remove(reportage2);
		winnieReportagesByService = service
				.getReportagesFromReporter(winnieReporter);
		Assert.assertTrue(winnieReportagesByUs
				.equals(winnieReportagesByService));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Modifying reportages of reporter collection succeeded.");
		service.addReportageToNews(news, reportage1);
		service.addReportageToNews(news, reportage2);
		Set<ReportageDTO> newsReportagesByService = service
				.getReportagesFromNews(news);
		Set<ReportageDTO> newsReportagesByUs = new TreeSet<ReportageDTO>();
		newsReportagesByUs.add(reportage1);
		newsReportagesByUs.add(reportage2);
		Assert.assertTrue(newsReportagesByUs.equals(newsReportagesByService));
		service.removeReportageFromNews(news, reportage1);
		newsReportagesByService = service.getReportagesFromNews(news);
		newsReportagesByUs.remove(reportage1);
		Assert.assertTrue(newsReportagesByUs.equals(newsReportagesByService));
		/*
		 * <code>winnie</code> does work also as another worker while
		 * <code>kanga</code> only produces reportages that are used in
		 * <code>news</code>.
		 */
		service.addStaffMemberToTvProduction(news, winnieReporter);
		service.addStaffMemberToTvProduction(news, pigletWorker);
		service.addStaffMemberToTvProduction(news, eeyoreActor);
		Set<TvWorkerDTO> newsWorkersByService = service
				.getStaffFromTvProduction(news);
		Set<TvWorkerDTO> newsWorkersByUs = new TreeSet<TvWorkerDTO>();
		newsWorkersByUs.add(winnieReporter);
		newsWorkersByUs.add(pigletWorker);
		newsWorkersByUs.add(eeyoreActor);
		Assert.assertTrue(newsWorkersByService.equals(newsWorkersByUs));
		service.removeStaffMemberFromTvProduction(news, winnieReporter);
		newsWorkersByService = service.getStaffFromTvProduction(news);
		newsWorkersByUs.remove(winnieReporter);
		Assert.assertTrue(newsWorkersByUs.equals(newsWorkersByService));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Adding/removing workers to TV production is ok.");
		TvSeriesDTO tvSeries3 = service.createTvSeries("BGTHW12",
				"Forest, Big Forest");
		EpisodeDTO episode3_1_1 = service.createEpisode(tvSeries3, 1, 1);
		EpisodeDTO episode3_1_2 = service.createEpisode(tvSeries3, 1, 2);
		EpisodeDTO episode3_19_2 = service.createEpisode(tvSeries3, 19, 2);
		Set<EpisodeDTO> episodesByService = service
				.getEpisodesFromTvSeries(tvSeries3);
		Set<EpisodeDTO> episodesByUs = new TreeSet<EpisodeDTO>();
		episodesByUs.add(episode3_1_1);
		episodesByUs.add(episode3_19_2);
		episodesByUs.add(episode3_1_2);
		Assert.assertTrue(episodesByUs.equals(episodesByService));
		service.removeEpisodeFromTvSeries(tvSeries3, episode3_1_2);
		episodesByUs.remove(episode3_1_2);
		episodesByService = service.getEpisodesFromTvSeries(tvSeries3);
		Assert.assertTrue(episodesByService.equals(episodesByUs));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Adding and removing episodes from TV series works.");
		service.deleteTvSeries(tvSeries3);
		List<TvSeriesDTO> nonExistingTvSeries = service
				.findTvSeries(DetachedCriteria.forClass(TvSeries.class).add(
						Property.forName("id").eq(tvSeries3.getId())));
		Assert.assertTrue(nonExistingTvSeries == null
				|| nonExistingTvSeries.size() == 0);
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Deleting TV series works.");
	}

	@Test
	public void moveEpisodeToTvSeries() {
		TvSeriesDTO tvSeries = service.createTvSeries("Kolejny serial",
				"Serial 4562");
		EpisodeDTO episode = service.createEpisode(tvSeries, 1, 3);
		TvSeriesDTO otherTvSeries = service.createTvSeries("Nowy serial",
				"Miś uszatek");
		service.moveEpisodeToTvSeries(otherTvSeries, episode);
		LoggerFactory
				.getLogger(TvWorldServiceTest.class)
				.info("Episode has been moved from one TV series to another successfully.");
	}

	@Test
	public void findReportage() {
		ReportageDTO reportage1 = service.createReportage(
				"Reportaż do znalezienia", "Tu nic ciekawego nie ma.");
		DetachedCriteria getReportage1 = DetachedCriteria.forClass(
				Reportage.class).add(
				Property.forName("id").eq(reportage1.getId()));
		List<ReportageDTO> reportages = service.findReportage(getReportage1);
		Assert.assertTrue(reportages.size() == 1);
		Assert.assertTrue(reportages.contains(reportage1));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Finding reportages seems to be ok.");
	}

	@Test
	public void testReportageVersions() {
		TvStationDTO stat4 = service.createTvStation("Stat4");
		final String poName = "Po", poSurname = "Li Chi", poPesel = "AHJ78_7";
		PersonDTO po = service.createPerson(poName, poSurname, poPesel);
		ReporterDTO poReporter = service.createReporter(po,
				ReporterSpeciality.WILDLIFE, stat4);
		ReportageDTO reportage1 = service.createReportage("Che Chu Hinchi!",
				"Che Chi Hinchu");
		final String peName = "Pe", peSurname = "Lee", pePesel = "5458793";
		PersonDTO pe = service.createPerson(peName, peSurname, pePesel);
		ReporterDTO peReporter = service.createReporter(pe,
				ReporterSpeciality.DOCUMENTARY, stat4);
		service.addReportageToReporter(poReporter, reportage1);
		service.addReportageToReporter(peReporter, reportage1);
		Set<ReporterDTO> reportersByUs = new TreeSet<ReporterDTO>();
		reportersByUs.add(poReporter);
		reportersByUs.add(peReporter);
		Set<ReporterDTO> reportersByService = getReportageAuthors(reportage1);
		Assert.assertTrue(reportersByService.equals(reportersByUs));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Adding reportages to reporters works.");
		/* now let's change the version */
		reportage1.setContent(reportage1.getContent()
				+ " A Ala nie ma kota, tylko psa.");
		service.updateReportage(reportage1);
		reportersByService = getReportageAuthors(reportage1);
		Assert.assertTrue(reportersByService.equals(reportersByUs));
		List<Number> versions = service
				.getVersionsNumbersOfReportage(reportage1);
		Assert.assertTrue(versions.size() == 2);
		ReportageDTO reportage1v2 = service.getSpecificVersionOfReportage(
				reportage1, versions.get(1));
		Assert.assertTrue(reportage1.getContent().equals(
				reportage1v2.getContent()));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Versioning reportages works.");
	}

	@Test
	public void presentCurrentNews() {
		NewsDTO fastNews = service.createNews("FastNEWS");
		ReportageDTO reportage3 = service.createReportage("Quite wise one",
				"What is it?");
		service.addReportageToNews(fastNews, reportage3);
		reportage3.setContent(reportage3.getContent() + " And that's end.");
		service.updateReportage(reportage3);
		Map<NewsDTO, Set<ReportageDTO>> news = service.presentAllNews();
		Assert.assertTrue(news.containsKey(fastNews));
		Set<ReportageDTO> reportagesByService = service
				.getReportagesFromNews(fastNews);
		Assert.assertTrue(reportagesByService.size() == 1
				&& reportagesByService.contains(reportage3));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Presenting current news reportages works.");
	}

	@Test
	public void testLongestByEpisode() {
		TvSeriesDTO abc = service.createTvSeries("Abc1", "Abc");
		TvSeriesDTO def = service.createTvSeries("Def1", "Def");
		TvSeriesDTO ghi = service.createTvSeries("Ghi1", "Ghi");
		service.createEpisode(abc, 0, 0);
		service.createEpisode(abc, 0, 1);
		service.createEpisode(abc, 0, 2);
		service.createEpisode(abc, 0, 3);
		service.createEpisode(abc, 0, 4);
		service.createEpisode(def, 1, 0);
		service.createEpisode(def, 1, 9);
		service.createEpisode(def, 1, 2);
		service.createEpisode(def, 1, 3);
		service.createEpisode(def, 1, 4);
		service.createEpisode(ghi, 4, 19);
		service.createEpisode(ghi, 14, 9);
		List<TvSeriesDTO> longestByEpisodesByService = service
				.getLongestByEpisodesTvSeries();
		Assert.assertTrue(longestByEpisodesByService.size() == 2);
		Assert.assertTrue(longestByEpisodesByService.contains(abc));
		Assert.assertTrue(longestByEpisodesByService.contains(def));
		LoggerFactory
				.getLogger(TvWorldServiceTest.class)
				.info("The longest tv series in respect to the number of episodes found.");
	}

	@Test
	public void mostPopularNews() {
		NewsDTO news1 = service.createNews("Newsy1");
		news1.getAudience().add(99999999l);
		news1.getAudience().add(0l);
		news1.getAudience().add(0l);
		service.updateNews(news1);
		NewsDTO news2 = service.createNews("Newsy2");
		news2.getAudience().add(99999999l);
		news2.getAudience().add(0l);
		news2.getAudience().add(0l);
		service.updateNews(news2);
		NewsDTO news3 = service.createNews("Newsy3");
		news3.getAudience().add(66666666l);
		news3.getAudience().add(66666666l);
		news3.getAudience().add(66666666l);
		service.updateNews(news3);
		NewsDTO news4 = service.createNews("Newsy4");
		news4.getAudience().add(99999999l);
		news4.getAudience().add(0l);
		news4.getAudience().add(0l);
		service.updateNews(news4);
		List<NewsDTO> newsByService = service.getMostPopularNews();
		Assert.assertTrue(newsByService.size() == 3);
		Assert.assertTrue(newsByService.contains(news1));
		Assert.assertTrue(newsByService.contains(news2));
		Assert.assertTrue(newsByService.contains(news4));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"The most popular news found.");
	}

	@Test
	public void mostPopularInAverageNews() {
		NewsDTO news5 = service.createNews("Newsy5");
		news5.getAudience().add(77777777l);
		news5.getAudience().add(88888888l);
		service.updateNews(news5);
		NewsDTO news6 = service.createNews("Newsy6");
		news6.getAudience().add(11111111l);
		news6.getAudience().add(88888888l);
		service.updateNews(news6);
		List<NewsDTO> newsByService = service.getMostPopularInAverageNews();
		Assert.assertTrue(newsByService.size() == 1);
		Assert.assertTrue(newsByService.contains(news5));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"The most popular in average news found.");
	}

	@SuppressWarnings("unused")
	@Test
	public void testLongestBySeasons() {
		TvSeriesDTO jkl = service.createTvSeries("jkl1", "jkl");
		TvSeriesDTO mno = service.createTvSeries("mno1", "mno");
		TvSeriesDTO pqr = service.createTvSeries("pqr1", "pqr");
		EpisodeDTO jklEpisode1 = service.createEpisode(jkl, 0, 0);
		EpisodeDTO jklEpisode2 = service.createEpisode(jkl, 0, 1);
		EpisodeDTO jklEpisode3 = service.createEpisode(jkl, 0, 2);
		EpisodeDTO mnoEpisode1 = service.createEpisode(mno, 1, 0);
		EpisodeDTO mnoEpisode2 = service.createEpisode(mno, 1, 9);
		EpisodeDTO mnoEpisode3 = service.createEpisode(mno, 1, 2);
		EpisodeDTO pqrEpisode1 = service.createEpisode(pqr, 4, 19);
		EpisodeDTO pqrEpisode2 = service.createEpisode(pqr, 14, 9);
		EpisodeDTO pqrEpisode3 = service.createEpisode(pqr, 24, 19);
		EpisodeDTO pqrEpisode4 = service.createEpisode(pqr, 114, 9);
		List<TvSeriesDTO> longestBySeasonsByService = service
				.getLongestBySeasonsTvSeries();
		Assert.assertTrue(longestBySeasonsByService.size() == 1);
		Assert.assertTrue(longestBySeasonsByService.contains(pqr));
		LoggerFactory
				.getLogger(TvWorldServiceTest.class)
				.info("The longest tv series in respect to the number of seasons found.");
	}

	private PersonDTO getCurrentPerson(PersonDTO dto) {
		List<PersonDTO> persons = service.findPerson(createGetByIdCriteria(
				Person.class, dto.getId()));
		return pullUnique(persons);
	}

	private TvWorkerDTO getCurrentTvWorker(TvWorkerDTO dto) {
		List<TvWorkerDTO> workers = service.findTvWorker(DetachedCriteria
				.forClass(TvWorker.class)
				.add(Property.forName("identity.id").eq(
						dto.getIdentity().getId()))
				.add(Property.forName("employer.id").eq(
						dto.getEmployer().getId())));
		return pullUnique(workers);
	}

	private ReporterDTO getCurrentReporter(ReporterDTO dto) {
		List<ReporterDTO> reporters = service.findReporter(DetachedCriteria
				.forClass(Reporter.class)
				.add(Property.forName("identity.id").eq(
						dto.getIdentity().getId()))
				.add(Property.forName("employer.id").eq(
						dto.getEmployer().getId())));
		return pullUnique(reporters);
	}

	private ActorDTO getCurrentActor(ActorDTO dto) {
		List<ActorDTO> actors = service.findActor(DetachedCriteria
				.forClass(Actor.class)
				.add(Property.forName("identity.id").eq(
						dto.getIdentity().getId()))
				.add(Property.forName("employer.id").eq(
						dto.getEmployer().getId())));
		return pullUnique(actors);
	}

	private TvStationDTO getCurrentTvStation(TvStationDTO dto) {
		List<TvStationDTO> stations = service
				.findTvStation(createGetByIdCriteria(TvStation.class,
						dto.getId()));
		return pullUnique(stations);
	}

	private TvProductionDTO getCurrentTvProduction(TvProductionDTO dto) {
		List<TvProductionDTO> productions = service
				.findTvProduction(createGetByIdCriteria(TvProduction.class,
						dto.getId()));
		return pullUnique(productions);
	}

	private NewsDTO getCurrentNews(NewsDTO dto) {
		List<NewsDTO> news = service.findNews(createGetByIdCriteria(News.class,
				dto.getId()));
		return pullUnique(news);
	}

	private TvSeriesDTO getCurrentTvSeries(TvSeriesDTO dto) {
		List<TvSeriesDTO> tvSeries = service
				.findTvSeries(createGetByIdCriteria(TvSeries.class, dto.getId()));
		return pullUnique(tvSeries);
	}

	private EpisodeDTO getCurrentEpisode(EpisodeDTO dto) {
		List<EpisodeDTO> episodes = service.findEpisode(createGetByIdCriteria(
				Episode.class, dto.getId()));
		return pullUnique(episodes);
	}

	private <T> T pullUnique(List<T> list) {
		assertTrue(list == null || list.size() <= 1);
		if (list != null && list.size() == 1)
			return list.get(0);
		else
			return null;
	}

	private DetachedCriteria createGetByIdCriteria(Class entityClass, Long id) {
		return DetachedCriteria.forClass(entityClass).add(
				Property.forName("id").eq(id));
	}

	private Set<ReporterDTO> getReportageAuthors(ReportageDTO reportage) {
		/*
		 * Because HQL queries are not presented to the service user, we need to
		 * filter the reporters in an application (in this test we want to
		 * communicate with the service only).
		 */
		List<ReporterDTO> allReporters = service.findReporter(DetachedCriteria
				.forClass(Reporter.class));
		Set<ReporterDTO> reportageAuthors = new TreeSet<ReporterDTO>();
		for (ReporterDTO rd : allReporters) {
			if (service.getReportagesFromReporter(rd).contains(reportage))
				reportageAuthors.add(rd);
		}
		return reportageAuthors;
	}
}
