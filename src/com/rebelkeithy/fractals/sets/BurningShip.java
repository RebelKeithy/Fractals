package com.rebelkeithy.fractals.sets;

import com.rebelkeithy.fractals.Complex;

public class BurningShip extends MandelbrotSet
{
	@Override
	public Complex function(Complex z, Complex c)
	{
		Complex n = new Complex(Math.abs(z.getA()), Math.abs(z.getB()));
		return n.mul(n).add(c);
	}
	
	@Override
	public String toString()
	{
		return "Burning Ship";
	}
	
	@Override
	public int defaultMaxIters() 
	{	
		return 10;
	}
}
