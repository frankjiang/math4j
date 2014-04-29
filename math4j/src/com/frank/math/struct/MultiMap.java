/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * MultiHashMap.java is PROPRIETARY/CONFIDENTIAL built in 1:40:24 AM, Apr 7,
 * 2014.
 * Use is subject to license terms.
 */
package com.frank.math.struct;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The multiplied values hash map.
 * <p>
 * In this map, use {@linkplain HashMap} as inner implementation.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public abstract class MultiMap<K, V> implements Map<K, List<V>>,
		java.io.Serializable
{
	/**
	 * serialVersionUID.
	 */
	private static final long		serialVersionUID	= 4718399716167029093L;
	/**
	 * The inner map implementation.
	 */
	protected Map<K, List<V>>		map;
	/**
	 * The list type.
	 */
	protected Class<? extends List>	listType;

	/**
	 * Construct an instance of <tt>AbstractMultiMap</tt>.
	 * 
	 * @param map
	 *            the inner map implementation
	 * @param listType
	 *            the list type
	 */
	protected MultiMap(Map<K, List<V>> map, Class<? extends List> listType)
	{
		this.map = map;
		this.listType = listType;
	}

	// normal part
	/**
	 * @see java.util.Map#size()
	 */
	@Override
	public int size()
	{
		return map.size();
	}

	/**
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	/**
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key)
	{
		return map.containsKey(key);
	}

	/**
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public List<V> remove(Object key)
	{
		return map.remove(key);
	}

	/**
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear()
	{
		map.clear();
	}

	/**
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<K> keySet()
	{
		return map.keySet();
	}

	/**
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<List<V>> values()
	{
		return map.values();
	}

	/**
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<K, List<V>>> entrySet()
	{
		return map.entrySet();
	}

	// special part
	/**
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value)
	{
		for (List<V> c : map.values())
			for (V e : c)
				if (e.equals(value))
					return true;
		return false;
	}

	/**
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public List<V> get(Object key)
	{
		return map.get(key);
	}

	/**
	 * Returns a not null list.
	 * <p>
	 * If the original value is null, then instancing a new list and put it into
	 * the map.
	 * </p>
	 * 
	 * @param key
	 *            the key whose associated value is to be returned
	 * @return the values list to which the specified key is mapped
	 */
	public List<V> getNotNull(K key)
	{
		List<V> list = map.get(key);
		if (list == null)
		{
			try
			{
				list = listType.newInstance();
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			map.put(key, list);
		}
		return list;
	}

	/**
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public List<V> put(K key, List<V> value)
	{
		List<V> list = getNotNull(key);
		list.addAll(value);
		return list;
	}

	/**
	 * Associates the specified value with the specified key in this map
	 * (optional operation). If the map previously contained a mapping for
	 * the key, the old value is replaced by the specified value. (A map
	 * <tt>m</tt> is said to contain a mapping for a key <tt>k</tt> if and only
	 * if {@link #containsKey(Object) m.containsKey(k)} would return
	 * <tt>true</tt>.)
	 * 
	 * @param key
	 *            key with which the specified value is to be associated
	 * @param value
	 *            value to be associated with the specified key
	 * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
	 *         if there was no mapping for <tt>key</tt>.
	 *         (A <tt>null</tt> return can also indicate that the map
	 *         previously associated <tt>null</tt> with <tt>key</tt>,
	 *         if the implementation supports <tt>null</tt> values.)
	 * @throws UnsupportedOperationException
	 *             if the <tt>put</tt> operation
	 *             is not supported by this map
	 * @throws ClassCastException
	 *             if the class of the specified key or value
	 *             prevents it from being stored in this map
	 * @throws NullPointerException
	 *             if the specified key or value is null
	 *             and this map does not permit null keys or values
	 * @throws IllegalArgumentException
	 *             if some property of the specified key
	 *             or value prevents it from being stored in this map
	 */
	public List<V> put(K key, V value)
	{
		List<V> list = getNotNull(key);
		list.add(value);
		return list;
	}

	/**
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends List<V>> m)
	{
		for (Entry<? extends K, ? extends List<V>> e : map.entrySet())
			map.put(e.getKey(), e.getValue());
	}

	/**
	 * Returns the inner list type.
	 * 
	 * @return the inner list type.
	 */
	public Class<? extends List> getListType()
	{
		return listType;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return map.toString();
	}
}
