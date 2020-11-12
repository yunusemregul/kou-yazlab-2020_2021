package yazlab11;

import yazlab11.game.Gold;
import yazlab11.game.Grid;
import yazlab11.game.RandomGoldPositionGenerator;
import yazlab11.game.players.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameDrawer
{
	public static int gridSize = 25;
	private final int width = 1366;
	private final int height = 768;

	public static HashMap<String, Integer> settings;
	private ArrayList<Player> players;
	private ArrayList<Gold> golds;
	private RandomGoldPositionGenerator goldPositionGenerator;

	private JPanel panel;
	private PathFinder pathFinder;

	GameDrawer(HashMap<String, Integer> settings)
	{
		this.settings = settings;

		players = new ArrayList<Player>();
		players.add(new PlayerA(new Grid(new Point(0, 0)), settings.get("startinggold"), settings.get("playerA_choosecost"), settings.get("playerA_movecost")));
		players.add(new PlayerB(new Grid(new Point(settings.get("xsize") - 1, 0)), settings.get("startinggold"), settings.get("playerB_choosecost"), settings.get("playerB_movecost")));
		players.add(new PlayerC(new Grid(new Point(0, settings.get("ysize") - 1)), settings.get("startinggold"), settings.get("playerC_choosecost"), settings.get("playerC_movecost")));
		players.add(new PlayerD(new Grid(new Point(settings.get("xsize") - 1, settings.get("ysize") - 1)), settings.get("startinggold"), settings.get("playerD_choosecost"), settings.get("playerD_movecost")));

		golds = new ArrayList<Gold>();

		goldPositionGenerator = new RandomGoldPositionGenerator(settings.get("xsize"), settings.get("ysize"));
		goldPositionGenerator.generate();

		pathFinder = new PathFinder();

		int goldCount = Math.round((settings.get("xsize") * settings.get("ysize")) * settings.get("goldpercent") / 100);
		goldCount = settings.get("goldpercent") == 100 ? goldCount - 4 : goldCount;

		Random random = new Random();
		for (int i = 0; i < goldCount; i++)
		{
			int goldAmount = 5 * (random.nextInt(4) + 1);
			if (i < goldCount * settings.get("hiddengoldpercent") / 100)
			{
				golds.add(new Gold(new Grid(goldPositionGenerator.get()), true, goldAmount));
			}
			else
			{
				golds.add(new Gold(new Grid(goldPositionGenerator.get()), false, goldAmount));
			}
		}

		JFrame frame = new JFrame();
		frame.setTitle("Yazlab 1 - 1");
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(66, 66, 66));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				//g2d.setStroke(new BasicStroke(2));
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				draw(g2d);
			}
		};

		frame.add(panel);
		frame.setVisible(true);

		final int[] playerIndex = {0};
		Timer timer = new Timer(50, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Player player = players.get(playerIndex[0]);

				if (player.target != null)
				{
					for (int i = 0; i < Math.min(player.pathToTarget.grids.size(), 3); i++)
					{
						player.move(player.pathToTarget.grids.remove(0));
						if (golds.contains(new Gold(player.grid)))
						{
							Gold gold = golds.get(golds.indexOf(new Gold(player.grid)));
							if (gold.hidden)
								gold.hidden = false;
							else
							{
								player.addGold(gold.amount);
								for (Player other: players)
								{
									if (player==other)
										continue;

									if (other.target==gold)
									{
										other.target = null;
										other.pathToTarget = null;
									}
								}
								if(gold==player.target)
								{
									player.target = null;
									player.pathToTarget = null;
									golds.remove(gold);
									break;
								}
								golds.remove(gold);
							}
						}
						if (player.getGoldAmount() <= 0)
						{
							Logger.log(player.name, "Bakiyesi tükendi, oyundan elendi.");
							players.remove(player);
							break;
						}
						panel.revalidate();
						panel.repaint();
					}
				}
				else
				{
					player.chooseTarget(golds);
					Path path = pathFinder.findPath(player.grid, player.target.grid);
					player.pathToTarget = path;
				}

				panel.revalidate();
				panel.repaint();

				if (players.size() == 1)
					((Timer) e.getSource()).stop();

				if (golds.size() == 0)
					((Timer) e.getSource()).stop();

				playerIndex[0] = (playerIndex[0] + 1) % players.size();
			}
		});
		timer.start();
	}

	public static Point getGridScreenPos(Point p)
	{
		return new Point(p.x * gridSize, p.y * gridSize);
	}

	public static Point getGridScreenPos(Grid g)
	{
		return new Point(g.position.x * gridSize, g.position.y * gridSize);
	}

	private void draw(Graphics2D g)
	{
		g.setColor(new Color(44, 44, 44));
		g.fillRect(0, 0, width, height);

		for (int x = 0; x < settings.get("xsize"); x++)
		{
			for (int y = 0; y < settings.get("ysize"); y++)
			{
				Point screenPos = getGridScreenPos(new Point(x, y));
				g.setColor(new Color(22, 22, 22));
				g.drawRect((int) screenPos.x, (int) screenPos.y, gridSize, gridSize);
				for (Player player : players)
				{
					if (player.target == null)
						continue;

					if (player.pathToTarget == null)
						continue;

					if (player.pathToTarget.grids.contains(new Grid(x, y)))
					{
						g.setColor(player.color);
						g.fillRect((int) screenPos.x, (int) screenPos.y, gridSize, gridSize);
					}
				}
			}
		}

		for (Player player : players)
		{
			player.draw(g);
		}

		for (Gold gold : golds)
		{
			gold.draw(g);
		}

		/*for (Player player : players)
		{
			if (player.target == null)
				continue;

			Gold target = player.target;

			Point plyScreenPos = getGridScreenPos(player.grid);
			Point goldScreenPos = getGridScreenPos(target.grid);

			g.setStroke(new BasicStroke(3));
			g.setColor(Color.white);
			g.drawLine((int) plyScreenPos.x + gridSize / 2, (int) plyScreenPos.y + gridSize / 2, (int) goldScreenPos.x + gridSize / 2, (int) goldScreenPos.y + gridSize / 2);
			g.setStroke(new BasicStroke(1));
		}*/
	}
}
