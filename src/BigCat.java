
public abstract class BigCat extends Animal{

	public BigCat(int lifeExpectancy, String[] eats, char gender, int initialAge, int initialHealth){
		super( lifeExpectancy, eats, gender, initialAge, initialHealth);
	}
	
	public BigCat(char gender, int initialAge, int initialHealth){
		super( 24, new String[] {"steak","celery"}, gender, initialAge, initialHealth );
	}
	
	public BigCat(){
		super( 24, new String[] {"steak","celery"}, 'm', startingAge, MAX_health);
	}
	
	public void treat(){
		stroked();
	}
	
	protected abstract void stroked();
}