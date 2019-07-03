package model;

import com.google.gson.*;
import javafx.scene.Scene;

import java.io.FileOutputStream;
import java.io.*;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Card card = cardCreator(scanner);
            Gson gson = new Gson();
            String fileName = "src/model/temp/";
            fileName += card instanceof Minion ? "minions/" : (card instanceof Hero ? "heroes/" : "spells/");
            fileName += fileNameCreator(card.getName()) + ".json";
            try (FileOutputStream fos = new FileOutputStream(fileName);
                 OutputStreamWriter isr = new OutputStreamWriter(fos,
                         StandardCharsets.UTF_8)) {
                gson.toJson(card, isr);
            }
        }
    }

    public static void cardCreator() throws FileNotFoundException, IOException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        //primary Impact setting
        //target setting
        Scanner scanner = new Scanner(System.in);
        Impact impact = impactCreator(scanner);


        //creating spells

//        Spell spell = new Spell();
//        spell.setName("Kings Guard");
//        String fileName = "src/model/spells/" + fileNameCreator(spell.getName()) + ".json";
//        spell.description = "kills one of the minions around our hero randomly.";
//        spell.setCost(1750);
//        spell.setManaCost(9);
//        spell.setPrimaryImpact(primaryImpact);
//        if (secondaryImpact.getImpactTypeId().length() != 0) {
//            spell.setSecondaryImpact(secondaryImpact);
//        }
//        try (FileOutputStream fos = new FileOutputStream(fileName);
//             OutputStreamWriter isr = new OutputStreamWriter(fos,
//                     StandardCharsets.UTF_8)) {
//            gson.toJson(spell, isr);
//        }
        // end of creating spells
        //creating minions/
        Minion minion = new Minion();
        minion.setName("ThePersianPahlevoon"); //
        minion.setCost(600);
        minion.setManaCost(9);
        minion.setHealth(24);
        minion.setDamage(6);
        minion.setMelee(true);
        minion.setRanged(false);
        minion.setHybrid(false);
        //minion.setMaxAttackRange(5);
        minion.setComboAttacker(false);
        minion.setDescription("Deals 5 extra damage to ex targets");
//        minion.setPassiveImpact(primaryImpact);
        //minion.setSecondaryImpact(secondaryImpact);
//            minion.setOnAttackImpact(primaryImpact);
        String fileName = "src/model/minions/" + fileNameCreator(minion.getName()) + ".json";
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            gson.toJson(minion, isr);
        }

        //creating Heroes
//        Spell heroSpell = new Spell();
//        heroSpell.setPrimaryImpact(primaryImpact);
//        heroSpell.setManaCost(0);
//        heroSpell.setDescription("has three continuous holly buffs.");
//        Hero hero = new Hero("Esfandiar",35,3,heroSpell,0);
//        hero.setCost(11000);
//        hero.setMelee(false);
//        hero.setRanged(false);
//        hero.setHybrid(true);
//        hero.setDescription("has three continuous holly buffs.");
//        hero.setMaxAttackRange(3);
//        //minion.setPassiveImpact(primaryImpact);
//        //minion.setSecondaryImpact(secondaryImpact);
//        String fileName = "src/model/heroes/" + fileNameCreator(hero.getName()) + ".json";
//        try (FileOutputStream fos = new FileOutputStream(fileName);
//             OutputStreamWriter isr = new OutputStreamWriter(fos,
//                     StandardCharsets.UTF_8)) {
//            gson.toJson(hero, isr);
//        }
        //creating Items
//        //creating UsableItems
//         UsableItem usableItem = new UsableItem();
//        usableItem.setName("Damoul'sssssssssssssss Bow");
//        usableItem.setCost(30000);
//        usableItem.setDescription("upon our hero attacking, he/she disarms his victim for a turn.");
//        usableItem.setPrimaryImpact(primaryImpact);
//        String fileName = "src/model/items/usableitems/" + fileNameCreator(usableItem.getName()) + ".json";
//        try (FileOutputStream fos = new FileOutputStream(fileName);
//             OutputStreamWriter isr = new OutputStreamWriter(fos,
//                     StandardCharsets.UTF_8)) {
//            gson.toJson(usableItem, isr);
//        }

        //creating collectibleitems
