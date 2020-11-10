import com.sun.corba.se.impl.orbutil.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GameDrawer extends JPanel
{
	private final int width = 1366;
	private final int height = 768;
	private HashMap<String, Integer> settings;

	GameDrawer(HashMap<String, Integer> settings)
	{
		this.settings = settings;

		JFrame frame = new JFrame();
		frame.setTitle("Yazlab 1 - 1");
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(66, 66, 66));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(this);

		frame.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(new Color(44, 44, 44));
		g2d.fillRect(0, 0, width, height);

		g2d.setColor(Color.white);

		int squareSize = 20;
		for (int x = 0; x < settings.get("xsize"); x++)
		{
			for (int y = 0; y < settings.get("ysize"); y++)
			{
				g2d.drawRect(x * squareSize, y * squareSize, squareSize, squareSize);
			}
		}
	}
}
