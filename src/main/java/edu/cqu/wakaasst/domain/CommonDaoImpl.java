package edu.cqu.wakaasst.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository("commonDao")
public class CommonDaoImpl {

	@PersistenceContext(unitName = "wakaasstPU")
	protected EntityManager em;

	public <T> T readEntityById(Object id, Class<T> clazz) {
		return em.find(clazz, id);
	}
	
	public <PK, T> List<T> readEntitiesByIds(Set<PK> ids, Class<T> clazz) {
		List<T> entities = new ArrayList<T>();
		for(PK id : ids){
			entities.add(em.find(clazz, id));
		}
		return entities;
	}
	
	public void deleteEntityById(Object id, Class<?> clazz) {
		Object entity = em.find(clazz, id);
		if(entity != null) {
			em.remove(entity);
		}
	}
	
	public <T> T saveEntity(T entity) {
		if(em.contains(entity)) {
			return em.merge(entity);
		} else {
			em.persist(entity);
			return entity;
		}
	}
	
	public <T> T updateEntity(T entity) {
		return em.merge(entity);
	}
	
	public <T> T readEntityByJPQL(String jpql, Class<T> clazz) {
		TypedQuery<T> query = em.createQuery(jpql, clazz);
		T ret = null;
		try {
			ret = query.getSingleResult();
		} catch( Exception e) {
			ret = null;
		}
		return ret;
	}
	
	public <T> List<T> readEntitiesByJPQL(String jpql, Class<T> clazz, Object... params) {
		TypedQuery<T> query = em.createQuery(jpql, clazz);
		for(int i = 1; i<=params.length; i++){
			query.setParameter(i, params[i-1]);
		}
		return query.getResultList();
	}
	
	public int executeJPQL(String jpql) {
		return em.createQuery(jpql).executeUpdate();
	}
	
}
