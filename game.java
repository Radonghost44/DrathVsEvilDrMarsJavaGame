package game;

// Game is won by taking the beat up engine in location 14 (Crashed Space Ship) and repairing a space ship in location 5 (Whittie's 
//Space ship dealer

import java.io.File;
import java.io.FileNotFoundException;
// Amelia Beckham
import java.util.Scanner;

class game {
	// Globals
	public static int validMoves = 0;
	private static String goodDir = ""; // used
	private static final int MAX_LOCALES = 16; // Total number of
												// rooms/locations used
	private static int currentLocale = 0; // Player starts in locale 0. //used
	private static String command; // Game commands// used
	private static boolean stillPlaying = true; // Controls the game loop.//used
	private static int score = 5; // score is 5 for a new location//used
	private static final int[] roomCheck = new int[16];// used
	private static boolean tookMap = false;// used
	private static int numberOfItemsInInventory = 0;// used
	private static final String[] inventory = new String[700];
	private static int totalMoves = 0;
	private static Money[] arrayOfMoney;
	private static int coinPurse = 0;
	private static final ListofItems lm1 = new ListofItems();
	private static final ListofLocales listForDirs = new ListofLocales();
	private static final ListItem[] arrayForMagicShop = new ListItem[673];// more
	public static void main(String[] args) throws Exception {
		Queue playerPathForwardQueue = new Queue();
		Stack playerPathBackwordStack = new Stack();
		// Make the list manager.
		lm1.setName("Magic Items");
		lm1.setDesc("these are what are for sale.");
		final String fileName = "magic.txt";

		// noinspection ConstantConditions
		for (int i = 0; i == MAX_LOCALES - 1; i++) {
			roomCheck[i] = 0;
		}
		// Display the command line args.
		System.out.println("Starting with args:");
		for (int i = 0; i < args.length; i++) {
			System.out.println(i + ":" + args[i]);
		}
		// Set starting locale, if it was provided as a command line parameter.
		if (args.length > 0) {
			int startLocation = Integer.parseInt(args[0]);
			if (startLocation >= 0 && startLocation <= MAX_LOCALES) {
				currentLocale = startLocation;
			}
		}
		int oldLocation = -1;
		// Get the game started.
		init();
		readMagicItemsFromFileToArray(fileName);
		selectionSort();
		updateDisplay();
		while (stillPlaying) {
			if ((oldLocation != currentLocale)/*||(currentLocale==currentLocale)*/ ){
				playerPathForwardQueue.enqueue(currentLocale);
				playerPathBackwordStack.push(currentLocale);
				oldLocation = currentLocale;
			}
			getCommand();
			navigate();
			if (stillPlaying) {
				updateDisplay();
				if (currentLocale == 1) {// print out list
					System.out.println("Current items for sale are :\n");
					for (int i = 0; i < arrayForMagicShop.length; i++) {
						System.out.print(arrayForMagicShop[i]);
					}
				}
			}
		}
		// We're done. Thank the player and exit.
		System.out.println("Would you like to review your path through the game from start to finish, forward?  y = yes, anything else = no");
		Scanner choiceReader = new Scanner(System.in);
		String choice = choiceReader.nextLine(); // command is global.
		if (choice.equalsIgnoreCase("y")) {
			for (int i = 0; i < validMoves+1; i++) {
                 System.out.println(sequentialSearchLocaleName(playerPathForwardQueue.dequeue()));}// breaks after repeated location-add to queue
			
			
		System.out.println("Would you like to review your path backwards through the game from finish to start?  y = yes, anything else = no");
		Scanner otherChoiceReader = new Scanner(System.in);
		String otherChoice = otherChoiceReader.nextLine(); // command is global.
		if (otherChoice.equalsIgnoreCase("y")) {
			for (int i = 0; i < validMoves + 1; i++) {
				System.out.println(sequentialSearchLocaleName(playerPathBackwordStack.pop()));
			}
		}
		System.out.println("Thank you for playing.");}
	}

	/*--------------------------------------------------------------------------------*/
	private static void readMagicItemsFromFileToArray(String fileName) {
		File myFile = new File(fileName);
		try {
			int itemCount = 7;
			Scanner input = new Scanner(myFile);

			while (input.hasNext() && itemCount < game.arrayForMagicShop.length) {
				// Read a line from the file.
				String itemName = input.nextLine();

				// Construct a new list item and set its attributes.
				ListItem fileItem = new ListItem();
				fileItem.setListItemName(itemName);
				fileItem.setListItemCost((int) (Math.random() * 1000));
				fileItem.setListItemNext(null); // Still redundant. Still
				// safe.

				// Add the newly constructed item to the array.
				game.arrayForMagicShop[itemCount] = fileItem;
				itemCount = itemCount + 1;
			}
			// Close the file.
			input.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File not found. " + ex.toString());
		}
	}

	/*--------------------------------------------------------------------------------*/
	private static void selectionSort() {
		for (int pass = 0; pass < game.arrayForMagicShop.length - 1; pass++) {
			// System.out.println(pass + "-" + items[pass]);
			int indexOfTarget = pass;
			int indexOfSmallest = indexOfTarget;

			for (int j = indexOfTarget + 1; j < game.arrayForMagicShop.length; j++) {
				if (game.arrayForMagicShop[j].getListItemName()
						.compareToIgnoreCase(
								game.arrayForMagicShop[indexOfSmallest]
										.getListItemName()) < 0) {
					indexOfSmallest = j;
				}
			}
			ListItem temp = game.arrayForMagicShop[indexOfTarget];
			game.arrayForMagicShop[indexOfTarget] = game.arrayForMagicShop[indexOfSmallest];
			game.arrayForMagicShop[indexOfSmallest] = temp;
		}
	}

	/*--------------------------------------------------------------------------------*/
	private static void totalScore() {
		if (roomCheck[currentLocale] == 0) {
			score = score + 5;
			roomCheck[currentLocale] = 1;
		}
		System.out.println("Your current score is " + score);
	}

	/*--------------------------------------------------------------------------------*/
	private static void achievementRatio() {
		int ar = 0;
		try {
			ar = score / totalMoves;
		} catch (java.lang.ArithmeticException bendingTheLawsOfMath) {
			System.out.print("Your achievement ratio is 0");
		}
		System.out.println("Your achievement ratio is " + ar);
	}

