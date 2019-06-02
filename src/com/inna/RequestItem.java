package com.inna;

import org.jetbrains.annotations.NotNull;

public class RequestItem {

    private String destination;
    private String flight;
    private String name;
    private String date;

    private String getMasElement (String[] masData, int i, int masLength) {
        return (i < masLength) ? masData[i] : "";
    }

    public int parseDataToItem(@NotNull String command, String data) {
        String[] myData = data.split(",");
        int length = myData.length;

        int identifier;
        switch (command) {
            case "update":
                identifier = Integer.parseInt(myData[0].trim());
                destination = getMasElement(myData, 1, length);
                flight = getMasElement(myData, 2, length);
                name = getMasElement(myData, 3, length);
                date = getMasElement(myData, 4, length);
                return identifier;
            case "add":
                destination = getMasElement(myData, 0, length);
                flight = getMasElement(myData, 1, length);
                name = getMasElement(myData, 2, length);
                date = getMasElement(myData, 3, length);
                return 0;
            case "delete":
            case "show":
                identifier = Integer.parseInt(myData[0]);
                return identifier;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return destination + ", " + flight + ", " + name + ", " + date + "\n";
    }
}