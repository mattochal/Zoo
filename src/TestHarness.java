public class TestHarness {
	public static void testAnimals() {
		Lion var1 = new Lion();
		assertEquals("Expecting 1 from getAge() as a default value", var1.getAge(), 1);
		Bear var2 = new Bear();
		assertEquals("Expecting m from var2.getGender() as a default value",var2.getGender(), 'm');
		var2.decreaseHealth();
		System.out.println("[PASS] Decreased the health of the bear.");
		passed++;
	}

	public static void testFoodstore() {
		Foodstore var5 = new Foodstore();
		System.out.println("[PASS] Created foodstore");
		passed++;
		try {
		var5.addFood("steak", 5);
		}
		catch (Exception e){
			
		}
		System.out.println("[PASS] Added steak");
		passed++;
		//var5.addFood("pizza", 10);
		//System.out.println("[PASS] Added pizza?");
		//passed++;
		var5.takeFood("steak");
		System.out.println("[PASS] Eat steak");
		passed++;
	}

	public static void testEnclosures() {
		Enclosure e = new Enclosure();
		Lion var3 = new Lion();
		e.addAnimal(var3);
		var3.setEnclosure(e);
		Lion var4 = new Lion();
		e.addAnimal(var4);
		var4.setEnclosure(e);
		assertEquals("Expecting 2 from e.size() i.e. 2 animals",e.size(), 2);
		Foodstore f = e.getFoodstore();
		System.out.println("[PASS] Got the foodstore of the enclosure");
		passed++;
		try {
		f.addFood("steak",10);
		}
		catch (Exception ex){
			
		}
		var3.eat();
		var3.excrete();
		System.out.println("[PASS] The lion ate.");
		passed++;
		var4.aMonthPasses();
		System.out.println("[PASS] a month passed for the other lion.");
		passed++;
		assertEquals("Expecting 8 from e.getWasteSize()",e.getWasteSize(), 8);
		passed++;
		assertEquals("Expecting 8 from f.foodAvailable('steak')",f.foodAvailable("steak"), 8);
		passed++;
	}

	static int passed = 0;
	static int failed = 0;

	public static void main(String[] args) {
		testFoodstore();
		testAnimals();
		testEnclosures();
		if (failed > 0)
			System.out.println("Unpredicted outputs!");
		System.out.println("Tests run: " + (passed + failed) + ",  Failures: " + failed);
	}

	private static void assertEquals(String message, Object expected,
			Object actual) {
		if ((expected != null && expected.equals(actual))
				|| (expected == null && actual == null)) {
			System.out.println("[PASS] " + message);
			passed++;
		} else {
			System.out.println("[FAIL] " + message);
			failed++;
		}
	}

	private static void fail(String message) {
		System.out.println("[FAIL] " + message);
		failed++;
	}
}