package com.rebelkeithy.fractals.sets;

public class Lyapunov implements FractalSet
{
	String string;
	
	public Lyapunov(String string)
	{
		this.string = string;
	}

	@Override
	public float contains(double x, double y, int maxIters)
	{
		
		double sum = 0;
		int i; 
		double r;
		double z = 0.5;
		
		
		for(i = 0; i <= maxIters; i++)
		{
			if(string.charAt(i%string.length()) == 'A')
				r = z;
			else
				r = x;
			
			z = r * z * (1 - z);
			
			sum += Math.log(r*(1 - 2*z))/Math.log(2);
		}
		
		sum = sum/(double)(maxIters-1);
		
		return (float) sum;
	}

	@Override
	public int defaultMaxIters() 
	{
		return 100;
	}

}
