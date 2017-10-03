import java.util.ArrayList;
import java.util.Iterator;

/**
 * The GroceryList class constructs our Grocery List that holds all of the
 * ingredients we have to use for recipe creation. It utilizes
 * ArrayList<Ingredients> as the internal data structure.
 * 
 *
 * <p>
 * Bugs: (no known bugs)
 *
 * @author (Jake DeCamp)
 */
public class GroceryList implements ListADT<Ingredient> {

	private ArrayList<Ingredient> groceryList = new ArrayList<Ingredient>();

	@Override
	public Iterator<Ingredient> iterator() {

		return groceryList.iterator();
	}

	/**
	 * (Write a succinct high-level description of this method here. If
	 * necessary, additional paragraphs should be preceded by
	 * <p>
	 * , the html tag for a new paragraph.)
	 *
	 * PRECONDITIONS: (i.e. the incoming list is assumed to be non-null)
	 * 
	 * POSTCONDITIONS: (i.e. the incoming list has been reordered)
	 *
	 * @param (parameter
	 *            name) (Describe the first parameter here)
	 * @param (parameter
	 *            name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	@Override
	public void add(Ingredient item) {

		groceryList.add(item);
	}

	@Override
	public void add(int pos, Ingredient item) {

		groceryList.add(pos, item);
	}

	@Override
	public boolean contains(Ingredient item) {

		return groceryList.contains(item);

	}

	@Override
	public int size() {

		return groceryList.size();

	}

	@Override
	public boolean isEmpty() {

		return groceryList.isEmpty();

	}

	@Override
	public Ingredient get(int pos) {

		return groceryList.get(pos);

	}

	@Override
	public Ingredient remove(int pos) {

		return groceryList.remove(pos);

	}

}
