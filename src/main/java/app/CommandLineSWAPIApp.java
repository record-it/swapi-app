package app;

import service.SWAPIPeopleService;
import service.SWPeopleService;

import java.util.Scanner;

public class CommandLineSWAPIApp {
    public static final Scanner scanner = new Scanner(System.in);
    public static final SWPeopleService peopleService = new SWAPIPeopleService();

    public static void main(String[] args) {
        System.out.println("Podaj id osoby:");
        int id = scanner.nextInt();
        peopleService.findById(id);
        scanner.next();
    }
}
