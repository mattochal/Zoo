import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Zookeeper {

	// Enclosures that the Zookeeper looks after
	private ArrayList<Enclosure> enclosures;
	private Zoo zoo;
	
	// This array stores the classes of animals that can be treated
	protected ArrayList<Class<? extends Animal>> canTreat = 
			new ArrayList<Class<? extends Animal>>(Arrays.asList(Lion.class, Tiger.class, Bear.class));
	
	// This is visible to all subclasses 
	protected StringBuilder detailStatus;

	// Constructor
	public Zookeeper(Enclosure enclosure) {
		enclosures = new ArrayList<Enclosure>();
		addEnclosure(enclosure);
		this.zoo = null;
		detailStatus = new StringBuilder();
	}

	public Zookeeper() {
		enclosures = new ArrayList<Enclosure>();
		this.zoo = null;
		detailStatus = new StringBuilder();
	}

	// Returns the Enclosure at index that the Zookeeper looks after
	public Enclosure getEnclosure(int index) {
		return enclosures.get(index);
	}

	// Adds the passed enclosure to the enclosure list
	public void addEnclosure(Enclosure enclosure) {
		enclosures.add(enclosure);
	}

	public boolean hasNoEnclosures() {
		return (enclosures.size() == 0);
	}

	public void setZoo(Zoo zoo) {
		this.zoo = zoo;
	}

	// Returns the Zoo that the Zookeeper belongs to
	public Zoo getZoo() {
		return this.zoo;
	}

	// The zookeeper does the monthly routine: removes waste, moves food to
	// enclosures and gives each animal a treat
	public void aMonthPasses() {
		detailStatus.setLength(0);
		removeWaste();
		moveFood();
		giveTreat();
	}
	
	// moves food to all enclosures
	public void moveFood(){
		for (Enclosure enclosure : enclosures){
			if (!enclosure.isEmpty()) {
				moveFood(enclosure);
			}
		}
	}
	
	// removes waste from all enclosures
	public void removeWaste(){
		for (Enclosure enclosure : enclosures){
			removeWaste(enclosure);
		}
	}
	
	// treats animals in all enclosures
	public void giveTreat(){
		for (Enclosure enclosure : enclosures){
			giveTreat(enclosure);
		}
	}

	// Moves food from Zoo Foodstore to Enclosure Foodstore
	/*
	 * The following while loop goes through each of the required food items
	 * UNTIL either the Zookeeper moves all the 20 items of food and can't move
	 * anymore or the foodRequirements have been met and there is not need to
	 * add anymore food to the enclosure
	 * 
	 * The foodRequirements are got from the method called getFoodRequirement()
	 * in the enclosure It is represented by a hashmap, with the key as a food
	 * item, and value being the number of animals in the enclosure that eat
	 * that food item
	 * 
	 */
	public void moveFood(Enclosure enclosure) {
		HashMap<String, Integer> requirements = enclosure.getFoodRequirement();
		
		int foodCount = 20; // up to 20 items

		// The requirement will be met if the maximum number of animals that
		// need to eat a particular item of food is 0 (or less)
		try {
			boolean requirementMet = (Collections.max(requirements.values()) <= 0);

			// The foodTakenFoodstore will be true if *any* of the food items
			// from the food requirements are moved successfully from the Zoo
			// Foodstore
			boolean foodTankenSuccessfully = true;

			while ((foodCount > 0) && (!requirementMet) && (foodTankenSuccessfully)) {
				foodTankenSuccessfully = false;
				for (String food : requirements.keySet()) {

					// The if statement here is to take care of that facts that:
					// 1. the Zookeeper may reach the limit of 20 before the end
					// of the for each loop
					// 2. the food requirements are supplied so there is no need
					// to reach the limit of 20
					// 3. there may not be enough food in the foodstore of the zoo
					if ((foodCount > 0) && (requirements.get(food) > 0) && (zoo.getFoodstore().takeFood(food))) {
						enclosure.getFoodstore().addFood(food, 1);
						requirements.put(food, requirements.get(food) - 1);
						foodCount--;
						detailStatus.append("moves " + 1 + " " + food + " to enclosure " + enclosure.getEnclosureID() + "; ");
						foodTankenSuccessfully = true;
					}
				}
				// Program checks if the max number of animals that want to eat
				// is 0 (or less)
				requirementMet = (Collections.max(requirements.values()) <= 0);
			}

			} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	// Removing up to 20 items of waste from the Enclosure
	public void removeWaste(Enclosure enclosure) {
		int removeMAX = 20;
		int wasteToRemove = enclosure.getWasteSize();
		
		detailStatus.append("removes " );
		if ( wasteToRemove > removeMAX ) {
			// The following line means: if (enclosure.removeWaste(removeMAX)) is carried out successfully append removeMAX to detailStatus else append 0
			detailStatus.append( (enclosure.removeWaste(removeMAX)? removeMAX : 0 ));
		} else {
			detailStatus.append( (enclosure.removeWaste(wasteToRemove)? wasteToRemove : 0 ));
		}
		detailStatus.append(" waste from Enclosure " + enclosure.getEnclosureID() + "; ");
	}

	/*
	 * Give a treat to up to 2 animals in the enclosure
	 * Select the first animals from the enclosure
	 * The Zookeeper only attempts to treat only 2 animals
	 */
	public void giveTreat(Enclosure enclosure) {
		Animal animal;
		int i = 0;
		int toTreat = 2;
		while ( (i < enclosure.size()) && (toTreat > 0) ) {
			animal = enclosure.getAnimalAt(i);
			if (canTreat.contains(animal.getClass())){
				detailStatus.append(" treats " + animal.getClass().getName()+ " in enclosure " + enclosure.getEnclosureID() + "; ");
				animal.treat();
				toTreat--;
			}
			i++;
		}
	}
	
	public StringBuilder getDetailStatus(){
		return new StringBuilder("ZOOKEEPER(" + this.getClass().getName() + ") : " + detailStatus);
	}
}