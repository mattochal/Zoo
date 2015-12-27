public class Elephant extends Animal {
	
	public Elephant(int lifeExpectancy, String[] eats, char gender, int initialAge, int initialHealth){
		super( lifeExpectancy, eats, gender, initialAge, initialHealth);
	}
	
	public Elephant(char gender, int initialAge, int initialHealth){
		super( 36, new String[] {"hay","fruit"}, gender, initialAge, initialHealth );
	}
	
	public Elephant(){
		super( 36, new String[] {"hay","fruit"}, 'm', startingAge, MAX_health);
	}
	
	public void treat(){
		detailStatus.append("has a bath; ");
		bath();
	}
	
	protected void bath(){
		//Add 5 health points
		increaseHealth(5);
	}
}
