/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * Heap.java is PROPRIETARY/CONFIDENTIAL built in 10:35:15 AM, Sep 25, 2015.
 * Use is subject to license terms.
 */
package com.frank.math.struct;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * The data structure of minimum heap.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @param <E>
 * @version 1.0.0
 */
public class Heap<E>
{
	/**
	 * The heap container.
	 */
	protected ArrayList<E>				heap;
	/**
	 * The heap element comparator.
	 */
	protected Comparator<? extends E>	comparator;

	/**
	 * Construct an instance of <tt>Heap</tt>.
	 */
	public Heap()
	{
		this(11, null);
	}

	/**
	 * Construct an instance of <tt>Heap</tt>.
	 * 
	 * @param initCapacity
	 */
	public Heap(int initCapacity)
	{
		this(initCapacity, null);
	}

	/**
	 * Construct an instance of <tt>Heap</tt>.
	 * 
	 * @param initCapacity
	 * @param comparator
	 */
	public Heap(int initCapacity, Comparator<? extends E> comparator)
	{
		heap = new ArrayList<>(initCapacity);
		this.comparator = comparator;
	}

	/**
	 * Swap the elements of the two specified indices.
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j)
	{
		E tmp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, tmp);
	}

	/**
	 * Shift the bottom element to the top if matches the positive rules.
	 * <p>
	 * e.g. The parent is larger than the child when it is in a minimum heap.
	 * </p>
	 * 
	 * @param index
	 *            the index to be shifted
	 */
	private void shiftUpComparable(int index)
	{
		int parent = ((index & 0x1) == 0) ? ((index >> 1) - 1) : (index >> 1);
		while (parent > -1 && parent != index && ((Comparable<E>) heap.get(parent)).compareTo(heap.get(index)) > 0)
		{
			swap(parent, index);
			index = parent;
			parent = ((index & 0x1) == 0) ? ((index >> 1) - 1) : (index >> 1);
		}
	}

	//	/**
	//	 * Shift the bottom element to the top if matches the positive rules.
	//	 * <p>
	//	 * e.g. The parent is larger than the child when it is in a minimum heap.
	//	 * </p>
	//	 * 
	//	 * @param index
	//	 *            the index to be shifted
	//	 */
	//	private void shiftUpUsingComparator(int index)
	//	{
	//		int parent = ((index & 0x1) == 0) ? ((index >> 1) - 1) : (index >> 1);
	//		while (parent > -1 && parent != index && comparator.compare(o1, o2)((Comparable<E>) heap.get(parent)).compareTo(heap.get(index)) > 0)
	//		{
	//			swap(parent, index);
	//			index = parent;
	//			parent = ((index & 0x1) == 0) ? ((index >> 1) - 1) : (index >> 1);
	//		}
	//	}
	/**
	 * Shift the top element to the bottom if matches the negative rules.
	 * <p>
	 * e.g. The child is smaller than the parent when it is in a minimum heap.
	 * </p>
	 * 
	 * @param index
	 *            the index to be shifted
	 */
	private void shiftDownComparable(int index)
	{
		int left, right;
		while (index < heap.size())
		{
			left = index * 2 + 1;
			right = index * 2 + 2;
			if (left < heap.size() && right < heap.size())
			{
				if (((Comparable<E>) heap.get(left)).compareTo(heap.get(right)) < 0
						&& ((Comparable<E>) heap.get(left)).compareTo(heap.get(index)) < 0)
				{
					swap(left, index);
					index = left;
					continue;
				}
				if (((Comparable<E>) heap.get(right)).compareTo(heap.get(index)) < 0)
				{
					swap(right, index);
					index = right;
					continue;
				}
				break;
			}
			if (left < heap.size() && ((Comparable<E>) heap.get(left)).compareTo(heap.get(index)) < 0)
			{
				swap(left, index);
				index = left;
				continue;
			}
			if (right < heap.size() && ((Comparable<E>) heap.get(right)).compareTo(heap.get(index)) < 0)
			{
				swap(right, index);
				index = right;
				continue;
			}
			break;
		}
	}

	/**
	 * Pushes an item into this heap.
	 *
	 * @param item
	 *            the item to be pushed into this heap.
	 * @return the <code>item</code> argument.
	 */
	public void push(E e)
	{
		heap.add(e);
		shiftUpComparable(heap.size() - 1);
	}

	/**
	 * Removes the object at the heap of this heap and returns that
	 * object as the value of this function.
	 *
	 * @return The object at the top of this heap (the first item
	 *         of the <tt>ArrayList</tt> object).
	 * @throws NoSuchElementException
	 *             if this heap is empty.
	 */
	public E pop()
	{
		if (heap.isEmpty())
			throw new NoSuchElementException();
		E top = heap.get(0);
		E bottom = heap.remove(heap.size() - 1);
		if (!heap.isEmpty())
		{
			heap.set(0, bottom);
			shiftDownComparable(0);
		}
		return top;
	}

	public E peek()
	{
		if (heap.isEmpty())
			throw new NoSuchElementException();
		return heap.get(0);
	}

	/**
	 * Returns <tt>true</tt> if this list contains no elements.
	 *
	 * @return <tt>true</tt> if this list contains no elements
	 */
	public boolean isEmpty()
	{
		return heap.isEmpty();
	}

	/**
	 * Returns the number of elements in this list.
	 *
	 * @return the number of elements in this list
	 */
	public int size()
	{
		return heap.size();
	}
}
