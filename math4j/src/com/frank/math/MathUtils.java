/*
 * Copyright (c) 2011, 2020, Frank Jiang and/or its affiliates. All rights
 * reserved. Math.java is built in 2012-11-14.
 */
package com.frank.math;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.Vector;

import com.frank.math.matrix.Matrix;
import com.frank.math.struct.SearchMap;

/**
 * The mathematics utilities, which involves the distance measure, angle
 * measure, area measure and statistics such as calculating the standard
 * deviation, relative error, maximum of a set, minimum of a set, average of a
 * set, and so on.
 * 
 * @author Frank Jiang
 * @version 1.0.0
 */
public class MathUtils
{
	// Geometry algorithms.
	/**
	 * Calculate the angle of the vector in radians (0,2&pi;).
	 * 
	 * @param x
	 *            The X-axis coordinate of the vector.
	 * @param y
	 *            The Y-axis coordinate of the vector.
	 * @return The angle angle in radians (0,2&pi;).
	 */
	public static final double angle(double x, double y)
	{
		if (x > 0.0)
		{
			if (y > 0.0) // First quadrant.
				return Math.atan(y / x);
			else if (y == 0.0)// Positive X-axis.
				return 0.0;
			else
				// Fourth quadrant.
				return Math.atan(y / x) + 2 * Math.PI;
		}
		else if (x == 0.0)
		{
			if (y > 0.0)// Positive Y-axis.
				return Math.PI / 2;
			else if (y == 0.0) // Original point.
				return Double.NaN;// Negative Y-axis.
			else
				return Math.PI * 1.5;
		}
		else if (y > 0.0)// Second quadrant.
			return Math.atan(y / x) + Math.PI;
		else if (y == 0.0) // Negative X-axis.
			return Math.PI;
		else
			// Third quadrant.
			return Math.atan(y / x) + Math.PI;
	}

	/**
	 * Calculate the angle of two vectors, the angle is from the first vector to
	 * the second. Formula:<br>
	 * theta1 = arctan(y1/x1)<br>
	 * theta2 = arctan(y2/x2)<br>
	 * theta = theta1 - theta2
	 * 
	 * @param x1
	 *            The X-axis coordinate of the first vector.
	 * @param y1
	 *            The Y-axis coordinate of the first vector.
	 * @param x2
	 *            The X-axis coordinate of the second vector.
	 * @param y2
	 *            The Y-axis coordinate of the second vector.
	 * @return The angle of the two vectors, in degree.
	 */
	public static final double angle(double x1, double y1, double x2, double y2)
	{
		return (MathUtils.angle(x1, y1) - MathUtils.angle(x2, y2)) * 180.0 / Math.PI;
	}

	/**
	 * Calculate the value of the angle [0,180) degree. The angle is calculating
	 * according to cosine theory.
	 * 
	 * @param top
	 *            The top of the angle.
	 * @param corner1
	 *            The left corner point of the angle.
	 * @param corner2
	 *            The right corner point of the angle.
	 * @return The angle in degree.
	 */
	public static final double angle180(Point2D top, Point2D corner1, Point2D corner2)
	{
		return Math.toDegrees(anglePI(top, corner1, corner2));
	}

	/**
	 * Calculate the value of the angle [-180,180) degree.
	 * 
	 * @param top
	 *            The top of the angle.
	 * @param corner1
	 *            The left corner point of the angle.
	 * @param corner2
	 *            The right corner point of the angle.
	 * @return The angle in degree.
	 */
	public static final double angle360(Point2D top, Point2D corner1, Point2D corner2)
	{
		double a1 = Math.toDegrees(Math.atan2(corner1.getY() - top.getY(), corner1.getX() - top.getX()));
		double a2 = Math.toDegrees(Math.atan2(corner2.getY() - top.getY(), corner2.getX() - top.getX()));
		if (a1 < 0)
			a1 += 360;
		if (a2 < 0)
			a2 += 360;
		double res = a1 - a2;
		return res;
	}

	/**
	 * Calculate the value of the angle [0,PI). The angle is calculating
	 * according to cosine theory.
	 * 
	 * @param top
	 *            The top of the angle.
	 * @param corner1
	 *            The left corner point of the angle.
	 * @param corner2
	 *            The right corner point of the angle.
	 * @return The angle in radius.
	 */
	public static final double anglePI(Point2D top, Point2D corner1, Point2D corner2)
	{
		double x1 = corner1.getX() - top.getX();
		double x2 = corner2.getX() - top.getX();
		double y1 = corner1.getY() - top.getY();
		double y2 = corner2.getY() - top.getY();
		return Math.acos((x1 * x2 + y1 * y2) / Math.sqrt((x1 * x1 + y1 * y1) * (x2 * x2 + y2 * y2)));
	}

	/**
	 * Calculate the average of the number collection.
	 * 
	 * @param c
	 *            The number collection.
	 * @return The average.
	 */
	public static final double average(Collection<? extends Number> c)
	{
		if (c.isEmpty())
			throw new NullPointerException(Messages.getString("MathUtils.EmptyCollection")); //$NON-NLS-1$
		double summary = 0.0;
		for (Number n : c)
			summary += n.doubleValue();
		return summary / c.size();
	}

	/**
	 * Returns the average of specified complexes.
	 * 
	 * @param x
	 *            the specified complexes
	 * @return the average
	 */
	public static final Complex average(Complex[] x)
	{
		Complex c = MathUtils.summary(x);
		c.real /= x.length;
		c.imaginary /= x.length;
		return c;
	}

	/**
	 * Returns the average of specified <tt>double</tt> array.
	 * 
	 * @param x
	 *            the specified <tt>double</tt> array
	 * @return the average
	 */
	public static final double average(double[] x)
	{
		return MathUtils.summary(x) / (double) x.length;
	}

	/**
	 * Returns the average of specified <tt>float</tt> array.
	 * 
	 * @param x
	 *            the specified <tt>float</tt> array
	 * @return the average
	 */
	public static final double average(float[] x)
	{
		return MathUtils.summary(x) / (double) x.length;
	}

	/**
	 * Returns the average of specified <tt>int</tt> array.
	 * 
	 * @param x
	 *            the specified <tt>int</tt> matrix
	 * @return the average
	 */
	public static final double average(int[] x)
	{
		return MathUtils.summary(x) / (double) x.length;
	}

	/**
	 * Returns the average of specified <tt>long</tt> array.
	 * 
	 * @param x
	 *            the specified <tt>long</tt> array
	 * @return the average
	 */
	public static final double average(long[] x)
	{
		return MathUtils.summary(x) / (double) x.length;
	}

	/**
	 * Returns the average of <code>x</code>.
	 * 
	 * @param x
	 *            the specified array
	 * @return the average
	 */
	public static final <T extends Number> double average(T[] x)
	{
		return MathUtils.summary(x) / (double) x.length;
	}

	/**
	 * Calculate the center point of the specified point set.
	 * 
	 * @param c
	 *            the specified point set
	 * @return center
	 */
	public static Point2D center(Collection<? extends Point2D> c)
	{
		double sumX = 0.0;
		double sumY = 0.0;
		for (Point2D p : c)
		{
			sumX += p.getX();
			sumY += p.getY();
		}
		return new Point2D.Double(sumX / c.size(), sumY / c.size());
	}

	/**
	 * Returns the amount of combinations when base size of elements be combined
	 * to value size elements.
	 * 
	 * @param base
	 * @param value
	 * @return the amount of combinations
	 */
	public static final long combine(int base, int value)
	{
		if (value > base || base < 0 || value < 0)
			throw new IllegalArgumentException(String.format(Messages.getString("MathUtils.IllegalBaseAndValue"), base, //$NON-NLS-1$
					value));
		int min = base - value;
		int max = value;
		int t;
		if (max < min)
		{
			t = min;
			min = max;
			max = t;
		}
		long v = 1;
		for (int i = max + 1; i <= base; i++)
			v *= i;
		return v / factorial(min);
	}

