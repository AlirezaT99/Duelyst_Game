package model;

import java.util.ArrayList;

class Table {
    private Cell[][] cells;

    {
        cells = new Cell[5][9];
    }

    public Cell getCellByCoordination(int x, int y) {
        if (x < 5 && y < 9)
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
}