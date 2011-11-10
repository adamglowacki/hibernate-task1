package pl.edu.mimuw.ag291541.tvworld.testing;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.Reporter;
import pl.edu.mimuw.ag291541.tvworld.entity.TvSeries;
import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;
import pl.edu.mimuw.ag291541.tvworld.entity.TvWorker;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.ActorDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.EpisodeDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.NewsDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.PersonDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.ReportageDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.ReporterDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvSeriesDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvStationDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.TvWorkerDTO;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ActorRating;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ReporterSpeciality;
import pl.edu.mimuw.ag291541.tvworld.service.TvWorldService;
import pl.edu.mimuw.ag291541.tvworld.service.TvWorldServiceFactory;

public class TvWorldServiceTest {
	private static TvWorldService service;

	@BeforeClass
	public static void obtainServiceObject() {
		service = TvWorldServiceFactory.getInstance().getService();
	}

	@Test
	public void createAndRetrievePerson() {
		final String johnnyName = "Johnny", johnnySurname = "Red", johnnyPesel = "AFGHIJK";
		final String jamesName = "James", jamesSurname = "Tableboard", jamesPesel = "19121200007";
		final int peopleNumber = 2;
		final PersonDTO johnny = service.createPerson(johnnyName,
				johnnySurname, johnnyPesel);
		final PersonDTO james = service.createPerson(jamesName, jamesSurname,
				jamesPesel);
		final Long johnnyId = johnny.getId(), jamesId = james.getId();
		final Set<Long> people = new TreeSet<Long>();
		people.add(johnnyId);
		people.add(jamesId);
		final DetachedCriteria criteria = DetachedCriteria.forClass(
				Person.class).add(Property.forName("id").in(people));
		List<PersonDTO> persons = service.findPerson(criteria);
		Assert.assertTrue(persons.size() == peopleNumber);
		for (PersonDTO p : persons)
			Assert.assertTrue(people.contains(p.getId()));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Finding persons by criteria works.");
	}

	@Test
	public void updatePerson() {
		final String anneName = "Anne", anneSurname = "Katalinsky", annePesel = "TYTYTYTUS";
		final PersonDTO anne = service.createPerson(anneName, anneSurname,
				annePesel);
		Long anneId = anne.getId();
		final List<PersonDTO> annes2 = service.findPerson(DetachedCriteria
				.forClass(Person.class).add(Property.forName("id").eq(anneId)));
		Assert.assertTrue(annes2.size() == 1
				&& annes2.get(0).getId().equals(anneId));
		final PersonDTO anne2 = annes2.get(0);
		Assert.assertTrue(anne2.equals(anne));
		final String anneNewSurname = anne.getSurname() + "-Bull";
		anne.setSurname(anneNewSurname);
		service.updatePerson(anne);
		final List<PersonDTO> annes3 = service.findPerson(DetachedCriteria
				.forClass(Person.class).add(
						Property.forName("pesel").eq(annePesel)));
		Assert.assertTrue(annes3.size() == 1);
		final PersonDTO anne3 = annes3.get(0);
		/* it is still the same person */
		Assert.assertTrue(anne3.equals(anne));
		/* though with a new surname */
		Assert.assertTrue(anne3.getSurname().equals(anneNewSurname));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Updating persons works.");
	}

	@Test
	public void deletePerson() {
		PersonDTO julia = service.createPerson("Julia", "Mandelbrot",
				"11111222223333");
		DetachedCriteria getJulias = DetachedCriteria.forClass(Person.class)
				.add(Property.forName("id").eq(julia.getId()));
		List<PersonDTO> julias = service.findPerson(getJulias);
		Assert.assertTrue(julias.size() == 1);
		Assert.assertTrue(julias.get(0).equals(julia));
		service.deletePerson(julia);
		julias = service.findPerson(getJulias);
		Assert.assertTrue(julias.size() == 0);
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Deleting persons works.");
	}

