package model;

class Flag extends Item {
    Cell cell;
    Match match;

    public Cell getCell() {
        return cell;
    }

    public Match getMatch() {
        return match;
    }

    public Flag(Match match, Cell cell) {
        this.match = match;
        this.cell = cell;
    }
}
