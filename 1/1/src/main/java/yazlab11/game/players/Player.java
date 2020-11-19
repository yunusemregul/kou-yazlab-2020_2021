package yazlab11.game.players;

import yazlab11.*;
import yazlab11.Point;
import yazlab11.game.Gold;
import yazlab11.game.Grid;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public abstract class Player
{
	public final String name;
	public final Color color;
	public Grid grid;
	public Gold target;
	public Path pathHasGone;
	public Path pathToTarget;
	private int goldAmount;
	public int chooseCost;
	public int moveCost;
	public static final PathFinder pathFinder = new PathFinder();

	protected Player(String name, Color color, Grid grid, int goldAmount, int chooseCost, int moveCost)
	{
		this.name = name;
		this.color = color;
		this.grid = grid;
		this.goldAmount = goldAmount;
		this.chooseCost = chooseCost;
		this.moveCost = moveCost;
		this.pathHasGone = new Path();
		this.pathHasGone.grids.add(grid);
	}

	public void addGold(int amount, String reason)
	{
		goldAmount += amount;
		Logger.logPlayer(name, String.format("(%s) Bakiyesine %d altın eklendi. Yeni bakiyesi: %d", reason, amount, goldAmount));
	}

	public int getGoldAmount()
	{
		return goldAmount;
	}

	public void draw(Graphics2D g)
	{
		Point screenPos = GameDrawer.getGridScreenPos(grid);
		g.setColor(color);
		Ellipse2D.Float circle = new Ellipse2D.Float(screenPos.x + 2, screenPos.y + 2, GameDrawer.gridSize - 4, GameDrawer.gridSize - 4);
		g.fill(circle);
		g.setColor(Color.white);
		g.drawString(name, screenPos.x + GameDrawer.gridSize / 2 - 4, screenPos.y + GameDrawer.gridSize / 2 + 5);
	}

	public void move(Grid grid)
	{
		this.grid = grid;
		Logger.logPlayer(name, String.format("%.0f, %.0f karesine hareket etti.", grid.position.x, grid.position.y));
	}

	public Gold findMostProfitableGold(ArrayList<Gold> golds)
	{
		double minCost = Double.MAX_VALUE;
		Gold mostProfitableGold = null;
		for (Gold gold : golds)
		{
			if(gold.hidden)
				continue;

			Path path = pathFinder.findPath(gold.grid, this.grid);
			double moveCost = (path.cost/3) * this.moveCost;

			if (moveCost < minCost)
			{
				minCost = moveCost;
				mostProfitableGold = gold;
			}
		}

		return mostProfitableGold;
	}

	public abstract Gold chooseTarget(ArrayList<Gold> golds);
}
