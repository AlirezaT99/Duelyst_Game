package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Cell {

    private Coordination coordination;
    private MovableCard movableCard;
    private CollectibleItem item;
    private ArrayList<Cell> adjacentCells;
    ArrayList<Impact> cellImpacts;

    {
        adjacentCells = new ArrayList<>();
    }

    int findDistanceBetweenCells(Cell cell) {
        int x = Math.abs(cell.coordination.getX() - this.getCellCoordination().getX());
        int y = Math.abs(cell.coordination.getY() - this.getCellCoordination().getY());
        return x + y;
    }

    boolean isTheseCellsAdjacent(Cell cell) {
        int x = Math.abs(this.coordination.getX() - cell.coordination.getX());
        int y = Math.abs(this.coordination.getY() - cell.coordination.getY());
        return x + y <= 2 && (x == 0 || y == 0 || (x == 1 && y == 1));
    }

    void addCellToAdjacentCells(Cell cell) {
        this.adjacentCells.add(cell);
    }

    void addToImpacts(Impact impact) {
        cellImpacts.add(impact);
    }

    public boolean isCellFree() {
        return movableCard == null && item == null;
    }

    //getters
    public ArrayList<Cell> getAdjacentCells() {
        return adjacentCells;
    }

    ArrayList<Cell> getFullAdjacentCells(Player player) {
        ArrayList<Cell> cellArrayList = this.getAdjacentCells();
        cellArrayList.removeIf(cell -> cell.getMovableCard() == null || !cell.getMovableCard().player.equals(player));
        return cellArrayList;
    }

    public MovableCard getMovableCard() {
        return movableCard;
    }

    public Coordination getCellCoordination() {
        return coordination;
    }

    public CollectibleItem getItem() {
        return item;
    }
    //getters

    //setters

    void setCellCoordination(Coordination coordination) {
        this.coordination = coordination;
    }

    void setMovableCard(MovableCard movableCard) {
        this.movableCard = movableCard;
    }


    //setters

}
