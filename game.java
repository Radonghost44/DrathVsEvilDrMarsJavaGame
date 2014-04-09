package game;

import java.io.File;
import java.text.DecimalFormat;
import java.io.FileNotFoundException;
// Amelia Beckham
import java.util.Scanner;

public class game {
	// Globals
	public static String goodDir = "";
	public static final int MAX_LOCALES = 16; // Total number of rooms/locations
	public static int currentLocale = 0; // Player starts in locale 0.
	public static String command; // Game commands
	public static String itemToTake;
	public static int localInventory = 0;
	public static boolean stillPlaying = true; // Controls the game loop.
	public static Locale[] locations;
	public static int[][] nav; // An uninitialized array of type int int.
	public static boolean hasBeen = false; // used to keep track of score
	public static int score = 5; // score is 5 for a new location
	public static int[] roomCheck = new int[16];
	public static boolean tookMap = false;
	public static int numberOfItemsInInventory = 0;
	public static int numberOfItemsInGame = 21;
	public static String inventory[] = new String[700];// uses booleans, Item
														// class
														// to make this work.
	public static int currentLocation;
	public static int[] inLocale = new int[25];// array that tells where the
												// item is located
	public static Item[] arrayOfOtherItems = new Item[25];// similar to the
															// array of locales,
	// location, only this one is for the
	// items.
	public static int totalMoves = 0;
	public static Money arrayOfMoney[];
	public static int wallet[] = new int[16];// Is simlar to invetory, but is
												// used for the money
	public static int moneyToPickUp;
	public static int coinPurse = 0;
	public static int numberOfMoneyInWallet = 0;
	public static int numberOfMoneyInGame = 16;
	public static int[] moneyInLocale = new int[16];
	public static ListofItems lm1 = new ListofItems();
	public static ListofLocales listForDirs = new ListofLocales();
	public static Locale locn;

	public static void main(String[] args) {
		// Make some list items.
		for (int i = 0; i < 700; i++) {
			inventory[i] = null;
		}
		ListItem item25 = new ListItem();
		item25.setListItemName("+2 ring");
		item25.setListItemNameDesc("precious");
		item25.setListItemNameCost(42);
		item25.setListItemNameNext(null); // Redundant, but safe.

		ListItem item26 = new ListItem();
		item26.setListItemName("Cloak of Doom");
		item26.setListItemNameDesc("Scary");
		item26.setListItemNameCost(666);
		item26.setListItemNameNext(null); // Still redundant. Still safe.

		ListItem item27 = new ListItem();
		item27.setListItemName("broad sword");
		item27.setListItemNameDesc("sharp");
		item27.setListItemNameCost(12);
		item27.setListItemNameNext(null); // Still redundant. Still safe.

		// items 0-3 are for the magick shoppe- list items beacause they are
		// only avalbie in the magick shoppe
		// item0 magical summoning thingy //
		ListItem item0 = new ListItem();
		item0.setListItemName("Magical Summoning Thingy");
		item0.setListItemNameDesc("Rumoured to be from another dimension, it can summon random creatures.");
		item25.setListItemNameCost(728);
		item25.setListItemNameNext(null); // Redundant, but safe.

		// /item1 rusty hammer//
		ListItem item1 = new ListItem();
		item1.setListItemName("Rusty Hammer");
		item1.setListItemNameDesc("An old hammer used for hitting nails  ");
		item25.setListItemNameCost(52);
		item25.setListItemNameNext(null); // Redundant, but safe.

		// item2 wand with low batterys //
		ListItem item2 = new ListItem();
		item2.setListItemName("Wand with Low Batteries");
		item2.setListItemNameDesc("A wand that doesnt work well, since it needs some new AA batteries");
		item25.setListItemNameCost(14);
		item25.setListItemNameNext(null); // Redundant, but safe.

		// item 3-- broken sword //
		ListItem item3 = new ListItem();
		item3.setListItemName("Broken Sword");
		item3.setListItemNameDesc("A sword that is missing its tip");
		item25.setListItemNameCost(10);
		item25.setListItemNameNext(null); // Redundant, but safe.

		// Make the list manager.
		lm1.setName("Magic Items");
		lm1.setDesc("these are what are for sale.");

		// Put items in the list.
		lm1.add(item0);
		lm1.add(item1);
		lm1.add(item2);
		lm1.add(item3);
		lm1.add(item26);
		lm1.add(item27);
		lm1.add(item25);

		final String fileName = "magic.txt";
		File myFile = new File(fileName);
		try {
			Scanner input = new Scanner(myFile);
			while (input.hasNext()) {
				String itemName = input.nextLine();
				ListItem fileItem = new ListItem();
				fileItem.setListItemName(itemName);
				fileItem.setListItemNameDesc(null);
				fileItem.setListItemNameCost((int) (Math.random() * 1000));
				fileItem.setListItemNameNext(null); // Still redundant. Still
													// safe.

				lm1.add(fileItem);

			}
			input.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File not found. " + ex.toString());
		}

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
			int startLocation = Integer.parseInt(args[0]); // TODO We need to
															// catch a possible
															// exception here.
			// Check that the passed-in value for startLocation is within the
			// range of actual locations.
			if (startLocation >= 0 && startLocation <= MAX_LOCALES) {
				currentLocale = startLocation;
			}
		}

