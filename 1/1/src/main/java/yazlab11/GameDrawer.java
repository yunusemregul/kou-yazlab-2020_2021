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
	private final int width;
	private final int height;

	public static HashMap<String, Integer> settings;
	public static ArrayList<Player> activePlayers;
	public static ArrayList<Player> deadPlayers;
	public static ArrayList<Gold> golds;
	private RandomGoldPositionGenerator goldPositionGenerator;

	private JPanel panel;
	private PathFinder pathFinder;

	GameDrawer(HashMap<String, Integer> settings)
	{
		this.settings = settings;

		activePlayers = new ArrayList<>();
		deadPlayers = new ArrayList<>();
		activePlayers.add(new PlayerA(new Grid(new Point(0, 0)), settings.get("startinggold"), settings.get("playerA_choosecost"), settings.get("playerA_movecost")));
		activePlayers.add(new PlayerB(new Grid(new Point(settings.get("xsize") - 1, 0)), settings.get("startinggold"), settings.get("playerB_choosecost"), settings.get("playerB_movecost")));
		activePlayers.add(new PlayerC(new Grid(new Point(0, settings.get("ysize") - 1)), settings.get("startinggold"), settings.get("playerC_choosecost"), settings.get("playerC_movecost")));
		activePlayers.add(new PlayerD(new Grid(new Point(settings.get("xsize") - 1, settings.get("ysize") - 1)), settings.get("startinggold"), settings.get("playerD_choosecost"), settings.get("playerD_movecost")));

		golds = new ArrayList<>();

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

		width = gridSize * settings.get("xsize") + 17;
		height = gridSize * settings.get("ysize") + 40;

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
		Timer timer = new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Timer timer = ((Timer) e.getSource());
				Player player = activePlayers.get(playerIndex[0]);

				if (player.getGoldAmount() <= 0)
				{
					Logger.logPlayer(player.name, "Bakiyesi tükendi, oyundan elendi.");
					deadPlayers.add(player);
					activePlayers.remove(player);
					playerIndex[0] = (playerIndex[0] + 1) % activePlayers.size();
					return;
				}

				if (player.target == null)
				{
					player.chooseTarget(golds);

					if (player.target == null)
					{
						Logger.logPlayer(player.name, "Gidilebilecek hedef bulamadı.");
						playerIndex[0] = (playerIndex[0] + 1) % activePlayers.size();
						return;
					}

					if (player.grid.equals(player.target.grid))
					{
						Gold gold = checkGold(player);

						if (gold == player.target)
						{
							player.target = null;
							player.pathToTarget = null;
							playerIndex[0] = (playerIndex[0] + 1) % activePlayers.size();
							return;
						}
					}

					Path path = pathFinder.findPath(player.grid, player.target.grid);
					player.pathToTarget = path;
					if (player.getGoldAmount() <= 0)
					{
						Logger.logPlayer(player.name, "Bakiyesi tükendi, oyundan elendi.");
						deadPlayers.add(player);
						activePlayers.remove(player);
						playerIndex[0] = (playerIndex[0] + 1) % activePlayers.size();
						return;
					}
				}
				else
				{
					if (player.grid == player.target.grid)
					{
						Gold gold = checkGold(player);

						if (gold == player.target)
						{
							player.target = null;
							player.pathToTarget = null;
							playerIndex[0] = (playerIndex[0] + 1) % activePlayers.size();
							return;
						}
					}
				}

				panel.revalidate();
				panel.repaint();

				player.addGold(-player.moveCost, "Hamle");
				int pathToTargetDistance = player.pathToTarget.grids.size();
				for (int i = 0; i < Math.min(pathToTargetDistance, settings.get("movecount")); i++)
				{
					Grid gridToGo = player.pathToTarget.grids.remove(0);
					player.pathHasGone.grids.add(gridToGo);
					player.move(gridToGo);

					Gold gold = checkGold(player);

					if (gold == player.target)
					{
						player.target = null;
						player.pathToTarget = null;
						break;
					}

					if (player.getGoldAmount() <= 0)
					{
						Logger.logPlayer(player.name, "Bakiyesi tükendi, oyundan elendi.");
						deadPlayers.add(player);
						activePlayers.remove(player);
						panel.revalidate();
						panel.repaint();
						break;
					}
					panel.revalidate();
					panel.repaint();
				}

				panel.revalidate();
				panel.repaint();

				if (activePlayers.size() <= 1)
				{
					Logger.log(String.format("%s oyuncusu kazandı. Bakiyesi: %d", activePlayers.get(0).name, activePlayers.get(0).getGoldAmount()));
					endGameStats();
					timer.stop();
				}

				if (golds.size() == 0)
				{
					Logger.log("Oyun alanındaki altınlar tükendi.");
					endGameStats();
					timer.stop();
				}

				playerIndex[0] = (playerIndex[0] + 1) % activePlayers.size();
			}
		});
		timer.start();
	}

	public Gold checkGold(Player player)
	{
		if (golds.contains(new Gold(player.grid)))
		{
			Gold gold = golds.get(golds.indexOf(new Gold(player.grid)));
			if (gold.hidden)
				gold.hidden = false;
			else
			{
				player.addGold(gold.amount, "Altın toplama");
				for (Player other : activePlayers)
				{
					if (player == other)
						continue;

					if (other.target == gold)
					{
						other.target = null;
						other.pathToTarget = null;
					}
				}
				golds.remove(gold);
				return gold;
			}
		}
		return null;
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
				Grid thisGrid = new Grid(x, y);
				Point screenPos = getGridScreenPos(thisGrid);
				g.setColor(new Color(22, 22, 22));
				g.drawRect((int) screenPos.x, (int) screenPos.y, gridSize, gridSize);
				for (Player player : activePlayers)
				{
					if (player.pathHasGone.grids.contains(thisGrid))
					{
						g.setColor(new Color(player.color.getRed(), player.color.getGreen(), player.color.getBlue(), 100));
						g.fillRect((int) screenPos.x, (int) screenPos.y, gridSize, gridSize);

						for (int index = 0; index < player.pathHasGone.grids.size(); index++)
						{
							Grid grid = player.pathHasGone.grids.get(index);

							if (grid.equals(thisGrid) && (player.pathHasGone.grids.size() >= (index + 2)))
							{
								Point thisGridPos = getGridScreenPos(thisGrid);
								Point nextGridPos = getGridScreenPos(player.pathHasGone.grids.get(index + 1));

								thisGridPos.add(gridSize / 2, gridSize / 2);
								nextGridPos.add(gridSize / 2, gridSize / 2);

								g.setColor(player.color);
								g.drawLine((int) thisGridPos.x, (int) thisGridPos.y, (int) nextGridPos.x, (int) nextGridPos.y);
							}
						}
					}

					if (player.target == null)
						continue;

					if (player.pathToTarget == null)
						continue;

					if (player.pathToTarget.grids.contains(thisGrid))
					{
						g.setColor(new Color(player.color.getRed(), player.color.getGreen(), player.color.getBlue(), 50));
						g.fillRect((int) screenPos.x, (int) screenPos.y, gridSize, gridSize);
					}
				}
			}
		}

		for (Player player : activePlayers)
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

	private void endGameStats()
	{
		new GameStatsDrawer();
	}
}
