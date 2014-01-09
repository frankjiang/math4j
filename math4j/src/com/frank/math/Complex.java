/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. Complex.java is built in 2013-1-7.
 */
package com.frank.math;

/**
 * The definition of a complex.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public final class Complex implements java.lang.Cloneable,
		java.io.Serializable, Comparable<Complex>
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= 1951016925494857874L;

	/**
	 * Returns the quotient of a/b
	 * 
	 * @param a
	 * @param b
	 * @return a/b
	 */
	public static Complex divide(Complex a, Complex b)
	{
		double base = b.real * b.real + b.imaginary * b.imaginary;
		return new Complex(
				(a.real * b.real + a.imaginary * b.imaginary) / base,
				(a.imaginary * b.real - a.real * b.imaginary) / base);
	}

	/**
	 * Returns the subtraction of a-b
	 * 
	 * @param a
	 * @param b
	 * @return a-b
	 */
	public static Complex minus(Complex a, Complex b)
	{
		double real = a.real - b.real;
		double imag = a.imaginary - b.imaginary;
		return new Complex(real, imag);
	}

	/**
	 * Returns a*b
	 * 
	 * @param a
	 * @param b
	 * @return a*b
	 */
	public static Complex multiply(Complex a, Complex b)
	{
		double real = a.real * b.real - a.imaginary * b.imaginary;
		double imag = a.real * b.imaginary + a.imaginary * b.real;
		return new Complex(real, imag);
	}

	/**
	 * Returns a+b
	 * 
	 * @param a
	 * @param b
	 * @return a+b
	 */
	public static Complex plus(Complex a, Complex b)
	{
		double real = a.real + b.real;
		double imag = a.imaginary + b.imaginary;
		Complex sum = new Complex(real, imag);
		return sum;
	}

	/**
	 * the imaginary part
	 */
	public double	imaginary;
	/**
	 * the real part
	 */
	public double	real;

	/**
	 * Construct an instance of Complex(0+0*i).
	 */
	public Complex()
	{
		real = 0.0;
		imaginary = 0.0;
	}

	/**
	 * Construct an instance of Complex with specified real part.
	 * 
	 * @param real
	 */
	public Complex(double real)
	{
		this.real = real;
		imaginary = 0.0;
	}

	/**
	 * Construct an instance of Complex with specified real part and imaginary
	 * part.
	 * 
	 * @param real
	 * @param imaginary
	 */
	public Complex(double real, double imaginary)
	{
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Returns &radic;(a<sup>2</sup>+b<sup>2</sup>)
	 * 
	 * @return &radic;(a<sup>2</sup>+b<sup>2</sup>)
	 */
	public double abs()
	{
		return Math.hypot(real, imaginary);
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Complex clone()
	{
		return new Complex(real, imaginary);
	}

	/**
	 * Returns the conjugate complex.
	 * 
	 * @return conjugate complex
	 */
	public Complex conjugate()
	{
		return new Complex(real, -imaginary);
	}

	/**
	 * Returns the cosine value of current complex.
	 * 
	 * @return cosine
	 */
	public Complex cos()
	{
		return new Complex(Math.cos(real) * Math.cosh(imaginary),
				-Math.sin(real) * Math.sinh(imaginary));
	}

	/**
	 * Returns the quotient of current complex divided by b.
	 * 
	 * @param b
	 * @return the quotient
	 */
	public Complex divide(Complex b)
	{
		double base = b.real * b.real + b.imaginary * b.imaginary;
		return new Complex((real * b.real + imaginary * b.imaginary) / base,
				(imaginary * b.real - real * b.imaginary) / base);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Complex)
		{
			Complex c = (Complex) obj;
			return real == c.real && imaginary == c.imaginary;
		}
		else
			return super.equals(obj);
	}

	/**
	 * Returns the value with the current complex as the exponent.
	 * 
	 * @return the value
	 */
	public Complex exp()
	{
		return new Complex(Math.exp(real) * Math.cos(imaginary), Math.exp(real)
				* Math.sin(imaginary));
	}

	/**
	 * Returns the subtraction of current complex minuses b.
	 * 
	 * @param b
	 * @return the subtraction
	 */
	public Complex minus(Complex b)
	{
		Complex a = this;
		double real = a.real - b.real;
		double imag = a.imaginary - b.imaginary;
		return new Complex(real, imag);
	}

	/**
	 * Returns the product of current complex multiplies b.
	 * 
	 * @param b
	 * @return the product
	 */
	public Complex multiply(Complex b)
	{
		Complex a = this;
		double real = a.real * b.real - a.imaginary * b.imaginary;
		double imag = a.real * b.imaginary + a.imaginary * b.real;
		return new Complex(real, imag);
	}

	/**
	 * Returns the product of current complex multiplies a real number, alpha.
	 * 
	 * @param alpha
	 * @return the product
	 */
	public Complex multiply(double alpha)
	{
		return new Complex(alpha * real, alpha * imaginary);
	}

	/**
	 * Returns the phase of current complex (between -pi and pi).
	 * 
	 * @return arctan(b / a)
	 */
	public double phase()
	{
		return Math.atan2(imaginary, real);
	}

	/**
	 * Returns the summary of current complex plus b.
	 * 
	 * @param b
	 * @return the summary
	 */
	public Complex plus(Complex b)
	{
		Complex a = this; // invoking object
		double real = a.real + b.real;
		double imag = a.imaginary + b.imaginary;
		return new Complex(real, imag);
	}

	/**
	 * Returns the reciprocal of current complex.
	 * 
	 * @return the reciprocal
	 */
	public Complex reciprocal()
	{
		double scale = real * real + imaginary * imaginary;
		return new Complex(real / scale, -imaginary / scale);
	}

	/**
	 * Returns the sine of current complex.
	 * 
	 * @return the sine
	 */
	public Complex sin()
	{
		return new Complex(Math.sin(real) * Math.cosh(imaginary),
				Math.cos(real) * Math.sinh(imaginary));
	}

	/**
	 * Returns the tangent of current complex.
	 * 
	 * @return the tangent
	 */
	public Complex tan()
	{
		return sin().divide(cos());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		if (real == 0.0)
		{
			if (imaginary == 0.0)
				return "0"; //$NON-NLS-1$
			else
				return String.format("%.4f*i", imaginary); //$NON-NLS-1$
		}
		else
		{
			if (imaginary == 0.0)
				return String.format("%.4f", real); //$NON-NLS-1$
			else
				return String.format("%.4f%+.4f*i", real, imaginary); //$NON-NLS-1$
		}
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Complex o)
	{
		if (real == o.real)
			return Double.compare(imaginary, o.imaginary);
		else
			return Double.compare(real, o.real);
	}
}
