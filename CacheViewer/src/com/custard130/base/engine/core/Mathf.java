package com.custard130.base.engine.core;

public class Mathf {
	
	public static final float	PI	= (float) Math.PI;
	
	public static float wrap(float arg, float min, float max) {
		if (arg > max) return max;
		if (arg < min) return min;
		return arg;
	}
	
	public static float sin(float arg) {
		return (float) Math.sin(arg);
	}
	
	public static float cos(float arg) {
		return (float) Math.cos(arg);
	}
	
	public static float reverse_sin(float arg) {
		return (float) Math.asin(arg);
	}
	
	public static float reverse_cos(float arg) {
		return (float) Math.acos(arg);
	}
	
	public static float toRadians(float arg) {
		return (float) Math.toRadians(arg);
	}
	
	public static float square_root(final float arg) {
		return (float) Math.sqrt(arg);
	}
}
