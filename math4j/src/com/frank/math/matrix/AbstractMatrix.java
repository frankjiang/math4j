/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. Matrix.java is built in 2013-7-13.
 */
package com.frank.math.matrix;

import com.frank.math.Messages;

/**
 * The abstract matrix defines a matrix with specified width and height.
 * <p>
 * In this matrix, any type of data can be restored in. Anyway, it is generic
 * typed, so the elements can be recalled according to the type.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @param <T>
 *            the type of the elements in the matrix
 * @version 1.0.0
 */
public class AbstractMatrix<T>
{
	/**
	 * The matrix elements.
	 */
	protected Object[][]	data;
	/**
	 * width of matrix.
	 */
	protected int			width;
	/**
	 * height of matrix
	 */
	protected int			height;

	/**
	 * Construct an instance of <tt>AbstractMatrix</tt> with specified
	 * dimension. Allocate the memory but not initialize it.
	 * 
	 * @param width
	 *            the width of the matrix
	 * @param height
	 *            the height of the matrix
	 */
	protected AbstractMatrix(int width, int height)
	{
		data = new Object[height][width];
		this.width = width;
		this.height = height;
	}

	/**
	 * Construct an instance of <tt>AbstractMatrix</tt> with specified width and
	 * height.
	 * 
	 * @param width
	 *            the width of the matrix
	 * @param height
	 *            the height of the matrix
	 * @param value
	 *            the default value of the matrix elements
	 */
	public AbstractMatrix(int width, int height, T value)
	{
		data = new Object[height][width];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				data[y][x] = value;
		this.width = width;
		this.height = height;
	}

	/**
	 * Construct an empty instance of <tt>AbstractMatrix</tt>. The data matrix
	 * will be set as <tt>null</tt> and the dimension(width,height) as
	 * <code>(-1,-1)</code>.
	 */
	protected AbstractMatrix()
	{
		data = null;
		width = height = -1;
	}

	/**
	 * Returns the width of the matrix.
	 * 
	 * @return the width
	 */
	public int width()
	{
		return width;
	}

	/**
	 * Returns the height of the matrix.
	 * 
	 * @return the height
	 */
	public int height()
	{
		return height;
	}

	/**
	 * Returns the size/dimension of the matrix.
	 * 
	 * @return the size
	 */
	public java.awt.Dimension size()
	{
		return new java.awt.Dimension(width, height);
	}

	/**
	 * Returns the value at the specified position.
	 * 
	 * @param x
	 *            x coordinate of the element
	 * @param y
	 *            y coordinate of the element
	 * @return the element
	 */
	@SuppressWarnings("unchecked")
	public T value(int x, int y)
	{
		Object obj = data[y][x];
		if (obj == null)
			return null;
		else
			return (T) obj;
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
	public void set(int x, int y, T value) {
		data[y][x] = value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\r\n"); //$NON-NLS-1$
		sb.append(super.toString());
		sb.append(String.format(
				Messages.getString("AbstractMatrix.Size"), width, height)); //$NON-NLS-1$
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				if (x != 0)
					sb.append("\t"); //$NON-NLS-1$
				else
					sb.append("\r\n"); //$NON-NLS-1$
				sb.append(data[y][x].toString());
			}
		sb.append("\r\n}"); //$NON-NLS-1$
		return sb.toString();
	}

	/**
	 * Copy the specified matrix to current.
	 * 
	 * @param m
	 *            the specified matrix to copy.
	 */
	public void copy(AbstractMatrix<T> m) {
		this.width = m.width;
		this.height = m.height;
		this.data = new Object[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				data[y][x] = m.data[y][x];
	}

	/**
	 * Reallocate the matrix values. In this method, the data will be exactly
	 * referred to the new matrix input. In other word, their will share
	 * memories.
	 * 
	 * @param m
	 *            the new matrix to allocate
	 */
	protected void realloc(AbstractMatrix<T> m) {
		this.width = m.width;
		this.height = m.height;
		this.data = m.data;
	}

	/**
	 * Returns the exact reference of the matrix data inside. To notify that, do
	 * not change the data type of the matrix elements, unless you know what you
	 * are doing.
	 * 
	 * @return the matrix
	 */
	protected Object[][] getMatrix() {
		return data;
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
	 */
	public AbstractMatrix<T> subMatrix(int[] rows, int start, int end) {
		AbstractMatrix<T> matrix = new AbstractMatrix<T>(rows.length, end
				- start);
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
	 */
	public AbstractMatrix<T> subMatrix(int rowStart, int rowEnd, int colStart,
			int colEnd) {
		AbstractMatrix<T> matrix = new AbstractMatrix<T>(rowEnd - rowStart,
				colEnd - colStart);
		Object[][] data = matrix.getMatrix();
		try {
			for (int i = rowStart; i < rowEnd; i++)
				for (int j = colStart; j < colEnd; j++)
					data[i - rowStart][j - colStart] = this.data[i][j];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException(String.format(
					Messages.getString("AbstractMatrix.Indices"), //$NON-NLS-1$
					e.getLocalizedMessage()), e);
		}
		return matrix;
	}
}
