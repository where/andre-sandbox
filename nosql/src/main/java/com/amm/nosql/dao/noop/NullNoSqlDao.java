package com.amm.nosql.dao.noop;

import com.amm.nosql.NoSqlException;
import com.amm.nosql.data.NoSqlEntity;
import com.amm.nosql.dao.NoSqlDao;

/**
 * Null implementation - optionally will sleep a bit.
 * @author amesarovic
 */
public class NullNoSqlDao<T extends NoSqlEntity> implements NoSqlDao<T> {
	private long millis ;

	public NullNoSqlDao() {
	}

	public NullNoSqlDao(long millis) {
		this.millis = millis ;
	}

	public T get(String id) throws Exception {
		sleep();
		return null;
	}

	public void put(T entity) throws Exception {
		sleep();
	}

	public void delete(String id) throws Exception {
		sleep();
	}

	private void sleep() {
		if (millis > 0) {	
			try {
	  			Thread.currentThread().sleep(millis);
			} catch (InterruptedException ignoreme) {
			}
		}
	}
}
