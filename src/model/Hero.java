package model;

import java.util.ArrayList;

public class Hero extends MovableCard {
    private static ArrayList<Hero> heroes = new ArrayList<>();
    private Spell heroSpell;
    private int spellCost;
    private int spellCoolDown;

    public Hero(String name, int health, int damage, Spell heroSpell, int spellCoolDown) {
        this.heroSpell = heroSpell;
        this.spellCost = heroSpell.manaCost;
        this.spellCoolDown = spellCoolDown;
        this.setHealth(health);
        this.name = name;
        this.setDamage(damage);
    }

    public Hero copy() {
        Hero hero = new Hero(this.name, this.getHealth(), this.getDamage(), this.heroSpell == null ? null : this.heroSpell.copy(), this.spellCoolDown);
        hero.setCardCollectionID(this.collectionID);
        hero.manaCost = manaCost;
        hero.name = name;
        hero.cell = cell;
        hero.cost = cost;
        hero.match = match;
        hero.player = player;
        hero.cardID = cardID;
        hero.description = description;
        hero.moveRange = 2;
        hero.maxAttackRange = 2;
        hero.minAttackRange = 1;
        return hero;
    }

    @Override
    public int attack(MovableCard opponent) {
        int returnValue = super.attack(opponent);
        if (onAttackImpact != null)
            onAttackImpact.getImpactAreaClass().setImpactArea(this.player, opponent.cardCell, this.cardCell);
        return returnValue;
    }

    @Override
    public void counterAttack(MovableCard opponent) {
        super.counterAttack(opponent);
        if (onDefendImpact != null)
            onDefendImpact.getImpactAreaClass().setImpactArea(this.player, opponent.cardCell, this.cardCell);
    }

    @Override
    public String toString(boolean showCost) {
        String classType = getClassType(this);
        String output = "Name : " + name + " - AP : " + this.getDamage() + " - HP : " + this.getHealth() + " - Class : "
                + classType + " - Special power : " + description;
        if (showCost) output = output + " - Sell Cost : " + getCost();
        //output = output + "\n";
        return output;
    }

    public void castSpell(Cell cell) {
        //heroSpell.castCard(this.getMatch(), cell);
        // check should be in spell class
        // if check
        // cast spell
        // put the impact of spell in all targets impacts applied to this one
         this.heroSpell.castCard(cell, this.player);
    }

    // getters

    public int getSpellCost() {
        return spellCost;
    }

    public int getSpellCoolDown() {
        return spellCoolDown;
    }

    public static Hero getHeroByName(String name) {
        for (Hero hero : heroes)
            if (hero.getName().equals(name))
                return hero.copy();
        return null;
    }

    public static ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public Spell getHeroSpell() {
        return heroSpell;
    }

    // getters

    public static void addToHeroes(Hero hero) {
        heroes.add(hero);
    }
}