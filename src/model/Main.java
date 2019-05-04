package model;

import com.google.gson.*;

import java.io.FileOutputStream;
import java.io.*;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        cardCreator();
        // address haye local
//        String fileName = "src/model/spells/kingsguard.json";
        //  String fileName1 = "src/model/spells//spell.json";
////        String fileName2 = "src/model/spells/allpower/secondaryImpact.json";
//        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
//        /// mesal baraye tabdil be file e JSON
//        try (FileOutputStream fos = new FileOutputStream(fileName);
//             OutputStreamWriter isr = new OutputStreamWriter(fos,
//                     StandardCharsets.UTF_8)) {
//            Impact impact = new Impact();
//            impact.setName("All Power");
//            impact.setTargetTypeId("001020000");
//            impact.setImpactTypeId("112320200");
//            gson.toJson(impact, isr);
// }
//        try (FileOutputStream fos = new FileOutputStream(fileName2);
//             OutputStreamWriter isr = new OutputStreamWriter(fos,
//                     StandardCharsets.UTF_8)) {
//            Impact impact = new Impact();
//            impact.setName("Madness");
//            impact.setTargetTypeId("010020100");
//            impact.setImpactTypeId("111220003");
//            gson.toJson(impact, isr);
//        }
        ///entehaye mesal.
//        baraye neveshtane spell.
//        try (FileOutputStream fos = new FileOutputStream(fileName);
//             OutputStreamWriter isr = new OutputStreamWriter(fos,
//                     StandardCharsets.UTF_8)) {
//            Spell spell = new Spell();
//            spell.setName("Kings Guard");
//            Impact impact = new Impact();
//            impact.setName("Kings Guard");
//            impact.setTargetTypeId("010110001");
//            impact.setImpactTypeId("");
//            spell.setPrimaryImpact(impact);
////            Impact secondaryImpact = new Impact();
////            secondaryImpact.setName("Sacrifice");
////            secondaryImpact.setTargetTypeId("010010100");
////            secondaryImpact.setImpactTypeId("112380001");
////            spell.setSecondaryImpact(secondaryImpact);
//            spell.setCost(1600);
//            spell.setManaCost(2);
//            spell.setDescription("increases AP by 8 units, but reduces HP by 6.");
//            gson.toJson(spell, isr);
//        }


        /////etmame neveshtane spell
        //vali say konid mostaghim az khode file edit konid baghie ro inja vaghtetouno migire
        ///// khoundane Impact va spell
