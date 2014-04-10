/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * MultiTreeMap.java is PROPRIETARY/CONFIDENTIAL built in 2:12:47 AM, Apr 7,
 * 2014.
 * Use is subject to license terms.
 */
package com.frank.math.struct;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * A multiplied value map implemented with {@linkplain LinkedHashMap tree map}.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public class MultiTreeMap<K, V> extends MultiMap<K, V>
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
	public MultiTreeMap(Class<? extends List> listType)
	{
		super(new TreeMap(), listType);
	}
}
