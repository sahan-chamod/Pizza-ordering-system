package com.esoft.controllers;

import com.esoft.services.AdminService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminController {
    private AdminService adminService = new AdminService();

    public void displayMenu() {
        while (true) {
            System.out.println("\n--- Admin Panel ---");
            System.out.println("1. Show Orders");
            System.out.println("2. Add Pizza");
            System.out.println("3. Add Toppings");
            System.out.println("4. Update Order State");
            System.out.println("5. Exit");
            int choice = getValidIntegerInput();

            switch (choice) {
                case 1:
                    adminService.showOrders();
                    break;
                case 2:
                    adminService.addPizza();
                    break;
                case 3:
                    adminService.addToppings();
                    break;
                case 4:
                    adminService.updateOrderState();
                    break;
                case 5:
                    System.out.println("Exiting Admin Panel...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int getValidIntegerInput() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            return getValidIntegerInput();
        }
    }
}
