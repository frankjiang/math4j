/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * PrefixIterable.java is PROPRIETARY/CONFIDENTIAL built in 8:13:30 PM, Sep 8,
 * 2015.
 * Use is subject to license terms.
 */
package com.frank.math.struct.trie;

import java.util.Collection;

/**
 * The prefix iterable iterface for a trie.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @param <K>
 *            The key type of a trie
 * @param <V>
 *            The value type of a trie
 * @version 1.0.0
 */
public interface PrefixIterable<K, V>
{
	/**
	 * Returns the size of the value, which is the number of keys contained.
	 * 
	 * @param value
	 *            the value to check
	 * @return the size of the value
	 */
	public int size(V value);

	/**
	 * Returns the key at the specified position.
	 * 
	 * @param value
	 *            the value to check
	 * @param index
	 *            the index of the position
	 * @return the returned key
	 */
	public K keyAt(V value, int index);
	
	/**
	 * Combines a collection of the keys and returns its value.
	 * @param keys the keys
	 * @return the combined value
	 */
	public V combine(Collection<K> keys);
}
