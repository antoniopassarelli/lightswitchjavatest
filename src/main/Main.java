package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import models.LightThread;

public class Main {

	private static final int MAX_THREADS_NUMBER = 10;

	public static void main(String[] args) {
		int randomNum = ThreadLocalRandom.current().nextInt(1, MAX_THREADS_NUMBER + 1);
		ExecutorService executor = Executors.newFixedThreadPool(randomNum);
		System.out.println(randomNum + " threads will run...");
		for (int i = 0; i < randomNum; i++) {
			Runnable runnable = new LightThread(i + 1);
			executor.execute(runnable);
		}
		executor.shutdown();
		System.out.println("Events happened.");
	}

}
