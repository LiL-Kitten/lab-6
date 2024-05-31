package org.example.data;


import java.io.Serializable;

public enum Country implements Serializable {
    RUSSIA,
    UNITED_KINGDOM,
    SPAIN,
    VATICAN;

    public static String countries() {
        StringBuilder nameList = new StringBuilder();
        for (var country : values()) {
            if (nameList.length() > 0) {
                nameList.append("   ");
            }
            nameList.append(country.name());
        }
        return nameList.substring(0, nameList.length()) + "\n";
    }
}