	/*--------------------------------------------------------------------------------*/
	private static void init() {
		// Initialize any uninitialized globals.
		command = new String();
		stillPlaying = true;
		// Set up the location list.
		Locale[] locations = new Locale[16];
		locations[0] = new Locale();
		locations[0].setName("Kennel Town");
		locations[0].setId(0);
		locations[0]
				.setDesc("You are in a quiet and peaceful neighborhood where various animals live together.");
		locations[0].setNewLocaleNorth(3);
		locations[0].setNewLocaleSouth(4);
		locations[0].setNewLocaleEast(1);
		locations[0].setNewLocaleWest(2);
		locations[0].setNewLocaleUp(-1);
		locations[0].setNewLocaleDown(-1);
		locations[0].setNext(null);
		// item 8-- simple map
		locations[0].arrayOfItems[0] = new ListItem();
		locations[0].arrayOfItems[0].setarrayOfItemsName("Map");
		locations[0].arrayOfItems[0]
				.setarrayOfItemsDesc("A simple map, a little worn around the edges. Press m to see it");
		locations[0].arrayOfItems[1] = new ListItem();
		locations[0].arrayOfItems[1].setarrayOfItemsName(null);
		locations[0].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[0].arrayOfItems[2] = new ListItem();
		locations[0].arrayOfItems[2].setarrayOfItemsName(null);
		locations[0].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[0].arrayOfItems[3] = new ListItem();
		locations[0].arrayOfItems[3].setarrayOfItemsName(null);
		locations[0].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[1] = new Locale();
		// item0- magical summoning thingy
		ListItem item0 = new ListItem();
		item0.setListItemName("Magical Summoning Thingy");
		item0.setListItemDesc("Rumoured to be from another dimension, it can summon random creatures.");
		item0.setListItemCost(728);
		// item 1-- rusty hammer
		ListItem item1 = new ListItem();
		item1.setListItemName("Rusty Hammer");
		item1.setListItemDesc("An old hammer used for hitting nails  ");
		item1.setListItemCost(52);
		item1.setListItemNext(null); // Redundant, but safe.
		// ITEM 2 wand with low batteries
		ListItem item2 = new ListItem();
		item2.setListItemName("Wand with Low Batteries");
		item2.setListItemDesc("A wand that doesnt work well, since it needs some new AA batteries");
		item2.setListItemCost(14);
		item2.setListItemNext(null); // Redundant, but safe.
		// item 3 broken sword
		ListItem item3 = new ListItem();
		item3.setListItemName("Broken Sword");
		item3.setListItemDesc("A sword that is missing its tip");
		item3.setListItemCost(10);
		item3.setListItemNext(null); // Redundant, but safe.
		// item 25- +2 ring
		ListItem item25 = new ListItem();
		item25.setListItemName("+2 ring");
		item25.setListItemDesc("precious");
		item25.setListItemCost(42);
		item25.setListItemNext(null); // Redundant, but safe.
		// item 26- cloal of doom
		ListItem item26 = new ListItem();
		item26.setListItemName("Cloak of Doom");
		item26.setListItemDesc("Scary");
		item26.setListItemCost(666);
		item26.setListItemNext(null); // Still redundant. Still safe.
		// item 27= borad sword
		ListItem item27 = new ListItem();
		item27.setListItemName("broad sword");
		item27.setListItemDesc("sharp");
		item27.setListItemCost(12);
		item27.setListItemNext(null); // Still redundant. Still safe.

		// -- array for magic shops items
		arrayForMagicShop[0] = item0;
		arrayForMagicShop[1] = item1;
		arrayForMagicShop[2] = item2;
		arrayForMagicShop[3] = item3;
		arrayForMagicShop[4] = item25;
		arrayForMagicShop[5] = item26;
		arrayForMagicShop[6] = item27;

		// -- magic shop itself
		locations[1].setName("Magick Shoppe");
		locations[1].setId(1);
		locations[1]
				.setDesc("You are in the town's magic shop. It is made of concrete and sells various magical items. ");
		locations[1].setNewLocaleNorth(12);
		locations[1].setNewLocaleSouth(5);
		locations[1].setNewLocaleEast(-1);
		locations[1].setNewLocaleWest(0);
		locations[1].setNewLocaleUp(-1);
		locations[1].setNewLocaleDown(-1);
		locations[1].arrayOfItems[0] = new ListItem();
		locations[1].arrayOfItems[0].setarrayOfItemsName(null);
		locations[1].arrayOfItems[0].setarrayOfItemsDesc(null);
		locations[1].arrayOfItems[1] = new ListItem();
		locations[1].arrayOfItems[1].setarrayOfItemsName(null);
		locations[1].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[1].arrayOfItems[2] = new ListItem();
		locations[1].arrayOfItems[2].setarrayOfItemsName(null);
		locations[1].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[1].arrayOfItems[3] = new ListItem();
		locations[1].arrayOfItems[3].setarrayOfItemsName(null);
		locations[1].arrayOfItems[3].setarrayOfItemsDesc(null);
		locations[1].setNext(null);
		// magic shop is different from most locs, because it uses a list for
		// its items, not ArrayofItems.

		locations[2] = new Locale();
		locations[2].setName("Icy Cave");
		locations[2].setId(2);
		locations[2]
				.setDesc("A deep and cold cave where various wild creatures live");
		locations[2].setNewLocaleNorth(11);
		locations[2].setNewLocaleSouth(6);
		locations[2].setNewLocaleEast(0);
		locations[2].setNewLocaleWest(-1);
		locations[2].setNewLocaleUp(-1);
		locations[2].setNewLocaleDown(-1);
		locations[2].setNext(null);
		// item 9-- icicle of a million colors, found at icey cave //
		locations[2].arrayOfItems[0] = new ListItem();
		locations[2].arrayOfItems[0]
				.setarrayOfItemsName("Icicle of a Million Colors");
		locations[2].arrayOfItems[0]
				.setarrayOfItemsDesc("A large icicle that appears to shine several different colors.");
		locations[2].arrayOfItems[1] = new ListItem();
		locations[2].arrayOfItems[1].setarrayOfItemsName(null);
		locations[2].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[2].arrayOfItems[2] = new ListItem();
		locations[2].arrayOfItems[2].setarrayOfItemsName(null);
		locations[2].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[2].arrayOfItems[3] = new ListItem();
		locations[2].arrayOfItems[3].setarrayOfItemsName(null);
		locations[2].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[3] = new Locale();
		locations[3].setName("Unpleasant Meadows");
		locations[3].setId(3);
		locations[3]
				.setDesc("A polluted wasteland where various mutated creatures live ");
		locations[3].setNewLocaleNorth(14);
		locations[3].setNewLocaleSouth(0);
		locations[3].setNewLocaleEast(12);
		locations[3].setNewLocaleWest(11);
		locations[3].setNewLocaleUp(-1);
		locations[3].setNewLocaleDown(-1);
		locations[3].setNext(null);
		locations[3].arrayOfItems[0] = new ListItem();
		locations[3].arrayOfItems[0].setarrayOfItemsName("Three Headed Fish");
		locations[3].arrayOfItems[0]
				.setarrayOfItemsDesc("A dull brown fish that has three heads and a lot of teeth. Quite frightening , actually");
		locations[3].arrayOfItems[1] = new ListItem();
		locations[3].arrayOfItems[1]
				.setarrayOfItemsName("Tin Drum of Toxic Waste");
		locations[3].arrayOfItems[1]
				.setarrayOfItemsDesc("A drum full of waste from the fission planet. It's pretty nasty stuff");
		locations[3].arrayOfItems[2] = new ListItem();
		locations[3].arrayOfItems[2].setarrayOfItemsName(null);
		locations[3].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[3].arrayOfItems[3] = new ListItem();
		locations[3].arrayOfItems[3].setarrayOfItemsName(null);
		locations[3].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[4] = new Locale();
		locations[4].setName("Catnip City");
		locations[4].setId(4);
		locations[4]
				.setDesc("A newly restored city. Rumor says it was built by an ancient society millions of years ago ");
		locations[4].setNewLocaleNorth(0);
		locations[4].setNewLocaleSouth(8);
		locations[4].setNewLocaleEast(5);
		locations[4].setNewLocaleWest(6);
		locations[4].setNewLocaleUp(-1);
		locations[4].setNewLocaleDown(-1);
		locations[4].setNext(null);
		locations[4].arrayOfItems[0] = new ListItem();
		locations[4].arrayOfItems[0].setarrayOfItemsName("Taxi Ticket");
		locations[4].arrayOfItems[0]
				.setarrayOfItemsDesc("A ticket for one of the bright yellow taxis. ");
		locations[4].arrayOfItems[1] = new ListItem();
		locations[4].arrayOfItems[1]
				.setarrayOfItemsName("Album of Strange Music");
		locations[4].arrayOfItems[1]
				.setarrayOfItemsDesc("A cd full of music that sounds like drunk dance music, (its kind of hard to describe).");
		locations[4].arrayOfItems[2] = new ListItem();
		locations[4].arrayOfItems[2].setarrayOfItemsName(null);
		locations[4].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[4].arrayOfItems[3] = new ListItem();
		locations[4].arrayOfItems[3].setarrayOfItemsName(null);
		locations[4].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[5] = new Locale();
		locations[5].setName("Whittie's Space Ship Dealer");
		locations[5].setId(5);
		locations[5]
				.setDesc("A utilitarian building that sells different kinds of space ships  ");
		locations[5].setNewLocaleNorth(1);
		locations[5].setNewLocaleSouth(9);
		locations[5].setNewLocaleEast(-1);
		locations[5].setNewLocaleWest(4);
		locations[5].setNewLocaleUp(-1);
		locations[5].setNewLocaleDown(-1);
		locations[5].setNext(null);

		locations[5].arrayOfItems[0] = new ListItem();
		locations[5].arrayOfItems[0].setarrayOfItemsName("Verne Spacemaster");
		locations[5].arrayOfItems[0]
				.setarrayOfItemsDesc("A silver box-like spaceship with a navy blue interior");
		locations[5].arrayOfItems[1] = new ListItem();
		locations[5].arrayOfItems[1].setarrayOfItemsName("Ancient Alien");
		locations[5].arrayOfItems[1]
				.setarrayOfItemsDesc("A hot pink flying saucer without an engine. It has chrome trim around it and a purple interior. In other words, the girliest space ship in existence");
		locations[5].arrayOfItems[2] = new ListItem();
		locations[5].arrayOfItems[2].setarrayOfItemsName("Beyond Infinty");
		locations[5].arrayOfItems[2]
				.setarrayOfItemsDesc("A triangular space ship that is dark purple and has neon yellow interior.");
		locations[5].arrayOfItems[3] = new ListItem();
		locations[5].arrayOfItems[3]
				.setarrayOfItemsName("Experimental Aircraft-Area 51 Edition");
		locations[5].arrayOfItems[3]
				.setarrayOfItemsDesc("A gold space ship that is shaped like a sword. It has a tiny black cockpit.");

		locations[6] = new Locale();
		locations[6].setName("Vet of Horrors");
		locations[6].setId(6);
		locations[6]
				.setDesc("A hospital that all animals go to. It is run by the smartest and jerkiest computer in the world");
		locations[6].setNewLocaleNorth(2);
		locations[6].setNewLocaleSouth(7);
		locations[6].setNewLocaleEast(4);
		locations[6].setNewLocaleWest(-1);
		locations[6].setNewLocaleUp(-1);
		locations[6].setNewLocaleDown(-1);
		locations[6].setNext(null);
		locations[6].arrayOfItems[0] = new ListItem();
		locations[6].arrayOfItems[0]
				.setarrayOfItemsName("Syringe of Pain Killer");
		locations[6].arrayOfItems[0]
				.setarrayOfItemsDesc("A Syringe of a powerful pain killer, used to retore health");
		locations[6].arrayOfItems[1] = new ListItem();
		locations[6].arrayOfItems[1].setarrayOfItemsName(null);
		locations[6].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[6].arrayOfItems[2] = new ListItem();
		locations[6].arrayOfItems[2].setarrayOfItemsName(null);
		locations[6].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[6].arrayOfItems[3] = new ListItem();
		locations[6].arrayOfItems[3].setarrayOfItemsName(null);
		locations[6].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[7] = new Locale();
		locations[7].setName("Uncanny Valley");
		locations[7].setId(7);
		locations[7]
				.setDesc("A creepy valley where the clones are rumoured to live ");
		locations[7].setNewLocaleNorth(6);
		locations[7].setNewLocaleSouth(-1);
		locations[7].setNewLocaleEast(8);
		locations[7].setNewLocaleWest(10);
		locations[7].setNewLocaleUp(-1);
		locations[7].setNewLocaleDown(-1);
		locations[7].setNext(null);

		locations[7].arrayOfItems[0] = new ListItem();
		locations[7].arrayOfItems[0].setarrayOfItemsName("Clone Paw");
		locations[7].arrayOfItems[0]
				.setarrayOfItemsDesc("Exactly what it sounds like. Poor clone");
		locations[7].arrayOfItems[1] = new ListItem();
		locations[7].arrayOfItems[1].setarrayOfItemsName(null);
		locations[7].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[7].arrayOfItems[2] = new ListItem();
		locations[7].arrayOfItems[2].setarrayOfItemsName(null);
		locations[7].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[7].arrayOfItems[3] = new ListItem();
		locations[7].arrayOfItems[3].setarrayOfItemsName(null);
		locations[7].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[8] = new Locale();
		locations[8].setName("Draco's Dragon Meat Factory");
		locations[8].setId(8);
		locations[8]
				.setDesc("The largest feed lot and slaughterhouse of dragon meat. Famous for its gentle and kind treatment of its livestock ");
		locations[8].setNewLocaleNorth(4);
		locations[8].setNewLocaleSouth(-1);
		locations[8].setNewLocaleEast(9);
		locations[8].setNewLocaleWest(7);
		locations[8].setNewLocaleUp(-1);
		locations[8].setNewLocaleDown(-1);
		locations[8].setNext(null);

		locations[8].arrayOfItems[0] = new ListItem();
		locations[8].arrayOfItems[0].setarrayOfItemsName("Dragon Fang");
		locations[8].arrayOfItems[0]
				.setarrayOfItemsDesc("A fang from an old dragon");
		locations[8].arrayOfItems[1] = new ListItem();
		locations[8].arrayOfItems[1].setarrayOfItemsName(null);
		locations[8].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[8].arrayOfItems[2] = new ListItem();
		locations[8].arrayOfItems[2].setarrayOfItemsName(null);
		locations[8].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[8].arrayOfItems[3] = new ListItem();
		locations[8].arrayOfItems[3].setarrayOfItemsName(null);
		locations[8].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[9] = new Locale();
		locations[9].setName("Cheesy Tourist Area");
		locations[9].setId(9);
		locations[9]
				.setDesc("A tourist trap where tourists can buy cheap gifts for the ride back home");
		locations[9].setNewLocaleNorth(5);
		locations[9].setNewLocaleSouth(-1);
		locations[9].setNewLocaleEast(-1);
		locations[9].setNewLocaleWest(8);
		locations[9].setNewLocaleUp(-1);
		locations[9].setNewLocaleDown(-1);
		locations[9].setNext(null);
		locations[9].arrayOfItems[0] = new ListItem();
		locations[9].arrayOfItems[0].setarrayOfItemsName("Vulgar Tee Shirt");
		locations[9].arrayOfItems[0]
				.setarrayOfItemsDesc("A tee shirt with something very rude printed on it. ");
		locations[9].arrayOfItems[1] = new ListItem();
		locations[9].arrayOfItems[1].setarrayOfItemsName("Knock-off Purse");
		locations[9].arrayOfItems[1]
				.setarrayOfItemsDesc("A brightly colored purse that looks almost like a high end purse, but is a 1/16th the price, must be authentic.");
		locations[9].arrayOfItems[2] = new ListItem();
		locations[9].arrayOfItems[2].setarrayOfItemsName(null);
		locations[9].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[9].arrayOfItems[3] = new ListItem();
		locations[9].arrayOfItems[3].setarrayOfItemsName(null);
		locations[9].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[10] = new Locale();
		locations[10].setName("Monstrous Mountains");
		locations[10].setId(10);
		locations[10]
				.setDesc("Mountains so tall, that they appear to reach space ");
		locations[10].setNewLocaleNorth(-1);
		locations[10].setNewLocaleSouth(-1);
		locations[10].setNewLocaleEast(7);
		locations[10].setNewLocaleWest(-1);
		locations[10].setNewLocaleUp(-1);
		locations[10].setNewLocaleDown(-1);
		locations[10].setNext(null);
		locations[10].arrayOfItems[0] = new ListItem();
		locations[10].arrayOfItems[0]
				.setarrayOfItemsName("Snow From the Top of the World");
		locations[10].arrayOfItems[0]
				.setarrayOfItemsDesc("Snow from the top of the monstrous mountains. Careful, it will melt");
		locations[10].arrayOfItems[1] = new ListItem();
		locations[10].arrayOfItems[1].setarrayOfItemsName(null);
		locations[10].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[10].arrayOfItems[2] = new ListItem();
		locations[10].arrayOfItems[2].setarrayOfItemsName(null);
		locations[10].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[10].arrayOfItems[3] = new ListItem();
		locations[10].arrayOfItems[3].setarrayOfItemsName(null);
		locations[10].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[11] = new Locale();
		locations[11].setName("Abandoned Development");
		locations[11].setId(11);
		locations[11]
				.setDesc("A development that was abandoned for financial reasons. It is overtaken by weeds");
		locations[11].setNewLocaleNorth(-1);
		locations[11].setNewLocaleSouth(2);
		locations[11].setNewLocaleEast(3);
		locations[11].setNewLocaleWest(-1);
		locations[11].setNewLocaleUp(13);
		locations[11].setNewLocaleDown(-1);
		locations[11].setNext(null);
		locations[11].arrayOfItems[0] = new ListItem();
		locations[11].arrayOfItems[0].setarrayOfItemsName("Door Knob");
		locations[11].arrayOfItems[0]
				.setarrayOfItemsDesc("A bronze door knob that was on a trailer");
		locations[11].arrayOfItems[1] = new ListItem();
		locations[11].arrayOfItems[1].setarrayOfItemsName(null);
		locations[11].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[11].arrayOfItems[2] = new ListItem();
		locations[11].arrayOfItems[2].setarrayOfItemsName(null);
		locations[11].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[11].arrayOfItems[3] = new ListItem();
		locations[11].arrayOfItems[3].setarrayOfItemsName(null);
		locations[11].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[12] = new Locale();
		locations[12].setName("Rusty Dungeon");
		locations[12].setId(12);
		locations[12]
				.setDesc("A dungeon that was once used for dungeoneers to practice. Now, only the geekiest of animals hang out here ");
		locations[12].setNewLocaleNorth(-1);
		locations[12].setNewLocaleSouth(1);
		locations[12].setNewLocaleEast(-1);
		locations[12].setNewLocaleWest(3);
		locations[12].setNewLocaleUp(15);
		locations[12].setNewLocaleDown(-1);
		locations[12].setNext(null);
		locations[12].arrayOfItems[0] = new ListItem();
		locations[12].arrayOfItems[0].setarrayOfItemsName("Rusty Key");
		locations[12].arrayOfItems[0]
				.setarrayOfItemsDesc("A key that is rusty, it can unlock the door to all secrets, or somthing neat like that.");
		locations[12].arrayOfItems[1] = new ListItem();
		locations[12].arrayOfItems[1].setarrayOfItemsName(null);
		locations[12].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[12].arrayOfItems[2] = new ListItem();
		locations[12].arrayOfItems[2].setarrayOfItemsName(null);
		locations[12].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[12].arrayOfItems[3] = new ListItem();
		locations[12].arrayOfItems[3].setarrayOfItemsName(null);
		locations[12].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[13] = new Locale();
		locations[13].setName("Outer Outer Space");
		locations[13].setId(13);
		locations[13]
				.setDesc("The space that lies just outside of the multiverse ");
		locations[13].setNewLocaleNorth(-1);
		locations[13].setNewLocaleSouth(-1);
		locations[13].setNewLocaleEast(-1);
		locations[13].setNewLocaleWest(-1);
		locations[13].setNewLocaleUp(-1);
		locations[13].setNewLocaleDown(11);
		locations[13].setNext(null);
		locations[13].arrayOfItems[0] = new ListItem();
		locations[13].arrayOfItems[0]
				.setarrayOfItemsName("Comet dust From the nth Dimension");
		locations[13].arrayOfItems[0]
				.setarrayOfItemsDesc("Comet dust of the other universe. It is near priceless");
		locations[13].arrayOfItems[1] = new ListItem();
		locations[13].arrayOfItems[1].setarrayOfItemsName(null);
		locations[13].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[13].arrayOfItems[2] = new ListItem();
		locations[13].arrayOfItems[2].setarrayOfItemsName(null);
		locations[13].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[13].arrayOfItems[3] = new ListItem();
		locations[13].arrayOfItems[3].setarrayOfItemsName(null);
		locations[13].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[14] = new Locale();
		locations[14].setName("Crashed Space Ship");
		locations[14].setId(14);
		locations[14]
				.setDesc("A space ship that crashed after hitting a meteor while landing. Luckily everybody was ok ");
		locations[14].setNewLocaleNorth(-1);
		locations[14].setNewLocaleSouth(3);
		locations[14].setNewLocaleEast(-1);
		locations[14].setNewLocaleWest(-1);
		locations[14].setNewLocaleUp(-1);
		locations[14].setNewLocaleDown(-1);
		locations[14].setNext(null);

		locations[14].arrayOfItems[0] = new ListItem();
		locations[14].arrayOfItems[0].setarrayOfItemsName("Beat Up Engine");
		locations[14].arrayOfItems[0]
				.setarrayOfItemsDesc("A beat up engine from the crashed space ship. Still seems to work");
		locations[14].arrayOfItems[1] = new ListItem();
		locations[14].arrayOfItems[1].setarrayOfItemsName(null);
		locations[14].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[14].arrayOfItems[2] = new ListItem();
		locations[14].arrayOfItems[2].setarrayOfItemsName(null);
		locations[14].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[14].arrayOfItems[3] = new ListItem();
		locations[14].arrayOfItems[3].setarrayOfItemsName(null);
		locations[14].arrayOfItems[3].setarrayOfItemsDesc(null);

		locations[15] = new Locale();
		locations[15].setName("Not at all Evil Blimp");
		locations[15].setId(15);
		locations[15]
				.setDesc("Dr Mars' blimp, the one that he is using to abduct random animals to make them work for his company. Biggo Stocks. Nothing sinister here. ");
		locations[15].setNewLocaleNorth(-1);
		locations[15].setNewLocaleSouth(-1);
		locations[15].setNewLocaleEast(-1);
		locations[15].setNewLocaleWest(-1);
		locations[15].setNewLocaleUp(-1);
		locations[15].setNewLocaleDown(12);
		locations[15].setNext(null);

		locations[15].arrayOfItems[0] = new ListItem();
		locations[15].arrayOfItems[0]
				.setarrayOfItemsName("Don't Push Me Button");
		locations[15].arrayOfItems[0]
				.setarrayOfItemsDesc("A big red button, that says dont push in big, black letters.");
		locations[15].arrayOfItems[1] = new ListItem();
		locations[15].arrayOfItems[1].setarrayOfItemsName(null);
		locations[15].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[15].arrayOfItems[2] = new ListItem();
		locations[15].arrayOfItems[2].setarrayOfItemsName(null);
		locations[15].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[15].arrayOfItems[3] = new ListItem();
		locations[15].arrayOfItems[3].setarrayOfItemsName(null);
		locations[15].arrayOfItems[3].setarrayOfItemsDesc(null);
		/*--------------------------------------------------------------------------------*/
		// Create linked objects from the locations
		listForDirs.add(locations[0]);
		listForDirs.add(locations[1]);
		listForDirs.add(locations[2]);
		listForDirs.add(locations[3]);
		listForDirs.add(locations[4]);
		listForDirs.add(locations[5]);
		listForDirs.add(locations[6]);
		listForDirs.add(locations[7]);
		listForDirs.add(locations[8]);
		listForDirs.add(locations[9]);
		listForDirs.add(locations[10]);
		listForDirs.add(locations[11]);
		listForDirs.add(locations[12]);
		listForDirs.add(locations[13]);
		listForDirs.add(locations[14]);
		listForDirs.add(locations[15]);

		// array money has the money in it */
		arrayOfMoney = new Money[16];
		Money Money0 = new Money();
		Money0.setWorth(12453);
		Money Money1 = new Money();
		Money1.setWorth(50032);
		Money Money2 = new Money();
		Money2.setWorth(2642);
		Money Money3 = new Money();
		Money3.setWorth(12386);
		Money Money4 = new Money();
		Money4.setWorth(500);
		Money Money5 = new Money();
		Money5.setWorth(795);
		Money Money6 = new Money();
		Money6.setWorth(1200);
		Money Money7 = new Money();
		Money7.setWorth(10000);
		Money Money8 = new Money();
		Money8.setWorth(20);
		Money Money9 = new Money();
		Money9.setWorth(6831);
		Money Money10 = new Money();
		Money10.setWorth(7777);
		Money Money11 = new Money();
		Money11.setWorth(666);
		Money Money12 = new Money();
		Money12.setWorth(10000);
		Money Money13 = new Money();
		Money13.setWorth(25000);
		Money Money14 = new Money();
		Money14.setWorth(535);
		Money Money15 = new Money();
		Money15.setWorth(25000);
		arrayOfMoney[0] = Money0;
		arrayOfMoney[1] = Money1;
		arrayOfMoney[2] = Money2;
		arrayOfMoney[3] = Money3;
		arrayOfMoney[4] = Money4;
		arrayOfMoney[5] = Money5;
		arrayOfMoney[6] = Money6;
		arrayOfMoney[7] = Money7;
		arrayOfMoney[8] = Money8;
		arrayOfMoney[9] = Money9;
		arrayOfMoney[10] = Money10;
		arrayOfMoney[11] = Money11;
		arrayOfMoney[12] = Money12;
		arrayOfMoney[13] = Money13;
		arrayOfMoney[14] = Money14;
		arrayOfMoney[15] = Money15;

	}

