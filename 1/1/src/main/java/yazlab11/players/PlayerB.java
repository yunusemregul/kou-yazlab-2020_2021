package yazlab11.players;

import yazlab11.Gold;
import yazlab11.PathFinder;
import yazlab11.Point;

import java.awt.*;
import java.util.ArrayList;

public class PlayerB extends Player
{
	public PlayerB(Point position, int chooseCost, int moveCost)
	{
		super("B", new Color(50, 0, 50), position, chooseCost, moveCost);
	}

	public Gold chooseMove(ArrayList<Gold> golds)
	{
		double minDistance = Double.MAX_VALUE;
		Gold closestGold = null;
		for (Gold gold : golds)
		{
			double distance = PathFinder.calculateBirdViewDistance(this.grid, gold.grid);

			if (distance < minDistance)
			{
				minDistance = distance;
				closestGold = gold;
			}
		}

		target = closestGold;

		return target;
	}
}
