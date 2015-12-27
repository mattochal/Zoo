import java.util.ArrayList;
import java.util.Arrays;

public class PhysioZookeeper extends Zookeeper {

	public PhysioZookeeper(Enclosure enclosure) {
		super(enclosure);
		canTreat.add(Elephant.class);
		canTreat.add(Giraffe.class);
		
	}

	public PhysioZookeeper() {
		super();
		canTreat.add(Elephant.class);
		canTreat.add(Giraffe.class);
	}
}
