import java.util.ArrayList;
import java.util.Arrays;

public class PlayZookeeper extends Zookeeper {

	// Constructor
	public PlayZookeeper(Enclosure enclosure) {
		super(enclosure);
		canTreat.add(Chimpanzee.class);
		canTreat.add(Gorilla.class);
		canTreat.add(Penguin.class);
	}

	public PlayZookeeper() {
		super();
		canTreat.add(Chimpanzee.class);
		canTreat.add(Gorilla.class);
		canTreat.add(Penguin.class);
	}
}
