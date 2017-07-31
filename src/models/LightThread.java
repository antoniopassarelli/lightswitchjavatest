package models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LightThread implements Runnable {

	private int threadNumber = -1;
	/*
	 * events methods names
	 */
	List<String> eventsName = Arrays.asList("switchOn", "switchOff", "brighten", "dim", "removeBulb", "insertBulb");

	/*
	 * construtor with thread number
	 */
	public LightThread(int threadNumber) {
		this.threadNumber = threadNumber;
	}

	@Override
	public void run() {
		Switch switchInstance = Switch.getInstance();
		Method pickMethod = pickMethod();
		System.out.print("Thread #" + threadNumber);
		System.out.println(" is going to perform the " + pickMethod.getName() + " event");
		try {
			pickMethod.invoke(switchInstance);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println("Exception while trying to execute " + pickMethod.getName());
			e.printStackTrace();
		}
	}

	/*
	 * picks a random method
	 */
	private Method pickMethod() {
		Method[] allSwitchMethods = Switch.class.getMethods();
		Method pickedMethod = null;
		boolean pickedCorrect = false;
		while (!pickedCorrect) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, allSwitchMethods.length);
			Method currentMethod = allSwitchMethods[randomNum];
			if (eventsName.contains(currentMethod.getName())) {
				pickedMethod = allSwitchMethods[randomNum];
				pickedCorrect = true;
			}
		}
		return pickedMethod;
	}

}
