package program;

import java.util.Scanner;

public class InputHelp {
    static Scanner in = new Scanner(System.in);


    public static String inputString(String message, boolean allowNull) {
        String value = null;
        do {
            System.out.print(message);
            value = in.nextLine();
        } while (value == null && value.isEmpty() && !allowNull);
        return value;
    }

    public static double inputDouble(String message, boolean allowNull) {
        String strValue = null;
        do {
            strValue = inputString(message, allowNull);
        } while (!isNumber(strValue));
        return Double.parseDouble(strValue);
    }

    public static boolean isNumber(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static int inputInt(String message, boolean allowNull) {
        String strValue = null;
        do {
            strValue = inputString(message, allowNull);
        } while (!isNumberInt(strValue));
        return Integer.parseInt(strValue);
    }

    public static boolean isNumberInt(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
