package yazlab11.players;

import yazlab11.Gold;
import yazlab11.PathFinder;
import yazlab11.Point;

import java.awt.*;
import java.util.ArrayList;

public class PlayerD extends Player
{
	public PlayerD(Point position, int chooseCost, int moveCost)
	{
		super("D", new Color(154,203,154), position, chooseCost, moveCost);
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
