import java.awt.*;
import java.awt.geom.Ellipse2D;

public abstract class Player
{
	public final String name;
	public final Color color;
	public Point position;

	protected Player(String name, Color color, Point position)
	{
		this.name = name;
		this.color = color;
		this.position = position;
	}

	public void draw(Graphics2D g, int squareSize)
	{
		float x = position.x * squareSize;
		float y = position.y * squareSize;
		g.setColor(color);
		Ellipse2D.Float circle = new Ellipse2D.Float(x+2, y+2, squareSize-4, squareSize-4);
		g.fill(circle);
		g.setColor(Color.white);
		g.drawString(name, x+squareSize/2-4, y+squareSize/2+5);
	}

	abstract void chooseMove();
}
