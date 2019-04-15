package model;

class Cell {
    private Coordination coordination;
    private MovableCard movableCard;
    private Item item;

    public Coordination getCellCoordination() {
        return coordination;
    }
    public void setCellCoordination(Coordination coordination){ this.coordination = coordination;}

    public MovableCard getMovableCard() {
        return movableCard;
    }

    public void setMovableCard(MovableCard movableCard) {
        this.movableCard = movableCard;
    }

    public Item getItem() {
        return item;
    }

//        public Cell findCellByCoordination(Coordination coordination){
//
//        }

}
