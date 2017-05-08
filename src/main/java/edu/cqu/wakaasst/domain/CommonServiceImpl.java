package edu.cqu.wakaasst.domain;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {
	
	@Autowired
	protected CommonDaoImpl commonDao;
	
	public <T> T findEntityById(Object id, Class<T> clazz) {
		return commonDao.readEntityById(id, clazz);
	}

	public <PK, T> List<T> findEntitiesByIds(Set<PK> ids, Class<T> clazz) {
		return commonDao.readEntitiesByIds(ids, clazz);
	}

	public void removeEntityById(Object id, Class<?> clazz) {
		commonDao.deleteEntityById(id, clazz);
	}
	
	public void removeEntities(Object[] ids, Class<?> clazz) {
		for(Object id : ids) {
			commonDao.deleteEntityById(id, clazz);
		}
	}

	public <T> T addEntity(T entity) {
		return commonDao.saveEntity(entity);
	}
	
	public <T> T saveEntity(T entity) {
		return commonDao.updateEntity(entity);
	}
	
	public <T> T findEntityByJPQL(String jpql, Class<T> clazz) {
		return commonDao.readEntityByJPQL(jpql, clazz);
	}
	
	public <T> List<T> readEntitiesByJPQL(String jpql, Class<T> clazz, Object... params) {
		return commonDao.readEntitiesByJPQL(jpql, clazz, params);
	}
	
	public int executeJPQL(String jpql) {
		return commonDao.executeJPQL(jpql);
	}
	
}
