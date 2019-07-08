package model.Message.AddCardCommand;

import model.Message.Message;
import view.AddCardFX;

public class AddCardCommand extends Message {
    private boolean isSpell = false;
    private boolean isHero = false;
    private boolean isMinion = false;
    private String impact1 = "";
    private String impact2 = "";
    private String name = "";
    private int cost;
    private int health;
    private int damage;
    private int range;
    private int coolDown;
    private String heroSpecialPower;
    private String activation;
    private String attackType;

    public AddCardCommand(boolean isSpell,String impact11,String impact12,String impact13, String impact14,
                          String impact21, String impact22, String impact23, String impact24, String name, int cost){
        super("");
        isHero = false;
        isMinion = false;
        this.isSpell = isSpell;
        this.impact1 = impact11+" "+impact12+" "+impact13+" "+impact14;
        this.impact2 = impact21+" "+impact22+" "+impact23+" "+impact24;
        this.name = name;
        this.cost = cost;
    }

    public AddCardCommand(boolean isHero, int cost, int health, int damage, int range, int coolDown, String spellName, String name, String attackType){
        super("");
        this.name = name;
        isSpell = false;
        isMinion = false;
        this.isHero  = isHero;
        this.cost = cost;
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.coolDown = coolDown;
        this.heroSpecialPower = spellName;
        this.attackType = attackType;
    }

    public AddCardCommand( boolean isMinion, int cost, int health, int damage, int range, String activation, String attackType){
        super("");
        isSpell = false;
        isHero = false;
        this.isMinion = isMinion;
        this.cost = cost;
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.activation = activation;
        this.attackType = attackType;
    }


    public boolean isSpell() {
        return isSpell;
    }

    public void setSpell(boolean spell) {
        isSpell = spell;
    }

    public boolean isHero() {
        return isHero;
    }

    public void setHero(boolean hero) {
        isHero = hero;
    }

    public boolean isMinion() {
        return isMinion;
    }

    public void setMinion(boolean minion) {
        isMinion = minion;
    }

    public String getImpact1() {
        return impact1;
    }

    public void setImpact1(String impact1) {
        this.impact1 = impact1;
    }

    public String getImpact2() {
        return impact2;
    }

    public void setImpact2(String impact2) {
        this.impact2 = impact2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public String getHeroSpecialPower() {
        return heroSpecialPower;
    }

    public void setHeroSpecialPower(String heroSpecialPower) {
        this.heroSpecialPower = heroSpecialPower;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }
}
