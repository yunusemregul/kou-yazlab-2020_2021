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
		settings.put("startinggold", 200);
		settings.put("movecount", 3);

		settings.put("playerA_choosecost", 5);
		settings.put("playerA_movecost", 5);

		settings.put("playerB_choosecost", 10);
		settings.put("playerB_movecost", 5);

		settings.put("playerC_choosecost", 15);
		settings.put("playerC_movecost", 5);

		settings.put("playerD_choosecost", 20);
		settings.put("playerD_movecost", 5);

		final JFrame frame = new JFrame();
		frame.setTitle("Yazlab 1 - 1 | Oyun Ayarları");
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(66, 66, 66));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel generalContainer = new JPanel();
		generalContainer.setLayout(new BoxLayout(generalContainer, BoxLayout.PAGE_AXIS));
		generalContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel sizeContainer = new JPanel();
		sizeContainer.setLayout(new BoxLayout(sizeContainer, BoxLayout.PAGE_AXIS));

		final JLabel goldPercentLabel = new JLabel(String.format("Karelerin yüzde kaçında altın olacak: %%%d (%d tane)", settings.get("goldpercent"), settings.get("xsize") * settings.get("ysize") * settings.get("goldpercent") / 100));
		goldPercentLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

		final JLabel hiddenGoldPercentLabel = new JLabel(String.format("Altınların yüzde kaçı gizli olacak: %%%d (%d tane)", settings.get("hiddengoldpercent"), (settings.get("xsize") * settings.get("ysize") * settings.get("goldpercent") / 100) * settings.get("hiddengoldpercent") / 100));
		hiddenGoldPercentLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

		JLabel xSizeLabel = new JLabel("Yatay eksende kaç tane kare olacak:");
		final JSpinner xSize = new JSpinner();
		xSize.setValue(settings.get("xsize"));
		xSize.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				settings.put("xsize", (Integer) xSize.getValue());
				goldPercentLabel.setText(String.format("Karelerin yüzde kaçında altın olacak: %%%d (%d tane)", settings.get("goldpercent"), settings.get("xsize") * settings.get("ysize") * settings.get("goldpercent") / 100));
				hiddenGoldPercentLabel.setText(String.format("Altınların yüzde kaçı gizli olacak: %%%d (%d tane)", settings.get("hiddengoldpercent"), (settings.get("xsize") * settings.get("ysize") * settings.get("goldpercent") / 100) * settings.get("hiddengoldpercent") / 100));
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
				goldPercentLabel.setText(String.format("Karelerin yüzde kaçında altın olacak: %%%d (%d tane)", settings.get("goldpercent"), settings.get("xsize") * settings.get("ysize") * settings.get("goldpercent") / 100));
				hiddenGoldPercentLabel.setText(String.format("Altınların yüzde kaçı gizli olacak: %%%d (%d tane)", settings.get("hiddengoldpercent"), (settings.get("xsize") * settings.get("ysize") * settings.get("goldpercent") / 100) * settings.get("hiddengoldpercent") / 100));
			}
		});

		final JSlider goldPercent = new JSlider(0, 100, settings.get("goldpercent"));
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

		final JSlider hiddenGoldPercent = new JSlider(0, 100, settings.get("hiddengoldpercent"));
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

		final JLabel startingGoldLabel = new JLabel("Oyuncuların başlangıç parası:");
		startingGoldLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

		final JSpinner startingGold = new JSpinner();
		startingGold.setValue(settings.get("startinggold"));
		startingGold.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				settings.put("startinggold", (Integer) ySize.getValue());
			}
		});

		final JLabel moveCount = new JLabel("1 hamledeki maksimum adım sayısı:");
		moveCount.setBorder(new EmptyBorder(10, 0, 0, 0));

		final JSpinner moveCountSpinner = new JSpinner();
		moveCountSpinner.setValue(settings.get("movecount"));
		moveCountSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				settings.put("movecount", (Integer) ySize.getValue());
			}
		});


		JButton startButton = new JButton("Başla");
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.setVisible(false);
				new GameDrawer(settings);
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
		sizeContainer.add(startingGoldLabel);
		sizeContainer.add(startingGold);
		sizeContainer.add(moveCount);
		sizeContainer.add(moveCountSpinner);

		JPanel playerASettings = generatePlayerSettingsPanel("A");
		JPanel playerBSettings = generatePlayerSettingsPanel("B");
		JPanel playerCSettings = generatePlayerSettingsPanel("C");
		JPanel playerDSettings = generatePlayerSettingsPanel("D");

		generalContainer.add(sizeContainer);
		generalContainer.add(playerASettings);
		generalContainer.add(playerBSettings);
		generalContainer.add(playerCSettings);
		generalContainer.add(playerDSettings);
		generalContainer.add(startButton);
		frame.add(generalContainer);
		frame.pack();

		frame.setVisible(true);
	}

	private JPanel generatePlayerSettingsPanel(final String name)
	{
		JPanel playerSettingsPanel = new JPanel();
		JLabel playerChooseCostLabel = new JLabel(String.format("Hedef seçme maliyeti:", name));
		final JSpinner playerChooseCostSpinner = new JSpinner();
		JLabel playerMoveCostLabel = new JLabel(String.format("Hamle maliyeti:", name));
		final JSpinner playerMoveCostSpinner = new JSpinner();

		playerSettingsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), String.format("%s oyuncusu için ayarlar", name)));
		playerSettingsPanel.setLayout(new BoxLayout(playerSettingsPanel, BoxLayout.PAGE_AXIS));

		playerChooseCostSpinner.setValue(settings.get(String.format("player%s_choosecost", name)));
		playerMoveCostSpinner.setValue(settings.get(String.format("player%s_movecost", name)));

		playerChooseCostSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				settings.put(String.format("player%s_choosecost", name), (Integer) playerChooseCostSpinner.getValue());
			}
		});

		playerMoveCostSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				settings.put(String.format("player%s_movecost", name), (Integer) playerMoveCostSpinner.getValue());
			}
		});

		playerSettingsPanel.add(playerChooseCostLabel);
		playerSettingsPanel.add(playerChooseCostSpinner);
		playerSettingsPanel.add(playerMoveCostLabel);
		playerSettingsPanel.add(playerMoveCostSpinner);

		return playerSettingsPanel;
	}
}
