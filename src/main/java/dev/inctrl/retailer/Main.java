package dev.inctrl.retailer;

import java.io.*;
import java.util.*;

public class Main {

    private static List<String> members = new ArrayList<>();
    private static String membersFile = "data/members.txt";

    public static void main(String[] args) {
        System.out.println("Hello World by InCTRL.dev");

        readFile(membersFile, members);
        saveToFile(membersFile, members);

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
