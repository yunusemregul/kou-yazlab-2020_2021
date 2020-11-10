import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GameSettingsDrawer
{
	private final int width = 480;
	private final int height = 640;
	private HashMap<String, Integer> settings = new HashMap<String, Integer>();

	GameSettingsDrawer()
	{
		settings.put("xsize", 20);
		settings.put("ysize", 20);

		final JFrame frame = new JFrame();
		frame.setTitle("Yazlab 1 - 1 | Oyun Ayarları");
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(66, 66, 66));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel sizeContainer = new JPanel();
		sizeContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

		sizeContainer.setLayout(new BoxLayout(sizeContainer, BoxLayout.PAGE_AXIS));

		JLabel xSizeLabel = new JLabel("Yatay eksende kaç tane kare olacak:");
		final JSpinner xSize = new JSpinner();
		xSize.setValue(settings.get("xsize"));
		xSize.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				settings.put("xsize", (Integer) xSize.getValue());
			}
		});

		JLabel ySizeLabel = new JLabel("Dikey eksende kaç tane kare olacak:");
		ySizeLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
		final JSpinner ySize = new JSpinner();
		ySize.setValue(settings.get("ysize"));
		ySize.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				settings.put("ysize", (Integer) ySize.getValue());
			}
		});

		JButton startButton = new JButton("Başla");
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
				GameDrawer gameDrawer = new GameDrawer(settings);
			}
		});

		sizeContainer.add(xSizeLabel);
		sizeContainer.add(xSize);
		sizeContainer.add(ySizeLabel);
		sizeContainer.add(ySize);
		sizeContainer.add(startButton);

		frame.add(sizeContainer);
		frame.pack();

		frame.setVisible(true);
	}
}
