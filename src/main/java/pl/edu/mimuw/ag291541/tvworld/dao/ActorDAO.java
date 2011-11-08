package pl.edu.mimuw.ag291541.tvworld.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import pl.edu.mimuw.ag291541.tvworld.entity.Actor;
import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ActorRating;

public interface ActorDAO {
	public Actor create(Person identity, ActorRating rating, TvStation employer);

	public void delete(Actor p);

	public void update(Actor p);

	public List<Actor> find(DetachedCriteria criteria);
}
