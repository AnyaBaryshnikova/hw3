package com.company;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {


        JSONParser parser = new JSONParser();
        ArrayList<Organization> companies = new ArrayList<>();

        try (Reader reader = new FileReader("file.json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);


            JSONArray comps = (JSONArray) jsonObject.get("companies");
            String id;
            String name;
            String address;
            String phone;
            String inn;
            LocalDate foundation = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");


            for(int i = 0; i < comps.size(); ++i) {
                JSONObject ob = (JSONObject) comps.get(i);
                id = String.valueOf(ob.get("id"));
                name = (String) ob.get("name");
                address = (String) ob.get("address");
                phone = String.valueOf(ob.get("phoneNumber"));
                inn = String.valueOf(ob.get("INN"));
                String foundDate = (String) ob.get("founded");

                try{
                    foundation = LocalDate.parse(foundDate, formatter);
                }
                catch (Exception e){}

                companies.add(new Organization(id, name, address, phone, inn, foundation));
                Paper paper;

                JSONArray securities = (JSONArray)ob.get("securities");
                for(int k = 0; k < securities.size(); ++k){
                    JSONObject sec = (JSONObject) securities.get(i);
                    String papername = String.valueOf(sec.get("name"));
                    String papercode = String.valueOf(sec.get("code"));
                    LocalDate paperdate = LocalDate.now();
                    String pDate = (String) sec.get("date");

                    try{
                        paperdate = LocalDate.parse(pDate, formatter);
                    }
                    catch (Exception e){}
                    paper = new Paper(papername, papercode, paperdate);

                    JSONArray currs = (JSONArray)sec.get("currency");
                    Iterator<String> iterator = currs.iterator();
                    while (iterator.hasNext()) {
                        paper.addCurrency(iterator.next());
                    }
                    companies.get(i).addPaper(paper);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1
        long sum = companies.stream().count();
        System.out.println("Общее количество компаний: " + sum);
        companies.stream().map((s) -> "Название: " + s.getName() + " дата основания: " + s.getDate())
                .forEach(System.out::println);

        // 2
        System.out.println("\nПросроченные ценные бумаги: ");

        Map<String, String> datePapers = companies.stream()
                .collect(Collectors.toMap(p->p.getName(), p -> p.dateCurrencies()));
        datePapers.entrySet().stream().filter((s) -> s.getValue().compareTo("") > 0)
                .forEach((s) -> System.out.println("Компания " + s.getKey() + "\n" + s.getValue()));



        //3
        String strDate;
        Scanner sc = new Scanner (System.in);
        while (true){
            String regex = "^(0[1-9]|[12][0-9]|3[01])[ /.](0[1-9]|1[012])[- /.](19|20){0,1}\\d\\d$";
            System.out.println("Введите дату в формате ДД.ММ.ГГ или ДД.ММ.ГГГ или ДД/ММ/ГГ или ДД/ММ/ГГГГ: ");
            strDate = sc.nextLine();
            if(strDate.matches(regex))
                break;
            else
                System.out.println("Неверный формат даты или такой даты не существует:(");
        }

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yy");
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("dd.MM.yy");

        LocalDate tempdate = LocalDate.now();
        try{
            tempdate = LocalDate.parse(strDate, formatter1);
        }
        catch (Exception e){}
        try{
            tempdate = LocalDate.parse(strDate, formatter2);
        }
        catch (Exception e){}
        try{
            tempdate = LocalDate.parse(strDate, formatter3);
        }
        catch (Exception e){}
        try{
            tempdate = LocalDate.parse(strDate, formatter4);
        }
        catch (Exception e){}

        LocalDate finalDate = tempdate;
        List<Organization> orgs = companies.stream()
                .filter((s) -> s.getDate().compareTo(finalDate) >= 0)
                .collect(Collectors.toList());

        System.out.println("\nКомпаниии созданные после " + finalDate);
        orgs.stream().map((s) -> "Название: " + s.getName() + " дата основания: " + s.getDate())
                .forEach(System.out::println);


        //4
        String str;
        System.out.println("Введите код валюты: ");
        str = sc.nextLine();

        Map<String, String> compsMap = companies.stream().filter((s) -> s.containcCurr(str))
                .collect(Collectors.toMap(p->p.getName(), p -> p.currencies(str)));
        if(compsMap.size() == 0)
            System.out.println("Ценных бумаг с такой валютой не существует");
        else{
            System.out.println("\nБумаги, которые используют валюту\n" + str);
            compsMap.forEach((k,v)->System.out.println("Название компании: " + k + " коды ценных бумаг, использующих данную валюту: " + v));
        }

    }
}