	/*--------------------------------------------------------------------------------*/

	private static String sequentialSearchLocaleName(int currentLocation) {
		String retVal = null;
		Locale currentItem = new Locale();
		currentItem = game.listForDirs.getDirHead();
		boolean isFound = false;
		while ((!isFound) && (currentItem != null)) {
			if ((currentItem.getId() == currentLocation)) {
				isFound = true;
				retVal = currentItem.getName() + ".   " + currentItem.getDesc();
			} else {
				currentItem = currentItem.getNext();
			}
		}
		if (isFound) {
			return retVal;
		}
		return retVal;
	}

	/*--------------------------------------------------------------------------------*/

	private static int sequentialSearchLocaleDestination(int currentLocation,
			String dir) {
		int retVal = -1;
		int counter = 0;
		Locale currentItem = new Locale();
		currentItem = game.listForDirs.getDirHead();
		boolean isFound = false;
		while ((!isFound) && (currentItem != null)) {
			counter = counter + 1;
			if ((currentItem.getId() == currentLocation)) {
				isFound = true;
				if (dir.equalsIgnoreCase("n")) {
					retVal = currentItem.getNewLocaleNorth();
				}
				if (dir.equalsIgnoreCase("s")) {
					retVal = currentItem.getNewLocaleSouth();
				}
				if (dir.equalsIgnoreCase("e")) {
					retVal = currentItem.getNewLocaleEast();
				}
				if (dir.equalsIgnoreCase("w")) {
					retVal = currentItem.getNewLocaleWest();
				}
				if (dir.equalsIgnoreCase("u")) {
					retVal = currentItem.getNewLocaleUp();
				}
				if (dir.equalsIgnoreCase("d")) {
					retVal = currentItem.getNewLocaleDown();
				}
			} else {
				currentItem = currentItem.getNext();
			}
		}
		if (isFound) {
			return retVal;
		}
		return retVal;
	}

