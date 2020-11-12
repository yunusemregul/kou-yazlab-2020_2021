package yazlab11.game.players;

import yazlab11.PathFinder;
import yazlab11.Point;
import yazlab11.game.Gold;

import java.awt.*;
import java.util.ArrayList;

public class PlayerB extends Player
{
	public PlayerB(Point position, int chooseCost, int moveCost)
	{
		super("B", new Color(50, 0, 50), position, chooseCost, moveCost);
	}

	public Gold chooseTarget(ArrayList<Gold> golds)
	{
		double minCost = Double.MAX_VALUE;
		Gold mostProfitableGold = null;
		for (Gold gold : golds)
		{
			if(gold.hidden)
				continue;

			double distance = PathFinder.calculateBirdViewDistance(this.grid, gold.grid);
			double cost = distance/gold.amount;

			if (cost < minCost)
			{
				minCost = cost;
				mostProfitableGold = gold;
			}
		}

		target = mostProfitableGold;

		return target;
	}
}
