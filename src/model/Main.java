package model;

import com.google.gson.*;

import java.io.FileOutputStream;
import java.io.*;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

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
            Impact primaryImpact = new Impact();
            Impact secondaryImpact = new Impact();
            //primary Impact setting
            //target setting



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
            minion.setOnAttackImpact(primaryImpact);
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


