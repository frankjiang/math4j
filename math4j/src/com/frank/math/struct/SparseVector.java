/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. SparseVector.java is PROPRIETARY/CONFIDENTIAL built in 2013. Use is
 * subject to license terms.
 */
package com.frank.math.struct;

import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.frank.math.Messages;

/**
 * The sparse vector for the specified value type.
 * <p>
 * A sparse vector is a vector which contains no zero elements in the vector.
 * This is the definition of an abstract sparse vector, the sub-class shall
 * implement the method {@linkplain #createZero()} to finish it.
 * </p>
 * <p>
 * In this sparse vector, the vector is implemented by
 * {@linkplain java.util.TreeMap}&lt;Integer, T&gt;.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @param <T>
 *            the type of the number
 * @version 1.0.0
 */
public abstract class SparseVector<T extends Number> implements
		java.io.Serializable
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= -9187589186829527076L;

	/**
	 * The sparse vector for <tt>AtomicInteger</tt> values.
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	public static final class AtomicInteger extends
			SparseVector<java.util.concurrent.atomic.AtomicInteger>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= -2630657714244010960L;

		/**
		 * @see com.frank.math.struct.SparseVector#createZero()
		 */
		@Override
		protected java.util.concurrent.atomic.AtomicInteger createZero()
		{
			return new java.util.concurrent.atomic.AtomicInteger(0);
		}
	}

	/**
	 * The sparse vector for <tt>AtomicLong</tt> values.
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	public static final class AtomicLong extends
			SparseVector<java.util.concurrent.atomic.AtomicLong>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= -2047126350711218806L;

		/**
		 * @see com.frank.math.struct.SparseVector#createZero()
		 */
		@Override
		protected java.util.concurrent.atomic.AtomicLong createZero()
		{
			return new java.util.concurrent.atomic.AtomicLong(0L);
		}
	}

	/**
	 * The sparse vector for <tt>BigDecimal</tt> values.
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	public static final class BigDecimal extends
			SparseVector<java.math.BigDecimal>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= -8522775565140622737L;

		/**
		 * @see com.frank.math.struct.SparseVector#createZero()
		 */
		@Override
		protected java.math.BigDecimal createZero()
		{
			return java.math.BigDecimal.ZERO;
		}
	}

	/**
	 * The sparse vector for <tt>BigInteger</tt> values.
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	public static final class BigInteger extends
			SparseVector<java.math.BigInteger>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= -2700468802169806813L;

		/**
		 * @see com.frank.math.struct.SparseVector#createZero()
		 */
		@Override
		protected java.math.BigInteger createZero()
		{
			return java.math.BigInteger.ZERO;
		}
	}

	/**
	 * The sparse vector for <tt>Byte</tt> values.
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	public static final class Byte extends SparseVector<java.lang.Byte>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= -8563583148310929183L;

		/**
		 * @see com.frank.math.struct.SparseVector#createZero()
		 */
		@Override
		protected java.lang.Byte createZero()
		{
			return 0;
		}
	}

	/**
	 * The sparse vector for <tt>Double</tt> values.
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	public static final class Double extends SparseVector<java.lang.Double>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= -7186556474637084512L;

		/**
		 * @see com.frank.math.struct.SparseVector#createZero()
		 */
		@Override
		protected java.lang.Double createZero()
		{
			return 0.0;
		}
	}

	/**
	 * The sparse vector for <tt>Float</tt> values.
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	public static final class Float extends SparseVector<java.lang.Float>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= 2028863227359490340L;

		/**
		 * @see com.frank.math.struct.SparseVector#createZero()
		 */
		@Override
		protected java.lang.Float createZero()
		{
			return 0.0f;
		}
	}

	/**
	 * The sparse vector for <tt>Integer</tt> values.
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	public static final class Integer extends SparseVector<java.lang.Integer>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= -8364138264698917392L;

		/**
		 * @see com.frank.math.struct.SparseVector#createZero()
		 */
		@Override
		protected java.lang.Integer createZero()
		{
			return 0;
		}
	}

	/**
	 * The sparse vector for <tt>Long</tt> values.
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	public static final class Long extends SparseVector<java.lang.Long>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= 1949944312065472630L;

		/**
		 * @see com.frank.math.struct.SparseVector#createZero()
		 */
		@Override
		protected java.lang.Long createZero()
		{
			return 0L;
		}
	}

	/**
	 * The sparse vector for <tt>Short</tt> values.
	 * 
	 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
	 * @version 1.0.0
	 */
	public static final class Short extends SparseVector<java.lang.Short>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= 7705106671969962265L;

		/**
		 * @see com.frank.math.struct.SparseVector#createZero()
		 */
		@Override
		protected java.lang.Short createZero()
		{
			return 0;
		}
	}

	/**
	 * The inner map for the sparse vector.
	 */
	public TreeMap<java.lang.Integer, T>	map;

	/**
	 * Construct an instance of <tt>SparseVector</tt> with empty elements.
	 */
	public SparseVector()
	{
		map = new TreeMap<java.lang.Integer, T>();
	}

	/**
	 * Returns the zero element value for the sparse vector.
	 * 
	 * @return the zero element value
	 */
	abstract protected T createZero();

	/**
	 * Returns the entries contains in the sparse vector.
	 * 
	 * @return the entry set
	 */
	public Set<Entry<java.lang.Integer, T>> entrySet()
	{
		return map.entrySet();
	}

	/**
	 * Returns the value contains in the vector.
	 * 
	 * @param index
	 *            the index of the value.
	 * @return the value of the element, <tt>zero</tt> if not contains
	 */
	public T get(int index)
	{
		T v = map.get(index);
		if (v == null)
			return createZero();
		else
			return v;
	}

	/**
	 * Returns the maximum index of the <tt>non-zero</tt> elements.
	 * <p>
	 * If the sparse vector contains no non-zero elements, return <tt>0</tt>.
	 * </p>
	 * 
	 * @return the maximum index, or <tt>0</tt> if the sparse vector contains no
	 *         element.
	 */
	public java.lang.Integer getMaximumIndex()
	{
		java.lang.Integer index = map.lastKey();
		if (index == null)
			return 0;
		else
			return index;
	}

	/**
	 * Returns the minimum index of the <tt>non-zero</tt> elements.
	 * <p>
	 * If the sparse vector contains no non-zero elements, return <tt>0</tt>.
	 * </p>
	 * 
	 * @return the minimum index, or <tt>0</tt> if the sparse vector contains no
	 *         element.
	 */
	public java.lang.Integer getMinimumIndex()
	{
		java.lang.Integer index = map.firstKey();
		if (index == null)
			return 0;
		else
			return index;
	}

	/**
	 * Insert a non-zero element to the specified index in the vector.
	 * 
	 * @param index
	 *            the index of the element
	 * @param value
	 *            the value of the element
	 */
	public void insert(int index, T value)
	{
		if (index < 0)
			throw new IllegalArgumentException(String.format(
					Messages.getString("SparseVector.0"), index)); //$NON-NLS-1$
		if (value.doubleValue() != 0)
			map.put(index, value);
	}

	/**
	 * Returns <tt>true</tt> if the sparse vector contains no non-zero elements.
	 * 
	 * @return <tt>true</tt> if the sparse vector contains no non-zero elements,
	 *         otherwise <tt>false</tt>.
	 */
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	/**
	 * Returns the amount of non-zero elements contains in the sparse vector.
	 * 
	 * @return the non-zero elements amount
	 */
	public int size()
	{
		return map.size();
	}
}
