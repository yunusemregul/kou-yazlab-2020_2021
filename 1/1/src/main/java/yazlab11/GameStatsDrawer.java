package yazlab11;

import yazlab11.game.players.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class GameStatsDrawer
{
	private final int width = 640;
	private final int height = 480;

	GameStatsDrawer()
	{
		final JFrame frame = new JFrame();
		frame.setTitle("Yazlab 1 - 1 | Oyun İstatistiği");
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(66, 66, 66));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Vector<String> columnNames = new Vector<>();
		columnNames.add("Oyuncu");
		columnNames.add("Adım Sayısı");
		columnNames.add("Harcanan Altın");
		columnNames.add("Kasadaki Altın");
		columnNames.add("Toplanan Altın");

		Vector<Vector> data = new Vector<>();

		for (Player player:GameDrawer.activePlayers)
		{
			data.add(player.generateStatsVector());
		}

		for (Player player:GameDrawer.deadPlayers)
		{
			data.add(player.generateStatsVector());
		}

		JTable table = new JTable(data, columnNames);

		frame.add(new JScrollPane(table));
		frame.pack();
		frame.setVisible(true);
	}
}
