import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This class provides the file reader methods for reading ingredient data files
 * and recipe data files for the GroceryMatch program. Do not change method
 * signatures. Do implement the missing method bodies.
 */
public class Loaders {

	// DO NOT CHANGE THESE CLASS CONSTANTS
	public static final String GROCERY_FILE_IO_ERROR_MSG = "IOError when loading grocery lists";
	public static final String RECIPE_FILE_IO_ERROR_MSG = "IOError when loading recipes";
	public static final String OUTPUT_FILENAME = "remaining.txt";

	/**
	 * 1. Load groceries from file, each line of the file indicates an
	 * ingredient and its quantity. 2. Each ingredient is in the format of "name
	 * : quantity", the number of spaces between name, colon and quantity can be
	 * any. And there may be leading and trailing spaces in a line. 3. Name of
	 * ingredient may have duplicate, this means there may be multiple lines
	 * with the same ingredient name. If names are duplicated, their quantities
	 * should be summed up. 4. If a line does not match the above mentioned
	 * format, ignore the line and continue reading the next line of
	 * ingredients. 5. If an IOException happens, print
	 * GROCERY_FILE_IO_ERROR_MSG, and throw the exception.
	 * 
	 * @param filename
	 *            The name of the file that contains the list of ingredients for
	 *            the grocery.
	 * @return A grocery list that includes all of the ingredients that were
	 *         were properly read from the file.
	 * @throws IOException
	 *             if the filename does not exist, the error msg is displayed
	 *             and the exception is thrown to calling method
	 */
	public static GroceryList loadGroceriesFromFile(String filename) throws IOException {

		try {
			File ingredientList = new File(filename);
			Scanner scan = new Scanner(ingredientList);
			GroceryList list = new GroceryList();
			String input;
			String[] ingredientInfo;
			String ingredientName;
			Double quantity;
			Ingredient tempIngredient;

			// Scans every line of the file
			while (scan.hasNextLine()) {
				input = scan.nextLine();
				ingredientInfo = input.split(":");

				// Continue's out of the while loop if invalid data
				if (ingredientInfo.length != 2) {
					continue;
				}
				ingredientName = ingredientInfo[0].trim().toLowerCase();

				// Handles exception if the value read is not a double
				try {
					quantity = Double.parseDouble(ingredientInfo[1].trim());

					// Discards ingredients with negative quantity
					if (quantity < 0) {
						continue;
					}
				} catch (NumberFormatException e) {
					continue;
				}
				tempIngredient = new Ingredient(ingredientName, quantity);
				boolean flag = true;

				// For each ingredient in the grocery list
				for (int i = 0; i < list.size(); i++) {
					Ingredient e = list.get(i);

					// If there is a duplicate ingredient, sum the quantity
					if (e.getName().equals(tempIngredient.getName())) {
						e.setQuantity(quantity + e.getQuantity());
						flag = false;
					}
				}
				// Flag prevents adding duplicate ingredients
				if (flag == true) {
					list.add(tempIngredient);
				}
			}
			return list;
		} catch (FileNotFoundException e) {
			System.out.print(GROCERY_FILE_IO_ERROR_MSG);
			throw new IOException();
		}
	}

	/**
	 * 1. Load recipes from file, each line of the file indicates a recipe. 2.
	 * Each recipe is in the format "name -> ingredient1-name:
	 * ingredient1-quantity, ingredient2-name: ingredient2-quantity" 3. The
	 * number of ingredients in a recipe can be any. 4. The number of spaces
	 * between name and quantity can be any, and there may be leading and
	 * trailing spaces. 5. For simplicity, names of recipes will not have
	 * duplication, names of ingredients in a recipe will not have duplication,
	 * the format of the recipe is guaranteed to be correct. 6. Names of
	 * ingredients might not be in GroceryList, this means you need to buy this
	 * ingredient if you want to use this recipe. 7. If an IOException happens,
	 * print RECIPE_FILE_IO_ERROR_MSG, and throw the exception.
	 * 
	 * @param filename
	 *            The name of a file containing recipe information.
	 * @return A recipe list containing the recipes read from the named file.
	 * @throws IOException
	 *             if the filename does not exist, the error msg is displayed
	 *             and the exception is thrown to calling method
	 */
	public static RecipeList loadRecipesFromFile(String filename) throws IOException {

		try {
			RecipeList recipeList = new RecipeList();
			File recipes = new File(filename);
			Scanner scnr = new Scanner(recipes);
			String line = null;
			String[] array1 = null;
			String[] array2;
			String recipeTitle;
			Recipe newRecipe = null;

			// While there are more lines of data to
			while (scnr.hasNextLine())

			{
				ArrayList<Ingredient> recipeContents = new ArrayList<Ingredient>();
				line = scnr.nextLine();

				/*
				 * Splits the string of data at the "->" character sequence and
				 * places the tokens into the string array(array1). Array2 now
				 * holds the ingredients for each recipe and their quantity
				 * (name: quantity)
				 */
				array1 = line.split("->");
				recipeTitle = array1[0].trim();
				array2 = array1[1].split(",");

				/*
				 * Leafs through each ingredient in the recipe utilizes the
				 * split method to separate the ingredient info from the
				 */
				for (int i = 0; i < array2.length; i++) {
					String[] tempHold;
					tempHold = array2[i].split(":");
					String ingredientName = tempHold[0].trim();
					Double quantity = Double.parseDouble(tempHold[1].trim());
					Ingredient ingredient = new Ingredient(ingredientName, quantity);
					recipeContents.add(ingredient);
				}

				// Create a new recipe object and add it to the recipe list
				newRecipe = new Recipe(recipeTitle, recipeContents);
				recipeList.add(newRecipe);
			}
			return recipeList;
		} catch (FileNotFoundException e) {
			System.out.print(RECIPE_FILE_IO_ERROR_MSG);
			throw new IOException();
		}
	}

	/**
	 * Write the GroceryList items to the specified file.
	 *
	 * Each ingredient is written to the file in the order that the ingredient
	 * is found in the GrocerList the format for each line is:
	 *
	 * ingredient_name: amount
	 *
	 * @param grocery
	 *            list of ingredients
	 * @param name
	 *            of the file to write them to.
	 */
	public static void write(GroceryList groceries, String filename) {
		GroceryList list = groceries;
		PrintWriter pw = null;

		try {
			pw = new PrintWriter(new File(filename));
			// For each ingredient in the grocery list
			for (int i = 0; i < list.size(); i++) {
				pw.println(list.get(i).getName() + ": " + list.get(i).getQuantity());
			}
			pw.close(); // Close stream to prevent resource leak
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
