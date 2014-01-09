/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. StraightLine.java is built in 2013-2-14.
 */
package com.frank.math;

import java.awt.geom.Point2D;
import java.util.Arrays;

/**
 * The data structure of straight line. It is described in a continuous
 * interval, which means it matches all the definition of a straight line and
 * with no termination.
 * 
 * @author Frank Jiang
 * @version 1.0.0
 */
public class StraightLine
{
	/**
	 * The coefficients of the line.
	 */
	protected double[]	coefficients;

	/**
	 * Construct a straight line by determine all its coefficients,<br>
	 * according to the formula of line "Ax + By + C = 0",
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public StraightLine(double a, double b, double c)
	{
		// coefficients = new double[] { a, b, c };
		double[] temp = new double[] { Math.abs(a), Math.abs(b), Math.abs(c) };
		Arrays.sort(temp);
		double value = 0.0;
		if (temp[0] == 0.0)
		{
			if (temp[1] == 0.0)
				value = temp[2];
			else
				value = temp[1];
		}
		else
			value = temp[1];
		coefficients = new double[] { a / value, b / value, c / value };
	}

	/**
	 * Construct a straight line by determine the two points which the straight
	 * line passes.
	 * 
	 * @param p1
	 *            The first point.
	 * @param p2
	 *            The second point.
	 */
	public StraightLine(Point2D p1, Point2D p2)
	{
		if (p1.getX() == p2.getX())
			coefficients = new double[] { 1.0, 0, -p1.getX() };
		else
		{
			double k = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
			coefficients = new double[] { k, -1.0, p1.getY() - k * p1.getX() };
		}
	}

	/**
	 * Get the coefficients of the line.<br>
	 * According to the formula of line "Ax + By + C = 0",<br>
	 * all of the lines can be described in the formula above.
	 * 
	 * @return The coefficients in the formula as {A, B, C}.
	 */
	public double[] getCoefficients()
	{
		return coefficients;
	}

	/**
	 * Get the gradient of the straight line. It may return infinite value, use
	 * {@link java.lang.Double#isInfinite(double)} to judge.
	 * 
	 * @return The gradient.
	 */
	public double getGradient()
	{
		return -coefficients[0] / coefficients[1];
	}

	/**
	 * Get the intercept of the straight line. It may return infinite value, use
	 * {@link java.lang.Double#isInfinite(double)} to judge.
	 * 
	 * @return The intercept.
	 */
	public double getIntercept()
	{
		return -coefficients[2] / coefficients[1];
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return String.format("Line[%fX%+fY%+fC=0]", coefficients[0],
				coefficients[1], coefficients[2]);
	}

	/**
	 * Get the <tt>x</tt> value according to <tt>y</tt> value.<br>
	 * <code>x = -(By+C)/A</code>
	 * 
	 * @param y
	 *            The value of y.
	 * @return x
	 */
	public double x(double y)
	{
		return -(coefficients[1] * y + coefficients[2]) / coefficients[0];
	}

	/**
	 * Get the <tt>y</tt> value according to <tt>x</tt> value.<br>
	 * <code>y = -(Ax+C)/B</code>
	 * 
	 * @param x
	 *            The value of x.
	 * @return y
	 */
	public double y(double x)
	{
		return -(coefficients[0] * x + coefficients[2]) / coefficients[1];
	}
}
