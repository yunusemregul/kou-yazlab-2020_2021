package yazlab11.game.players;

import yazlab11.PathFinder;
import yazlab11.Point;
import yazlab11.game.Gold;

import java.awt.*;
import java.util.ArrayList;

public class PlayerA extends Player
{
	public PlayerA(Point position, int chooseCost, int moveCost)
	{
		super("A", Color.red, position, chooseCost, moveCost);
	}

	public Gold chooseTarget(ArrayList<Gold> golds)
	{
		double minDistance = Double.MAX_VALUE;
		Gold closestGold = null;
		for (Gold gold : golds)
		{
			if(gold.hidden)
				continue;

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
