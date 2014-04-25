package com.rebelkeithy.fractals.sets;

import com.rebelkeithy.fractals.Complex;

public class JuliaSet implements FractalSet
{
	private Complex c;
	
	
	public JuliaSet(float a, float b)
	{
		c = new Complex(a, b);
	}

	@Override
	public float contains(double x, double y, int maxIters)
	{
		return contains(new Complex(x, y), 0, maxIters);
	}
	
	public float contains(Complex z, int i, int maxIters)
	{
		if(z.magSqr() > 100000 || i >= maxIters)
		{
			return i;
		}
		else
		{
			Complex n = z.mul(z);
			n = n.add(c);
			return contains(n, i+1, maxIters);
		}
	}

	@Override
	public int defaultMaxIters() 
	{
		return 30;
	}

}
