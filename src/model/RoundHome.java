package model;

// Generated Jul 19, 2014 11:26:45 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Round.
 * @see model.Round
 * @author Hibernate Tools
 */
@Stateless
public class RoundHome {

	private static final Log log = LogFactory.getLog(RoundHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Round transientInstance) {
		log.debug("persisting Round instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Round persistentInstance) {
		log.debug("removing Round instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Round merge(Round detachedInstance) {
		log.debug("merging Round instance");
		try {
			Round result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Round findById(Integer id) {
		log.debug("getting Round instance with id: " + id);
		try {
			Round instance = entityManager.find(Round.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
