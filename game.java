package game;

import java.util.Scanner;

public class game {

   // Globals
   public static final int MAX_LOCALES = 15;    // Total number of rooms/locations we have in the game.
   public static int currentLocale = 0;        // Player starts in locale 0.
   public static String command;               // What the player types as he or she plays the game.
   public static boolean stillPlaying = true; // Controls the game loop.
   public static String[] locations;           // An uninitialized array of type String. See init() for initialization.
   public static int[][]  nav;                 // An uninitialized array of type int int.
   public static boolean hasBeen=false;        // used to keep track of score
   public static int score=5;  
   // score is 5 for a new location 
   public static int[] roomCheck=new int [16];
   public static void totalScore() {
     if (roomCheck[currentLocale]==0){
       score=score + 5;
       roomCheck[currentLocale]=1;
     };
    System.out.println ("Your current score is "+score);
       }
  ;
 


   public static void main(String[] args) {
      for (int i=0; i==16; i++) {
    roomCheck[i]=0;
  }
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

      /*
     System.out.println("START Testing------------------");

      // TODO Set the descriptions for each locale.
   // locale 0-- kennel town //
      Locale loc0 = new Locale(0);
      loc0.setName(locations[0]);
      System.out.println(loc0.getId() + ": " + loc0.getName());
      loc0.setDesc ("A quit and peaceful nighborhood were various animals live together");
      System.out.println(loc0.getId() + ": " + loc0.getDesc());
      
   // locale 1-- magick shoppe //
      Locale loc1 = new Locale(1);
      loc1.setName(locations[1]);
      System.out.println(loc1.getId() + ": " + loc1.getName());
      loc1.setDesc ("You are in the town's magic shop. It is made of concert and sells various magical items ");
      System.out.println(loc1.getId() + ": " + loc1.getDesc());
      
   // locale 2-- icy cave //
      Locale loc2 = new Locale(2);
      loc2.setName(locations[2]);
      System.out.println(loc2.getId() + ": " + loc2.getName());
      loc2.setDesc ("A deep and cold cave were various wild creatures live");
      System.out.println(loc2.getId() + ": " + loc2.getDesc());
      
   // locale 3-- unpleasent meadows //
      Locale loc3 = new Locale(3);
      loc3.setName(locations[3]);
      System.out.println(loc3.getId() + ": " + loc3.getName());
      loc3.setDesc ("A pollated wasteland were various mutated creatures live ");
      System.out.println(loc3.getId() + ": " + loc3.getDesc());
      
   // locale 4-- catnip city // 
      Locale loc4 = new Locale(4);
      loc4.setName(locations[4]);
      System.out.println(loc1.getId() + ": " + loc4.getName());
      loc4.setDesc ("A new restored city. Rumor has it it was bulit by an anicinet socity millions of years ago ");
      System.out.println(loc4.getId() + ": " + loc4.getDesc());
   
    // locale 5-- whittie's space ship dealer //
      Locale loc5 = new Locale(5);
      loc5.setName(locations[5]);
      System.out.println(loc5.getId() + ": " + loc5.getName());
      loc5.setDesc ("A ulitiartian bluding that sells differ kind of space ships  ");
      System.out.println(loc5.getId() + ": " + loc5.getDesc());
    
   // locale 6-- vet of horrors //
      Locale loc6 = new Locale(6);
      loc6.setName(locations[6]);
      System.out.println(loc6.getId() + ": " + loc6.getName());
      loc6.setDesc ("A hospital that all animals go to. It is ran by the smartest and jerkiest computer in the world");
      System.out.println(loc6.getId() + ": " + loc6.getDesc());

   // locale 7-- uncanny valley //  
      Locale loc7 = new Locale(7);
      loc7.setName(locations[7]);
      System.out.println(loc7.getId() + ": " + loc7.getName());
      loc7.setDesc ("a creepy vally were the clones are rumoured to live ");
      System.out.println(loc7.getId() + ": " + loc7.getDesc());

   // locale 8-- Draco's Dragon Meat Facotry //   
      Locale loc8 = new Locale(8);
      loc8.setName(locations[8]);
      System.out.println(loc8.getId() + ": " + loc8.getName());
      loc8.setDesc ("The largest feed lot and slaughterhouse of dragon meat. Famous for its gentle and kind treatment of its livestock ");
      System.out.println(loc8.getId() + ": " + loc8.getDesc());
      
   // locale 9-- cheesy tourest area // 
      Locale loc9 = new Locale(9);
      loc9.setName(locations[9]);
      System.out.println(loc9.getId() + ": " + loc9.getName());
      loc9.setDesc ("A tourest fly trap were tourest can but cheap gifts for the ride back home");
      System.out.println(loc9.getId() + ": " + loc9.getDesc());

   // locale 10-- monturus mountains //   
      Locale loc10 = new Locale(10);
      loc10.setName(locations[10]);
      System.out.println(loc10.getId() + ": " + loc10.getName());
      loc10.setDesc ("Mountains so tall, that they appiar to reach space ");
      System.out.println(loc10.getId() + ": " + loc10.getDesc());

   // locale 11-- abanded delviment //   
      Locale loc11 = new Locale(11);
      loc11.setName(locations[11]);
      System.out.println(loc11.getId() + ": " + loc11.getName());
      loc11.setDesc ("A delivement that was abanded for fincal reasons. It is over taken by weeds");
      System.out.println(loc11.getId() + ": " + loc11.getDesc());
      
   // locale 12-- rusty dungeon //   
      Locale loc12 = new Locale(12);
      loc12.setName(locations[12]);
      System.out.println(loc12.getId() + ": " + loc12.getName());
      loc12.setDesc ("AN dungeon that was once used for dungoneers to pratice. Now, only the geekest of animals hang out here ");
      System.out.println(loc12.getId() + ": " + loc12.getDesc());

   // locale 13-- outer outer space //  
      Locale loc13 = new Locale(13);
      loc13.setName(locations[13]);
      System.out.println(loc13.getId() + ": " + loc13.getName());
      loc13.setDesc ("The space that lies just out side of the mutiverse ");
      System.out.println(loc13.getId() + ": " + loc13.getDesc());
      
   // locale 14-- crashed space ship //   
      Locale loc14 = new Locale(14);
      loc14.setName(locations[14]);
      System.out.println(loc14.getId() + ": " + loc14.getName());
      loc14.setDesc ("A space ship that crashed after hitting an metor while landing. Luckly everbody was ok ");
      System.out.println(loc14.getId() + ": " + loc14.getDesc());

   // locale 15-- not at all evil blip //  
      Locale loc15 = new Locale(15);
      loc15.setName(locations[15]);
      System.out.println(loc15.getId() + ": " + loc15.getName()); 
      loc15.setDesc ("Dr Mars blimp, the one that he is useing to abuct random animals to make them work for his company. Biggo Stocks. Nothing sinser here. ");
      System.out.println(loc15.getId() + ": " + loc15.getDesc());
      

      



      System.out.println("END Testing------------------");

*/

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
      } else if ( command.equalsIgnoreCase("take")  || command.equalsIgnoreCase("t")) {
         take(); 
      } else if ( command.equalsIgnoreCase("attack")  || command.equalsIgnoreCase("a")) {
         attack();  
      } else if ( command.equalsIgnoreCase("inventory")  || command.equalsIgnoreCase("i")) {
         inventory(); 
      } else if ( command.equalsIgnoreCase("map")  || command.equalsIgnoreCase("map")) {
         map();     
      };

