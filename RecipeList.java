import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * The RecipeList class constructs a list of recipes that holds all of the
 * recipes we can make. It utilizes an ArrayList<Recipe> as the internal data
 * structure.
 * 
 * <p>
 * Bugs: (No known bugs)
 *
 * @author (Jake DeCamp)
 */
public class RecipeList implements ListADT<Recipe> {

	private ArrayList<Recipe> recipe = new ArrayList<Recipe>();

	@Override
	public Iterator<Recipe> iterator() {

		return recipe.iterator();

	}

	@Override
	public void add(Recipe item) {

		recipe.add(item);

	}

	@Override
	public void add(int pos, Recipe item) {

		recipe.add(pos, item);
	}

	@Override
	public boolean contains(Recipe item) {

		return recipe.contains(item);

	}

	@Override
	public int size() {

		return recipe.size();

	}

	@Override
	public boolean isEmpty() {

		return recipe.isEmpty();

	}

	@Override
	public Recipe get(int pos) {

		return recipe.get(pos);

	}

	@Override
	public Recipe remove(int pos) {

		return recipe.remove(pos);

	}

}
