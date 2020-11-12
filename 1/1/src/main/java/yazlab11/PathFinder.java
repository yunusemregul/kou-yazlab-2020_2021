package yazlab11;

import yazlab11.game.Grid;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathFinder
{
	public static double calculateBirdViewDistance(Point a, Point b)
	{
		return Math.sqrt(Math.pow((b.x - a.x), 2) + Math.pow((b.y - a.y), 2));
	}

	public static double calculateBirdViewDistance(Grid a, Grid b)
	{
		return calculateBirdViewDistance(a.position, b.position);
	}

	public Path findPath(Grid from, Grid to)
	{
		Queue<PathGrid> openSet = new PriorityQueue<>();
		Map<Grid, PathGrid> allNodes = new HashMap<>();

		PathGrid start = new PathGrid(from, null, 0.f, (float) calculateBirdViewDistance(from, to));
		openSet.add(start);
		allNodes.put(from, start);

		while (!openSet.isEmpty())
		{
			PathGrid next = openSet.poll();
			// we arrived to the target
			if (next.getCurrent().equals(to))
			{
				Path path = new Path();
				PathGrid current = next;
				path.cost = current.getPathScore();
				do
				{
					path.grids.add(0, current.getCurrent());
					current = allNodes.get(current.getPrevious());
				} while (current.getCurrent() != start.getCurrent());
				return path;
			}

			for (Grid connection : next.getCurrent().getConnected())
			{
				PathGrid nextNode = allNodes.getOrDefault(connection, new PathGrid(connection));
				allNodes.put(connection, nextNode);

				float newScore = (float) (next.getPathScore() + calculateBirdViewDistance(next.getCurrent(), connection));
				if (newScore < nextNode.getPathScore())
				{
					nextNode.setPrevious(next.getCurrent());
					nextNode.setPathScore(newScore);
					nextNode.setEstimatedScore((float) (newScore + calculateBirdViewDistance(connection, to)));
					openSet.add(nextNode);
				}
			}
		}

		return null;
	}
}
