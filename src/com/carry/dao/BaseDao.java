package com.carry.dao;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository("baseDao")
public class BaseDao {

    @Autowired
    private SessionFactory sessionFactory;

	protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

	public BaseDao() {
		super();
	}

	/**
	 * <保存实体> <完整保存实体>
	 *
	 * @param entity 实体参数
	 * @throws Exception
	 */
	public boolean save(Object entity) throws Exception {

		boolean result = false;
		try {
			Session session = getCurrentSession();
			session.save(entity);
			String s = session.save(entity).toString();
			session.flush();
			if (s.trim().length() != 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public List<Map<String, Object>> getListMapBySQL(final String sql, Map<String, Object> params) throws Exception {
		try {
			Session session = getCurrentSession();
			final SQLQuery query=(SQLQuery) session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (params != null) {
				Iterator<String> keySet = params.keySet().iterator();
				while (keySet.hasNext()) {
					String key = keySet.next();
					if (sql.contains(":" + key)) {
						Object obj = params.get(key);
						if(obj instanceof Collection<?>){
							query.setParameterList(key, (Collection<?>)obj);
						}else if(obj instanceof Object[]){
							query.setParameterList(key, (Object[])obj);
						}else{
							query.setParameter(key, obj);
						}
					}
				}
			}
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Map<String, Object> getMapBySQL(final String sql, Map<String, Object> params) throws Exception {
		try {
			List<Map<String, Object>> list = getListMapBySQL(sql,params);
			if ( list!=null && list.size()>0 ) {
				return (Map<String, Object>) list.get(0);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <执行Sql语句>
	 *
	 * @param sql  sql
	 * @param params 不定参数数组
	 */
	public boolean executeSql(final String sql, Map<String, Object> params) {
		try {
			Query query = getCurrentSession().createSQLQuery(sql);
			if (params != null) {
				Iterator<String> keySet = params.keySet().iterator();
				while (keySet.hasNext()) {
					String key = keySet.next();
					if (sql.contains(":" + key)) {
						Object obj = params.get(key);
						if (obj instanceof Collection<?>) {
							query.setParameterList(key, (Collection<?>) obj);
						} else if (obj instanceof Object[]) {
							query.setParameterList(key, (Object[]) obj);
						} else {
							query.setParameter(key, obj);
						}
					}
				}
			}
//			int i = query.executeUpdate();
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
