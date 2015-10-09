/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. SparseVector.java is PROPRIETARY/CONFIDENTIAL built in 2013. Use is
 * subject to license terms.
 */
package com.frank.math.struct.sparse;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
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
 * @version 1.1.0
 */
public abstract class SparseVector<T extends Number> implements java.io.Serializable, java.util.Collection<T>
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
	public static final class AtomicInteger extends SparseVector<java.util.concurrent.atomic.AtomicInteger>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= -2630657714244010960L;

		/**
		 * @see com.frank.math.struct.sparse.SparseVector#createZero()
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
	public static final class AtomicLong extends SparseVector<java.util.concurrent.atomic.AtomicLong>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= -2047126350711218806L;

		/**
		 * @see com.frank.math.struct.sparse.SparseVector#createZero()
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
	public static final class BigDecimal extends SparseVector<java.math.BigDecimal>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= -8522775565140622737L;

		/**
		 * @see com.frank.math.struct.sparse.SparseVector#createZero()
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
	public static final class BigInteger extends SparseVector<java.math.BigInteger>
	{
		/**
		 * serialVersionUID.
		 */
		private static final long	serialVersionUID	= -2700468802169806813L;

		/**
		 * @see com.frank.math.struct.sparse.SparseVector#createZero()
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
		 * @see com.frank.math.struct.sparse.SparseVector#createZero()
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
		 * @see com.frank.math.struct.sparse.SparseVector#createZero()
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
		 * @see com.frank.math.struct.sparse.SparseVector#createZero()
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
		 * @see com.frank.math.struct.sparse.SparseVector#createZero()
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
		 * @see com.frank.math.struct.sparse.SparseVector#createZero()
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
		 * @see com.frank.math.struct.sparse.SparseVector#createZero()
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
	 * @return the maximum index, or <tt>-1</tt> if the sparse vector contains
	 *         no
	 *         element.
	 */
	public java.lang.Integer getMaximumIndex()
	{
		java.lang.Integer index = map.lastKey();
		if (index == null)
			return -1;
		else
			return index;
	}

	/**
	 * Returns the minimum index of the <tt>non-zero</tt> elements.
	 * <p>
	 * If the sparse vector contains no non-zero elements, return <tt>0</tt>.
	 * </p>
	 * 
	 * @return the minimum index, or <tt>-1</tt> if the sparse vector contains
	 *         no
	 *         element.
	 */
	public java.lang.Integer getMinimumIndex()
	{
		java.lang.Integer index = map.firstKey();
		if (index == null)
			return -1;
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
			throw new IllegalArgumentException(String.format(Messages.getString("SparseVector.NegativeIndex"), index)); //$NON-NLS-1$
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
	public int nonZeroCount()
	{
		return map.size();
	}

	/**
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size()
	{
		return getMaximumIndex() + 1;
	}

	/**
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o)
	{
		return map.containsValue(o);
	}

	/**
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<T> iterator()
	{
		return new SparseVectorIterator<T>(map, createZero(), getMaximumIndex() + 1);
	}

	/**
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray()
	{
		Object[] a = new Object[size()];
		Object zero = createZero();
		for (Entry<java.lang.Integer, T> e : map.entrySet())
			a[e.getKey()] = e.getValue();
		for (int i = 0; i < a.length; i++)
			if (a[i] == null)
				a[i] = zero;
		return a;
	}

	/**
	 * Returns an array containing all of the elements in this collection.
	 * If this collection makes any guarantees as to what order its elements
	 * are returned by its iterator, this method must return the elements in
	 * the same order.
	 * <p>
	 * If the specified destination size is less than the {@linkplain #size()
	 * vector length}, the returned array will be cut off to the length of
	 * specified size.
	 * </p>
	 * 
	 * @param size
	 *            the destination size
	 * @return an array containing all of the elements in this collection
	 */
	public Object[] toArray(int size)
	{
		int length = size();
		if (length < size)
			length = size;
		Object[] a = new Object[length];
		Object zero = createZero();
		java.lang.Integer key;
		int bound = length - 1;
		for (Entry<java.lang.Integer, T> e : map.entrySet())
		{
			key = e.getKey();
			if (key < bound)
				a[key] = e.getValue();
		}
		for (int i = 0; i < a.length; i++)
			if (a[i] == null)
				a[i] = zero;
		return a;
	}

	/**
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a)
	{
		int size = size();
		if (a.length < size())
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		T zero = (T) createZero();
		for (Entry e : map.entrySet())
			a[(java.lang.Integer) e.getKey()] = (T) e.getValue();
		for (int i = 0; i < a.length; i++)
			if (a[i] == null)
				a[i] = zero;
		return a;
	}

	/**
	 * Returns an array containing all of the elements in this collection;
	 * the runtime type of the returned array is that of the specified array.
	 * If the collection fits in the specified array, it is returned therein.
	 * Otherwise, a new array is allocated with the runtime type of the
	 * specified array and the size of this collection.
	 * <p>
	 * If the specified destination size is less than the {@linkplain #size()
	 * vector length}, the returned array will be cut off to the length of
	 * specified size.
	 * </p>
	 * 
	 * @param size
	 *            the destination size
	 * @return an array containing all of the elements in this collection
	 */
	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a, int size)
	{
		if (a.length < size)
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		T zero = (T) createZero();
		for (Entry e : map.entrySet())
		{
			java.lang.Integer index = (java.lang.Integer) e.getKey();
			if (index < a.length)
				a[index] = (T) e.getValue();
		}
		for (int i = 0; i < a.length; i++)
			if (a[i] == null)
				a[i] = zero;
		return a;
	}

	/**
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(T e)
	{
		insert(getMaximumIndex() + 1, e);
		return true;
	}

	/**
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o)
	{
		LinkedList<java.lang.Integer> toRemove = new LinkedList<java.lang.Integer>();
		for (Entry<java.lang.Integer, T> e : map.entrySet())
			if (o.equals(e.getValue()))
				toRemove.add(e.getKey());
		for (java.lang.Integer key : toRemove)
			map.remove(key);
		return !toRemove.isEmpty();
	}

	/**
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c)
	{
		return map.values().containsAll(c);
	}

	/**
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends T> c)
	{
		int index = getMaximumIndex() + 1;
		for (T e : c)
			insert(index++, e);
		return true;
	}

	/**
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c)
	{
		LinkedList<java.lang.Integer> toRemove = new LinkedList<java.lang.Integer>();
		for (Entry<java.lang.Integer, T> e : map.entrySet())
			if (c.contains(e.getValue()))
				toRemove.add(e.getKey());
		for (java.lang.Integer key : toRemove)
			map.remove(key);
		return true;
	}

	/**
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c)
	{
		return map.values().retainAll(c);
	}

	/**
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear()
	{
		map.clear();
	}
}
