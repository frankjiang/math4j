/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * Ray.java is built in 2013-1-7.
 */
package com.frank.math;

import java.awt.geom.Point2D;

/**
 * The implementation of a ray.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class Ray
{
	/**
	 * The accuracy of matching intersections of the ray.
	 */
	protected double			accuracy	= 0.001;
	/**
	 * The radius of a right angle.
	 */
	public static final double	RIGHT		= Math.PI / 2.0;
	/**
	 * The anchor of the ray.
	 */
	protected Point2D			anchor;
	/**
	 * The X coordinate of the anchor.
	 */
	private final int			x0;
	/**
	 * The Y coordinate of the anchor.
	 */
	private final int			y0;
	/**
	 * The incidence angle of the ray in radius.
	 */
	protected double			theta;
	/**
	 * The flag of quadrant. Quadrant(1,2,3,4) to axis(0,-1,-2,-3 :
	 * X+,Y+,X-,Y-)
	 */
	private final int			quadrant;

	/**
	 * Construct an instance of ray.
	 * 
	 * @param anchor
	 *            the anchor of the ray
	 * @param theta
	 *            the incidence of the ray in radius
	 */
	public Ray(Point2D anchor, double theta)
	{
		this.anchor = anchor;
		this.x0 = (int) anchor.getX();
		this.y0 = (int) anchor.getY();
		if (theta < 0)
			this.theta = 2 * Math.PI + theta % (2 * Math.PI);
		else
			this.theta = theta % (2 * Math.PI);
		if (theta % RIGHT == 0)
			// on the axis
			quadrant = (int) (-theta / RIGHT);
		else
			// in the quadrant
			quadrant = (int) (theta / RIGHT + 1);
	}

	/**
	 * Returns true if the specified point is on the ray with accuracy.
	 * 
	 * @param x
	 *            the X coordinate of the specified point
	 * @param y
	 *            the Y coordinate of the specified point
	 * @return true if on the ray
	 */
	public boolean contains(double x, double y)
	{
		double dy = y - y0;
		double dx = x - x0;
		if (dx == 0.0 && dy == 0.0)
			return true;
		if (quadrant > 0)// on the accuracy or in the base point (0, 0)
		{
			switch (quadrant)
			{
				case 1:
					if (dx > 0 && dy > 0)
						break;
					return false;
				case 2:
					if (dx < 0 && dy > 0)
						break;
					return false;
				case 3:
					if (dx < 0 && dy < 0)
						break;
					return false;
				case 4:
					if (dx > 0 && dy < 0)
						break;
					return false;
			}
			return equals(y, y0 + Math.tan(theta) * dx, accuracy);
		}
		else
		// on axis
		{
			switch (quadrant)
			{
				default:// X+
					return equals(y, y0, accuracy) && dx >= 0;
				case -1:// Y+
					return equals(x, x0, accuracy) && dy >= 0;
				case -2:// X-
					return equals(y, y0, accuracy) && dx <= 0;
				case -3:// Y-
					return equals(x, x0, accuracy) && dy <= 0;
			}
		}
	}

	/**
	 * Returns true if the specified point is on the ray with accuracy.
	 * 
	 * @param p
	 *            the specified point
	 * @return true if on the ray
	 */
	public boolean contains(Point2D p)
	{
		return contains(p.getX(), p.getY());
	}

	/**
	 * Returns the value of the specified value in default accuracy.
	 * 
	 * @param d
	 *            the specified value
	 * @param accuracy
	 *            the accuracy to set
	 * @return the defaulted accuracy value
	 */
	public static double accuray(double d, double accuracy)
	{
		return ((int) (d / accuracy)) * accuracy;
	}

	/**
	 * Returns true if two specified values are equals remained in the default
	 * accuracy.
	 * 
	 * @param d1
	 * @param d2
	 * @param accuracy
	 * @return <code>true</code> if equals
	 */
	public static boolean equals(double d1, double d2, double accuracy)
	{
		return Math.abs(d1 - d2) < accuracy;
	}

	/**
	 * Returns accuracy.
	 * 
	 * @return the accuracy
	 */
	public double getAccuracy()
	{
		return accuracy;
	}

	/**
	 * Set accuracy.
	 * 
	 * @param accuracy
	 *            the value of accuracy
	 */
	public void setAccuracy(double accuracy)
	{
		this.accuracy = accuracy;
	}
}
