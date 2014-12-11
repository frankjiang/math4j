/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. Matrix.java is built in 2013-7-13.
 */
package com.frank.math.matrix;

import com.frank.math.Messages;

/**
 * Defines a numerical matrix.
 * <p>
 * In this class, a numerical matrix is defined. The related elementary
 * operations for a matrix is implemented. The rank computing is implemented
 * according to singular value decomposition which implemented in
 * {@linkplain SingularValueDecomposition}.
 * </p>
 * 
 * @author Jama
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.1.0
 */
public class Matrix extends SerializableMatrix<Double>
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= -8386997590480153092L;

	/**
	 * Returns a / b.
	 * 
	 * @param a
	 * @param b
	 * @return a / b
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 */
	public static Matrix divide(Matrix a, Matrix b)
			throws IllegalArgumentException
	{
		if (a.width != b.width || a.height != b.height)
			throw new IllegalArgumentException(String.format(
					Messages.getString("Matrix.DimensionNotAgree"), a.width, //$NON-NLS-1$
					a.height, b.width, b.height));
		Matrix d = new Matrix(a.width, a.height);
		for (int x = 0; x < a.width; x++)
			for (int y = 0; y < a.height; y++)
				d.set(x, y, a.value(x, y) / b.value(x, y));
		return d;
	}

	/**
	 * Returns m * k.
	 * 
	 * @param m
	 * @param k
	 * @return m * k
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 */
	public static Matrix multiply(Matrix m, double k)
			throws IllegalArgumentException
	{
		Matrix d = new Matrix(m.width, m.height);
		for (int x = 0; x < m.width; x++)
			for (int y = 0; y < m.height; y++)
				d.data[y][x] = m.value(x, y) * k;
		return d;
	}

	/**
	 * Returns a * b.
	 * 
	 * @param a
	 * @param b
	 * @return a * b
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 */
	public static Matrix multiply(Matrix a, Matrix b)
			throws IllegalArgumentException
	{
		if (a.width != b.height)
			throw new IllegalArgumentException(String.format(
					Messages.getString("Matrix.CannotMultiply"), //$NON-NLS-1$
					a.width, a.height, b.width, b.height));
		Matrix d = new Matrix(a.width, b.height);
		int s = a.width;
		double tmp;
		for (int x = 0; x < a.height; x++)
			for (int y = 0; y < b.width; y++)
			{
				tmp = 0.0;
				for (int k = 0; k < s; k++)
					tmp += a.value(k, y) * b.value(x, k);
				d.data[y][x] = tmp;
			}
		return d;
	}

	/**
	 * Returns a + b.
	 * 
	 * @param a
	 * @param b
	 * @return a + b
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 */
	public static Matrix plus(Matrix a, Matrix b)
			throws IllegalArgumentException
	{
		if (a.width != b.width || a.height != b.height)
			throw new IllegalArgumentException(String.format(
					Messages.getString("Matrix.DimensionNotAgree"), a.width, //$NON-NLS-1$
					a.height, b.width, b.height));
		Matrix d = new Matrix(a.width, a.height);
		for (int x = 0; x < a.width; x++)
			for (int y = 0; y < a.height; y++)
				d.data[y][x] = a.value(x, y) + b.value(x, y);
		return d;
	}

	/**
	 * Returns a - b.
	 * 
	 * @param a
	 * @param b
	 * @return a - b
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 */
	public static Matrix subtract(Matrix a, Matrix b)
			throws IllegalArgumentException
	{
		if (a.width != b.width || a.height != b.height)
			throw new IllegalArgumentException(String.format(
					Messages.getString("Matrix.DimensionNotAgree"), a.width, //$NON-NLS-1$
					a.height, b.width, b.height));
		Matrix d = new Matrix(a.width, a.height);
		for (int x = 0; x < a.width; x++)
			for (int y = 0; y < a.height; y++)
				d.data[y][x] = a.value(x, y) - b.value(x, y);
		return d;
	}

	/**
	 * Returns the transpose matrix of <tt>m</tt>.
	 * 
	 * @param m
	 *            the specified matrix to transpose
	 * @return the transposed matrix
	 */
	public static Matrix transpose(Matrix m)
	{
		int w = m.height, h = m.width;
		Object[][] data = new Object[h][w];
		for (int y = 0; y < h; y++)
			for (int x = 0; x < w; x++)
				data[y][x] = m.data[x][y];
		Matrix t = new Matrix();
		t.data = data;
		t.width = w;
		t.height = h;
		return t;
	}

	/**
	 * Construct an empty instance of <tt>Matrix</tt>. The data matrix will be
	 * set as <tt>null</tt> and the dimension(width,height) as
	 * <code>(-1,-1)</code>.
	 */
	protected Matrix()
	{
		super();
	}

	/**
	 * Construct an instance of <tt>Matrix</tt> according to the two-dimensional
	 * double array.
	 * 
	 * @param matrix
	 *            the specified two-dimensional <tt>double</tt> array
	 */
	public Matrix(double[][] matrix)
	{
		height = matrix.length;
		width = matrix[0].length;
		data = new Object[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				data[y][x] = matrix[y][x];
	}

	/**
	 * Construct an instance of <tt>Matrix</tt> with specified dimension..
	 * Allocate the memory but not initialize it.
	 * 
	 * @param width
	 *            the width of the matrix
	 * @param height
	 *            the height of the matrix
	 */
	protected Matrix(int width, int height)
	{
		super(width, height);
	}

	/**
	 * Construct an instance of <tt>Matrix</tt> with specified default element
	 * value.
	 * 
	 * @param width
	 *            the width of the matrix
	 * @param height
	 *            the height of the matrix
	 * @param value
	 *            the default value of the matrix.
	 */
	public Matrix(int width, int height, double value)
	{
		super(width, height, value);
	}

	/**
	 * Returns the copy of the matrix.
	 * 
	 * @return the copy
	 */
	public double[][] copyMatrix()
	{
		double[][] m = new double[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				m[y][x] = value(x, y);
		return m;
	}

	/**
	 * Left divide the current matrix and returns the result of division. <br>
	 * <code>curr = curr / m</code>
	 * 
	 * @param d
	 *            the matrix to divide
	 * @return curr / m
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 */
	public Matrix leftDivide(Matrix d) throws IllegalArgumentException
	{
		realloc(Matrix.divide(this, d));
		return this;
	}

	/**
	 * Left divide the current matrix and returns the result of division. <br>
	 * <code>curr = curr / m</code>
	 * <p>
	 * This method has the same affect as {@linkplain #leftDivide(Matrix)}.
	 * </p>
	 * 
	 * @param d
	 *            the matrix to divide
	 * @return curr / m
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 * @see #leftDivide(Matrix)
	 */
	public Matrix divide(Matrix d) throws IllegalArgumentException
	{
		realloc(Matrix.divide(this, d));
		return this;
	}

	/**
	 * Left multiply the current matrix with specified matrix. <br>
	 * <code>curr = curr * d</code>
	 * 
	 * @param d
	 *            the specified matrix
	 * @return the result of multiplication
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 */
	public Matrix leftMultiply(Matrix d) throws IllegalArgumentException
	{
		realloc(Matrix.multiply(this, d));
		return this;
	}

	/**
	 * Multiply the current matrix with specified value. <br>
	 * <code>curr = curr * k</code>
	 * 
	 * @param d
	 *            the specified value
	 * @return the result of multiplication
	 */
	public Matrix multiply(double k)
	{
		realloc(Matrix.multiply(this, k));
		return this;
	}

	/**
	 * Multiply the current matrix with specified matrix. <br>
	 * <code>curr = curr * d</code>
	 * <p>
	 * This method has the same affect as {@linkplain #leftMultiply(Matrix)}.
	 * </p>
	 * 
	 * @param d
	 *            the specified matrix
	 * @return the result of multiplication
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 * @see #leftMultiply(Matrix)
	 */
	public Matrix multiply(Matrix d) throws IllegalArgumentException
	{
		return leftMultiply(d);
	}

	/**
	 * Normalize the matrix elements to the specified region.
	 * 
	 * @param minimum
	 *            the minimum value of the region
	 * @param maximum
	 *            the maximum value of the region
	 */
	public void normalize(double minimum, double maximum)
	{
		double max = value(0, 0);
		double min = max;
		for (Object[] line : data)
			for (Object o : line)
			{
				double d = (Double) o;
				if (d > max)
					max = d;
				if (d < min)
					min = d;
			}
		double len = max - min;
		if (max == min)
		{
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					data[y][x] = (double) ((maximum - minimum) / 2.0);
			return;
		}
		double length = maximum - minimum;
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				data[y][x] = (double) (minimum + (value(x, y) - min) / len
						* length);
	}

	/**
	 * Plus the current matrix with the specified matrix.<br>
	 * <code>curr = curr + d</code>
	 * 
	 * @param d
	 *            the specified matrix
	 * @return the result of plus
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 */
	public Matrix plus(Matrix d) throws IllegalArgumentException
	{
		realloc(Matrix.plus(this, d));
		return this;
	}

	/**
	 * Returns the rank of the current matrix.
	 * 
	 * @return the rank of matrix
	 */
	public double rank()
	{
		return new SingularValueDecomposition(this).rank();
	}

	/**
	 * Left divide the current matrix and returns the result of division. <br>
	 * <code>curr = m / curr</code>
	 * 
	 * @param d
	 *            the matrix to divide
	 * @return m / curr
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 */
	public Matrix rightDivide(Matrix d) throws IllegalArgumentException
	{
		realloc(Matrix.divide(d, this));
		return this;
	}

	/**
	 * Right multiply the current matrix with specified matrix. <br>
	 * <code>curr = d * curr</code>
	 * 
	 * @param d
	 *            the specified matrix
	 * @return the result of multiplication
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 */
	public Matrix rightMultiply(Matrix d) throws IllegalArgumentException
	{
		realloc(Matrix.multiply(this, d));
		return this;
	}

	/**
	 * Set the element at the specified position with specified value.
	 * 
	 * @param x
	 *            x coordinate of the element
	 * @param y
	 *            y coordinate of the element
	 * @param value
	 *            the specified value
	 */
	public void set(int x, int y, double value)
	{
		super.set(x, y, value);
	}

	/**
	 * Simplify the values in the matrix.<br>
	 * As matrix / min_abstract_element(matrix).
	 */
	public void simplify()
	{
		double min = Double.POSITIVE_INFINITY, tmp;
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
			{
				tmp = Math.abs(value(x, y));
				if (tmp < min && tmp > 0)
					min = value(x, y);
			}
		if (!Double.isInfinite(min))
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++)
					data[y][x] = value(x, y) / min;
	}

	/**
	 * Get a submatrix.
	 * 
	 * @param rows
	 *            array of row indices.
	 * @param start
	 *            initial column index
	 * @param end
	 *            final column index (not reached)
	 * @return A(r(:), start:end)
	 * @see com.frank.math.matrix.AbstractMatrix#subMatrix(int[], int, int)
	 */
	@Override
	public Matrix subMatrix(int[] rows, int start, int end)
	{
		Matrix matrix = new Matrix(rows.length, end - start);
		Object[][] data = matrix.getMatrix();
		for (int i = 0; i < rows.length; i++)
			for (int j = start; j < end; j++)
				data[i][j - start] = this.data[rows[i]][j];
		return matrix;
	}

	/**
	 * Get a submatrix.
	 * 
	 * @param rowStart
	 *            initial row index
	 * @param rowEnd
	 *            final row index
	 * @param colStart
	 *            initial column index
	 * @param colEnd
	 *            final column index
	 * @return A(rowStart:rowEnd, colStart:colEnd)
	 * @see com.frank.math.matrix.AbstractMatrix#subMatrix(int, int, int, int)
	 */
	@Override
	public Matrix subMatrix(int rowStart, int rowEnd, int colStart, int colEnd)
	{
		Matrix matrix = new Matrix(rowEnd - rowStart, colEnd - colStart);
		Object[][] data = matrix.getMatrix();
		try
		{
			for (int i = rowStart; i < rowEnd; i++)
				for (int j = colStart; j < colEnd; j++)
					data[i - rowStart][j - colStart] = this.data[i][j];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			throw new RuntimeException(Messages.getString("Matrix.SubMatIdx") //$NON-NLS-1$
					+ e.getLocalizedMessage(), e);
		}
		return matrix;
	}

	/**
	 * Subtract the current matrix with specified matrix.<br>
	 * <code>curr = curr - d</code>
	 * 
	 * @param d
	 *            the specified matrix
	 * @return the result of subtraction
	 * @throws IllegalArgumentException
	 *             if the matrix dimensions not agree
	 */
	public Matrix subtract(Matrix d) throws IllegalArgumentException
	{
		realloc(Matrix.subtract(this, d));
		return this;
	}

	/**
	 * Returns the result of singular value decomposition.
	 * 
	 * @return result instance
	 */
	public SingularValueDecomposition svd()
	{
		return new SingularValueDecomposition(this);
	}

	/**
	 * Returns the result of LU decomposition.
	 * <p>
	 * To notify that,
	 * </p>
	 * 
	 * @return result instance.
	 */
	public LUDecomposition lu()
	{
		return new LUDecomposition(this);
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{\r\n"); //$NON-NLS-1$
		sb.append(getClass().getName() + "@" + Integer.toHexString(hashCode())); //$NON-NLS-1$
		sb.append(String.format(Messages.getString("Matrix.Size"), width, height)); //$NON-NLS-1$
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
			{
				if (x != 0)
					sb.append("\t"); //$NON-NLS-1$
				else
					sb.append("\r\n"); //$NON-NLS-1$
				sb.append(String.format("% 10.4f", //$NON-NLS-1$
						((Number) data[y][x]).doubleValue()));
			}
		sb.append("\r\n}"); //$NON-NLS-1$
		return sb.toString();
	}

	/**
	 * Transpose the current matrix.
	 */
	public void transpose()
	{
		realloc(Matrix.transpose(this));
	}

	/**
	 * Returns the double value of the specified element.
	 * 
	 * @see com.frank.math.matrix.AbstractMatrix#value(int, int)
	 */
	@Override
	public Double value(int x, int y)
	{
		return ((Number) data[y][x]).doubleValue();
	}

	/**
	 * Augment the current matrix <tt>m</tt> with specified vector <tt>d</tt>.
	 * 
	 * @param d
	 *            the vector to augment
	 * @return the matrix after augment
	 * @throws IllegalArgumentException
	 *             throws if the vector dimension not match
	 */
	public Matrix augment(double[] d) throws IllegalArgumentException
	{
		realloc(augment(this, d));
		return this;
	}

	/**
	 * Augment the specified matrix <tt>m</tt> with specified vector <tt>d</tt>.
	 * 
	 * @param m
	 *            the matrix to be augmented
	 * @param d
	 *            the vector to augment
	 * @return the matrix after augment
	 * @throws IllegalArgumentException
	 *             throws if the vector dimension not match
	 */
	public static Matrix augment(Matrix m, double[] d)
			throws IllegalArgumentException
	{
		if (d.length != m.height)
			throw new IllegalArgumentException(String.format(
					Messages.getString("Matrix.VectorDimensionNotMatch"), //$NON-NLS-1$
					d.length, m.height));
		Matrix p = new Matrix(m.width + 1, m.height);
		for (int x = 0; x < m.width; x++)
			for (int y = 0; y < m.height; y++)
				p.set(x, y, m.value(x, y));
		for (int y = 0; y < m.height; y++)
			p.set(m.width, y, d[y]);
		return p;
	}

	public Matrix inverse()
	{
		Matrix e = Matrix.identity(width, height);
		return (width == height ? (new LUDecomposition(this)).solve(e)
				: (new QRDecomposition(this)).solve(e));
	}

	/**
	 * Generate identity matrix
	 * 
	 * @param width
	 *            the width of the matrix
	 * @param height
	 *            the height of the matrix
	 * @return a specified dimension identity matrix
	 */
	public static Matrix identity(int width, int height)
	{
		Matrix m = new Matrix(width, height, 0.0);
		int size = width < height ? width : height;
		for (int i = 0; i < size; i++)
			m.set(i, i, 1);
		return m;
	}
}
