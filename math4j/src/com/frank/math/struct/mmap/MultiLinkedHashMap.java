/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * MultiLinkedHashMap.java is PROPRIETARY/CONFIDENTIAL built in 2:13:06 AM, Apr
 * 7, 2014.
 * Use is subject to license terms.
 */
package com.frank.math.struct.mmap;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * A multiplied value map implemented with {@linkplain LinkedHashMap linked hash
 * map}.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class MultiLinkedHashMap<K, V> extends MultiMap<K, V>
{
	/**
	 * serialVersionUID.
	 */
	private static final long	serialVersionUID	= 4256922079669533399L;

	/**
	 * Construct an instance of <tt>MultiLinkedHashMap</tt>.
	 */
	public MultiLinkedHashMap()
	{
		super(new LinkedHashMap(), java.util.LinkedList.class);
	}

	/**
	 * Construct an instance of <tt>MultiLinkedHashMap</tt>.
	 * 
	 * @param listType
	 *            the inner list type
	 */
	public MultiLinkedHashMap(Class<? extends List> listType)
	{
		super(new LinkedHashMap(), listType);
	}
}
