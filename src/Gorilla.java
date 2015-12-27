public class Gorilla extends Ape {
	
	public Gorilla(int lifeExpectancy, String[] eats, char gender, int initialAge, int initialHealth){
		super( lifeExpectancy, eats, gender, initialAge, initialHealth);
	}
	
	public Gorilla(char gender, int initialAge, int initialHealth){
		super(32, gender, initialAge, initialHealth );
	}
	
	public Gorilla(){
		super();
	}
	
	public void treat(){
		detailStatus.append("gets to paint; ");
		painting();
	}
	
	public void painting(){
		increaseHealth(4);
	}
}