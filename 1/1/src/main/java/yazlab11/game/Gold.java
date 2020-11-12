package yazlab11.game;

import yazlab11.GameDrawer;
import yazlab11.Point;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

public class Gold
{
	public int amount;
	public Grid grid;
	public boolean hidden;
	private static final Color color = new Color(255,223,0);
	private static final Color hiddenColor = new Color(99, 99, 99);

	public Gold(Grid grid, boolean hidden, int amount)
	{
		this.grid = grid;
		this.hidden = hidden;
		this.amount = amount;
	}

	public Gold(Grid grid)
	{
		this.grid = grid;
		this.hidden = false;
		this.amount = 0;
	}

	public void draw(Graphics2D g)
	{
		Point screenPos = GameDrawer.getGridScreenPos(grid);
		g.setColor(hidden ? hiddenColor : color);
		Ellipse2D.Float circle = new Ellipse2D.Float(screenPos.x+2, screenPos.y+2, GameDrawer.gridSize-4, GameDrawer.gridSize-4);
		g.fill(circle);
		g.setColor(hidden ? Color.white : Color.BLACK);
		g.drawString(String.valueOf(amount), screenPos.x+ GameDrawer.gridSize/2-4, screenPos.y+ GameDrawer.gridSize/2+5);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Gold gold = (Gold) o;
		return Objects.equals(grid, gold.grid);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(grid);
	}
}