	/**
	 * Combined sorting, which sort in the natural order of the specified key.
	 * <p>
	 * The data in the original array will also be sorted to the natural order.
	 * </p>
	 * 
	 * @param <T>
	 *            The type of the specified key.
	 * @param data
	 *            The data to sort.
	 * @param keys
	 *            The sorting keys.
	 * @return The sorted data array.
	 */
	public static final <T> T[] combineSort(T[] data, Comparable[] keys)
	{
		if (data.length != keys.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.UncomatibleKeyAndData")); //$NON-NLS-1$
		/**
		 * Sorting structure.
		 * 
		 * @author Frank Jiang
		 * @version 1.0.0
		 */
		class Bean implements java.lang.Comparable<Bean>
		{
			public Comparable	key;
			public T			node;

			public Bean(T node, Comparable key)
			{
				this.node = node;
				this.key = key;
			}

			/**
			 * @see java.lang.Comparable#compareTo(java.lang.Object)
			 */
			@Override
			public int compareTo(Bean o)
			{
				return key.compareTo(o.key);
			}
		}
		Bean[] a = new Bean[data.length];
		for (int i = 0; i < data.length; i++)
			a[i] = new Bean(data[i], keys[i]);
		Arrays.sort(a);
		for (int i = 0; i < data.length; i++)
		{
			data[i] = a[i].node;
			keys[i] = a[i].key;
		}
		return data;
	}

	/**
	 * Combined sorting, which sort in the natural order of the specified key.
	 * 
	 * @param <T>
	 *            The type of the specified key.
	 * @param data
	 *            The data to sort.
	 * @param keys
	 *            The sorting keys.
	 * @return The sorted data array.
	 * @exception IllegalAccessException
	 *                if the class or its nullary
	 *                constructor is not accessible.
	 * @exception InstantiationException
	 *                if this {@code Class} represents an abstract class,
	 *                an interface, an array class, a primitive type, or void;
	 *                or if the class has no nullary constructor;
	 *                or if the instantiation fails for some other reason.
	 * @exception ExceptionInInitializerError
	 *                if the initialization
	 *                provoked by this method fails.
	 * @exception SecurityException
	 *                If a security manager, <i>s</i>, is present and any of the
	 *                following conditions is met:
	 *                <ul>
	 *                <li>invocation of
	 *                {@link SecurityManager#checkMemberAccess
	 *                s.checkMemberAccess(this, Member.PUBLIC)} denies creation
	 *                of new instances of this class
	 *                <li>the caller's class loader is not the same as or an
	 *                ancestor of the class loader for the current class and
	 *                invocation of {@link SecurityManager#checkPackageAccess
	 *                s.checkPackageAccess()} denies access to the package of
	 *                this class
	 *                </ul>
	 */
	public static final <T> List<T> combineSort(List<T> data, List<? extends Comparable> keys)
			throws InstantiationException, IllegalAccessException
	{
		if (data.size() != keys.size())
			throw new IllegalArgumentException(Messages.getString("MathUtils.2")); //$NON-NLS-1$
		/**
		 * Sorting structure.
		 * 
		 * @author Frank Jiang
		 * @version 1.0.0
		 */
		class Bean implements java.lang.Comparable<Bean>
		{
			public Comparable	key;
			public T			node;

			public Bean(T node, Comparable key)
			{
				this.node = node;
				this.key = key;
			}

			/**
			 * @see java.lang.Comparable#compareTo(java.lang.Object)
			 */
			@Override
			public int compareTo(Bean o)
			{
				return key.compareTo(o.key);
			}
		}
		Bean[] a = new Bean[data.size()];
		for (int i = 0; i < data.size(); i++)
			a[i] = new Bean(data.get(i), keys.get(i));
		Arrays.sort(a);
		List<T> list = data.getClass().newInstance();
		for (int i = 0; i < data.size(); i++)
			list.add(a[i].node);
		return list;
	}

	/**
	 * Combined sorting, which sort in the natural order of the specified key.
	 * 
	 * @param <T>
	 *            The type of the specified key.
	 * @param data
	 *            The data to sort.
	 * @param keys
	 *            The sorting keys.
	 * @return The sorted data array.
	 */
	public static final <T> T[] combineSort(T[] data, double[] keys)
	{
		if (data.length != keys.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.2")); //$NON-NLS-1$
		/**
		 * Sorting structure.
		 * 
		 * @author Frank Jiang
		 * @version 1.0.0
		 */
		class Bean implements java.lang.Comparable<Bean>
		{
			public double	key;
			public T		node;

			public Bean(T node, double key)
			{
				this.node = node;
				this.key = key;
			}

			/**
			 * @see java.lang.Comparable#compareTo(java.lang.Object)
			 */
			@Override
			public int compareTo(Bean o)
			{
				return (int) Math.signum(key - o.key);
			}
		}
		Bean[] a = new Bean[data.length];
		for (int i = 0; i < data.length; i++)
			a[i] = new Bean(data[i], keys[i]);
		Arrays.sort(a);
		for (int i = 0; i < data.length; i++)
		{
			data[i] = a[i].node;
			keys[i] = a[i].key;
		}
		return data;
	}

	/**
	 * Create a hash set according to the specified text.
	 * 
	 * @param text
	 * @return The hash set.
	 */
	public static final HashSet<Integer> createHashMap(String text)
	{
		HashSet<Integer> set = new HashSet<Integer>();
		StringTokenizer st = new StringTokenizer(text, " ,\uFF0C;\uFF1B\t\n\r"); //$NON-NLS-1$
		while (st.hasMoreTokens())
		{
			String s = st.nextToken();
			if (s.indexOf('-') != 0)
			{
				String[] list = s.split("-"); //$NON-NLS-1$
				Integer min = Integer.valueOf(list[0]);
				Integer max = Integer.valueOf(list[list.length - 1]);
				for (int i = min; i <= max; i++)
					set.add(i);
			}
			else
				set.add(Integer.valueOf(s));
		}
		return set;
	}

	/**
	 * Create a search map according to the specified text. The values is
	 * divided by " ,\uff0c;\uff1b\n\r". The characters "\u2014-"can be used as
	 * range divider. Use "-" can create the number range. e.g. "1-3" = "1,2,3",
	 * but "6-4" = ""
	 * 
	 * @param text
	 *            the text represents the map
	 * @return The search map.
	 */
	public static final SearchMap<Integer> createSearchMap(String text)
	{
		SearchMap<Integer> set = new SearchMap<Integer>();
		text = text.replaceAll("\u2014", "-"); //$NON-NLS-1$ //$NON-NLS-2$
		StringTokenizer st = new StringTokenizer(text, " ,\uFF0C;\uFF1B\t\n\r"); //$NON-NLS-1$
		while (st.hasMoreTokens())
		{
			String s = st.nextToken();
			try
			{
				if (s.indexOf('-') != 0)
				{
					String[] list = s.split("-"); //$NON-NLS-1$
					Integer min = Integer.valueOf(list[0]);
					Integer max = Integer.valueOf(list[list.length - 1]);
					for (int i = min; i <= max; i++)
						set.add(i);
				}
				else
					set.add(Integer.valueOf(s));
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
		}
		return set;
	}

	/**
	 * Calculate the distance from point A to B, no direction.
	 * 
	 * @param a
	 *            Point2D A.
	 * @param b
	 *            Point2D B.
	 * @return The distance.
	 */
	public static final double distance(Point2D a, Point2D b)
	{
		return Point2D.distance(a.getX(), a.getY(), b.getX(), b.getY());
	}

	/**
	 * Calculate the distance from point X to the straight line L.
	 * 
	 * @param x
	 *            Point2D X.
	 * @param l
	 *            Straight line L.
	 * @return The distance.
	 */
	public static final double distance(Point2D x, StraightLine l)
	{
		double c[] = l.getCoefficients();
		return Math.abs(c[0] * x.getX() + c[1] * x.getY() + c[2]) / Math.sqrt(c[0] * c[0] + c[1] * c[1]);
	}

	/**
	 * Calculate the edge of the specified linked area. This algorithm is
	 * implemented according to Robert algorithm.
	 * 
	 * @param points
	 *            the point set of the specified linked area
	 * @return the point set of the edge
	 */
	public static HashSet<Point> edge(Collection<Point> points)
	{
		// Clear the edge set first.
		HashSet<Point> edge = new HashSet<Point>();
		int x = 0;
		int y = 0;
		for (Point p : points)
		{
			if (p.x > x)
				x = p.x;
			if (p.y > y)
				y = p.y;
		}
		// x,y are used as the size of the array, therefore it should be 1
		// larger than the maximum coordinate. In the meanwhile, Robert
		// algorithm needs one more size to deal with the algorithm model,
		// therefore x,y should plus 2.
		x += 2;
		y += 2;
		boolean[][] image = new boolean[x][y];
		boolean[][] copy = image.clone();
		for (Point p : points)
			image[p.x][p.y] = true;
		for (int i = 0; i < image.length - 1; i++)
			for (int j = 0; j < image[i].length - 1; j++)
			{
				// Robert algorithm.
				int p5 = image[i][j] ? 1 : 0;
				int p6 = image[i][j + 1] ? 1 : 0;
				int p8 = image[i + 1][j] ? 1 : 0;
				int p9 = image[i + 1][j + 1] ? 1 : 0;
				copy[i][j] = Math.max(Math.abs(p5 - p9), Math.abs(p8 - p6)) == 1;
			}
		for (int i = 0; i < copy.length - 1; i++)
			for (int j = 0; j < copy[i].length - 1; j++)
				if (copy[i][j])
					edge.add(new Point(i, j));
		return edge;
	}

	/**
	 * Returns the factorial of the specified value.
	 * 
	 * @param value
	 *            the specified value
	 * @return the factorial
	 */
	public static final long factorial(int value)
	{
		if (value > 20)
			throw new IllegalArgumentException(String.format(Messages.getString("MathUtils.OutOfLongInteger"), //$NON-NLS-1$
					value));
		if (value <= 1)
			return 1;
		else
			return value * factorial(value - 1);
	}
	
	/**
	 * Returns the factorial of the specified value.
	 * 
	 * @param value
	 *            the specified value
	 * @return the factorial
	 */
	public static BigInteger factorialLarge(int value)
	{
		BigInteger max = BigInteger.valueOf(value);
		BigInteger v = BigInteger.ONE;
		for (BigInteger i = BigInteger.valueOf(2); i.compareTo(max) < 1; i = i.add(BigInteger.ONE))
			v = v.multiply(i);
		return v;
	}

	/**
	 * Returns the intersection point of the specified lines {@code a} and
	 * {@code b}.
	 * 
	 * @param a
	 * @param b
	 * @return the intersection point
	 */
	public static Point2D intersect(StraightLine a, StraightLine b)
	{
		double x = (a.coefficients[1] * b.coefficients[2] - b.coefficients[1] * a.coefficients[2])
				/ (a.coefficients[0] * b.coefficients[1] - b.coefficients[0] * a.coefficients[1]);
		double y = -(a.coefficients[0] * b.coefficients[2] - b.coefficients[0] * a.coefficients[2])
				/ (a.coefficients[0] * b.coefficients[1] - b.coefficients[0] * a.coefficients[1]);
		if (Double.isNaN(x) || Double.isInfinite(x) || Double.isNaN(y) || Double.isInfinite(y))
			return null;
		else
			return new Point2D.Double(x, y);
	}

	/**
	 * Check the specified value whether it is a prime number.
	 * 
	 * @param value
	 *            the specified value
	 * @return true if is prime, otherwise, false
	 */
	public static final boolean isPrime(int value)
	{
		return PrimeSet.getInstance().isPrime(value);
	}

	// Statistics algorithms.
	/**
	 * Calculate the value of the angle.
	 * 
	 * @param top
	 *            The top of the angle.
	 * @param corner1
	 *            The left corner point of the angle.
	 * @param corner2
	 *            The right corner point of the angle.
	 * @return The angle in degree.
	 */
	public static final boolean isUpon(Point2D head, Point2D end, Point2D p)
	{
		return p.getY() >= new StraightLine(head, end).y(p.getX());
	}

	/**
	 * Calculate the linear regression equation of the line according to the
	 * point set. <br>
	 * 
	 * <pre>
	 * x' = &sum;X/n
	 * y' = &sum;Y/n
	 *  A = &sum;XY - nx'y'
	 *  B = n(x')<sup>2</sup> - &sum;X<sup>2</sup>
	 *  C = [&sum;x<sup>2</sup> - n(x')<sup>2</sup>] * y' - (&sum;XY - nx'y') * x'
	 * &nbsp;&nbsp;&nbsp;= -B * y' - A * x'
	 * </pre>
	 * 
	 * @param c
	 *            the point set
	 * @return the regressed straight line
	 */
	public static final StraightLine linearRegression(Collection<? extends Point2D> c)
	{
		boolean flag = true;// the flag for the unique x values.
		double t = Double.NaN;
		double sumX = 0.0;
		double sumY = 0.0;
		double argA = 0.0;
		double argB = 0.0;
		double argC = 0.0;
		double avgX = 0.0;
		double avgY = 0.0;
		int n = c.size();
		for (Point2D p : c)
		{
			double x = p.getX();
			if (flag && !Double.isNaN(t))
				flag = t == x;
			t = x;
			double y = p.getY();
			sumX += x;
			sumY += y;
			argA += x * y;
			argB -= x * x;
		}
		if (flag)
			return new StraightLine(1.0, 0.0, -t);
		avgX = sumX / n;
		avgY = sumY / n;
		argA -= sumX * avgY;
		argB += sumX * avgX;
		argC = -argB * avgY - argA * avgX;
		return new StraightLine(argA, argB, argC);
	}

	/**
	 * Filter the points in the linear regression. It will delete the last
	 * <tt>rate</tt> far away points from the center point in the set.
	 * 
	 * @param c
	 *            The point set.
	 * @param rate
	 *            The delete rate. (0.0&lt;r&lt;0.9)
	 * @return The filtered points.
	 */
	public static Vector<Point2D> linearRegressionCenterFilter(Collection<? extends Point2D> c, double rate)
	{
		if (rate > 0.9)
			rate = 0.9;
		else if (rate < 0.0)
			rate = 0.0;
		final Point2D center = MathUtils.center(c);
		int initialCapacity = c.size() - (int) (c.size() * rate);
		Comparator<Point2D> comparator = new Comparator<Point2D>()
		{
			@Override
			public int compare(Point2D o1, Point2D o2)
			{
				return (int) Math.signum(center.distance(o1) - center.distance(o2));
			}
		};
		PriorityQueue<Point2D> pq = new PriorityQueue<Point2D>(initialCapacity, comparator);
		for (Point2D e : c)
			pq.add(e);
		Vector<Point2D> v = new Vector<Point2D>(initialCapacity);
		for (int i = 0; i < initialCapacity; i++)
			v.add(pq.poll());
		return v;
	}

	/**
	 * Filter the points in the linear regression. It will delete the last
	 * <tt>rate</tt> far away points from the regression line in the set. The
	 * regression line will be calculated automatically.
	 * 
	 * @param c
	 *            The point set.
	 * @param rate
	 *            The delete rate. (0.0&lt;r&lt;0.9)
	 * @return The filtered points.
	 */
	public static Vector<Point2D> linearRegressionLinearFilter(Collection<? extends Point2D> c, double rate)
	{
		if (rate > 0.9)
			rate = 0.9;
		else if (rate < 0.0)
			rate = 0.0;
		int initialCapacity = c.size() - (int) (c.size() * rate);
		final StraightLine l = linearRegression(c);
		Comparator<Point2D> comparator = new Comparator<Point2D>()
		{
			@Override
			public int compare(Point2D o1, Point2D o2)
			{
				return (int) Math.signum(MathUtils.distance(o1, l) - MathUtils.distance(o2, l));
			}
		};
		PriorityQueue<Point2D> pq = new PriorityQueue<Point2D>(initialCapacity, comparator);
		for (Point2D e : c)
			pq.add(e);
		Vector<Point2D> v = new Vector<Point2D>(initialCapacity);
		for (int i = 0; i < initialCapacity; i++)
			v.add(pq.poll());
		return v;
	}

	/**
	 * Filter the points in the linear regression. It will delete the last
	 * <tt>rate</tt> far away points from the regression line in the set.
	 * 
	 * @param l
	 *            The regression line.
	 * @param c
	 *            The point set.
	 * @param rate
	 *            The delete rate. (0.0&lt;r&lt;0.9)
	 * @return The filtered points.
	 */
	public static Vector<Point2D> linearRegressionLinearFilter(final StraightLine l, Collection<? extends Point2D> c,
			double rate)
	{
		if (rate > 0.9)
			rate = 0.9;
		else if (rate < 0.0)
			rate = 0.0;
		int initialCapacity = c.size() - (int) (c.size() * rate);
		Comparator<Point2D> comparator = new Comparator<Point2D>()
		{
			@Override
			public int compare(Point2D o1, Point2D o2)
			{
				return (int) Math.signum(MathUtils.distance(o1, l) - MathUtils.distance(o2, l));
			}
		};
		PriorityQueue<Point2D> pq = new PriorityQueue<Point2D>(initialCapacity, comparator);
		for (Point2D e : c)
			pq.add(e);
		Vector<Point2D> v = new Vector<Point2D>(initialCapacity);
		for (int i = 0; i < initialCapacity; i++)
			v.add(pq.poll());
		return v;
	}

	/**
	 * Returns the relation for the specified point set <code>c</code>.
	 * 
	 * <pre>
	 * x' = &sum;x/n
	 * y' = &sum;y/n
	 * r = <sup>&sum;(x-x')(y-y')</sup>/<sub>&radic;(&sum;((x-x')<sup>2</sup>)&radic;&sum;((y-y')<sup>2</sup>)</sub>
	 * </pre>
	 * 
	 * @param c
	 *            the point set
	 * @return the relation in [-1, 1]
	 */
	public static final double linearRegressionRelation(Collection<? extends Point2D> c)
	{
		double x, y;
		double sumX = 0.0;
		double sumY = 0.0;
		double avgX = 0.0;
		double avgY = 0.0;
		int n = c.size();
		for (Point2D p : c)
		{
			x = p.getX();
			y = p.getY();
			sumX += x;
			sumY += y;
		}
		avgX = sumX / n;
		avgY = sumY / n;
		double head = sumX = sumY = 0.0;
		for (Point2D p : c)
		{
			x = p.getX() - avgX;
			y = p.getY() - avgY;
			head += x * y;
			sumX += Math.pow(x, 2);
			sumY += Math.pow(y, 2);
		}
		return head / Math.sqrt(sumX * sumY);
	}

	/**
	 * Find the index of the maximum value of the number <tt>byte</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final byte maximum(byte[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		byte max = a[0];
		for (byte i : a)
			if (i > max)
				max = i;
		return max;
	}

	/**
	 * Find the maximum value of the number collection.
	 * 
	 * @param c
	 *            The number collection.
	 * @return The maximum value.
	 */
	public static final double maximum(Collection<? extends Number> c)
	{
		if (c.isEmpty())
			throw new NullPointerException(Messages.getString("MathUtils.EmptyCollection")); //$NON-NLS-1$
		Iterator<? extends Number> it = c.iterator();
		Number value = it.next();
		Number temp = null;
		while (it.hasNext())
		{
			temp = it.next();
			if (value.doubleValue() < temp.doubleValue())
				value = temp;
		}
		return value.doubleValue();
	}

	/**
	 * Returns the index of the maximum value in the specified array.
	 * 
	 * @param data
	 *            The specified array.
	 * @return -1 if the data is null or empty, otherwise returns the index of
	 *         the maximum value.
	 */
	public static final int maximum(Comparable[] data)
	{
		if (data == null || data.length == 0)
			return -1;
		else if (data.length == 1)
			return 0;
		else
		{
			int index = 0;
			for (int i = 1; i < data.length; i++)
				if (data[i].compareTo(data[index]) > 0)
					index = i;
			return index;
		}
	}

	/**
	 * Find the index of the maximum value of the number <tt>double</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final double maximum(double[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		double max = a[0];
		for (double i : a)
			if (i > max)
				max = i;
		return max;
	}

	/**
	 * Find the index of the maximum value of the number <tt>float</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final float maximum(float[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		float max = a[0];
		for (float i : a)
			if (i > max)
				max = i;
		return max;
	}

	/**
	 * Find the index of the maximum value of the number <tt>int</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final int maximum(int[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		int max = a[0];
		for (int i : a)
			if (i > max)
				max = i;
		return max;
	}

	/**
	 * Returns the index of the maximum value in the specified list.
	 * 
	 * @param data
	 *            The specified list.
	 * @return -1 if the data is null or empty, otherwise returns the index of
	 *         the maximum value.
	 */
	public static final int maximum(List<Comparable> data)
	{
		if (data == null || data.isEmpty())
			return -1;
		else if (data.size() == 1)
			return 0;
		else
		{
			int index = 0;
			for (int i = 1; i < data.size(); i++)
				if (data.get(i).compareTo(data.get(index)) > 0)
					index = i;
			return index;
		}
	}

	/**
	 * Find the index of the maximum value of the number <tt>long</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final long maximum(long[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		long max = a[0];
		for (long i : a)
			if (i > max)
				max = i;
		return max;
	}

	/**
	 * Find the index of the maximum value of the number <tt>short</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final short maximum(short[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		short max = a[0];
		for (short i : a)
			if (i > max)
				max = i;
		return max;
	}

	/**
	 * Find the index of the maximum value of the number <tt>byte</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final int maximumIndex(byte[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		int maxIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] > a[maxIdx])
				maxIdx = i;
		return maxIdx;
	}

	/**
	 * Find the index of the maximum value of the number <tt>double</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final int maximumIndex(double[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		int maxIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] > a[maxIdx])
				maxIdx = i;
		return maxIdx;
	}

	/**
	 * Find the index of the maximum value of the number <tt>float</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final int maximumIndex(float[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		int maxIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] > a[maxIdx])
				maxIdx = i;
		return maxIdx;
	}

	/**
	 * Find the index of the maximum value of the number <tt>int</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final int maximumIndex(int[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.20")); //$NON-NLS-1$
		int maxIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] > a[maxIdx])
				maxIdx = i;
		return maxIdx;
	}

	/**
	 * Find the index of the maximum value of the number <tt>long</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final int maximumIndex(long[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.21")); //$NON-NLS-1$
		int maxIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] > a[maxIdx])
				maxIdx = i;
		return maxIdx;
	}

	/**
	 * Find the index of the maximum value of the number <tt>short</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The maximum value.
	 */
	public static final int maximumIndex(short[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.22")); //$NON-NLS-1$
		int maxIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] > a[maxIdx])
				maxIdx = i;
		return maxIdx;
	}

	/**
	 * Find the index of the minimum value of the number <tt>byte</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final byte minimum(byte[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.23")); //$NON-NLS-1$
		byte min = a[0];
		for (byte i : a)
			if (i < min)
				min = i;
		return min;
	}

	/**
	 * Find the minimum value of the number collection.
	 * 
	 * @param c
	 *            The number collection.
	 * @return The minimum value.
	 */
	public static final double minimum(Collection<? extends Number> c)
	{
		if (c.isEmpty())
			throw new NullPointerException(Messages.getString("MathUtils.EmptyCollection")); //$NON-NLS-1$
		Iterator<? extends Number> it = c.iterator();
		Number value = it.next();
		Number temp = null;
		while (it.hasNext())
		{
			temp = it.next();
			if (value.doubleValue() > temp.doubleValue())
				value = temp;
		}
		return value.doubleValue();
	}

	/**
	 * Returns the index of the minimum value in the specified array.
	 * 
	 * @param data
	 *            The specified array.
	 * @return -1 if the data is null or empty, otherwise returns the index of
	 *         the minimum value.
	 */
	public static final int minimum(Comparable[] data)
	{
		if (data == null || data.length == 0)
			return -1;
		else if (data.length == 1)
			return 0;
		else
		{
			int index = 0;
			for (int i = 1; i < data.length; i++)
				if (data[i].compareTo(data[index]) < 0)
					index = i;
			return index;
		}
	}

	/**
	 * Find the index of the minimum value of the number <tt>double</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final double minimum(double[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.25")); //$NON-NLS-1$
		double min = a[0];
		for (double i : a)
			if (i < min)
				min = i;
		return min;
	}

	/**
	 * Find the index of the minimum value of the number <tt>float</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final float minimum(float[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.26")); //$NON-NLS-1$
		float min = a[0];
		for (float i : a)
			if (i < min)
				min = i;
		return min;
	}

	/**
	 * Find the index of the minimum value of the number <tt>int</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final int minimum(int[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.27")); //$NON-NLS-1$
		int min = a[0];
		for (int i : a)
			if (i < min)
				min = i;
		return min;
	}

	/**
	 * Returns the index of the minimum value in the specified list.
	 * 
	 * @param data
	 *            The specified list.
	 * @return -1 if the data is null or empty, otherwise returns the index of
	 *         the minimum value.
	 */
	public static final int minimum(List<Comparable> data)
	{
		if (data == null || data.isEmpty())
			return -1;
		else if (data.size() == 1)
			return 0;
		else
		{
			int index = 0;
			for (int i = 1; i < data.size(); i++)
				if (data.get(i).compareTo(data.get(index)) < 0)
					index = i;
			return index;
		}
	}

	/**
	 * Find the index of the minimum value of the number <tt>long</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final long minimum(long[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.28")); //$NON-NLS-1$
		long min = a[0];
		for (long i : a)
			if (i < min)
				min = i;
		return min;
	}

	/**
	 * Find the index of the minimum value of the number <tt>short</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final short minimum(short[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.29")); //$NON-NLS-1$
		short min = a[0];
		for (short i : a)
			if (i < min)
				min = i;
		return min;
	}

	/**
	 * Find the index of the minimum value of the number <tt>byte</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final int minimumIndex(byte[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		int minIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] < a[minIdx])
				minIdx = i;
		return minIdx;
	}

	/**
	 * Find the index of the minimum value of the number <tt>double</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final int minimumIndex(double[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		int minIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] < a[minIdx])
				minIdx = i;
		return minIdx;
	}

	/**
	 * Find the index of the minimum value of the number <tt>float</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final int minimumIndex(float[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		int minIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] < a[minIdx])
				minIdx = i;
		return minIdx;
	}

	/**
	 * Find the index of the minimum value of the number <tt>int</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final int minimumIndex(int[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		int minIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] < a[minIdx])
				minIdx = i;
		return minIdx;
	}

	/**
	 * Find the index of the minimum value of the number <tt>long</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final int minimumIndex(long[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		int minIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] < a[minIdx])
				minIdx = i;
		return minIdx;
	}

	/**
	 * Find the index of the minimum value of the number <tt>short</tt>array.
	 * 
	 * @param a
	 *            The number array.
	 * @return The minimum value.
	 */
	public static final int minimumIndex(short[] a)
	{
		if (a.length < 1)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyArray")); //$NON-NLS-1$
		int minIdx = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] < a[minIdx])
				minIdx = i;
		return minIdx;
	}