	/*--------------------------------------------------------------------------------*/
	private static String sequentialSearchLocaleAmeliasItemsForInventory(
			String itemToTake, int currentLocale) {
		String retVal = null;
		int counter = 0;
		Locale currentItem;
		currentItem = game.listForDirs.getDirHead();
		boolean isFound = false;
		while ((!isFound) && (currentItem != null)) {
			counter = counter + 1;
			if ((currentItem.getId() == currentLocale)) {
				isFound = true;
				if (currentItem.getItem0Name() != null) {
					if (currentItem.getItem0Name().equalsIgnoreCase(itemToTake)) {
						retVal = currentItem.getItem0Name();
					}
				}
				if (currentItem.getItem1Name() != null) {
					if (currentItem.getItem1Name().equalsIgnoreCase(itemToTake)) {
						retVal = currentItem.getItem1Name();
					}
				}
				if (currentItem.getItem2Name() != null) {
					if (currentItem.getItem2Name().equalsIgnoreCase(itemToTake)) {
						retVal = currentItem.getItem2Name();
					}
				}
				if (currentItem.getItem3Name() != null) {
					if (currentItem.getItem3Name().equalsIgnoreCase(itemToTake)) {
						retVal = currentItem.getItem3Name();
					}
				}
			} else {
				currentItem = currentItem.getNext();
			}
		}
		if (!isFound) {
			System.out.println("That item is not here.");
		}
		return retVal;
	}

