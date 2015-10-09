/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * ListPrefixIterator.java is PROPRIETARY/CONFIDENTIAL built in 8:18:33 PM, Sep
 * 8, 2015.
 * Use is subject to license terms.
 */
package com.frank.math.struct.trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The prefix iterator for lists.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class ListPrefixIterator<E> implements PrefixIterable<E, List<E>>
{
	/**
	 * @see com.frank.math.struct.trie.PrefixIterable#size(java.lang.Object)
	 */
	@Override
	public int size(List<E> value)
	{
		return value.size();
	}

	/**
	 * @see com.frank.math.struct.trie.PrefixIterable#keyAt(java.lang.Object,
	 *      int)
	 */
	@Override
	public E keyAt(List<E> value, int index)
	{
		return value.get(index);
	}

	/**
	 * @see com.frank.math.struct.trie.PrefixIterable#combine(java.util.Collection)
	 */
	@Override
	public List<E> combine(Collection<E> keys)
	{
		return new ArrayList<>(keys);
	}
}
