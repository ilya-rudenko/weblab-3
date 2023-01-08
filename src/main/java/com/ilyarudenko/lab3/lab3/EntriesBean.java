package com.ilyarudenko.lab3.lab3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Model
@SessionScoped
public class EntriesBean implements Serializable {

    Entry entry;
    private List<Entry> entries;

    private int page;

    private DBManager manager;

    public EntriesBean(){
        entry=new Entry();

        manager=new DBManager();

        page=1;

        entries=manager.getPage(page);

        if(entries==null) {
            entries=new ArrayList<>();
        }
    }

    public Entry getEntry() {
        return entry;
    }

    public void plusPage () {
        System.out.println("forward");
        page+=1;
        entries=manager.getPage(page);

        if(entries==null) {
            entries=new ArrayList<>();
        }
        if (entries.isEmpty()){
            if(page>0) page-=1;
            else page=0;
        }
        entries=manager.getPage(page);
    };

    public void minusPage () {
        System.out.println("back");
        page-=1;
        if(page<=1) page=1;
        entries=manager.getPage(page);
    };

    public void addEntry() {
        long startTime = System.nanoTime();

        String time = LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")) + " UTC";

        System.out.println("Got entry: ");
        System.out.print("X: ");
        System.out.println(entry.getxValue());
        System.out.print("Y: ");
        System.out.println(entry.getyValue());
        System.out.print("R: ");
        System.out.println(entry.getrValue());

        entry.checkHit();
        entry.setDateTime(time);
        entry.setScriptTime(System.nanoTime() - startTime);
        entries.add(0,entry);

        manager.addEntry(entry);

        entries=manager.getPage(page);

        entry = new Entry();
    }

    public void clearEntries() {
        manager.clear();

        entries.clear();

        page=1;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public int getPage(){
        return page;
    }

}
