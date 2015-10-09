/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * MultiHashMap.java is PROPRIETARY/CONFIDENTIAL built in 2:11:54 AM, Apr 7,
 * 2014.
 * Use is subject to license terms.
 */
package com.frank.math.struct.mmap;

import java.util.HashMap;
import java.util.List;

/**
 * A multiplied value map implemented with {@linkplain HashMap hash map}.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class MultiHashMap<K, V> extends MultiMap<K, V>
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= 3910837870815796510L;

	/**
	 * Construct an instance of <tt>MultiHashMap</tt>.
	 * 
	 * @param collectionType
	 *            the inner list type
	 */
	public MultiHashMap()
	{
		super(new HashMap(), java.util.LinkedList.class);
	}

	/**
	 * Construct an instance of <tt>MultiHashMap</tt>.
	 * 
	 * @param listType
	 *            the inner list type
	 */
	public MultiHashMap(Class<? extends List> listType)
	{
		super(new HashMap(), listType);
	}
}
