package ControlPanel.UI;


public class ColPrinter {

    /**
     * ANSI escape code for resetting text color to default.
     */
    private static final String RESET = "\u001B[0m";
    private static final String BLACK = "\u001B[30m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    private static final String BRIGHT_BLACK = "\u001B[90m";
    private static final String BRIGHT_RED = "\u001B[91m";
    private static final String BRIGHT_GREEN = "\u001B[92m";
    private static final String BRIGHT_YELLOW = "\u001B[93m";
    private static final String BRIGHT_BLUE = "\u001B[94m";
    private static final String BRIGHT_PURPLE = "\u001B[95m";
    private static final String BRIGHT_CYAN = "\u001B[96m";
    private static final String BRIGHT_WHITE = "\u001B[97m";

    /**
     * ANSI escape codes for various text colors.
     */
    public static String black(String text) {
        return BLACK + text + RESET;
    }
    public static String red(String text) {
        return RED + text + RESET;
    }
    public static String green(String text) {
        return GREEN + text + RESET;
    }
    public static String yellow(String text) {
        return YELLOW + text + RESET;
    }
    public static String blue(String text) {
        return BLUE + text + RESET;
    }
    public static String purple(String text) {
        return PURPLE + text + RESET;
    }
    public static String cyan(String text) {
        return CYAN + text + RESET;
    }
    public static String white(String text) {
        return WHITE + text + RESET;
    }
    public static String brightBlack(String text) {
        return BRIGHT_BLACK + text + RESET;
    }
    public static String brightRed(String text) {
        return BRIGHT_RED + text + RESET;
    }
    public static String brightGreen(String text) {
        return BRIGHT_GREEN + text + RESET;
    }
    public static String brightYellow(String text) {
        return BRIGHT_YELLOW + text + RESET;
    }
    public static String brightBlue(String text) {
        return BRIGHT_BLUE + text + RESET;
    }
    public static String brightPurple(String text) {
        return BRIGHT_PURPLE + text + RESET;
    }
    public static String brightCyan(String text) {
        return BRIGHT_CYAN + text + RESET;
    }
    public static String brightWhite(String text) {
        return BRIGHT_WHITE + text + RESET;
    }

    /**
     * Returns a string with the specified text colored in black.
     *
     * @param text the text to color
     * @return the colored text string
     */
    public static String black(int text) {
        return BLACK + text + RESET;
    }
    public static String red(int text) {
        return RED + text + RESET;
    }
    public static String green(int text) {
        return GREEN + text + RESET;
    }
    public static String yellow(int text) {
        return YELLOW + text + RESET;
    }
    public static String blue(int text) {
        return BLUE + text + RESET;
    }
    public static String purple(int text) {
        return PURPLE + text + RESET;
    }
    public static String cyan(int text) {
        return CYAN + text + RESET;
    }
    public static String white(int text) {
        return WHITE + text + RESET;
    }
    public static String brightBlack(int text) {
        return BRIGHT_BLACK + text + RESET;
    }
    public static String brightRed(int text) {
        return BRIGHT_RED + text + RESET;
    }
    public static String brightGreen(int text) {
        return BRIGHT_GREEN + text + RESET;
    }
    public static String brightYellow(int text) {
        return BRIGHT_YELLOW + text + RESET;
    }
    public static String brightBlue(int text) {
        return BRIGHT_BLUE + text + RESET;
    }
    public static String brightPurple(int text) {
        return BRIGHT_PURPLE + text + RESET;
    }
    public static String brightCyan(int text) {
        return BRIGHT_CYAN + text + RESET;
    }
    public static String brightWhite(int text) {
        return BRIGHT_WHITE + text + RESET;
    }

    public static String black(String[] text) {
        return BLACK + arrayToString(text) + RESET;
    }
    public static String red(String[] text) {
        return RED + arrayToString(text) + RESET;
    }
    public static String green(String[] text) {
        return GREEN + arrayToString(text) + RESET;
    }
    public static String yellow(String[] text) {
        return YELLOW + arrayToString(text) + RESET;
    }
    public static String blue(String[] text) {
        return BLUE + arrayToString(text) + RESET;
    }
    public static String purple(String[] text) {
        return PURPLE + arrayToString(text) + RESET;
    }
    public static String cyan(String[] text) {
        return CYAN + arrayToString(text) + RESET;
    }
    public static String white(String[] text) {
        return WHITE + arrayToString(text) + RESET;
    }
    public static String brightBlack(String[] text) {
        return BRIGHT_BLACK + arrayToString(text) + RESET;
    }
    public static String brightRed(String[] text) {
        return BRIGHT_RED + arrayToString(text) + RESET;
    }
    public static String brightGreen(String[] text) {
        return BRIGHT_GREEN + arrayToString(text) + RESET;
    }
    public static String brightYellow(String[] text) {
        return BRIGHT_YELLOW + arrayToString(text) + RESET;
    }
    public static String brightBlue(String[] text) {
        return BRIGHT_BLUE + arrayToString(text) + RESET;
    }
    public static String brightPurple(String[] text) {
        return BRIGHT_PURPLE + arrayToString(text) + RESET;
    }
    public static String brightCyan(String[] text) {
        return BRIGHT_CYAN + arrayToString(text) + RESET;
    }
    public static String brightWhite(String[] text) {
        return BRIGHT_WHITE + arrayToString(text) + RESET;
    }

    public static String black(int[] text) {
        return BLACK + arrayToString(text) + RESET;
    }
    public static String red(int[] text) {
        return RED + arrayToString(text) + RESET;
    }
    public static String green(int[] text) {
        return GREEN + arrayToString(text) + RESET;
    }
    public static String yellow(int[] text) {
        return YELLOW + arrayToString(text) + RESET;
    }
    public static String blue(int[] text) {
        return BLUE + arrayToString(text) + RESET;
    }
    public static String purple(int[] text) {
        return PURPLE + arrayToString(text) + RESET;
    }
    public static String cyan(int[] text) {
        return CYAN + arrayToString(text) +  RESET;
    }
    public static String white(int[] text) {
        return WHITE + arrayToString(text) + RESET;
    }
    public static String brightBlack(int[] text) {
        return BRIGHT_BLACK + arrayToString(text) + RESET;
    }
    public static String brightRed(int[] text) {
        return BRIGHT_RED + arrayToString(text) + RESET;
    }
    public static String brightGreen(int[] text) {
        return BRIGHT_GREEN + arrayToString(text) + RESET;
    }
    public static String brightYellow(int[] text) {
        return BRIGHT_YELLOW + arrayToString(text) + RESET;
    }
    public static String brightBlue(int[] text) {
        return BRIGHT_BLUE + arrayToString(text) + RESET;
    }
    public static String brightPurple(int[] text) {
        return BRIGHT_PURPLE + arrayToString(text) + RESET;
    }
    public static String brightCyan(int[] text) {
        return BRIGHT_CYAN + arrayToString(text) + RESET;
    }
    public static String brightWhite(int[] text) {
        return BRIGHT_WHITE + arrayToString(text) + RESET;
    }

    /**
     * Returns a string representation of the given array of integers.
     *
     * @param array the array of integers
     * @return a string representation of the array
     */
    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns a string representation of the given array of strings.
     *
     * @param array the array of strings
     * @return a string representation of the array
     */
    private static String arrayToString(String[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append("\"").append(array[i]).append("\"");
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}