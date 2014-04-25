package com.rebelkeithy.fractals.sets;

import com.rebelkeithy.fractals.Complex;

public class Multibrot extends MandelbrotSet
{
	private int exponent;
	
	public Multibrot(int exponent)
	{
		this.exponent = exponent;
	}
	
	@Override
	public Complex function(Complex z, Complex c)
	{
		Complex n = new Complex(1, 0);
		
		for(int i = 0; i < exponent; i++)
		{
			n = n.mul(z);
		}
		
		return n.add(c);
	}
	
	@Override
	public String toString()
	{
		return "Multibrot(" + exponent + ")";
	}
}
