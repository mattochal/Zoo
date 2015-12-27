import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Enclosure {
	
	// Each enclosures store an ArrayList of animals
	// I think an ArrayList is a better choice than an Array because the size can be easily changed
	private ArrayList<Animal> animals;
	
	private Foodstore foodstore; // each enclosure has one food store
	private int enclosureID; // each enclosure has an ID for the user to identify enclosures
	private int waste; // amount of waste in the Enclosure
	private StringBuilder detailStatus; // stores details of what the animal does for user's informations
	// Declaring as protected because there may be subclasses in the future which could make use of the detailStatus 

	public Enclosure() {
		animals = new ArrayList<Animal>();
		foodstore = new Foodstore();
		detailStatus = new StringBuilder();
		waste = 0;
	}

	/*
	 *  Adds an animal to the Enclosure
	 *  Returns true if the animal has been successfully added to the enclosure
	 */
	public boolean addAnimal(Animal animal) {
		if ( this.size() < 20) {
			animals.add(animal);
			animal.setEnclosure(this);
			detailStatus.append("animal added to enclosure; ");
			return true;
		} else {
			detailStatus.append("enclosure full; ");
			return false;
		}
	}

	// Removes the specified Animal from the Enclosure and deletes the reference from the animal to the enclosure
	public void removeAnimal(Animal animal) {
		animal.setEnclosure(null);
		animals.remove(animal);
	}

	/*
	 *  Removes the specified amount of waste from the Enclosure.
	 *  Quantity must be positive and
	 */
	public boolean removeWaste(int quantity) {

		// If quantity is negative return false
		if (quantity < 0)
			return false;

		// If trying to take more waste out than there is waste
		else if (quantity > waste)
			waste = 0;
		
		else
			waste -= quantity;
		
		return true;
	}

	// Adds the specified amount of waste to the Enclosure
	public void addWaste(int quantity) {
		waste += quantity;
	}

	// Returns the amount of waste in the Enclosure.
	public int getWasteSize() {
		return waste;
	}

	// Returns the Foodstore that belongs to the Enclosure
	public Foodstore getFoodstore() {
		return foodstore;
	}

	// Returns true if no animals in the enclosure present
	public boolean isEmpty() {
		return (size() == 0);
	}

	// Returns the number of Animals in the Enclosure.
	public int size() {
		return animals.size();
	}

	public int getEnclosureID() {
		return enclosureID;
	}

	public void setEnclosureID(int iD) {
		enclosureID = iD;
	}

	/* 
	 * Calls aMonthPasses on each of the Animals 
	 * and counts the number of females and males in the enclosure so the animals can breed
	 */
	public boolean aMonthPasses() {
		// Clears the StringBuilder
		detailStatus.setLength(0);
		
		// Used to check if the animals is alive after the passed month
		boolean animalAlive;

		// These ArrayLists are used store the classes of male and female animals that inherit from Animal
		// These will be passed on the the mateAnimals() function
		ArrayList<Class<? extends Animal>> maleAnimals = new ArrayList<Class<? extends Animal>>();
		ArrayList<Class<? extends Animal>> femaleAnimals = new ArrayList<Class<? extends Animal>>();
		
		// Iterator is created so that dead animals can be removed safely from the animal ArrayList
		Iterator<Animal> itrAnimal = animals.iterator();

		// Calls aMonthPasses on each of the Animals and removing dead ones
		while (itrAnimal.hasNext()) {
			Animal animal = itrAnimal.next();
			animalAlive = animal.aMonthPasses();

			if (!animalAlive) {
				detailStatus.append(animal.getClass().getName() + "(" + animal.getGender() + ") dies at "+ animal.getAge() +"; ");
				itrAnimal.remove();
			} else {
				if (animal.getGender()=='m'){
					maleAnimals.add(animal.getClass());
				} else {
					femaleAnimals.add(animal.getClass());
				}
			}
		}
		
		// If there is at least one female and at least one male animal in the array lists attempt to breed animals
		if ((femaleAnimals.size() > 0 ) && (maleAnimals.size() > 0)) {
			breedAnimals(maleAnimals, femaleAnimals);
		}
		
		// If the Enclosure is empty after the month return false
		return !this.isEmpty();
	}
	
	/*
	 * Breeding animals.
	 * breedAnimals() takes lists of male and female animals and uses a nested loops to go through each animal.
	 * If there exists a pair of a male and female animal that are of the same type there is a 1 in 100 chance that a new animal is born
	 * */
	private void breedAnimals(ArrayList<Class<? extends Animal>> maleAnimals, ArrayList<Class<? extends Animal>> femaleAnimals){
		for (Class<? extends Animal> maleAnimalClass : maleAnimals){
			for (Class<? extends Animal> femaleAnimalClass : femaleAnimals){
				if (maleAnimalClass.isAssignableFrom(femaleAnimalClass)) {
					
					Random rand = new Random();
					int number = rand.nextInt(1000);
					//There is a 0.01 chance that a new animal is born
					if (number < 10){
						try {
							// There is a 50% chance that the newborn is male or female
							char gender = ( (number % 2 == 0 )? 'm' : 'f' );
							Animal newAnimal = maleAnimalClass.getConstructor(char.class, int.class, int.class).newInstance(gender,0,10);
							// If the animal gets added successfully or it there are too many animals
							detailStatus.append( "new " + newAnimal.getClass().getName() + " is born; " );
							if ( addAnimal(newAnimal) ) {
								detailStatus.append( "sucessfully added; ");
							} else {
								detailStatus.append( "failed to add; ");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}	

	// Puts together and returns the HashMap of food items that the animal in
	// this enclosure can eat
	// with the number of animals in the enclosure that eat that particular food
	// item
	// This is will be used by the Zookeepers to know which food to
	public HashMap<String, Integer> getFoodRequirement() {
		HashMap<String, Integer> foodRequirement = new HashMap<String, Integer>();

		// For each animal in the enclosure
		for (Animal a : animals) {
			// For each food the animal eats
			for (String food : a.getEatsArray()) {
				// If the hashmap doesn't contains the food add it with the
				// value of 1
				if (!foodRequirement.containsKey(food)) {
					foodRequirement.put(food, 1);
				} else {
					foodRequirement.put(food, foodRequirement.get(food) + 1);
				}
			}
		}

		// The Foodstore of the Enclosure may contain some food already
		// So it takes the number of food items that it already has from the
		// foodRequirement
		for (String food : foodRequirement.keySet()) {
			foodRequirement.put(food, foodRequirement.get(food) - foodstore.foodAvailable(food));
		}

		return foodRequirement;
	}

	// Returns a randomly selected animal that belongs to the enclosure
	public Animal getRandomAnimal() {
		Random rand = new Random();
		int number = rand.nextInt(size());
		return this.animals.get(number);
	}
	
	public Animal getAnimalAt(int index){
		if ( (index < size()) && (index >= 0) )
			return this.animals.get(index);
		else 
			return null;
	}

	public StringBuilder getEnclosureStatus(int mode) {
		StringBuilder status = new StringBuilder("ENCLOSURE "+ getEnclosureID() + ": \t size(" + size() + ")  waste(" + waste + ") \n");
		status.append(foodstore.getFoodstoreStatus());
		status.append("\n");
		status.append((mode == 2) ? "GENERAL: \t " + detailStatus + "\n": "");
 		status.append("ANIMALS: \n");
		for (Animal animal : animals) {
			status.append("\t");
			status.append(animal.getAnimalStatus(mode));
			status.append("\n");
		}
		return status;
	}
}
