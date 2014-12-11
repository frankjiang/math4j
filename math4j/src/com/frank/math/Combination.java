/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * Combination.java is built in 2013-2-14.
 */
package com.frank.math;

import java.util.Vector;

/**
 * The implementation of combination algorithm.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class Combination
{
	/**
	 * The input index.
	 */
	private int			index	= 0;
	/**
	 * All the combinations.
	 */
	private Object[][]	combinations;

	/**
	 * Construct an instance of Combination.
	 * 
	 * @param base
	 * @param value
	 */
	private Combination(int base, int value)
	{
		index = 0;
		long sizeL = MathUtils.combine(base, value);
		if (sizeL > Integer.MAX_VALUE)
			throw new IllegalArgumentException(
					Messages.getString("Combination.OutOfIntegerValue")); //$NON-NLS-1$
		int size = (int) sizeL;
		combinations = new Object[size][value];
	}

	/**
	 * Recursively add combinations to the specified combination
	 * {@linkplain Vector}.
	 * 
	 * @param <T>
	 *            the type of combination data
	 * @param allCombinations
	 *            all the combinations, the combination {@linkplain Vector}
	 * @param data
	 *            the data to combine
	 * @param initialCombination
	 *            the initial combinations
	 * @param length
	 *            the length of combination
	 */
	private void combinate(Object[][] allCombinations, Object[] data,
			Object[] initialCombination, int length)
	{
		if (length == 1)
			for (int i = 0; i < data.length; i++)
			{
				Object[] newCombination = new Object[initialCombination.length + 1];
				System.arraycopy(initialCombination, 0, newCombination, 0,
						initialCombination.length);
				newCombination[initialCombination.length] = data[i];
				allCombinations[index++] = newCombination;
			}
		if (length > 1)
			for (int i = 0; i < data.length; i++)
			{
				Object[] newCombination = new Object[initialCombination.length + 1];
				System.arraycopy(initialCombination, 0, newCombination, 0,
						initialCombination.length);
				newCombination[initialCombination.length] = data[i];
				Object[] newData = new Object[data.length - i - 1];
				System.arraycopy(data, i + 1, newData, 0, newData.length);
				combinate(allCombinations, newData, newCombination, length - 1);
			}
	}

	/**
	 * Returns all the length specified combinations containing in a
	 * {@linkplain Vector}.
	 * 
	 * @param <T>
	 *            the type of combination data
	 * @param data
	 *            the data to combine
	 * @param length
	 *            the length of combination
	 * @return the length specified combinations
	 */
	private void combinate(Object[] data, int length)
	{
		if (length < 1 || length > data.length)
			throw new IllegalArgumentException(
					String.format(
							Messages.getString("Combination.OutOfBounds"), //$NON-NLS-1$
							length, 1, data.length));
		combinate(combinations, data, new Object[0], length);
	}

	/**
	 * Returns all the combinations of specified length.
	 * 
	 * @param <E>
	 *            the combination data type
	 * @param data
	 *            the data to combine
	 * @param length
	 *            the length of combination
	 * @return all the length specified combinations
	 */
	@SuppressWarnings("unchecked")
	public static <E> Vector<Vector<E>> getCombinations(E[] data, int length)
	{
		Combination c = new Combination(data.length, length);
		c.combinate(data, length);
		Vector<Vector<E>> v = new Vector<Vector<E>>(c.combinations.length);
		for (Object[] args : c.combinations)
		{
			Vector<E> e = new Vector<E>(args.length);
			for (Object obj : args)
				e.add((obj == null)?null:((E) obj));
			v.add(e);
		}
		return v;
	}

	/**
	 * Returns all the combinations of specified length.
	 * 
	 * @param <E>
	 *            the combination data type
	 * @param allCombinations
	 *            the combinations matrix into which the combinations are to
	 *            be stored, it must be big enough; otherwise, an exception will
	 *            occur.
	 * @param data
	 *            the data to combine
	 * @param length
	 *            the length of combination
	 * @return all the length specified combinations
	 */
	@SuppressWarnings("unchecked")
	public static <E> void getCombinations(E[][] allCombinations, E[] data,
			int length)
	{
		Combination c = new Combination(data.length, length);
		if (allCombinations.length != c.combinations.length
				|| allCombinations[0].length != c.combinations[0].length)
			throw new IllegalArgumentException(
					Messages.getString("Combination.InvalidSize")); //$NON-NLS-1$
		c.combinate(data, length);
		Object obj;
		for (int i = 0; i < allCombinations.length; i++)
			for (int j = 0; j < allCombinations[i].length; j++)
			{
				obj = c.combinations[i][j];
				allCombinations[i][j] = (obj != null)?null:((E) obj);
			}
	}
}
