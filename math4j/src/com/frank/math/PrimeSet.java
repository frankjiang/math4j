/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved.
 * PrimeSet.java is built in 2011-11-3.
 */
package com.frank.math;

/**
 * The implementation of prime numbers searching set.
 * <p>
 * Any prime integers can be search in the synchronized singleton set. The
 * instance of the singleton will get by {@linkplain PrimeSet#getInstance()}.
 * </p>
 * <p>
 * While retrieving the prime numbers the maximum limits the current range of
 * primes, when the request number is greater than the maximum, the set of
 * primes will be updated, this will cost some time; the primes are stored in a
 * {@link java.util.LinkedHashSet}
 * </p>
 * 
 * @author <a href="mailto:jiangfan0576@gmail.com">Frank Jiang</a>
 * @version 1.0.0
 */
public final class PrimeSet
{
	/**
	 * The singleton of prime set.
	 */
	private volatile static PrimeSet	instance;

	/**
	 * Get the instance of prime set.
	 * 
	 * @return the prime set
	 */
	public static synchronized PrimeSet getInstance()
	{
		if (PrimeSet.instance == null)
			PrimeSet.instance = new PrimeSet();
		return PrimeSet.instance;
	}

	/**
	 * The maximum of current range.
	 */
	private int									maximum	= 100;
	/**
	 * The set stores primes.
	 */
	private java.util.LinkedHashSet<Integer>	primes;

	/**
	 * Construct an instance of PrimeSet with maximum 100.
	 */
	private PrimeSet()
	{
		primes = new java.util.LinkedHashSet<Integer>();
		for (int i = 2; i <= maximum; i++)
			if (isPrime0(i))
				primes.add(i);
	}

	/**
	 * Returns true if i is prime, false otherwise.
	 * 
	 * @param i
	 *            the specified integer
	 * @return true if i is prime, false otherwise.
	 */
	public boolean isPrime(Integer i)
	{
		if (i > maximum)
		{
			boolean flag = isPrime0(i);
			for (int n = maximum + 1; n <= i; n++)
				if (isPrime0(n))
					primes.add(n);
			return flag;
		}
		else
			return primes.contains(i);
	}

	/**
	 * The standard algorithm of fast prime judging.
	 * <p>
	 * The primes are the numbers which is greater than 1, than can only be
	 * divided exactly by 1 and itself.
	 * </p>
	 * 
	 * @param i
	 *            the specified integer
	 * @return true if i is prime, false otherwise.
	 */
	private boolean isPrime0(Integer i)
	{
		if (i < 2)
			return false;
		for (int n = 2; n <= Math.sqrt(i); n++)
			if (i % n == 0)
				return false;
		return true;
	}
}