	@Test
	public void deleteTvWorker() {
		PersonDTO zenek = service.createPerson("Zenek", "Mandelbrot", "445566");
		TvStationDTO tdz = service.createTvStation("Telewizja dla Zenka");
		TvWorkerDTO zenekWorker = service.createTvWorker(zenek, tdz);
		DetachedCriteria getZenki = DetachedCriteria.forClass(TvWorker.class)
				.add(Property.forName("identity.id").eq(zenek.getId()))
				.add(Property.forName("employer.id").eq(tdz.getId()));
		List<TvWorkerDTO> zenki = service.findTvWorker(getZenki);
		Assert.assertTrue(zenki.size() == 1);
		Assert.assertTrue(zenki.get(0).equals(zenekWorker));
		service.deleteTvWorker(zenekWorker);
		zenki = service.findTvWorker(getZenki);
		Assert.assertTrue(zenki.size() == 0);
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Deleting TV workers works.");
	}

	@Test
	public void findPersonsByCriteria() {
		final String emilName = "Emil", emilSurname = "Red", emilPesel = "AFGHIJK123";
		final String ulaName = "ula", ulaSurname = "Katalinsky", ulaPesel = "TYTYTYTUS123";
		final String jurekName = "jurek", jurekSurname = "Tableboard", jurekPesel = "19121200007123";
		final int peopleNumber = 3;
		final PersonDTO emil = service.createPerson(emilName, emilSurname,
				emilPesel);
		final PersonDTO ula = service.createPerson(ulaName, ulaSurname,
				ulaPesel);
		final PersonDTO jurek = service.createPerson(jurekName, jurekSurname,
				jurekPesel);
		final Long emilId = emil.getId(), ulaId = ula.getId(), jurekId = jurek
				.getId();
		final Set<Long> people = new TreeSet<Long>();
		people.add(emilId);
		people.add(jurekId);
		people.add(ulaId);
		final DetachedCriteria criteria = DetachedCriteria.forClass(
				Person.class).add(Property.forName("id").in(people));
		List<PersonDTO> persons = service.findPerson(criteria);
		Assert.assertTrue(persons.size() == peopleNumber);
		for (PersonDTO p : persons)
			Assert.assertTrue(people.contains(p.getId()));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Finding persons by criteria works.");
	}

	@Test
	public void createAndRetrieveTvStation() {
		TvStationDTO tdc = service.createTvStation("Telewizja dla Czerwonych");
		List<TvStationDTO> stations = service.findTvStation(DetachedCriteria
				.forClass(TvStation.class).add(
						Property.forName("id").eq(tdc.getId())));
		Assert.assertTrue(stations.size() == 1);
		Assert.assertTrue(stations.get(0).equals(tdc));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Creating TV station works.");
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
		NewsDTO news = service.createNews("Super-Express", 45000);
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
		service.addReportageToNews(news, reportage1);
		service.addReportageToNews(news, reportage2);
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Modifying reportages of reporter collection succeeded.");
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
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Adding workers to TV production is ok.");
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
		service.deleteTvSeries(tvSeries3);
		List<TvSeriesDTO> nonExistingTvSeries = service
				.findTvSeries(DetachedCriteria.forClass(TvSeries.class).add(
						Property.forName("id").eq(tvSeries3.getId())));
		Assert.assertTrue(nonExistingTvSeries == null
				|| nonExistingTvSeries.size() == 0);
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"Adding and removing episodes from TV series works.");
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
		NewsDTO fastNews = service.createNews("FastNEWS", 9);
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
		NewsDTO news1 = service.createNews("Newsy1", 9999999);
		NewsDTO news2 = service.createNews("Newsy2", 9999999);
		@SuppressWarnings("unused")
		NewsDTO news3 = service.createNews("Newsy3", 999999);
		NewsDTO news4 = service.createNews("Newsy4", 9999999);
		List<NewsDTO> newsByService = service.getMostPopularNews();
		Assert.assertTrue(newsByService.size() == 3);
		Assert.assertTrue(newsByService.contains(news1));
		Assert.assertTrue(newsByService.contains(news2));
		Assert.assertTrue(newsByService.contains(news4));
		LoggerFactory.getLogger(TvWorldServiceTest.class).info(
				"The most popular news found.");
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
