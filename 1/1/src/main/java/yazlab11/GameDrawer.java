package yazlab11;

import yazlab11.game.Gold;
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

	private HashMap<String, Integer> settings;
	private ArrayList<Player> players;
	private ArrayList<Gold> golds;
	private RandomGoldPositionGenerator goldPositionGenerator;

	private JPanel panel;

	GameDrawer(HashMap<String, Integer> settings)
	{
		this.settings = settings;

		players = new ArrayList<Player>();
		players.add(new PlayerA(new Point(0, 0), settings.get("playerA_choosecost"), settings.get("playerA_movecost")));
		players.add(new PlayerB(new Point(settings.get("xsize") - 1, 0), settings.get("playerB_choosecost"), settings.get("playerB_movecost")));
		players.add(new PlayerC(new Point(0, settings.get("ysize") - 1), settings.get("playerC_choosecost"), settings.get("playerC_movecost")));
		players.add(new PlayerD(new Point(settings.get("xsize") - 1, settings.get("ysize") - 1), settings.get("playerD_choosecost"), settings.get("playerD_movecost")));

		golds = new ArrayList<Gold>();

		goldPositionGenerator = new RandomGoldPositionGenerator(settings.get("xsize"), settings.get("ysize"));
		goldPositionGenerator.generate();

		int goldCount = Math.round((settings.get("xsize") * settings.get("ysize")) * settings.get("goldpercent") / 100);
		goldCount = settings.get("goldpercent") == 100 ? goldCount - 4 : goldCount;

		Random random = new Random();
		for (int i = 0; i < goldCount; i++)
		{
			int goldAmount = 5 * (random.nextInt(4) + 1);
			if (i < goldCount * settings.get("hiddengoldpercent") / 100)
			{
				golds.add(new Gold(goldPositionGenerator.get(), true, goldAmount));
			}
			else
			{
				golds.add(new Gold(goldPositionGenerator.get(), false, goldAmount));
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

		Timer timer = new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (players.size() > 0)
				{
					for (Player player : players)
					{
						if(player.target!=null)
							continue;

						player.chooseTarget(golds);
						panel.revalidate();
						panel.repaint();

						break;
					}
				}
			}
		});
		timer.start();
	}

	public static Point getGridScreenPos(Point p)
	{
		return new Point(p.x * gridSize, p.y * gridSize);
	}

	private void draw(Graphics2D g)
	{
		g.setColor(new Color(44, 44, 44));
		g.fillRect(0, 0, width, height);

		g.setColor(new Color(22, 22, 22));

		for (int x = 0; x < settings.get("xsize"); x++)
		{
			for (int y = 0; y < settings.get("ysize"); y++)
			{
				Point screenPos = getGridScreenPos(new Point(x, y));
				g.drawRect((int) screenPos.x, (int) screenPos.y, gridSize, gridSize);
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

		for (Player player : players)
		{
			if (player.target==null)
				continue;

			Gold target = player.target;

			Point plyScreenPos = getGridScreenPos(player.grid);
			Point goldScreenPos = getGridScreenPos(target.grid);

			g.setStroke(new BasicStroke(3));
			g.setColor(Color.white);
			g.drawLine((int) plyScreenPos.x + gridSize / 2, (int) plyScreenPos.y + gridSize / 2, (int) goldScreenPos.x + gridSize / 2, (int) goldScreenPos.y + gridSize / 2);
			g.setStroke(new BasicStroke(1));
		}
	}
}
