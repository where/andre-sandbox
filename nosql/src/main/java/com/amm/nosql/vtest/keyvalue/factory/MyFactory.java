package com.amm.nosql.vtest.keyvalue.factory;

import com.amm.nosql.vtest.keyvalue.*;
import com.amm.vtest.Task;
import com.amm.vtest.TaskFactory;
import com.amm.vtest.plugins.datagen.ValueGenerator;
import com.amm.vtest.plugins.datagen.RandomValueGenerator;
import java.util.*;

/**
 * 
 */
public class MyFactory implements TaskFactory {
	private List<Task> tasks = new ArrayList<Task>();
	private Random random = new Random();
	private int max ;

	public MyFactory(PutTask putTask, GetTask getTask, int max) {
		this.max = max ;
		doit(putTask, getTask);
	}

	private void doit(PutTask putTask, GetTask getTask) {

		tasks.add(putTask);

		long seed = 0 ;

		ValueGenerator generator = (ValueGenerator)putTask.getValueGenerator();
		if (generator instanceof RandomValueGenerator) {
			RandomValueGenerator rgenerator = (RandomValueGenerator) generator;
			seed = rgenerator.getSeed();
		}

		for (int j=0 ; j < max ; j++) {

			PutTask ptask = (PutTask)Utils.clone(putTask);
			ptask.setName("Update"+j);
			ValueGenerator vgen = putTask.getValueGenerator();
			setSeed(vgen,seed+random.nextLong());
			tasks.add(ptask);

			GetTask gtask = (GetTask)Utils.clone(getTask);
			gtask.setName("Get"+j);
			tasks.add(gtask);
		}
	}

/*
	private void _doit(PutTask putTask, GetTask getTask) {
		debug("putTask="+putTask);
		debug("getTask="+getTask);

		tasks.add(putTask);
		tasks.add(getTask);

		PutTask updateTask = (PutTask)Utils.clone(putTask);
		updateTask.setName("Update");
		ValueGenerator vg = updateTask.getValueGenerator();
		setSeed(vg,200);
		tasks.add(updateTask);
		debug("updateTask="+updateTask);

	}
*/

	public List<Task> getTasks() {
		return tasks ;
	}

	private void setSeed(Object ogenerator, long seed) {
		if (ogenerator instanceof RandomValueGenerator) {
			RandomValueGenerator rgenerator = (RandomValueGenerator) ogenerator;
			rgenerator.setSeed(seed);
		} else {
			throw new RuntimeException("TODO");
		}
	}

	void debug(Object o) { System.out.println(">> MyFactory: "+o);}
}
