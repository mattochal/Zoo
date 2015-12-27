public class Giraffe extends Animal {

	public Giraffe(int lifeExpectancy, String[] eats, char gender, int initialAge, int initialHealth){
		super( lifeExpectancy, eats, gender, initialAge, initialHealth);
	}
	
	public Giraffe(char gender, int initialAge, int initialHealth){
		super( 28, new String[] {"hay", "fruit"}, gender, initialAge, initialHealth );
	}
	
	public Giraffe(){
		super( 28, new String[] {"hay", "fruit"}, 'm', startingAge, MAX_health);
	}
	
	public void treat(){
		detailStatus.append("has a neck massage; ");
		neckMassage();
	}
	
	public void neckMassage(){
		increaseHealth(4);
	}

}