	/*--------------------------------------------------------------------------------*/
	private static String sequentialSearchLocaleAmeliasItems(int currentLocation) {
		String retVal = "";
		Locale currentItem;
		int counter = 0;
		currentItem = game.listForDirs.getDirHead();
		boolean isFound = false;
		boolean isInInventory;
		while ((!isFound) && (currentItem != null)) {
			if ((currentItem.getId() == currentLocation)) {
				isFound = true;
				isInInventory = false;
				if (currentItem.getItem0Name() != null) {
					{
						for (int i = 0; i < numberOfItemsInInventory + 1; i++) {
							if ((inventory[i] != null)
									&& (currentItem.getItem0Name()
											.equalsIgnoreCase(inventory[i]))) {
								isInInventory = true;
							}
						}
					}
					if (!isInInventory) {
						counter = counter + 1;
						retVal = retVal + currentItem.getItem0Name() + ".  "
								+ currentItem.getItem0Description() + ".\n";
					}
				}
				isInInventory = false;
				if (currentItem.getItem1Name() != null) {
					{

						for (int i = 0; i < numberOfItemsInInventory + 1; i++) {
							if ((inventory[i] != null)
									&& (currentItem.getItem1Name()
											.equalsIgnoreCase(inventory[i]))) {
								isInInventory = true;
							}
						}
					}
					if (!isInInventory) {
						counter = counter + 1;
						retVal = retVal + currentItem.getItem1Name() + ".  "
								+ currentItem.getItem1Description() + ".\n";
					}
				}
				isInInventory = false;
				if (currentItem.getItem2Name() != null) {
					{

						for (int i = 0; i < numberOfItemsInInventory + 1; i++) {
							if ((inventory[i] != null)
									&& (currentItem.getItem2Name()
											.equalsIgnoreCase(inventory[i]))) {
								isInInventory = true;
							}
						}
					}
					if (!isInInventory) {
						counter = counter + 1;
						retVal = retVal + currentItem.getItem2Name() + ".  "
								+ currentItem.getItem2Description() + ".\n";
					}
				}
				isInInventory = false;
				if (currentItem.getItem3Name() != null) {
					{

						for (int i = 0; i < numberOfItemsInInventory + 1; i++) {
							if ((inventory[i] != null)
									&& (currentItem.getItem3Name()
											.equalsIgnoreCase(inventory[i]))) {
								isInInventory = true;
							}
						}
					}
					if (!isInInventory) {
						counter = counter + 1;
						retVal = retVal + currentItem.getItem3Name() + ".  "
								+ currentItem.getItem3Description() + ".\n";
					}
				}
			} else {
				currentItem = currentItem.getNext();
			}
		}
		if (isFound) {
			System.out.println("There are " + counter + " free item(s) here.");
			return retVal;
		}
		return retVal;
	}

