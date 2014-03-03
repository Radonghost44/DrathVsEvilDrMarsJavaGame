package game;

public class Item {
	private int    itemId;
	   private String itemName;
	   private String itemDesc;
       private final boolean tracker=false;  // both of these boolans are used to place items in the invetory and take items form their locales
       private final boolean inventory=false;
       
       public Item (int theId) {
 	      itemId = theId;
 	   }
 	  
 	   public int getId() {
 	      return itemId;
 	   }

 	   public String getName() {
 	      return itemName;
 	   }
 	   public void setName(String value) {
 	      itemName = value;
 	   }   
 	   
 	   public String getDesc() {
 	      return itemDesc;
 	   }
 	   public void setDesc(String value) {
 	      itemDesc = value;
 	   }

       // to string method
 	  public String toString () {
		   return "[Locale object : id="+this.itemId +"name="+this.itemName+"desc="+this.itemDesc+"]";
}
}