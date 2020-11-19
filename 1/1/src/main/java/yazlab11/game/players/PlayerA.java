package yazlab11.game.players;

import yazlab11.Logger;
import yazlab11.PathFinder;
import yazlab11.game.Gold;
import yazlab11.game.Grid;

import java.awt.*;
import java.util.ArrayList;

public class PlayerA extends Player
{
	public PlayerA(Grid grid, int goldAmount, int chooseCost, int moveCost)
	{
		super("A", Color.red, grid, goldAmount, chooseCost, moveCost);
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

		if (target == null)
			return null;

		Logger.logPlayer(name, String.format("Yeni hedef olarak %.0f, %.0f karesindeki altını belirledi.", target.grid.position.x, target.grid.position.y));
		addGold(-this.chooseCost, "Hedef belirleme");
		return target;
	}
}
