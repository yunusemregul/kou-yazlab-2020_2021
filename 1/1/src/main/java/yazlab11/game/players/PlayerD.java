package yazlab11.game.players;

import yazlab11.Logger;
import yazlab11.PathFinder;
import yazlab11.Point;
import yazlab11.game.Gold;
import yazlab11.game.Grid;

import java.awt.*;
import java.util.ArrayList;

public class PlayerD extends Player
{
	public PlayerD(Grid grid, int goldAmount, int chooseCost, int moveCost)
	{
		super("D", new Color(154,203,154), grid, goldAmount, chooseCost, moveCost);
	}

	public Gold chooseTarget(ArrayList<Gold> golds)
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

		Logger.log(name, String.format("Yeni hedef olarak %.0f, %.0f karesindeki altını belirledi.", target.grid.position.x, target.grid.position.y));
		addGold(-this.chooseCost);
		return target;
	}
}
