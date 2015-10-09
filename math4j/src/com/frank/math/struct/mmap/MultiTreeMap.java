/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * MultiTreeMap.java is PROPRIETARY/CONFIDENTIAL built in 2:12:47 AM, Apr 7,
 * 2014.
 * Use is subject to license terms.
 */
package com.frank.math.struct.mmap;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A multiplied value map implemented with {@linkplain LinkedHashMap tree map}.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class MultiTreeMap<K, V> extends MultiMap<K, V> implements
		NavigableMap<K, Collection<V>>
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= -8879110194462801985L;

	/**
	 * Construct an instance of <tt>MultiTreeMap</tt>.
	 */
	public MultiTreeMap()
	{
		super(new TreeMap(), java.util.LinkedList.class);
	}

	/**
	 * Construct an instance of <tt>MultiTreeMap</tt>.
	 * 
	 * @param listType
	 *            the inner list type
	 */
	public MultiTreeMap(Class<? extends Collection> listType)
	{
		super(new TreeMap(), listType);
	}

	/**
	 * Returns the real tree map.
	 * 
	 * @return the real tree map.
	 */
	protected TreeMap<K, Collection<V>> getRealMap()
	{
		return (TreeMap<K, Collection<V>>) map;
	}

	/**
	 * @see java.util.SortedMap#comparator()
	 */
	@Override
	public Comparator<? super K> comparator()
	{
		return getRealMap().comparator();
	}

	/**
	 * @see java.util.SortedMap#firstKey()
	 */
	@Override
	public K firstKey()
	{
		return getRealMap().firstKey();
	}

	/**
	 * @see java.util.SortedMap#lastKey()
	 */
	@Override
	public K lastKey()
	{
		return getRealMap().lastKey();
	}

	/**
	 * @see java.util.NavigableMap#lowerEntry(java.lang.Object)
	 */
	@Override
	public Entry<K, Collection<V>> lowerEntry(K key)
	{
		return getRealMap().lowerEntry(key);
	}

	/**
	 * @see java.util.NavigableMap#lowerKey(java.lang.Object)
	 */
	@Override
	public K lowerKey(K key)
	{
		return getRealMap().lowerKey(key);
	}

	/**
	 * @see java.util.NavigableMap#floorEntry(java.lang.Object)
	 */
	@Override
	public Entry<K, Collection<V>> floorEntry(K key)
	{
		return getRealMap().floorEntry(key);
	}

	/**
	 * @see java.util.NavigableMap#floorKey(java.lang.Object)
	 */
	@Override
	public K floorKey(K key)
	{
		return getRealMap().floorKey(key);
	}

	/**
	 * @see java.util.NavigableMap#ceilingEntry(java.lang.Object)
	 */
	@Override
	public Entry<K, Collection<V>> ceilingEntry(K key)
	{
		return getRealMap().ceilingEntry(key);
	}

	/**
	 * @see java.util.NavigableMap#ceilingKey(java.lang.Object)
	 */
	@Override
	public K ceilingKey(K key)
	{
		return getRealMap().ceilingKey(key);
	}

	/**
	 * @see java.util.NavigableMap#higherEntry(java.lang.Object)
	 */
	@Override
	public Entry<K, Collection<V>> higherEntry(K key)
	{
		return getRealMap().higherEntry(key);
	}

	/**
	 * @see java.util.NavigableMap#higherKey(java.lang.Object)
	 */
	@Override
	public K higherKey(K key)
	{
		return getRealMap().higherKey(key);
	}

	/**
	 * @see java.util.NavigableMap#firstEntry()
	 */
	@Override
	public Entry<K, Collection<V>> firstEntry()
	{
		return getRealMap().firstEntry();
	}

	/**
	 * @see java.util.NavigableMap#lastEntry()
	 */
	@Override
	public Entry<K, Collection<V>> lastEntry()
	{
		return getRealMap().lastEntry();
	}

	/**
	 * @see java.util.NavigableMap#pollFirstEntry()
	 */
	@Override
	public Entry<K, Collection<V>> pollFirstEntry()
	{
		return getRealMap().pollFirstEntry();
	}

	/**
	 * @see java.util.NavigableMap#pollLastEntry()
	 */
	@Override
	public Entry<K, Collection<V>> pollLastEntry()
	{
		return getRealMap().pollLastEntry();
	}

	/**
	 * @see java.util.NavigableMap#descendingMap()
	 */
	@Override
	public NavigableMap<K, Collection<V>> descendingMap()
	{
		return getRealMap().descendingMap();
	}

	/**
	 * @see java.util.NavigableMap#navigableKeySet()
	 */
	@Override
	public NavigableSet<K> navigableKeySet()
	{
		return getRealMap().navigableKeySet();
	}

	/**
	 * @see java.util.NavigableMap#descendingKeySet()
	 */
	@Override
	public NavigableSet<K> descendingKeySet()
	{
		return getRealMap().descendingKeySet();
	}

	/**
	 * @see java.util.NavigableMap#subMap(java.lang.Object, boolean,
	 *      java.lang.Object, boolean)
	 */
	@Override
	public NavigableMap<K, Collection<V>> subMap(K fromKey, boolean fromInclusive,
			K toKey, boolean toInclusive)
	{
		return getRealMap().subMap(fromKey, fromInclusive, toKey, toInclusive);
	}

	/**
	 * @see java.util.NavigableMap#headMap(java.lang.Object, boolean)
	 */
	@Override
	public NavigableMap<K, Collection<V>> headMap(K toKey, boolean inclusive)
	{
		return getRealMap().headMap(toKey, inclusive);
	}

	/**
	 * @see java.util.NavigableMap#tailMap(java.lang.Object, boolean)
	 */
	@Override
	public NavigableMap<K, Collection<V>> tailMap(K fromKey, boolean inclusive)
	{
		return getRealMap().tailMap(fromKey, inclusive);
	}

	/**
	 * @see java.util.NavigableMap#subMap(java.lang.Object, java.lang.Object)
	 */
	@Override
	public SortedMap<K, Collection<V>> subMap(K fromKey, K toKey)
	{
		return getRealMap().subMap(fromKey, toKey);
	}

	/**
	 * @see java.util.NavigableMap#headMap(java.lang.Object)
	 */
	@Override
	public SortedMap<K, Collection<V>> headMap(K toKey)
	{
		return getRealMap().headMap(toKey);
	}

	/**
	 * @see java.util.NavigableMap#tailMap(java.lang.Object)
	 */
	@Override
	public SortedMap<K, Collection<V>> tailMap(K fromKey)
	{
		return getRealMap().tailMap(fromKey);
	}
}
