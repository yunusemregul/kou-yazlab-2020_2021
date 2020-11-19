package yazlab11.game.players;

import yazlab11.Logger;
import yazlab11.PathFinder;
import yazlab11.game.Gold;
import yazlab11.game.Grid;

import java.awt.*;
import java.util.ArrayList;

public class PlayerB extends Player
{
	public PlayerB(Grid grid, int goldAmount, int chooseCost, int moveCost)
	{
		super("B", new Color(50, 0, 50), grid, goldAmount, chooseCost, moveCost);
	}

	public Gold chooseTarget(ArrayList<Gold> golds)
	{
		target = findMostProfitableGold(golds);

		if (target == null)
			return null;

		Logger.logPlayer(name, String.format("Yeni hedef olarak %.0f, %.0f karesindeki altını belirledi.", target.grid.position.x, target.grid.position.y));
		addGold(-this.chooseCost, "Hedef belirleme");
		return target;
	}
}
