package org.example;


import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {



        logger.info("Начало работы парсера");
        LocalDate datenow = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = datenow.format(formatter);
        String fileName = "ParserSaveFile_" + dateString + ".csv";

        try{
          var document =  Jsoup.connect("https://www.wienerborse.at/en/bonds/").get();
          var titleElem = document.select("tr[data-key]");
            System.out.println("Name\tLast\tChg. % 1D\tChg. Abs.\tDate\tTime\tISIN\tBid\tBid Volume\tAsk\tAsk Volume\tMaturity\tStatus");


            for (Element element : titleElem) {
                StringBuilder line = new StringBuilder();

                Elements tds = element.select("td");
                for (int i = 0; i <tds.size() ; i++) {
                    line.append(tds.get(i).text());
                    if(i <tds.size()-1)
                    {
                        line.append("\t");
                    }
                }
                System.out.println(line.toString());

            }
            logger.info("Успешное получение информации");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                StringBuilder line = new StringBuilder();

                writer.write(line.toString());
                writer.newLine();
                System.out.println("Файл сохранен!");
            } catch (IOException e) {
                logger.error("Ошибка сохранения");
                e.printStackTrace();
            }

            //System.out.println(titleElem.text());
        }catch (Exception exception)
        {
            logger.fatal("Ошибка  парсера");
            exception.printStackTrace();
        }

    }
}