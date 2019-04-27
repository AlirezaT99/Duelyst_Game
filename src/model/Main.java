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
        // address haye local
        String fileName = "src/model/spells/kingsguard.json";
        //  String fileName1 = "src/model/spells//spell.json";
//        String fileName2 = "src/model/spells/allpower/secondaryImpact.json";
        Gson gson = new GsonBuilder().serializeNulls().create();
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
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
            Spell spell = new Spell();
            spell.setName("Kings Guard");
            Impact impact = new Impact();
            impact.setName("Kings Guard");
            impact.setTargetTypeId("010110001");
            impact.setImpactTypeId("");
            spell.setPrimaryImpact(impact);
//            Impact secondaryImpact = new Impact();
//            secondaryImpact.setName("Sacrifice");
//            secondaryImpact.setTargetTypeId("010010100");
//            secondaryImpact.setImpactTypeId("112380001");
//            spell.setSecondaryImpact(secondaryImpact);
            spell.setCost(1600);
            spell.setManaCost(2);
            spell.setDescription("increases AP by 8 units, but reduces HP by 6.");
            gson.toJson(spell, isr);
        }
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
}

