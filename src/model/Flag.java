package model;

public class Flag extends Item {
    Cell cell;
    Match match;

    public Flag(Match match, Cell cell) {
        this.match = match;
        this.cell = cell;
    }


    //getters
    public Cell getCell() {
        return cell;
    }

    public Match getMatch() {
        return match;
    }
    //getters
}
