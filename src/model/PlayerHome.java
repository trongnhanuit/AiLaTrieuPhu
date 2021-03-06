package model;

// Generated Jul 19, 2014 11:26:45 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Player.
 * @see model.Player
 * @author Hibernate Tools
 */
@Stateless
public class PlayerHome {

	private static final Log log = LogFactory.getLog(PlayerHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Player transientInstance) {
		log.debug("persisting Player instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Player persistentInstance) {
		log.debug("removing Player instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Player merge(Player detachedInstance) {
		log.debug("merging Player instance");
		try {
			Player result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Player findById(Integer id) {
		log.debug("getting Player instance with id: " + id);
		try {
			Player instance = entityManager.find(Player.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
