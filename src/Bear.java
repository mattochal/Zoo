public class Bear extends Animal {
	
	public Bear(int lifeExpectancy, String[] eats, char gender, int initialAge, int initialHealth){
		super( lifeExpectancy, eats, gender, initialAge, initialHealth);
	}
	
	public Bear(char gender, int initialAge, int initialHealth){
		super( 24, new String[] {"fish","steak"}, gender, initialAge, initialHealth );
	}
	
	public Bear(){
		super( 24, new String[] {"fish","steak"}, 'm', startingAge, MAX_health);
	}
	
	public void treat(){
		detailStatus.append("gets a hug; ");
		hug();
	}
	
	protected void hug(){
		increaseHealth(3);
	}
}
