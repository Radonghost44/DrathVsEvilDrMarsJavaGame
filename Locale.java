package game;

public class Locale {//
	private int id;
	private String name;
	private String desc;
	private Locale next = null;
	ListItem[] arrayOfItems;
	private int newLocaleNorth;
	private int newLocaleSouth;
	private int newLocaleEast;
	private int newLocaleWest;
	private int newLocaleUp;
	private int newLocaleDown;
	
	public Locale() {
		arrayOfItems = new ListItem[4];
		arrayOfItems[0] = new ListItem();
		arrayOfItems[1] = new ListItem();
		arrayOfItems[2] = new ListItem();
		arrayOfItems[3] = new ListItem();
		arrayOfItems[0] = null;
		arrayOfItems[1] = null;
		arrayOfItems[2] = null;
		arrayOfItems[3] = null;
		
	}

	public void setId(int value) {
		id = value;
	}

	public int getId() {
		return id;
	}
	public String getItem0Name() {
		return arrayOfItems[0].getarrayOfItemsName();
	}
	public String getItem0Description() {
		return arrayOfItems[0].getarrayOfItemsDesc();
	}
	public String getItem1Name() {
		return arrayOfItems[1].getarrayOfItemsName();
	}
	public String getItem1Description() {
		return arrayOfItems[1].getarrayOfItemsDesc();
	}
	public String getItem2Name() {
		return arrayOfItems[2].getarrayOfItemsName();
	}
	public String getItem2Description() {
		return arrayOfItems[2].getarrayOfItemsDesc();
	}
	public String getItem3Name() {
		return arrayOfItems[3].getarrayOfItemsName();
	}
	public String getItem3Description() {
		return arrayOfItems[3].getarrayOfItemsDesc();
	}
	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String value) {
		desc = value;
	}

	public void setNext(Locale nextDIr) {
		next = nextDIr;
	}

	public Locale getNext() {
		return next;
	}

	//
	// -- PRIVATES --
	//

	// -- To String///

	public String toString() {
		return "[Locale object : id=" + this.id + "name=" + this.name + "desc="
				+ this.desc + "nextItem" + this.next + "]";
	}

	public void setNewLocaleNorth(int i) {
		newLocaleNorth = i;
	}
	
	public int getNewLocaleNorth() {
		return newLocaleNorth;
	}

	public void setNewLocaleSouth(int i) {
		newLocaleSouth = i;
	}
	public int getNewLocaleSouth() {
		return newLocaleSouth;
	}
	public void setNewLocaleEast(int i) {
		newLocaleEast = i;

	}
	public int getNewLocaleEast() {
		return newLocaleEast;
	}
	public void setNewLocaleWest(int i) {
		newLocaleWest = i;

	}
	public int getNewLocaleWest() {
		return newLocaleWest;
	}
	public void setNewLocaleUp(int i) {
		newLocaleUp = i;

	}
	public int getNewLocaleUp() {
		return newLocaleUp;
	}
	public void setNewLocaleDown(int i) {
		newLocaleDown = i;

	}
	public int getNewLocaleDown() {
		return newLocaleDown;
	}
}
