package model;

// Generated Jul 19, 2014 11:26:45 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Quickquestion.
 * @see model.Quickquestion
 * @author Hibernate Tools
 */
@Stateless
public class QuickquestionHome {

	private static final Log log = LogFactory.getLog(QuickquestionHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Quickquestion transientInstance) {
		log.debug("persisting Quickquestion instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Quickquestion persistentInstance) {
		log.debug("removing Quickquestion instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Quickquestion merge(Quickquestion detachedInstance) {
		log.debug("merging Quickquestion instance");
		try {
			Quickquestion result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Quickquestion findById(Integer id) {
		log.debug("getting Quickquestion instance with id: " + id);
		try {
			Quickquestion instance = entityManager
					.find(Quickquestion.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
