import java.util.Random;

/**
 * Animal class is an abstract class that defines common methods and fields throughout different types of animals
 **/
public abstract class Animal {
	
	private int lifeExpectancy; // stores the average age that the Animal lives to in months
	private String[] eats; // stores the types of food that the Animal can eat
	private int age; // stores the age of the Animal in months
	private char gender; // stores whether the Animal is Male (‘m’) or Female (‘f’)
	private int health; // stores how healthy the animal is, 10 being healthiest
	
	private Enclosure enclosure; // stores the reference to the enclosure the Animal belongs to
	private int waste; // stores the amount of waste the animal will produce
	
	protected StringBuilder detailStatus; // stores details of what the animal does for user's informations
	// Declaring as protected because the subclasses can easily access the detailStatus to add appropriate 
	
	public static final int MAX_health = 10; // maximum health an animal can have
	public static final int MIN_health = 0; // minimum health an animal can have
	public static final int startingAge = 1; // default starting age of an animal
	
	public Animal(int lifeExpectancy, String[] eats, char gender, int initialAge, int initialHealth){
		this.age = initialAge;
		setHealth(initialHealth);
		this.lifeExpectancy = lifeExpectancy;
		setGender(gender);
		this.eats = eats;
		this.waste = 0;
		detailStatus = new StringBuilder();
	}
	
	//Default animal
	public Animal(){
		this.age = startingAge;
		this.health = MAX_health;
		this.lifeExpectancy = 30;
		setGender(gender);
		this.eats = new String[] {"hay","fruit","steak","celery"};
		this.waste = 0;
		detailStatus = new StringBuilder();
	}
	
	/*
	 * The gender of the animal will be female or male
	 */
	private void setGender(char gender){
		if (gender == 'f'){
			this.gender = gender;
		} else {
			this.gender = 'm';
		}
	}
	
	/* 
	 * Checks if the animal's health reaches the minimum health or if it reaches it's life expectancy
	 * Animal dies if it is too old
	 */
	private boolean isDead(){
		if (health <= MIN_health){
			return true;
		}
		if (age >= lifeExpectancy){
			return true;
		}
		return false;
	}
	
	/* 
	 * Select a random food the animal can eat and returns it
	 */
	private String getRandomFood(){
		Random rand = new Random();
		int number = rand.nextInt(eats.length);
		return this.eats[number];
	}
	
	/* 
	 * aMonthPasses() calls the core methods that allow the animal to eat, loose health points, excrete and grow 
	 * It also reduces the health of the animal by 2,
	 * and if the animal is still alive the program will allow it to eat and produce waste and breed
	 * 
	 * NOTE: The specification suggests this method to be declared as abstract.
	 * However, I decided to make it non-abstract as this method looks exactly the same for all Animal sub-classes.
	 * If in the future a subclass wants to implement this method differently, it will still be able to do so by overriding.
	 */
	public boolean aMonthPasses(){
		// Clears the StringBuilder, apparently this is a good way to do so
		detailStatus.setLength(0);
		
		decreaseHealth(2);
		increaseAge();
		if (isDead()){
			detailStatus.append("dies; ");
			return false;
		}
		eat();
		excrete();
		return true;
	}
	
	// Returns whether or not the Animal can eat the specified type of food 
	public boolean canEat(String food){
		int i = 0;
		
		// loops through the array of food in variable 'eats' until the name of the food is found 
		// or if the program reaches the end of the array
		while ( i < eats.length ){
			if (eats[i].equals(food)){
				return true;
			}
			i++;
		}
		
		return false;
	}
	
	/* 
	 * eat() makes the animal eat a piece of food,
	 * if the first piece of food is not available the next food in the array is tried 
	 * As a result this may increase the animals health and add an appropriate amount of waste to the animal,
	 * and eat() returns true. 
	 * If the animal has not eaten a piece of food eat() returns false.
	 */
	public boolean eat(){
		int foodCount = eats.length;
		String food = "";
		boolean eaten = false;
		
		while ((foodCount > 0) && (!eaten)) {
			foodCount--;
			food = eats[foodCount];
			eaten = enclosure.getFoodstore().takeFood(food);
		}
		
		if (eaten) {
			detailStatus.append("eats " + food + "; ");
			increaseHealth( Foodstore.getFoodHealth(food) );
			waste = waste + Foodstore.getFoodWaste(food);
			return true;
		} else {
			detailStatus.append("FAILS to eat; ");
			return false;
		}
	}

	// Declared as abstract because each child class of Animal will have a different treat method
	public abstract void treat();
	
	public int getAge(){
		return age;
	}
	
	public char getGender(){
		return gender;
	}
	
	public String[] getEatsArray(){
		return eats;
	}
	
	public void setEnclosure(Enclosure enclosure){
		this.enclosure = enclosure;
	}
	
	// Decrease health by the given health points, healthPoints must be a positive number
	public boolean decreaseHealth(int healthPoints){
		if (healthPoints > 0){
			detailStatus.append("looses " + healthPoints + " health points; ");
			setHealth(health - healthPoints);
			return true;
		} else {
			return false;
		}
	}
	
	public void decreaseHealth(){
		decreaseHealth(1);
	}
	
	// Increases health points but if healthPoints is a negative number health will not be increased
	public boolean increaseHealth(int healthPoints){
		if (healthPoints > 0){
			detailStatus.append("gains " + healthPoints + " health points; ");
			setHealth(health + healthPoints);
			return true;
		} else {
			return false;
		}
	}
	
	public void increaseHealth(){
		increaseHealth(1);
	}
	
	/*
	 * Sets the health of the animal verifying that the healthPoints is within bounds.
	 * Declared as private because it should not be a case that an external class sets the health directly.
	 * Initial health of the animal should be set through the Animal constructor.
	 */
	private void setHealth(int healthPoints){
		health = healthPoints;
		if (health > MAX_health){
			health = MAX_health;
		} else if (health < MIN_health) {
			health = 0;
		}
	}
	
	// Increases the age by one
	public void increaseAge(){
		detailStatus.append("gets older by " + 1 + " month; ");
		age++;
	}
	
	// Adds waste to the Animal's enclosure and the animal produces and empty's the waste
	public void excrete(){
		enclosure.addWaste(waste);
		detailStatus.append("produces " + waste + " waste; ");
		waste = 0;
	}
	
	// Returns the status of the animal with details of what it has done throughout the month
	public StringBuilder getAnimalStatus(int mode){
		return new StringBuilder(this.getClass().getName() + "("+ gender +") "
				+ ((mode == 2) ? " " + detailStatus + "\n\t\t": "")
				+ " At the end of the month has " + health + " health and is " + age + " months old");
	}
}