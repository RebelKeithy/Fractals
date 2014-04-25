package com.rebelkeithy.fractals.sets;

public interface FractalSet
{
	public float contains(double x, double y, int maxIters);
	public int defaultMaxIters();
	public String toString();
}
