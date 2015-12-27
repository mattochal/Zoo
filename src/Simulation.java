import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Simulation class has taken certain methods out from the Zoo class to allow
 * multiple Zoos run at the same time. For example the go() method suggested by
 * the specification has been taken out and the Tread.sleep() is now in the main
 * method of the simulation class
 */
public class Simulation {

	private static ArrayList<Zoo> simZoos = new ArrayList<Zoo>();
	private static int zooCount = 0;
	private static Zoo lastZooAdded;
	
	private static int lineNo = 0;

	public static void main(String[] args) {
		// Default parameters if no arguments are passed
		String filename = "example.txt";
		int latency = 500; // in milliseconds 
		int mode = 1;
		
		/* mode 0 will show no detail at all
		 * mode 1 will show the end-of-month status
		 * mode 2 will show what is going to show the most detail inside zoo */
		try {
			if ( args.length >= 1 ) {
				filename = args[0];
			}
			if ( args.length >= 2 ) {
				latency = Integer.parseInt(args[1]);
			}
			if ( args.length >= 3 ) {
				mode = Integer.parseInt(args[2]);
				if ((mode < 0) || (mode > 2)){
					throw new Exception("Invalid mode.");
				}
			}
		} 
		catch (Exception e){
			System.err.println(e.getMessage());
			System.err.println("Invalid arguments. Running the simulation with default values. ");
			filename = "example.txt";
			latency = 500;
			mode = 1;
		}
		
		runSimulation(filename, latency, mode);
	}
	