	/*--------------------------------------------------------------------------------*/
	private static void updateDisplay() {
		if (totalMoves == 0 && currentLocale == 0) {
			System.out.println("Darth Vs. The Evil Dr. Mars");
		}
		System.out.println(sequentialSearchLocaleName(currentLocale));
		validMove(currentLocale);
		String tempName;
		tempName = sequentialSearchLocaleAmeliasItems(currentLocale);
		System.out.println(tempName);
		System.out.println("There are "
				+ arrayOfMoney[currentLocale].getWorth() + " Gold Coins here");
		System.out
				.println("----------------------------------------------------------------");
	}

	/*--------------------------------------------------------------------------------*/
	private static void getCommand() {
		Scanner inputReader = new Scanner(System.in);
		command = inputReader.nextLine(); // command is global.
	}

	/*--------------------------------------------------------------------------------*/
	private static void navigate() {
		int newLocation = 0;
		String dir = "INVALID";
		if (command.equalsIgnoreCase("north") || command.equalsIgnoreCase("n")) {
			dir = "n";
		} else if (command.equalsIgnoreCase("south")
				|| command.equalsIgnoreCase("s")) {
			dir = "s";
		} else if (command.equalsIgnoreCase("east")
				|| command.equalsIgnoreCase("e")) {
			dir = "e";
		} else if (command.equalsIgnoreCase("west")
				|| command.equalsIgnoreCase("w")) {
			dir = "w";
		} else if (command.equalsIgnoreCase("up")
				|| command.equalsIgnoreCase("u")) {
			dir = "u";
		} else if (command.equalsIgnoreCase("down")
				|| command.equalsIgnoreCase("d")) {
			dir = "d";
		} else if (command.equalsIgnoreCase("quit")
				|| command.equalsIgnoreCase("q")) {
			quit();
		} else if (command.equalsIgnoreCase("help")
				|| command.equalsIgnoreCase("h")) {
			help();
		} else if (command.equalsIgnoreCase("take")
				|| command.equalsIgnoreCase("t")) {
			take();
		} else if (command.equalsIgnoreCase("inventory")
				|| command.equalsIgnoreCase("i")) {
			inventory();
		} else if (command.equalsIgnoreCase("map")
				|| command.equalsIgnoreCase("m")) {
			asciiMap();
		} else if (command.equalsIgnoreCase("pick up")
				|| command.equalsIgnoreCase("p")) {
			pickup();
		} else if (command.equalsIgnoreCase("buy")
				|| command.equalsIgnoreCase("b")) {
			buy();
		} else if (command.equalsIgnoreCase("repair")
				|| command.equalsIgnoreCase("r")) {
			install();
		} else {
			System.out
					.print("That is not a valid command. Valid commands are n(north),s(south),w(west),e(east),h(help),q(queit),t(take),i(inventory),m(map)and a(attack). Both upper and lower case are valid commands");
		}

		if (!dir.equalsIgnoreCase("INVALID")) { // This means a dir was set.
			totalMoves = totalMoves + 1;
			if ("nsewud".indexOf(dir) > -1) {
				newLocation = sequentialSearchLocaleDestination(currentLocale,
						dir);
			}
			if (newLocation == -1) {
				System.out.println("You cannot go that way. Press h for help");
				validMove(newLocation);
			} else {
				validMoves = validMoves + 1;
				currentLocale = newLocation;
				totalScore();
				System.out.println("You have made " + totalMoves + " moves.");
				achievementRatio();
			}
		}

	}

