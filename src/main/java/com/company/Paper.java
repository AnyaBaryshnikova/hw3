package com.company;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Paper implements Comparable<Paper>{
    private String name;
    private ArrayList<String> currency = new ArrayList<>();
    private String code;
    private LocalDate date;

    public Paper(String name, ArrayList<String> currency, String code, LocalDate date) {
        this.name = name;
        this.currency = currency;
        this.code = code;
        this.date = date;
    }

    public Paper(String name, String code, LocalDate date) {
        this.name = name;
        this.code = code;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCurrency() {
        return currency;
    }

    public void setCurrency(ArrayList<String> currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void addCurrency(String currency){
        this.currency.add(currency);
    }


    @Override
    public int compareTo(Paper o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        String str = "";
        str += ("\nНазвание бумаги: " + name + " код: " + code + " дата: " + date.toString());
        if(currency.size() == 0){
            str += ".";
            return str;
        }
        str += " валюты: ";
        for (String s : currency) str += s + " ";

        str += ".";

        return str;
    }

    public boolean containsCurrency(String str){
        return currency.contains(str);
    }
}