	/**
	 * Normalize the original <tt>byte</tt> data to specified range.
	 * 
	 * @param data
	 *            the original data
	 * @param maximum
	 *            the maximum of the specified range
	 * @param minimum
	 *            the minimum of the specified range
	 */
	public static void normalize(byte[] data, byte maximum, byte minimum)
	{
		if (data.length == 0)
			return;
		byte max = data[0];
		byte min = data[0];
		for (byte d : data)
		{
			if (d > max)
				max = d;
			if (d < min)
				min = d;
		}
		byte len = (byte) (max - min);
		if (max == min)
		{
			for (int i = 0; i < data.length; i++)
				data[i] = (byte) ((maximum - minimum) / 2.0);
			return;
		}
		byte length = (byte) (maximum - minimum);
		for (int i = 0; i < data.length; i++)
			data[i] = (byte) (minimum + (data[i] - min) / (double) len * length);
	}

	/**
	 * Normalize the original <tt>double</tt> data to specified range.
	 * 
	 * @param data
	 *            the original data
	 * @param maximum
	 *            the maximum of the specified range
	 * @param minimum
	 *            the minimum of the specified range
	 */
	public static void normalize(double[] data, double maximum, double minimum)
	{
		if (data.length == 0)
			return;
		double max = data[0];
		double min = data[0];
		for (double d : data)
		{
			if (d > max)
				max = d;
			if (d < min)
				min = d;
		}
		double len = (double) (max - min);
		if (max == min)
		{
			for (int i = 0; i < data.length; i++)
				data[i] = (double) ((maximum - minimum) / 2.0);
			return;
		}
		double length = (double) (maximum - minimum);
		for (int i = 0; i < data.length; i++)
			data[i] = (double) (minimum + (data[i] - min) / (double) len * length);
	}

	/**
	 * Normalize the original <tt>float</tt> data to specified range.
	 * 
	 * @param data
	 *            the original data
	 * @param maximum
	 *            the maximum of the specified range
	 * @param minimum
	 *            the minimum of the specified range
	 */
	public static void normalize(float[] data, float maximum, float minimum)
	{
		if (data.length == 0)
			return;
		float max = data[0];
		float min = data[0];
		for (float d : data)
		{
			if (d > max)
				max = d;
			if (d < min)
				min = d;
		}
		float len = (float) (max - min);
		if (max == min)
		{
			for (int i = 0; i < data.length; i++)
				data[i] = (float) ((maximum - minimum) / 2.0);
			return;
		}
		float length = (float) (maximum - minimum);
		for (int i = 0; i < data.length; i++)
			data[i] = (float) (minimum + (data[i] - min) / (double) len * length);
	}

