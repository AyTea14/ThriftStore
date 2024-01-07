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
        System.out.println("A: RM 70\nB: RM 55\nC: RM 35\nD: RM 20\n");

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

        System.out.println("\nHere is your reciept.");
        calcTotalPrice(items);
    }

    private static void calcTotalPrice(String[] items) {
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

        System.out.printf("Total Price: RM %.2f%n", totalPrice);
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

    public static String readFile(String fileName, List<String> list) {
        StringBuilder content = new StringBuilder();

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

        return content.toString();
    }

}
