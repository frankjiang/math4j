/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * MultiHashMap.java is PROPRIETARY/CONFIDENTIAL built in 1:40:24 AM, Apr 7,
 * 2014.
 * Use is subject to license terms.
 */
package com.frank.math.struct.mmap;

import java.util.Collection;
import java.util.HashMap;
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
public abstract class MultiMap<K, V> implements Map<K, Collection<V>>,
		java.io.Serializable
{
	/**
	 * serialVersionUID.
	 */
	private static final long				serialVersionUID	= 4718399716167029093L;
	/**
	 * The inner map implementation.
	 */
	protected Map<K, Collection<V>>			map;
	/**
	 * The list type.
	 */
	protected Class<? extends Collection>	collectionType;

	/**
	 * Construct an instance of <tt>AbstractMultiMap</tt>.
	 * 
	 * @param map
	 *            the inner map implementation
	 * @param listType
	 *            the list type
	 */
	protected MultiMap(Map<K, Collection<V>> map,
			Class<? extends Collection> listType)
	{
		this.map = map;
		this.collectionType = listType;
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
	public Collection<V> remove(Object key)
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
	public Collection<Collection<V>> values()
	{
		return map.values();
	}

	/**
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<K, Collection<V>>> entrySet()
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
		for (Collection<V> c : map.values())
			for (V e : c)
				if (e.equals(value))
					return true;
		return false;
	}

	/**
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public Collection<V> get(Object key)
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
	public Collection<V> getNotNull(K key)
	{
		Collection<V> list = null;
		if (!map.containsKey(key))
		{
			try
			{
				list = collectionType.newInstance();
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
		else
			list = map.get(key);
		return list;
	}

	/**
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Collection<V> put(K key, Collection<V> value)
	{
		Collection<V> list = getNotNull(key);
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
	public Collection<V> putOne(K key, V value)
	{
		Collection<V> list = getNotNull(key);
		list.add(value);
		return list;
	}

	/**
	 * Copies all of the mappings from the specified map to this map
	 * (optional operation). The effect of this call is equivalent to that
	 * of calling {@link #put(Object,Object) put(k, v)} on this map once
	 * for each mapping from key <tt>k</tt> to value <tt>v</tt> in the
	 * specified map. The behavior of this operation is undefined if the
	 * specified map is modified while the operation is in progress.
	 *
	 * @param m
	 *            mappings to be stored in this map
	 * @throws UnsupportedOperationException
	 *             if the <tt>putAll</tt> operation
	 *             is not supported by this map
	 * @throws ClassCastException
	 *             if the class of a key or value in the
	 *             specified map prevents it from being stored in this map
	 * @throws NullPointerException
	 *             if the specified map is null, or if
	 *             this map does not permit null keys or values, and the
	 *             specified map contains null keys or values
	 * @throws IllegalArgumentException
	 *             if some property of a key or value in
	 *             the specified map prevents it from being stored in this map
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAllValues(Map<? extends K, ? extends V> m)
	{
		for (Entry<? extends K, ? extends V> e : m.entrySet())
			putOne(e.getKey(), e.getValue());
	}

	/**
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends Collection<V>> m)
	{
		for (Entry<? extends K, ? extends Collection<V>> e : m.entrySet())
		{
			K key = e.getKey();
			for (V value : e.getValue())
				putOne(key, value);
		}
	}

	/**
	 * Returns the inner list type.
	 * 
	 * @return the inner list type.
	 */
	public Class<? extends Collection> getCollectionType()
	{
		return collectionType;
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
