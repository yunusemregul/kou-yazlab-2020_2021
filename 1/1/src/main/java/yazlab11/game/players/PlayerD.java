package yazlab11.game.players;

import yazlab11.GameDrawer;
import yazlab11.Logger;
import yazlab11.PathFinder;
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
		double closestOtherPlayersTargetDistance = Double.MAX_VALUE;
		Gold closestOtherPlayersTarget = null;
		for (Player player : GameDrawer.activePlayers)
		{
			if (player==this)
				continue;

			if (player.target==null)
				continue;

			double playersDistance = PathFinder.calculateBirdViewDistance(player.grid, player.target.grid);
			double ourDistance = PathFinder.calculateBirdViewDistance(this.grid, player.target.grid);
			if (ourDistance<playersDistance)
			{
				if (ourDistance < closestOtherPlayersTargetDistance)
				{
					closestOtherPlayersTarget = player.target;
					closestOtherPlayersTargetDistance = ourDistance;
				}
			}
		}

		if (closestOtherPlayersTarget!=null)
		{
			target = closestOtherPlayersTarget;
			Logger.logPlayer(name, String.format("Yeni hedef olarak %.0f, %.0f karesindeki altını belirledi.", target.grid.position.x, target.grid.position.y));
			addGold(-this.chooseCost, "Hedef belirleme");
			return closestOtherPlayersTarget;
		}

		target = findMostProfitableGold(golds);

		if (target == null)
			return null;

		Logger.logPlayer(name, String.format("Yeni hedef olarak %.0f, %.0f karesindeki altını belirledi.", target.grid.position.x, target.grid.position.y));
		addGold(-this.chooseCost,"Hedef belirleme");
		return target;
	}
}
