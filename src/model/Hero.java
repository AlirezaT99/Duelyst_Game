package model;

//setters}
class Hero extends MovableCard {

    private Spell heroSpell;
    private int spellCost;
    private int spellCoolDown;

    public Hero(String name, int health, int damage, Spell heroSpell, int spellCost, int spellCoolDown) {
        //Ali : sepehr man impact ro kardam int ke ba tarifet tou MovableCard bekhoune
        this.heroSpell = heroSpell;
        this.spellCost = spellCost;
        this.spellCoolDown = spellCoolDown;
        this.health = health;
        this.name = name;
        this.damage = damage;
    }

    public void castSpell(Cell cell) {
        // check should be in spell class
        // if check
        // cast spell
        // put the impact of spell in all targets impacts applied to this one
    }

    // getter

    public int getSpellcost() {
        return spellCost;
    }

    public int getSpellCoolDown() {
        return spellCoolDown;
    }

    // getter}
}