	/**
	 * Normalize the original <tt>int</tt> data to specified range.
	 * 
	 * @param data
	 *            the original data
	 * @param maximum
	 *            the maximum of the specified range
	 * @param minimum
	 *            the minimum of the specified range
	 */
	public static void normalize(int[] data, int maximum, int minimum)
	{
		if (data.length == 0)
			return;
		int max = data[0];
		int min = data[0];
		for (int d : data)
		{
			if (d > max)
				max = d;
			if (d < min)
				min = d;
		}
		int len = (int) (max - min);
		if (max == min)
		{
			for (int i = 0; i < data.length; i++)
				data[i] = (int) ((maximum - minimum) / 2.0);
			return;
		}
		int length = (int) (maximum - minimum);
		for (int i = 0; i < data.length; i++)
			data[i] = (int) (minimum + (data[i] - min) / (double) len * length);
	}

	/**
	 * Normalize the original <tt>long</tt> data to specified range.
	 * 
	 * @param data
	 *            the original data
	 * @param maximum
	 *            the maximum of the specified range
	 * @param minimum
	 *            the minimum of the specified range
	 */
	public static void normalize(long[] data, long maximum, long minimum)
	{
		if (data.length == 0)
			return;
		long max = data[0];
		long min = data[0];
		for (long d : data)
		{
			if (d > max)
				max = d;
			if (d < min)
				min = d;
		}
		long len = (long) (max - min);
		if (max == min)
		{
			for (int i = 0; i < data.length; i++)
				data[i] = (long) ((maximum - minimum) / 2.0);
			return;
		}
		long length = (long) (maximum - minimum);
		for (int i = 0; i < data.length; i++)
			data[i] = (long) (minimum + (data[i] - min) / (double) len * length);
	}

	/**
	 * Normalize the original <tt>short</tt> data to specified range.
	 * 
	 * @param data
	 *            the original data
	 * @param maximum
	 *            the maximum of the specified range
	 * @param minimum
	 *            the minimum of the specified range
	 */
	public static void normalize(short[] data, short maximum, short minimum)
	{
		if (data.length == 0)
			return;
		short max = data[0];
		short min = data[0];
		for (short d : data)
		{
			if (d > max)
				max = d;
			if (d < min)
				min = d;
		}
		short len = (short) (max - min);
		if (max == min)
		{
			for (int i = 0; i < data.length; i++)
				data[i] = (short) ((maximum - minimum) / 2.0);
			return;
		}
		short length = (short) (maximum - minimum);
		for (int i = 0; i < data.length; i++)
			data[i] = (short) (minimum + (data[i] - min) / (double) len * length);
	}

