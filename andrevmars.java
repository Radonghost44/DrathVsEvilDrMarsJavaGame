import java.util.Scanner;

public class AndreVsMars {

  // Global
  public static int currentLocale = 0;  // Player starts in kennel town.
  public static String command;
  public static boolean stillPlaying = true;
   int [][] locationNavigation={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};// used for navigation0 
   int [] ItemsInventory={};// fill up with items   
  public static void main(String[] args) {
     init();    
     updateDisplay();
     
     // Game Loop
     while (stillPlaying) {
        getCommand();
        navigate();
        updateDisplay();
     }  
     System.out.println("Thank you for playing.");
  }
  
  

  private static void init() {
     command = new String();
     stillPlaying = true;
  }

  private static void updateDisplay() {
     String msg = new String();
     msg = "";
     switch (currentLocale) {
        case 0: msg = "space ship";
                break;
        case 1: msg = "pit of dispair";
                break;
        case 2: msg = "laser room";
                break;
        
        default: msg = "currentLocale " + currentLocale + " does not compute.";
     }
     System.out.println(msg);
  } 

  private static void getCommand() {
     Scanner inputReader = new Scanner(System.in);
     // command is global.
     command = inputReader.nextLine();     
  }

  private static void navigate() {
     if ( command.equals("north") || command.equals("n") ) {
        // We are going NORTH. But from where...?
        if (currentLocale == 1) {
           currentLocale = 0;           
        } else if (currentLocale == 0) {
           currentLocale = 2;       
        }

     } else if ( command.equals("south") || command.equals("s") ) {
        // We are going SOUTH. But from where...?
        if (currentLocale == 2) {
           currentLocale = 0;           
        } else if (currentLocale == 0) {
           currentLocale = 1;       
        }

        
     } else if ( command.equals("quit") ) {
        stillPlaying = false;
     }
  }

}