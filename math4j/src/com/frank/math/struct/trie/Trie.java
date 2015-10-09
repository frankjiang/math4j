/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * Trie.java is PROPRIETARY/CONFIDENTIAL built in 8:04:53 PM, Sep 8, 2015.
 * Use is subject to license terms.
 */
package com.frank.math.struct.trie;

/**
 * TODO The implementation of a trie.
 * <p>
 * In this implementation, the value can be divided into keys, and be organized
 * by the prefix of keys.
 * </p>
 * <p>
 * For example, a string value "abc", its keys are 'a', 'b', 'c'. Therefore,
 * String is value type, Character is key type.
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @param <K>
 *            The key type of a trie
 * @param <V>
 *            The value type of a trie
 * @version 1.0.0
 */
public class Trie<K, V>
{
	/**
	 * The prefix iterator.
	 */
	protected PrefixIterable<K, V>	prefixIterator;
	
	// protected void 

	/**
	 * Construct an instance of <tt>Trie</tt>.
	 * 
	 * @param prefixIterator
	 *            the prefix iterator
	 */
	public Trie(PrefixIterable<K, V> prefixIterator)
	{
		this.prefixIterator = prefixIterator;
	}

	public void add(V value)
	{
	}
}
