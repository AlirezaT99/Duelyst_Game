package model;

import java.util.ArrayList;

class Cell {

    private Coordination coordination;
    private MovableCard movableCard;
    private CollectibleItem item;
    private ArrayList<Cell> adjacentCells;

    {
        adjacentCells = new ArrayList<>();
    }

    public Coordination getCellCoordination() {
        return coordination;
    }

    public void setCellCoordination(Coordination coordination) {
        this.coordination = coordination;
    }

    public MovableCard getMovableCard() {
        return movableCard;
    }

    public void setMovableCard(MovableCard movableCard) {
        this.movableCard = movableCard;
    }

    public int findDistanceBetweenCells(Cell cell) {
        int x = Math.abs(cell.coordination.getX() - this.getCellCoordination().getX());
        int y = Math.abs(cell.coordination.getY() - this.getCellCoordination().getY());
        return x + y;
    }

    public boolean isTheseCellsAdjacent(Cell cell) {
        int x = Math.abs(this.coordination.getX() - cell.coordination.getX());
        int y = Math.abs(this.coordination.getY() - cell.coordination.getY());
        return x + y <= 2 && (x == 0 || y == 0 || (x == 1 && y == 1));
    }

    public CollectibleItem getItem() {
        return item;
    }

    void addCellToAdjacentCells(Cell cell){
        this.adjacentCells.add(cell);
    }

    //getters

    public ArrayList<Cell> getAdjacentCells() {
        return adjacentCells;
    }

    //getters
}
