package com.company;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Organization {
    private String id;
    private String name;
    private String adress;
    private String  phone;
    private String  inn;
    private LocalDate date;
    private ArrayList<Paper> papers = new ArrayList<>();


    public Organization(String id, String name, String adress, String phone, String inn, LocalDate date, ArrayList<Paper> papers) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.inn = inn;
        this.date = date;
        this.papers = papers;
    }

    public Organization(String id, String name, String adress, String phone, String inn, LocalDate date) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.inn = inn;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<Paper> getPapers() {
        return papers;
    }

    public void setPapers(ArrayList<Paper> papers) {
        this.papers = papers;
    }

    public void addPaper(Paper paper) {
        papers.add(paper);
    }

    @Override
    public String toString() {
        String str = "";
        str += ("Название: " + name + " адрес: " + adress + " телефон: " + phone + " ИНН: " + inn + " дата основания: " + date.toString());
        if (papers.size() == 0) {
            str += ".";
            return str;
        }
        str += " ценные бумаги: ";
        for (Paper paper : papers) str += paper.toString() + " ";

        str += ".";

        return str;
    }


    public boolean containcCurr(String str){
        for (Paper paper : papers) {
            if (paper.containsCurrency(str))
                return true;
        }
        return false;
    }

    // Строка кодов ценных бумаг, у которых есть переданная валюта
    public String currencies(String str){
        final String[] st = {""};
        // список бумаг которые содержат валюту
        List<Paper> curpapers = papers.stream().filter((s) -> s.containsCurrency(str)).collect(Collectors.toList());
        curpapers.stream().forEach((s) -> st[0] += s.getCode() + "\n");


        return st[0];
    }

    public String dateCurrencies(){
        LocalDate today = LocalDate.now();
        final String[] st = {""};
        List<Paper> datePapers = papers.stream().filter(k -> k.getDate().compareTo(today) < 0).collect(Collectors.toList());
        datePapers.stream().forEach((s) -> st[0] += s.toString() + "\n");


        return st[0];
    }
}



