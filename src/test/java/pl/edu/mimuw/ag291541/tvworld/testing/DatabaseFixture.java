package pl.edu.mimuw.ag291541.tvworld.testing;

import pl.edu.mimuw.ag291541.tvworld.entity.dto.PersonDTO;

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
	public PersonDTO johnny;
	public PersonDTO james;

	private DatabaseFixture() {
	}
}
