package yazlab11.game.players;

import yazlab11.*;
import yazlab11.Point;
import yazlab11.game.Gold;
import yazlab11.game.Grid;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Vector;

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
	public int totalMoveCount;
	public int wastedGoldAmount;
	public int collectedGoldAmount;
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
		this.totalMoveCount = 0;
		this.wastedGoldAmount = 0;
		this.collectedGoldAmount = 0;

	}

	public void addGold(int amount, String reason)
	{
		if (amount > 0)
		{
			collectedGoldAmount += amount;
		}
		else
		{
			wastedGoldAmount += -amount;
		}
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
		totalMoveCount++;
		this.grid = grid;
		Logger.logPlayer(name, String.format("%.0f, %.0f karesine hareket etti.", grid.position.x, grid.position.y));
	}

	public Gold findMostProfitableGold(ArrayList<Gold> golds)
	{
		double minCost = Double.MAX_VALUE;
		Gold mostProfitableGold = null;
		for (Gold gold : golds)
		{
			if (gold.hidden)
				continue;

			Path path = pathFinder.findPath(gold.grid, this.grid);
			double moveCost = (path.cost / GameDrawer.settings.get("movecount")) * this.moveCost + this.chooseCost - gold.amount;

			if (moveCost < minCost)
			{
				minCost = moveCost;
				mostProfitableGold = gold;
			}
		}

		return mostProfitableGold;
	}

	public Vector<String> generateStatsVector()
	{
		Vector<String > stats = new Vector<>();
		stats.add(this.name);
		stats.add(""+this.totalMoveCount);
		stats.add(""+this.wastedGoldAmount);
		stats.add(""+this.goldAmount);
		stats.add(""+this.collectedGoldAmount);

		return stats;
	}

	public abstract Gold chooseTarget(ArrayList<Gold> golds);
}
