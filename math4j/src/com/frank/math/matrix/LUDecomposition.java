/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * LUDecomposition.java is built in 2013-7-13.
 */
package com.frank.math.matrix;

import com.frank.math.Messages;

/**
 * LU Decomposition.
 * <p>
 * Use m for the height, n for the width. For an m-by-n matrix A with m >= n,
 * the LU decomposition is an m-by-n unit lower triangular matrix L, an n-by-n
 * upper triangular matrix U, and a permutation vector pivot of length m so that
 * A(pivot,:) = L*U. If m < n, then L is m-by-m and U is m-by-n.
 * </p>
 * <p>
 * The LU decomposition with pivoting always exists, even if the matrix is
 * singular, so the constructor will never fail. The primary use of the LU
 * decomposition is in the solution of square systems of simultaneous linear
 * equations. This will fail if isNonsingular() returns false.
 * </p>
 * 
 * @author Jama
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class LUDecomposition implements java.io.Serializable
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= -2677502543179424687L;
	/**
	 * decomposition.
	 */
	protected double[][]		LU;
	/**
	 * The column dimension (width).
	 */
	protected int				height;
	/**
	 * the row dimension (height).
	 */
	protected int				width;
	/**
	 * The pivot sign.
	 */
	protected int				pivsign;
	/**
	 * The pivot vector.
	 */
	private int[]				pivot;

	/**
	 * LU decomposition structure to access L, U and piv.
	 * 
	 * @param matrix
	 *            rectangular matrix
	 */
	public LUDecomposition(Matrix matrix)
	{
		// Use a "left-looking", dot-product, Crout/Doolittle algorithm.
		LU = matrix.copyMatrix();
		height = matrix.height;
		width = matrix.width;
		pivot = new int[height];
		for (int i = 0; i < height; i++)
			pivot[i] = i;
		pivsign = 1;
		double[] LUrowi;
		double[] LUcolj = new double[height];
		// Outer loop.
		for (int j = 0; j < width; j++)
		{
			// Make a copy of the j-th column to localize references.
			for (int i = 0; i < height; i++)
				LUcolj[i] = LU[i][j];
			// Apply previous transformations.
			for (int i = 0; i < height; i++)
			{
				LUrowi = LU[i];
				// Most of the time is spent in the following dot product.
				int kmax = Math.min(i, j);
				double s = 0.0;
				for (int k = 0; k < kmax; k++)
					s += LUrowi[k] * LUcolj[k];
				LUrowi[j] = LUcolj[i] -= s;
			}
			// Find pivot and exchange if necessary.
			int p = j;
			for (int i = j + 1; i < height; i++)
				if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p]))
					p = i;
			if (p != j)
			{
				for (int k = 0; k < width; k++)
				{
					double t = LU[p][k];
					LU[p][k] = LU[j][k];
					LU[j][k] = t;
				}
				int k = pivot[p];
				pivot[p] = pivot[j];
				pivot[j] = k;
				pivsign = -pivsign;
			}
			// Compute multipliers.
			if (j < height & LU[j][j] != 0.0)
				for (int i = j + 1; i < height; i++)
					LU[i][j] /= LU[j][j];
		}
	}

	/**
	 * Returns the determinant.
	 * 
	 * @return the determinant of the input square matrix
	 * @exception IllegalArgumentException
	 *                throws if the matrix is not square
	 */
	public double determinant() throws IllegalArgumentException
	{
		if (height != width)
			throw new IllegalArgumentException(
					Messages.getString("LUDecomposition.NotSquare")); //$NON-NLS-1$
		double d = pivsign;
		for (int j = 0; j < width; j++)
			d *= LU[j][j];
		return d;
	}

	/**
	 * Return pivot permutation vector as a one-dimensional double array
	 * 
	 * @return (double) pivot
	 */
	public double[] getDoublePivot()
	{
		double[] vals = new double[height];
		for (int i = 0; i < height; i++)
			vals[i] = pivot[i];
		return vals;
	}

	/**
	 * Return lower triangular factor.
	 * 
	 * @return the lower triangular matrix (L)
	 */
	public Matrix getLowerTriangularFactor()
	{
		Matrix matrix = new Matrix(height, width);
		Object[][] data = matrix.getMatrix();
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				if (i > j)
					data[i][j] = LU[i][j];
				else if (i == j)
					data[i][j] = 1.0;
				else
					data[i][j] = 0.0;
		return matrix;
	}

	/**
	 * Returns pivot permutation vector.
	 * 
	 * @return pivot permutation vector
	 */
	public int[] getPivot()
	{
		int[] p = new int[height];
		for (int i = 0; i < height; i++)
			p[i] = pivot[i];
		return p;
	}

	/**
	 * Returns upper triangular factor.
	 * 
	 * @return upper triangular factor
	 */
	public Matrix getUpperTriangularFactor()
	{
		Matrix matrix = new Matrix(width, width);
		Object[][] U = matrix.getMatrix();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < width; j++)
				if (i <= j)
					U[i][j] = LU[i][j];
				else
					U[i][j] = 0.0;
		return matrix;
	}

	/**
	 * Returns <tt>true</tt> if the matrix is non-singular?
	 * 
	 * @return <tt>true</tt> if U, and hence A, is non-singular.
	 */
	public boolean isNonsingular()
	{
		for (int j = 0; j < width; j++)
			if (LU[j][j] == 0)
				return false;
		return true;
	}

	/**
	 * Solve A*X = B.
	 * 
	 * @param B
	 *            A Matrix with as many rows as A and any number of columns.
	 * @return X so that L*U*X = B(piv,:)
	 * @exception IllegalArgumentException
	 *                throws if matrix row dimensions not agree
	 * @exception RuntimeException
	 *                throws if matrix is singular.
	 */
	public Matrix solve(Matrix B) throws IllegalArgumentException,
			RuntimeException
	{
		if (B.height != height)
			throw new IllegalArgumentException(
					Messages.getString("LUDecomposition.RowNumNotAgree")); //$NON-NLS-1$
		if (!isNonsingular())
			throw new RuntimeException(Messages.getString("LUDecomposition.SingularMatrix")); //$NON-NLS-1$
		// Copy right hand side with pivoting
		int nx = B.width;
		Matrix Xmat = B.subMatrix(pivot, 0, nx);
		double[][] X = Xmat.copyMatrix();
		// Solve L*Y = B(piv,:)
		for (int k = 0; k < width; k++)
			for (int i = k + 1; i < width; i++)
				for (int j = 0; j < nx; j++)
					X[i][j] -= X[k][j] * LU[i][k];
		// Solve U*X = Y;
		for (int k = width - 1; k >= 0; k--)
		{
			for (int j = 0; j < nx; j++)
				X[k][j] /= LU[k][k];
			for (int i = 0; i < k; i++)
				for (int j = 0; j < nx; j++)
					X[i][j] -= X[k][j] * LU[i][k];
		}
		return new Matrix(X);
	}
}
