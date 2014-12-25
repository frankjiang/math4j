/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * DistanceMinkowski.java is PROPRIETARY/CONFIDENTIAL built in 上午12:03:32,
 * 2014年12月26日.
 * Use is subject to license terms.
 */
package com.frank.math.metric;

import com.frank.math.MathUtils;

/**
 * TODO
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class DistanceMinkowski implements Metric<double[]>
{
	protected int	k;

	/**
	 * Construct an instance of <tt>DistanceMinkowski</tt>.
	 */
	public DistanceMinkowski(int k)
	{
		if (k <= 0)
			throw new IllegalArgumentException("The coefficient k cannot be nonpositive.");
		this.k = k;
	}

	/**
	 * @see com.frank.math.metric.Metric#distance(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public double distance(double[] a, double[] b)
	{
		return MathUtils.disMinkowski(a, b, k);
	}

	/**
	 * Returns the <code>k</code> coefficient.
	 * 
	 * @return the <code>k</code> coefficient
	 */
	public int k()
	{
		return k;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public DistanceMinkowski clone()
	{
		return new DistanceMinkowski(k);
	}
}
