/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * Permutation.java is built in 2013-2-14.
 */
package com.frank.math;

import java.util.Vector;

/**
 * The implementation of permutation algorithm.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class Permutation
{
	/**
	 * Returns the permutations of data array with length elements.
	 * 
	 * @param <T>
	 *            the data type to permute
	 * @param data
	 *            the data to permute
	 * @param length
	 *            the length of permutations
	 * @return the permuted data
	 */
	public final static <T> Vector<T[]> permute(T[] data, int length)
	{
		Vector<T[]> permutations = new Vector<T[]>((int) MathUtils.permute(
				data.length, length));
		permute(permutations, data, data.length - length);
		return permutations;
	}

	/**
	 * Permute the value and add the permutations to the specified container.
	 * 
	 * @param <T>
	 *            the type of data to permute
	 * @param permutations
	 *            the permuted data
	 * @param data
	 *            the data to permute
	 * @param toPermute
	 *            the rest value to permute
	 */
	private final static <T> void permute(Vector<T[]> permutations, T[] data,
			int toPermute)
	{
		if (toPermute < data.length - 1)
		{
			T tmp;
			for (int j = toPermute; j < data.length; j++)
			{
				tmp = data[j];
				for (int k = j; k > toPermute; k--)
					data[k] = data[k - 1];
				data[toPermute] = tmp;
				Permutation.permute(permutations, data, toPermute + 1);
				for (int k = toPermute; k < j; k++)
					data[k] = data[k + 1];
				data[j] = tmp;
			}
		}
		else
			permutations.add(data.clone());
	}
}
