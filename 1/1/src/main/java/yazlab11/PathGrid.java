package yazlab11;

import yazlab11.game.Grid;

import java.util.Objects;

/**
 * A* algoritmasındaki bir düğümü temsil eden sınıf.
 * PriorityQueue listesine ekleyebilmek için Comparable sınıfından türüyor.
 */
public class PathGrid implements Comparable<PathGrid>
{
	private final Grid current;
	private Grid previous;
	private float pathScore;
	private float estimatedScore;

	/**
	 * PathGrid sınıfındaki 1. yapıcı metot.
	 * A* algoritmasında yeni bir grid keşfettiğimizde ilk olarak bu metot kullanılıyor.
	 *
	 * @param current düğümü oluşturulacak grid
	 */
	public PathGrid(Grid current)
	{
		this(current, null, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
	}

	/**
	 * PathGrid sınıfındaki 2. yapıcı metot.
	 *
	 * @param current        düğümü oluşturulacak grid
	 * @param previous       bu şehre hangi gridden geldiysek o grid
	 * @param pathScore      bu şehre geliş mesafesi
	 * @param estimatedScore bu gridden rotanın varış şehrine olan mesafe
	 *                       bunu A* algoritması varış şehri yönünde ilerlemek için kullanıyor
	 */
	public PathGrid(Grid current, Grid previous, float pathScore, float estimatedScore)
	{
		this.current = current;
		this.previous = previous;
		this.pathScore = pathScore;
		this.estimatedScore = estimatedScore;
	}

	/**
	 * Düğümleri PriorityQueue listesine ekleyebilmek için gereken metot.
	 * Bir düğümü diğerine göre karşılaştırmaya yarıyor.
	 * Düğümlerin rotanın varış şehrine olan mesafelerini karşılaştırıp
	 * ona göre bir dönüş yapıyor.
	 *
	 * @param other bu düğümün karşılaştırılacağı diğer düğüm
	 * @return diğer grid varış şehrine daha yakınsa 1, bu grid daha yakınsa -1, eşitlerse 0
	 */
	@Override
	public int compareTo(PathGrid other)
	{
		if (this.estimatedScore > other.estimatedScore)
			return 1;
		else if (this.estimatedScore < other.estimatedScore)
			return -1;
		else
			return 0;
	}

	public Grid getCurrent()
	{
		return current;
	}

	public Grid getPrevious()
	{
		return previous;
	}

	public void setPrevious(Grid previous)
	{
		this.previous = previous;
	}

	public float getPathScore()
	{
		return pathScore;
	}

	public void setPathScore(float pathScore)
	{
		this.pathScore = pathScore;
	}

	public float getEstimatedScore()
	{
		return estimatedScore;
	}

	public void setEstimatedScore(float estimatedScore)
	{
		this.estimatedScore = estimatedScore;
	}

	@Override
	public String toString()
	{
		return "PathGrid{" +
				"current=" + current +
				", previous=" + previous +
				'}';
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PathGrid pathGrid = (PathGrid) o;
		return Objects.equals(current, pathGrid.current);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(current);
	}
}
