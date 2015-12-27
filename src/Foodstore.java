import java.util.HashMap;
import java.util.Set;

// FoodStore class
public class Foodstore {

	/*--- STATIC FIELDS and METHODS ---*/
	
	// Stores the health points given by each food
	// static because stores values common to each instance of FoodStore
	// could be implemented as a HashTable, but apparently it is slower and not necessary if not doing multi-threading
	private static HashMap<String, Integer> foodHealth =  new HashMap<String, Integer>() {{
		put("hay", 1);
		put("steak", 4);
		put("fruit", 2);
		put("celery", 0);
		put("fish", 3);
		put("ice cream", 1);
	}};
	
	// Stores the waste associated with each type of food 
	// static because stores values common to each instance of FoodStore
	private static HashMap<String, Integer> foodWaste = new HashMap<String, Integer>() {{
		put("hay", 4);
		put("steak", 4);
		put("fruit", 3);
		put("celery", 1);
		put("fish", 2);
		put("ice cream", 3);
	}};
	
	// Gets the set of ALL food items possible
	public static Set<String> getFoodSet(){
		return foodHealth.keySet();
	}
	
	// Returns the health value of the food
	public static int getFoodHealth(String food){
		return foodHealth.get(food);
	}
	
	// Returns the waste value of the food
	public static int getFoodWaste(String food){
		return foodWaste.get(food);
	}
	
	/*--- NON STATIC FIELDS and METHODS ---*/
	
	// Stores the amount of food in the food store
	// not static because stores values that may be different from other instances of FoodStore
	private HashMap<String, Integer> foodQuantity;
	
	public Foodstore(){
		foodQuantity = new HashMap<String, Integer>();
		resetFoodQuantity();
	}
	
	// Empties all the food in the food store
	// Assumes that foodHeath and foodQuantity are in sync
	public void resetFoodQuantity(){
		for (String key : Foodstore.foodHealth.keySet()){
			foodQuantity.put(key, 0);
		}
	}	
	
	// Returns true if the loop is empty
	public boolean isEmpty(){
		// Sums the food quantity of each food and if the sum is zero return true
		int sum = 0;
		for (String food : foodQuantity.keySet()){
			sum += foodQuantity.get(food);
		}
		
		if (sum <= 0){
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Add food to the FoodStore with the quantity specified
	 * Throws an exception if the adding a negative number to the food store.
	 * Throws an exception if the food is not in the FoodHealth HashMap
	 */
	public void addFood(String food, int quantity) throws Exception{
		// Making sure the quantity is a positive number
		if (!foodHealth.containsKey(food)) {
			throw new Exception(food + " as a type of food does not exist!");
		}
		
		if (quantity < 0) {
			throw new Exception("Adding negative quantity to food store: " + quantity);
		}
		
		int iniQuantity = foodQuantity.get(food); // initial quantity
		foodQuantity.put(food, iniQuantity + quantity);
	}
	
	// Subtract a unit of food from the FoodStore
	// Returns false if the food supply run out
	public boolean takeFood(String food){
		if (foodQuantity.get(food) <= 0){
			return false;
		} else {
			foodQuantity.put(food, foodQuantity.get(food) - 1);
			return true;
		}
	}
	
	// It returns the quantity of a food item the foodstore contains
	public int foodAvailable(String food){
		return foodQuantity.get(food);
	}
	
	public StringBuilder getFoodstoreStatus(){
		StringBuilder status = new StringBuilder("FOODSTORE: ");
		status.append("\t");
		for (String food : foodQuantity.keySet()){
			status.append(" " + food + "(" + foodQuantity.get(food) + ") ");
		}
		return status;
	}
}