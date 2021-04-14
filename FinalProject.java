/**
 @author Redwanul Islam <a href="mailto:redwanul.islam@ucalgary.ca">redwanul.islam@ucalgary.ca</a>
 @author Mubashir Rahman <a href="mailto:mubashir.rahman@ucalgary.ca">mubashir.rahman@ucalgaru.ca</a>
 @author Hasan Mahtab <a href="mailto:hasan.mahtab@ucalgary.ca">hasan.mahtab@ucalgary.ca</a>
 @author Arafatul Mamur <a "mailto:arafatul.mamur@ucalgary.ca">arafatul.mamur@ucalgary.ca</a>
 @version 2.3
 @since 1.0
 */

package edu.ucalgary.ensf409;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.sql.*;

/**
 * The FinalProject class was created and implemented to fulfil the requirements
 * for ENSF 409 Final Project, in accordance with the project handout. It
 * implements various methods, all of which are used to fulfil the requirements
 * of the project.
 */

public class FinalProject {
    public final String DBURL; // store the database url information
    public final String USERNAME; // store the user's account username
    public final String PASSWORD; // store the user's account password

    private Connection dbConnect; // used to establish connection with the database in order to run statements
    private ResultSet results; // stores the results from execution of SQL Query

    public final String[] chairManufacturers = { "Office Furnishings", "Chairs R Us", "Furniture Goods",
            "Fine Office Supplies" };
    public final String[] deskManufacturers = { "Academic Desks", "Office Furnishings", "Furniture Goods",
            "Fine Office Supplies" };
    public final String[] lampManufacturers = { "Office Furnishings", "Furniture Goods", "Fine Office Supplies" };
    public final String[] filingManufacturers = { "Office Furnishings", "Furniture Goods", "Fine Office Supplies" };

