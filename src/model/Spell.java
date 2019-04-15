package model;

class Spell extends Card {
    private String name;
    //private int AreaTargetSquare;
    private Impact primaryimpact = super.getImpact();
    private Impact secondaryImpact;

    public Impact getPrimaryimpact() {
        return primaryimpact;
    }

    public boolean isCastingValid(Cell cell, Impact impact) {
//        if(everyWhere)
//            return true;
//        if(singletarget)
//        {
//            if(primaryimpact.e)
//        }
    }

    public void castCard(Cell cell) {
        if (isCastingValid(cell, primaryimpact))
            primaryimpact.doImpact();
        if (secondaryImpact != null && isCastingValid(cell, secondaryImpact))
            secondaryImpact.doImpact();
    }
}
