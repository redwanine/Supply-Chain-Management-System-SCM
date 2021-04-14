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
    
     /**
     * This method deletes the records from the database, the IDs of which correspond to the IDs provided in the myArray[] parameter. 
     * @param myArray String array containing IDs of the records to be deleted from the database and the price of the cheapest combination
     * @param category The name of the table from the database
     */
    public void deleteRecord(String[] myArray, String category){
        //Used to put all the IDs from myArray into a String[]. Essentially getting rid of the price.
        String[] itemID = new String[myArray.length-1];
        for(int x = 0; x < itemID.length; x++){
            itemID[x] = myArray[x];
        }

        //Used to loop through itemID and delete the record from the database corresponding to the IDs.
        for(int x = 0; x < itemID.length; x++){

            String queryString = "DELETE FROM " + category + " WHERE ID = '" + itemID[x] + "'";

            try{
                Statement myStmt = dbConnect.createStatement();
                myStmt.executeUpdate(queryString);
    
                myStmt.close();
            } catch(SQLException ex){
                ex.printStackTrace();
            }
        }

        return;

    }

    /**
     * Returns a string containing a message and names of the manufacturers which produce furniture of a certain category(table). This string is initially printed to the terminal.
     * This method is called when an order cannot be filled.
     * @param category The name of the table from the database
     * @return String containing a message and names of manufacturers
     */
    public String orderUnfilled(String category){

        //ArrayList manuNames is used to store the names of all the manufacturers of a certain category of furniture.
        List<String> manuNames = new ArrayList<String>();

        if(category.toLowerCase().equals("chair")){
            manuNames = new ArrayList<String>(Arrays.asList(getChairManufacturers()));
        } else if(category.toLowerCase().equals("desk")){
            manuNames = new ArrayList<String>(Arrays.asList(getDeskManufacturers()));
        } else if(category.toLowerCase().equals("lamp")){
            manuNames = new ArrayList<String>(Arrays.asList(getLampManufacturers()));
        } else if(category.toLowerCase().equals("filing")){
            manuNames = new ArrayList<String>(Arrays.asList(getFilingManufacturers()));
        }
        
        //StringBuilder sb is used to form a String containing a message telling that the order cannot be filled.
        //Moreover, manuNames is looped through and added to sb.
        StringBuilder sb = new StringBuilder();
        sb.append("Order cannot be fulfilled based on current inventory. Suggested manufacturers are ");
        for(int x  = 0; x < manuNames.size(); x++){
            sb.append(manuNames.get(x) + ", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        sb.append(".");

        //String tempString is created from sb and is printed to the terminal and returned.
        
        String tempString = sb.toString();
        System.out.println(tempString);
        return tempString;
    }

    /**
     * Returns a boolean value of true if all the elements in the paramater flagArray[][] are 1s, otherwise returns 0.
     * This method is used by calculation() method in order to figure out if enough items have been picked to fill an order.
     * @param flagArray int[][] which is tested by the method to find out if all the 
     * @return boolean value of of true if all elements are 1, otherwise 0.
     */
    public boolean areAllOne(int[][] flagArray) {
        
        //Loop through flagArray[][] to check if all elements are not 1.
        //If true, returns false, otherwise returns true.
        int expectedFlag = 1;
      
        for (int[] flags: flagArray){
          for (int flag: flags){
            if(flag != expectedFlag){
              return false;
            }
          }
        }
        return true;
      }

    /**
     * Returns a String[] containing the IDs of the cheapest combination of items to fill an order, along with the total price if the whole order can be filled.
     * Otherwise return a String[] of length 0.
     * First the method checks if the length of myArray[][] is 
     * @param myArray String[][] containing data pulled by databasetoArray()
     * @param numFur Number of furnitures requested
     * @return String[] containing IDs of cheapest combination to fill an order and the total price.
     */
    public String[] calculation(String[][] myArray, int numFur){

        //Tests if the length of myArray[][] is 0, returns a String[] of length 0 if true.
        if(myArray.length == 0){
            String[] returnString = new String[0];
            return returnString;
        }

        ArrayList<String> IDs = new ArrayList<String>();
        ArrayList<String> combinationArray = new ArrayList<String>();
        
        int numRow = myArray.length;
        int numCol = myArray[0].length;

        String[] indexArray = new String[numRow];
       
        //Used to add the number of row numbers into indexArray
        for(int x = 0; x < numRow; x++){
            indexArray[x] = String.valueOf(x);
        }

        //Used to create and add all the possible combinations for the number of rows and columns in myArray[][] to combinationArray
        for (int i = 1, max = 1 << indexArray.length; i < max; ++i) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0, k = 1; j < indexArray.length; ++j, k <<= 1){
                if ((k & i) != 0){
                    sb.append(indexArray[j] + " ");

                }    
            }
            sb.deleteCharAt(sb.length()-1);
            combinationArray.add(sb.toString());
        }

        /*
        Checks if each combination in combinationArray is a viable option by making use of an int[][] flagArray.
        The element in the flagArray is changed to 1, if the corresponding element in myArray is "Y".
        StringBuilder sb is used to temporarily store the IDs with "~" used as a delimiter.
        */
        for (int i = 0; i < combinationArray.size(); i++){ 
            int[][] flagArray = new int[numFur][numCol - 2];
            String[] str = combinationArray.get(i).split(" ");
            ArrayList<Integer> al = new ArrayList<Integer>();
            for (int z = 0; z < str.length; z++){
                al.add(Integer.parseInt(str[z]));
            }
            StringBuilder sb = new StringBuilder();
            if (numCol == 6){
                for(int s: al){
                    boolean addFlag = false;
                    if (myArray[s][1].equals("Y")){
                        for (int x = 0; x < flagArray.length; x++){
                            if (flagArray[x][0] == 1){
                                continue;
                            } else {
                                flagArray[x][0] = 1;
                                if (addFlag == false){
                                    sb.append(myArray[s][0] + "~");
                                    addFlag = true;
                                }
                                break;
                            }
                        }
                    }
                    if (myArray[s][2].equals("Y")){
                        for (int x = 0; x < flagArray.length; x++){
                            if (flagArray[x][1] == 1){
                                continue;
                            } else {
                                flagArray[x][1] = 1;
                                if (addFlag == false){
                                    sb.append(myArray[s][0] + "~");
                                    addFlag = true;
                                }
                                break;
                            }
                        }
                        
                        
                    }
                    if (myArray[s][3].equals("Y")){
                        for (int x = 0; x < flagArray.length; x++){
                            if (flagArray[x][2] == 1){
                                continue;
                            } else {
                                flagArray[x][2] = 1;
                                if (addFlag == false){
                                    sb.append(myArray[s][0] + "~");
                                    addFlag = true;
                                }
                                break;
                            }
                        }
                        
                        
                    }
                    if (myArray[s][4].equals("Y")){
                        for (int x = 0; x < flagArray.length; x++){
                            if (flagArray[x][3] == 1){
                                continue;
                            } else {
                                flagArray[x][3] = 1;
                                if (addFlag == false){
                                    sb.append(myArray[s][0] + "~");
                                    addFlag = true;
                                }
                                break;
                            }
                        }
                        
                        
                    }
                }
            } else if (numCol == 5){
                for(int s: al){
                    boolean addFlag = false;
                    if (myArray[s][1].equals("Y")){
                        for (int x = 0; x < flagArray.length; x++){
                            if (flagArray[x][0] == 1){
                                continue;
                            } else {
                                flagArray[x][0] = 1;
                                if (addFlag == false){
                                    sb.append(myArray[s][0] + "~");
                                    addFlag = true;
                                }
                                break;
                            }
                        }
                        
                    }
                    if (myArray[s][2].equals("Y")){
                        for (int x = 0; x < flagArray.length; x++){
                            if (flagArray[x][1] == 1){
                                continue;
                            } else {
                                flagArray[x][1] = 1;
                                if (addFlag == false){
                                    sb.append(myArray[s][0] + "~");
                                    addFlag = true;
                                }
                                break;
                            }
                        }
                        
                        
                    }
                    if (myArray[s][3].equals("Y")){
                        for (int x = 0; x < flagArray.length; x++){
                            if (flagArray[x][2] == 1){
                                continue;
                            } else {
                                flagArray[x][2] = 1;
                                if (addFlag == false){
                                    sb.append(myArray[s][0] + "~");
                                    addFlag = true;
                                }
                                break;
                            }
                        }
                        
                        
                    }
                }
            } else if (numCol == 4){
                for(int s: al){
                    boolean addFlag = false;
                    if (myArray[s][1].equals("Y")){
                        for (int x = 0; x < flagArray.length; x++){
                            if (flagArray[x][0] == 1){
                                continue;
                            } else {
                                flagArray[x][0] = 1;
                                if (addFlag == false){
                                    sb.append(myArray[s][0] + "~");
                                    addFlag = true;
                                }
                                break;
                            }
                        }
                        
                    }
                    if (myArray[s][2].equals("Y")){
                        for (int x = 0; x < flagArray.length; x++){
                            if (flagArray[x][1] == 1){
                                continue;
                            } else {
                                flagArray[x][1] = 1;
                                if (addFlag == false){
                                    sb.append(myArray[s][0] + "~");
                                    addFlag = true;
                                }
                                break;
                            }
                        }
                        
                        
                    }

                }
            }

            //Test to check if StringBuilder sb is null, which indicates the current combination question is not a viable option
            if(!sb.toString().equals(null)){
                //Tests if all elements in flag array is 1, indicating that the current combination fulfills the order. 
                //This doesn't indiacte that its the cheapest option though.
                if (areAllOne(flagArray) == true){
                    //sb is added to IDs arraylist
                    IDs.add(sb.toString());
                }
            }
           
                
        }

        //If the order cannot be fulfilled, the method returns a String[] of length 0.
        if(IDs.size() == 0){
            String[] returnString = new String[0];
            return returnString;
        }

        //Used to remove duplicate combinations
        List<String> deduped = IDs.stream().distinct().collect(Collectors.toList());

        ArrayList<Double> priceList = new ArrayList<Double>();

        /*
        Used to loop through deduped, add and store the prices of all IDs in each index into
        each index of priceList arrayList.
        */
        for(int x = 0; x < deduped.size(); x++){
            deduped.set(x, deduped.get(x).substring(0, deduped.get(x).length()-1));
            String[] tempString = deduped.get(x).split("~");
            double tempPrice = 0;
            for(int y = 0; y < tempString.length; y++){
                
                for(int z = 0; z < myArray.length; z++){
                    if (myArray[z][0].equals(tempString[y])){
                        tempPrice += Double.valueOf(myArray[z][myArray[0].length - 1]);
                    }
                }
                
                
            }
            priceList.add(tempPrice);
        }

        //Used to find the cheapest price in priceList and the corresponding ID(s).
        int indexOfMinimum = priceList.indexOf(Collections.min(priceList));
        String[] tempReturnString = deduped.get(indexOfMinimum).split("~");
        String[] returnString = new String[tempReturnString.length + 1];
        
        //Used to store all the ID values for the cheapest combination into returnString[]
        for(int x = 0; x < tempReturnString.length; x++){
            returnString[x] = tempReturnString[x];
        }

        //Used to append the total price of the cheapest combinations to the end of returnString[]
        returnString[returnString.length - 1] = String.valueOf(Collections.min(priceList));

        return returnString;
    }

    /**
     * Returns a boolean value, true if the order is not filled and false if the order is filled
     * It figures this out by checking the length of calcResults[].
     * The length of calcResults passed to the method is 0 if there is no possible combination to fill the order
     * @param category The name of the table from the database
     * @param type The type of furniture requested
     * @param item Number of furnitures requested
     * @param calcResults String[] containing the IDs of the cheapest combination and their total price
     * @return boolean value, true if the order is not filled and false otherwise.
     * @throws IOException
     */
    public boolean filledUnfilled(String category, String type, int item, String[] calcResults) throws IOException{
        //Checks if the length of calcResults is 0, if true it indicates that the order cannot be filled so orderUnfilled() is called.
        //Otherwise deleteRecord() is called, followed by fileControl(). To delete the required records and create the orderform.
        if(calcResults.length == 0){
            String x = orderUnfilled(category);
            return true;
        } else {
            deleteRecord(calcResults, category);
            fileControl(type, category, item, calcResults);
            return false;
        }
    }
}

