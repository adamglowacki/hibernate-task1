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

import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.Reporter;
import pl.edu.mimuw.ag291541.tvworld.entity.TvSeries;
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
	public void personTest() {
		final String johnnyName = "Johnny", johnnySurname = "Red", johnnyPesel = "AFGHIJK";
		final String anneName = "Anne", anneSurname = "Katalinsky", annePesel = "TYTYTYTUS";
		final String jamesName = "James", jamesSurname = "Tableboard", jamesPesel = "19121200007";
		final int peopleNumber = 3;
		final PersonDTO johnny = service.createPerson(johnnyName,
				johnnySurname, johnnyPesel);
		final PersonDTO anne = service.createPerson(anneName, anneSurname,
				annePesel);
		final PersonDTO james = service.createPerson(jamesName, jamesSurname,
				jamesPesel);
		final Long johnnyId = johnny.getId(), anneId = anne.getId(), jamesId = james
				.getId();
		final Set<Long> people = new TreeSet<Long>();
		people.add(johnnyId);
		people.add(jamesId);
		people.add(anneId);
		final DetachedCriteria criteria = DetachedCriteria.forClass(
				Person.class).add(Property.forName("id").in(people));
		List<PersonDTO> persons = service.findPerson(criteria);
		Assert.assertTrue(persons.size() == peopleNumber);
		for (PersonDTO p : persons) {
			Assert.assertTrue(people.contains(p.getId()));
		}
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
	}

	@Test
	public void testTvWorkers() {
		TvStationDTO tdz = service.createTvStation("Telewizja Dla Zielonych");
		TvStationDTO tdf = service.createTvStation("Telewizja Dla Fioletowych");
		final String wincentyName = "Wincenty", wincentySurname = "Kapusta", wincentyPesel = "181920212322";
		PersonDTO wincenty = service.createPerson(wincentyName,
				wincentySurname, wincentyPesel);
		final String jacekName = "Jacek", jacekSurname = "Rybołówek", jacekPesel = "12AX798Y";
		PersonDTO jacek = service.createPerson(jacekName, jacekSurname,
				jacekPesel);
		final String joasiaName = "Joasia", joasiaSurname = "Brzęczyk", joasiaPesel = "007";
		PersonDTO joasia = service.createPerson(joasiaName, joasiaSurname,
				joasiaPesel);
		TvWorkerDTO wincenty4Tdz = service.createTvWorker(wincenty, tdz);
		TvWorkerDTO jacek4Tdz = service.createTvWorker(jacek, tdz);
		ReporterDTO joasia4Tdz = service.createReporter(joasia,
				ReporterSpeciality.WILDLIFE, tdz);
		ReporterDTO jacek4Tdf = service.createReporter(jacek,
				ReporterSpeciality.GARDENING_SHOW, tdf);
		ActorDTO joasia4Tdf = service
				.createActor(joasia, ActorRating.GOOD, tdf);
		Set<TvWorkerDTO> tdzWorkersByService = service
				.getTvWorkersFromTvStation(tdz);
		Set<TvWorkerDTO> tdzWorkersByUs = new TreeSet<TvWorkerDTO>();
		tdzWorkersByUs.add(jacek4Tdz);
		tdzWorkersByUs.add(wincenty4Tdz);
		tdzWorkersByUs.add(joasia4Tdz);
		Assert.assertTrue(tdzWorkersByUs.equals(tdzWorkersByService));
		Set<TvWorkerDTO> tdfWorkersByService = service
				.getTvWorkersFromTvStation(tdf);
		Set<TvWorkerDTO> tdfWorkersByUs = new TreeSet<TvWorkerDTO>();
		tdfWorkersByUs.add(jacek4Tdf);
		tdfWorkersByUs.add(joasia4Tdf);
		Assert.assertTrue(tdfWorkersByService.equals(tdfWorkersByUs));
		/* now let's fire jacek from tdf */
		service.deleteTvWorker(jacek4Tdf);
		tdfWorkersByService = service.getTvWorkersFromTvStation(tdf);
		tdfWorkersByUs.remove(jacek4Tdf);
		Assert.assertTrue(tdfWorkersByService.equals(tdfWorkersByUs));
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
		NewsDTO fastNews = service.createNews("FastNEWS", 9);
		service.addReportageToNews(fastNews, reportage1v2);
		Map<NewsDTO, Set<ReportageDTO>> news = service.presentAllNews();
		Assert.assertTrue(news.containsKey(fastNews));
		Set<ReportageDTO> reportagesByService = service
				.getReportagesFromNews(fastNews);
		Assert.assertTrue(reportagesByService.size() == 1
				&& reportagesByService.contains(reportage1v2));
	}

	@SuppressWarnings("unused")
	@Test
	public void testSpecialMethods() {
		TvSeriesDTO abc = service.createTvSeries("Abc1", "Abc");
		TvSeriesDTO def = service.createTvSeries("Def1", "Def");
		TvSeriesDTO ghi = service.createTvSeries("Ghi1", "Ghi");
		EpisodeDTO abcEpisode1 = service.createEpisode(abc, 0, 0);
		EpisodeDTO abcEpisode2 = service.createEpisode(abc, 0, 1);
		EpisodeDTO abcEpisode3 = service.createEpisode(abc, 0, 2);
		EpisodeDTO defEpisode1 = service.createEpisode(def, 1, 0);
		EpisodeDTO defEpisode2 = service.createEpisode(def, 1, 9);
		EpisodeDTO defEpisode3 = service.createEpisode(def, 1, 2);
		EpisodeDTO ghiEpisode1 = service.createEpisode(ghi, 4, 19);
		EpisodeDTO ghiEpisode2 = service.createEpisode(ghi, 14, 9);
		List<TvSeriesDTO> longestByEpisodesByService = service
				.getLongestByEpisodesTvSeries();
		Assert.assertTrue(longestByEpisodesByService.size() == 2);
		Assert.assertTrue(longestByEpisodesByService.contains(abc));
		Assert.assertTrue(longestByEpisodesByService.contains(def));
	}

	public Set<ReporterDTO> getReportageAuthors(ReportageDTO reportage) {
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
