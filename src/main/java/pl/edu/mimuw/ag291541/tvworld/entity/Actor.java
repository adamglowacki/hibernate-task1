package pl.edu.mimuw.ag291541.tvworld.entity;

import java.util.Collection;

import pl.edu.mimuw.ag291541.tvworld.entity.type.ActorRating;


public class Actor extends TVWorker {
	private ActorRating rating;
	/**
	 * The episodes that the actor plays in.
	 */
	private Collection<Episode> episodes;

	public ActorRating getRating() {
		return rating;
	}

	public void setRating(ActorRating rating) {
		this.rating = rating;
	}

	public Collection<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Collection<Episode> episodes) {
		this.episodes = episodes;
	}
}
