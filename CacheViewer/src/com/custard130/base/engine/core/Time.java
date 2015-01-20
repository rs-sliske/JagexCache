package com.custard130.base.engine.core;

public class Time {
	private static final long SECOND = 1000000000L;
	private static final long startTime = System.currentTimeMillis();
	private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

	public enum FORMAT {
		MILI, SEC, MIN, HOUR
	}

	public static double getTime() {
		return (double) System.nanoTime() / (double) SECOND;
	}

	public static String formatTime(final int seconds) {
		int temp = seconds;
		int hours = temp / 3600;
		int temp2 = temp % (hours * 3600);
		int mins = temp2 / 60;
		int secs = temp % 60;
		// StringBuilder sf = new StringBuilder();
		// sf.append(hours).append(":");
		// sf.append(mins).append(":");
		// sf.append(secs);
		// if(sf.length()==7)
		// sf.append(' ');
		// return sf.toString();

		return String.format("%2d : %2d : %2d", hours, mins, secs);
	}
	public static double ranFor(FORMAT f) {
		long curTime = System.currentTimeMillis();
		double temp = curTime - startTime;
		switch (f) {
			case MILI:
				return temp;
			case SEC:
				return temp / 1000.0;
			case MIN:
				return temp / 60000.0;
			case HOUR:
				return temp / 3600000.0;
		}
		return -1;
	}
	public static String getTimeRan() {
		int temp = (int) ranFor(FORMAT.SEC);
		// int hours = temp / 3600;
		// int mins = temp / 60;
		// int secs = temp % 60;
		// StringBuilder sf = new StringBuilder();
		// sf.append(hours).append(" : ");
		// sf.append(mins).append(" : ");
		// sf.append(secs);
		// return sf.toString();

		return formatTime(temp);
	}

	public static String getCurrentTime() {
		long temp = (System.currentTimeMillis() % MILLIS_IN_A_DAY);
		int temp2 = (int) (temp / 1000);
		return formatTime(temp2);
	}
}
