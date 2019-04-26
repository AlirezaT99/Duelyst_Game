package model;

class Item {
    private String name;
    private String description;
    protected String itemID;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getItemID() {
        return itemID;
    }
}
