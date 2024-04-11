package examples.data;

public enum Color {
    RED("\u001B[31m"),
    BLUE("\u001B[34m"),
    YELLOW("\u001B[33m"),
    WHITE("\u001B[0m"),
    BROWN("\u001B[0;33m");

    private final String colorCode;

    Color(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public static String colors()
    {
        StringBuilder nameList = new StringBuilder();
        for (var form : values())
        {
            nameList.append(form.getColorCode()).append(form.name()).append("\u001B[0m").append("   ");
        }
        return nameList.toString()+"\n";

    }

}