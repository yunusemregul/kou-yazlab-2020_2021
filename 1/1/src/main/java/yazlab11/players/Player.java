package yazlab11.players;

import yazlab11.GameDrawer;
import yazlab11.Gold;
import yazlab11.Point;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public abstract class Player
{
	public final String name;
	public final Color color;
	public yazlab11.Point grid;
	public Gold target;

	protected Player(String name, Color color, yazlab11.Point grid)
	{
		this.name = name;
		this.color = color;
		this.grid = grid;
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

	abstract void chooseMove();
}
