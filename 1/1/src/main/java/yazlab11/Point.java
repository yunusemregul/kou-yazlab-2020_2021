package yazlab11;

import java.util.Objects;

public class Point
{
	public float x;
	public float y;

	public Point()
	{
		this.x = 0;
		this.y = 0;
	}

	public Point(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void add(Point b)
	{
		this.x += b.x;
		this.y += b.y;
	}

	public void add(float x, float y)
	{
		this.x += x;
		this.y += y;
	}

	@Override
	public String toString()
	{
		return "Point{" +
				"x=" + x +
				", y=" + y +
				'}';
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Point point = (Point) o;
		return Float.compare(point.x, x) == 0 &&
				Float.compare(point.y, y) == 0;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(x, y);
	}
}
