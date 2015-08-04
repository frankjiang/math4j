/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. SearchMap.java is built in 2013-2-7.
 */
package com.frank.math.struct;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import com.frank.math.Messages;

/**
 * The search multiple map.
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 * @param <T>
 *            The type of element to be stored.
 */
public class SearchMap<T>
{
	/**
	 * The actual map which stored data.
	 */
	protected Map<T, Integer>	map;

	/**
	 * Construct a search map.
	 */
	public SearchMap()
	{
		this(LinkedHashMap.class);
	}

	/**
	 * Construct an instance of <tt>SearchMap</tt>.
	 * 
	 * @param mapType
	 *            the inner map type
	 */
	public SearchMap(Class<? extends Map> mapType)
	{
		try
		{
			map = mapType.getConstructor().newInstance();
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e)
		{
			throw new RuntimeException(Messages.getString("SearchMap.UnsupportedMapType"));//$NON-NLS-0$
		}
	}
	
	/**
	 * Add one value to the search map.
	 * 
	 * @param key
	 */
	public void add(T key)
	{
		add(key, 1);
	}

	/**
	 * Add one value to the search map.
	 * 
	 * @param key
	 */
	public void add(T key, int times)
	{
		Integer value = map.get(key);
		if (value == null)
			map.put(key, times);
		else
			map.put(key, value + times);
	}

	/**
	 * Add one value to the search map.
	 * 
	 * @param key
	 */
	public void addAll(Collection<? extends T> c)
	{
		for (T e : c)
			add(e);
	}

	/**
	 * Returns the value contains in the map.
	 * 
	 * @param key
	 *            The value to search.
	 * @return The amount of the specified key in the map.
	 */
	public int get(T key)
	{
		Integer value = map.get(key);
		return value == null ? 0 : value;
	}

	// /**
	// * Returns the value contains in the map. If map is empty or key is
	// * <code>null</code> it will always return 1.
	// *
	// * @param key
	// * The value to search.
	// * @return The amount of the specified key in the map.
	// */
	// public int getLoose(T key)
	// {
	// if (map.isEmpty() || key == null)
	// return 1;
	// Integer value = map.get(key);
	// return value == null ? 0 : value;
	// }
	/**
	 * Returns the statistic map.
	 * 
	 * @return the map
	 */
	public Map<T, Integer> getMap()
	{
		return map;
	}

	/**
	 * Returns the number of key-value mappings in this map. If the map contains
	 * more than <tt>Integer.MAX_VALUE</tt> elements, returns
	 * <tt>Integer.MAX_VALUE</tt>.
	 * 
	 * @return the number of key-value mappings in this map
	 */
	public int size()
	{
		return map.size();
	}

	/**
	 * Returns <tt>true</tt> if this map contains no key-value mappings.
	 * 
	 * @return <tt>true</tt> if this map contains no key-value mappings
	 */
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	/**
	 * Returns the elements with the specified amount in the map.
	 * 
	 * @param value
	 *            the element amount contained in the map
	 * @return the list of elements
	 */
	public Vector<T> getElementsByMatch(int value)
	{
		Vector<T> v = new Vector();
		for (Entry<T, Integer> e : map.entrySet())
			if (e.getValue() == value)
				v.add(e.getKey());
		return v;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return map.toString();
	}
}
