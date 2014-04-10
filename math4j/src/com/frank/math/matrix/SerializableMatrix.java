/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. SerializableMatrix.java is built in 2013-7-13.
 */
package com.frank.math.matrix;

import java.io.Serializable;

/**
 * Serializable matrix.
 * <p>
 * A serializeable matrix is a serializable object which contains serializable
 * matrix elements. This matrix will have its state serialized and deserialized.
 * </p>
 * 
 * @see Serializable
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class SerializableMatrix<T extends Serializable> extends
		AbstractMatrix<T> implements Serializable
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= -7605545974055802381L;

	/**
	 * Construct an instance of <tt>SerializableMatrix</tt> with specified
	 * dimension. Allocate the memory but not initialize it.
	 * 
	 * @param width
	 *            the width of the matrix
	 * @param height
	 *            the height of the matrix
	 */
	protected SerializableMatrix(int width, int height)
	{
		super(width, height);
	}

	/**
	 * Construct an instance of <tt>SerializableMatrix</tt> with specified width
	 * and height.
	 * 
	 * @param width
	 *            the width of the matrix
	 * @param height
	 *            the height of the matrix
	 * @param value
	 *            the default value of the matrix elements
	 */
	public SerializableMatrix(int width, int height, T value)
	{
		super(width, height, value);
	}

	/**
	 * Construct an instance of <tt>SerializableMatrix</tt>. The data matrix
	 * will be set as <tt>null</tt> and the dimension(width,height) as
	 * <code>(-1,-1)</code>.
	 */
	public SerializableMatrix()
	{
		super();
	}

	/**
	 * Copy the specified matrix to current.
	 * 
	 * @param m
	 *            the specified matrix to copy.
	 */
	public void copy(SerializableMatrix<T> m)
	{
		this.width = m.width;
		this.height = m.height;
		this.data = new Object[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				data[y][x] = m.data[y][x];
	}
}
