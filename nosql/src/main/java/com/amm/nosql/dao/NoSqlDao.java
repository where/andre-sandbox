package com.amm.nosql.dao;

import com.amm.nosql.data.NoSqlEntity;

/**
 * Basic operations for a Key/Value store.
 * @author amesarovic
 */
public interface NoSqlDao<T extends NoSqlEntity>  {
   /**
	* Puts a new data object of this type
	* 
	* @param o The object to put in the data store.
	*/
   public void put(T o) throws Exception;

   /**
	* Find the object in the data store with the given id
	* @param id The id of the object to lookup
	* @return The object referenced by the given id
	*/
   public T get(String id) throws Exception;

   /**
	* Remove the given object from the data store
	* 
	* @param id The id to delete.
	*/
   public void delete(String id) throws Exception;
}
