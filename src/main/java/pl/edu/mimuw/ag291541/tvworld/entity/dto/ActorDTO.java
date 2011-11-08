package pl.edu.mimuw.ag291541.tvworld.entity.dto;

import pl.edu.mimuw.ag291541.tvworld.entity.Actor;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ActorRating;

public class ActorDTO extends TvWorkerDTO {
	private ActorRating rating;

	public ActorDTO(Actor actor) {
		super(actor);
		this.rating = actor.getRating();
	}

	public ActorRating getRating() {
		return rating;
	}

	public void setRating(ActorRating rating) {
		this.rating = rating;
	}
}
