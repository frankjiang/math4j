/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * DistanceEuclidean.java is PROPRIETARY/CONFIDENTIAL built in 上午12:07:23,
 * 2014年12月26日.
 * Use is subject to license terms.
 */
package com.frank.math.metric;

import com.frank.math.MathUtils;

/**
 * The implements of Euclidean distance.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class DistanceEuclidean implements Metric<double[]>
{
	/**
	 * Construct an instance of <tt>DistanceEuclidean</tt>.
	 */
	public DistanceEuclidean()
	{
	}

	/**
	 * @see com.frank.math.metric.Metric#distance(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public double distance(double[] a, double[] b)
	{
		return MathUtils.disEuclidean(a, b);
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public DistanceEuclidean clone()
	{
		return new DistanceEuclidean();
	}
}
