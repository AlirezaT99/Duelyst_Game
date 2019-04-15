package model;

class CollectibleItem extends InfluentialItem {
    private Cell cell;
    private Match match;

    public Cell getCell() {
        return cell;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
