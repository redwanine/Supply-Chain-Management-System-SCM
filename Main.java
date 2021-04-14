/**
 @author Redwanul Islam <a href="mailto:redwanul.islam@ucalgary.ca">redwanul.islam@ucalgary.ca</a>
 @author Mubashir Rahman <a href="mailto:mubashir.rahman@ucalgary.ca">mubashir.rahman@ucalgaru.ca</a>
 @author Hasan Mahtab <a href="mailto:hasan.mahtab@ucalgary.ca">hasan.mahtab@ucalgary.ca</a>
 @author Arafatul Mamur <a "mailto:arafatul.mamur@ucalgary.ca">arafatul.mamur@ucalgary.ca</a>
 @version 1.3
 @since 1.0
 */

package edu.ucalgary.ensf409;

import java.io.*;
import java.util.*;

/*

GROUP 29 Members:
			Redwanul Islam - redwanul.islam@ucalgary.ca
			Mubashir Rahman - mubashir.rahman@ucalgaru.ca
			Hasan Mahtab - hasan.mahtab@ucalgary.ca
			Arafatul Mamur - arafatul.mamur@ucalgary.ca

How to compile and run our program:

1) Extract Group29.zip into an empty accessible directory. This can be done use WinRAR, 7-Zip or WinZip

2) The 'Group29' directory should contain a 'lib' folder.
   'lib' should contain 3 .jar files and 1 .sql file: 
				     			hamcrest-core-1.3.jar
				     			junit-4.13.2.jar
                        mysql-connector-java-8.0.23.jar
							   inventory.sql
					 
   This folder should also be the root folder for the package edu.ucalgary.ensf409,
   which contains all the code for this project.
   The 'Group29' directory should also contain 'Project_UML_Final.pdf', which is the UML Diagram of the project.
   The 'Group29' directory should also contain 'Demonstration Video - Group29.mp4', which is the demonstration video.
   The demonstration video can also be found @ https://youtu.be/9HY-LmWVHEg

3) 'edu/ucalgary/ensf409' should contain 3 .java files:
							Main.java - This file contians main() and is used as a runner to run the program as intended.
							FinalProject.java - This file contains the code required to fulfil the project requirements.
							FinalProjectTest.java - This file contains the code required to run unit tests on FinalProject.java

4) In order to run our unit tests as intended, make sure you have imported the default 'inventory.sql' found on D2l
   to the 'ensf409' user, which has the password 'scm'. The default inventory.sql can also be found the in the
   'lib' folder.

   The following command can be used to import 'inventory.sql' on the MySQL Command Line Client:
   
   	source C:\path\to\inventory.sql

5) Now, to run our program as intended, we must first compile it. 
   Open up cmd.exe, and navigate to the 'Group29' directory,
   then type the following command:

   	javac edu/ucalgary/ensf409/Main.java

   This command is used to compile the program. It should compile Main.java and FinalProject.java

6) Now type the following command to run the program:

	java -cp .;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/Main
	
   This command is used to run the program. 
   If an order is filled, 'orderform.txt' will be created and stored in the 'Group29' folder which contains 'lib' and 'edu' folders.

7) Now to run our unit tests, we must first compile it.
   Type the following command to compile our unit tests:

	javac -cp .;lib/junit-4.13.2.jar;hamcrest-core-1.3.jar edu/ucalgary/ensf409/FinalProjectTest.java

   This command is used to compile the unit tests. It should compile FinalProjectTest.java and FinalProject.java

8) Now type the following command to run the tests:

	java -cp .;lib/mysql-connector-java-8.0.23.jar;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore edu.ucalgary.ensf409.FinalProjectTest

   This command is used to run the unit tests.

*/

/**
 * The Main class was created and implemented to fulfil the requirements for ENSF 409 Final Project,
 * in accordance with the project handout. It is mainly used as a runner for the FinalProject class.
 * It implements a single method main() in order to fulfil the requirements of the project.
 */

public class Main{
    public static void main(String[] args) throws IOException{
        FinalProject myJDBC = new FinalProject("jdbc:mysql://localhost/inventory","ensf409","scm");
        
        //Used to establish connection between program and database
        boolean z = myJDBC.initializeConnection();

        //Checks if connection has been established
        if(z == false){
            return;
        }

        //Used to take inputs from the user
        Scanner myObj = new Scanner(System.in);

        System.out.println("Enter furniture category: ");
        String tempCategory = myObj.nextLine();
        System.out.println("Enter furniture type: ");
        String tempType = myObj.nextLine();
        System.out.println("Enter the number of furnitures requested: ");
        String tempNum = myObj.nextLine();
        

        myObj.close();

        //Used to check if the inputs are valid
        boolean a = false;
        a = myJDBC.inputHandling(tempCategory, tempType, tempNum);

        //Ends the program if inputs are invalid
        if(a == false){
            return;
        }

        String category = tempCategory.toLowerCase().trim();
        String type = tempType.toLowerCase().trim();
        int item = Integer.valueOf(tempNum.trim());
        
        //Calls databaseToArray()
        String[][] myArray = myJDBC.databaseToArray(type, category);

        //Calls calculation()
        String[] calcResults = myJDBC.calculation(myArray, item);

        //Calls filledUnfilled()
        boolean x = myJDBC.filledUnfilled(category, type, item, calcResults);

        return;
 
    }
}
