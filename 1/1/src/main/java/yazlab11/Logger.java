package yazlab11;

public class Logger
{
	public static void log(String playerName, String action)
	{
		System.out.println(String.format("[%s oyuncusu] %s",playerName,action));
	}
}