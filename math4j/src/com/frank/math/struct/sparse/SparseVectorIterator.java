/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * SparseVecotrIterator.java is PROPRIETARY/CONFIDENTIAL built in 下午8:17:03,
 * 2014年11月2日.
 * Use is subject to license terms.
 */
package com.frank.math.struct.sparse;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.frank.math.Messages;

/**
 * The iterator for sparse vector.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
class SparseVectorIterator<E> implements Iterator<E>
{
	/**
	 * The inner map.
	 */
	protected Map<Integer, E>	map;
	/**
	 * Size of the vector.
	 */
	protected int				size;
	/**
	 * The index of current iteration.
	 */
	protected int				currentIndex;
	/**
	 * The default value if sparse vector element is not defined.
	 * <p>
	 * Always ZERO recommended.
	 * </p>
	 */
	protected E					defaultValue;

	public SparseVectorIterator(Map<Integer, E> map, E defaultValue, int size)
	{
		this.map = map;
		this.size = size;
		this.defaultValue = defaultValue;
		this.currentIndex = 0;
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext()
	{
		return currentIndex < size;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	@Override
	public E next()
	{
		if(currentIndex>=size)
			throw new NoSuchElementException(Messages.getString("SparseVectorIterator.OutOfBounds")); //$NON-NLS-1$
		E e = map.get(currentIndex++);
		if (e == null)
			return defaultValue;
		else
			return e;
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove()
	{
		throw new NoSuchMethodError(Messages.getString("SparseVectorIterator.RemoveNotSupport")); //$NON-NLS-1$
	}
}
