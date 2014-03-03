package game;

import java.util.Scanner;

public class game {

   // Globals
   public static final int MAX_LOCALES = 15;    // Total number of rooms/locations we have in the game.
   public static int currentLocale = 0;        // Player starts in locale 0.
   public static String command;               // What the player types as he or she plays the game.
   public static boolean stillPlaying = true; // Controls the game loop.
   public static Locale[] locations;           // An uninitialized array of type String. See init() for initialization.
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
  public static Item inventory []= new Item [24]; // uses booleans, Item class to make this work.
  public static int totalMoves = 0;


   public static void main (String[] args) {
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
         if (stillPlaying == true) {updateDisplay();
         }
      }

      // We're done. Thank the player and exit.
      System.out.println("Thank you for playing.");
   }


   private static void init() {
      // Initialize any uninitialized globals.
      command = new String();
      stillPlaying = true;   
      // Set up the location list.
      
      
      //locale 0-- kennel town; //
      Locale loc0 = new Locale(0);
      loc0.setName("Kennel Town");
      System.out.println(loc0.getId() + ": " + loc0.getName());
      loc0.setDesc ("You are in a quiet and peaceful neighborhood where various animals live together.");  
      System.out.println(loc0.getId() + ": " + loc0.getDesc());
      
    ///locale 1-- magick shoppe //
      ItemStore loc1 = new ItemStore(1);
      loc1.setName("Magick Shoppe");
      System.out.println(loc1.getId() + ": " + loc1.getName());
      loc1.setDesc ("You are in the town's magic shop. It is made of concrete and sells various magical items ");
      System.out.println(loc1.getId() + ": " + loc1.getDesc());
      
   // locale 2-- icy cave //
      Locale loc2 = new Locale(2);
      loc2.setName("Icy Cave");
      System.out.println(loc2.getId() + ": " + loc2.getName());
      loc2.setDesc ("A deep and cold cave were various wild creatures live");
      System.out.println(loc2.getId() + ": " + loc2.getDesc());
      
   // locale 3-- unpleasent meadows //
      Locale loc3 = new Locale(3);
      loc3.setName("Unpleasant Meadows");
      System.out.println(loc3.getId() + ": " + loc3.getName());
      loc3.setDesc ("A polluted wasteland where various mutated creatures live ");
      System.out.println(loc3.getId() + ": " + loc3.getDesc());
      
   // locale 4-- catnip city // 
      Locale loc4 = new Locale(4);
      loc4.setName("Catnip City");
      System.out.println(loc4.getId() + ": " + loc4.getName());
      loc4.setDesc ("A new restored city. Rumor has it it was built by an ancient society millions of years ago ");
      System.out.println(loc4.getId() + ": " + loc4.getDesc());
   
    // locale 5-- whittie's space ship dealer //
      ItemStore loc5 = new ItemStore(5);
      loc5.setName("Whittie's Space Ship Dealer");
      System.out.println(loc5.getId() + ": " + loc5.getName());
      loc5.setDesc ("A utilitarian building that sells different kinds of space ships  ");
      System.out.println(loc5.getId() + ": " + loc5.getDesc());
    
   // locale 6-- vet of horrors //
      Locale loc6 = new Locale(6);
      loc6.setName("Vet of Horrors");
      System.out.println(loc6.getId() + ": " + loc6.getName());
      loc6.setDesc ("A hospital that all animals go to. It is run by the smartest and jerkiest computer in the world");
      System.out.println(loc6.getId() + ": " + loc6.getDesc());

   // locale 7-- uncanny valley //  
      Locale loc7 = new Locale(7);
      loc7.setName("Uncanny Valley");
      System.out.println(loc7.getId() + ": " + loc7.getName());
      loc7.setDesc ("A creepy vally were the clones are rumoured to live ");
      System.out.println(loc7.getId() + ": " + loc7.getDesc());

   // locale 8-- Draco's Dragon Meat Facotry //   
      Locale loc8 = new Locale(8);
      loc8.setName("Draco's Dragon Meat Factory");
      System.out.println(loc8.getId() + ": " + loc8.getName());
      loc8.setDesc ("The largest feed lot and slaughterhouse of dragon meat. Famous for its gentle and kind treatment of its livestock ");
      System.out.println(loc8.getId() + ": " + loc8.getDesc());
      
   // locale 9-- cheesy tourest area // 
      Locale loc9 = new Locale(9);
      loc9.setName("Cheesy Toureist Area");
      System.out.println(loc9.getId() + ": " + loc9.getName());
      loc9.setDesc ("A tourist fly trap where tourist can buy cheap gifts for the ride back home");
      System.out.println(loc9.getId() + ": " + loc9.getDesc());

   // locale 10-- monturus mountains //   
      Locale loc10 = new Locale(10);
      loc10.setName("Monstrous Mountains");
      System.out.println(loc10.getId() + ": " + loc10.getName());
      loc10.setDesc ("Mountains so tall, that they appear to reach space ");
      System.out.println(loc10.getId() + ": " + loc10.getDesc());

   // locale 11-- abanded delviment //   
      Locale loc11 = new Locale(11);
      loc11.setName("Abandoned Development");
      System.out.println(loc11.getId() + ": " + loc11.getName());
      loc11.setDesc ("A development that was abandoned for finanical reasons. It is overtaken by weeds");
      System.out.println(loc11.getId() + ": " + loc11.getDesc());
      
   // locale 12-- rusty dungeon //   
      Locale loc12 = new Locale(12);
      loc12.setName("Rusty Dungeon");
      System.out.println(loc12.getId() + ": " + loc12.getName());
      loc12.setDesc ("A dungeon that was once used for dungoneers to practice. Now, only the geekiest of animals hang out here ");
      System.out.println(loc12.getId() + ": " + loc12.getDesc());

   // locale 13-- outer outer space //  
      Locale loc13 = new Locale(13);
      loc13.setName("Outer Outer Space");
      System.out.println(loc13.getId() + ": " + loc13.getName());
      loc13.setDesc ("The space that lies just outside of the multiverse ");
      System.out.println(loc13.getId() + ": " + loc13.getDesc());
      
   // locale 14-- crashed space ship //   
      Locale loc14 = new Locale(14);
      loc14.setName("Crashed Space Ship");
      System.out.println(loc14.getId() + ": " + loc14.getName());
      loc14.setDesc ("A space ship that crashed after hitting an metyor while landing. Luckily everybody was ok ");
      System.out.println(loc14.getId() + ": " + loc14.getDesc());

   // locale 15-- not at all evil blip //  
      Locale loc15 = new Locale(15);
      loc15.setName("Not at all Evil Blimp");
      System.out.println(loc15.getId() + ": " + loc15.getName()); 
      loc15.setDesc ("Dr Mars' blimp, the one that he is using to abduct random animals to make them work for his company. Biggo Stocks. Nothing sinser here. ");
      System.out.println(loc15.getId() + ": " + loc15.getDesc());
      
      
      locations = new Locale[16];
      locations[2] = loc2;       //  <-w
      locations[0] = loc0;    // center of map 
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
      //Now for the Items
      // items 0-3 are for the magick shoppe
      // item0 magicy summy thingy //
      Item item0 = new Item(0);
      item0.setName("magicy summy thingy");
      System.out.println(item0.getId() + ": " + item0.getName());
      item0.setDesc ("Rumoured to be from another dimension, it can summon random creatures.");  
      System.out.println(item0.getId() + ": " + item0.getDesc());
      
    ///item1 rusty hammer//
      Item item1 = new Item(1);
      item1.setName("rusty hammer");
      System.out.println(item1.getId() + ": " + item1.getName());
      item1.setDesc ("An old hammer used for hitting nails  ");
      System.out.println(item1.getId() + ": " + item1.getDesc());
      
   // item2 wand with low batterys //
      Item item2 = new Item(2);
      item2.setName("wand with low batteries");
      System.out.println(item2.getId() + ": " + item2.getName());
      item2.setDesc ("a wand that doesnt work well, since it needs some new AA batteries");
      System.out.println(item2.getId() + ": " + item2.getDesc());
      
   // item 3-- broken sword //
      Item item3 = new Item(3);
      item3.setName("broken sword");
      System.out.println(item3.getId() + ": " + item3.getName());
      item3.setDesc ("A pollated wasteland were various mutated creatures live ");
      System.out.println(item3.getId() + ": " + item3.getDesc());
   // items 3-7 are for whitie's space ship dealer   
  
      // item 4-- Verne Spacemaster // 
      Item item4 = new Item(4);
      item4.setName("Verne Spacemaster");
      System.out.println(item4.getId() + ": " + item4.getName());
      item4.setDesc ("A sliver space ship that looks like a cigar. It has a long point on the fornt of it and sits on a pair of skis ");
      System.out.println(item4.getId() + ": " + item4.getDesc());
   
    // item 5-- Anticent Alien //
      Item item5 = new Item(5);
      item5.setName("Anticent Alein");
      System.out.println(item5.getId() + ": " + item5.getName());
      loc5.setDesc ("A hot pink flying saucer. It has crome trim around it and a pruple inteor. In other words, the girlist space ship in extences");
      System.out.println(item5.getId() + ": " + item5.getDesc());
    
   // item 6-- Benoynd Infintie //
      Item item6 = new Item(6);
      item6.setName("Benoynd Infinite");
      System.out.println(item6.getId() + ": " + item6.getName());
      item6.setDesc ("A navy space ship that looks like a box. It has a nice, tan intoir in it.");
      System.out.println(item6.getId() + ": " + item6.getDesc());

   // item 7-- Expermental AIrcraft- Aera 51 edition //  
      Item item7 = new Item(7);
      item7.setName("Expermental Aircraft-Aera 51 edition");
      System.out.println(item7.getId() + ": " + item7.getName());
      item7.setDesc ("a gold space ship that is shaped like a sword. It has a tiny black cockpit.");
      System.out.println(item7.getId() + ": " + item7.getDesc());

   // items found thoughout the game
   // item 8-- map, found at kennel town //   
      Item item8 = new Item(8);
      item8.setName("map");
      System.out.println(item8.getId() + ": " + item8.getName());
      item8.setDesc ("a simple map, a little worn around the edges. Press m to see it");
      System.out.println(item8.getId() + ": " + item8.getDesc());
      
   // item  9-- icle of a million colors, found at icey cave // 
      Item item9 = new Item(9);
      item9.setName("icle of a million colors");
      System.out.println(item9.getId() + ": " + item9.getName());
      item9.setDesc ("A large icle that appears to shine several differnet colors.");
      System.out.println(item9.getId() + ": " + item9.getDesc());

    //unpleanse meadows has 2 items 
   // item 10-- three headed fish //   
      Item item10 = new Item(10);
      item10.setName("three headed fish");
      System.out.println(item10.getId() + ": " + item10.getName());
      loc10.setDesc ("A dull brown fish that has three heads and a lot of teeth. QUit frighting , acutally");
      System.out.println(item10.getId() + ": " + item10.getDesc());

   // item 11-- tin drum of toxic waste //   
      Item item11 = new Item(11);
      item11.setName("tin drum of toxic waste");
      System.out.println(item11.getId() + ": " + item11.getName());
      item11.setDesc ("A durm full of wste form the fission planet. Its pretty nastey stuff");
      System.out.println(item11.getId() + ": " + item11.getDesc());
      
   // item 12-- taxi ticket, loacted at Catnip City //   
      Item item12 = new Item(12);
      item12.setName("taxi ticket");
      System.out.println(item12.getId() + ": " + item12.getName());
      item12.setDesc ("A ticket for one of the bringt yellow taxis. ");
      System.out.println(item12.getId() + ": " + item12.getDesc());

   // item 13-- srignine of pain killer, located at vet of horrors //  
      Item item13 = new Item(13);
      item13.setName("sringine of pain killer");
      System.out.println(item13.getId() + ": " + item13.getName());
      loc13.setDesc ("a sringine of powerful pain killer, used to retore health");
      System.out.println(item13.getId() + ": " + item13.getDesc());
      
   // item 14--clone paw, located at uncanny valley //   
      Item item14 = new Item(14);
      item14.setName("clone paw");
      System.out.println(item14.getId() + ": " + item14.getName());
      item14.setDesc ("Excalty what it sounds like. Poor clone");
      System.out.println(item14.getId() + ": " + item14.getDesc());

   // item 15-- drang fang, llocated at dragon meat factory //  
      Item item15 = new Item(15);
      item15.setName("Draogon Fang");
      System.out.println(item15.getId() + ": " + item15.getName()); 
      loc15.setDesc ("a fang form an old dragon");
      System.out.println(item15.getId() + ": " + item15.getDesc());
      
   // magicy summy thingy //
      Item item16 = new Item(16);
      item16.setName("magicy summy thingy");
      System.out.println(item16.getId() + ": " + item16.getName());
      item16.setDesc ("Rumoured to be form anther dimsonion, it can summon random creatures.");  
      System.out.println(item16.getId() + ": " + item16.getDesc());
     
      // cheesy tourest aera has 2 items
    ///item 17-- vulgar tee shirt //
      Item item17 = new Item(17);
      item17.setName("vulgar tee shirt");
      System.out.println(item17.getId() + ": " + item17.getName());
      item17.setDesc ("an tee shirt with something very rude printed on it. ");
      System.out.println(item17.getId() + ": " + item17.getDesc());
      
   // item 2-- knock off purse //
      Item item18 = new Item(18);
      item18.setName("knock off pruse");
      System.out.println(item18.getId() + ": " + item18.getName());
      item18.setDesc ("A brightly colored purse that looks almost like a high end purse, but is a 1/16th the price, must be atemtic.");
      System.out.println(item18.getId() + ": " + item18.getDesc());
      
   // item 19-- snow form the top of the world, located at montuus mountains //
      Item item19 = new Item(19);
      item19.setName("snow form the top of the world");
      System.out.println(item19.getId() + ": " + item19.getName());
      item19.setDesc ("Snow form the top of the monturus montians. Careful, it will melt");
      System.out.println(item19.getId() + ": " + item19.getDesc());
      
   // item 20-- door knob, located at abanoded delvement // 
      Item item20 = new Item(20);
      item20.setName("door knob");
      System.out.println(item20.getId() + ": " + item20.getName());
      item20.setDesc ("A bronze door knob that was on a trailer");
      System.out.println(item20.getId() + ": " + item20.getDesc());
   
    // item 21-- rusty key, located at therusty dungeon //
      Item item21 = new Item(21);
      item21.setName("rusty key");
      System.out.println(item21.getId() + ": " + item21.getName());
      item21.setDesc ("a key that is rusty, it can unlocated the door to all secerts, or somthing neat like that.");
      System.out.println(item21.getId() + ": " + item21.getDesc());
    
   // item 22- bent up enigine, located at the crashed space ship //
      Item item22 = new Item(22);
      item22.setName("bent up enigine");
      System.out.println(item22.getId() + ": " + item22.getName());
      item22.setDesc ("a beat up enigne form the crashed space ship. Still sems to work");
      System.out.println(item22.getId() + ": " + item22.getDesc());

   //item 23-- comet dust form the nth dimsion, found it outer, outer space //  
      Item item23 = new Item(23);
      item23.setName("comet dust form the nth dimson");
      System.out.println(item23.getId() + ": " + item23.getName());
      item23.setDesc ("comet dust of the other universe. It is near perisless");
      System.out.println(item23.getId() + ": " + item23.getDesc());

   // item 24-- dont push me button, located at the not at all evil blimp //   
      Item item24 = new Item(24);
      item24.setName("dont pust me button");
      System.out.println(item24.getId() + ": " + item24.getName());
      item24.setDesc ("a big red button, that says dont push in big, black letters.  ");
      System.out.println(item24.getId() + ": " + item24.getDesc());
      
   
 

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
     System.out.println("Drath Vs. The Evil Dr. Mars");
       System.out.println(locations[currentLocale].getName());
       System.out.println(locations[currentLocale].getDesc());
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
      } else if ( command.equalsIgnoreCase("map")  || command.equalsIgnoreCase("m")) {
         asciiMap();     
      } else { System.out.print("That is not a valid command. Valid commands are n(north),s(south),w(west),e(east),h(help),q(queit),t(take),i(inventory),m(map)and a(attack). Both upper and lower case are valid commands");};

      if (dir > -1) {   // This means a dir was set.
         totalMoves = totalMoves + 1;
    	  int newLocation = nav[currentLocale][dir];
         if (newLocation == INVALID) {
            System.out.println("You cannot go that way. Press h for help");
         } else {
            currentLocale = newLocation;
            totalScore();
            System.out.println("You have made "+totalMoves+" moves."); 
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
      System.out.print(inventory [24]);
      // doesnt work since the array is emety. 
      // fill array
   }
 
 // now for the ascii map and ascii art for the locales
 // doesnt work- stack overflow error
 // even with try catch it doesnt work- due to a unresolved compliation problem. Game works besides this isssuse.
 
 public static void asciiMap(){
	 
	 System.out.println("                              [_outer_outer_spcae]              [_crashed_space_ship_]             [_not_at_all_evil_blimp]");
	 System.out.println("                                       |                                  |                                    |  ");
	 System.out.println("                             [ Abandoned_Development_]----------[_unpleasent_meadows_]----------------[_rusty_dungeon_]");
	 System.out.println("                                       |                                  |                                    |");
	 System.out.println("                                [_icy_______cave_]-----------------[_kennel_town_]---------------------[_magick_shoppe_]");
	 System.out.println("                                       |                                  |                                    |");
	 System.out.println("                                [_vet_of_horrors__]----------------[_cat_nip_city_]------------[_whittie's space-ship_dealer]");
	 System.out.println("                                       |                                  |                                    |");
	 System.out.println("[_monturous_mountains_]------ [_uncanny__valley_]----------[_dracos_dragon_meat_factory_]----------[_cheesey_tourist_area]");
	 
 }	  
	 
}