/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * FFT.java is built in 2013-1-7.
 */
package com.frank.math;

import com.frank.math.trans.DoubleFFT_1D;


/**
 * The implementation of one dimensional Fast Fourier Transformation.
 * <p>
 * This is the interface of recalling the Fourier Transformation part of Open
 * Source Physics Project.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class FFT1D
{
	/**
	 * Returns the circle convolution of two complex arrays.
	 * 
	 * @param x
	 *            complex arrays X
	 * @param y
	 *            complex arrays Y
	 * @return the circle convolution
	 */
	public static Complex[] cconvolve(Complex[] x, Complex[] y)
	{
		// should probably pad x and y with 0s so that they have same length
		// and are powers of 2
		if (x.length != y.length)
			throw new RuntimeException(Messages.getString("FFT1D.DimensionNotAgree")); //$NON-NLS-1$
		int N = x.length;
		// compute FFT of each sequence
		Complex[] a = FFT1D.fft(x);
		Complex[] b = FFT1D.fft(y);
		// point-wise multiply
		Complex[] c = new Complex[N];
		for (int i = 0; i < N; i++)
			c[i] = a[i].multiply(b[i]);
		// compute inverse FFT
		return FFT1D.ifft(c);
	}

	/**
	 * Returns the linear convolution of two complex arrays.
	 * 
	 * @param x
	 *            complex arrays X
	 * @param y
	 *            complex arrays Y
	 * @return the linear convolution
	 */
	public static Complex[] convolve(Complex[] x, Complex[] y)
	{
		Complex ZERO = new Complex(0, 0);
		Complex[] a = new Complex[2 * x.length];
		for (int i = 0; i < x.length; i++)
			a[i] = x[i];
		for (int i = x.length; i < 2 * x.length; i++)
			a[i] = ZERO;
		Complex[] b = new Complex[2 * y.length];
		for (int i = 0; i < y.length; i++)
			b[i] = y[i];
		for (int i = y.length; i < 2 * y.length; i++)
			b[i] = ZERO;
		return FFT1D.cconvolve(a, b);
	}

	/**
	 * Perform the 1D Fast Fourier Transformation.
	 * 
	 * @param x
	 *            the specified complex array
	 * @return the result of 1D Fast Fourier Transformation
	 */
	public static Complex[] fft(Complex[] x)
	{
		if (x == null || x.length == 0)
			throw new IllegalArgumentException(Messages.getString("FFT1D.NullArray")); //$NON-NLS-1$
		double[] data = new double[x.length * 2];
		for (int i = 0; i < x.length; i++)
		{
			data[i * 2] = x[i].real;
			data[i * 2 + 1] = x[i].imaginary;
		}
		new DoubleFFT_1D(x.length).complexForward(data);
		Complex[] y = new Complex[x.length];
		for (int i = 0; i < y.length; i++)
			y[i] = new Complex(data[i * 2], data[i * 2 + 1]);
		return y;
	}

	/**
	 * Perform the 1D Inverse Fast Fourier Transformation.
	 * 
	 * @param y
	 *            the specified complex array
	 * @param scale
	 *            <tt>true</tt> if scaling is need to be performed
	 * @return the result of 1D Inverse Fast Fourier Transformation
	 */
	public static Complex[] ifft(Complex[] y, boolean scale)
	{
		double[] data = new double[y.length * 2];
		for (int i = 0; i < y.length; i++)
		{
			data[i * 2] = y[i].real;
			data[i * 2 + 1] = y[i].imaginary;
		}
		new DoubleFFT_1D(y.length).complexInverse(data, scale);
		Complex[] x = new Complex[y.length];
		for (int i = 0; i < x.length; i++)
			x[i] = new Complex(data[i * 2], data[i * 2 + 1]);
		return x;
	}

	/**
	 * Perform the 1D Inverse Fast Fourier Transformation.
	 * 
	 * @param y
	 *            the specified complex array
	 * @return the result of 1D Inverse Fast Fourier Transformation
	 */
	public static Complex[] ifft(Complex[] y)
	{
		return ifft(y, true);
	}
}
