import java.util.*;
import java.io.*;

/////////////////////////////////////////////////////////////////////////////
//Semester:         CS367 Fall 2017 
//PROJECT:          (P1)
//FILE:             (GroceryMatch)
//
//TEAM:    (individual)
//Author1: (Jake DeCamp)
//
//---------------- OTHER ASSISTANCE CREDITS 
//Persons: Identify persons by name, relationship to you, and email. 
//Describe in detail the the ideas and help they provided. 
//
//Online sources: avoid web searches to solve your problems, but if you do 
//search, be sure to include Web URLs and description of 
//of any information you find. 
////////////////////////////80 columns wide //////////////////////////////////
/**
 * This is the main class of p1. It provides the user interface and matching
 * algorithm to see if there are sufficient ingredients for each recipe.
 */
public class GroceryMatch {

	// DO NOT EDIT DATA MEMBERS (use where needed in GroceryMatch program)
	private static final String RECIPE_NAME_INPUT_PROMPT = "Please input recipe name";
	private static final String SERVING_NUMBER_INPUT_PROMPT = "Please input number of servings";
	private static final String RECIPE_READY = "Dish is ready";

	private static final String RECIPE_NAME_NOT_FOUND_ERROR_MSG = "Recipe not found";
	private static final String SERVING_NUMBER_INVALID_ERROR_MSG = "Please enter positive integer for number of servings";
	private static final String UNRECOGNIZED_COMMAND_ERROR_MSG = "Unrecognized command";

	private GroceryList groceries;
	private RecipeList recipes;

	/**
	 * Calculate what is the maximum number of servings of this recipe using
	 * current GroceryList. All ingredients must have enough for the maximum
	 * number of servings. The maximum number of servings is 0 if any ingredient
	 * is not available in sufficient quantity for one serving.
	 *
	 * For example: if an omelet requires 4 eggs and 1 milk and there are 10
	 * milk and 12 eggs in groceries, then the max servings of omelet recipes is
	 * 3
	 * 
	 * @param recipe
	 *            The recipe that you want to serve.
	 * @return The maximum number of servings, return 0 if unable to serve a
	 *         single serving.
	 */
	public Integer calcMaxNumServing(Recipe recipe) {

		ArrayList<Ingredient> recipeIngredients = recipe.getIngredients();

		ArrayList<Integer> servings = new ArrayList<Integer>();
		boolean match; // Flag used to determine whether the ingredient found a
						// matching ingredient in the grocery list

		// Loops through each ingredient in the recipe
		for (int i = 0; i < recipeIngredients.size(); i++)

		{
			match = false; // Initialize flag to false every integration to
							// prevent bugs

			// Loops through each ingredient in the grocery list
			for (int j = 0; j < groceries.size(); j++)

			{
				Ingredient item = recipeIngredients.get(i);

				// If a match was found, add the servings to the ArrayList
				if (item.getName().equalsIgnoreCase(groceries.get(j).getName())) {

					match = true; // ingredient was matched so flip the flag
					Double recipeQuantity = item.getQuantity();
					Double groceryQuantity = groceries.get(j).getQuantity();
					servings.add((int) (groceryQuantity / recipeQuantity));
				}
			}
			// If there was no match of ingredient, add 0 servings
			if (!match) {
				servings.add(0);
			}

		}
		// Utilizes the sort method in the Collections class that organizes
		// the ArrayList in ascending order
		Collections.sort(servings);
		// If no servings can be made, return zero
		if (servings.isEmpty()) {
			return 0;
		} else {
			return servings.get(0);
		}
	}

	/**
	 * This method is called when the desired number of servings is greater than
	 * maximum possible number of servings.
	 * 
	 * This method will print how many more ingredients need to be bought for
	 * the insufficient ingredients. For sufficient ingredients, do not print.
	 * One ingredient per line, format is "name: quantity", no leading or
	 * trailing spaces.
	 * 
	 * @param recipe
	 *            The recipe that you can not serve.
	 * @param numOfServing
	 *            The number of servings of the recipe.
	 */
	public void reportShortage(Recipe recipe, Integer numOfServing) {

		ArrayList<Ingredient> ingredients = recipe.getIngredients();

		double difference = 0;
		int numServings = numOfServing;

		// Loops through each ingredient in the recipe
		for (int i = 0; i < ingredients.size(); i++) {
			Ingredient recipeIngredient = ingredients.get(i);
			boolean foundIngredient = false;

			// Loops through each ingredient in the grocery list
			for (int j = 0; j < groceries.size(); j++) {
				Ingredient groceryItem = groceries.get(j);

				// If the ingredient matches
				if (recipeIngredient.getName().equalsIgnoreCase(groceryItem.getName())) {
					foundIngredient = true;
					// If the recipe times serving size requires more than
					// alloted ingredient
					if (groceryItem.getQuantity() < (recipeIngredient.getQuantity() * numServings)) {
						difference = (recipeIngredient.getQuantity() * numServings) - groceryItem.getQuantity();
						System.out.println(groceryItem.getName() + ": " + difference);
					}
				}
			}
			// If the grocery list does not contain the ingredient
			if (!foundIngredient) {
				System.out.println(recipeIngredient.getName() + ": " + recipeIngredient.getQuantity() * numServings);
			}
		}
	}

