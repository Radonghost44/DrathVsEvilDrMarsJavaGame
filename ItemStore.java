package game;

public class ItemStore extends Locale {
	public ItemStore(int theId) {
		super(theId);
		// TODO Auto-generated constructor stub
	}
	private boolean sold=false;
	 public void SoldItem (boolean sell) {
	      sold = sell;
	   }
	public boolean getSold() {
	      return sold;
	   }
	

}
