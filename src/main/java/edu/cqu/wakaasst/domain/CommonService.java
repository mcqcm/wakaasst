package edu.cqu.wakaasst.domain;

import java.util.List;
import java.util.Set;

public interface CommonService {

	public <T> T findEntityById(Object id, Class<T> clazz);

	public <PK, T> List<T> findEntitiesByIds(Set<PK> ids, Class<T> clazz);

	public void removeEntityById(Object id, Class<?> clazz);

	public void removeEntities(Object[] ids, Class<?> clazz);

	public <T> T addEntity(T entity);

	public <T> T saveEntity(T entity);

	public <T> T findEntityByJPQL(String jpql, Class<T> clazz);

	public <T> List<T> readEntitiesByJPQL(String jpql, Class<T> clazz, Object... params);

	public int executeJPQL(String jpql);

}