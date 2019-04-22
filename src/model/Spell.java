package model;

class Spell extends Card {
    private Impact impact;
    private int[] areaTargetSquare = new int[2];
    private Impact primaryImpact;
    private Impact secondaryImpact;

    public boolean isCastingValid(Match match, Cell cell, Impact impact) {
        impact.setImpactArea(match.currentTurnPlayer(),match.notCurrentTurnPlayer(),match,cell);
        for(int i = 0; i < impact.getImpactArea().size(); i++){
            if(impact.getImpactArea().get(i).equals(cell))
                return true;
        }
        return false;
//        if (this.manaCost > match.currentTurnPlayer().getMana()) {
//            // You don't have enough mana bayad tou View chaap she
//            return false;
//        }
//        if (cell.getCellCoordination().getX() >= 5 || cell.getCellCoordination().getY() >= 9 ||
//                areaTargetSquare[0] <= 3 && cell.getCellCoordination().getX() >= (6 - areaTargetSquare[0]) ||
//                areaTargetSquare[1] <= 3 && cell.getCellCoordination().getY() >= (10 - areaTargetSquare[1])
//        ) {
//            // Invalid target bayad tou View chaap she
//            return false;
//        }
//
//        if (impact.isValidOnEnemies()) {
//            if (this.areaTargetSquare[0] == 1 && this.areaTargetSquare[1] == 1 &&
//                    cell.getMovableCard() != null && !cell.getMovableCard().player.equals(match.currentTurnPlayer())
//                    && impact.isValidOnMinion() && impact.isValidOnHero() && !impact.isValidOnAllies()) // Total Disarm, Fireball, Shock --> "یک نیروی دشمن"
//                return true;
//            if (impact.isValidOnHero() && !impact.isValidOnMinion() && !impact.isValidOnAllies()) // Lightning Bolt
//                return true;
//            if (!impact.isValidOnHero() && impact.isValidOnMinion() && !impact.isValidOnAllies()
//                    && cell.getMovableCard().getClass().toString() == "Minion") // Weakening
//                return true;
//            if (areaTargetSquare[1] > 3 && impact.isValidOnHero() && impact.isValidOnMinion()) //All Disarm, All Poison
//                return true;
//            if (areaTargetSquare[0] == 1 && this.areaTargetSquare[1] == 1 &&
//                    impact.isValidOnAllies() && cell.getMovableCard() != null) // Dispel
//                return true;
//            if (!impact.isValidOnAllies() && areaTargetSquare[0] == 5 && areaTargetSquare[1] == 1) { // All Attack
//                for (int i = 0; i < 5; i++) {
//                    if (match.table.getCellByCoordination(i, cell.getCellCoordination().getY()).getMovableCard() != null
//                            && !match.table.getCellByCoordination(i, cell.getCellCoordination().getY()).getMovableCard().player
//                            .equals(match.currentTurnPlayer()))
//                        return true;
//                }
//            }
//            if (!impact.isValidOnHero() && impact.isValidOnMinion() && !impact.isValidOnAllies()
//                    && cell.getMovableCard().getClass().toString().equals("Hero")
//                    && cell.getMovableCard().player.equals(match.currentTurnPlayer())) { // Kings Gaurd
//                //
//                for (Cell adjacentCell : match.table.adjacentCells(cell)) {
//                    if (adjacentCell.getMovableCard() != null
//                            && adjacentCell.getMovableCard().getClass().toString().equals("Minion"))
//                        return true;
//                }
//            }
//        }
//        if (impact.isValidOnAllies()) {
//            if (this.areaTargetSquare[0] == 1 && this.areaTargetSquare[1] == 1 && cell.getMovableCard() != null &&
//                    !cell.getMovableCard().player.equals(match.currentTurnPlayer())
//                    && impact.isValidOnMinion() && impact.isValidOnHero()) // Empower, Madness, Power Up, Health with Profit --> "یک نیروی خودی"
//                return false;
//            if (impact.isValidOnHero() && !impact.isValidOnMinion() && !impact.isValidOnEnemies()) // God Strength
//                return true;
//            if (!impact.isValidOnHero() && impact.isValidOnMinion() && !impact.isValidOnEnemies() &&
//                    cell.getMovableCard().getClass().toString() == "Minion") // Sacrifice
//                return true;
//            if (areaTargetSquare[1] > 3 && impact.isValidOnHero() && impact.isValidOnMinion()) //All Power,
//                return true;
//        }
//        return false;
    }

    public void castCard(Match match, Cell cell) {
        if (isCastingValid(match, cell, primaryImpact))
            primaryImpact.doImpact();
        if (secondaryImpact != null && isCastingValid(match, cell, secondaryImpact))
            secondaryImpact.doImpact();
    }

    //getters
    public Impact getPrimaryimpact() {
        return primaryImpact;
    }

    public Impact getSecondaryImpact() {
        return secondaryImpact;
    }
    //getters

    //setters

    public void setName(String name) {
        this.name = name;
    }

    public void setAreaTargetSquare(int[] areaTargetSquare) {
        this.areaTargetSquare = areaTargetSquare;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPrimaryImpact(Impact primaryImpact) {
        this.primaryImpact = primaryImpact;
    }

    public void setSecondaryImpact(Impact secondaryImpact) {
        this.secondaryImpact = secondaryImpact;
    }
    //setters
}
