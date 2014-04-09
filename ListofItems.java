package game;

public class ListofItems {
	public ListofItems() {
    }

    public  String getListofItemsName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ListItem getHead() {
        return head;
    }
    public void setHead(ListItem head) {
        this.head = head;
    }

    public ListItem getLast() {
        return last;
    }
    public void setLast(ListItem last) {
        this.last = last;
    }

    public void add(ListItem item) {
        // System.out.println("adding " + item.toString());
        if (this.head == null) {
            // The list is empty.
            this.head = item;
            this.last = item;
        } else {
            // The list is NOT empty.
            this.last.setListItemNameNext(item);
            this.last = item;

            // Before:
            // 1. Move to the end of the list.
            /*
            ListItem lastItem = this.head;
            while (lastItem.getNext() != null) {
                lastItem = lastItem.getNext();
            }
            // 2. Attach the passed-in item to the last item in the list.
            lastItem.setNext(item);
            */
        }
    }
        public void removeItem(ListofItems listName, String deadItem){
        if (listName.head.getListItemNameDesc().equalsIgnoreCase(deadItem)){
        	listName.head = head.getListItemNameNext();
        } else {
        	ListItem current;
        	ListItem previous;
        	current = head.getListItemNameNext();
        	previous = head;
        	System.out.println("current = " + current + "previous = " + previous );
        	while (current != null && !(current.getListItemName().equalsIgnoreCase(deadItem))) {
        		previous = current;
        		current = current.getListItemNameNext();
        	}
        	if (current != null && current.getListItemName().equalsIgnoreCase(deadItem)) {
        		previous.setListItemNameNext(current.getListItemNameNext());
        	} else {System.out.println("Could not find the item");
        			
        }
        
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
		/*sb.append("[ListMan: name=" + this.name + " desc=" + this.desc + "] List Items:\n");*/
        ListItem currentItem = this.head;
        while (currentItem != null) {
            sb.append(currentItem.toString());
            
            currentItem = currentItem.getListItemNameNext();
        }
        return sb.toString();
    }

    //
    // Private
    //
    private String name;
    private String desc;
    private ListItem head = null;
    private ListItem last = null;
}

