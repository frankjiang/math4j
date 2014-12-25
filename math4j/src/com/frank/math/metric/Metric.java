/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * Metric.java is PROPRIETARY/CONFIDENTIAL built in 下午11:59:32, 2014年12月25日.
 * Use is subject to license terms.
 */
package com.frank.math.metric;

/**
 * The metric interface.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @param <T>
 *            the type of the instance to measure
 * @version 1.0.0
 */
public interface Metric<T> extends Cloneable
{
	/**
	 * Returns the distance between <code>a</code> and <code>b</code> under the
	 * metric system.
	 * 
	 * @param a
	 * @param b
	 * @return the distance
	 */
	public double distance(T a, T b);

	/**
	 * Clones one instance for this metric.
	 * 
	 * @return the clone
	 */
	public Metric<T> clone();
}
