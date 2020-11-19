package yazlab11.game.players;

import yazlab11.Logger;
import yazlab11.PathFinder;
import yazlab11.game.Gold;
import yazlab11.game.Grid;

import java.awt.*;
import java.util.ArrayList;

public class PlayerC extends Player
{
	public PlayerC(Grid grid, int goldAmount, int chooseCost, int moveCost)
	{
		super("C", Color.BLUE, grid, goldAmount, chooseCost, moveCost);
	}

	public Gold chooseTarget(ArrayList<Gold> golds)
	{
		for (int i = 0; i < 2; i++)
		{
			double minDistance = Double.MAX_VALUE;
			Gold closestGold = null;
			for (Gold gold : golds)
			{
				if (!gold.hidden)
					continue;

				double distance = PathFinder.calculateBirdViewDistance(this.grid, gold.grid);

				if (distance < minDistance)
				{
					minDistance = distance;
					closestGold = gold;
				}
			}
			if (closestGold!=null)
				closestGold.hidden = false;
		}

		target = findMostProfitableGold(golds);

		if (target == null)
			return null;

		Logger.logPlayer(name, String.format("Yeni hedef olarak %.0f, %.0f karesindeki altını belirledi.", target.grid.position.x, target.grid.position.y));
		addGold(-this.chooseCost, "Hedef belirleme");
		return target;
	}
}
