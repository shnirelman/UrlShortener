package org.example;

import org.example.controller.UrlController;
import org.example.controller.dto.UrlDto;
import org.example.jdbc.JdbcUtils;
import org.example.repository.UrlRepositoryImpl;
import org.example.repository.dao.UrlDao;
import org.example.service.UrlServiceImpl;
import org.example.utils.ReadUtils;
import org.example.exception.EntityNotFoundException;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            boolean connected = JdbcUtils.createConnection();
            if (!connected) {
                System.out.println("Error with connection to database");
                return;
            }

            runCli();
        }
        finally {
            JdbcUtils.closeConnection();
        }
    }

    private static void runCli() {
        UrlController urlController = new UrlController(new UrlServiceImpl(new UrlRepositoryImpl()));

        while(true) {
            printMenu();

            String chosenService = ReadUtils.readLine();

            if (chosenService.equals("1")) {
                System.out.println("Введите длинную ссылку");
                String longForm;
                do {
                    longForm = readAndValidateLongForm();
                } while(longForm == null);
                String shortForm = urlController.addUrl(new UrlDto(longForm));
                System.out.printf("Короткая ссылка: %s\n", shortForm);
            } else if (chosenService.equals("2")) {
                System.out.println("Введите короткую ссылку:");
                String shortForm = ReadUtils.readLine();
                try {
                    UrlDto urlDto = urlController.getUrlByShortForm(shortForm);
                    System.out.printf("Найдена ссылка %s\n", urlDto.longForm());
                }
                catch(EntityNotFoundException ex) {
                    System.out.printf("Короткая ссылка %s ничему не соответствует\n", shortForm);
                }

            } else if (chosenService.equals("3")) {
                return;
            } else {
                System.out.println("Выберите валидный вариант");
            }
        }
    }

    private static String readAndValidateLongForm() {
        String longForm = ReadUtils.readLine();
        if (longForm == null || longForm.isEmpty()) {
            System.out.println("Неверный ввод");
            return null;
        }
        else
            return longForm;
    }

    private static void printMenu() {
        System.out.println("""
                Приложение для укорачивания ссылок
                                
                Выберите действие:
                                
                1. По длинной ссылке найти короткую
                2. По короткой ссылке найти длинную
                3. Выйти
                                
                """);
    }
}