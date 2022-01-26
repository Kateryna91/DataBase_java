package program;

public class HandMadeSeeder {

    public static double randomPriceDouble(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }

    public static int randomPriceInt(int min, int max) {
        return (int)((Math.random() * (max - min)) + min);
    }

    public static String randomNameProduct() {
        String[] nameArray = new String[]{
                "Bread", "Bananas", "Lemon", "Tea", "Coffee",
                "Milk", "Sugar", "Cakes", "Avocado", "Sushi",
                "Vine", "Potato", "Tomato", "Candy", "Chocolate",
                "Fish", "Chia", "Mails",  "Ketchup",
                "Sous", "Gum", "Ice cream", ""
        };
        return nameArray[(int)((Math.random() * (nameArray.length - 0)) + 0)];
    }

    public static String randomDescriptionProduct() {
        String[] nameArray = new String[]{
                "Decisions", "Very testy", "Fresh", "Spice", "Hot",
                "Not good condition", "top sale", "Good", "Chipper"
        };
        return nameArray[(int)((Math.random() * (nameArray.length - 0)) + 0)];
    }


}