		// Get the game started.
		init();
		/* System.out.println(listForDirs); */
		updateDisplay();

		// Game Loop
		while (stillPlaying) {

			getCommand();
			navigate();
			if (stillPlaying == true) {
				updateDisplay();
				if (currentLocale == 1) {// print out list
					System.out.println("Current items for sale are :"
							+ lm1.toString());
				}
			}
		}

		// We're done. Thank the player and exit.
		System.out.println("Thank you for playing.");
	}

	/*--------------------------------------------------------------------------------*/
	public static void totalScore() {
		if (roomCheck[currentLocale] == 0) {
			score = score + 5;
			roomCheck[currentLocale] = 1;
		}
		;
		System.out.println("Your current score is " + score);
	};

	/*--------------------------------------------------------------------------------*/
	public static void achievementRatio() {
		int ar = score / totalMoves;
		try {
			ar = score / totalMoves;
		} catch (java.lang.ArithmeticException bendingTheLawsOfMath) {
			System.out.print("Your achievement ratio is 0");
		}
		System.out.println("Your achievement ratio is " + ar);
	}

	/*--------------------------------------------------------------------------------*/

	/*--------------------------------------------------------------------------------*/
	private static void init() {
		// Initialize any uninitialized globals.
		command = new String();
		stillPlaying = true;
		// Set up the location list.
		// tracker keeps track of if an item is an a location
		// inventory places the item in a inventroy
		// items are numbered in the order they were initialized

		// locale 0-- kennel town; //

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

		// /locale 1-- magick shoppe //
		// loc1-- north
		locations[1] = new Locale();
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
		// magic shop is different form most locs, beacuse it uses a list for
		// its items, not ArrayofItems.

		// locale 2-- icy cave //
		locations[2] = new Locale();
		locations[2].setName("Icy Cave");
		locations[2].setId(2);
		locations[2]
				.setDesc("A deep and cold cave where various wild creatures live");
		locations[2].setNewLocaleNorth(11);
		locations[2].setNewLocaleSouth(6);
		locations[2].setNewLocaleEast(1);
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

		// locale 3-- unpleasent meadows //
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
		// unpleanse meadows has 2 items
		// item 10-- three headed fish //
		locations[3].arrayOfItems[0] = new ListItem();
		locations[3].arrayOfItems[0].setarrayOfItemsName("Three Headed Fish");
		locations[3].arrayOfItems[0]
				.setarrayOfItemsDesc("A dull brown fish that has three heads and a lot of teeth. Quite frightening , actually");
		// item 11-- tin drum of toxic waste //
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

		// locale 4-- catnip city //
		locations[4] = new Locale();
		locations[4].setName("Catnip City");
		locations[4].setId(4);
		locations[4]
				.setDesc("A newly restored city. Rumor has it it was built by an ancient society millions of years ago ");
		locations[4].setNewLocaleNorth(0);
		locations[4].setNewLocaleSouth(8);
		locations[4].setNewLocaleEast(5);
		locations[4].setNewLocaleWest(6);
		locations[4].setNewLocaleUp(-1);
		locations[4].setNewLocaleDown(-1);
		locations[4].setNext(null);
		// item 12-- taxi ticket, located at Catnip City //
		locations[4].arrayOfItems[0] = new ListItem();
		locations[4].arrayOfItems[0].setarrayOfItemsName("Taxi Ticket");
		locations[4].arrayOfItems[0]
				.setarrayOfItemsDesc("A ticket for one of the bright yellow taxis. ");
		// item 16-- album of strange music, found in catnip city //
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

		// locale 5-- whittie's space ship dealer //
		locations[5] = new Locale();
		locations[5].setName("Whittie's Space Ship Dealer");
		locations[5].setId(5);
		locations[5]
				.setDesc("A utilitarian building that sells different kinds of space ships  ");
		locations[5].setNewLocaleNorth(1);
		locations[5].setNewLocaleSouth(5);
		locations[5].setNewLocaleEast(-1);
		locations[5].setNewLocaleWest(4);
		locations[5].setNewLocaleUp(-1);
		locations[5].setNewLocaleDown(-1);
		locations[5].setNext(null);

		// items 3-7 are for whitie's space ship dealer
		// item 4-- Verne Spacemaster //
		locations[5].arrayOfItems[0] = new ListItem();
		locations[5].arrayOfItems[0].setarrayOfItemsName("Verne Spacemaster");
		locations[5].arrayOfItems[0]
				.setarrayOfItemsDesc("A sliver box like spaceship with a navy intior");
		// item 5-- Anticent Alien //
		locations[5].arrayOfItems[1] = new ListItem();
		locations[5].arrayOfItems[1].setarrayOfItemsName("Ancient Alien");
		locations[5].arrayOfItems[1]
				.setarrayOfItemsDesc("A hot pink flying saucer. It has chrome trim around it and a purple interior. In other words, the girliest space ship in existence");
		// item 6-- Benoynd Infintie //
		locations[5].arrayOfItems[2] = new ListItem();
		locations[5].arrayOfItems[2].setarrayOfItemsName("Benoynd Infintie");
		locations[5].arrayOfItems[2]
				.setarrayOfItemsDesc("A trianglar space ship that is dark purple and has neon yellow intor.");
		// item 7-- Expermental AIrcraft- Aera 51 edition //
		locations[5].arrayOfItems[3] = new ListItem();
		locations[5].arrayOfItems[3]
				.setarrayOfItemsName("Experimental Aircraft-Area 51 Edition");
		locations[5].arrayOfItems[3]
				.setarrayOfItemsDesc("A gold space ship that is shaped like a sword. It has a tiny black cockpit.");

		// locale 6-- vet of horrors //
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
		// item 13-- srignine of pain killer, located at vet of horrors //
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

		// locale 7-- uncanny valley //
		locations[7] = new Locale();
		locations[7].setName("Uncanny Valley");
		locations[7].setId(7);
		locations[7]
				.setDesc("A creepy valley were the clones are rumoured to live ");
		locations[7].setNewLocaleNorth(6);
		locations[7].setNewLocaleSouth(-1);
		locations[7].setNewLocaleEast(8);
		locations[7].setNewLocaleWest(10);
		locations[7].setNewLocaleUp(-1);
		locations[7].setNewLocaleDown(-1);

		locations[7].setNext(null);

		// item 14--clone paw, located at uncanny valley //
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

		// locale 8-- Draco's Dragon Meat Facotry //
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

		// item 15-- drang fang, llocated at dragon meat factory //
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

		// locale 9-- cheesy tourest area //
		locations[9] = new Locale();
		locations[9].setName("Cheesy Tourist Area");
		locations[9].setId(9);
		locations[9]
				.setDesc("A tourist trap where tourist can buy cheap gifts for the ride back home");
		locations[9].setNewLocaleNorth(5);
		locations[9].setNewLocaleSouth(-1);
		locations[9].setNewLocaleEast(-1);
		locations[9].setNewLocaleWest(8);
		locations[9].setNewLocaleUp(-1);
		locations[9].setNewLocaleDown(-1);
		locations[9].setNext(null);
		// cheesy tourest aera has 2 items
		// /item 17-- vulgar tee shirt //
		locations[9].arrayOfItems[0] = new ListItem();
		locations[9].arrayOfItems[0].setarrayOfItemsName("Vulgar Tee Shirt");
		locations[9].arrayOfItems[0]
				.setarrayOfItemsDesc("A tee shirt with something very rude printed on it. ");
		// item 18-- knock off purse //
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

		// locale 10-- monturus mountains //
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
		// item 19-- snow form the top of the world
		locations[10].arrayOfItems[0] = new ListItem();
		locations[10].arrayOfItems[0]
				.setarrayOfItemsName("Snow From the Top of the World");
		locations[10].arrayOfItems[0]
				.setarrayOfItemsDesc("Snow from the top of the monstrous montians. Careful, it will melt");
		locations[10].arrayOfItems[1] = new ListItem();
		locations[10].arrayOfItems[1].setarrayOfItemsName(null);
		locations[10].arrayOfItems[1].setarrayOfItemsDesc(null);
		locations[10].arrayOfItems[2] = new ListItem();
		locations[10].arrayOfItems[2].setarrayOfItemsName(null);
		locations[10].arrayOfItems[2].setarrayOfItemsDesc(null);
		locations[10].arrayOfItems[3] = new ListItem();
		locations[10].arrayOfItems[3].setarrayOfItemsName(null);
		locations[10].arrayOfItems[3].setarrayOfItemsDesc(null);

		// locale 11-- abanded delviment //
		locations[11] = new Locale();
		locations[11].setName("Abandoned Development");
		locations[11].setId(11);
		locations[11]
				.setDesc("A development that was abandoned for finanical reasons. It is overtaken by weeds");
		locations[11].setNewLocaleNorth(-1);
		locations[11].setNewLocaleSouth(2);
		locations[11].setNewLocaleEast(3);
		locations[11].setNewLocaleWest(-1);
		locations[11].setNewLocaleUp(13);
		locations[11].setNewLocaleDown(-1);
		locations[11].setNext(null);
		// item 20-- door knob, located at abanoded delvement //
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

		// locale 12-- rusty dungeon //
		locations[12] = new Locale();
		locations[12].setName("Rusty Dungeon");
		locations[12].setId(12);
		locations[12]
				.setDesc("A dungeon that was once used for dungoneers to practice. Now, only the geekiest of animals hang out here ");
		locations[12].setNewLocaleNorth(-1);
		locations[12].setNewLocaleSouth(1);
		locations[12].setNewLocaleEast(-1);
		locations[12].setNewLocaleWest(3);
		locations[12].setNewLocaleUp(15);
		locations[12].setNewLocaleDown(-1);
		locations[12].setNext(null);
		// item 21-- rusty key, located at therusty dungeon //
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

		// locale 13-- outer outer space //
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
		// item 23-- comet dust form the nth dimsion,/
		locations[13].arrayOfItems[0] = new ListItem();
		locations[13].arrayOfItems[0]
				.setarrayOfItemsName("Comet Dust From the nth Dimension");
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

		// locale 14-- crashed space ship //
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

		// item 22- bent up enigine //
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

		// locale 15-- not at all evil blip //
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

		// item 24-- dont push me button
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

		// listForDirs.setDirName("direction list");
		// listForDirs.setDirDesc("these are the directions for movenemt");

		/*--------------------------------------------------------------------------------*/

		// list of locales and their directions
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
	private static int sequentialSearchLocaleDown(ListofLocales listForDirs,
			int currentLocation) {
		int retVal = -1;
		int counter = 0;
		Locale currentItem;
		currentItem = listForDirs.getDirHead();
		boolean isFound = false;
		while ((!isFound) && (currentItem != null)) {
			counter = counter + 1;
			if ((currentItem.getId() == currentLocation)) {
				isFound = true;
				retVal = currentItem.getNewLocaleDown();
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
	private static int sequentialSearchLocaleUp(ListofLocales listForDirs,
			int currentLocation) {
		int retVal = -1;
		int counter = 0;
		Locale currentItem;
		currentItem = listForDirs.getDirHead();
		boolean isFound = false;
		while ((!isFound) && (currentItem != null)) {
			counter = counter + 1;
			if ((currentItem.getId() == currentLocation)) {
				isFound = true;
				retVal = currentItem.getNewLocaleUp();
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
	private static int sequentialSearchLocaleWest(ListofLocales listForDirs,
			int currentLocation) {
		int retVal = -1;
		int counter = 0;
		Locale currentItem;
		currentItem = listForDirs.getDirHead();
		boolean isFound = false;
		while ((!isFound) && (currentItem != null)) {
			counter = counter + 1;
			if ((currentItem.getId() == currentLocation)) {
				isFound = true;
				retVal = currentItem.getNewLocaleWest();
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
	private static int sequentialSearchLocaleEast(ListofLocales listForDirs,
			int currentLocation) {
		int retVal = -1;
		int counter = 0;
		Locale currentItem;
		currentItem = listForDirs.getDirHead();
		boolean isFound = false;
		while ((!isFound) && (currentItem != null)) {
			counter = counter + 1;
			if ((currentItem.getId() == currentLocation)) {
				isFound = true;
				retVal = currentItem.getNewLocaleEast();
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
	private static int sequentialSearchLocaleSouth(ListofLocales listForDirs,
			int currentLocation) {
		int retVal = -1;
		int counter = 0;
		Locale currentItem;
		currentItem = listForDirs.getDirHead();
		boolean isFound = false;
		while ((!isFound) && (currentItem != null)) {
			counter = counter + 1;
			if ((currentItem.getId() == currentLocation)) {
				isFound = true;
				retVal = currentItem.getNewLocaleSouth();
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
	private static int sequentialSearchLocaleNorth(ListofLocales listForDirs,
			int currentLocation) {
		int retVal = -1;
		int counter = 0;
		Locale currentItem = new Locale();
		currentItem = listForDirs.getDirHead();
		boolean isFound = false;
		while ((!isFound) && (currentItem != null)) {
			counter = counter + 1;
			if ((currentItem.getId() == currentLocation)) {
				isFound = true;
				retVal = currentItem.getNewLocaleNorth();
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
			String itemToTake, ListofLocales listForDirs, int currentLocale) {
		String retVal = null;
		int counter = 0;
		Locale currentItem;
		currentItem = listForDirs.getDirHead();
		boolean isFound = false;
		while ((!isFound) && (currentItem != null)) {
			counter = counter + 1;
			if ((currentItem.getId() == currentLocale)) {
				isFound = true;
				if (currentItem.getItem0Name() != null) {
					System.out.println("currentItem.getItem0Name = "  +currentItem.getItem0Name() + " itemToTake = " + itemToTake);
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
	private static String sequentialSearchLocaleAmeliasItems(
			ListofLocales listForDirs, int currentLocation) {
		String retVal = "";
		Locale currentItem;
		int counter = 0;
		currentItem = listForDirs.getDirHead();
		boolean isFound = false;
		boolean isInInventory = false;
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
					if (isInInventory == false) {
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
					if (isInInventory == false) {
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
					if (isInInventory == false) {
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
					if (isInInventory == false) {
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
			System.out.println("There are " + counter + " item(s) here.");
			return retVal;
		}
		return retVal;
	}

	/*--------------------------------------------------------------------------------*/
	private static void updateDisplay() {
		if (totalMoves == 0 && currentLocale == 0) {
			System.out.println("Darth Vs. The Evil Dr. Mars");
		}

		validMove(currentLocale);
		String tempName = "";
		tempName = sequentialSearchLocaleAmeliasItems(listForDirs,
				currentLocale);
		System.out.println(tempName);
		System.out.println("There are "
				+ arrayOfMoney[currentLocale].getWorth() + " Gold Coins here");
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
		} else {
			System.out
					.print("That is not a valid command. Valid commands are n(north),s(south),w(west),e(east),h(help),q(queit),t(take),i(inventory),m(map)and a(attack). Both upper and lower case are valid commands");
		}
		;

		if (!dir.equalsIgnoreCase("INVALID")) { // This means a dir was set.
			totalMoves = totalMoves + 1;
			if (dir.equalsIgnoreCase("n")) {
				newLocation = sequentialSearchLocaleNorth(listForDirs,
						currentLocale);
			}
			if (dir.equalsIgnoreCase("s")) {
				newLocation = sequentialSearchLocaleSouth(listForDirs,
						currentLocale);
			}
			if (dir.equalsIgnoreCase("e")) {
				newLocation = sequentialSearchLocaleEast(listForDirs,
						currentLocale);
			}
			if (dir.equalsIgnoreCase("w")) {
				newLocation = sequentialSearchLocaleWest(listForDirs,
						currentLocale);
			}
			if (dir.equalsIgnoreCase("u")) {
				newLocation = sequentialSearchLocaleUp(listForDirs,
						currentLocale);
			}
			if (dir.equalsIgnoreCase("d")) {
				newLocation = sequentialSearchLocaleUp(listForDirs,
						currentLocale);
			}
			if (newLocation == -1) {
				System.out.println("You cannot go that way. Press h for help");
				validMove(newLocation);
			} else {
				currentLocale = newLocation;
				totalScore();
				System.out.println("You have made " + totalMoves + " moves.");
				achievementRatio();
				validMove(newLocation);
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
		System.out.println("   q/quit");
	}

	/*--------------------------------------------------------------------------------*/
	private static void quit() {
		stillPlaying = false;
	}

	/*--------------------------------------------------------------------------------*/
	private static void take() {
		String takenItem = null;
		System.out.println("Which item do you wish to take?");
		
			Scanner inputReader = new Scanner(System.in);
			itemToTake = inputReader.nextLine();
			takenItem = sequentialSearchLocaleAmeliasItemsForInventory(
					itemToTake, listForDirs, currentLocale);
			System.out.println("You took the " + itemToTake);
			if (itemToTake.equalsIgnoreCase("map")) {
				tookMap = true;
			}
			inventory[numberOfItemsInInventory] = takenItem;
			numberOfItemsInInventory = numberOfItemsInInventory + 1;
		
	}

	/*--------------------------------------------------------------------------------*/
	private static ListItem sequentialSearchItem(ListofItems lim,
			String targetName) {
		ListItem retVal = null;
		System.out.println("Searching for " + targetName + ".");
		int counter = 0;
		ListItem currentItem = new ListItem();
		currentItem = lim.getHead();
		boolean isFound = false;
		while ((!isFound) && (currentItem != null)) {
			counter = counter + 1;
			if (currentItem.getListItemName().equalsIgnoreCase(targetName)) {
				isFound = true;
				retVal = currentItem;
			} else {
				currentItem = currentItem.getListItemNameNext();
			}
		}
		if (isFound) {
			return currentItem;
		} else {
			System.out.println("Cound not find " + targetName + " in "
					+ counter + " comparisons.");
		}
		return retVal;
	}

	/*--------------------------------------------------------------------------------*/
	private static void buy() {
		if (currentLocale != 1)
			System.out.println("You can't buy anything here");
		else {
			System.out.println("What do you want to buy?");
			Scanner inputReader = new Scanner(System.in);
			String itemToBuy = inputReader.nextLine();
			ListItem tempItem = new ListItem();
			tempItem = sequentialSearchItem(lm1, itemToBuy);
			int purchasePrice = tempItem.getListItemNameCost();
			System.out.println("purchasePrice = "
					+ tempItem.getListItemNameCost());
			System.out.println("coinPurse = " + coinPurse + " purchasePrice = "
					+ purchasePrice);
			if (coinPurse >= purchasePrice) {
				System.out.println("You have purchased the " + itemToBuy);
				coinPurse = coinPurse - purchasePrice;
				inventory[numberOfItemsInInventory] = itemToBuy;
				numberOfItemsInInventory = numberOfItemsInInventory + 1;
				lm1.removeItem(lm1, itemToBuy);

			} else {
				System.out.println("You don't have enough money to buy that.");
			}
		}
	}

	/*--------------------------------------------------------------------------------*/
	// pick is simlar to take , in fact a modifed take method, to work with
	// doulbes
	private static void pickup() {

		if (arrayOfMoney[currentLocale].getWorth() == 0) {
			System.out.println("There are no magic inflated dollars here");
		}

		else {
			coinPurse = coinPurse + arrayOfMoney[currentLocale].getWorth();
			arrayOfMoney[currentLocale].setWorth(0);
		}
	}

	/*--------------------------------------------------------------------------------*/
	// inventory
	private static String inventory() {
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
		return inventory[i];
	}

	/*--------------------------------------------------------------------------------*/
	public static void validMove(int currentLocale) {
		int newLocationTemp = -1;
		newLocationTemp = sequentialSearchLocaleNorth(listForDirs,
				currentLocale);
		if (newLocationTemp != -1) {
			goodDir = goodDir + "north ";
		}
		newLocationTemp = sequentialSearchLocaleSouth(listForDirs,
				currentLocale);
		if (newLocationTemp != -1) {
			goodDir = goodDir + "south ";
		}
		newLocationTemp = sequentialSearchLocaleEast(listForDirs, currentLocale);
		if (newLocationTemp != -1) {
			goodDir = goodDir + "east ";
		}
		newLocationTemp = sequentialSearchLocaleWest(listForDirs, currentLocale);
		if (newLocationTemp != -1) {
			goodDir = goodDir + "west";
		}
		newLocationTemp = sequentialSearchLocaleUp(listForDirs, currentLocale);
		if (newLocationTemp != -1) {
			goodDir = goodDir + "up ";
		}
		newLocationTemp = sequentialSearchLocaleDown(listForDirs, currentLocale);
		if (newLocationTemp != -1) {
			goodDir = goodDir + "down";
		}

		System.out.println("You can move in these directions: " + goodDir);
		goodDir = "";
	}

	/*--------------------------------------------------------------------------------*/
	// now for the ascii map and ascii art for the locales

	public static void asciiMap() {
		if (tookMap == true) {
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
