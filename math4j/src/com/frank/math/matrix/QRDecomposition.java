/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * QRDecomposition.java is PROPRIETARY/CONFIDENTIAL built in 10:11:39 PM, May 6,
 * 2014.
 * Use is subject to license terms.
 */
package com.frank.math.matrix;

import com.frank.math.MathUtils;
import com.frank.math.Messages;

/**
 * QR Decomposition.
 * <p>
 * Use m for the height, n for the width. For an m-by-n matrix A with m >= n,
 * the QR decomposition is an m-by-n orthogonal matrix Q and an n-by-n upper
 * triangular matrix R so that A = Q*R.
 * </p>
 * <p>
 * The QR decompostion always exists, even if the matrix does not have full
 * rank, so the constructor will never fail. The primary use of the QR
 * decomposition is in the least squares solution of nonsquare systems of
 * simultaneous linear equations. This will fail if isFullRank() returns false.
 * </p>
 * 
 * @author Jama
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class QRDecomposition implements java.io.Serializable
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= 6992876780257049636L;
	/*
	 * ------------------------
	 * Class variables
	 * ------------------------
	 */
	/**
	 * Array for internal storage of decomposition.
	 * 
	 * @serial internal array storage.
	 */
	private double[][]			QR;
	/**
	 * Row and column dimensions.
	 * 
	 * @serial column dimension.
	 * @serial row dimension.
	 */
	private int					height, width;
	/**
	 * Array for internal storage of diagonal of R.
	 * 
	 * @serial diagonal of R.
	 */
	private double[]			Rdiag;

	/*
	 * ------------------------
	 * Constructor
	 * ------------------------
	 */
	/**
	 * QR Decomposition, computed by Householder reflections.
	 * Structure to access R and the Householder vectors and compute Q.
	 * 
	 * @param A
	 *            Rectangular matrix
	 */
	public QRDecomposition(Matrix A)
	{
		// Initialize.
		QR = A.copyMatrix();
		height = A.height;
		width = A.width;
		Rdiag = new double[width];
		// Main loop.
		for (int k = 0; k < width; k++)
		{
			// Compute 2-norm of k-th column without under/overflow.
			double nrm = 0;
			for (int i = k; i < height; i++)
				nrm = MathUtils.hypot(nrm, QR[i][k]);
			if (nrm != 0.0)
			{
				// Form k-th Householder vector.
				if (QR[k][k] < 0)
					nrm = -nrm;
				for (int i = k; i < height; i++)
					QR[i][k] /= nrm;
				QR[k][k] += 1.0;
				// Apply transformation to remaining columns.
				for (int j = k + 1; j < width; j++)
				{
					double s = 0.0;
					for (int i = k; i < height; i++)
						s += QR[i][k] * QR[i][j];
					s = -s / QR[k][k];
					for (int i = k; i < height; i++)
						QR[i][j] += s * QR[i][k];
				}
			}
			Rdiag[k] = -nrm;
		}
	}

	/*
	 * ------------------------
	 * Public Methods
	 * ------------------------
	 */
	/**
	 * Is the matrix full rank?
	 * 
	 * @return true if R, and hence A, has full rank.
	 */
	public boolean isFullRank()
	{
		for (int j = 0; j < width; j++)
		{
			if (Rdiag[j] == 0)
				return false;
		}
		return true;
	}

	/**
	 * Return the Householder vectors
	 * 
	 * @return Lower trapezoidal matrix whose columns define the reflections
	 */
	public Matrix getH()
	{
		Matrix X = new Matrix(height, width);
		double[][] H = X.copyMatrix();
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				if (i >= j)
					H[i][j] = QR[i][j];
				else
					H[i][j] = 0.0;
		return X;
	}

	/**
	 * Return the upper triangular factor
	 * 
	 * @return R
	 */
	public Matrix getR()
	{
		Matrix X = new Matrix(width, width);
		double[][] R = X.copyMatrix();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < width; j++)
				if (i < j)
					R[i][j] = QR[i][j];
				else if (i == j)
					R[i][j] = Rdiag[i];
				else
					R[i][j] = 0.0;
		return X;
	}

	/**
	 * Generate and return the (economy-sized) orthogonal factor
	 * 
	 * @return Q
	 */
	public Matrix getQ()
	{
		Matrix X = new Matrix(height, width);
		double[][] Q = X.copyMatrix();
		for (int k = width - 1; k >= 0; k--)
		{
			for (int i = 0; i < height; i++)
				Q[i][k] = 0.0;
			Q[k][k] = 1.0;
			for (int j = k; j < width; j++)
				if (QR[k][k] != 0)
				{
					double s = 0.0;
					for (int i = k; i < height; i++)
						s += QR[i][k] * Q[i][j];
					s = -s / QR[k][k];
					for (int i = k; i < height; i++)
						Q[i][j] += s * QR[i][k];
				}
		}
		return X;
	}

	/**
	 * Least squares solution of A*X = B
	 * 
	 * @param B
	 *            A Matrix with as many rows as A and any number of columns.
	 * @return X that minimizes the two norm of Q*R*X-B.
	 * @exception IllegalArgumentException
	 *                Matrix row dimensions must agree.
	 * @exception RuntimeException
	 *                Matrix is rank deficient.
	 */
	public Matrix solve(Matrix B)
	{
		if (B.height != height)
			throw new IllegalArgumentException(
					Messages.getString("QRDecomposition.RowNotAgree")); //$NON-NLS-1$
		if (!this.isFullRank())
			throw new RuntimeException(Messages.getString("QRDecomposition.RankDeficient")); //$NON-NLS-1$
		// Copy right hand side
		int nx = B.width;
		double[][] X = B.copyMatrix();
		// Compute Y = transpose(Q)*B
		for (int k = 0; k < width; k++)
			for (int j = 0; j < nx; j++)
			{
				double s = 0.0;
				for (int i = k; i < height; i++)
					s += QR[i][k] * X[i][j];
				s = -s / QR[k][k];
				for (int i = k; i < height; i++)
					X[i][j] += s * QR[i][k];
			}
		// Solve R*X = Y;
		for (int k = width - 1; k >= 0; k--)
		{
			for (int j = 0; j < nx; j++)
				X[k][j] /= Rdiag[k];
			for (int i = 0; i < k; i++)
				for (int j = 0; j < nx; j++)
					X[i][j] -= X[k][j] * QR[i][k];
		}
		return new Matrix(X);
	}
}
