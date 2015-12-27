
public class Chimpanzee extends Ape {
	
	public Chimpanzee(int lifeExpectancy, String[] eats, char gender, int initialAge, int initialHealth){
		super( lifeExpectancy, eats, gender, initialAge, initialHealth);
	}
	
	public Chimpanzee(char gender, int initialAge, int initialHealth){
		super(24, gender, initialAge, initialHealth );
	}
	
	public Chimpanzee(){
		super();
	}
	
	public void treat(){
		detailStatus.append("plays chase; ");
		playChase();
	}
	
	protected void playChase(){
		increaseHealth(4);
	}
}