	/**
	 * Reduce the quantities in GroceryList since you have used them for
	 * serving.
	 * 
	 * @param recipe
	 *            The recipe you are serving.
	 * @param numOfServing
	 *            The number of servings of the recipe.
	 */
	public void updateGroceries(Recipe recipe, Integer numOfServing) {

		ArrayList<Ingredient> ingredients = recipe.getIngredients();
		double updatedQuant = 0.0;
		int numServings = numOfServing;

		// Loops through each ingredient in the recipe
		for (int i = 0; i < ingredients.size(); i++) {
			Ingredient recipeIngredient = ingredients.get(i);

			// Loops through each ingredient in the grocery list
			for (int j = 0; j < groceries.size(); j++) {
				Ingredient groceryItem = groceries.get(j);

				// If there is a used ingredient, deduct the amount used
				if (recipeIngredient.getName().equalsIgnoreCase(groceryItem.getName())) {
					updatedQuant = groceryItem.getQuantity() - (recipeIngredient.getQuantity() * numServings);
					groceryItem.setQuantity(updatedQuant);
					break;
				}
			}
		}
	}

	/**
	 * Handle the command when you try to (U)se a recipe. Input the recipe name
	 * and the number of servings, see if it is able to serve using the current
	 * GroceryList. (1) If able to serve, update the quantities in GroceryList,
	 * and print a serving success message. (2) If unable to serve, do not
	 * update the quantities in GroceryList, and do print the ingredients need
	 * to be bought.
	 * 
	 * @param stdin
	 *            The scanner for input.
	 */
	public void handleUse(Scanner stdin) {

		System.out.println(RECIPE_NAME_INPUT_PROMPT);
		boolean validInput = false;
		String recipeName = stdin.nextLine().trim().toLowerCase();
		Recipe recipe = null;
		int servings;

		// Loop through each recipe in the list
		for (int i = 0; i < recipes.size(); i++) {
			// if the recipe read from the user matches
			if (recipes.get(i).getRecipeName().equalsIgnoreCase(recipeName)) {
				validInput = true;
				recipe = recipes.get(i);
				break;
			}
		}

		// If the recipe read from the user does not exist
		if (!validInput) {
			System.out.println(RECIPE_NAME_NOT_FOUND_ERROR_MSG);
			return;
		}

		// Get number of servings from user input (positive integer only)
		System.out.println(SERVING_NUMBER_INPUT_PROMPT);
		try {
			servings = Integer.parseInt(stdin.nextLine());
			// Handles negative serving amount
			if (servings <= 0) {
				System.out.println(SERVING_NUMBER_INVALID_ERROR_MSG);
				return;
			}
		} catch (Exception e) {
			System.out.print(SERVING_NUMBER_INVALID_ERROR_MSG);
			return;
		}

		// If the user asks for more servings than the ingredients can make
		if (calcMaxNumServing(recipe) < servings) {
			reportShortage(recipe, servings);
		} else {
			System.out.println(RECIPE_READY);
			updateGroceries(recipe, servings);
		}
	}

	/**
	 * Print all the ingredient names and quantities in a GroceryList. One
	 * ingredient each line. Do not sort the ingredients. Display in the order
	 * added in the list.
	 *
	 * name1: quantity1 name2: quantity2 name3: quantity3 name4: quantity4
	 * 
	 * @param groceries
	 *            The GroceryList you want to print.
	 */
	public static void print(GroceryList groceries) {

		// Prints each ingredient in the grocery list
		Iterator<Ingredient> iterate = groceries.iterator();
		while (iterate.hasNext()) {
			Ingredient ing = iterate.next();
			System.out.print(ing.getName() + ": ");
			System.out.printf("% 4f", ing.getQuantity());
			System.out.print("\n");
		}
	}

