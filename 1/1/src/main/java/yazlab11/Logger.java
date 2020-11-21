package yazlab11;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger
{
	public static void logPlayer(String playerName, String action)
	{
		String message = String.format("[%s oyuncusu] %s",playerName,action);
		System.out.println(message);
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("%s_oyuncusu_hareketleri.txt",playerName),true));
			writer.append(message);
			writer.newLine();
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void log(String text)
	{
		System.out.println(text);
	}
}