	public static void runSimulation(String filename, int latency, int mode ) {
		try {
			loadFile(filename);
			
			boolean zoosAreRunning = true;
			int month = 1;
	
			// Run aMonthPasses() method for each zoo
			while (zoosAreRunning) {
				zoosAreRunning = false;
				System.out.println();
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> MONTH " + month + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				
				for (Zoo zoo : simZoos) {
					if (zoo.aMonthPasses()) {
						System.out.println(zoo.getZooStatus(mode));
						zoosAreRunning = true;
					}
				}
	
				// Pause for 0.5 seconds
				try {
					Thread.sleep(latency);
				} catch (InterruptedException e) {
					System.err.println("ERROR!");
					System.err.println(e.getMessage());
				}
				month++;
			}
			for (Zoo zoo : simZoos) {
				System.out.println(zoo.getZooStatus(mode));
				zoosAreRunning = true;
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Unable to open file '" + filename + "'");
		}

		catch (IOException e) {
			System.err.println("Error reading file '" + filename + "'");
		} 
		
		catch (NumberFormatException e){
			System.err.println(getLineNo() + "Invalid number. " + e.getMessage());
		}
		
		catch (NullPointerException e){
			System.err.println(getLineNo() + e.getMessage());
		}
		
		catch (ArrayIndexOutOfBoundsException e){
			System.err.println(getLineNo() + " Incorrect format.");
		}
		
		catch (Exception e) {
			System.err.println(getLineNo());
			e.printStackTrace();
		}

		System.out.println("No zoos are currently running, simulation has ended.");
	}

	public static String getLineNo(){
		return ("Error at line no " + lineNo + " : ");
	}
	
	// loads simulations from a file
	public static void loadFile(String filename) throws Exception{

		String line = null;

		// FileReader reads text files in the default encoding.
		FileReader fileReader = new FileReader(filename);

		// Always wrap FileReader in BufferedReader.
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		// Add appropriate objects to the simulation
		while ((line = bufferedReader.readLine()) != null) {
			lineNo++;
			interpretAdd(line);
		}

		// Closes the file
		bufferedReader.close();
	}

	// Interprets and creates and adds an appropriate object to the simulation
	public static void interpretAdd(String line) throws Exception {
		String keyWord = line.split(":")[0];

		if (keyWord.equals("zoo")) {
			Zoo zoo = new Zoo();
			addZooToSimulation(zoo);
			lastZooAdded = zoo;
		}

		else if (lastZooAdded != null) {

			if (keyWord.equals("enclosure")) {
				Enclosure enclosure = new Enclosure();
				enclosure.addWaste(Integer.parseInt(line.split(":")[1]));
				lastZooAdded.addEnclosure(enclosure);
			}

			// Foodstore inside the zoo
			else if (lastZooAdded.lastEnclosureAdded() == null) {

				/*
				 * Instead of checking for food using a switch case statement, I
				 * used a single if statement to check if the food specified
				 * exists in the static definition of Foodstore in FoodSet. This
				 * has a benefit in the future when a new food item is added to
				 * the foodstore definitions it will not have to be updated here
				 */
				if (Foodstore.getFoodSet().contains(keyWord)) {
					int quantity = Integer.valueOf(line.split(":")[1]);
					lastZooAdded.getFoodstore().addFood(keyWord, quantity);
				}
			}

			else if (lastZooAdded.lastEnclosureAdded() != null) {
				Enclosure lastEnclosure = lastZooAdded.lastEnclosureAdded();

				// Foodstore inside enclosure
				if (Foodstore.getFoodSet().contains(keyWord)) {
					int quantity = Integer.valueOf(line.split(":")[1]);
					lastEnclosure.getFoodstore().addFood(keyWord, quantity);
				}

				else {
					if ( Zoo.animalTypes.contains(keyWord.toLowerCase()) ) {
						addAnimal(keyWord, line.split(":")[1]);
					} else if (Zoo.zookeeperTypes.contains(keyWord.toLowerCase())){
						if ( line.split(":").length > 1){ 
							addZookeeper(keyWord, line.split(":")[1]);
						} else {
							addZookeeper(keyWord,"");
						}
					}
				}
			}  else {
				throw new NullPointerException(" No definition for : " + keyWord );
			}
		}
	}
	
	/*
	 * Adds a Zookeeper to the lastZooAdded, and based on details is added to appropriate enclosure
	 * If no details are specified then add the zookeeper to the lastEnclosureAdded, 
	 * 		else parses through the details and adds the zookeepers to the specified enclosures
	 * 
	 * Passes an exception from lastZooAdded.getEnclosure() if the enclosure is not found
	 * 		or if the program cannot convert a string to an int
	 * */
	public static void addZookeeper(String zookeeperType, String details) throws Exception{		
		switch (zookeeperType){
		case "zookeeper":
			Zookeeper zookeeper = new Zookeeper();
			lastZooAdded.addZookeeper(zookeeper);
			break;
			
		case "physioZookeeper":
			Zookeeper physioZookeeper = new PhysioZookeeper();
			lastZooAdded.addZookeeper(physioZookeeper);
			break;
	
		case "playZookeeper":
			Zookeeper playZookeeper = new PlayZookeeper();
			lastZooAdded.addZookeeper(playZookeeper);
			break;
		}
		if (details.length() > 0) {
			for (String enclosureID : details.split(",")){
				lastZooAdded.lastZookeeperAdded().addEnclosure(lastZooAdded.getEnclosure(Integer.parseInt(enclosureID)));
			}
		} else {
			lastZooAdded.lastZookeeperAdded().addEnclosure(lastZooAdded.lastEnclosureAdded());
		}
	}
	
	/*
	 * Adds an animal to the last enclosure that was added based on the details given,
	 * If the enclosure ID was not specified the animal is added to the last enclosure that was added
	 * If the enclosure ID was specified but it does not exist it will pass the Exception thrown by lastZooAdded.getEnclosure()
	 * If the details are not split into at least 3 elements it will pass the NullPointerException thrown by .split()
	 * 		or if the details are not a number where they're supposed to be
	 * */
	public static void addAnimal(String animalType, String details) throws Exception{
		char gender = Character.toLowerCase(details.charAt(0));
		int age = Integer.parseInt( details.split(",")[1] );
		int health = Integer.parseInt( details.split(",")[2] );
		Enclosure enclosure;
		
		if (details.split(",").length == 4) {
			enclosure = lastZooAdded.getEnclosure( Integer.parseInt( details.split(",")[3] ) );
		} else {
			enclosure = lastZooAdded.lastEnclosureAdded();
		}
		
		switch (animalType) {
		case "lion":
			Lion lion = new Lion(gender, age, health);
			enclosure.addAnimal(lion);
			break;

		case "bear":
			Bear bear = new Bear(gender, age, health);
			enclosure.addAnimal(bear);
			break;

		case "chimpanzee":
			Chimpanzee chimpanzee = new Chimpanzee(gender, age, health);
			enclosure.addAnimal(chimpanzee);
			break;

		case "elephant":
			Elephant elephant = new Elephant(gender, age, health);
			enclosure.addAnimal(elephant);
			break;

		case "gorilla":
			Gorilla gorilla = new Gorilla(gender, age, health);
			enclosure.addAnimal(gorilla);
			break;

		case "penguin":
			Penguin penguin = new Penguin(gender, age, health);
			enclosure.addAnimal(penguin);
			break;
			
		case "tiger":
			Tiger tiger = new Tiger(gender, age, health);
			enclosure.addAnimal(tiger);
			break;
			
		case "giraffe":
			Giraffe giraffe = new Giraffe(gender, age, health);
			enclosure.addAnimal(giraffe);
			break;
		}
	}
	
	// Adds a zoo to the simulation list
	public static void addZooToSimulation(Zoo zoo){
		simZoos.add(zoo);
		zooCount++;
		zoo.setZooID(zooCount);
	}
}