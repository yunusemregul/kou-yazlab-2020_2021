package yazlab11.game.players;

import yazlab11.GameDrawer;
import yazlab11.Logger;
import yazlab11.Path;
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

	public void addGold(int amount)
	{
		goldAmount += amount;
		Logger.logPlayer(name, String.format("Bakiyesine %d altın eklendi. Yeni bakiyesi: %d", amount, goldAmount));
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

	public abstract Gold chooseTarget(ArrayList<Gold> golds);
}
