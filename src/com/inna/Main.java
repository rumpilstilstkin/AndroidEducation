package com.inna;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Main {

    private static boolean requestChecker(LinkedList commands, String checkedCommand, String checkedString) {
        if (commands.contains(checkedCommand)) {
            String[] myData = checkedString.split(", ");
            if (!checkedCommand.equalsIgnoreCase("add") && !checkedCommand.equalsIgnoreCase("showAll")) {
                try {
                    Integer.parseInt(myData[0]);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            } else return true;
        } else return false;
    }

    private static void writeToFile(String data) {
        File file = new File(System.getProperty("user.dir") + "/output.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fr != null;
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int id;

    private static HashMap<Integer, RequestItem> listOfRequests = new LinkedHashMap<>();

    public static void main(String[] args) {

        LinkedList avaliableCommands = new LinkedList();

        avaliableCommands.add("add");
        avaliableCommands.add("update");
        avaliableCommands.add("delete");
        avaliableCommands.add("show");
        avaliableCommands.add("showAll");

        Scanner in = new Scanner(System.in);
        String command = "";

        while (!command.equals("finish")) {
            //System.out.print("Input data: ");
            command = in.next().trim();
            String data = in.nextLine().trim();

            if (requestChecker(avaliableCommands, command, data)) {
                RequestItem newRecord = new RequestItem();
                int getId = newRecord.parseDataToItem(command, data);
                String content;
                switch (command) {
                    case "add":
                        ++id;
                        listOfRequests.put(id, newRecord);
                        content = listOfRequests.get(id).toString();
                        writeToFile(id + ", " + content);
                        break;
                    case "delete":
                        if (listOfRequests.containsKey(getId)) {
                            content = listOfRequests.get(getId).toString();
                            writeToFile(getId + ", " + content);
                            listOfRequests.remove(getId);
                        } else {
                            System.out.print("Not found id: " + getId + "\n");
                            writeToFile("Not found \n");
                        }
                        break;
                    case "update":
                        if (listOfRequests.containsKey(getId)) {
                            listOfRequests.put(getId, newRecord);
                            content = listOfRequests.get(getId).toString();
                            writeToFile(getId + ", " + content);
                        } else {
                            System.out.print("Not found id: " + getId + "\n");
                            writeToFile("Not found \n");
                        }
                        break;
                    case "show":
                        if (listOfRequests.containsKey(getId)) {
                            content = listOfRequests.get(getId).toString();
                            System.out.print(getId + ", " + content);
                            writeToFile(getId + ", " + content);
                        } else {
                            System.out.print("Not found id: " + getId + "\n");
                            writeToFile("Not found \n");
                        }
                        break;
                    case "showAll":
                        if (listOfRequests.size() != 0) {
                            content = listOfRequests
                                    .toString()
                                    .replace("=", ", ")
                                    .replace("\n" + ", ", "\n")
                                    .replace("{", "")
                                    .replace("}", "");

                            writeToFile(content);
                            System.out.println(content);
                        } else {
                            System.out.print("Empty \n");
                            writeToFile("Empty \n");
                        }
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + command + "\n");
                }
            } else {
                System.out.print("Your data is incorrect! \n");
            }
        }
    }
}
