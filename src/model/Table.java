package model;

import java.util.ArrayList;
import java.util.Iterator;

class Table {
    private Cell[][] cells;

    {
        cells = new Cell[7][];
    }

    // table initiation
    public Table() {
        for (int i = 0; i < 7; i++) {
            cells[i] = new Cell[11];
        }
        setAdjacentCells();
    }

    private void setAdjacentCells() {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 9; j++) {
                addDesiredCellToAdjacentCells(cells[i][j], i - 1, j - 1);
                addDesiredCellToAdjacentCells(cells[i][j], i, j - 1);
                addDesiredCellToAdjacentCells(cells[i][j], i + 1, j - 1);
                addDesiredCellToAdjacentCells(cells[i][j], i - 1, j);
                addDesiredCellToAdjacentCells(cells[i][j], i + 1, j);
                addDesiredCellToAdjacentCells(cells[i][j], i - 1, j + 1);
                addDesiredCellToAdjacentCells(cells[i][j], i, j + 1);
                addDesiredCellToAdjacentCells(cells[i][j], i + 1, j + 1);
            }
        }
    }

    private void addDesiredCellToAdjacentCells(Cell cell, int indexI, int indexJ) {
        cell.addCellToAdjacentCells(cells[indexI][indexJ]);
    }

    // table initiation

    {
        cells = new Cell[5][9];
    }

    public Cell getCellByCoordination(int x, int y) {
        if (x < 6 && y < 10  && x>0 && y>0)
            return cells[x][y];
        return null;
    }

    public ArrayList<Cell> adjacentCells(Cell cell) {
        ArrayList<Cell> adjacentCells = new ArrayList<>();
        if (cell.getCellCoordination().getX() > 0 && cell.getCellCoordination().getY() > 0)
            adjacentCells.add(this.getCellByCoordination(cell.getCellCoordination().getX() - 1,
                    cell.getCellCoordination().getY() - 1));
        if (cell.getCellCoordination().getY() > 0)
            adjacentCells.add(this.getCellByCoordination(cell.getCellCoordination().getX(),
                    cell.getCellCoordination().getY() - 1));
        if (cell.getCellCoordination().getX() < 4 && cell.getCellCoordination().getY() > 0)
            adjacentCells.add(this.getCellByCoordination(cell.getCellCoordination().getX() + 1,
                    cell.getCellCoordination().getY() - 1));
        if (cell.getCellCoordination().getX() > 0)
            adjacentCells.add(this.getCellByCoordination(cell.getCellCoordination().getX(),
                    cell.getCellCoordination().getY() - 1));
        if (cell.getCellCoordination().getX() < 4)
            adjacentCells.add(this.getCellByCoordination(cell.getCellCoordination().getX() + 1,
                    cell.getCellCoordination().getY() - 1));
        if (cell.getCellCoordination().getX() > 0 && cell.getCellCoordination().getY() < 9)
            adjacentCells.add(this.getCellByCoordination(cell.getCellCoordination().getX() - 1,
                    cell.getCellCoordination().getY() + 1));
        if (cell.getCellCoordination().getY() < 9)
            adjacentCells.add(this.getCellByCoordination(cell.getCellCoordination().getX(),
                    cell.getCellCoordination().getY() + 1));
        if (cell.getCellCoordination().getX() < 4 && cell.getCellCoordination().getY() < 9)
            adjacentCells.add(this.getCellByCoordination(cell.getCellCoordination().getX() + 1,
                    cell.getCellCoordination().getY() + 1));
        return adjacentCells;
    }

    //soldier finder

    ArrayList<Cell> findAllSoldiers(Player player) {
        ArrayList<Cell> wantedCells = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 9; j++) {
                if (cells[i][j].getMovableCard() != null) {
                    if (player == null)
                        wantedCells.add(cells[i][j]);
                    else if (player.equals(cells[i][j].getMovableCard().player))
                        wantedCells.add(cells[i][j]);
                }
            }
        }
        return wantedCells;
    }

    ArrayList<Cell> findAllSpecificSoldiers(Player player, boolean isHybrid, boolean isMelee, boolean isRanged) {
        ArrayList<Cell> candidateCells = findAllSoldiers(player);
        Iterator<Cell> cellIterator = candidateCells.iterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (isHybrid && cell.getMovableCard().isHybrid())
                continue;
            if (isMelee && cell.getMovableCard().isMelee())
                continue;
            if (isRanged && cell.getMovableCard().isRanged())
                continue;
            cellIterator.remove();
        }
        return candidateCells;
    }

    //find closest Soldiers

    ArrayList<Cell> findClosestSoldiers(MovableCard movableCard, Player opponent) {
        ArrayList<Cell> cellArrayList = findAllSoldiers(opponent);
        int min = getMin(movableCard, cellArrayList, 99);
        cellArrayList.removeIf(cell -> cell.findDistanceBetweenCells(movableCard.cardCell) != min);
        return cellArrayList;
    }

    private int getMin(MovableCard movableCard, ArrayList<Cell> cellArrayList, int min) {
        int length;
        for (Cell cell : cellArrayList) {
            length = cell.findDistanceBetweenCells(movableCard.cardCell);
            if (length < min)
                min = length;
        }
        return min;
    }

    // find closest Soldiers

    ArrayList<Cell> findAllMinions(Player player) {
        ArrayList<Cell> wantedCells = new ArrayList<>();
        for (int i = 1; i <= 5; i++)
            for (int j = 1; j <= 9; j++)
                if (cells[i][j].getMovableCard() instanceof MovableCard.Minion) {
                    if (player == null)
                        wantedCells.add(cells[i][j]);
                    else if (cells[i][j].getMovableCard().player.equals(player))
                        wantedCells.add(cells[i][j]);
                }
        return wantedCells;
    }

    //sodier finder
}