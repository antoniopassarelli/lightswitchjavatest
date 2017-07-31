package models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BrightenThread implements Runnable {

	private int threadNumber = 0;

	/*
	 * construtor with thread number
	 */
	public BrightenThread(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	@Override
	public void run() {
		Method[] allSwitchMethods = Switch.class.getMethods();
		Switch switchInstance = Switch.getInstance();
		System.out.println("Thread #" + threadNumber + " is going to perform the Brighten event");
		try {
			for (Method m : allSwitchMethods) {
				if (m.getName().equals("brighten")) {
					m.invoke(switchInstance);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println("Exception while trying to execute brighten method");
			e.printStackTrace();
		}
	}

}