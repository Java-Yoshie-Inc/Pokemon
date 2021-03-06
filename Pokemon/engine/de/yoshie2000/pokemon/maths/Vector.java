package de.yoshie2000.pokemon.maths;

import java.awt.Point;

public class Vector {
	
	public float x = 0, y = 0;
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "[" + x + "|" + y + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Vector) {
			Vector v = (Vector) obj;
			return x == v.x && y == v.y;
		}
		return false;
	}
	
	public void add(Vector v) {
		x += v.x;
		y += v.y;
	}
	
	public Vector getAdded(Vector v) {
		return new Vector(x + v.x, y + v.y);
	}
	
	public void multiply(float value) {
		x *= value;
		y *= value;
	}
	
	public Vector getMultiplied(float value) {
		return new Vector(x * value, y * value);
	}
	
	public void limit(float value) {
		if(getMagnitude() > value) {
			Vector n = getNormalized();
			x = n.x * value;
			y = n.y * value;
		}
	}
	
	public Vector getLimited(float value) {
		if(getMagnitude() > value) {
			Vector n = getNormalized();
			n.x *= value;
			n.y *= value;
			return n;
		}
		return this;
	}
	
	public float getMagnitude() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public void normalize() {
		float length = (float) Math.sqrt(x * x + y * y);
		x /= length;
		y /= length;
	}
	
	public Vector getNormalized() {
		float length = (float) Math.sqrt(x * x + y * y);
		return new Vector(x / length, y / length);
	}
	
	public float getAngle() {
		return (float) (Math.atan(y / x) * 180 / Math.PI);
	}
	
	public float getDistance(Vector v) {
		float a = v.x - x;
		float b = v.y - y;
		return (float) Math.sqrt(a * a + b * b);
	}
	
	public float getDistance(float a, float b) {
		return getDistance(new Vector(a, b));
	}
	
	public float getDistance(Point p) {
		return getDistance(new Vector(p.x, p.y));
	}
	
	public boolean isBelow(Vector v) {
		Vector diff = getAdded(v.getMultiplied(-1));
		return diff.equals(new Vector(0f, 16f));
	}
	
}
