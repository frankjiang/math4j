/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * TrieNode.java is PROPRIETARY/CONFIDENTIAL built in 8:22:48 PM, Sep 8, 2015.
 * Use is subject to license terms.
 */
package com.frank.math.struct.trie;

import java.util.Map;
import java.util.TreeMap;

/**
 * TODO The trie node.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @param <K> The key type of the trie.
 * @version 1.0.0
 */
public class TrieNode<K>
{
	protected K						key;
	protected int					count;
	protected Map<K, TrieNode<K>>	mappings;

	/**
	 * Construct an instance of <tt>TrieNode</tt>.
	 * 
	 * @param key
	 */
	public TrieNode(K key)
	{
		this(key, key == null ? 0 : 1, new TreeMap());
	}

	/**
	 * Construct an instance of <tt>TrieNode</tt>.
	 * 
	 * @param key
	 * @param mapType
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public TrieNode(K key, Class<? extends Map> mapType) throws InstantiationException, IllegalAccessException
	{
		this(key, key == null ? 0 : 1, mapType);
	}

	/**
	 * Construct an instance of <tt>TrieNode</tt>.
	 * 
	 * @param key
	 * @param count
	 * @param mapType
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public TrieNode(K key, int count, Class<? extends Map> mapType) throws InstantiationException,
			IllegalAccessException
	{
		this.count = count;
		this.mappings = mapType.newInstance();
	}

	/**
	 * Construct an instance of <tt>TrieNode</tt>.
	 * 
	 * @param key
	 * @param count
	 * @param mappings
	 */
	public TrieNode(K key, int count, Map<K, TrieNode<K>> mappings)
	{
		this.count = count;
		this.mappings = mappings;
	}

	public int countChildren()
	{
		int count = this.count;
		if (mappings != null)
			for (TrieNode<K> node : mappings.values())
				count += node.countChildren();
		return count;
	}

	public K getKey()
	{
		return key;
	}

	public <V> void add(int index, V value, PrefixIterable<K, V> prefixIterator)
	{
		// if(index < prefixIterator.size(value))
		// K key = prefixIterator.keyAt(value, index);
		// if(mappings==null || mappings.isEmpty())
			
	}
	
	public <V> int remove(int index, V value, PrefixIterable<K, V> prefixIterator)
	{
		return 0;
	}
}
