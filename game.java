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
												// we have in the game.
	public static int currentLocale = 0; // Player starts in locale 0.
	public static String command; // What the player types as he or she plays
									// the game.
	public static int itemToTake;
	public static int localInventory = 0;
	public static boolean stillPlaying = true; // Controls the game loop.
	public static Locale[] locations; // An uninitialized array of type String.
										// See init() for initialization.
	public static int[][] nav; // An uninitialized array of type int int.
	public static boolean hasBeen = false; // used to keep track of score
	public static int score = 5; // score is 5 for a new location
	public static int[] roomCheck = new int[16];
	public static boolean tookMap = false;
	public static int numberOfItemsInInventory = 0;
	public static int numberOfItemsInGame = 21;
	public static String inventory[] = new String[25];// uses booleans, Item
														// class
														// to make this work.

	public static int[] inLocale = new int[25];// array that tells where the
												// item is located
	public static Item arrayOfItems[];// similar to the array of locales,
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

	public static void main(String[] args) {
		// Make some list items.
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

		// inLocale subscript is item number, value is location. Location -1
		// means player has item
		inLocale[0] = 1;
		inLocale[1] = 1;
		inLocale[2] = 1;
		inLocale[3] = 1;
		inLocale[4] = 5;
		inLocale[5] = 5;
		inLocale[6] = 5;
		inLocale[7] = 7;
		inLocale[8] = 0;
		inLocale[9] = 2;
		inLocale[10] = 3;
		inLocale[11] = 3;
		inLocale[12] = 4;
		inLocale[13] = 6;
		inLocale[14] = 7;
		inLocale[15] = 8;
		inLocale[16] = 4;
		inLocale[17] = 9;
		inLocale[18] = 9;
		inLocale[19] = 10;
		inLocale[20] = 11;
		inLocale[21] = 12;
		inLocale[22] = 14;
		inLocale[23] = 13;
		inLocale[24] = 15;
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
		Locale loc0 = new Locale(0);
		loc0.setName("Kennel Town");
		loc0.setDesc("You are in a quiet and peaceful neighborhood where various animals live together.");
		// item 8-- map, found at kennel town //
		Item item8 = new Item(8);
		item8.setItemName("Map");
		item8.setItemDesc("A simple map, a little worn around the edges. Press m to see it");
		Money Money0 = new Money();
		Money0.setWorth(25000);

		// /locale 1-- magick shoppe //
		ItemStore loc1 = new ItemStore(1);
		loc1.setName("Magick Shoppe");
		loc1.setDesc("You are in the town's magic shop. It is made of concrete and sells various magical items. ");
		Money Money1 = new Money();
		Money1.setWorth(21120);

		// locale 2-- icy cave //
		Locale loc2 = new Locale(2);
		loc2.setName("Icy Cave");
		loc2.setDesc("A deep and cold cave where various wild creatures live");
		// item 9-- icicle of a million colors, found at icey cave //
		Item item9 = new Item(9);
		item9.setItemName("Icicle of a Million Colors");
		item9.setItemDesc("A large icicle that appears to shine several different colors.");
		Money Money2 = new Money();
		Money2.setWorth(15000);

		// locale 3-- unpleasent meadows //
		Locale loc3 = new Locale(3);
		loc3.setName("Unpleasant Meadows");
		loc3.setDesc("A polluted wasteland where various mutated creatures live ");
		// unpleanse meadows has 2 items
		// item 10-- three headed fish //
		Item item10 = new Item(10);
		item10.setItemName("Three Headed Fish");
		item10.setItemDesc("A dull brown fish that has three heads and a lot of teeth. Quite frightening , actually");
		// item 11-- tin drum of toxic waste //
		Item item11 = new Item(11);
		item11.setItemName("Tin Drum of Toxic Waste");
		item11.setItemDesc("A drum full of waste from the fission planet. It's pretty nasty stuff");
		Money Money3 = new Money();
		Money3.setWorth(77777);

		// locale 4-- catnip city //
		Locale loc4 = new Locale(4);
		loc4.setName("Catnip City");
		loc4.setDesc("A newly restored city. Rumor has it it was built by an ancient society millions of years ago ");
		// item 12-- taxi ticket, located at Catnip City //
		Item item12 = new Item(12);
		item12.setItemName("Taxi Ticket");
		item12.setItemDesc("A ticket for one of the bright yellow taxis. ");
		Money Money4 = new Money();
		Money4.setWorth(12345);

		// item 16-- album of strange music, found in catnip city //
		Item item16 = new Item(16);
		item16.setItemName("Album of Strange Music");
		item16.setItemDesc("A cd full of music that sounds like drunk dance music, (its kind of hard to describe).");

		// locale 5-- whittie's space ship dealer //
		ItemStore loc5 = new ItemStore(5);
		loc5.setName("Whittie's Space Ship Dealer");
		loc5.setDesc("A utilitarian building that sells different kinds of space ships  ");
		Money Money5 = new Money();
		Money5.setWorth(99999);
		// items 3-7 are for whitie's space ship dealer

		// item 4-- Verne Spacemaster //
		Item item4 = new Item(4);
		item4.setItemName("Verne Spacemaster");
		item4.setItemDesc("A silver space ship that looks like a cigar. It has a long point on the front of it and sits on a pair of skis ");
		// item 5-- Anticent Alien //
		Item item5 = new Item(5);
		item5.setItemName("Ancient Alien");
		item5.setItemDesc("A hot pink flying saucer. It has chrome trim around it and a purple interior. In other words, the girliest space ship in existence");

		// item 6-- Benoynd Infintie //
		Item item6 = new Item(6);
		item6.setItemName("Beyond Infinite");
		item6.setItemDesc("A navy space ship that looks like a box. It has a nice, tan interior in it.");

		// item 7-- Expermental AIrcraft- Aera 51 edition //
		Item item7 = new Item(7);
		item7.setItemName("Experimental Aircraft-Area 51 Edition");
		item7.setItemDesc("A gold space ship that is shaped like a sword. It has a tiny black cockpit.");

		// locale 6-- vet of horrors //
		Locale loc6 = new Locale(6);
		loc6.setName("Vet of Horrors");
		loc6.setDesc("A hospital that all animals go to. It is run by the smartest and jerkiest computer in the world");
		// item 13-- srignine of pain killer, located at vet of horrors //
		Money Money6 = new Money();
		Money6.setWorth(56000);
		Item item13 = new Item(13);
		item13.setItemName("Syringe of Pain Killer");
		item13.setItemDesc("A Syringe of a powerful pain killer, used to retore health");

		// locale 7-- uncanny valley //
		Locale loc7 = new Locale(7);
		loc7.setName("Uncanny Valley");
		loc7.setDesc("A creepy valley were the clones are rumoured to live ");
		Money Money7 = new Money();
		Money7.setWorth(56890);
		// item 14--clone paw, located at uncanny valley //
		Item item14 = new Item(14);
		item14.setItemName("Clone Paw");
		item14.setItemDesc("Exactly what it sounds like. Poor clone");

		// locale 8-- Draco's Dragon Meat Facotry //
		Locale loc8 = new Locale(8);
		loc8.setName("Draco's Dragon Meat Factory");
		loc8.setDesc("The largest feed lot and slaughterhouse of dragon meat. Famous for its gentle and kind treatment of its livestock ");
		Money Money8 = new Money();
		Money8.setWorth(66000);
		// item 15-- drang fang, llocated at dragon meat factory //
		Item item15 = new Item(15);
		item15.setItemName("Dragon Fang");
		item15.setItemDesc("A fang from an old dragon");

		// locale 9-- cheesy tourest area //
		Locale loc9 = new Locale(9);
		loc9.setName("Cheesy Toureist Area");
		loc9.setDesc("A tourist trap where tourist can buy cheap gifts for the ride back home");
		// cheesy tourest aera has 2 items
		// /item 17-- vulgar tee shirt //
		Money Money9 = new Money();
		Money9.setWorth(10000);
		Item item17 = new Item(17);
		item17.setItemName("Vulgar Tee Shirt");
		item17.setItemDesc("A tee shirt with something very rude printed on it. ");

		// item 18-- knock off purse //
		Item item18 = new Item(18);
		item18.setItemName("Knock-off Purse");
		item18.setItemDesc("A brightly colored purse that looks almost like a high end purse, but is a 1/16th the price, must be authentic.");

		// locale 10-- monturus mountains //
		Locale loc10 = new Locale(10);
		loc10.setName("Monstrous Mountains");
		loc10.setDesc("Mountains so tall, that they appear to reach space ");
		// item 19-- snow form the top of the world, located at montuus
		// mountains //
		Money Money10 = new Money();
		Money10.setWorth(33450);
		Item item19 = new Item(19);
		item19.setItemName("Snow From the Top of the World");
		item19.setItemDesc("Snow from the top of the monstrous montians. Careful, it will melt");

		// locale 11-- abanded delviment //
		Locale loc11 = new Locale(11);
		loc11.setName("Abandoned Development");
		loc11.setDesc("A development that was abandoned for finanical reasons. It is overtaken by weeds");
		// item 20-- door knob, located at abanoded delvement //
		Money Money11 = new Money();
		Money11.setWorth(15200);
		Item item20 = new Item(20);
		item20.setItemName("Door Knob");
		item20.setItemDesc("A bronze door knob that was on a trailer");

		// locale 12-- rusty dungeon //
		Locale loc12 = new Locale(12);
		loc12.setName("Rusty Dungeon");
		loc12.setDesc("A dungeon that was once used for dungoneers to practice. Now, only the geekiest of animals hang out here ");
		// item 21-- rusty key, located at therusty dungeon //
		Money Money12 = new Money();
		Money12.setWorth(89741);
		Item item21 = new Item(21);
		item21.setItemName("Rusty Key");
		item21.setItemDesc("A key that is rusty, it can unlock the door to all secrets, or somthing neat like that.");

		// locale 13-- outer outer space //
		Locale loc13 = new Locale(13);
		loc13.setName("Outer Outer Space");
		loc13.setDesc("The space that lies just outside of the multiverse ");
		// item 23-- comet dust form the nth dimsion, found it outer, outer
		// space //
		Money Money13 = new Money();
		Money13.setWorth(10258);
		Item item23 = new Item(23);
		item23.setItemName("Comet Dust From the nth Dimenson");
		item23.setItemDesc("Comet dust of the other universe. It is near priceless");

		// locale 14-- crashed space ship //
		Locale loc14 = new Locale(14);
		loc14.setName("Crashed Space Ship");
		loc14.setDesc("A space ship that crashed after hitting a meteor while landing. Luckily everybody was ok ");
		Money Money14 = new Money();
		Money14.setWorth(35678);
		// item 22- bent up enigine, located at the crashed space ship //
		Item item22 = new Item(22);
		item22.setItemName("Beat Up Engine");
		item22.setItemDesc("A beat up engine from the crashed space ship. Still seems to work");

		// locale 15-- not at all evil blip //
		Locale loc15 = new Locale(15);
		loc15.setName("Not at all Evil Blimp");
		loc15.setDesc("Dr Mars' blimp, the one that he is using to abduct random animals to make them work for his company. Biggo Stocks. Nothing sinister here. ");
		Money Money15 = new Money();
		Money15.setWorth(65789);
		// item 24-- dont push me button, located at the not at all evil blimp
		// //
		Item item24 = new Item(24);
		item24.setItemName("Dont Push Me Button");
		item24.setItemDesc("A big red button, that says dont push in big, black letters.");

		// now for the array of items, to store for invetory and items locale
		arrayOfItems = new Item[21];
		arrayOfItems[0] = item4;// item4-7 are in space ship dealer
		arrayOfItems[1] = item5;
		arrayOfItems[2] = item6;
		arrayOfItems[3] = item7;
		arrayOfItems[4] = item8;// item 8 is in kennel town
		arrayOfItems[5] = item9;// item 9 is in the icy cave
		arrayOfItems[6] = item10;// item10-11 are in the unpleasent meadows
		arrayOfItems[7] = item11;
		arrayOfItems[8] = item12;// item 12 is in catnip city
		arrayOfItems[9] = item13;// item 13 is in the vet of horrors
		arrayOfItems[10] = item14;// item 14 is in the uncanny valley
		arrayOfItems[11] = item15;// item 15 is in the dragon meat factory
		arrayOfItems[12] = item16;// item 16 is in catnip city(was numbered
									// this// way beacause I had a duplicate
									// item.)
		arrayOfItems[13] = item17;// item 17-18 are in the cheesey tourset aera
		arrayOfItems[14] = item18;
		arrayOfItems[15] = item19;// item 19 is in the monturus mountains
		arrayOfItems[16] = item20;// item 20 is in the abanded delvement
		arrayOfItems[17] = item21;// item 21 is in the rusty dungeon
		arrayOfItems[18] = item22;// item 22 is in the crashed space ship
		arrayOfItems[19] = item23;// item 23 is in outer outer space
		arrayOfItems[20] = item24;// item 24 is in the evil blimp

		// array of locales to store for nav
		locations = new Locale[16];
		locations[2] = loc2; // <-w
		locations[0] = loc0; // center of map
		locations[1] = loc1;// e->
		locations[3] = loc3;
		locations[4] = loc4;
		locations[5] = loc5;
		locations[6] = loc6;
		locations[7] = loc7;
		locations[8] = loc8;
		locations[9] = loc9;
		locations[10] = loc10;
		locations[11] = loc11;
		locations[12] = loc12;
		locations[13] = loc13;
		locations[14] = loc14;
		locations[15] = loc15;

		// array money has the money in it
		arrayOfMoney = new Money[16];
		arrayOfMoney[2] = Money2; // <-w
		arrayOfMoney[0] = Money0; // center of map
		arrayOfMoney[1] = Money1;// e->
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

		// Set up the navigation matrix.
		nav = new int[][] {
		/* N S E W U D */// --U stands for UP and D stands for Down
				/* 0 1 2 3 4 5 */
				/* nav[0] for loc 0 */{ 3, 4, 1, 2, -1, -1 },
				/* nav[1] for loc 1 */{ 12, 5, -1, 0, -1, -1 },
				/* nav[2] for loc 2 */{ 11, 6, 0, -1, -1, -1 },
				/* nav[3] for loc 3 */{ 14, 0, 12, 11, -1, -1 },
				/* nav[4] for loc 4 */{ 0, 8, 5, 6, -1, -1 },
				/* nav[5] for loc 5 */{ 1, 9, -1, 4, -1, -1 },
				/* nav[6] for loc 6 */{ 2, 7, 4, -1, -1, -1 },
				/* nav[7] for loc 7 */{ 6, -1, 8, 10, -1, -1 },
				/* nav[8] for loc 8 */{ 4, -1, 9, 7, -1, -1 },
				/* nav[9] for loc 9 */{ 5, -1, -1, 8, -1, -1 },
				/* nav[10] for loc 10 */{ -1, -1, 7, -1, -1, -1 },
				/* nav[11] for loc 11 */{ -1, 2, 3, -1, 13, -1 },
				/* nav[12] for loc 12 */{ -1, 1, -1, 3, 15, -1 },
				/* nav[13] for loc 13 */{ -1, -1, -1, -1, -1, 11 },
				/* nav[14] for loc 14 */{ -1, 3, -1, -1, -1, -1 },
				/* nav[15] for loc 15 */{ -1, -1, -1, -1, -1, 12 } };
	}

	/*--------------------------------------------------------------------------------*/
	private static void updateDisplay() {
		if (totalMoves == 0 && currentLocale == 0) {
			System.out.println("Darth Vs. The Evil Dr. Mars");
		}
		System.out.println(locations[currentLocale].getName());
		System.out.println(locations[currentLocale].getDesc());
		validMove();
		for (int i = 0; i < numberOfItemsInGame; i++) {
			if (inLocale[i] == currentLocale) {
				if (currentLocale != 1) {
					System.out.print("There is a "
							+ arrayOfItems[i].getItemName() + ". ");
					System.out.println(arrayOfItems[i].getItemDesc());
				}
			}
		}
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
		final int INVALID = -1;
		int dir = INVALID; // This will get set to a value > 0 if a direction
							// command was entered.
		if (command.equalsIgnoreCase("north") || command.equalsIgnoreCase("n")) {
			dir = 0;
		} else if (command.equalsIgnoreCase("south")
				|| command.equalsIgnoreCase("s")) {
			dir = 1;
		} else if (command.equalsIgnoreCase("east")
				|| command.equalsIgnoreCase("e")) {
			dir = 2;
		} else if (command.equalsIgnoreCase("west")
				|| command.equalsIgnoreCase("w")) {
			dir = 3;
		} else if (command.equalsIgnoreCase("up")
				|| command.equalsIgnoreCase("u")) {
			dir = 4;
		} else if (command.equalsIgnoreCase("down")
				|| command.equalsIgnoreCase("d")) {
			dir = 5;
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

		if (dir > -1) { // This means a dir was set.
			totalMoves = totalMoves + 1;
			int newLocation = nav[currentLocale][dir];
			if (newLocation == INVALID) {
				System.out.println("You cannot go that way. Press h for help");
				validMove();
			} else {
				currentLocale = newLocation;
				totalScore();
				System.out.println("You have made " + totalMoves + " moves.");
				achievementRatio();
				validMove();
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
		localInventory = 0;
		for (int i = 0; i < numberOfItemsInGame; i++) {
			if (inLocale[i] == currentLocale) {
				localInventory = localInventory + 1;
			}
		}
		if (localInventory == 0) {
			System.out.println("There are no items here to take");
		}

		else {
			System.out.println("Which item number do you wish to take?");
			for (int i = 0; i < numberOfItemsInGame; i++) {
				if (inLocale[i] == currentLocale) {
					System.out.println("Item number " + i + ":  "
							+ arrayOfItems[i].getItemName());
				}
			}
			try {
				Scanner inputReader = new Scanner(System.in);
				itemToTake = inputReader.nextInt();
				if (inLocale[itemToTake] == currentLocale) {
					System.out.println("You took the "
							+ arrayOfItems[itemToTake].getItemName());
					inLocale[itemToTake] = -1;
					if (itemToTake == 8) {
						tookMap = true;
					}
					inventory[numberOfItemsInInventory] = arrayOfItems[itemToTake]
							.getItemName();
					numberOfItemsInInventory = numberOfItemsInInventory + 1;
				} else {
					System.out.println("That is not a valid selection.");
				}
			} catch (Exception InputMismatchException) {
				System.out.println("That is not a valid selection.");
			}
		}
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
	public static void validMove() {
		for (int i = 0; i < 6; i++) {

			if (nav[currentLocale][i] != -1)
				switch (i) {
				case 0:
					goodDir = goodDir + "north ";
					break;
				case 1:
					goodDir = goodDir + "south ";
					break;
				case 2:
					goodDir = goodDir + "east ";
					break;
				case 3:
					goodDir = goodDir + "west ";
					break;
				case 4:
					goodDir = goodDir + "up ";
					break;
				case 5:
					goodDir = goodDir + "down ";
					break;
				}
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