	/**
	 * Calculate the Pearson Correlation Coefficient.
	 * 
	 * @return Pearson Correlation Coefficient
	 */
	public static final double pearson(int[] x, int[] y)
	{
		if (x.length != y.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double[] xt = new double[x.length];
		double[] yt = new double[y.length];
		for (int i = 0; i < xt.length; i++)
		{
			xt[i] = x[i];
			yt[i] = y[i];
		}
		double xa = MathUtils.average(x);
		double ya = MathUtils.average(y);
		for (int i = 0; i < xt.length; i++)
		{
			xt[i] -= xa;
			yt[i] -= ya;
		}
		double p = 0.0;
		for (int i = 0; i < xt.length; i++)
			p += xt[i] * yt[i];
		double a = 0.0, b = 0.0;
		for (int i = 0; i < xt.length; i++)
		{
			a += xt[i] * xt[i];
			b += yt[i] * yt[i];
		}
		return p / Math.sqrt(a) / Math.sqrt(b);
	}

	/**
	 * Calculate the Pearson Correlation Coefficient.
	 * 
	 * @return Pearson Correlation Coefficient
	 */
	public static final double pearson(long[] x, long[] y)
	{
		if (x.length != y.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double[] xt = new double[x.length];
		double[] yt = new double[y.length];
		for (int i = 0; i < xt.length; i++)
		{
			xt[i] = x[i];
			yt[i] = y[i];
		}
		double xa = MathUtils.average(x);
		double ya = MathUtils.average(y);
		for (int i = 0; i < xt.length; i++)
		{
			xt[i] -= xa;
			yt[i] -= ya;
		}
		double p = 0.0;
		for (int i = 0; i < xt.length; i++)
			p += xt[i] * yt[i];
		double a = 0.0, b = 0.0;
		for (int i = 0; i < xt.length; i++)
		{
			a += xt[i] * xt[i];
			b += yt[i] * yt[i];
		}
		return p / Math.sqrt(a) / Math.sqrt(b);
	}

	/**
	 * Calculate the Pearson Correlation Coefficient.
	 * 
	 * @return Pearson Correlation Coefficient
	 */
	public static final double pearson(float[] x, float[] y)
	{
		if (x.length != y.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double[] xt = new double[x.length];
		double[] yt = new double[y.length];
		for (int i = 0; i < xt.length; i++)
		{
			xt[i] = x[i];
			yt[i] = y[i];
		}
		double xa = MathUtils.average(x);
		double ya = MathUtils.average(y);
		for (int i = 0; i < xt.length; i++)
		{
			xt[i] -= xa;
			yt[i] -= ya;
		}
		double p = 0.0;
		for (int i = 0; i < xt.length; i++)
			p += xt[i] * yt[i];
		double a = 0.0, b = 0.0;
		for (int i = 0; i < xt.length; i++)
		{
			a += xt[i] * xt[i];
			b += yt[i] * yt[i];
		}
		return p / Math.sqrt(a) / Math.sqrt(b);
	}

	/**
	 * Calculate the Pearson Correlation Coefficient.
	 * 
	 * @return Pearson Correlation Coefficient
	 */
	public static final double pearson(double[] x, double[] y)
	{
		if (x.length != y.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double[] xt = new double[x.length];
		double[] yt = new double[y.length];
		for (int i = 0; i < xt.length; i++)
		{
			xt[i] = x[i];
			yt[i] = y[i];
		}
		double xa = MathUtils.average(x);
		double ya = MathUtils.average(y);
		for (int i = 0; i < xt.length; i++)
		{
			xt[i] -= xa;
			yt[i] -= ya;
		}
		double p = 0.0;
		for (int i = 0; i < xt.length; i++)
			p += xt[i] * yt[i];
		double a = 0.0, b = 0.0;
		for (int i = 0; i < xt.length; i++)
		{
			a += xt[i] * xt[i];
			b += yt[i] * yt[i];
		}
		return p / Math.sqrt(a) / Math.sqrt(b);
	}

	/**
	 * Calculate the Pearson Correlation Coefficient.
	 * 
	 * @return Pearson Correlation Coefficient
	 */
	public static final <T extends Number> double pearson(T[] x, T[] y)
	{
		if (x.length != y.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double[] xt = new double[x.length];
		double[] yt = new double[y.length];
		for (int i = 0; i < xt.length; i++)
		{
			xt[i] = x[i].doubleValue();
			yt[i] = y[i].doubleValue();
		}
		double xa = MathUtils.average(xt);
		double ya = MathUtils.average(yt);
		for (int i = 0; i < xt.length; i++)
		{
			xt[i] -= xa;
			yt[i] -= ya;
		}
		double p = 0.0;
		for (int i = 0; i < xt.length; i++)
			p += xt[i] * yt[i];
		double a = 0.0, b = 0.0;
		for (int i = 0; i < xt.length; i++)
		{
			a += xt[i] * xt[i];
			b += yt[i] * yt[i];
		}
		return p / Math.sqrt(a) / Math.sqrt(b);
	}

	/**
	 * Returns the amount of permutations when base size of elements be permuted
	 * to value size elements.
	 * 
	 * @param base
	 * @param value
	 * @return the amount of permutations
	 */
	public static final long permute(int base, int value)
	{
		if (value > base || base < 0 || value < 0)
			throw new IllegalArgumentException(String.format(Messages.getString("MathUtils.IllegalBaseAndValue"), base, //$NON-NLS-1$
					value));
		int n = base - value;
		int t = 1;
		for (int i = n + 1; i <= base; i++)
			t *= i;
		return t;
	}

	/**
	 * Calculate the radius of triangle inscribed circle. <br>
	 * Formula: r = &radic;((p-a)(p-b)(p-c)/p)
	 * 
	 * @param a
	 *            The point of the triangle.
	 * @param b
	 *            The point of the triangle.
	 * @param c
	 *            The point of the triangle.
	 * @return The radius of triangle inscribed circle.
	 */
	static public final double radius(Point2D a, Point2D b, Point2D c)
	{
		double ab = MathUtils.distance(a, b);
		double bc = MathUtils.distance(b, c);
		double ca = MathUtils.distance(c, a);
		double p = (ab + bc + ca) / 2.0;
		return Math.sqrt((p - ab) * (p - bc) * (p - ca) / p);
	}

	/**
	 * Returns the least square error of the data in specified <tt>byte</tt>
	 * vectors.
	 * 
	 * @param evaluated
	 *            The evaluated value vector.
	 * @param actual
	 *            The actual value vector.
	 * @return The least square error.
	 */
	public static final double relativeError(byte[] evaluated, byte[] actual)
	{
		if (evaluated.length != actual.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double re = 0.0;
		for (int i = 0; i < evaluated.length; i++)
			re += Math.pow(evaluated[i] - actual[i], 2.0);
		return re / evaluated.length;
	}

	/**
	 * Returns the least square error of the data in specified <tt>double</tt>
	 * vectors.
	 * 
	 * @param evaluated
	 *            The evaluated value vector.
	 * @param actual
	 *            The actual value vector.
	 * @return The least square error.
	 */
	public static final double relativeError(double[] evaluated, double[] actual)
	{
		if (evaluated.length != actual.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double re = 0.0;
		for (int i = 0; i < evaluated.length; i++)
			re += Math.pow(evaluated[i] - actual[i], 2.0);
		return re / evaluated.length;
	}

	/**
	 * Returns the least square error of the data in specified <tt>float</tt>
	 * vectors.
	 * 
	 * @param evaluated
	 *            The evaluated value vector.
	 * @param actual
	 *            The actual value vector.
	 * @return The least square error.
	 */
	public static final double relativeError(float[] evaluated, float[] actual)
	{
		if (evaluated.length != actual.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double re = 0.0;
		for (int i = 0; i < evaluated.length; i++)
			re += Math.pow(evaluated[i] - actual[i], 2.0);
		return re / evaluated.length;
	}

	/**
	 * Returns the least square error of the data in specified <tt>int</tt>
	 * vectors.
	 * 
	 * @param evaluated
	 *            The evaluated value vector.
	 * @param actual
	 *            The actual value vector.
	 * @return The least square error.
	 */
	public static final double relativeError(int[] evaluated, int[] actual)
	{
		if (evaluated.length != actual.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double re = 0.0;
		for (int i = 0; i < evaluated.length; i++)
			re += Math.pow(evaluated[i] - actual[i], 2.0);
		return re / evaluated.length;
	}

	/**
	 * Returns the least square error of the data in specified <tt>long</tt>
	 * vectors.
	 * 
	 * @param evaluated
	 *            The evaluated value vector.
	 * @param actual
	 *            The actual value vector.
	 * @return The least square error.
	 */
	public static final double relativeError(long[] evaluated, long[] actual)
	{
		if (evaluated.length != actual.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double re = 0.0;
		for (int i = 0; i < evaluated.length; i++)
			re += Math.pow(evaluated[i] - actual[i], 2.0);
		return re / evaluated.length;
	}

	/**
	 * Calculate the relative error of the evaluated value.
	 * 
	 * @param evaluated
	 *            The evaluated value.
	 * @param actual
	 *            The actual value.
	 * @return The relative error.
	 */
	public static final double relativeError(Number evaluated, Number actual)
	{
		double e = evaluated.doubleValue();
		double a = actual.doubleValue();
		return Math.abs((e - a) / e);
	}

	/**
	 * Returns the least square error of the data in specified <tt>short</tt>
	 * vectors.
	 * 
	 * @param evaluated
	 *            The evaluated value vector.
	 * @param actual
	 *            The actual value vector.
	 * @return The least square error.
	 */
	public static final double relativeError(short[] evaluated, short[] actual)
	{
		if (evaluated.length != actual.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double re = 0.0;
		for (int i = 0; i < evaluated.length; i++)
			re += Math.pow(evaluated[i] - actual[i], 2.0);
		return re / evaluated.length;
	}

	/**
	 * Trough searching algorithm.
	 * <p>
	 * With the specified threshold, we conduct the point where the value
	 * changes from the previous point to the current point and current point to
	 * the next point are both large than the threshold as the trough of the
	 * data array.
	 * </p>
	 * <p>
	 * This algorithm will returns the array which consists of trough found and
	 * the beginning point and size (the point after end) in order [start,
	 * trough1, trough2, [] , size].
	 * </p>
	 * 
	 * @param data
	 *            the original data array.
	 * @param threshold
	 *            the trough judging threshold
	 * @return trough
	 */
	public static Vector<Integer> searchTrough(byte[] data, double threshold)
	{
		if (data.length < 5)
			throw new IllegalArgumentException(Messages.getString("MathUtils.DataSizeTooSmall")); //$NON-NLS-1$
		// confirm the starting direction
		Vector<Integer> v = new Vector<Integer>();
		double curr = 0;// the current value
		for (int i = 0; i < 5; i++)
			curr += data[i];
		curr /= 5;
		v.add(0);
		int dir = curr >= data[0] ? 1 : -1;// true if the wave is going upward
		for (int i = 0; i < data.length - 1; i++)
			if ((data[i + 1] - data[i]) * dir > 0)
			{
				dir *= -1;
				v.add(i);
			}
		v.add(data.length - 1);
		Vector<Integer> troughs = new Vector<Integer>();
		byte prev, next;
		troughs.add(0);
		for (int i = 1; i < v.size() - 1; i++)
		{
			prev = data[v.get(i - 1)];
			curr = data[v.get(i)];
			next = data[v.get(i + 1)];
			if (prev - curr >= threshold && next - curr >= threshold)
				troughs.add(v.get(i));
		}
		troughs.add(data.length - 1);
		return troughs;
	}

	/**
	 * Trough searching algorithm.
	 * <p>
	 * With the specified threshold, we conduct the point where the value
	 * changes from the previous point to the current point and current point to
	 * the next point are both large than the threshold as the trough of the
	 * data array.
	 * </p>
	 * <p>
	 * This algorithm will returns the array which consists of trough found and
	 * the beginning point and size (the point after end) in order [start,
	 * trough1, trough2, [] , size].
	 * </p>
	 * 
	 * @param data
	 *            the original data array.
	 * @param threshold
	 *            the trough judging threshold
	 * @return trough
	 */
	public static Vector<Integer> searchTrough(double[] data, double threshold)
	{
		if (data.length < 5)
			throw new IllegalArgumentException(Messages.getString("MathUtils.DataSizeTooSmall")); //$NON-NLS-1$
		// confirm the starting direction
		Vector<Integer> v = new Vector<Integer>();
		double curr = 0;// the current value
		for (int i = 0; i < 5; i++)
			curr += data[i];
		curr /= 5;
		v.add(0);
		int dir = curr >= data[0] ? 1 : -1;// true if the wave is going upward
		for (int i = 0; i < data.length - 1; i++)
			if ((data[i + 1] - data[i]) * dir > 0)
			{
				dir *= -1;
				v.add(i);
			}
		v.add(data.length - 1);
		Vector<Integer> troughs = new Vector<Integer>();
		double prev, next;
		troughs.add(0);
		for (int i = 1; i < v.size() - 1; i++)
		{
			prev = data[v.get(i - 1)];
			curr = data[v.get(i)];
			next = data[v.get(i + 1)];
			if (prev - curr >= threshold && next - curr >= threshold)
				troughs.add(v.get(i));
		}
		troughs.add(data.length - 1);
		return troughs;
	}

	/**
	 * Trough searching algorithm.
	 * <p>
	 * With the specified threshold, we conduct the point where the value
	 * changes from the previous point to the current point and current point to
	 * the next point are both large than the threshold as the trough of the
	 * data array.
	 * </p>
	 * <p>
	 * This algorithm will returns the array which consists of trough found and
	 * the beginning point and size (the point after end) in order [start,
	 * trough1, trough2, [] , size].
	 * </p>
	 * 
	 * @param data
	 *            the original data array.
	 * @param threshold
	 *            the trough judging threshold
	 * @return trough
	 */
	public static Vector<Integer> searchTrough(float[] data, double threshold)
	{
		if (data.length < 5)
			throw new IllegalArgumentException(Messages.getString("MathUtils.DataSizeTooSmall")); //$NON-NLS-1$
		// confirm the starting direction
		Vector<Integer> v = new Vector<Integer>();
		double curr = 0;// the current value
		for (int i = 0; i < 5; i++)
			curr += data[i];
		curr /= 5;
		v.add(0);
		int dir = curr >= data[0] ? 1 : -1;// true if the wave is going upward
		for (int i = 0; i < data.length - 1; i++)
			if ((data[i + 1] - data[i]) * dir > 0)
			{
				dir *= -1;
				v.add(i);
			}
		v.add(data.length - 1);
		Vector<Integer> troughs = new Vector<Integer>();
		float prev, next;
		troughs.add(0);
		for (int i = 1; i < v.size() - 1; i++)
		{
			prev = data[v.get(i - 1)];
			curr = data[v.get(i)];
			next = data[v.get(i + 1)];
			if (prev - curr >= threshold && next - curr >= threshold)
				troughs.add(v.get(i));
		}
		troughs.add(data.length - 1);
		return troughs;
	}

	/**
	 * Trough searching algorithm.
	 * <p>
	 * With the specified threshold, we conduct the point where the value
	 * changes from the previous point to the current point and current point to
	 * the next point are both large than the threshold as the trough of the
	 * data array.
	 * </p>
	 * <p>
	 * This algorithm will returns the array which consists of trough found and
	 * the beginning point and size (the point after end) in order [start,
	 * trough1, trough2, [] , size].
	 * </p>
	 * 
	 * @param data
	 *            the original data array.
	 * @param threshold
	 *            the trough judging threshold
	 * @return trough
	 */
	public static Vector<Integer> searchTrough(int[] data, double threshold)
	{
		if (data.length < 5)
			throw new IllegalArgumentException(Messages.getString("MathUtils.DataSizeTooSmall")); //$NON-NLS-1$
		// confirm the starting direction
		Vector<Integer> v = new Vector<Integer>();
		double curr = 0;// the current value
		for (int i = 0; i < 5; i++)
			curr += data[i];
		curr /= 5;
		v.add(0);
		int dir = curr >= data[0] ? 1 : -1;// true if the wave is going upward
		for (int i = 0; i < data.length - 1; i++)
			if ((data[i + 1] - data[i]) * dir > 0)
			{
				dir *= -1;
				v.add(i);
			}
		v.add(data.length - 1);
		Vector<Integer> troughs = new Vector<Integer>();
		int prev, next;
		troughs.add(0);
		for (int i = 1; i < v.size() - 1; i++)
		{
			prev = data[v.get(i - 1)];
			curr = data[v.get(i)];
			next = data[v.get(i + 1)];
			if (prev - curr >= threshold && next - curr >= threshold)
				troughs.add(v.get(i));
		}
		troughs.add(data.length - 1);
		return troughs;
	}

	/**
	 * Trough searching algorithm.
	 * <p>
	 * With the specified threshold, we conduct the point where the value
	 * changes from the previous point to the current point and current point to
	 * the next point are both large than the threshold as the trough of the
	 * data array.
	 * </p>
	 * <p>
	 * This algorithm will returns the array which consists of trough found and
	 * the beginning point and size (the point after end) in order [start,
	 * trough1, trough2, [] , size].
	 * </p>
	 * 
	 * @param data
	 *            the original data array.
	 * @param threshold
	 *            the trough judging threshold
	 * @return trough
	 */
	public static Vector<Integer> searchTrough(long[] data, double threshold)
	{
		if (data.length < 5)
			throw new IllegalArgumentException(Messages.getString("MathUtils.DataSizeTooSmall")); //$NON-NLS-1$
		// confirm the starting direction
		Vector<Integer> v = new Vector<Integer>();
		double curr = 0;// the current value
		for (int i = 0; i < 5; i++)
			curr += data[i];
		curr /= 5;
		v.add(0);
		int dir = curr >= data[0] ? 1 : -1;// true if the wave is going upward
		for (int i = 0; i < data.length - 1; i++)
			if ((data[i + 1] - data[i]) * dir > 0)
			{
				dir *= -1;
				v.add(i);
			}
		v.add(data.length - 1);
		Vector<Integer> troughs = new Vector<Integer>();
		long prev, next;
		troughs.add(0);
		for (int i = 1; i < v.size() - 1; i++)
		{
			prev = data[v.get(i - 1)];
			curr = data[v.get(i)];
			next = data[v.get(i + 1)];
			if (prev - curr >= threshold && next - curr >= threshold)
				troughs.add(v.get(i));
		}
		troughs.add(data.length - 1);
		return troughs;
	}

	/**
	 * Trough searching algorithm.
	 * <p>
	 * With the specified threshold, we conduct the point where the value
	 * changes from the previous point to the current point and current point to
	 * the next point are both large than the threshold as the trough of the
	 * data array.
	 * </p>
	 * <p>
	 * This algorithm will returns the array which consists of trough found and
	 * the beginning point and size (the point after end) in order [start,
	 * trough1, trough2, [] , size].
	 * </p>
	 * 
	 * @param data
	 *            the original data array.
	 * @param threshold
	 *            the trough judging threshold
	 * @return trough
	 */
	public static Vector<Integer> searchTrough(short[] data, double threshold)
	{
		if (data.length < 5)
			throw new IllegalArgumentException(Messages.getString("MathUtils.DataSizeTooSmall")); //$NON-NLS-1$
		// confirm the starting direction
		Vector<Integer> v = new Vector<Integer>();
		double curr = 0;// the current value
		for (int i = 0; i < 5; i++)
			curr += data[i];
		curr /= 5;
		v.add(0);
		int dir = curr >= data[0] ? 1 : -1;// true if the wave is going upward
		for (int i = 0; i < data.length - 1; i++)
			if ((data[i + 1] - data[i]) * dir > 0)
			{
				dir *= -1;
				v.add(i);
			}
		v.add(data.length - 1);
		Vector<Integer> troughs = new Vector<Integer>();
		short prev, next;
		troughs.add(0);
		for (int i = 1; i < v.size() - 1; i++)
		{
			prev = data[v.get(i - 1)];
			curr = data[v.get(i)];
			next = data[v.get(i + 1)];
			if (prev - curr >= threshold && next - curr >= threshold)
				troughs.add(v.get(i));
		}
		troughs.add(data.length - 1);
		return troughs;
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>byte</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(byte[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (byte) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (byte) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>short</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(short[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (short) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (short) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>int</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(int[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (int) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (int) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>long</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(long[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (long) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (long) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>float</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(float[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (float) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (float) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>double</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(double[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (double) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (double) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>Byte</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(Byte[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (byte) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (byte) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>Short</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(Short[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (short) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (short) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>Integer</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(Integer[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (int) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (int) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>Long</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(Long[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (long) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (long) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>Float</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(Float[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (float) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (float) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Data smooth algorithm.
	 * <p>
	 * This algorithm smooth the specified <tt>Double</tt> data array with the
	 * replace the current value to the average of the values in the size
	 * specified window.
	 * </p>
	 * <p>
	 * This algorithm is speeded up by integral technology. To notify that, this
	 * algorithm can only provide approximate results for discrete data, can not
	 * be used as exact solution.
	 * </p>
	 * 
	 * @param data
	 *            the specified data
	 * @param wsize
	 *            the size of the window
	 * @return data after smoothing
	 */
	public static void smooth(Double[] data, int wsize)
	{
		if (wsize < 1)
			return;
		int n = wsize / 2;
		double[] d = new double[data.length];
		d[0] = data[0];
		for (int i = 1; i < data.length; i++)
			d[i] = (d[i - 1] + data[i]);
		if (wsize % 2 == 0)
		{
			int begin = n, end = d.length - wsize + n;
			for (int i = begin; i < end; i++)
				data[i] = (double) ((d[i + wsize - n] - d[i - n]) / wsize);
		}
		else
		{
			int begin = n + 1, end = d.length - wsize + n - 1;
			for (int i = begin; i < end; i++)
				data[i] = (double) ((d[i + wsize - n - 1] - d[i - n - 1]) / wsize);
		}
	}

	/**
	 * Returns the squared linear regression relation value r<sup>2</sup> of the
	 * specified point set.
	 * 
	 * @param c
	 *            the point set
	 * @return the squared linear regression relation in [0, 1]
	 * @see MathUtils#linearRegressionRelation(Collection)
	 */
	public static final double squaredLinearRegressionRelation(Collection<? extends Point2D> c)
	{
		return Math.pow(linearRegressionRelation(c), 2.0);
	}

	/**
	 * Calculate the standard deviation of the number collection.
	 * 
	 * @param c
	 *            The number collection.
	 * @return The standard deviation.
	 */
	public static final double standardDeviation(Collection<? extends Number> c)
	{
		return Math.sqrt(MathUtils.variance(c));
	}

	/**
	 * Calculate the standard deviation of the <tt>double</tt> number array.
	 * 
	 * @param c
	 *            The <tt>double</tt> number collection.
	 * @return The standard deviation.
	 */
	public static final double standardDeviation(double[] c)
	{
		return Math.sqrt(MathUtils.variance(c));
	}

	/**
	 * Calculate the standard deviation of the <tt>Double</tt> number array.
	 * 
	 * @param c
	 *            The <tt>Double</tt> number collection.
	 * @return The standard deviation.
	 */
	public static final double standardDeviation(Double[] c)
	{
		return Math.sqrt(MathUtils.variance(c));
	}

	/**
	 * Calculate the standard deviation of the <tt>float</tt> number array.
	 * 
	 * @param c
	 *            The <tt>float</tt> number collection.
	 * @return The standard deviation.
	 */
	public static final double standardDeviation(float[] c)
	{
		return Math.sqrt(MathUtils.variance(c));
	}

	/**
	 * Calculate the standard deviation of the <tt>Float</tt> number array.
	 * 
	 * @param c
	 *            The <tt>Float</tt> number collection.
	 * @return The standard deviation.
	 */
	public static final double standardDeviation(Float[] c)
	{
		return Math.sqrt(MathUtils.variance(c));
	}

	/**
	 * Calculate the standard deviation of the <tt>int</tt> number array.
	 * 
	 * @param c
	 *            The <tt>int</tt> number collection.
	 * @return The standard deviation.
	 */
	public static final double standardDeviation(int[] c)
	{
		return Math.sqrt(MathUtils.variance(c));
	}

	/**
	 * Calculate the standard deviation of the <tt>Integer</tt> number array.
	 * 
	 * @param c
	 *            The <tt>Integer</tt> number collection.
	 * @return The standard deviation.
	 */
	public static final double standardDeviation(Integer[] c)
	{
		return Math.sqrt(MathUtils.variance(c));
	}

	/**
	 * Calculate the standard deviation of the <tt>long</tt> number array.
	 * 
	 * @param c
	 *            The <tt>long</tt> number collection.
	 * @return The standard deviation.
	 */
	public static final double standardDeviation(long[] c)
	{
		return Math.sqrt(MathUtils.variance(c));
	}

	/**
	 * Calculate the standard deviation of the <tt>Long</tt> number array.
	 * 
	 * @param c
	 *            The <tt>Long</tt> number collection.
	 * @return The standard deviation.
	 */
	public static final double standardDeviation(Long[] c)
	{
		return Math.sqrt(MathUtils.variance(c));
	}

	/**
	 * Returns the summary of the specified complex array.
	 * 
	 * @param x
	 *            the specified complex array
	 * @return the summary
	 */
	public static final Complex summary(Complex[] x)
	{
		double real = 0.0;
		double imaginary = 0.0;
		for (Complex c : x)
		{
			real += c.real;
			imaginary += c.imaginary;
		}
		return new Complex(real, imaginary);
	}

	/**
	 * Returns the summary of the specified <tt>double</tt>array.
	 * 
	 * @param x
	 *            the specified <tt>double</tt>array
	 * @return the summary
	 */
	public static final double summary(double[] x)
	{
		double sum = 0.0;
		for (double i : x)
			sum += i;
		return sum;
	}

	/**
	 * Returns the summary of <code>x</code>
	 * 
	 * @param x
	 *            the specified array
	 * @return the summary
	 */
	public static final <T extends Number> double summary(T[] x)
	{
		double sum = 0.0;
		for (T i : x)
			sum += i.doubleValue();
		return sum;
	}

	/**
	 * Returns the summary of the specified <tt>float</tt>array.
	 * 
	 * @param x
	 *            the specified <tt>float</tt>array
	 * @return the summary
	 */
	public static final double summary(float[] x)
	{
		double sum = 0.0;
		for (double i : x)
			sum += i;
		return sum;
	}

	/**
	 * Returns the summary of the specified <tt>int</tt>array.
	 * 
	 * @param x
	 *            the specified <tt>int</tt>array
	 * @return the summary
	 */
	public static final double summary(int[] x)
	{
		double sum = 0.0;
		for (double i : x)
			sum += i;
		return sum;
	}

	/**
	 * Returns the summary of the specified <tt>long</tt>array.
	 * 
	 * @param x
	 *            the specified <tt>long</tt>array
	 * @return the summary
	 */
	public static final double summary(long[] x)
	{
		double sum = 0.0;
		for (double i : x)
			sum += i;
		return sum;
	}

	/**
	 * Calculate the area of the triangle according to its three points.
	 * 
	 * @return The area of the triangle.
	 */
	static final private double triangle_area(double x1, double y1, double x2, double y2, double x3, double y3)
	{
		return Math.abs((x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3)) / 2;
	}

	/**
	 * To calculate the area of the triangle surrounded by edge a, b, c using
	 * Heron-Formula.<br>
	 * p = (a + b + c) / 2<br>
	 * S = sqrt(p * (p - a) * (p - b) * (p - c))
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return The area.
	 */
	public static final double triangleArea(double a, double b, double c)
	{
		double p = (a + b + c) / 2;
		return Math.sqrt(p * (p - a) * (p - b) * (p - c));
	}

	/**
	 * To calculate the area of the triangle determined by point A, B, C.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return The area.
	 */
	public static final double triangleArea(Point2D a, Point2D b, Point2D c)
	{
		return MathUtils.triangle_area(a.getX(), a.getY(), b.getX(), b.getY(), c.getX(), c.getY());
	}

	/**
	 * Calculate the variance of the number collection.
	 * 
	 * @param c
	 *            The number collection.
	 * @return The variance.
	 */
	public static final double variance(Collection<? extends Number> c)
	{
		if (c.isEmpty())
			throw new NullPointerException(Messages.getString("MathUtils.EmptyCollection")); //$NON-NLS-1$
		double average = MathUtils.average(c);
		double summary = 0.0;
		for (Number n : c)
			summary += Math.pow(n.doubleValue() - average, 2.0);
		return summary / c.size();
	}

	/**
	 * Calculate the variance of the <tt>double</tt> number collection.
	 * 
	 * @param c
	 *            The <tt>double</tt>number array.
	 * @return The variance.
	 */
	public static final double variance(double[] c)
	{
		if (c == null || c.length == 0)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyCollection")); //$NON-NLS-1$
		double average = MathUtils.average(c);
		double summary = 0.0;
		for (Number n : c)
			summary += Math.pow(n.doubleValue() - average, 2.0);
		return summary / c.length;
	}

	/**
	 * Calculate the variance of <code>c</code>;
	 * 
	 * @param c
	 * @return the variance
	 */
	public static final <T extends Number> double variance(T[] c)
	{
		if (c == null || c.length == 0)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyCollection")); //$NON-NLS-1$
		double average = MathUtils.average(c);
		double summary = 0.0;
		for (Number n : c)
			summary += Math.pow(n.doubleValue() - average, 2.0);
		return summary / c.length;
	}

	/**
	 * Calculate the variance of the <tt>float</tt> number collection.
	 * 
	 * @param c
	 *            The <tt>float</tt>number array.
	 * @return The variance.
	 */
	public static final double variance(float[] c)
	{
		if (c == null || c.length == 0)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyCollection")); //$NON-NLS-1$
		double average = MathUtils.average(c);
		double summary = 0.0;
		for (Number n : c)
			summary += Math.pow(n.doubleValue() - average, 2.0);
		return summary / c.length;
	}

	/**
	 * Calculate the variance of the <tt>int</tt> number collection.
	 * 
	 * @param c
	 *            The <tt>int</tt>number array.
	 * @return The variance.
	 */
	public static final double variance(int[] c)
	{
		if (c == null || c.length == 0)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyCollection")); //$NON-NLS-1$
		double average = MathUtils.average(c);
		double summary = 0.0;
		for (Number n : c)
			summary += Math.pow(n.doubleValue() - average, 2.0);
		return summary / c.length;
	}

	/**
	 * Calculate the variance of the <tt>long</tt> number collection.
	 * 
	 * @param c
	 *            The <tt>long</tt>number array.
	 * @return The variance.
	 */
	public static final double variance(long[] c)
	{
		if (c == null || c.length == 0)
			throw new NullPointerException(Messages.getString("MathUtils.EmptyCollection")); //$NON-NLS-1$
		double average = MathUtils.average(c);
		double summary = 0.0;
		for (Number n : c)
			summary += Math.pow(n.doubleValue() - average, 2.0);
		return summary / c.length;
	}

	/**
	 * Returns the value of specified decimal index.
	 * <p>
	 * Calculating algorithm is implemented by first-order interpolation.
	 * 
	 * <pre>
	 * f(x) = f(x') + (f(x'+1) - f(x')) * (x - x')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>int</tt> matrix
	 * @param x
	 *            the decimal index
	 * @return the interpolation value
	 */
	public static final double interpolate(int[] data, double x)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1)
			return data[data.length];
		if (x < 0)
			return data[0];
		int dx = (int) x;
		return data[dx] + (data[dx + 1] - (double) data[dx]) * (x - dx);
	}

	/**
	 * Returns the value of specified decimal index.
	 * <p>
	 * Calculating algorithm is implemented by first-order interpolation.
	 * 
	 * <pre>
	 * f(x) = f(x') + (f(x'+1) - f(x')) * (x - x')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>long</tt> array
	 * @param x
	 *            the decimal index
	 * @return the interpolation value
	 */
	public static final double interpolate(long[] data, double x)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1)
			return data[data.length];
		if (x < 0)
			return data[0];
		int dx = (int) x;
		return data[dx] + (data[dx + 1] - (double) data[dx]) * (x - dx);
	}

	/**
	 * Returns the value of specified decimal index.
	 * <p>
	 * Calculating algorithm is implemented by first-order interpolation.
	 * 
	 * <pre>
	 * f(x) = f(x') + (f(x'+1) - f(x')) * (x - x')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>float</tt> array
	 * @param x
	 *            the decimal index
	 * @return the interpolation value
	 */
	public static final double interpolate(float[] data, double x)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1)
			return data[data.length];
		if (x < 0)
			return data[0];
		int dx = (int) x;
		return data[dx] + (data[dx + 1] - (double) data[dx]) * (x - dx);
	}

	/**
	 * Returns the value of specified decimal index.
	 * <p>
	 * Calculating algorithm is implemented by first-order interpolation.
	 * 
	 * <pre>
	 * f(x) = f(x') + (f(x'+1) - f(x')) * (x - x')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>double</tt> array
	 * @param x
	 *            the decimal index
	 * @return the interpolation value
	 */
	public static final double interpolate(double[] data, double x)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1)
			return data[data.length];
		if (x < 0)
			return data[0];
		int dx = (int) x;
		return data[dx] + (data[dx + 1] - (double) data[dx]) * (x - dx);
	}

	/**
	 * Returns the value of specified decimal index.
	 * <p>
	 * Calculating algorithm is implemented by first-order interpolation.
	 * 
	 * <pre>
	 * f(x) = f(x') + (f(x'+1) - f(x')) * (x - x')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>Integer</tt> array
	 * @param x
	 *            the decimal index
	 * @return the interpolation value
	 */
	public static final double interpolate(Integer[] data, double x)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1)
			return data[data.length];
		if (x < 0)
			return data[0];
		int dx = (int) x;
		return data[dx] + (data[dx + 1] - (double) data[dx]) * (x - dx);
	}

	/**
	 * Returns the value of specified decimal index.
	 * <p>
	 * Calculating algorithm is implemented by first-order interpolation.
	 * 
	 * <pre>
	 * f(x) = f(x') + (f(x'+1) - f(x')) * (x - x')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>Long</tt> array
	 * @param x
	 *            the decimal index
	 * @return the interpolation value
	 */
	public static final double interpolate(Long[] data, double x)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1)
			return data[data.length];
		if (x < 0)
			return data[0];
		int dx = (int) x;
		return data[dx] + (data[dx + 1] - (double) data[dx]) * (x - dx);
	}

	/**
	 * Returns the value of specified decimal index.
	 * <p>
	 * Calculating algorithm is implemented by first-order interpolation.
	 * 
	 * <pre>
	 * f(x) = f(x') + (f(x'+1) - f(x')) * (x - x')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>Float</tt> array
	 * @param x
	 *            the decimal index
	 * @return the interpolation value
	 */
	public static final double interpolate(Float[] data, double x)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1)
			return data[data.length];
		if (x < 0)
			return data[0];
		int dx = (int) x;
		return data[dx] + (data[dx + 1] - (double) data[dx]) * (x - dx);
	}

	/**
	 * Returns the value of specified decimal index.
	 * <p>
	 * Calculating algorithm is implemented by first-order interpolation.
	 * 
	 * <pre>
	 * f(x,y) = f(x',y') + (f(x'+1,y') - f(x',y')) * (x - x') + (f(x',y'+1) - f(x',y')) * (y - y')
	 * + (f(x'+1,y'+1)+f(x',y' )-f(x'+1,y' )-f(x',y'+1)) * (x - x') * (y - y')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>Double</tt> array
	 * @param x
	 *            the decimal index
	 * @return the interpolation value
	 */
	public static final double interpolate(Double[] data, double x)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1)
			return data[data.length];
		if (x < 0)
			return data[0];
		int dx = (int) x;
		return data[dx] + (data[dx + 1] - (double) data[dx]) * (x - dx);
	}

	/**
	 * Returns the value of specified decimal position(x,y).
	 * <p>
	 * Calculating algorithm is implemented by 2D first-order interpolation.
	 * 
	 * <pre>
	 * f(x,y) = f(x',y') + (f(x'+1,y') - f(x',y')) * (x - x') + (f(x',y'+1) - f(x',y')) * (y - y')
	 * + (f(x'+1,y'+1)+f(x',y' )-f(x'+1,y' )-f(x',y'+1)) * (x - x') * (y - y')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>int</tt> matrix
	 * @param x
	 *            the decimal x coordinate
	 * @param y
	 *            the decimal y coordinate
	 * @return the interpolation value
	 */
	public static final double interpolate(int[][] data, double x, double y)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1 || y > data[0].length - 1)
			return data[data.length - 1][data[data.length - 1].length - 1];
		if (x < 0 || y < 0)
			return data[0][0];
		int dx = (int) x;
		int dy = (int) y;
		int p00 = data[dx][dy];
		int p01 = data[dx][dy + 1];
		int p10 = data[dx + 1][dy];
		int p11 = data[dx + 1][dy + 1];
		return p00 + (p10 - p00) * (x - dx) + (p01 - p00) * (y - dy) - (p11 + p00 - p10 - p11) * (x - dx) * (y - dy);
	}

	/**
	 * Returns the value of specified decimal position(x,y).
	 * <p>
	 * Calculating algorithm is implemented by 2D first-order interpolation.
	 * 
	 * <pre>
	 * f(x,y) = f(x',y') + (f(x'+1,y') - f(x',y')) * (x - x') + (f(x',y'+1) - f(x',y')) * (y - y')
	 * + (f(x'+1,y'+1)+f(x',y' )-f(x'+1,y' )-f(x',y'+1)) * (x - x') * (y - y')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>long</tt> matrix
	 * @param x
	 *            the decimal x coordinate
	 * @param y
	 *            the decimal y coordinate
	 * @return the interpolation value
	 */
	public static final double interpolate(long[][] data, double x, double y)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1 || y > data[0].length - 1)
			return data[data.length - 1][data[data.length - 1].length - 1];
		if (x < 0 || y < 0)
			return data[0][0];
		int dx = (int) x;
		int dy = (int) y;
		long p00 = data[dx][dy];
		long p01 = data[dx][dy + 1];
		long p10 = data[dx + 1][dy];
		long p11 = data[dx + 1][dy + 1];
		return p00 + (p10 - p00) * (x - dx) + (p01 - p00) * (y - dy) - (p11 + p00 - p10 - p11) * (x - dx) * (y - dy);
	}

	/**
	 * Returns the value of specified decimal position(x,y).
	 * <p>
	 * Calculating algorithm is implemented by 2D first-order interpolation.
	 * 
	 * <pre>
	 * f(x,y) = f(x',y') + (f(x'+1,y') - f(x',y')) * (x - x') + (f(x',y'+1) - f(x',y')) * (y - y')
	 * + (f(x'+1,y'+1)+f(x',y' )-f(x'+1,y' )-f(x',y'+1)) * (x - x') * (y - y')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>float</tt> matrix
	 * @param x
	 *            the decimal x coordinate
	 * @param y
	 *            the decimal y coordinate
	 * @return the interpolation value
	 */
	public static final double interpolate(float[][] data, double x, double y)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1 || y > data[0].length - 1)
			return data[data.length - 1][data[data.length - 1].length - 1];
		if (x < 0 || y < 0)
			return data[0][0];
		int dx = (int) x;
		int dy = (int) y;
		float p00 = data[dx][dy];
		float p01 = data[dx][dy + 1];
		float p10 = data[dx + 1][dy];
		float p11 = data[dx + 1][dy + 1];
		return p00 + (p10 - p00) * (x - dx) + (p01 - p00) * (y - dy) - (p11 + p00 - p10 - p11) * (x - dx) * (y - dy);
	}

	/**
	 * Returns the value of specified decimal position(x,y).
	 * <p>
	 * Calculating algorithm is implemented by 2D first-order interpolation.
	 * 
	 * <pre>
	 * f(x,y) = f(x',y') + (f(x'+1,y') - f(x',y')) * (x - x') + (f(x',y'+1) - f(x',y')) * (y - y')
	 * + (f(x'+1,y'+1)+f(x',y' )-f(x'+1,y' )-f(x',y'+1)) * (x - x') * (y - y')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>double</tt> matrix
	 * @param x
	 *            the decimal x coordinate
	 * @param y
	 *            the decimal y coordinate
	 * @return the interpolation value
	 */
	public static final double interpolate(double[][] data, double x, double y)
	{
		if (data.length == 0)
			return 0;
		if (x < 0 || y < 0)
			return data[0][0];
		int dx = (int) x;
		int dy = (int) y;
		int maxY = data.length - 1;
		int maxX = data[maxY].length - 1;
		if (dy + 1 > maxY)
		{
			if (dx + 1 > maxX)
				return data[maxY][maxX];
			else
				// calculate when only y is at the end
				return (x - dx) * data[maxY][dx] + (dx + 1 - x) * data[maxY][dx + 1];
		}
		else if (dx + 1 > maxX)
			// calculate when only x is at the end
			return (y - dy) * data[dy][maxX] + (dy + 1 - y) * data[dy + 1][maxX];
		double p00 = data[dy][dx];
		double p01 = data[dy + 1][dx];
		double p10 = data[dy][dx + 1];
		double p11 = data[dy + 1][dx + 1];
		return p00 + (p10 - p00) * (x - dx) + (p01 - p00) * (y - dy) - (p11 + p00 - p10 - p11) * (x - dx) * (y - dy);
	}

	/**
	 * Returns the value of specified decimal position(x,y).
	 * <p>
	 * Calculating algorithm is implemented by 2D first-order interpolation.
	 * 
	 * <pre>
	 * f(x,y) = f(x',y') + (f(x'+1,y') - f(x',y')) * (x - x') + (f(x',y'+1) - f(x',y')) * (y - y')
	 * + (f(x'+1,y'+1)+f(x',y' )-f(x'+1,y' )-f(x',y'+1)) * (x - x') * (y - y')
	 * x' = rounded_down(x)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param data
	 *            the specified <tt>double</tt> matrix
	 * @param x
	 *            the decimal x coordinate
	 * @param y
	 *            the decimal y coordinate
	 * @return the interpolation value
	 */
	public static final <T extends Number> double interpolate(T[][] data, double x, double y)
	{
		if (data.length == 0)
			return 0;
		if (x > data.length - 1 || y > data[0].length - 1)
			return data[data.length - 1][data[data.length - 1].length - 1].doubleValue();
		if (x < 0 || y < 0)
			return data[0][0].doubleValue();
		int dx = (int) x;
		int dy = (int) y;
		double p00 = data[dx][dy].doubleValue();
		double p01 = data[dx][dy + 1].doubleValue();
		double p10 = data[dx + 1][dy].doubleValue();
		double p11 = data[dx + 1][dy + 1].doubleValue();
		return p00 + (p10 - p00) * (x - dx) + (p01 - p00) * (y - dy) - (p11 + p00 - p10 - p11) * (x - dx) * (y - dy);
	}

	/**
	 * Returns &radic;(a^2 + b^2) without under/overflow.
	 * 
	 * @param a
	 * @param b
	 * @return &radic;(a^2 + b^2)
	 */
	public static double hypot(double a, double b)
	{
		double r;
		if (Math.abs(a) > Math.abs(b))
		{
			r = b / a;
			r = Math.abs(a) * Math.sqrt(1 + r * r);
		}
		else if (b != 0)
		{
			r = a / b;
			r = Math.abs(b) * Math.sqrt(1 + r * r);
		}
		else
		{
			r = 0.0;
		}
		return r;
	}

	/**
	 * Returns the largest (closest to positive infinity) {@code double} value
	 * that is less than or equal to the
	 * argument and is equal to a mathematical integer. Special cases:
	 * <ul>
	 * <li>If the argument value is already equal to a mathematical integer,
	 * then the result is the same as the argument.</li>
	 * <li>If the argument is NaN or an infinity or positive zero or negative
	 * zero, then the result is the same as the argument.</li>
	 * </ul>
	 * <p>
	 * This method is different from the {@linkplain Math#floor(double) native
	 * floor method}, this method can avoid adding up error. If the value is has
	 * the smaller distance between <code>d</code> and <code>(int) a</code> than
	 * use (int) a to represent it.
	 * </p>
	 * 
	 * @param a
	 *            a value.
	 * @param accuracy
	 *            the accuracy for judging adding up error
	 * @return the largest (closest to positive infinity)
	 *         floating-point value that less than or equal to the argument
	 *         and is equal to a mathematical integer.
	 */
	public static long floor(double a, double accuracy)
	{
		if (accuracy > a - (long) a)
			return (int) a;
		else
			return (int) Math.floor(a);
	}

	/**
	 * Returns the smallest (closest to negative infinity) {@code double} value
	 * that is greater than or equal to the
	 * argument and is equal to a mathematical integer. Special cases:
	 * <ul>
	 * <li>If the argument value is already equal to a mathematical integer,
	 * then the result is the same as the argument.</li>
	 * <li>If the argument is NaN or an infinity or positive zero or negative
	 * zero, then the result is the same as the argument.</li>
	 * <li>If the argument value is less than zero but greater than -1.0, then
	 * the result is negative zero.</li>
	 * </ul>
	 * Note that the value of {@code Math.ceil(x)} is exactly the
	 * value of {@code -Math.floor(-x)}.
	 * <p>
	 * This method is different from the {@linkplain Math#floor(double) native
	 * floor method}, this method can avoid adding up error. If the value is has
	 * the smaller distance between <code>d</code> and <code>(int) a</code> than
	 * use (int) a to represent it.
	 * </p>
	 * 
	 * @param a
	 *            a value.
	 * @return the smallest (closest to negative infinity)
	 *         floating-point value that is greater than or equal to
	 *         the argument and is equal to a mathematical integer.
	 */
	public static long ceil(double a, double accuracy)
	{
		if (accuracy > a - (long) a)
			return (long) a;
		else
			return (long) Math.ceil(a);
	}

	/**
	 * Returns the Euclidean distance between vector <code>a</code> and
	 * <code>b</code>.
	 * 
	 * @param a
	 * @param b
	 * @return the Euclidean distance
	 */
	public static final double disEuclidean(int[] a, int[] b)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double sum = 0, d;
		for (int i = 0; i < a.length; i++)
		{
			d = a[i] - b[i];
			sum += d * d;
		}
		return sum;
	}

	/**
	 * Returns the Euclidean distance between vector <code>a</code> and
	 * <code>b</code>.
	 * 
	 * @param a
	 * @param b
	 * @return the Euclidean distance
	 */
	public static final double disEuclidean(long[] a, long[] b)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double sum = 0, d;
		for (int i = 0; i < a.length; i++)
		{
			d = a[i] - b[i];
			sum += d * d;
		}
		return sum;
	}

	/**
	 * Returns the Euclidean distance between vector <code>a</code> and
	 * <code>b</code>.
	 * 
	 * @param a
	 * @param b
	 * @return the Euclidean distance
	 */
	public static final double disEuclidean(float[] a, float[] b)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double sum = 0, d;
		for (int i = 0; i < a.length; i++)
		{
			d = a[i] - b[i];
			sum += d * d;
		}
		return sum;
	}

	/**
	 * Returns the Euclidean distance between vector <code>a</code> and
	 * <code>b</code>.
	 * 
	 * @param a
	 * @param b
	 * @return the Euclidean distance
	 */
	public static final double disEuclidean(double[] a, double[] b)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double sum = 0, d;
		for (int i = 0; i < a.length; i++)
		{
			d = a[i] - b[i];
			sum += d * d;
		}
		return sum;
	}

	/**
	 * Returns the Euclidean distance between vector <code>a</code> and
	 * <code>b</code>.
	 * 
	 * @param a
	 * @param b
	 * @return the Euclidean distance
	 */
	public static final <T extends Number> double disEuclidean(T[] a, T[] b)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		double sum = 0, d;
		for (int i = 0; i < a.length; i++)
		{
			d = a[i].doubleValue() - b[i].doubleValue();
			sum += d * d;
		}
		return sum;
	}

	/**
	 * Returns the Minkowski distance between vector <code>a</code> and
	 * <code>b</code>.
	 * 
	 * @param a
	 * @param b
	 * @param order
	 * @return the Minkowski distance
	 */
	public static final double disMinkowski(int[] a, int[] b, int order)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		if (order < 1)
			throw new IllegalArgumentException(Messages.getString("MathUtils.OrderMustPositive")); //$NON-NLS-1$
		double sum = 0, d;
		for (int i = 0; i < a.length; i++)
		{
			d = a[i] - b[i];
			sum += Math.pow(d, order);
		}
		return Math.pow(sum, 1.0 / order);
	}

	/**
	 * Returns the Minkowski distance between vector <code>a</code> and
	 * <code>b</code>.
	 * 
	 * @param a
	 * @param b
	 * @param order
	 * @return the Minkowski distance
	 */
	public static final double disMinkowski(long[] a, long[] b, int order)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		if (order < 1)
			throw new IllegalArgumentException(Messages.getString("MathUtils.OrderMustPositive")); //$NON-NLS-1$
		double sum = 0, d;
		for (int i = 0; i < a.length; i++)
		{
			d = a[i] - b[i];
			sum += Math.pow(d, order);
		}
		return Math.pow(sum, 1.0 / order);
	}

	/**
	 * Returns the Minkowski distance between vector <code>a</code> and
	 * <code>b</code>.
	 * 
	 * @param a
	 * @param b
	 * @param order
	 * @return the Minkowski distance
	 */
	public static final double disMinkowski(float[] a, float[] b, int order)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		if (order < 1)
			throw new IllegalArgumentException(Messages.getString("MathUtils.OrderMustPositive")); //$NON-NLS-1$
		double sum = 0, d;
		for (int i = 0; i < a.length; i++)
		{
			d = a[i] - b[i];
			sum += Math.pow(d, order);
		}
		return Math.pow(sum, 1.0 / order);
	}

	/**
	 * Returns the Minkowski distance between vector <code>a</code> and
	 * <code>b</code>.
	 * 
	 * @param a
	 * @param b
	 * @param order
	 * @return the Minkowski distance
	 */
	public static final double disMinkowski(double[] a, double[] b, int order)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		if (order < 1)
			throw new IllegalArgumentException(Messages.getString("MathUtils.OrderMustPositive")); //$NON-NLS-1$
		double sum = 0, d;
		for (int i = 0; i < a.length; i++)
		{
			d = a[i] - b[i];
			sum += Math.pow(d, order);
		}
		return Math.pow(sum, 1.0 / order);
	}

	/**
	 * Returns the Minkowski distance between vector <code>a</code> and
	 * <code>b</code>.
	 * 
	 * @param a
	 * @param b
	 * @param order
	 * @return the Minkowski distance
	 */
	public static final <T extends Number> double disMinkowski(T[] a, T[] b, int order)
	{
		if (a.length != b.length)
			throw new IllegalArgumentException(Messages.getString("MathUtils.ArraySizeNotAgreed")); //$NON-NLS-1$
		if (order < 1)
			throw new IllegalArgumentException(Messages.getString("MathUtils.OrderMustPositive")); //$NON-NLS-1$
		double sum = 0, d;
		for (int i = 0; i < a.length; i++)
		{
			d = a[i].doubleValue() - b[i].doubleValue();
			sum += Math.pow(d, order);
		}
		return Math.pow(sum, 1.0 / order);
	}

	/**
	 * Returns the covariance matrix between <code>x</code> and <code>y</code>
	 * 
	 * @param x
	 * @param y
	 * @return the covariacne matrix
	 */
	public static final Matrix covariance(int[] x, int[] y)
	{
		Matrix cov = new Matrix(x.length, y.length, 0.0);
		double xa = MathUtils.average(x);
		double ya = MathUtils.average(y);
		for (int j = 0; j < y.length; j++)
			for (int i = 0; i < x.length; i++)
				cov.set(i, j, (x[i] - xa) * (y[i] - ya));
		return cov;
	}

	/**
	 * Returns the covariance matrix between <code>x</code> and <code>y</code>
	 * 
	 * @param x
	 * @param y
	 * @return the covariacne matrix
	 */
	public static final Matrix covariance(long[] x, long[] y)
	{
		Matrix cov = new Matrix(x.length, y.length, 0.0);
		double xa = MathUtils.average(x);
		double ya = MathUtils.average(y);
		for (int j = 0; j < y.length; j++)
			for (int i = 0; i < x.length; i++)
				cov.set(i, j, (x[i] - xa) * (y[i] - ya));
		return cov;
	}

	/**
	 * Returns the covariance matrix between <code>x</code> and <code>y</code>
	 * 
	 * @param x
	 * @param y
	 * @return the covariacne matrix
	 */
	public static final Matrix covariance(float[] x, float[] y)
	{
		Matrix cov = new Matrix(x.length, y.length, 0.0);
		double xa = MathUtils.average(x);
		double ya = MathUtils.average(y);
		for (int j = 0; j < y.length; j++)
			for (int i = 0; i < x.length; i++)
				cov.set(i, j, (x[i] - xa) * (y[i] - ya));
		return cov;
	}

	/**
	 * Returns the covariance matrix between <code>x</code> and <code>y</code>
	 * 
	 * @param x
	 * @param y
	 * @return the covariacne matrix
	 */
	public static final Matrix covariance(double[] x, double[] y)
	{
		Matrix cov = new Matrix(x.length, y.length, 0.0);
		double xa = MathUtils.average(x);
		double ya = MathUtils.average(y);
		for (int j = 0; j < y.length; j++)
			for (int i = 0; i < x.length; i++)
				cov.set(i, j, (x[i] - xa) * (y[i] - ya));
		return cov;
	}

	/**
	 * Returns the covariance matrix between <code>x</code> and <code>y</code>
	 * 
	 * @param x
	 * @param y
	 * @return the covariacne matrix
	 */
	public static final <T extends Number> Matrix covariance(T[] x, T[] y)
	{
		Matrix cov = new Matrix(x.length, y.length, 0.0);
		double xa = MathUtils.average(x);
		double ya = MathUtils.average(y);
		for (int j = 0; j < y.length; j++)
			for (int i = 0; i < x.length; i++)
				cov.set(i, j, (x[i].doubleValue() - xa) * (y[i].doubleValue() - ya));
		return cov;
	}

	/**
	 * Calculate the square root of the specified <code>b</code> and returns
	 * only the integer part of square root.
	 * <p>
	 * It will return <code>ZERO</code> if it meet nonpositive value.
	 * </p>
	 * 
	 * @param b
	 * @return (int)&radic;<code>b</code>
	 */
	public final static BigInteger sqrt(BigInteger b)
	{
		String num = b.toString(); // the string of b
		int len = num.length(); // the bit count in 10 radix 
		if (b.compareTo(BigInteger.ZERO) < 0)
			return BigInteger.ZERO;
		BigInteger sqrt = BigInteger.ZERO; // the result of square root
		BigInteger pre = BigInteger.ZERO; // the minuend to calculate in square root
		BigInteger dividend;// the dividend to calculate in square root
		BigInteger trynum; // try number, the subtrahend to calculate in square root
		BigInteger flag; // try number, the number after a statisfied subtrahend
		BigInteger twenty = new BigInteger("20"); // big integer version: 20
		if (len % 2 == 0) // length is even
			for (int i = 0; i < len / 2; ++i) // the length of the sqrt root must be len/2
			{
				dividend = new BigInteger(pre + num.substring(2 * i, 2 * i + 2));
				for (int j = 0; j <= 9; ++j)
				{
					trynum = twenty.multiply(sqrt).multiply(new BigInteger(j + ""))
							.add(new BigInteger(j + "").multiply(new BigInteger(j + "")));
					flag = twenty.multiply(sqrt).multiply(new BigInteger((j + 1) + ""))
							.add(new BigInteger((j + 1) + "").multiply(new BigInteger((j + 1) + "")));
					// if j statisify that the substraction of trynum & minuend is a smallest positive number
					if (trynum.subtract(dividend).compareTo(BigInteger.ZERO) <= 0
							&& flag.subtract(dividend).compareTo(BigInteger.ZERO) > 0)
					{
						sqrt = new BigInteger(sqrt.toString() + j);
						pre = dividend.subtract(trynum);// update
						break;
					}
				}
			}
		else
			// length is odd
			for (int i = 0; i < len / 2 + 1; ++i) // the length of the sqrt root must be len/2 + 1
			{
				if (i == 0) // the head of odd number should be specially dealed
					dividend = new BigInteger(num.charAt(0) + "");
				else
					dividend = new BigInteger(pre + num.substring(2 * i - 1, 2 * i + 1));
				for (int j = 0; j <= 9; ++j)
				{
					trynum = twenty.multiply(sqrt).multiply(new BigInteger(j + ""))
							.add(new BigInteger(j + "").multiply(new BigInteger(j + "")));
					flag = twenty.multiply(sqrt).multiply(new BigInteger((j + 1) + ""))
							.add(new BigInteger((j + 1) + "").multiply(new BigInteger((j + 1) + "")));
					// if j statisify that the substraction of trynum & minuend is a smallest positive number
					if (trynum.subtract(dividend).compareTo(BigInteger.ZERO) <= 0
							&& flag.subtract(dividend).compareTo(BigInteger.ZERO) > 0)
					{
						sqrt = new BigInteger(sqrt.toString() + j);
						pre = dividend.subtract(trynum);
						break;
					}
				}
			}
		return sqrt;
	}

	/**
	 * Returns the factors of the specified <code>n</code>
	 * 
	 * @param n
	 * @return the factors of the specified <code>n</code>
	 */
	public static List<BigInteger> factoring(BigInteger n)
	{
		LinkedList<BigInteger> list = new LinkedList<>();
		BigInteger max, i, j;
		BigInteger two = BigInteger.valueOf(2);
		while (n.compareTo(two) > 0)
		{
			max = sqrt(n).add(BigInteger.ONE);
			j = n;
			for (i = two; i.compareTo(max) <= 0; i = i.add(BigInteger.ONE))
				if (n.mod(i) == BigInteger.ZERO)
				{
					list.add(i);
					n = n.divide(i);
					break;
				}
			if (j == n)
				break;
		}
		if (list.isEmpty() || n.compareTo(two) > 0)
			list.add(n);
		return list;
	}

	/**
	 * Returns the factors of the specified <code>n</code>
	 * 
	 * @param n
	 * @return the factors of the specified <code>n</code>
	 */
	public static List<Long> factoring(long n)
	{
		LinkedList<Long> list = new LinkedList<>();
		long i, j, max;
		while (n > 2)
		{
			max = (long) Math.sqrt(n) + 1;
			j = n;
			for (i = 2; i <= max; i++)
				if (n % i == 0)
				{
					list.add(i);
					n /= i;
					break;
				}
			if (j == n)
				break;
		}
		if (list.isEmpty() || n > 2)
			list.add(n);
		return list;
	}

	/**
	 * Returns the factors of the specified <code>n</code>
	 * 
	 * @param n
	 * @return the factors of the specified <code>n</code>
	 */
	public static List<Integer> factoring(int n)
	{
		LinkedList<Integer> list = new LinkedList<>();
		int i, j, max;
		while (n > 2)
		{
			max = (int) Math.sqrt(n) + 1;
			j = n;
			for (i = 2; i <= max; i++)
				if (n % i == 0)
				{
					list.add(i);
					n /= i;
					break;
				}
			if (j == n)
				break;
		}
		if (list.isEmpty() || n > 2)
			list.add(n);
		return list;
	}
}
