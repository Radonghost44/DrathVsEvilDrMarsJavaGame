package game;

public class ListofLocales {

	    public ListofLocales() {
	    }

	    public String getListOfLocalesName() {
	        return name;
	    }
	    public void setDirName(String name) {
	        this.name = name;
	    }

	    public String getDirDesc() {
	        return desc;
	    }
	    public void setDirDesc(String desc) {
	        this.desc = desc;
	    }

	    public Locale getDirHead() {
	        return head;
	    }
	    public void setDirHead(Locale head) {
	        this.head = head;
	    }

	    public Locale getDirLast() {
	        return last;
	    }
	    public void setDirLast(Locale last) {
	        this.last = last;
	    }

	    public void add(Locale locn) {
	        // System.out.println("adding " + item.toString());
	        if (this.head == null) {
	            // The list is empty.
	            this.head = locn;
	            this.last = locn;
	        } else {
	            // The list is NOT empty.
	            this.last.setNext(locn);
	            this.last = locn;
	        }
	        
	    }

	    @Override
	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("[ListMan: name=" + this.name + " desc=" + this.desc + "] List Items:\n");
	        Locale currentLocale = this.head;
	        while (currentLocale != null) {
	            sb.append(currentLocale.toString());
	            sb.append("\n");
	            currentLocale = currentLocale.getNext();
	        }
	        return sb.toString();
	    }

	    //
	    // Private
	    //
	    private String name;
	    private String desc;
	   
	    private Locale head = null;
	    private Locale last = null;
	}
