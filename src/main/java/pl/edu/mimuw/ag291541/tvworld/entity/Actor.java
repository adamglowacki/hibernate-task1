package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Set;
import java.util.TreeSet;

import pl.edu.mimuw.ag291541.tvworld.entity.type.ActorRating;

/**
 * It is a type of a TV worker. One TV worker has only one type.
 * 
 * @author adas
 * 
 */
public class Actor extends TvWorker {
	private static final long serialVersionUID = -1670051486778818856L;
	private ActorRating rating;
	/**
	 * The episodes that the actor plays in.
	 */
	private Set<Episode> episodes = new TreeSet<Episode>();

	public Actor() {

	}

	public Actor(Person person, ActorRating rating, TvStation employer) {
		super(person, employer);
		this.rating = rating;
	}

	public ActorRating getRating() {
		return rating;
	}

	public void setRating(ActorRating rating) {
		this.rating = rating;
	}

	public Set<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Set<Episode> episodes) {
		this.episodes = episodes;
	}
}
