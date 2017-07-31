package models;

public class Switch {
	/*
	 * singleton instance
	 * for thread safe test
	 */
	private static Switch switchInstance = null;

	/*
	 * constants
	 */
	public final static int INIT_BRIGHTNESS = 5;
	public final static int MAX_BRIGHTNESS = 10;
	public final static int MIN_BRIGHTNESS = 0;

	/*
	 * light's features
	 */
	public int brightness = INIT_BRIGHTNESS;
	public boolean light = false;
	public boolean bulb = true;

	/*
	 * singleton constructor
	 * for thread safe test
	 */
	public static Switch getInstance() {
		if (null == switchInstance) {
			switchInstance = new Switch();
		}
		return switchInstance;

	}

	/*
	 * getters
	 */
	public int getBrightness() {
		return brightness;
	}

	public boolean isLight() {
		return light;
	}

	/*
	 * events
	 */
	public synchronized boolean switchOn() {
		if (bulb) {
			if (!light) {
				light = true;
				brightness = 5;
			}
			System.out.println("Light says: light is On");
		} else {
			System.out.println("Light says: there is no bulb!");
		}
		return light;
	}

	public synchronized boolean switchOff() {
		if (bulb) {
			if (light) {
				light = false;
			}
			System.out.println("Light says: light is Off");
		} else {
			System.out.println("Light says: there is no bulb!");
		}
		return light;
	}

	public synchronized int brighten() {
		if (bulb) {
			if (isLight()) {
				if (brightness < MAX_BRIGHTNESS) {
					brightness++;
				}
			} else {
				if (brightness==0) {
					brightness++;
					light=true;
				}
			}
			System.out.println("Light says: light is Brightened to " + brightness);
		} else {
			System.out.println("Light says: there is no bulb!");
		}
		return brightness;
	}

	public synchronized int dim() {
		if (bulb) {
			if (isLight()) {
				if (brightness > MIN_BRIGHTNESS) {
					brightness--;
					if (brightness==0) {
						light=false;
					}
				}
			}
			System.out.println("Light says: light is Dimmed to " + brightness);
		} else {
			System.out.println("Light says: there is no bulb!");
		}
		return brightness;
	}

	public synchronized boolean removeBulb() {
		if (bulb) {
			bulb = false;
			System.out.println("Light says: bulb is Removed");
		} else {
			System.out.println("Light says: there is no bulb!");
		}
		return bulb;
	}

	public synchronized boolean insertBulb() {
		if (!bulb) {
			bulb = true;
			System.out.println("Light says: bulb is Inserted");
		} else {
			System.out.println("Light says: bulb already present!");
		}
		return bulb;
	}

}
