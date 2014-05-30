/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * SingularValueDecomposition.java is built in 2013-7-13.
 */
package com.frank.math.matrix;

import java.io.Serializable;

import com.frank.math.MathUtils;

/**
 * Singular Value Decomposition.
 * <p>
 * For an m-by-n matrix A with m >= n, the singular value decomposition is an
 * m-by-n orthogonal matrix U, an n-by-n diagonal matrix S, and an n-by-n
 * orthogonal matrix V so that A = U*S*V'.
 * </p>
 * <p>
 * The singular values, sigma[k] = S[k][k], are ordered so that sigma[0] >=
 * sigma[1] >= ... >= sigma[n-1].
 * </p>
 * <p>
 * The singular value decompostion always exists, so the constructor will never
 * fail. The matrix condition number and the effective numerical rank can be
 * computed from this decomposition.
 * </p>
 * 
 * @author Jama
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class SingularValueDecomposition implements Serializable
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= 3027751179842530581L;
	/**
	 * The singular values array. Normal values of (S).
	 */
	protected double[]			singularValues;
	/**
	 * The left singular vectors (U).
	 */
	protected double[][]		leftSingularVectors;
	/**
	 * The right singular vectors (V).
	 */
	protected double[][]		rightSingularVectors;
	/**
	 * The width of the matrix to decompose.
	 */
	protected int				width;
	/**
	 * The height of the matrix to decompose.
	 */
	protected int				height;

	/**
	 * Construct an instance of <tt>SingularValueDecomposition</tt> which
	 * represents the result of singular value decomposition for the specified
	 * matrix.
	 * 
	 * @param matrix
	 *            the specified matrix
	 */
	public SingularValueDecomposition(Matrix matrix)
	{
		// Derived from LINPACK code.
		// Initialize.
		double[][] data = matrix.copyMatrix();
		width = matrix.width;
		height = matrix.height;
		int nu = Math.min(height, width);
		singularValues = new double[Math.min(height + 1, width)];
		leftSingularVectors = new double[height][nu];
		rightSingularVectors = new double[width][width];
		double[] e = new double[width];
		double[] work = new double[height];
		boolean wantu = true;
		boolean wantv = true;
		// Reduce A to bidiagonal form, storing the diagonal elements
		// in s and the super-diagonal elements in e.
		int nct = Math.min(height - 1, width);
		int nrt = Math.max(0, Math.min(width - 2, height));
		for (int k = 0; k < Math.max(nct, nrt); k++)
		{
			if (k < nct)
			{
				// Compute the transformation for the k-th column and
				// place the k-th diagonal in s[k].
				// Compute 2-norm of k-th column without under/overflow.
				singularValues[k] = 0;
				for (int i = k; i < height; i++)
					singularValues[k] = MathUtils.hypot(singularValues[k],
							data[i][k]);
				if (singularValues[k] != 0.0)
				{
					if (data[k][k] < 0.0)
						singularValues[k] = -singularValues[k];
					for (int i = k; i < height; i++)
						data[i][k] /= singularValues[k];
					data[k][k] += 1.0;
				}
				singularValues[k] = -singularValues[k];
			}
			for (int j = k + 1; j < width; j++)
			{
				if (k < nct & singularValues[k] != 0.0)
				{
					// Apply the transformation.
					double t = 0;
					for (int i = k; i < height; i++)
						t += data[i][k] * data[i][j];
					t = -t / data[k][k];
					for (int i = k; i < height; i++)
						data[i][j] += t * data[i][k];
				}
				// Place the k-th row of A into e for the
				// subsequent calculation of the row transformation.
				e[j] = data[k][j];
			}
			if (wantu & k < nct)
				// Place the transformation in U for subsequent back
				// multiplication.
				for (int i = k; i < height; i++)
					leftSingularVectors[i][k] = data[i][k];
			if (k < nrt)
			{
				// Compute the k-th row transformation and place the
				// k-th super-diagonal in e[k].
				// Compute 2-norm without under/overflow.
				e[k] = 0;
				for (int i = k + 1; i < width; i++)
					e[k] = MathUtils.hypot(e[k], e[i]);
				if (e[k] != 0.0)
				{
					if (e[k + 1] < 0.0)
						e[k] = -e[k];
					for (int i = k + 1; i < width; i++)
						e[i] /= e[k];
					e[k + 1] += 1.0;
				}
				e[k] = -e[k];
				if (k + 1 < height & e[k] != 0.0)
				{
					// Apply the transformation.
					for (int i = k + 1; i < height; i++)
						work[i] = 0.0;
					for (int j = k + 1; j < width; j++)
						for (int i = k + 1; i < height; i++)
							work[i] += e[j] * data[i][j];
					for (int j = k + 1; j < width; j++)
					{
						double t = -e[j] / e[k + 1];
						for (int i = k + 1; i < height; i++)
							data[i][j] += t * work[i];
					}
				}
				if (wantv)
					// Place the transformation in V for subsequent
					// back multiplication.
					for (int i = k + 1; i < width; i++)
						rightSingularVectors[i][k] = e[i];
			}
		}
		// Set up the final bidiagonal matrix or order p.
		int p = Math.min(width, height + 1);
		if (nct < width)
			singularValues[nct] = data[nct][nct];
		if (height < p)
			singularValues[p - 1] = 0.0;
		if (nrt + 1 < p)
			e[nrt] = data[nrt][p - 1];
		e[p - 1] = 0.0;
		// If required, generate U.
		if (wantu)
		{
			for (int j = nct; j < nu; j++)
			{
				for (int i = 0; i < height; i++)
					leftSingularVectors[i][j] = 0.0;
				leftSingularVectors[j][j] = 1.0;
			}
			for (int k = nct - 1; k >= 0; k--)
				if (singularValues[k] != 0.0)
				{
					for (int j = k + 1; j < nu; j++)
					{
						double t = 0;
						for (int i = k; i < height; i++)
							t += leftSingularVectors[i][k]
									* leftSingularVectors[i][j];
						t = -t / leftSingularVectors[k][k];
						for (int i = k; i < height; i++)
							leftSingularVectors[i][j] += t
									* leftSingularVectors[i][k];
					}
					for (int i = k; i < height; i++)
						leftSingularVectors[i][k] = -leftSingularVectors[i][k];
					leftSingularVectors[k][k] = 1.0 + leftSingularVectors[k][k];
					for (int i = 0; i < k - 1; i++)
						leftSingularVectors[i][k] = 0.0;
				}
				else
				{
					for (int i = 0; i < height; i++)
						leftSingularVectors[i][k] = 0.0;
					leftSingularVectors[k][k] = 1.0;
				}
		}
		// If required, generate V.
		if (wantv)
			for (int k = width - 1; k >= 0; k--)
			{
				if (k < nrt & e[k] != 0.0)
					for (int j = k + 1; j < nu; j++)
					{
						double t = 0;
						for (int i = k + 1; i < width; i++)
							t += rightSingularVectors[i][k]
									* rightSingularVectors[i][j];
						t = -t / rightSingularVectors[k + 1][k];
						for (int i = k + 1; i < width; i++)
							rightSingularVectors[i][j] += t
									* rightSingularVectors[i][k];
					}
				for (int i = 0; i < width; i++)
					rightSingularVectors[i][k] = 0.0;
				rightSingularVectors[k][k] = 1.0;
			}
		// Main iteration loop for the singular values.
		int pp = p - 1;
		int iter = 0;
		double eps = Math.pow(2.0, -52.0);
		double tiny = Math.pow(2.0, -966.0);
		while (p > 0)
		{
			int k, kase;
			// Here is where a test for too many iterations would go.
			// This section of the program inspects for
			// negligible elements in the s and e arrays. On
			// completion the variables kase and k are set as follows.
			// kase = 1 if s(p) and e[k-1] are negligible and k<p
			// kase = 2 if s(k) is negligible and k<p
			// kase = 3 if e[k-1] is negligible, k<p, and
			// s(k), ..., s(p) are not negligible (qr step).
			// kase = 4 if e(p-1) is negligible (convergence).
			for (k = p - 2; k >= -1; k--)
			{
				if (k == -1)
					break;
				if (Math.abs(e[k]) <= tiny
						+ eps
						* (Math.abs(singularValues[k]) + Math
								.abs(singularValues[k + 1])))
				{
					e[k] = 0.0;
					break;
				}
			}
			if (k == p - 2)
				kase = 4;
			else
			{
				int ks;
				for (ks = p - 1; ks >= k; ks--)
				{
					if (ks == k)
						break;
					double t = (ks != p ? Math.abs(e[ks]) : 0.)
							+ (ks != k + 1 ? Math.abs(e[ks - 1]) : 0.);
					if (Math.abs(singularValues[ks]) <= tiny + eps * t)
					{
						singularValues[ks] = 0.0;
						break;
					}
				}
				if (ks == k)
					kase = 3;
				else if (ks == p - 1)
					kase = 1;
				else
				{
					kase = 2;
					k = ks;
				}
			}
			k++;
			// Perform the task indicated by kase.
			switch (kase)
			{
			// Deflate negligible s(p).
				case 1:
				{
					double f = e[p - 2];
					e[p - 2] = 0.0;
					for (int j = p - 2; j >= k; j--)
					{
						double t = MathUtils.hypot(singularValues[j], f);
						double cs = singularValues[j] / t;
						double sn = f / t;
						singularValues[j] = t;
						if (j != k)
						{
							f = -sn * e[j - 1];
							e[j - 1] = cs * e[j - 1];
						}
						if (wantv)
							for (int i = 0; i < width; i++)
							{
								t = cs * rightSingularVectors[i][j] + sn
										* rightSingularVectors[i][p - 1];
								rightSingularVectors[i][p - 1] = -sn
										* rightSingularVectors[i][j] + cs
										* rightSingularVectors[i][p - 1];
								rightSingularVectors[i][j] = t;
							}
					}
				}
					break;
				// Split at negligible s(k).
				case 2:
				{
					double f = e[k - 1];
					e[k - 1] = 0.0;
					for (int j = k; j < p; j++)
					{
						double t = MathUtils.hypot(singularValues[j], f);
						double cs = singularValues[j] / t;
						double sn = f / t;
						singularValues[j] = t;
						f = -sn * e[j];
						e[j] = cs * e[j];
						if (wantu)
							for (int i = 0; i < height; i++)
							{
								t = cs * leftSingularVectors[i][j] + sn
										* leftSingularVectors[i][k - 1];
								leftSingularVectors[i][k - 1] = -sn
										* leftSingularVectors[i][j] + cs
										* leftSingularVectors[i][k - 1];
								leftSingularVectors[i][j] = t;
							}
					}
				}
					break;
				// Perform one qr step.
				case 3:
				{
					// Calculate the shift.
					double scale = Math.max(Math.max(Math.max(
							Math.max(Math.abs(singularValues[p - 1]),
									Math.abs(singularValues[p - 2])),
							Math.abs(e[p - 2])), Math.abs(singularValues[k])),
							Math.abs(e[k]));
					double sp = singularValues[p - 1] / scale;
					double spm1 = singularValues[p - 2] / scale;
					double epm1 = e[p - 2] / scale;
					double sk = singularValues[k] / scale;
					double ek = e[k] / scale;
					double b = ((spm1 + sp) * (spm1 - sp) + epm1 * epm1) / 2.0;
					double c = sp * epm1 * (sp * epm1);
					double shift = 0.0;
					if (b != 0.0 | c != 0.0)
					{
						shift = Math.sqrt(b * b + c);
						if (b < 0.0)
							shift = -shift;
						shift = c / (b + shift);
					}
					double f = (sk + sp) * (sk - sp) + shift;
					double g = sk * ek;
					// Chase zeros.
					for (int j = k; j < p - 1; j++)
					{
						double t = MathUtils.hypot(f, g);
						double cs = f / t;
						double sn = g / t;
						if (j != k)
							e[j - 1] = t;
						f = cs * singularValues[j] + sn * e[j];
						e[j] = cs * e[j] - sn * singularValues[j];
						g = sn * singularValues[j + 1];
						singularValues[j + 1] = cs * singularValues[j + 1];
						if (wantv)
							for (int i = 0; i < width; i++)
							{
								t = cs * rightSingularVectors[i][j] + sn
										* rightSingularVectors[i][j + 1];
								rightSingularVectors[i][j + 1] = -sn
										* rightSingularVectors[i][j] + cs
										* rightSingularVectors[i][j + 1];
								rightSingularVectors[i][j] = t;
							}
						t = MathUtils.hypot(f, g);
						cs = f / t;
						sn = g / t;
						singularValues[j] = t;
						f = cs * e[j] + sn * singularValues[j + 1];
						singularValues[j + 1] = -sn * e[j] + cs
								* singularValues[j + 1];
						g = sn * e[j + 1];
						e[j + 1] = cs * e[j + 1];
						if (wantu && j < height - 1)
							for (int i = 0; i < height; i++)
							{
								t = cs * leftSingularVectors[i][j] + sn
										* leftSingularVectors[i][j + 1];
								leftSingularVectors[i][j + 1] = -sn
										* leftSingularVectors[i][j] + cs
										* leftSingularVectors[i][j + 1];
								leftSingularVectors[i][j] = t;
							}
					}
					e[p - 2] = f;
					iter = iter + 1;
				}
					break;
				// Convergence.
				case 4:
				{
					// Make the singular values positive.
					if (singularValues[k] <= 0.0)
					{
						singularValues[k] = singularValues[k] < 0.0 ? -singularValues[k]
								: 0.0;
						if (wantv)
							for (int i = 0; i <= pp; i++)
								rightSingularVectors[i][k] = -rightSingularVectors[i][k];
					}
					// Order the singular values.
					while (k < pp)
					{
						if (singularValues[k] >= singularValues[k + 1])
							break;
						double t = singularValues[k];
						singularValues[k] = singularValues[k + 1];
						singularValues[k + 1] = t;
						if (wantv && k < width - 1)
							for (int i = 0; i < width; i++)
							{
								t = rightSingularVectors[i][k + 1];
								rightSingularVectors[i][k + 1] = rightSingularVectors[i][k];
								rightSingularVectors[i][k] = t;
							}
						if (wantu && k < height - 1)
							for (int i = 0; i < height; i++)
							{
								t = leftSingularVectors[i][k + 1];
								leftSingularVectors[i][k + 1] = leftSingularVectors[i][k];
								leftSingularVectors[i][k] = t;
							}
						k++;
					}
					iter = 0;
					p--;
				}
					break;
			}
		}
	}

	/**
	 * Returns the ratio of largest to smallest singular value.
	 * 
	 * @return max(S)/min(S)
	 */
	public double ratio()
	{
		return singularValues[0] / singularValues[Math.min(height, width) - 1];
	}

	/**
	 * Returns the diagonal matrix of singular values.
	 * 
	 * @return singular values
	 */
	public Matrix getS()
	{
		Matrix matrix = new Matrix(width, width);
		Object[][] diagonalMatrix = matrix.getMatrix();
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < width; j++)
				diagonalMatrix[i][j] = 0.0;
			diagonalMatrix[i][i] = this.singularValues[i];
		}
		return matrix;
	}

	/**
	 * Returns the one-dimensional array of singular values.
	 * 
	 * @return diagonal of singular values.
	 */
	public double[] getSingularValues()
	{
		return singularValues;
	}

	/**
	 * Returns the left singular vectors.
	 * 
	 * @return the left singular vectors
	 */
	public Matrix getLeftSingularVectors()
	{
		return new Matrix(leftSingularVectors);
	}

	/**
	 * Returns the right singular vectors.
	 * 
	 * @return right singular vectors
	 */
	public Matrix getRightSingularVectors()
	{
		return new Matrix(rightSingularVectors);
	}

	/**
	 * Returns the maximum singular value.
	 * 
	 * @return max(S)
	 */
	public double maximumSingularValue()
	{
		return singularValues[0];
	}

	/**
	 * Returns effective numerical matrix rank.
	 * 
	 * @return number of non-negligible singular values.
	 */
	public int rank()
	{
		double eps = Math.pow(2.0, -52.0);
		double tol = Math.max(height, width) * singularValues[0] * eps;
		int r = 0;
		for (double singularValue : singularValues)
			if (singularValue > tol)
				r++;
		return r;
	}
}
