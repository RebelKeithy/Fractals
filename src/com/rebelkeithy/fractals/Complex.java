package com.rebelkeithy.fractals;

public class Complex
{
	private double a;
	private double b;
	
	public Complex(double a, double b)
	{
		this.a = a;
		this.b = b;
	}
	
	public double getA()
	{
		return a;
	}
	
	public double getB()
	{
		return b;
	}
	
	public Complex add(Complex c)
	{
		return new Complex(a + c.getA(), b + c.getB());
	}
	
	public Complex mul(Complex c)
	{
		return new Complex(a * c.getA() - b * c.getB(), a * c.getB() + b * c.getA());
	}
	
	public Complex inv()
	{
		return new Complex(b/(a*a + b*b), a/(a*a + b*b));
	}

	public double magnitude()
	{
		return Math.sqrt(a*a + b*b);
	}

	public double magSqr()
	{
		return a*a + b*b;
	}
	
	public String toString()
	{
		return "" + a + " + " + b + "i";
	}
}
