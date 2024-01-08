package dev.inctrl.retailer;

import java.io.*;
import java.util.*;

public class Main {

    private static List<String> members = new ArrayList<>();
    private static String membersFile = "data/members.txt";

    public static void main(String[] args) {
        System.out.println("Hello World by InCTRL.dev");

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to In CTRL Store.");
        System.out.println("Now enter all the required informations.");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Are you a member? [Yes/No]: ");
        boolean isMember = sc.nextLine().equalsIgnoreCase("yes");
        System.out.print("How many items you have?: ");
        int amount = sc.nextInt();
        sc.nextLine();

        System.out.println("\nThe price of the item is based on the ID's prefix. Here the list");
        System.out.println("A: RM 70\nB: RM 55\nC: RM 35\nD: RM 20");
        System.out.println("The format should [category:3 digit code]");
        System.out.println("e.g. A231 or C213\n");

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

        readFile(membersFile, members);
        members.add(String.format("Name: %s, Member: %s", name, isMember ? "Yes" : "No"));
        saveToFile(membersFile, members);

        System.out.println("\nHere is your items list.");
        double totalPrice = calcTotalPrice(items);

        discount(totalPrice, isMember);
        System.out.printf("Total Price: RM %.2f%n", totalPrice);

    }

    private static double discount(double totalPrice, boolean isMember) {

        // if (isMember) {

        // }

        double discountPercent = 0;

        if (totalPrice >= 200) {
            if (isMember) {
                discountPercent = .1;
            }
        } else if (totalPrice >= 100) {
            if (isMember) {
                discountPercent = .05;
            }
        } else {
            discountPercent = 0;
        }

        return discountPercent;
    }

    private static double calcTotalPrice(String[] items) {
        double totalPrice = 0;
        for (int i = 0; i < items.length; i++) {
            double price = 0;
            String currentItem = items[i];
            String id = currentItem.split(" ")[0].replace("[", "").replace("]", "");
            id = id.toLowerCase();

            if (id.startsWith("a")) {
                price = 70.0;
            } else if (id.startsWith("b")) {
                price = 55.0;
            } else if (id.startsWith("c")) {
                price = 35.0;
            } else if (id.startsWith("d")) {
                price = 20.0;
            }
            totalPrice += price;

            System.out.printf("%s: %.2f%n", currentItem, price);
        }

        return totalPrice;
    }

    // private static String getMember(String name) {

    // }

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