//        GsonBuilder builder = new GsonBuilder();
//        builder.serializeNulls();
//        Path path = new File(fileName).toPath();
//        Reader reader = Files.newBufferedReader(path,
//                StandardCharsets.UTF_8);
//        Impact impact = gson.fromJson(reader, Impact.class);
//        path = new File(fileName1).toPath();
//        reader = Files.newBufferedReader(path,
//                StandardCharsets.UTF_8);
//        Spell spell = gson.fromJson(reader,Spell.class);
//        spell.setPrimaryImpact(impact);
//        System.out.println(spell.getDescription());
    }

    public static void cardCreator() throws FileNotFoundException, IOException {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Impact primaryImpact = new Impact();
        Impact secondaryImpact = new Impact();
        //primary Impact setting
        //target setting

        //0.(0,1)"ValidOnAll"
        primaryImpact.addToTargetTypeID("0");
        secondaryImpact.addToTargetTypeID("0");
        //1.(0,1)"SelectedCellImportance"
        primaryImpact.addToTargetTypeID("1");
        secondaryImpact.addToTargetTypeID("0");
        //2.(0,1)"ValidOnAWholeTeam"
        primaryImpact.addToTargetTypeID("0");
        secondaryImpact.addToTargetTypeID("0");
        //3.(0-2)"onWhichTeam"{friendly, hostile, both}
        primaryImpact.addToTargetTypeID("1");
        secondaryImpact.addToTargetTypeID("0");
        //4.(0-2)"targetSoldierType"{hero,minion,both}
        primaryImpact.addToTargetTypeID("2");
        secondaryImpact.addToTargetTypeID("0");

        //5.(0-n)"targetFactionType"
        primaryImpact.addToTargetTypeID("0");
        secondaryImpact.addToTargetTypeID("0");

        //6.(2,3)"SquareLength"
        primaryImpact.addToTargetTypeID("0");
        secondaryImpact.addToTargetTypeID("0");

        //7.column(1,0)
        primaryImpact.addToTargetTypeID("0");
        secondaryImpact.addToTargetTypeID("0");

        //8.nearHeroHostileMinion(0-2){none, one , all}
        primaryImpact.addToTargetTypeID("0");
        secondaryImpact.addToTargetTypeID("0");

        //9. random(0,1)
        primaryImpact.addToTargetTypeID("0");
        secondaryImpact.addToTargetTypeID("0");

        //10.previousAttacksMatter(0,1)
        primaryImpact.addToTargetTypeID("1");
        secondaryImpact.addToTargetTypeID("0");

        //11.soldierAttackType(0-3){doesn't matter,melee, ranged or hybrid}
        primaryImpact.addToTargetTypeID("0");
        secondaryImpact.addToTargetTypeID("0");

        //12.row(0,1)
        primaryImpact.addToTargetTypeID("0");
        secondaryImpact.addToTargetTypeID("0");

        //13.closestSoldiers(0,1)
        primaryImpact.addToTargetTypeID("0");
        secondaryImpact.addToTargetTypeID("0");

       // secondaryImpact.setTargetTypeId(primaryImpact.getTargetTypeId());

        //end of target setting

        //Impact setting
        //0.(0,1)isPositive|1.(0-6)buffType{holy,power,poison,weakness,stun,disarm}|
        // 2.(0-4)QuantityChange{mana,health,damage}|3.(0,1)quantityChangeSign{negative/positive}|4,5.(0,n)"impactQuantity"|
        // 6.(0,2)PassivePermanent{none , passive , permanent , continuous, momentary }|7.(0,n)turnsToBeActivated |8,9.(0,n)turnsActive|
        // 10.(0,1)fromWhichTeamAssigned{player1 , player2} |11.isAssignedOnWhichTeam(0,1){player1,player2}
        // 12.deactivatedForThisTurn(0,1) |13.theWayItIsGonnaBeAssigned(0-3){spellWay,attack,defend,don't care}
        // 14.dispel(0-2){none,buffDispel,allPositiveDispel}|15.setsOnCells(0,1) | 16.appliedToOnWhichState(state is for card that have impact)(0-3){none,defend,attack}
        // |17.cellImpact(0-4){none,poison,fire,holy}

        //0.(0,1)isPositive
        primaryImpact.addToImpactTypeID("0");
        secondaryImpact.addToImpactTypeID("0");

        //1.(0-6)buffType{none,holy,power,poison,weakness,stun,disarm}
        primaryImpact.addToImpactTypeID("0");
        secondaryImpact.addToImpactTypeID("0");

        //2.(0-3)QuantityChange{none,mana,health,damage}
        primaryImpact.addToImpactTypeID("0");
        secondaryImpact.addToImpactTypeID("0");

        //3.(0,1)quantityChangeSign{negative/positive}
        primaryImpact.addToImpactTypeID("1");
        secondaryImpact.addToImpactTypeID("0");

        //4,5.(0,n)"impactQuantity"
        primaryImpact.addToImpactTypeID("0");
        primaryImpact.addToImpactTypeID("5");
        secondaryImpact.addToImpactTypeID("0");
        secondaryImpact.addToImpactTypeID("0");

        //6.(0,3)PassivePermanent{none , passive , permanent , continuous}
        primaryImpact.addToImpactTypeID("0");
        secondaryImpact.addToImpactTypeID("0");

        //7.(0,n)turnsToBeActivated
        primaryImpact.addToImpactTypeID("0");
        secondaryImpact.addToImpactTypeID("0");

        //8,9.(0,n)turnsActive
        primaryImpact.addToImpactTypeID("0");
        primaryImpact.addToImpactTypeID("0");
        secondaryImpact.addToImpactTypeID("0");
        secondaryImpact.addToImpactTypeID("0");
        //10.dispel(0-2){none,buffDispel,allPositiveDispel,allDispel}
        primaryImpact.addToImpactTypeID("0");
        secondaryImpact.addToImpactTypeID("0");

        //11.setsOnCells(0,1)
        primaryImpact.addToImpactTypeID("0");
        secondaryImpact.addToImpactTypeID("0");
        //12.cellImpact(0-3){none,poison,fire,holy}
        primaryImpact.addToImpactTypeID("0");
        secondaryImpact.addToImpactTypeID("0");

        //end of impact setting

        //impactTypeIdComp setting
        //0.antiHolyBuff |1.antiNegativeImpactOnDefend(0,1) |
        // 2.antiPoisonOnDefend(0,1)|3.kill(0,1)|4.risingDamage(0-2){none,firstOneInDoc,secondOneInDoc}|5.immuneToMinDamage(0,1)

        //0.antiHolyBuff
        primaryImpact.addToImpactTypeIdComp("0");
        secondaryImpact.addToImpactTypeIdComp("0");

        //1.antiNegativeImpactOnDefend(0,1)
        primaryImpact.addToImpactTypeIdComp("0");
        secondaryImpact.addToImpactTypeIdComp("0");

        //2.antiPoisonOnDefend(0,1)
        primaryImpact.addToImpactTypeIdComp("0");
        secondaryImpact.addToImpactTypeIdComp("0");

        //3.kill(0,1)
        primaryImpact.addToImpactTypeIdComp("0");
        secondaryImpact.addToImpactTypeIdComp("0");

        //4.risingDamage(0-2){none,firstOneInDoc,secondOneInDoc}
        primaryImpact.addToImpactTypeIdComp("0");
        secondaryImpact.addToImpactTypeIdComp("0");

        //5.immuneToMinDamage(0,1)
        primaryImpact.addToImpactTypeIdComp("0");
        secondaryImpact.addToImpactTypeIdComp("0");
        //end of ImpactTypeIDComp setting

        //6.antiDisarmOnDefend(0,1)
        primaryImpact.addToImpactTypeIdComp("0");
        secondaryImpact.addToImpactTypeIdComp("0");

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
        //creating minions
        Minion minion = new Minion();
        minion.setName("the Persian Pahlevaan"); //
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
        minion.setOnAttackImpact(primaryImpact);
        String fileName = "src/model/minions/" + fileNameCreator(minion.getName()) + ".json";
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
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
        //creating UsableItems
        // UsableItem usableItem = new UsableItem();
//        usableItem.setName("Damoul's Bow");
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

