package game;

public class north {

    //
    // Public
    //
    public north() {
    }

    public String getName() {
        return northName;
    }
    public void setName(String name) {
        this.northName = name;
    }

    public String getDesc() {
        return northDesc;
    }
    public void setDesc(String desc) {
        this.northDesc = desc;
    }


    public north getNorthNext() {
        return northNext;
    }
    public void setNext(north next) {
        this.northNext = next;
    }

    @Override
    public String toString() {
        return "[ListItem name=" + this.northName
                      + " desc=" + this.northDesc
                      + "]";
    }

    //
    // Private
    //
    private String northName;
    private String northDesc;
    private north northNext = null;
}

