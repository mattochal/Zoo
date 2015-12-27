  import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Things to note about my implementations of the Zoo class. 1. The
 * specification says that enclosures should be stored as an Array, however I
 * decided to use ArrayList since it gives more flexibility
 * 
 * 
 **/

public class Zoo {
	
	public static final Set<String> animalTypes = new HashSet<String>(Arrays.asList("lion", "chimpanzee", "tiger", "gorilla", "penguin", "bear", "elephant", "giraffe"));
	public static final Set<String> zookeeperTypes = new HashSet<String>(Arrays.asList("zookeeper", "playzookeeper", "phisiozookeeper"));
	
	private ArrayList<Enclosure> enclosures;
	private ArrayList<Zookeeper> zookeepers;
	private Foodstore foodstore;
	private Enclosure lastEnclosureAdded;
	private Zookeeper lastZookeeperAdded;
	private int zooID;
	private int enclosureCount;
	private boolean zooStillRunning;
	
	public Zoo() {
		enclosures = new ArrayList<Enclosure>();
		zookeepers = new ArrayList<Zookeeper>();
		foodstore = new Foodstore();
		lastZookeeperAdded = null;
		lastEnclosureAdded = null;
		enclosureCount = 0;
		zooStillRunning = true;
	}

	/*
	 * This method is responsible for calling aMonthPasses method on all
	 * Enclosures and Zookeepers Returned boolean indicates whether there are
	 * any animals still alive in each zoo, which is returned by aMonthPasses
	 * method of Enclosure
	 */
	public boolean aMonthPasses(){
		zooStillRunning = false;
		
		for (Enclosure e : enclosures) {
			if (e.aMonthPasses()) {
				zooStillRunning = true;
			}
		}

		if (zooStillRunning) {
			for (Zookeeper zk : zookeepers) {
				zk.aMonthPasses();
			}
			
			try {
				restockFoodstore();
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		}
		
		return zooStillRunning;
	}

	// Returns the foodstore that belongs to the Zoo
	public Foodstore getFoodstore() {
		return foodstore;
	}

	// Resets the default values of foodstore
	public void restockFoodstore() throws Exception{
		for (String food : Foodstore.getFoodSet()) {
			foodstore.addFood(food, 50);
		}
	}
	
	public Enclosure getEnclosure(int iD) throws NullPointerException{
		if ((enclosures.size() > iD) && (0 <= iD) ) {
			return enclosures.get(iD);
		} else {
			throw new NullPointerException("Enclosure does not exist.");
		}
	}

	// Adds the enclosure passed to the list and updates the lastEnclosureAdded
	public void addEnclosure(Enclosure enclosure) {
		enclosures.add(enclosure);
		enclosureCount++;
		enclosure.setEnclosureID(enclosureCount);
		this.lastEnclosureAdded = enclosure;
	}

	// Returns the last enclosure added
	public Enclosure lastEnclosureAdded() {
		return this.lastEnclosureAdded;
	}

	// Adds the Zookeeper passed and updates the lastZookeeperAdded
	public void addZookeeper(Zookeeper zookeeper) {
		zookeepers.add(zookeeper);
		this.lastZookeeperAdded = zookeeper;
		zookeeper.setZoo(this);
	}

	// Returns the last zookeeper added
	public Zookeeper lastZookeeperAdded() {
		return this.lastZookeeperAdded;
	}

	// True if there are no animals in the enclosures
	public boolean hasNoEnclosures() {
		return (enclosures.size() == 0);
	}

	/*
	 * Returns the condition of the Zoo in a StringBuilder 
	 * which can then be passed to for example System.out.print();
	 * StringBuilders are a good way of building string 
	 * which can include new lines ('\n') and tabs ('\t') for formating 
	 * whereas a String class does not have that functionality
	 * */
	public StringBuilder getZooStatus(int mode) {
		StringBuilder status = new StringBuilder(">>> ZOO " + this.getZooID() + " : ");
		if (zooStillRunning){
			status.append("RUNNING");
			if (mode >= 1) {
				status.append("\n");
				status.append("ZOO" + foodstore.getFoodstoreStatus());
				
				if (mode == 2){
					status.append("\n");
					for (Zookeeper zookeeper : zookeepers){
						status.append("\n");
						status.append( zookeeper.getDetailStatus() );
					}
				}
	
				status.append("\n");
				
				for (Enclosure enclosure : enclosures) {
					status.append("\n");
					status.append("> " + enclosure.getEnclosureStatus(mode));
				}
			}
		} else {
			status.append("STOPPED");
		}
		return status;
	}

	public int getZooID() {
		return zooID;
	}

	public void setZooID(int zooID) {
		this.zooID = zooID;
	}
	
	// This methods makes the zoo run on its own until all animals die
	public void go(){
		int month = 0;
		
		while (aMonthPasses()) {
			System.out.println();
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> MONTH " + month + " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			
			System.out.println(getZooStatus(2));

			// Pause for 0.5 seconds
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.err.println("ERROR!");
				System.err.println(e.getMessage());
			}
			month++;
		}
		
		System.out.println("No zoos are currently running, simulation has ended.");
	}
}