package program;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        String strConn = "jdbc:mariadb://localhost:3306/vpd912java";
        //System.out.println(HandMadeSeeder.randomNameProduct() + " " + HandMadeSeeder.randomDescriptionProduct());
        //System.out.println(HandMadeSeeder.randomNameNews() + " | " + HandMadeSeeder.randomDescriptionNews());
//        menu(strConn);
//        selectNews(strConn);
//        insertRandomNews(strConn);
//        selectNews(strConn);
        insertRandomProduct(strConn);
    }
    private static void menu(String strConn){
        int select;
        do{
        System.out.println("(1)- Керування продуктами");
        System.out.println("(2)- Керування новинами");
        System.out.print("\t>");
        select = in.nextInt();
        switch (select)
        {
            case 1:
                adminProduct(strConn);
                break;
            case 2:
                adminNews(strConn);
                break;
            default:
                System.out.println("Некоректний вибір");
                break;
        }
        }while (select < 1 && select > 2);
    }

    private static void adminProduct(String strConn){
        int select;
        do{
            System.out.println("(1)- Показати продукти");
            System.out.println("(2)- Додати продукт");
            System.out.println("(3)- Видалити продукт");
            System.out.println("(4)- Редагувати продукт");
            System.out.print("\t>");
            select = in.nextInt();
            switch (select)
            {
                case 1:
                    select(strConn);
                    break;
                case 2:
                    insert(strConn);
                    break;
                case 3:
                    delete(strConn);
                    break;
                case 4:
                    update(strConn);
                    break;
                default:
                    System.out.println("Некоректний вибір");
                    break;
            }
        }while (select < 1 && select > 4);
    }

    private static void adminCategoryNews(String strConn){
        int select;
        do{
            System.out.println("(1)- Показати категорії");
            System.out.println("(2)- Додати категорію");
            System.out.println("(3)- Видалити категорію");
            System.out.println("(4)- Редагувати категорію");
            System.out.print("\t>");
            select = in.nextInt();
            switch (select)
            {
                case 1:
                    selectCategory(strConn);
                    break;
                case 2:
                    insertCategory(strConn);
                    break;
                case 3:
                    deleteCategory(strConn);
                    break;
                case 4:
                    updateCategory(strConn);
                    break;
                default:
                    System.out.println("Некоректний вибір");
                    break;
            }
        }while (select < 1 && select > 4);
        menu(strConn);
    }

    private static void adminNews(String strConn){
        int select;
        do{
            System.out.println("(1)- Показати новини");
            System.out.println("(2)- Додати новину");
            System.out.println("(3)- Видалити новину");
            System.out.println("(4)- Редагувати новину");
            System.out.println("(5)- Керувати категоріями");
            System.out.print("\t>");
            select = in.nextInt();
            switch (select)
            {
                case 1:
                    selectNews(strConn);
                    break;
                case 2:
                    insertNews(strConn);
                    break;
                case 3:
                    deleteNews(strConn);
                    break;
                case 4:
                    updateNews(strConn);
                    break;
                case 5:
                    adminCategoryNews(strConn);
                    break;
                default:
                    System.out.println("Некоректний вибір");
                    break;
            }
        }while (select < 1 && select > 5);
    }

    private static void insert(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "INSERT INTO products (name, price, description) " +
                    "VALUES (?, ?, ?);";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                Product p = new Product();

                p.setName(InputHelp.inputString("Enter name: ", false));
                p.setPrice(InputHelp.inputDouble("Enter price: ", false));
                p.setDescription(InputHelp.inputString("Enter description: ", false));

                ps.setString(1, p.getName());
                ps.setBigDecimal(2, new BigDecimal(p.getPrice()));
                ps.setString(3, p.getDescription());
                int rows = ps.executeUpdate();
                System.out.println("Update rows = " + rows);
                menu(strConn);
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }

    private static void select(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "SELECT * FROM products";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ResultSet resultSet = ps.executeQuery();
                if (resultSet != null) {
                    while (resultSet.next()) {
                        System.out.print("{id = " + resultSet.getInt("id") + ", ");
                        System.out.print("name = " + resultSet.getString("name") + ", ");
                        System.out.print("price = " + resultSet.getBigDecimal("price") + ", ");
                        System.out.print("description = " + resultSet.getString("description") + "}\n");
                    }
                }
                else
                    insertRandomProduct(strConn);

                menu(strConn);
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }

    private static void delete(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "DELETE FROM products WHERE id = ?;";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                int id;
                System.out.print("Enter id: ");
                id = in.nextInt();
                Product p = getById(strConn, id);
                if (p != null) {
                    ps.setInt(1, id);
                    int rows = ps.executeUpdate();
                    System.out.println("Update rows: " + rows);
                }
                else
                    System.out.println("Продукта з таким id не знайдено!!!");

                menu(strConn);
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }

    private static Product getById(String strConn, int id){
        try(Connection con = DriverManager.getConnection(strConn, "root", ""))
        {
            System.out.println("Connection is good");
            String query = "SELECT * FROM products where id = ?";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();
                if(resultSet.next())
                {
                    Product p = new Product();
                    p.setId(resultSet.getInt("id"));
                    p.setName(resultSet.getString("name"));
                    p.setPrice(resultSet.getDouble("price"));
                    p.setDescription(resultSet.getString("description"));
                    return p;
                }
            }
            catch(Exception ex) {
                System.out.println("error statement");
            }
        }
        catch(Exception ex) {
            System.out.println("Error connection");
        }
        return null;
    }

    private static void update(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", ""))
        {
            System.out.println("Connection is good");
            String query = "UPDATE products SET name = ?, price=?, description=? " +
                    "WHERE id = ?;";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                int id;
                System.out.print("Enter id: ");
                id = Integer.parseInt(in.nextLine());
                Product p = getById(strConn, id);
                if (p != null) {
                    String tmp = InputHelp.inputString("Enter new name: ", true);
                    if (tmp != null && !tmp.isEmpty()) {
                        p.setName(tmp);
                    }
                    tmp = InputHelp.inputString("Enter price: ", true);
                    if (tmp != null && !tmp.isEmpty() && InputHelp.isNumber(tmp)) {
                        p.setPrice(Double.parseDouble(tmp));
                    }
                    tmp = InputHelp.inputString("Enter description: ", true);
                    if (tmp != null && !tmp.isEmpty()) {
                        p.setDescription(tmp);
                    }
                }
                ps.setString(1, p.getName());
                ps.setDouble(2, p.getPrice());
                ps.setString(3,p.getDescription());
                ps.setInt(4, id);
                int rows = ps.executeUpdate();
                System.out.println("Update rows: " +rows);
                menu(strConn);
            }
            catch(Exception ex) {
                System.out.println("Error statement");
            }
        }
        catch(Exception ex) {
            System.out.println("Error connection");
        }
    }

    private static void selectCategory(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "SELECT * FROM category";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ResultSet resultSet = ps.executeQuery();
                if (resultSet != null) {
                    while (resultSet.next()) {
                        System.out.print("{number = " + resultSet.getInt("id") + ", ");
                        System.out.print("name = " + resultSet.getString("name") + "};\n");
                    }
                }
                else
                {
                    System.out.println("Даних немає!!!\n");
                    System.out.println("Добавте дані");
                    insertCategory(strConn);
                }

                //menu(strConn);
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }

    private static void insertCategory(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "INSERT INTO category (name) " +
                    "VALUES (?);";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                String name = InputHelp.inputString("Enter name: ", false);

                ps.setString(1, name);
                int rows = ps.executeUpdate();
                System.out.println("Update rows = " + rows);
                //menu(strConn);
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }

    private static void deleteCategory(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "DELETE FROM category WHERE id = ?;";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                int id;
                System.out.print("Enter id: ");
                id = in.nextInt();
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                System.out.println("Update rows: " + rows);
                menu(strConn);
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }

    private static void updateCategory(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", ""))
        {
            System.out.println("Connection is good");
            String query = "UPDATE category SET name = ?" +
                    "WHERE id = ?;";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                int id;
                System.out.print("Enter id: ");
                id = Integer.parseInt(in.nextLine());
                String name = InputHelp.inputString("Enter new name: ", true);
                ps.setString(1, name);
                ps.setInt(2, id);
                int rows = ps.executeUpdate();
                System.out.println("Update rows: " +rows);
                menu(strConn);
            }
            catch(Exception ex) {
                System.out.println("Error statement");
            }
        }
        catch(Exception ex) {
            System.out.println("Error connection");
        }
    }

    private static void selectNews(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "SELECT nw.id, nw.name, nw.description, cat.name" +
                    " FROM news as nw join category as cat on nw.category_id = cat.id;";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ResultSet resultSet = ps.executeQuery();
                if (resultSet != null) {
                    while (resultSet.next()) {
                        System.out.print("{id = " + resultSet.getInt("nw.id") + ", ");
                        System.out.print("name = " + resultSet.getString("nw.name") + ", ");
                        System.out.print("description = " + resultSet.getString("nw.description") + ", ");
                        System.out.print("category = " + resultSet.getString("cat.name") + "}\n");
                    }
                }
                else
                    insertRandomNews(strConn);

                menu(strConn);
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }

    private static void insertNews(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "INSERT INTO news (name, description, category_id) " +
                    "VALUES (?, ?, ?);";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                News p = new News();
                selectCategory(strConn);
                p.setName(InputHelp.inputString("Enter name: ", false));
                p.setDescription(InputHelp.inputString("Enter description: ", false));
                p.setCategory_id(InputHelp.inputInt("Enter the category number from the\n list above: ", false));

                ps.setString(1, p.getName());
                ps.setString(2, p.getDescription());
                ps.setInt(3, p.getCategory_id());
                int rows = ps.executeUpdate();
                System.out.println("Update rows = " + rows);
                menu(strConn);
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }

    private static void deleteNews(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "DELETE FROM news WHERE id = ?;";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                int id;
                System.out.print("Enter id: ");
                id = in.nextInt();
                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                System.out.println("Update rows: " + rows);
                menu(strConn);
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }

    private static News getNewsById(String strConn, int id){
        try(Connection con = DriverManager.getConnection(strConn, "root", ""))
        {
            System.out.println("Connection is good");
            String query = "SELECT * FROM news where id = ?";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();
                if(resultSet.next())
                {
                    News p = new News();
                    p.setId(resultSet.getInt("id"));
                    p.setName(resultSet.getString("name"));
                    p.setDescription(resultSet.getString("description"));
                    p.setCategory_id(resultSet.getInt("category_id"));
                    return p;
                }
            }
            catch(Exception ex) {
                System.out.println("error statement");
            }
        }
        catch(Exception ex) {
            System.out.println("Error connection");
        }
        return null;
    }

    private static void updateNews(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", ""))
        {
            System.out.println("Connection is good");
            String query = "UPDATE news SET name = ?, description = ?, category_id = ? WHERE id = ?;";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                int id;
                System.out.print("Enter id: ");
                id = Integer.parseInt(in.nextLine());
                selectCategory(strConn);
                News p = getNewsById(strConn, id);
                if (p != null) {
                    String tmp = InputHelp.inputString("Enter new name: ", true);
                    if (tmp != null && !tmp.isEmpty()) {
                        p.setName(tmp);
                    }
                    tmp = InputHelp.inputString("Enter description: ", true);
                    if (tmp != null && !tmp.isEmpty()) {
                        p.setDescription(tmp);
                    }
                    tmp = InputHelp.inputString("Enter the category number from the\n list above: ", true);
                    if (tmp != null && !tmp.isEmpty() && InputHelp.isNumber(tmp)) {
                        p.setCategory_id(Integer.parseInt(tmp));
                    }
                }
                ps.setString(1, p.getName());
                ps.setString(2,p.getDescription());
                ps.setInt(3, p.getCategory_id());
                ps.setInt(4, id);
                int rows = ps.executeUpdate();
                System.out.println("Update rows: " +rows);
                menu(strConn);
            }
            catch(Exception ex) {
                System.out.println("Error statement");
            }
        }
        catch(Exception ex) {
            System.out.println("Error connection");
        }
    }

    private static void insertRandomProduct(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "INSERT INTO products (name, price, description) " +
                    "VALUES (?, ?, ?);";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                int rows = 0;
                for (int i = 0; i < 100; i++)
                {
                    ps.setString(1, HandMadeSeeder.randomNameProduct());
                    ps.setBigDecimal(2, new BigDecimal(HandMadeSeeder.randomPriceDouble(10, 100)));
                    ps.setString(3, HandMadeSeeder.randomDescriptionProduct());
                    rows = ps.executeUpdate();
                }
                System.out.println("Update rows = " + rows);
                menu(strConn);
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }

    private static int selectCountCategory(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "SELECT COUNT(name) AS countID FROM category;";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
                return 0;
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
                return 0;
            }
        }
        catch (Exception ex){
            System.out.println("Problem connect - " + ex);
            return 0;
        }
    }

    private static int[] getIdFromCategory(String strConn) {
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            String query = "SELECT id FROM category;";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ResultSet resultSet = ps.executeQuery();
                if (resultSet != null) {
                    int count = selectCountCategory(strConn);
                    int[] idArray = new int[count];
                    for (int i = 0; resultSet.next(); i++){
                            idArray[i] = resultSet.getInt(1);
                    }
                    return idArray;
                }
                else
                {
                    insertRandomCategory(strConn);
                    getIdFromCategory(strConn);
                }

            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
        return new int[10];
    }

    private static void insertRandomNews(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "INSERT INTO news (name, description, category_id) " +
                    "VALUES (?, ?, ?);";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                int rows = 0;
                int[] array = getIdFromCategory(strConn);
                for (int i = 0; i < 10; i++)
                {
//                    ps.setString(1, HandMadeSeeder.randomNameNews());
//                    ps.setString(2, HandMadeSeeder.randomDescriptionNews());
//                    ps.setInt(3, HandMadeSeeder.randomCategoryNews(array));
//                    rows = ps.executeUpdate();
                }
                System.out.println("Update rows = " + rows);
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }

    private static void insertRandomCategory(String strConn){
        try(Connection con = DriverManager.getConnection(strConn, "root", "")){
            System.out.println("Happy hacking");
            String query = "INSERT INTO category (name) " +
                    "VALUES (?);";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                int rows = 0;
                for (int i = 0; i < 100; i++)
                {
                    //ps.setString(1, HandMadeSeeder.randomNameCategoryNews());
                    rows = ps.executeUpdate();
                }
            }
            catch (Exception ex){
                System.out.println("Problem - " + ex);
            }
        }
        catch (Exception ex){
            System.out.println("Problem - " + ex);
        }
    }
}
