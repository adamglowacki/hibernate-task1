package pl.edu.mimuw.ag291541.tvworld.testing;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.dto.PersonDTO;
import pl.edu.mimuw.ag291541.tvworld.service.TvWorldService;
import pl.edu.mimuw.ag291541.tvworld.service.TvWorldServiceFactory;

public class TvWorldServiceTest {
	private static TvWorldService service;

	@BeforeClass
	public static void obtainServiceObject() {
		service = TvWorldServiceFactory.getInstance().getService();
	}

	@Test
	public void PersonTest() {
		final String johnnyName = "Johnny", johnnySurname = "Red", johnnyPesel = "AFGHIJK";
		final String anneName = "Anne", anneSurname = "Katalinsky", annePesel = "TYTYTYTUS";
		final String jamesName = "James", jamesSurname = "Tableboard", jamesPesel = "19121200007";
		final int peopleNumber = 3;
		final PersonDTO johnny = service.createPerson(johnnyName, johnnySurname,
				johnnyPesel);
		final PersonDTO anne = service.createPerson(anneName, anneSurname, annePesel);
		final PersonDTO james = service
				.createPerson(jamesName, jamesSurname, jamesPesel);
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
}
