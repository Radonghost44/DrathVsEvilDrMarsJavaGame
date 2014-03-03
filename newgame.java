package DrathVsEvilDrMarsJavaGame;

import java.util.Scanner;

public class newgame {

   // Globals
   public static final int MAX_LOCALES = 15;    // Total number of rooms/locations we have in the game.
   public static int currentLocale = 0;        // Player starts in locale 0.
   public static String command;               // What the player types as he or she plays the game.
   public static boolean stillPlaying = true; // Controls the game loop.
   public static String[] locations;           // An uninitialized array of type String. See init() for initialization.
   public static int[][]  nav;                 // An uninitialized array of type int int.

   public static void main(String[] args) {
      // Display the command line args.
      System.out.println("Starting with args:");
      for (int i = 0; i < args.length; i++) {
         System.out.println(i + ":" + args[i]);
      }

      // Set starting locale, if it was provided as a command line parameter.
      if (args.length > 0) {
         int startLocation = Integer.parseInt(args[0]);    // TODO We need to catch a possible exception here.
         // Check that the passed-in value for startLocation is within the range of actual locations.
         if ( startLocation >= 0 && startLocation <= MAX_LOCALES) {
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
         if (stillPlaying == true) {updateDisplay();}
      }

      // We're done. Thank the player and exit.
      System.out.println("Thank you for playing.");
   }


   private static void init() {
      // Initialize any uninitialized globals.
      command = new String();
      stillPlaying = true;   // TODO: Do we need this?

      // Set up the location list.
      locations = new String[16];
      locations[2] = "Icy Cave";       //  <-w
      locations[0] = "Kennel Town";    // center of map 
      locations[1] = "Magick Shoppe";// e-> 
      locations[3] = "Unpleasant Meadows";       
      locations[4] = "Catnip City";     
      locations[5] = "Whitie's Spaceship Dealer";
      locations[6] = "Vet of Horrors";       
      locations[7] = "Uncanny Valley";     
      locations[8] = "Draco's Dragon Meat Factory"; 
      locations[9] = "Cheesy Tourist Area";       
      locations[10] = "Monsterous Mountains";     
      locations[11] = "Abandoned Development";
      locations[12] = "Rusty Dungeon";       
      locations[13] = "Outer Outer Space";     
      locations[14] = "Crashed Space Ship";   
      locations[15] = "Not At All Evil Blimp";        

     System.out.println("START Testing------------------");

      // TODO Set the descriptions for each locale.

      Locale loc0 = new Locale(0);
      loc0.setName(locations[0]);
      System.out.println(loc0.getId() + ": " + loc0.getName());

      Locale loc1 = new Locale(1);
      loc1.setName(locations[1]);
      System.out.println(loc1.getId() + ": " + loc1.getName());

      Locale loc2 = new Locale(2);
      loc2.setName(locations[2]);
      System.out.println(loc2.getId() + ": " + loc2.getName());
      
      Locale loc3 = new Locale(3);
      loc0.setName(locations[3]);
      System.out.println(loc0.getId() + ": " + loc0.getName());

      Locale loc4 = new Locale(4);
      loc1.setName(locations[4]);
      System.out.println(loc1.getId() + ": " + loc1.getName());

      Locale loc5 = new Locale(5);
      loc2.setName(locations[5]);
      System.out.println(loc2.getId() + ": " + loc2.getName());
    
      Locale loc6 = new Locale(6);
      loc0.setName(locations[6]);
      System.out.println(loc0.getId() + ": " + loc0.getName());

      Locale loc7 = new Locale(7);
      loc1.setName(locations[7]);
      System.out.println(loc1.getId() + ": " + loc1.getName());

      Locale loc8 = new Locale(8);
      loc2.setName(locations[8]);
      System.out.println(loc2.getId() + ": " + loc2.getName());
      
      Locale loc9 = new Locale(9);
      loc0.setName(locations[9]);
      System.out.println(loc0.getId() + ": " + loc0.getName());

      Locale loc10 = new Locale(10);
      loc1.setName(locations[10]);
      System.out.println(loc1.getId() + ": " + loc1.getName());

      Locale loc11 = new Locale(11);
      loc2.setName(locations[11]);
      System.out.println(loc2.getId() + ": " + loc2.getName());  
      
      Locale loc12 = new Locale(12);
      loc1.setName(locations[12]);
      System.out.println(loc1.getId() + ": " + loc1.getName());

      Locale loc13 = new Locale(13);
      loc2.setName(locations[13]);
      System.out.println(loc2.getId() + ": " + loc2.getName());
      
      Locale loc14 = new Locale(14);
      loc1.setName(locations[14]);
      System.out.println(loc1.getId() + ": " + loc1.getName());

      Locale loc15 = new Locale(15);
      loc2.setName(locations[15]);
      System.out.println(loc2.getId() + ": " + loc2.getName());  
      

      



      System.out.println("END Testing------------------");



      System.out.println("All game locations:");
      for (int i = 0; i < locations.length; ++i) {
         System.out.println(i + ":" + locations[i]);
      }

      // Set up the navigation matrix.
      nav = new int[][] {
                                 /* N   S   E   W  U   D*///--U stands for UP and D stands for Down
                                 /* 0   1   2   3  4   5*/
         /* nav[0] for loc 0 */  {  3,  4, 1, 2 , -1, -1},
         /* nav[1] for loc 1 */  {  12, 5, -1, 0, -1, -1 },
         /* nav[2] for loc 2 */  { 11,  6, 0, -1, -1, -1 },
         /* nav[3] for loc 3 */  { 14,  0, 12,11, -1, -1 },
         /* nav[4] for loc 4 */  { 0,  8,  5,  6, -1, -1 },
         /* nav[5] for loc 5 */  { 1,  9, -1,  4, -1, -1 }, 
         /* nav[6] for loc 6 */  { 2,  7,  4, -1, -1, -1 },
         /* nav[7] for loc 7 */  { 6,  -1, 8, 10, -1, -1 },
         /* nav[8] for loc 8 */  { 4,  -1, 9, 7,  -1, -1 },
         /* nav[9] for loc 9 */  { 5,  -1, -1, 8, -1, -1 },
         /* nav[10] for loc 10 */{ -1,  -1, 7, -1, -1, -1 },
         /* nav[11] for loc 11 */{ -1,  2, 3, -1, 13,  -1 },
         /* nav[12] for loc 12 */{ -1,  1, -1, 3, 15, -1 },
         /* nav[13] for loc 13 */{ -1, -1,-1, -1, -1, 11 }, 
         /* nav[14] for loc 14 */{ -1,  3, -1, -1, -1, -1 },
         /* nav[15] for loc 15 */{ -1,  -1, -1, -1, -1, 12 }
      };
   }

  private static void updateDisplay() {
     System.out.println(locations[currentLocale]);
     // TODO Use the instances of the Locale class here.
     // TODO Display the locale descriptions.
  }

   private static void getCommand() {
      Scanner inputReader = new Scanner(System.in);
      command = inputReader.nextLine();  // command is global.
   }

   private static void navigate() {
      final int INVALID = -1;
      int dir = INVALID;  // This will get set to a value > 0 if a direction command was entered.

      if (        command.equalsIgnoreCase("north") || command.equalsIgnoreCase("n") ) {
         dir = 0;
      } else if ( command.equalsIgnoreCase("south") || command.equalsIgnoreCase("s") ) {
         dir = 1;
      } else if ( command.equalsIgnoreCase("east")  || command.equalsIgnoreCase("e") ) {
         dir = 2;
      } else if ( command.equalsIgnoreCase("west")  || command.equalsIgnoreCase("w") ) {
         dir = 3;
      } else if (command.equalsIgnoreCase("up")  || command.equalsIgnoreCase("u") ) {
        dir=4;
      } else if ( command.equalsIgnoreCase("down")  || command.equalsIgnoreCase("d") ) {
         dir = 5;
      }else if ( command.equalsIgnoreCase("quit")  || command.equalsIgnoreCase("q")) {
         quit();
      } else if ( command.equalsIgnoreCase("help")  || command.equalsIgnoreCase("h")) {
         help();
      };

      if (dir > -1) {   // This means a dir was set.
         int newLocation = nav[currentLocale][dir];
         if (newLocation == INVALID) {
            System.out.println("You cannot go that way.");
         } else {
            currentLocale = newLocation;
         }
      }
   }

   private static void help() {
      System.out.println("The commands are as follows:");
      System.out.println("   n/north");
      System.out.println("   s/south");
      System.out.println("   q/quit");
   }

   private static void quit() {
      stillPlaying = false;
   }
}