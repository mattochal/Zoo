
public class Tiger extends BigCat {

	public Tiger(int lifeExpectancy, String[] eats, char gender, int initialAge, int initialHealth){
		super( lifeExpectancy, eats, gender, initialAge, initialHealth);
	}
	
	public Tiger(char gender, int initialAge, int initialHealth){
		super(gender, initialAge, initialHealth );
	}
	
	public Tiger(){
		super();
	}
	
	public void stroked(){
		// Add 3 health points
		increaseHealth(3);
	}
}
