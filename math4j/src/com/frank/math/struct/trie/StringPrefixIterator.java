/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * StringPrefixIterator.java is PROPRIETARY/CONFIDENTIAL built in 8:16:25 PM,
 * Sep 8, 2015.
 * Use is subject to license terms.
 */
package com.frank.math.struct.trie;

import java.nio.CharBuffer;
import java.util.Collection;

/**
 * The prefix iterator for strings.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class StringPrefixIterator implements PrefixIterable<Character, String>
{
	/**
	 * @see com.frank.math.struct.trie.PrefixIterable#size(java.lang.Object)
	 */
	@Override
	public int size(String value)
	{
		return value.length();
	}

	/**
	 * @see com.frank.math.struct.trie.PrefixIterable#keyAt(java.lang.Object,
	 *      int)
	 */
	@Override
	public Character keyAt(String value, int index)
	{
		return value.charAt(index);
	}

	/**
	 * @see com.frank.math.struct.trie.PrefixIterable#combine(java.util.Collection)
	 */
	@Override
	public String combine(Collection<Character> keys)
	{
		CharBuffer buf = CharBuffer.allocate(keys.size());
		for (Character key : keys)
			buf.append(key);
		return buf.toString();
	}
}
