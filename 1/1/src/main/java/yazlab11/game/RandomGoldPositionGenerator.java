package yazlab11.game;

import yazlab11.Point;

import java.util.ArrayList;
import java.util.Collections;

public class RandomGoldPositionGenerator
{
	private ArrayList<Point> positions;
	private int xSize;
	private int ySize;

	public RandomGoldPositionGenerator(int xSize, int ySize)
	{
		this.xSize = xSize;
		this.ySize = ySize;
		this.positions = new ArrayList<Point>();
	}

	public void generate()
	{
		for (int x = 0; x < xSize; x++)
		{
			for (int y = 0; y < ySize; y++)
			{
				// to not generate positions at board corners, where players start the game, we exclude them
				if(x==0 && y==0)
					continue;
				if(x==(xSize-1) && y==0)
					continue;
				if(x==0 && y==(ySize-1))
					continue;
				if(x==(xSize-1) && y==(ySize-1))
					continue;

				positions.add(new Point(x, y));
			}
		}
		Collections.shuffle(positions);
	}

	public Point get()
	{
		return positions.remove(0);
	}
}