//        CollectibleItem collectibleItem = new CollectibleItem();
//        collectibleItem.setName("Death's Curse");
//        collectibleItem.setDescription("gives a minion the ability to hit a close random force a total of 8 units of HPs.");
//        collectibleItem.setPrimaryImpact(primaryImpact);
//        //collectibleItem.setSecondaryImpact(secondaryImpact);
//        String fileName = "src/model/items/collectibleitems/" + fileNameCreator(collectibleItem.getName()) + ".json";
//        try (FileOutputStream fos = new FileOutputStream(fileName);
//             OutputStreamWriter isr = new OutputStreamWriter(fos,
//                     StandardCharsets.UTF_8)) {
//            gson.toJson(collectibleItem, isr);
//        }


    }

    private static Card cardCreator(Scanner scanner) {
        System.out.println("Begin--------------------");
        scanner.nextLine();
        System.out.println("Enter Name");
        String name = scanner.nextLine();
        System.out.println("Enter Cost");
        int cost = scanner.nextInt();
        System.out.println("Enter mana cost");
        int manaCost = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter description");
        String description = scanner.nextLine();
        System.out.println("Enter card type(spell(s)/minion(m)/hero(h)");
        String c = scanner.next();
        if (c.equals("s")) {
            Spell spell = spellCreator(scanner);
            spell.name = name;
            spell.cost = cost;
            spell.manaCost = manaCost;
            spell.setDescription(description);
            return spell;
        } else if (c.equals("m")) {
            Minion minion = minionCreator(scanner, name);
            minion.setManaCost(manaCost);
            minion.setCost(cost);
            minion.setDescription(description);
            return minion;
        } else if (c.equals("h")) {
            Hero hero = heroCreator(scanner, name);
            hero.setCost(cost);
            hero.setManaCost(0);
            hero.setDescription(description);
            return hero;
        }

        return null;
    }

    private static Spell spellCreator(Scanner scanner) {
        System.out.println("Create Primary Impact");
        Impact primaryImpact = impactCreator(scanner);
        Impact secondaryImpact = null;
        System.out.println("does it have secondary Impact?(y/n)");
        String c = scanner.next();
        if (c.equals("y")) {
            System.out.println("Create secondary Impact");
            secondaryImpact = impactCreator(scanner);
        }
        return new Spell(primaryImpact, secondaryImpact);

    }

    private static Hero heroCreator(Scanner scanner, String name) {
        System.out.println("Creating hero spell");
        Spell spell = spellCreator(scanner);
        System.out.println("spell cool down:");
        int spellCoolDown = scanner.nextInt();
        System.out.println("spell mana cost:");
        int manaCost = scanner.nextInt();
        spell.manaCost = manaCost;
        System.out.println("health:");
        int health = scanner.nextInt();
        System.out.println("damage:");
        int damage = scanner.nextInt();
        System.out.println("Attack Type : (melee(m)/ranged(r)/hybrid(h)");
        String  c = scanner.next();
        System.out.println("Attack Range:");
        int range = scanner.nextInt();
        Hero hero = new Hero(name, health, damage, spell, spellCoolDown);
        hero.maxAttackRange = range;
        if(c.equalsIgnoreCase("m"))
            hero.isMelee = true;
        else if(c.equalsIgnoreCase("r"))
            hero.isRanged = true;
        else if(c.equalsIgnoreCase("h"))
            hero.isHybrid = true;
        return hero;
    }

    private static Minion minionCreator(Scanner scanner, String name) {
        System.out.println("Creating movable card");
        System.out.println("health:");
        int health = scanner.nextInt();
        System.out.println("damage:");
        int damage = scanner.nextInt();
        System.out.println("attack range: ");
        int attackRange = scanner.nextInt();
        Minion minion = new Minion();
        minion.maxAttackRange = attackRange;
        System.out.println("Attack Type : (melee(m)/ranged(r)/hybrid(h)");
        String c = scanner.next();
        if(c.equalsIgnoreCase("m"))
            minion.isMelee = true;
        else if(c.equalsIgnoreCase("r"))
            minion.isRanged = true;
        else if(c.equalsIgnoreCase("h"))
            minion.isHybrid = true;
        minion.setName(name);
        minion.setHealth(health);//dup
        minion.setDamage(damage);
        scanner.nextLine();
        System.out.println("has summon Impact?(y/n)");
        if (scanner.nextLine().equals("y"))
            minion.setOnAttackImpact(impactCreator(scanner));
        System.out.println("has on death Impact?(y/n)");
        if (scanner.nextLine().equals("y"))
            minion.setDyingWishImpact(impactCreator(scanner));
        System.out.println("has on attack Impact?(y/n)");
        if (scanner.nextLine().equals("y"))
            minion.setOnAttackImpact(impactCreator(scanner));
        System.out.println("has on defend Impact?(y/n)");
        if (scanner.nextLine().equals("y"))
            minion.setOnDefendImpact(impactCreator(scanner));
        System.out.println("has any Impact?(y/n)");
        if (scanner.nextLine().equals("y"))
            minion.getImpactsAppliedToThisOne().add(impactCreator(scanner));
        return minion;
    }

    private static Impact impactCreator(Scanner scanner) {
        // 0.(0,1)"SelectedCellImportance"
        // 1.(0,1)"ValidOnAll"
        // 2.(0,1)"ValidOnAWholeTeam"
        // 3.(0-2)"onWhichTeam"{friendly, hostile, both}
        // 4.(0-2)"targetSoldierType"{hero,minion,both}
        // 5.(2,3)"SquareLength"
        // 6.column(1,0)
        // 7.row(0,1)
        // 8.adjacentToObjectedCell(0-3){none, one , all,all+self}
        // 9.random(0,1)
        // 10.soldierAttackType(0-3){doesn't matter,melee, ranged, hybrid,ranged & hybrid}
        // 11.closestSoldiers(0,1)
        // 12.ranged(0-n)
        StringBuilder targetTypeId = new StringBuilder();
        System.out.println("Target Type Id");
        scanner.nextLine();
        getNextThing(scanner, targetTypeId, "0.(0,1)\"SelectedCellImportance\"");
        getNextThing(scanner, targetTypeId, "1.(0,1)\"ValidOnAll\"");
        getNextThing(scanner, targetTypeId, "2.(0,1)\"ValidOnAWholeTeam\"");
        getNextThing(scanner, targetTypeId, "3.(0-2)\"onWhichTeam\"{friendly, hostile, both}");
        getNextThing(scanner, targetTypeId, "4.(0-2)\"targetSoldierType\"{hero,minion,both}");
        getNextThing(scanner, targetTypeId, "5.(2,3)\"SquareLength\"");
        getNextThing(scanner, targetTypeId, "6.column(1,0)");
        getNextThing(scanner, targetTypeId, "7.row(0,1)");
        getNextThing(scanner, targetTypeId, "8.adjacentToObjectedCell(0-3){none, one , all,all+self}");
        getNextThing(scanner, targetTypeId, "9.random(0,1)");
        getNextThing(scanner, targetTypeId, "10.soldierAttackType(0-3){doesn't matter,melee, ranged, hybrid,ranged & hybrid}");
        getNextThing(scanner, targetTypeId, "11.closestSoldiers(0,1)");
        getNextThing(scanner, targetTypeId, "12.ranged(0-n)");

        StringBuilder impactTypeId = new StringBuilder();
        // 0.(0,1)isPositive
        // 1.(0-6)buffType{holy,power,poison,weakness,stun,disarm}
        // 2.(0-3)QuantityChange{mana,health,damage}
        // 3.(0,1)quantityChangeSign{negative/isPositiveImpact}
        // 4,5.(0,n)"impactQuantity"
        // 6.(0-3)PassivePermanent{none , passive , permanent , continuous}
        // 7.(0,n)turnsToBeActivated
        // 8,9.(0,n)turnsActive
        // 10.dispel(0-2){none,buffDispel,allPositiveDispel}
        // 11.cellImpact(0-4){none,poison,fire,holy}
        //12.isOnCell(0,1)
        getNextThing(scanner, impactTypeId, "0.(0,1)isPositive");
        getNextThing(scanner, impactTypeId, "1.(0-6)buffType{holy,power,poison,weakness,stun,disarm}");
        getNextThing(scanner, impactTypeId, "2.(0-3)QuantityChange{mana,health,damage}");
        getNextThing(scanner, impactTypeId, "3.(0,1)quantityChangeSign{negative/isPositiveImpact}");
        getNextThing(scanner, impactTypeId, "4,5.(0,n)\"impactQuantity\"");
        getNextThing(scanner, impactTypeId, "6.(0-3)PassivePermanent{none , passive , permanent , continuous}");
        getNextThing(scanner, impactTypeId, "7.(0,n)turnsToBeActivated");
        getNextThing(scanner, impactTypeId, "8,9.(0,n)turnsActive");
        getNextThing(scanner, impactTypeId, "10.dispel(0-2){none,buffDispel,allPositiveDispel}");
        getNextThing(scanner, impactTypeId, "11.cellImpact(0-4){none,poison,fire,holy}");
        getNextThing(scanner, impactTypeId, "12.isOnCell(0,1)");

        StringBuilder impactIdComp = new StringBuilder("000000000");
        StringBuilder wayOfAssigning = new StringBuilder("0000");
        System.out.println("does It have Comp?(y/n)");
        String c = scanner.next();
        if (c.equals("y")) {
            impactIdComp = new StringBuilder();
            wayOfAssigning = new StringBuilder();
            // 0.antiHolyBuff
            // 1.holyBuffCanceler
            // 2.antiNegativeImpactOnDefend(0,1)
            // 3.antiPoisonOnDefend(0,1)
            // 4.kill(0,1)
            // 5.risingDamage(0-2){none,constRise,difRise}
            // 6.immuneToMinDamage(0,1)
            // 7.antiDisarmOnDefend(0,1)
            // 8.previousAttackMatters(0,1)
            scanner.nextLine();
            getNextThing(scanner, impactIdComp, "0.antiHolyBuff");
            getNextThing(scanner, impactIdComp, "1.holyBuffCanceler");
            getNextThing(scanner, impactIdComp, "2.antiNegativeImpactOnDefend(0,1)");
            getNextThing(scanner, impactIdComp, "3.antiPoisonOnDefend(0,1)");
            getNextThing(scanner, impactIdComp, "4.kill(0,1)");
            getNextThing(scanner, impactIdComp, "5.risingDamage(0-2){none,constRise,difRise}");
            getNextThing(scanner, impactIdComp, "6.immuneToMinDamage(0,1)");
            getNextThing(scanner, impactIdComp, "7.antiDisarmOnDefend(0,1)");
            getNextThing(scanner, impactIdComp, "8.previousAttackMatters(0,1)");
            // 0.whereToPutIt(0-4){no where,defend,attack,dyingWish,summonImpact}
            // 1.impactGiverTeam(0-3)
            // 2.impactGetterTeam(0-3)
            scanner.nextLine();
            getNextThing(scanner, wayOfAssigning, "0.whereToPutIt(0-4){no where,defend,attack,dyingWish,summonImpact}");
            getNextThing(scanner, wayOfAssigning, "1.impactGiverTeam(0-3)");
            getNextThing(scanner, wayOfAssigning, "2.impactGetterTeam(0-3)");
        }
        return new Impact(impactIdComp.toString(), wayOfAssigning.toString(), targetTypeId.toString(), impactTypeId.toString());
    }

    private static void getNextThing(Scanner scanner, StringBuilder targetTypeId, String message) {
        System.out.println(message);
        targetTypeId.append(scanner.nextLine());
    }

    //
    private static String fileNameCreator(String name) {
        String fileName = "";
        String[] nameCompleted = name.split("[ ]");
        for (String s : nameCompleted) {
            fileName += s.toLowerCase();
        }
        return fileName;
    }
}


