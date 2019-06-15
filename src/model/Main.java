package model;

import com.google.gson.*;
import javafx.scene.Scene;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.FileOutputStream;
import java.io.*;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
//        cardCreator();
//        // address haye local
////        String fileName = "src/model/spells/kingsguard.json";
//        //  String fileName1 = "src/model/spells//spell.json";
//////        String fileName2 = "src/model/spells/allpower/secondaryImpact.json";
////        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
////        /// mesal baraye tabdil be file e JSON
////        try (FileOutputStream fos = new FileOutputStream(fileName);
////             OutputStreamWriter isr = new OutputStreamWriter(fos,
////                     StandardCharsets.UTF_8)) {
////            Impact impact = new Impact();
////            impact.setName("All Power");
////            impact.setTargetTypeId("001020000");
////            impact.setImpactTypeId("112320200");
////            gson.toJson(impact, isr);
//// }
////        try (FileOutputStream fos = new FileOutputStream(fileName2);
////             OutputStreamWriter isr = new OutputStreamWriter(fos,
////                     StandardCharsets.UTF_8)) {
////            Impact impact = new Impact();
////            impact.setName("Madness");
////            impact.setTargetTypeId("010020100");
////            impact.setImpactTypeId("111220003");
////            gson.toJson(impact, isr);
////        }
//        ///entehaye mesal.
////        baraye neveshtane spell.
////        try (FileOutputStream fos = new FileOutputStream(fileName);
////             OutputStreamWriter isr = new OutputStreamWriter(fos,
////                     StandardCharsets.UTF_8)) {
////            Spell spell = new Spell();
////            spell.setName("Kings Guard");
////            Impact impact = new Impact();
////            impact.setName("Kings Guard");
////            impact.setTargetTypeId("010110001");
////            impact.setImpactTypeId("");
////            spell.setPrimaryImpact(impact);
//////            Impact secondaryImpact = new Impact();
//////            secondaryImpact.setName("Sacrifice");
//////            secondaryImpact.setTargetTypeId("010010100");
//////            secondaryImpact.setImpactTypeId("112380001");
//////            spell.setSecondaryImpact(secondaryImpact);
////            spell.setCost(1600);
////            spell.setManaCost(2);
////            spell.setDescription("increases AP by 8 units, but reduces HP by 6.");
////            gson.toJson(spell, isr);
////        }
//
//
//        /////etmame neveshtane spell
//        //vali say konid mostaghim az khode file edit konid baghie ro inja vaghtetouno migire
//        ///// khoundane Impact va spell
////        GsonBuilder builder = new GsonBuilder();
////        builder.serializeNulls();
////        Path path = new File(fileName).toPath();
////        Reader reader = Files.newBufferedReader(path,
////                StandardCharsets.UTF_8);
////        Impact impact = gson.fromJson(reader, Impact.class);
////        path = new File(fileName1).toPath();
////        reader = Files.newBufferedReader(path,
////                StandardCharsets.UTF_8);
////        Spell spell = gson.fromJson(reader,Spell.class);
////        spell.setPrimaryImpact(impact);
////        System.out.println(spell.getDescription());
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

        private static Card cardCreator(Scanner scanner){
            System.out.println("Enter Name");
            String name = scanner.nextLine();
            System.out.println("Enter Cost");
            int cost = scanner.nextInt();
            System.out.println("Enter mana cost");
            int manaCost = scanner.nextInt();
            System.out.println("Enter card type(spell(s)/minion(m)/hero(h)");
            char c = (char)scanner.nextByte();
            if(c == 's'){
                Spell spell = spellCreator(scanner);
                spell.name = name;
                spell.cost = cost;
                spell.manaCost = manaCost;
                return spell;
            }

            return null;
        }

        private static Spell spellCreator(Scanner scanner){
            System.out.println("Create Primary Impact");
            Impact primaryImpact = impactCreator(scanner);
            Impact secondaryImpact = null;
            System.out.println("does it have secondary Impact?(y/n)");
            char c = (char)scanner.nextByte();
            if(c == 'y'){
                System.out.println("Create secondary Impact");
                secondaryImpact = impactCreator(scanner);
            }
            return new Spell(primaryImpact,secondaryImpact);

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
        getNextThing(scanner,targetTypeId,"0.(0,1)\"SelectedCellImportance\"");
        getNextThing(scanner, targetTypeId,"1.(0,1)\"ValidOnAll\"");
        getNextThing(scanner, targetTypeId,"2.(0,1)\"ValidOnAWholeTeam\"");
        getNextThing(scanner, targetTypeId,"3.(0-2)\"onWhichTeam\"{friendly, hostile, both}");
        getNextThing(scanner, targetTypeId,"4.(0-2)\"targetSoldierType\"{hero,minion,both}");
        getNextThing(scanner, targetTypeId,"5.(2,3)\"SquareLength\"");
        getNextThing(scanner, targetTypeId,"6.column(1,0)");
        getNextThing(scanner, targetTypeId,"7.row(0,1)");
        getNextThing(scanner,targetTypeId,"8.adjacentToObjectedCell(0-3){none, one , all,all+self}");
        getNextThing(scanner,targetTypeId,"9.random(0,1)");
        getNextThing(scanner,targetTypeId,"10.soldierAttackType(0-3){doesn't matter,melee, ranged, hybrid,ranged & hybrid}");
        getNextThing(scanner,targetTypeId,"11.closestSoldiers(0,1)");
        getNextThing(scanner,targetTypeId,"12.ranged(0-n)");

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
        getNextThing(scanner,impactTypeId,"0.(0,1)isPositive");
        getNextThing(scanner,impactTypeId,"1.(0-6)buffType{holy,power,poison,weakness,stun,disarm}");
        getNextThing(scanner,impactTypeId,"2.(0-3)QuantityChange{mana,health,damage}");
        getNextThing(scanner,impactTypeId,"3.(0,1)quantityChangeSign{negative/isPositiveImpact}");
        getNextThing(scanner,impactTypeId,"4,5.(0,n)\"impactQuantity\"");
        getNextThing(scanner,impactTypeId,"6.(0-3)PassivePermanent{none , passive , permanent , continuous}");
        getNextThing(scanner,impactTypeId,"7.(0,n)turnsToBeActivated");
        getNextThing(scanner,impactTypeId,"8,9.(0,n)turnsActive");
        getNextThing(scanner,impactTypeId,"10.dispel(0-2){none,buffDispel,allPositiveDispel}");
        getNextThing(scanner,impactTypeId,"11.cellImpact(0-4){none,poison,fire,holy}");
        getNextThing(scanner,impactTypeId,"12.isOnCell(0,1)");
        getNextThing(scanner,impactTypeId,"0.(0,1)isPositive");
        getNextThing(scanner,impactTypeId,"0.(0,1)isPositive");
        getNextThing(scanner,impactTypeId,"0.(0,1)isPositive");

        StringBuilder impactIdComp = new StringBuilder("000000000");
        StringBuilder wayOfAssigning = new StringBuilder("0000");
        System.out.println("does It have Comp?(y/n)");
        char c= (char)scanner.nextByte();
        if(c == 'y'){
            // 0.antiHolyBuff
            // 1.holyBuffCanceler
            // 2.antiNegativeImpactOnDefend(0,1)
            // 3.antiPoisonOnDefend(0,1)
            // 4.kill(0,1)
            // 5.risingDamage(0-2){none,constRise,difRise}
            // 6.immuneToMinDamage(0,1)
            // 7.antiDisarmOnDefend(0,1)
            // 8.previousAttackMatters(0,1)
            getNextThing(scanner,impactIdComp,"0.antiHolyBuff");
            getNextThing(scanner,impactIdComp,"1.holyBuffCanceler");
            getNextThing(scanner,impactIdComp,"2.antiNegativeImpactOnDefend(0,1)");
            getNextThing(scanner,impactIdComp,"3.antiPoisonOnDefend(0,1)");
            getNextThing(scanner,impactIdComp,"4.kill(0,1)");
            getNextThing(scanner,impactIdComp,"5.risingDamage(0-2){none,constRise,difRise}");
            getNextThing(scanner,impactIdComp,"6.immuneToMinDamage(0,1)");
            getNextThing(scanner,impactIdComp,"7.antiDisarmOnDefend(0,1)");
            getNextThing(scanner,impactIdComp,"8.previousAttackMatters(0,1)");
            // 0.whereToPutIt(0-4){no where,defend,attack,dyingWish,summonImpact}
            // 1.impactGiverTeam(0-3)
            // 2.impactGetterTeam(0-3)
            getNextThing(scanner,wayOfAssigning,"0.whereToPutIt(0-4){no where,defend,attack,dyingWish,summonImpact}");
            getNextThing(scanner,wayOfAssigning,"1.impactGiverTeam(0-3)");
            getNextThing(scanner,wayOfAssigning,"2.impactGetterTeam(0-3)");
        }
          return new Impact(impactIdComp.toString(),wayOfAssigning.toString(),targetTypeId.toString(),impactTypeId.toString());
    }

    private static void getNextThing(Scanner scanner, StringBuilder targetTypeId,String message) {
        System.out.println(message);
        targetTypeId.append(scanner.nextLine());
    }

    //
        private static String fileNameCreator (String name){
            String fileName = "";
            String[] nameCompleted = name.split("[ ]");
            for (String s : nameCompleted) {
                fileName += s.toLowerCase();
            }
            return fileName;
        }
    }


