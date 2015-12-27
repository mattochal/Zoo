
public class Penguin extends Animal {
	
	public Penguin(int lifeExpectancy, String[] eats, char gender, int initialAge, int initialHealth){
		super( lifeExpectancy, eats, gender, initialAge, initialHealth);
	}
	
	public Penguin(char gender, int initialAge, int initialHealth){
		super( 15, new String[] {"fish", "ice cream"}, gender, initialAge, initialHealth );
	}
	
	public Penguin(){
		super( 15, new String[] {"fish", "ice cream"}, 'm', startingAge, MAX_health);
	}
	
	public void treat(){
		detailStatus.append("watches a film; ");
		watchAFilm();
	}
	
	public void watchAFilm(){
		increaseHealth(2);
	}
}
