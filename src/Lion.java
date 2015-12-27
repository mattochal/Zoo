
public class Lion extends BigCat {

	public Lion(int lifeExpectancy, String[] eats, char gender, int initialAge, int initialHealth){
		super( lifeExpectancy, eats, gender, initialAge, initialHealth);
	}
	
	public Lion(char gender, int initialAge, int initialHealth){
		super(gender, initialAge, initialHealth );
	}
	
	public Lion(){
		super();
	}

	protected void stroked(){
		detailStatus.append("gets stroked; ");
		// Add 2 health points
		increaseHealth(2);
	}
}
