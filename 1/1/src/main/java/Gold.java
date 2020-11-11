import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Gold
{
	public Point position;
	public boolean hidden;
	private static final Color color = new Color(255,223,0);
	private static final Color hiddenColor = new Color(77, 77, 77);

	public Gold(Point position, boolean hidden)
	{
		this.position = position;
		this.hidden = hidden;
	}

	public void draw(Graphics2D g, int squareSize)
	{
		float x = position.x * squareSize;
		float y = position.y * squareSize;
		g.setColor(hidden ? hiddenColor : color);
		Ellipse2D.Float circle = new Ellipse2D.Float(x+2, y+2, squareSize-4, squareSize-4);
		g.fill(circle);
	}
}
