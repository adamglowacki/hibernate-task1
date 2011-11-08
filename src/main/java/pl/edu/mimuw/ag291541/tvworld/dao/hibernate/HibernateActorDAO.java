package pl.edu.mimuw.ag291541.tvworld.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import pl.edu.mimuw.ag291541.tvworld.dao.ActorDAO;
import pl.edu.mimuw.ag291541.tvworld.dao.util.HibernateUtil;
import pl.edu.mimuw.ag291541.tvworld.entity.Actor;
import pl.edu.mimuw.ag291541.tvworld.entity.Person;
import pl.edu.mimuw.ag291541.tvworld.entity.TvStation;
import pl.edu.mimuw.ag291541.tvworld.entity.type.ActorRating;

public class HibernateActorDAO implements ActorDAO {

	@Override
	public Actor create(Person identity, ActorRating rating, TvStation employer) {
		Actor actor = new Actor(identity, rating, employer);
		HibernateUtil.getSessionFactory().getCurrentSession().save(actor);
		return actor;
	}

	@Override
	public void delete(Actor p) {
		HibernateUtil.getSessionFactory().getCurrentSession().delete(p);
	}

	@Override
	public Actor get(Person identity, TvStation employer) {
		return (Actor) HibernateUtil.getSessionFactory().getCurrentSession()
				.createCriteria(Actor.class)
				.add(Property.forName("identity").eq(identity))
				.add(Property.forName("employer").eq(employer)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Actor> find(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(
				HibernateUtil.getSessionFactory().getCurrentSession()).list();
	}

}
