public abstract class Ape extends Animal {
	
	public Ape(int lifeExpectancy, String[] eats, char gender, int initialAge, int initialHealth){
		super( lifeExpectancy, eats, gender, initialAge, initialHealth);
	}
	
	public Ape(int lifeExpectancy, char gender, int initialAge, int initialHealth){
		super( lifeExpectancy, new String[] {"fruit","ice cream"}, gender, initialAge, initialHealth );
	}
	
	public Ape(){
		super( 28, new String[] {"fruit","ice cream"}, 'm', startingAge, MAX_health);
	}
}
