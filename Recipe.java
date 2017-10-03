// DO NOT EDIT OR HANDIN THIS CLASS
import java.util.ArrayList;

public class Recipe{
    /**
     * 1. Name of a recipe is case sensitive.
     */
    private String recipeName;
    private ArrayList<Ingredient> ingredients;

    public Recipe(String recipeName, ArrayList<Ingredient> ingredients){
        this.recipeName = recipeName;
        this.ingredients = ingredients;
    }

    public void setRecipeName(String recipeName){
        this.recipeName = recipeName;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    public String getRecipeName(){
        return recipeName;
    }

    public ArrayList<Ingredient> getIngredients(){
        return ingredients;
    }
}