    /**
     * Constructor that initializes the 3 public data members using arguments
     * provided
     * 
     * @param DBURL    database url information
     * @param USERNAME user's account username
     * @param PASSWORD user's account password
     */
    FinalProject(String DBURL, String USERNAME, String PASSWORD) {
        this.DBURL = DBURL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    /**
     * Getter method which returns the DBURL variable data
     * 
     * @return database url information
     */
    String getDburl() {
        return this.DBURL;
    }

    /**
     * Getter method which returns the USERNAME variable data
     * 
     * @return user's account username
     */
    String getUsername() {
        return this.USERNAME;
    }

    /**
     * Getter method which returns the chairManufacturers variable data
     * 
     * @return chairManufacturers[]
     */
    String[] getChairManufacturers() {
        return this.chairManufacturers;
    }

    /**
     * Getter method which returns the deskManufacturers variable data
     * 
     * @return deskManufacturers[]
     */
    String[] getDeskManufacturers() {
        return this.deskManufacturers;
    }

    /**
     * Getter method which returns the lampManufacturers variable data
     * 
     * @return lampManufacturers[]
     */
    String[] getLampManufacturers() {
        return this.lampManufacturers;
    }

    /**
     * Getter method which returns the filingManufacturers variable data
     * 
     * @return filingManufacturers[]
     */
    String[] getFilingManufacturers() {
        return this.filingManufacturers;
    }

    /**
     * Getter method which returns the PASSWORD variable data
     * 
     * @return user's account password
     */
    String getPassword() {
        return this.PASSWORD;
    }

    /**
     * Returns a boolean value of true, provided that the parameters are valid and
     * false otherwise. This method checks if the category is either chair,desk,lamp
     * or filing and throws an IllegalArgumentException is that isnt the case. It
     * also checks if the type is valid and throws an IllegalArgumentException if
     * that isnt the case. Checks if the value of item is greater than or equal to 1
     * and if it is valid, throws an IllegalArgumentException if that isnt the case.
     * 
     * @param category The name of the table from the database
     * @param type     The type of furniture requested
     * @param item     Number of furnitures requested as a String
     * @return A boolean value of true if parameters are valid and false otherwise
     */
    public boolean inputHandling(String category, String type, String tempItem) {
        // Checks if category entered is valid
        if (!(category.toLowerCase().trim().equals("chair") || category.toLowerCase().trim().equals("desk")
                || category.toLowerCase().trim().equals("lamp") || category.toLowerCase().trim().equals("filing"))) {
            throw new IllegalArgumentException("The category entered is not valid");
        }

        // Checks if type entered is valid
        if (category.toLowerCase().trim().equals("chair")) {
            String temp = type.toLowerCase().trim();
            if (!(temp.equals("kneeling") || temp.equals("task") || temp.equals("mesh") || temp.equals("ergonomic")
                    || temp.equals("executive"))) {
                throw new IllegalArgumentException("The type entered is not valid");
            }
        } else if (category.toLowerCase().trim().equals("desk")) {
            String temp = type.toLowerCase().trim();
            if (!(temp.equals("standing") || temp.equals("adjustable") || temp.equals("traditional"))) {
                throw new IllegalArgumentException("The type entered is not valid");
            }
        } else if (category.toLowerCase().trim().equals("lamp")) {
            String temp = type.toLowerCase().trim();
            if (!(temp.equals("desk") || temp.equals("study") || temp.equals("swing arm"))) {
                throw new IllegalArgumentException("The type entered is not valid");
            }
        } else if (category.toLowerCase().trim().equals("filing")) {
            String temp = type.toLowerCase().trim();
            if (!(temp.equals("small") || temp.equals("medium") || temp.equals("large"))) {
                throw new IllegalArgumentException("The type entered is not valid");
            }
        }
        // Checks if the value entered is valid (i.e not negative and not a decimal)
        String tempItemTrimmed = tempItem.trim();
        if (!(tempItemTrimmed.matches("^[0-9]*$"))) {
            throw new IllegalArgumentException("The input for number of furnitures requested is not valid");
        }
        int item = Integer.valueOf(tempItemTrimmed);

        // Checks if value entered for number of furnitures required is greater than or
        // equal to 1
        if (item <= 0) {
            throw new IllegalArgumentException("Number of furnitures ordered must be greater than or equal to 1");
        }
        return true;
    }

    /**
     * This method creates the orderform with the details of the fulfilled order
     * which includes the price and cheapest combination of items from the database,
     * called "orderform.txt" according to the parameters provided, in accordance
     * with the format mentioned in the project handout, with added functionality in
     * case the price is a decimal value. It also outputs a purchase message
     * containing the IDs and the price to the terminal
     * 
     * @param type        The type of furniture requested
     * @param category    The name of the table from the database
     * @param item        Number of furnitures requested
     * @param itemIdPrice String array which contains the ItemIDs of the cheapest
     *                    combination and the total price
     * @throws IOException
     */
    public void fileControl(String type, String category, int item, String[] itemIdPrice) throws IOException {
        // Creates a .txt file called orderform
        File outputFile = new File("orderform.txt");
        FileWriter output = new FileWriter(outputFile);

        // Used to seperate the IDs into their own String[]
        String[] itemID = new String[itemIdPrice.length - 1];
        for (int x = 0; x < itemID.length; x++) {
            itemID[x] = itemIdPrice[x];
        }

        // Used to retrieve the last element of itemIdPrice, which is the total price.
        double price = Double.valueOf(itemIdPrice[itemIdPrice.length - 1]);

        // Used to fill in the order form, according to the values provided.
        output.write("Furniture Order Form\n\nFaculty Name:\nContact:\nDate\n\n");
        output.write("Original Request: " + type.toLowerCase() + " " + category.toLowerCase() + ", " + item + "\n");
        output.write("\nItems Ordered\n");

        for (int x = 0; x < itemID.length; x++) {
            output.write("ID: " + itemID[x] + "\n");
        }

        output.write("\n");

        output.write("Total Price: $" + price);

        output.close();

        // Used to output the purchase message into the terminal
        StringBuilder sb = new StringBuilder("\nPurchase ");

        for (int x = 0; x < itemID.length - 1; x++) {
            sb.append(itemID[x] + ", ");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" and " + itemID[itemID.length - 1] + " for $" + price);

        System.out.println(sb.toString());

        return;
    }

    /**
     * Returns a boolean value, true if the connection is successful and false if
     * the connection is unsuccessful. This method is used to initialize the
     * connection between this program and inventory database. It uses the
     * getConnection() method which takes 3 string arguments, the Database URL,
     * Username of the account with access to the database, and the password of the
     * account. The try-catch statements are used in order to deal with possible SQL
     * related issues (SQLException)
     * 
     * @return A boolean value of true if the connection is successful, otherwise
     *         false
     */
    public boolean initializeConnection() {
        try {
            dbConnect = DriverManager.getConnection(getDburl(), getUsername(), getPassword());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns a String[][] containing all the data pulled from the database
     * according to the parameters provided. The try-catch statements are used in
     * order to deal with possible SQL related issues (SQLException)
     * 
     * @param type     The type of furniture requested
     * @param category The name of the table from the database
     * @return String[][] containing data pulled from the database.
     */
    public String[][] databaseToArray(String type, String category) {

        int row = -1;
        int col = -1;
        // Determines the number of columns required for the String[][] based on
        // category
        if (category.toLowerCase().equals("chair")) {
            col = 6;
        } else if (category.toLowerCase().equals("desk")) {
            col = 5;
        } else if (category.toLowerCase().equals("lamp")) {
            col = 4;
        } else if (category.toLowerCase().equals("filing")) {
            col = 5;
        }

        // Used to determine the number of rows required for the String[][]
        String queryString = "SELECT COUNT(*) FROM " + category + " WHERE Type = '" + type + "'";

        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery(queryString);

            while (results.next()) {
                row = Integer.valueOf(results.getString("COUNT(*)"));
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String[][] myArray = new String[row][col];

        // Used to fill all the elements in String[][] based on the category and type
        // provided.
        queryString = "SELECT * FROM " + category + " WHERE Type = '" + type + "'";

        if (category.toLowerCase().equals("chair")) {

            int x = 0;

            try {
                Statement myStmt = dbConnect.createStatement();
                results = myStmt.executeQuery(queryString);

                while (results.next()) {
                    if (x < row) {
                        myArray[x][0] = results.getString("ID");
                        myArray[x][1] = results.getString("Legs");
                        myArray[x][2] = results.getString("Arms");
                        myArray[x][3] = results.getString("Seat");
                        myArray[x][4] = results.getString("Cushion");
                        myArray[x][5] = String.valueOf(results.getInt("Price"));
                        x++;

                    }
                }

                myStmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (category.toLowerCase().equals("desk")) {
            int x = 0;

            try {
                Statement myStmt = dbConnect.createStatement();
                results = myStmt.executeQuery(queryString);

                while (results.next()) {
                    if (x < row) {
                        myArray[x][0] = results.getString("ID");
                        myArray[x][1] = results.getString("Legs");
                        myArray[x][2] = results.getString("Top");
                        myArray[x][3] = results.getString("Drawer");
                        myArray[x][4] = String.valueOf(results.getInt("Price"));
                        x++;

                    }
                }

                myStmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (category.toLowerCase().equals("lamp")) {
            int x = 0;

            try {
                Statement myStmt = dbConnect.createStatement();
                results = myStmt.executeQuery(queryString);

                while (results.next()) {
                    if (x < row) {
                        myArray[x][0] = results.getString("ID");
                        myArray[x][1] = results.getString("Base");
                        myArray[x][2] = results.getString("Bulb");
                        myArray[x][3] = String.valueOf(results.getInt("Price"));
                        x++;

                    }
                }

                myStmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (category.toLowerCase().equals("filing")) {
            int x = 0;

            try {
                Statement myStmt = dbConnect.createStatement();
                results = myStmt.executeQuery(queryString);

                while (results.next()) {
                    if (x < row) {
                        myArray[x][0] = results.getString("ID");
                        myArray[x][1] = results.getString("Rails");
                        myArray[x][2] = results.getString("Drawers");
                        myArray[x][3] = results.getString("Cabinet");
                        myArray[x][4] = String.valueOf(results.getInt("Price"));
                        x++;
                    }
                }

                myStmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        // Test to check if the length of myArray is 0, indicating that that are no
        // records that fit the criteria of category and type.
        // Returns a String[][] of length 0 if that is the case.
        if (myArray.length == 0) {
            String[][] returnString = new String[0][0];
            return returnString;
        }

        return myArray;

    }

}