	/**
	 * Print all the recipes in a RecipeList. One recipe each line. Do not sort
	 * the recipes. Display recipes in the order they were added to the list.
	 * Display ingredients in the order they were added to the recipe's
	 * ingredients.
	 * 
	 * Output Format Example: omelet -> milk: 1, eggs: 4 recipeName1 ->
	 * ingredient1: quantity1, ingredient2: quantity2, ... recipeName2 ->
	 * ingredient1: quantity1, ingredient2: quantity2, ...
	 * 
	 * @param recipes
	 *            The RecipeList that contains the recipes that you want to
	 *            print.
	 */
	public static void print(RecipeList recipes) {
		Iterator<Recipe> iterate = recipes.iterator();
		// Leafs through the RecipeList while there
		// are additional recipes
		while (iterate.hasNext()) {
			Recipe o = iterate.next();
			String recipeTitle = o.getRecipeName();
			ArrayList<Ingredient> q = o.getIngredients();
			System.out.print(recipeTitle + "-> ");

			// Leafs through the ArrayList of ingredients
			for (int i = 0; i < q.size(); i++) {
				Ingredient ing = q.get(i);
				System.out.print(ing.getName() + ": " + ing.getQuantity());

				// Prints comma's after each ingredient except the last
				// ingredient
				if (!(i == (q.size() - 1))) {
					System.out.print(", ");
				}
			}
			System.out.print("\n");
		}
	}

	/**
	 * DO NOT EDIT THIS METHOD Handle the command when you try to show how many
	 * servings are possible. For each recipe in RecipeList, print the maximum
	 * number of servings using the current GroceryList. One recipe per line,
	 * format is "recipe-name: max-num-of-serving", no leading or trailing
	 * spaces. DO NOT EDIT THIS METHOD
	 */
	public void handleShow() {
		Iterator<Recipe> itr = recipes.iterator();
		while (itr.hasNext()) {
			Recipe recipe = itr.next();
			Integer maxNumServing = calcMaxNumServing(recipe);
			System.out.println(String.format("%s: %d", recipe.getRecipeName(), maxNumServing));
		}
	}

	/**
	 * DO NOT EDIT THIS METHOD Main loop of GroceryMatch. The main loop accept
	 * input commands, execute them, and print results. The main loop accepts
	 * three types of commands:
	 * 
	 * (1) q : Save current groceries to file and quit the program.
	 * 
	 * (2) s : For all recipes, show how many servings are possible using the
	 * current GroceryList.
	 * 
	 * (3) u : Use a recipe.
	 * 
	 * For other commands, print UNRECOGNIZED_COMMAND_ERROR and ignore. DO NOT
	 * EDIT THIS METHOD
	 * 
	 * @param stdin
	 *            The Scanner for input.
	 */
	public void mainLoop(Scanner stdin) {
		String command = "";
		while (!command.equalsIgnoreCase("q")) {
			System.out.println("(s)ervings, (u)se, (q)uit? ");
			command = stdin.nextLine().trim();
			switch (command) {
			case "s":
				handleShow();
				break;
			case "u":
				handleUse(stdin);
				break;
			case "q":
				Loaders.write(groceries, Loaders.OUTPUT_FILENAME);
				break;
			default:
				System.out.println(UNRECOGNIZED_COMMAND_ERROR_MSG);
			}
		}
	}

	/**
	 * DO NOT EDIT THIS METHOD This method will initialize groceries and
	 * recipes. Return false when there is IOException.
	 * 
	 * @param groceryFile
	 *            filename of groceries
	 * @param recipeFile
	 *            filename of recipes
	 * @return Return true if GroceryList and RecipeList are successfully
	 *         loaded. Return false if there are Exceptions.
	 */
	public Boolean initialize(String groceryFile, String recipeFile) {
		try {
			groceries = Loaders.loadGroceriesFromFile(groceryFile);
			print(groceries);
			recipes = Loaders.loadRecipesFromFile(recipeFile);
			print(recipes);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/** DO NOT EDIT CONSTRUCTOR */
	public GroceryMatch() {
		groceries = new GroceryList();
		recipes = new RecipeList();
	}

	/**
	 * DO NOT EDIT THIS METHOD The main method initializes the GroceryMatch
	 * object and call the initialize method before starting the main menu loop.
	 */
	public static void main(String[] args) throws IOException {
		Scanner stdin = new Scanner(System.in);
		GroceryMatch gm = new GroceryMatch();
		try {
			if (!gm.initialize(args[0], args[1])) {
				return;
			}
			gm.mainLoop(stdin);
		} catch (Exception e) {
			System.out.println("Usage: java GroceryMatch ingredientFileName recipeFileName");
		}
	}
}
