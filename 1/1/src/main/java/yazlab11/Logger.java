package yazlab11;

public class Logger
{
	public static void logPlayer(String playerName, String action)
	{
		System.out.println(String.format("[%s oyuncusu] %s",playerName,action));
	}

	public static void log(String text)
	{
		System.out.println(text);
	}
}
