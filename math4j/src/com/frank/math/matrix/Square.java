/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * Square.java is built in 2013-7-13.
 */
package com.frank.math.matrix;


/**
 * The square matrix class.
 * <p>
 * The square matrix is matrix with the same width(column) and height(row)
 * dimensions. In such matrix, its determinant can be calculated.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class Square extends Matrix
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= -761282163987930157L;
	/**
	 * The only dimension of the square.<br>
	 * <code>size = width = height</code>
	 */
	protected int				size;

	/**
	 * Construct an instance of <tt>Square</tt> with specified default element
	 * value.
	 * 
	 * @param size
	 *            the dimension of the square
	 * @param value
	 *            the default value
	 */
	public Square(int size, double value)
	{
		super(size, size, value);
		this.size = size;
	}

	/**
	 * Construct an instance of <tt>Square</tt> with specified dimension.
	 * Allocate the memory but not initialize it.
	 * 
	 * @param size
	 *            the dimension of the square
	 */
	protected Square(int size)
	{
		super(size, size);
		this.size = size;
	}

	/**
	 * Construct an empty instance of <tt>Square</tt>.
	 * The data matrix will be set as <tt>null</tt> and the dimension(size,size)
	 * as <code>-1</code>.
	 */
	protected Square()
	{
		super();
		this.size = -1;
	}

	/**
	 * Construct an instance of <tt>Square</tt> according to the specified
	 * matrix.
	 * 
	 * @param matrix
	 *            the specified matrix
	 */
	protected Square(double[][] matrix)
	{
		super(matrix);
	}

	/**
	 * Returns the determinant of current square.
	 * 
	 * @return the determinant
	 */
	public double determinant()
	{
		return lu().determinant();
	}
}
