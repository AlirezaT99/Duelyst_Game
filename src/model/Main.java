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
        String fileName = "src/model/spells/totaldisarm/primaryImpact.json";
        String fileName1 = "src/model/spells/totaldisarm/spell.json";
        /// mesal baraye tabdil be file e JSON
        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            Impact impact = new Impact();
            impact.setName("TotalDisarm");
            //impact.setImpactTypeId();
            gson.toJson(impact, isr);
 }
        ///entehaye mesal.
        //baraye neveshtane spell.
//        try(FileOutputStream fos = new FileOutputStream(fileName1);
//            OutputStreamWriter isr = new OutputStreamWriter(fos,
//                    StandardCharsets.UTF_8)){
//            Spell spell = new Spell();
//            spell.setName("Total Disarm");
//            spell.setCost(1000);
//            spell.setManaCost(0);
//            spell.setAreaTargetSquare(1);
//            gson.toJson(spell,isr);
//        }
        /////etmame neveshtane spell
        //vali say konid mostaghim az khode file edit konid baghie ro inja vaghtetouno migire
        ///// khoundane Impact va spell
//        GsonBuilder builder = new GsonBuilder();
//        builder.serializeNulls();
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        Path path = new File(fileName).toPath();
//        Reader reader = Files.newBufferedReader(path,
//                StandardCharsets.UTF_8);
//        Impact impact = gson.fromJson(reader, Impact.class);
//        path = new File(fileName1).toPath();
//        reader = Files.newBufferedReader(path,
//                StandardCharsets.UTF_8);
//        Spell spell = gson.fromJson(reader,Spell.class);
    }
}

