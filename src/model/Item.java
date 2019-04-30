package model;

public class Item {
    protected String name;
    protected String description;
    protected String itemID;

    //getters
    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }

    public String getItemID() {
        return itemID;
    }
    //getters

    //setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) { this.name = name;}
    //setters

}
