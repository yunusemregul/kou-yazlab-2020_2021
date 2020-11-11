package yazlab11;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Gold
{
	public Point grid;
	public boolean hidden;
	private static final Color color = new Color(255,223,0);
	private static final Color hiddenColor = new Color(99, 99, 99);

	public Gold(Point grid, boolean hidden)
	{
		this.grid = grid;
		this.hidden = hidden;
	}

	public void draw(Graphics2D g)
	{
		Point screenPos = GameDrawer.getGridScreenPos(grid);
		g.setColor(hidden ? hiddenColor : color);
		Ellipse2D.Float circle = new Ellipse2D.Float(screenPos.x+2, screenPos.y+2, GameDrawer.gridSize-4, GameDrawer.gridSize-4);
		g.fill(circle);
	}
}
