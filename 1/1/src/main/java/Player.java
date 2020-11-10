import java.awt.*;

public abstract class Player
{
	public final String name;
	public Point position;

	protected Player(String name)
	{
		this.name = name;
	}

	public void draw(Graphics2D g)
	{

	}

	abstract void move();
}
