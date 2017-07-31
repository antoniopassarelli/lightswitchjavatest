package test;

import static org.junit.Assert.assertSame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import models.BrightenThread;
import models.Switch;

public class LightTest {

	@Test
	public void testMinBrighten() {
		/*
		 * the value brightness2 should be -1.
		 * indeed it's equal to brightness, ie 0
		 */
		System.out.println("Min Brightness Test");
		Switch switchInstance = new Switch();
		switchInstance.switchOn();
		int brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		int brightness2 = switchInstance.dim();
		System.out.println("brightness: " + brightness);
		System.out.println("brightness2: " + brightness2);
		System.out.println("Light is " +  (switchInstance.isLight() ? "on" : "off") );
		assertSame(brightness, brightness2);
		System.out.println();
		System.out.println();
	}
	
	@Test
	public void testMaxBrighten() {
		/*
		 * the value brightness2 should be 11.
		 * indeed it's equal to brightness, ie 10
		 */
		System.out.println("Max Brightness Test");
		Switch switchInstance = new Switch();
		switchInstance.switchOn();
		int brightness = switchInstance.brighten();
		brightness = switchInstance.brighten();
		brightness = switchInstance.brighten();
		brightness = switchInstance.brighten();
		brightness = switchInstance.brighten();
		int brightness2 = switchInstance.brighten();
		System.out.println("brightness: " + brightness);
		System.out.println("brightness2: " + brightness2);
		assertSame(brightness, brightness2);
		System.out.println();
		System.out.println();
	}


	@Test
	public void testInitBrighten() {
		System.out.println("Initial Brightness Test");
		Switch switchInstance = new Switch();
		switchInstance.switchOn();
		System.out.println("Initial brightness: " + Switch.INIT_BRIGHTNESS);
		System.out.println("Brightness: " + switchInstance.getBrightness());
		assertSame(switchInstance.getBrightness(), Switch.INIT_BRIGHTNESS);
		System.out.println();
		System.out.println();
	}

	@Test
	public void testDimmingOff() {
		System.out.println("Dimming Off Test");
		Switch switchInstance = new Switch();
		switchInstance.switchOn();
		int brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		System.out.println("brightness: " + brightness);
		System.out.println("Minimum brightness: " + Switch.MIN_BRIGHTNESS);
		System.out.println("Light is " +  (switchInstance.isLight() ? "on" : "off") );
		assertSame(false, switchInstance.isLight());
		System.out.println();
		System.out.println();
	}

	@Test
	public void testBrightningOn() {
		System.out.println("Brightning On Test");
		Switch switchInstance = new Switch();
		switchInstance.switchOn();
		int brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		brightness = switchInstance.dim();
		System.out.println("brightness: " + brightness);
		System.out.println("Minimum brightness: " + Switch.MIN_BRIGHTNESS);
		System.out.println("Light is " +  (switchInstance.isLight() ? "on" : "off") );
		brightness = switchInstance.brighten();
		System.out.println("Brightness: " + brightness);
		System.out.println("Light is " +  (switchInstance.isLight() ? "on" : "off") );
		assertSame(true, switchInstance.isLight());
		System.out.println();
		System.out.println();
	}

	@Test
	public void testThreadSafety() {
		System.out.println("Thread Safe Test:");
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Switch switchInstance = Switch.getInstance();
		/*
		 * light needs to be turned on out of the thread pool
		 */
		switchInstance.switchOn();
		System.out.println("Brightness: " + switchInstance.getBrightness());


		/*
		 * the size of the pool is between 1 and 10 this way the maximum brightness can
		 * be randomly tested
		 */
		int randomNum = ThreadLocalRandom.current().nextInt(1, Switch.MAX_BRIGHTNESS);
		System.out.println("There will be " + randomNum +" threads brightning the light");

		/*
		 * submits the threads
		 */
		for (int i = 0; i < randomNum; i++) {
			executor.submit(new BrightenThread(i + 1));
		}
		executor.shutdown();
		/*
		 * wait for executor to complete all threads
		 */
		while (!executor.isTerminated()) {
		}

		/*
		 * calculates the expected brightness level
		 */
		int expectedBrightness = randomNum + Switch.INIT_BRIGHTNESS;
		if (expectedBrightness > Switch.MAX_BRIGHTNESS) {
			/*
			 * the maximum is Switch.MAX_BRIGHTNESS. if the random value is greater than 5
			 * the expected brightness cannot be greater than Switch.MAX_BRIGHTNESS
			 */
			expectedBrightness = Switch.MAX_BRIGHTNESS;
		}

		/*
		 * test the expected value with the actual light's brightness
		 */
		assertSame(switchInstance.getBrightness(), expectedBrightness);
		System.out.println();
		System.out.println();
	}

	@Test
	public void testInsertBuld() {
		System.out.println("Already Inserted Bulb Test");
		Switch switchInstance = new Switch();
		boolean inserted = switchInstance.insertBulb();
		boolean inserted2 = switchInstance.insertBulb();
		System.out.println("inserted: " + inserted);
		System.out.println("inserted2: " + inserted2);
		assertSame(inserted, inserted2);
		System.out.println();
		System.out.println();
	}

	@Test
	public void testRemovedBuld() {
		System.out.println("Already Removed Bulb Test");
		Switch switchInstance = new Switch();
		boolean removed = switchInstance.removeBulb();
		boolean removed2 = switchInstance.removeBulb();
		System.out.println("removed: " + removed);
		System.out.println("removed2: " + removed2);
		assertSame(removed, removed2);
		System.out.println();
		System.out.println();
	}

	@Test
	public void testMultipleInstances() {
		System.out.println("Multiple Instances Test");
		Switch switchInstance1 = new Switch();
		Switch switchInstance2 = new Switch();
		switchInstance1.switchOn();
		System.out.println("switchInstance1 light's: " + switchInstance1.isLight());
		System.out.println("switchInstance2 light's: " + switchInstance2.isLight());
		assertSame(switchInstance1.isLight(), !switchInstance2.isLight());
		System.out.println();
		System.out.println();
	}

}
