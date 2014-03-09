package game;

public class Item {
	private int    itemId;
	   private String itemName;
	   private String itemDesc;
       
       public Item (int theId) {
 	      itemId = theId;
 	   }
 	  
 	   public int getItemId() {
 	      return itemId;
 	   }

 	   public String getItemName() {
 	      return itemName;
 	   }
 	   public void setItemName(String value) {
 	      itemName = value;
 	   }   
 	   
 	   public String getItemDesc() {
 	      return itemDesc;
 	   }
 	   public void setItemDesc(String value) {
 	      itemDesc = value;
 	   }
       // to string method
 	  public String toString () {
		   return "[Locale object : id="+this.itemId +"name="+this.itemName+"desc="+this.itemDesc+"]";
}
}