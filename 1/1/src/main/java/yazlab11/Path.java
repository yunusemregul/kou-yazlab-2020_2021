package yazlab11;

import yazlab11.game.Grid;

import java.util.ArrayList;

public class Path
{
	public float cost = 0;
	public ArrayList<Grid> grids = new ArrayList<>();

	@Override
	public String toString()
	{
		StringBuilder gridStr = new StringBuilder();

		for (Grid grid : grids)
		{
			gridStr.append(String.format("(%.0f, %.0f) ", grid.position.x, grid.position.y));
		}

		return String.format("Path: cost=%.0f grids=%s", cost, gridStr.toString());
	}
}
