package game;

public class directionMangerer {
	
//TODO: set up the other five directions after north works
	// directionManger works as a pointer in Locale for the navigation.
	    //
	    // Public
	    //
	    public directionMangerer() {
	    }

	    public String getName() {
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

	    public north getHead() {
	        return northHead;
	    }
	    public void setHead(north head) {
	        this.northHead = head;
	    }

	    public north getLast() {
	        return northLast;
	    }
	    public void setLast(north last) {
	        this.northLast = last;
	    }

	    public void add(north item) {
	        // System.out.println("adding " + item.toString());
	        if (this.northHead == null) {
	            // The list is empty.
	            this.northHead = item;
	            this.northLast = item;
	        } else {
	            // The list is NOT empty.
	            this.northLast.setNext(item);
	            this.northLast = item;

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



	    // TODO Add length property

	    @Override
	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("[ListMan: name=" + this.name + " desc=" + this.desc + "] List Items:\n");
	        north currentItem = this.northHead;
	        while (currentItem != null) {
	            sb.append(currentItem.toString());
	            sb.append("\n");
	            currentItem = currentItem.getNorthNext();
	        }
	        return sb.toString();
	    }

	    //
	    // Private
	    //
	    private String name;
	    private String desc;
	    private north northHead = null;
	    private north northLast = null;
	}


