package com.rebelkeithy.fractals.sets;

import com.rebelkeithy.fractals.Complex;

public class MandelbrotSet implements FractalSet
{	
	public float contains(double a, double b, int maxIters)
	{
		Complex z = new Complex(0, 0);
		Complex c = new Complex(a, b);
		
		int i = 0;
		while(i < maxIters)
		{
			if(z.magSqr() > 8)
			{
				break;
			}
			else
			{
				//Mandelbrot
				z = function(z, c);
				
				//Mandelbar set (broken)
				//Complex n = z.inv();
				//n = n.mul(n);
			}
			i++;
		}
		
		if(i == maxIters)
			return i;
		

		Complex n = z.mul(z);
		z = n.add(c);
		return (float) (i + 1 - Math.log(Math.log(z.magnitude()))/Math.log(2));
		
		//return i;
		//return contains(new Complex(0, 0), new Complex(a, b), 0, maxIters);
	}
	
	public Complex function(Complex z, Complex c)
	{
		return z.mul(z).add(c);
	}
	
	public int contains(Complex z, Complex c, int i, int maxIters)
	{		
		if(Math.sqrt(z.magSqr()) > 4)
		{
			return i;
		}
		
		if(i < maxIters)
		{
			//if(i > 1)
			//System.out.println(i + ": " + z + " and " + c);
			Complex n = z.mul(z);
			n = n.add(c);
			return contains(n, c, i+1, maxIters);
		}
		else
		{
			return i;
		}
	}
	
	@Override
	public String toString()
	{
		return "Mandlebrot";
	}

	@Override
	public int defaultMaxIters() 
	{	
		return 50;
	}
}
