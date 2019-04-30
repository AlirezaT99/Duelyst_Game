package model;

class CollectibleItem extends InfluentialItem {
    private Cell cell;
    private Match match;

    //getters

    public Cell getCell() {
        return cell;
    }

    public Match getMatch() {
        return match;
    }

    //getters

    //setters
    public void setMatch(Match match) {
        this.match = match;
    }
    //setters
}
