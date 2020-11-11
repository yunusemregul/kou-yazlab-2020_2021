package yazlab11;

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
		settings.put("goldpercent", 20);
		settings.put("hiddengoldpercent", 10);

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

		final JLabel goldPercentLabel = new JLabel(String.format("Karelerin yüzde kaçında altın olacak: %%%d (%d tane)", settings.get("goldpercent"), settings.get("xsize") * settings.get("ysize") * settings.get("goldpercent") / 100));
		goldPercentLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

		final JLabel hiddenGoldPercentLabel = new JLabel(String.format("Altınların yüzde kaçı gizli olacak: %%%d (%d tane)", settings.get("hiddengoldpercent"), (settings.get("xsize") * settings.get("ysize") * settings.get("goldpercent") / 100) * settings.get("hiddengoldpercent") / 100));
		hiddenGoldPercentLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

		final JSlider goldPercent = new JSlider(0, 100, 20);
		goldPercent.setPaintLabels(true);
		goldPercent.setPaintTicks(true);
		goldPercent.setPaintTrack(true);
		goldPercent.setMajorTickSpacing(10);
		goldPercent.setMinorTickSpacing(1);
		goldPercent.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				settings.put("goldpercent", goldPercent.getValue());
				goldPercentLabel.setText(String.format("Karelerin yüzde kaçında altın olacak: %%%d (%d tane)", settings.get("goldpercent"), settings.get("xsize") * settings.get("ysize") * settings.get("goldpercent") / 100));
				hiddenGoldPercentLabel.setText(String.format("Altınların yüzde kaçı gizli olacak: %%%d (%d tane)", settings.get("hiddengoldpercent"), (settings.get("xsize") * settings.get("ysize") * settings.get("goldpercent") / 100) * settings.get("hiddengoldpercent") / 100));
			}
		});

		final JSlider hiddenGoldPercent = new JSlider(0, 100, 20);
		hiddenGoldPercent.setPaintLabels(true);
		hiddenGoldPercent.setPaintTicks(true);
		hiddenGoldPercent.setPaintTrack(true);
		hiddenGoldPercent.setMajorTickSpacing(10);
		hiddenGoldPercent.setMinorTickSpacing(1);
		hiddenGoldPercent.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				settings.put("hiddengoldpercent", hiddenGoldPercent.getValue());
				hiddenGoldPercentLabel.setText(String.format("Altınların yüzde kaçı gizli olacak: %%%d (%d tane)", settings.get("hiddengoldpercent"), (settings.get("xsize") * settings.get("ysize") * settings.get("goldpercent") / 100) * settings.get("hiddengoldpercent") / 100));
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
		sizeContainer.add(goldPercentLabel);
		sizeContainer.add(goldPercent);
		sizeContainer.add(hiddenGoldPercentLabel);
		sizeContainer.add(hiddenGoldPercent);
		sizeContainer.add(startButton);

		frame.add(sizeContainer);
		frame.pack();

		frame.setVisible(true);
	}
}