      if (dir > -1) {   // This means a dir was set.
         int newLocation = nav[currentLocale][dir];
         if (newLocation == INVALID) {
            System.out.println("You cannot go that way. Press h for help");
         } else {
            currentLocale = newLocation;
            totalScore(); 
         }
      }
   }

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
      System.out.println("   a/attack");
      System.out.println("   i/inventory");
      System.out.println("   m/map");
      System.out.println("   q/quit");
   }

   private static void quit() {
      stillPlaying = false;
   }
  private static void take() {
      // takes an item if ture
   } 
 private static void attack() {
      // kills enemey if ture
   } 
 private static void inventory() {
      // prints inventory and all intems in invenotry
   }
 private static void map() {
     // pirnts out map
   }
 // now for the ascii map and ascii art for the locales
 
 public static void  asciiMap() {
	 System.out.print("[_outer_outer_spcae]       [_crashed_space_ship_]        [_not_at_all_evil_blimp]   ");
	 System.out.print("    |    ^                     |               ^                |             ^     ");
	 System.out.print("    v    |                     v               |                v             |     ");
	 System.out.print("[_abandend_delvipment_] <----->[_unpleasent_meadows_] <------>[_rusty_dungeon_]     ");
	 System.out.print("    |         ^                     |           ^                 |     ^           ");
	 System.out.print("    v         |                     v           |                 v     |           ");
	 System.out.print("[_icy_______cave_] <----------->[_kennel_town_____] <-------->[_magick_shoppe_]     ");
	 System.out.print("   ^         |                      ^        |                     ^    |           ");
	 System.out.print("   |         v                      |        V                     |    v           ");
	 System.out.print("[_vet_of_horrors__] <----------> [_cat_nip_city_] <-> [_whittie's space-ship_dealer]");
	 System.out.print("                 ^       |                       ^       |                     ^     |          ");
	 System.out.print("                 |       v                       |       v                     |     v          ");
	 System.out.print("[]               [_monturous_mountains]          [_kennel_town_]");
	 
 }
}