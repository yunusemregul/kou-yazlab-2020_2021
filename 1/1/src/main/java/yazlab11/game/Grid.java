package yazlab11.game;

import yazlab11.GameDrawer;
import yazlab11.Point;

import java.util.ArrayList;
import java.util.Objects;

public class Grid
{
	public Point position;

	public Grid(Point position)
	{
		this.position = position;
	}

	public Grid(float x, float y)
	{
		this.position = new Point(x, y);
	}

	public ArrayList<Grid> getConnected()
	{
		ArrayList<Grid> connected = new ArrayList<>();

		// left
		if (position.x - 1 >= 0)
			connected.add(new Grid(position.x - 1, position.y));

		// top
		if (position.y - 1 >= 0)
			connected.add(new Grid(position.x, position.y - 1));

		// right
		if (position.x + 1 < GameDrawer.settings.get("xsize"))
			connected.add(new Grid(position.x + 1, position.y));

		// bottom
		if (position.y + 1 < GameDrawer.settings.get("ysize"))
			connected.add(new Grid(position.x, position.y + 1));

		return connected;
	}

	@Override
	public String toString()
	{
		return "Grid{" +
				"position=" + position +
				'}';
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Grid grid = (Grid) o;
		return this.position.equals(grid.position);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(position);
	}
}
