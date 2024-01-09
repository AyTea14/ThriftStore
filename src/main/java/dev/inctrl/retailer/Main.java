package dev.inctrl.retailer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    private static final List<String> reciepts = new ArrayList<>();
    private static final String recieptsFile = "reciepts.txt";

    private static final String[][] itemData = {
            { "A", "70.0" },
            { "B", "55.0" },
            { "C", "35.0" },
            { "D", "20.0" }
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Thrifty Thirty Store.");
        System.out.println("Now enter all the required informations.");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Are you a member? [Yes/No]: ");
        boolean isMember = sc.nextLine().equalsIgnoreCase("yes");
        System.out.print("How many items you have?: ");
        int amount = sc.nextInt();
        sc.nextLine();

        printList();

        String[] items = new String[amount];

        for (int i = 0; i < amount; i++) {
            System.out.println("Enter your item no. " + (i + 1));
            System.out.print("Item Name: ");
            String itemName = sc.nextLine();
            System.out.print("Item ID: ");
            String id = sc.nextLine();

            items[i] = String.format("[%s] %s", id, itemName);
        }
        sc.close();

        System.out.println("\nHere is your items list.");
        double totalPrice = calcTotalPrice(items, true);

        double finalPrice = totalPrice - (totalPrice * discount(totalPrice, isMember));
        System.out.printf("Total Price: RM %.2f%n", finalPrice);

        readFile(recieptsFile, reciepts);
        reciepts.add(generateReciept(name, isMember, items));
        saveToFile(recieptsFile, reciepts);

    }

    private static void printList() {
        System.out.println("\nThe price of the item is based on the ID's prefix. Here the list");
        System.out.println("A: RM 70\nB: RM 55\nC: RM 35\nD: RM 20");
        System.out.println("The format should [category:3 digit code]");
        System.out.println("e.g. A231 or C213\n");
    }

    private static String generateReciept(String name, boolean isMember, String[] items) {
        String reciept = String.format("Reciept created for %s [%s]:%n", name, isMember ? "Member" : "Not Member");
        reciept += String.format("Created on %s%n", getDate());

        for (String currentItem : items) {
            String id = currentItem.split(" ")[0].replace("[", "").replace("]", "");
            double price = getItemPrice(id);
            reciept += String.format("%s: %.2f%n", currentItem, price);
        }

        double totalPrice = calcTotalPrice(items, false);
        double finalPrice = totalPrice - (totalPrice * discount(totalPrice, isMember));
        reciept += String.format("Total Price: RM %.2f%n", finalPrice);
        reciept += "\n";

        return reciept;
    }

    private static String getDate() {
        LocalDateTime current = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = current.format(formatter);

        return formatted;
    }

    private static double discount(double totalPrice, boolean isMember) {
        double discountPercent;

        if (totalPrice >= 200) {
            if (isMember) {
                discountPercent = 0.1;
            } else {
                discountPercent = 0;
            }
        } else if (totalPrice >= 100) {
            if (isMember) {
                discountPercent = 0.05;
            } else {
                discountPercent = 0;
            }
        } else {
            discountPercent = 0;
        }

        return discountPercent;
    }

    private static double calcTotalPrice(String[] items, boolean printList) {
        double totalPrice = 0;
        for (String currentItem : items) {
            String id = currentItem.split(" ")[0].replace("[", "").replace("]", "");
            id = id.toLowerCase();

            double price = getItemPrice(id);
            totalPrice += price;

            if (printList) {
                System.out.printf("%s: %.2f%n", currentItem, price);
            }
        }

        return totalPrice;
    }

    private static double getItemPrice(String id) {
        double price = 0;
        id = id.toLowerCase();

        for (String[] item : itemData) {
            if (id.startsWith(item[0].toLowerCase())) {
                price = Double.parseDouble(item[1]);
                break;
            }
        }

        return price;
    }

    private static void saveToFile(String filePath, List<String> data) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void readFile(String fileName, List<String> list) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line.trim());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
