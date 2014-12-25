/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * DistanceMahalanobis.java is PROPRIETARY/CONFIDENTIAL built in 上午12:08:35,
 * 2014年12月26日.
 * Use is subject to license terms.
 */
package com.frank.math.metric;

import Jama.Matrix;

/**
 * TODO
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class DistanceMahalanobis implements Metric<double[]>
{
	/**
	 * Construct an instance of <tt>DistanceMahalanobis</tt>.
	 */
	public DistanceMahalanobis()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see com.frank.math.metric.Metric#distance(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public double distance(double[] a, double[] b)
	{
		double[][] del = new double[3][1];
		for (int m = 0; m < 3; m++)
			for (int n = 0; n < 1; n++)
				del[m][n] = a[m] - b[m];
		Matrix M1 = new Matrix(del);
		Matrix M2 = M1.transpose();
		double[][] covar = new double[3][3];
		for (int m = 0; m < 3; m++)
			for (int n = 0; n < 3; n++)
				covar[m][n] += (a[m] - b[m]) * (a[n] - b[n]);
		Matrix cov = new Matrix(covar);
		Matrix covInv = cov.inverse();
		Matrix temp1 = M2.times(covInv);
		Matrix temp2 = temp1.times(M1);
		double dist = temp2.trace();
		if (dist > 0.)
			dist = Math.sqrt(dist);
		return dist;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public DistanceMahalanobis clone()
	{
		return new DistanceMahalanobis();
	}
}
