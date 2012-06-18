package com.andre.mongo;

import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.ReadPreference;

/**
 * Spring config-friendly factory to create Mongo DBCollection
 * @author amesarovic
 */
public class MongoCollectionFactory {
	private static final Logger logger = Logger.getLogger(MongoCollectionFactory.class);
	private String databaseName ;
	private String collectionName ;
	private boolean slaveOk ;

	public MongoCollectionFactory(Mongo mongo, String databaseName, String collectionName) {
		this(mongo, databaseName, collectionName, false) ;
	}

	public MongoCollectionFactory(Mongo mongo, String databaseName, String collectionName, boolean slaveOk) {
		this.mongo = mongo ;
		this.databaseName = databaseName ;
		this.collectionName = collectionName ;
		database = mongo.getDB(databaseName);
		collection = database.getCollection(collectionName);

		this.slaveOk = slaveOk ;
		logger.debug("database="+databaseName+" collection="+collectionName+" slaveOk="+slaveOk);
		if (slaveOk) {
			//mongo.slaveOk(); // Java client 2.6.5
			mongo.setReadPreference(ReadPreference.SECONDARY);
		}
	}

	private Mongo mongo; 
	public Mongo getMongo() { return mongo; }
	public void setMongo(Mongo mongo) { this.mongo = mongo; } 
 
	private DBCollection collection; 
	public DBCollection getCollection() { return collection; }
	public void setCollection(DBCollection collection) { this.collection = collection; } 
 
	private DB database; 
	public DB getDatabase() { return database; }
	public void setDatabase(DB database) { this.database = database; } 

	@Override 
	public String toString() {
		return
			"databaseName="+databaseName
			+" collectionName="+collectionName
			+" slaveOk="+slaveOk
			+" mongo=["+mongo+"]"
			;
	}
}