	/*--------------------------------------------------------------------------------*/
	private static void help() {
		System.out.println("The commands are as follows:");
		System.out.println(" These are the directions you can travel");
		System.out.println("   n/north");
		System.out.println("   s/south");
		System.out.println("   e/east");
		System.out.println("   w/west");
		System.out.println("   u/up");
		System.out.println("   d/down");
		System.out.println("These are the commands for an action");
		System.out.println("   t/take");
		System.out.println("   i/inventory");
		System.out.println("   m/map");
		System.out.println("   p/pick up");
		System.out.println("   r/repair (installs engine in rocket ship in Whittie's Space Ship Dealer");
		System.out.println("   q/quit");
	}

	/*--------------------------------------------------------------------------------*/
	private static void quit() {
		stillPlaying = false;

	}

	/*--------------------------------------------------------------------------------*/
	private static void take() {
		String takenItem;
		System.out.println("Which item do you wish to take?");

		Scanner inputReader = new Scanner(System.in);
		String itemToTake = inputReader.nextLine();
		takenItem = sequentialSearchLocaleAmeliasItemsForInventory(itemToTake,
				currentLocale);
		if (takenItem == null) {
			System.out.println("That item isn't here.");
			System.out
					.println("----------------------------------------------------------------");
		} else {
			System.out.println("You took the " + takenItem);
			System.out
					.println("----------------------------------------------------------------");
		}
		if (itemToTake.equalsIgnoreCase("map")) {
			tookMap = true;
		}
		inventory[numberOfItemsInInventory] = takenItem;
		numberOfItemsInInventory = numberOfItemsInInventory + 1;

	}

