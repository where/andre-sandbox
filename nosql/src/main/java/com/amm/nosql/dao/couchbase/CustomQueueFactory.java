package com.amm.nosql.dao.couchbase;

import java.util.*;
import java.util.concurrent.*;
import net.spy.memcached.ops.OperationQueueFactory;
import net.spy.memcached.ops.Operation;

/**
 * Per advice of Couchbase folks.
 */
public class CustomQueueFactory implements OperationQueueFactory {
	//private int queueSize = 65536 ; // max suggested by Couchbase folks
	private int queueSize = 16384 ; // default

	public CustomQueueFactory(int queueSize) {
		this.queueSize = queueSize;
	}

	public BlockingQueue<Operation> create() { 
		// the default is 16384, we've gone up by two orders of binary magnitude 
		// memory usage is approximately key size + value size + a bit more 
		return new ArrayBlockingQueue<Operation>(queueSize); 
	}

	@Override
	public String toString() {
		return "queueSize="+ queueSize;
	}
}
