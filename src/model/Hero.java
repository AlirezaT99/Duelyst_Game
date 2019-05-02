package model;

import java.util.ArrayList;

public class Hero extends MovableCard {
    private static ArrayList<Hero> heroes = new ArrayList<>();
    private Spell heroSpell;
    private int spellCost;
    private int spellCoolDown;
    private Impact onHitImpact;
    private int spellManaCost;

    public Hero(String name, int health, int damage, Spell heroSpell, int spellCoolDown) {
        this.heroSpell = heroSpell;
        this.spellCost = heroSpell.getCost();
        this.spellCoolDown = spellCoolDown;
        this.setHealth(health);
        this.name = name;
        this.setDamage(damage);
        this.onHitImpact = null;
    }

    public void attack(Cell cell) {
        super.attack(cell);
        if (onHitImpact != null)
            onHitImpact.setImpactArea(this.player,cell,this.cardCell);
    }

    public void counterAttack(MovableCard opponent) {
        super.counterAttack(opponent);
        if (onHitImpact != null)
            onHitImpact.setImpactArea(this.player, opponent.cardCell, this.cardCell);
    }

    @Override
    public String toString(boolean showCost) {
        String classType = getClassType(this);
        String output = "Name : " + name + " - AP : " + this.getDamage() + " - HP : " + this.getHealth() + " - Class : "
                + classType + " - Special power : " + description;
        if (showCost) output = output + " - Sell Cost : " + getCost();
       // output = output + "\n";
        return output;
    }

    public void castSpell(Cell cell) {
//            heroSpell.castCard(this.getMatch(), cell);
        // check should be in spell class
        // if check
        // cast spell
        // put the impact of spell in all targets impacts applied to this one
    }
    // getters

    public int getSpellCost() {
        return spellCost;
    }

    public int getSpellCoolDown() {
        return spellCoolDown;
    }

    public static ArrayList<Hero> getHeroes() {
        return heroes;
    }
// getters

    public static void addToHeroes(Hero hero){
        heroes.add(hero);
    }
}