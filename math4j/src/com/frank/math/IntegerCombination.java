/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * IntegerCombination.java is built in 2013-2-14.
 */
package com.frank.math;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.frank.math.MathUtils;

/**
 * Lottery algorithm for combination.
 * 
 * @author Frank Jiang
 * @version 1.0.0
 */
public class IntegerCombination
{
	/**
	 * The selected array.
	 */
	private Vector<int[]>	data;

	/**
	 * Construction.
	 */
	public IntegerCombination(int[] array, int selectAmount)
	{
		data = new Vector<int[]>((int) MathUtils.combine(array.length,
				selectAmount));
		lottery(array, 0, array.length - 1, selectAmount,
				new HashSet<Integer>());
	}

	/**
	 * Cast Set<Integer> to int array.
	 * 
	 * @param src
	 * @return
	 */
	private int[] cast(Set<Integer> src)
	{
		int[] res = new int[src.size()];
		int i = 0;
		for (Integer e : src)
			res[i++] = e;
		return res;
	}

	/**
	 * Returns combinations as a vector.
	 * 
	 * @return combinations
	 */
	public Vector<int[]> getVector()
	{
		return data;
	}

	/**
	 * Returns combinations as an array.
	 * 
	 * @return combinations
	 */
	public int[][] getData()
	{
		int[][] res = new int[data.size()][];
		for (int i = 0; i < res.length; i++)
			res[i] = data.get(i);
		return res;
	}

	/**
	 * The lottery algorithm.
	 * 
	 * @param array
	 * @param startIndex
	 * @param endIndex
	 * @param neededBalls
	 * @param alreadyChosen
	 */
	private void lottery(int array[], int startIndex, int endIndex,
			int neededBalls, Set<Integer> alreadyChosen)
	{
		if (neededBalls == 0)
		{
			data.add(cast(alreadyChosen));
			return;
		}
		for (int i = startIndex; i <= endIndex - neededBalls + 1; i++)
		{
			alreadyChosen.add(array[i]);
			lottery(array, i + 1, endIndex, neededBalls - 1, alreadyChosen);
			alreadyChosen.remove(array[i]);
		}
	}

	/**
	 * Get the combinations.
	 * 
	 * @param array
	 * @param selectNumber
	 * @return combinations
	 */
	public static int[][] combinations(int[] array, int selectNumber)
	{
		return new IntegerCombination(array, selectNumber).getData();
	}

	/**
	 * Get the combinations.
	 * 
	 * @param array
	 * @param selectNumber
	 * @return combinations
	 */
	public static Vector<int[]> combinationVector(int[] array, int selectNumber)
	{
		return new IntegerCombination(array, selectNumber).data;
	}
}
