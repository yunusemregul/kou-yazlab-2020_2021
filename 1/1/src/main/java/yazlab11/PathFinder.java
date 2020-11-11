package yazlab11;

public class PathFinder
{
	public static double calculateBirdViewDistance(Point a, Point b)
	{
		return Math.sqrt(Math.pow((b.x - a.x), 2) + Math.pow((b.y - a.y), 2));
	}
}
