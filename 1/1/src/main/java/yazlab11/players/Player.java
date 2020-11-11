package yazlab11.players;

import yazlab11.GameDrawer;
import yazlab11.Gold;
import yazlab11.Point;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public abstract class Player
{
	public final String name;
	public final Color color;
	public Point grid;
	public Gold target;
	public int goldAmount;
	private int chooseCost;
	private int moveCost;

	protected Player(String name, Color color, Point grid, int chooseCost, int moveCost)
	{
		this.name = name;
		this.color = color;
		this.grid = grid;
		this.goldAmount = 0;
		this.chooseCost = chooseCost;
		this.moveCost = moveCost;
	}

	public void addGold(int amount)
	{
		goldAmount += amount;
	}

	public void draw(Graphics2D g)
	{
		Point screenPos = GameDrawer.getGridScreenPos(grid);
		g.setColor(color);
		Ellipse2D.Float circle = new Ellipse2D.Float(screenPos.x+2, screenPos.y+2, GameDrawer.gridSize-4, GameDrawer.gridSize-4);
		g.fill(circle);
		g.setColor(Color.white);
		g.drawString(name, screenPos.x+ GameDrawer.gridSize/2-4, screenPos.y+ GameDrawer.gridSize/2+5);
	}

	public abstract Gold chooseMove(ArrayList<Gold> golds);
}
