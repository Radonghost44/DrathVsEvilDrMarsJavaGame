package game;

public class ListItem {

	//
	// Public
	//
	public ListItem() {
	}
	public void setarrayOfItemsName(String name) {
		this.name = name;
	}
	public void setarrayOfItemsDesc(String name) {
		this.desc = name;
	}
	public String getarrayOfItemsName() {
		return name;
	}
	
	public String getarrayOfItemsDesc() {
		return desc;
	}
	public String getListItemName() {
		return name;
	}

	public void setListItemName(String name) {
		this.name = name;
	}

	public String getListItemNameDesc() {
		return desc;
	}

	public void setListItemNameDesc(String desc) {
		this.desc = desc;
	}

	public int getListItemNameCost() {
		return cost;
	}

	public void setListItemNameCost(int cost) {
		this.cost = cost;
	}

	public ListItem getListItemNameNext() {
		return next;
	}

	public void setListItemNameNext(ListItem next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return this.name +  "     cost=" + this.cost + "\n";
	}

	//
	// Private
	//
	private String name;
	private String desc;
	private int cost;
	private ListItem next = null;
	
	
}