	/*--------------------------------------------------------------------------------*/
	private static ListItem binarySearchArray(String target) {
		ListItem retVal = null;
		System.out.println("Binary Searching for " + target + ".");
		ListItem currentItem = new ListItem();
		boolean isFound = false;
		int counter = 0;
		int low = 0;
		int high = game.arrayForMagicShop.length - 1; // because 0-based arrays
		while ((!isFound) && (low <= high)) {
			int mid = Math.round((high + low) / 2);
			currentItem = game.arrayForMagicShop[mid];
			if (currentItem.getListItemName().equalsIgnoreCase(target)) {
				// We found it!
				isFound = true;
				retVal = currentItem;
			} else {
				// Keep looking.
				counter++;
				if (currentItem.getListItemName().compareToIgnoreCase(target) > 0) {
					// target is higher in the list than the currentItem (at
					// mid)
					high = mid - 1;
				} else {
					// target is lower in the list than the currentItem (at mid)
					low = mid + 1;
				}
			}
		}
		if (isFound) {
			System.out.println("Found " + target + " after " + counter
					+ " comparisons.");
		} else {
			System.out.println("Could not find " + target + " in " + counter
					+ " comparisons.");
		}
		return retVal;
	}

	/*--------------------------------------------------------------------------------*/
	private static void buy() {
		try {
			if (currentLocale != 1)
				System.out.println("You can't buy anything here");
			else {
				System.out.println("What do you want to buy?");
				Scanner inputReader = new Scanner(System.in);
				String itemToBuy = inputReader.nextLine();
				ListItem tempItem = new ListItem();
				tempItem = binarySearchArray(itemToBuy);
				int purchasePrice = tempItem.getListItemCost();
				System.out.println("purchasePrice = "
						+ tempItem.getListItemCost());
				System.out.println("coinPurse = " + coinPurse
						+ " purchasePrice = " + purchasePrice);
				if (coinPurse >= purchasePrice) {
					System.out.println("You have purchased the "
							+ tempItem.getListItemName());
					coinPurse = coinPurse - purchasePrice;
					inventory[numberOfItemsInInventory] = tempItem
							.getListItemName();
					numberOfItemsInInventory = numberOfItemsInInventory + 1;

				} else {
					System.out
							.println("You don't have enough money to buy that.");
				}
			}
		} catch (NullPointerException ex) {
			System.out.println("That item is not in the list. ");
		}
	}

	/*--------------------------------------------------------------------------------*/
	// pick is similar to take
	private static void pickup() {
		if (arrayOfMoney[currentLocale].getWorth() == 0) {
			System.out.println("There are no magic inflated dollars here");
		} else {
			coinPurse = coinPurse + arrayOfMoney[currentLocale].getWorth();
			arrayOfMoney[currentLocale].setWorth(0);
		}
	}

	/*--------------------------------------------------------------------------------*/
	private static void install() {
		boolean hasEngine = false;
		for (int i = 0; i < numberOfItemsInInventory; i++) {
			if (inventory[i].equalsIgnoreCase("Beat Up Engine"))
				;
			{
				hasEngine = true;
			}
		}
		if (currentLocale != 5) {
			System.out
					.println("You can not install the spaceship engine here.");
		} else {
			if (hasEngine = false) {
				System.out.println("You don't have the engine");
			} else {
				System.out
						.println("You repaired the pink spaceship with the engine.");
				System.out
						.println("You climb into the spaceship.  It blasts off and takes you home.");
				System.out.println("You have won the game!");
				stillPlaying = false;
			}
		}
	}

	// inventory
	private static void inventory() {
		int i;
		System.out.println("You have " + numberOfItemsInInventory
				+ " items in bag");
		for (i = 0; i < numberOfItemsInInventory; i++) {
			System.out.println(inventory[i]);
		}
		System.out.println();
		System.out.println("You have " + coinPurse
				+ " gold coins in your wallet");
		System.out.println();

	}

	/*--------------------------------------------------------------------------------*/
	private static void validMove(int currentLocale) {
		int newLocationTemp;
		newLocationTemp = sequentialSearchLocaleDestination(currentLocale, "n");
		if (newLocationTemp != -1) {
			goodDir = goodDir + "north ";
		}
		newLocationTemp = sequentialSearchLocaleDestination(currentLocale, "s");
		if (newLocationTemp != -1) {
			goodDir = goodDir + "south ";
		}
		newLocationTemp = sequentialSearchLocaleDestination(currentLocale, "e");
		if (newLocationTemp != -1) {
			goodDir = goodDir + "east ";
		}
		newLocationTemp = sequentialSearchLocaleDestination(currentLocale, "w");
		if (newLocationTemp != -1) {
			goodDir = goodDir + "west ";
		}
		newLocationTemp = sequentialSearchLocaleDestination(currentLocale, "u");
		if (newLocationTemp != -1) {
			goodDir = goodDir + "up ";
		}
		newLocationTemp = sequentialSearchLocaleDestination(currentLocale, "d");
		if (newLocationTemp != -1) {
			goodDir = goodDir + "down";
		}
		System.out.println("You can move in these directions: " + goodDir);
		goodDir = "";
	}

	/*--------------------------------------------------------------------------------*/
	// now for the ascii map and ascii art for the locales
	private static void asciiMap() {
		if (tookMap) {
			System.out
					.println("                              [_outer_outer_space]              [_crashed_space_ship_]             [_not_at_all_evil_blimp]");
			System.out
					.println("                                       |                                  |                                    |  ");
			System.out
					.println("                             [ Abandoned_Development_]----------[_unpleasant_meadows_]----------------[_rusty_dungeon_]");
			System.out
					.println("                                       |                                  |                                    |");
			System.out
					.println("                                [_icy_______cave_]-----------------[_kennel_town_]---------------------[_magick_shoppe_]");
			System.out
					.println("                                       |                                  |                                    |");
			System.out
					.println("                                [_vet_of_horrors__]----------------[_cat_nip_city_]------------[_whittie's space-ship_dealer]");
			System.out
					.println("                                      |                                  |                                    |");
			System.out
					.println("[_monstrous_mountains_]------ [_uncanny__valley_]----------[_dracos_dragon_meat_factory_]----------[_cheesie_tourist_area]");
		} else {
			System.out.println("You haven't taken the map");
		}
	}
}
