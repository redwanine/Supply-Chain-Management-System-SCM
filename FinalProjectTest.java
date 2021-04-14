/**
 @author Redwanul Islam <a href="mailto:redwanul.islam@ucalgary.ca">redwanul.islam@ucalgary.ca</a>
 @author Mubashir Rahman <a href="mailto:mubashir.rahman@ucalgary.ca">mubashir.rahman@ucalgaru.ca</a>
 @author Hasan Mahtab <a href="mailto:hasan.mahtab@ucalgary.ca">hasan.mahtab@ucalgary.ca</a>
 @author Arafatul Mamur <a "mailto:arafatul.mamur@ucalgary.ca">arafatul.mamur@ucalgary.ca</a>
 @version 1.9
 @since 1.0
*/

package edu.ucalgary.ensf409;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runners.MethodSorters;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

/**
 * The FinalProjectTest class was created and implemented to fulfil the requirements for ENSF 409 Final Project,
 * in accordance with the project handout. It implements various methods, all of which are used to fulfil the
 * requirements of the project. It is used to run various JUnit 4 tests, all of them should pass if the default
 * inventory.sql provided on D2l is used. All edge cases and standard cases have been taken into consideration.
 */
public class FinalProjectTest {

  public final static String USERNAME = "root";
  public final static String PASSWORD = "toor";
  public final static String DBURL = "jdbc:mysql://localhost/inventory";
  private Connection dbConnect;
  
  /**
   * Used to reset the Desk table to the default values provided in inventory.sql
   * @throws SQLException
   */
  public void desk() throws SQLException {
    try {
      initializeConnection();
      Statement myStmt1 = dbConnect.createStatement();
      myStmt1.executeUpdate("DROP TABLE IF EXISTS DESK");

      Statement myStmt2 = dbConnect.createStatement();
      myStmt2.executeUpdate("CREATE TABLE DESK ( ID char(5) not null, Type varchar(25), Legs char(1), Top char(1), Drawer char(1), Price integer, ManuID char(3), primary key (ID), foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE )");

      Statement myStmt3 = dbConnect.createStatement();
      myStmt3.executeUpdate("INSERT INTO DESK (ID, Type, Legs, Top, Drawer, Price, ManuID) VALUES ('D3820', 'Standing', 'Y', 'N', 'N', 150, '001'), ('D4475', 'Adjustable', 'N', 'Y', 'Y', 200, '002'), ('D0890', 'Traditional', 'N', 'N', 'Y', 25, '002'), ('D2341', 'Standing', 'N', 'Y', 'N', 100, '001'), ('D9387', 'Standing', 'Y', 'Y', 'N', 250, '004'), ('D7373', 'Adjustable', 'Y', 'Y', 'N', 350, '005'), ('D2746', 'Adjustable', 'Y', 'N', 'Y', 250, '004'), ('D9352', 'Traditional', 'Y', 'N', 'Y', 75, '002'), ('D4231', 'Traditional', 'N', 'Y', 'Y', 50, '005'), ('D8675', 'Traditional', 'Y', 'Y', 'N', 75, '001'), ('D1927', 'Standing', 'Y', 'N', 'Y', 200, '005'), ('D1030', 'Adjustable', 'N', 'Y', 'N', 150, '002'), ('D4438', 'Standing', 'N', 'Y', 'Y', 150, '004'), ('D5437', 'Adjustable', 'Y', 'N', 'N', 200, '001'), ('D3682', 'Adjustable', 'N', 'N', 'Y', 50, '005')");

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Used to empty the Desk table
   * @throws SQLException
   */
  public void emptyDesk() throws SQLException {
    try {
      initializeConnection();
      Statement myStmt1 = dbConnect.createStatement();
      myStmt1.executeUpdate("DROP TABLE IF EXISTS DESK");

      Statement myStmt2 = dbConnect.createStatement();
      myStmt2.executeUpdate("CREATE TABLE DESK ( ID char(5) not null, Type varchar(25), Legs char(1), Top char(1), Drawer char(1), Price integer, ManuID char(3), primary key (ID), foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE )");

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Used to reset the Lamp table to the default values provided in inventory.sql
   * @throws SQLException
   */
  public void lamp() throws SQLException {
    try {
      initializeConnection();
      Statement myStmt1 = dbConnect.createStatement();
      myStmt1.executeUpdate("DROP TABLE IF EXISTS LAMP");

      Statement myStmt2 = dbConnect.createStatement();
      myStmt2.executeUpdate("CREATE TABLE LAMP ( ID char(4) not null, Type varchar(25), Base char(1), Bulb char(1), Price integer, ManuID char(3), primary key (ID), foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE )");

      Statement myStmt3 = dbConnect.createStatement();
      myStmt3.executeUpdate("INSERT INTO LAMP (ID, Type, Base, Bulb, Price, ManuID) VALUES ('L132', 'Desk', 'Y', 'N', 18, '005'), ('L980', 'Study', 'N', 'Y', 2, '004'), ('L487', 'Swing Arm', 'Y', 'N', 27, '002'), ('L564', 'Desk', 'Y', 'Y', 20, '004'), ('L342', 'Desk', 'N', 'Y', 2, '002'), ('L982', 'Study', 'Y', 'N', 8, '002'), ('L879', 'Swing Arm', 'N', 'Y', 3, '005'), ('L208', 'Desk', 'N', 'Y', 2, '005'), ('L223', 'Study', 'N', 'Y', 2, '005'), ('L928', 'Study', 'Y', 'Y', 10, '002'), ('L013', 'Desk', 'Y', 'N', 18, '004'), ('L053', 'Swing Arm', 'Y', 'N', 27, '002'), ('L112', 'Desk', 'Y', 'N', 18, '005'), ('L649', 'Desk', 'Y', 'N', 18, '004'), ('L096', 'Swing Arm', 'N', 'Y', 3, '002')");

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

    /**
     * Used to reset the Chair table to the default values provided in inventory.sql
     * @throws SQLException
     */
  public void chair() throws SQLException {
    try {
      initializeConnection();
      Statement myStmt1 = dbConnect.createStatement();
      myStmt1.executeUpdate("DROP TABLE IF EXISTS CHAIR");

      Statement myStmt2 = dbConnect.createStatement();
      myStmt2.executeUpdate("CREATE TABLE CHAIR ( ID char(5) not null, Type varchar(25), Legs char(1), Arms char(1), Seat char(1), Cushion char(1), Price integer, ManuID char(3), primary key (ID), foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE )");

      Statement myStmt3 = dbConnect.createStatement();
      myStmt3.executeUpdate("INSERT INTO CHAIR (ID, Type, Legs, Arms, Seat, Cushion, Price, ManuID) VALUES ('C1320', 'Kneeling', 'Y', 'N', 'N', 'N', 50, '002'), ('C3405', 'Task', 'Y', 'Y', 'N', 'N', 100, '003'), ('C9890', 'Mesh', 'N', 'Y', 'N', 'Y', 50, '003'), ('C7268', 'Executive', 'N', 'N', 'Y', 'N', 75, '004'), ('C0942', 'Mesh', 'Y', 'N', 'Y', 'Y', 175, '005'), ('C4839', 'Ergonomic', 'N', 'N', 'N', 'Y', 50, '002'), ('C2483', 'Executive', 'Y', 'Y', 'N', 'N', 175, '002'), ('C5789', 'Ergonomic', 'Y', 'N', 'N', 'Y', 125, '003'), ('C3819', 'Kneeling', 'N', 'N', 'Y', 'N', 75, '005'), ('C5784', 'Executive', 'Y', 'N', 'N', 'Y', 150, '004'), ('C6748', 'Mesh', 'Y', 'N', 'N', 'N', 75, '003'), ('C0914', 'Task', 'N', 'N', 'Y', 'Y', 50, '002'), ('C1148', 'Task', 'Y', 'N', 'Y', 'Y', 125, '003'), ('C5409', 'Ergonomic', 'Y', 'Y', 'Y', 'N', 200, '003'), ('C8138', 'Mesh', 'N', 'N', 'Y', 'N', 75, '005')");

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Used to reset the Filing table to the default values provided in inventory.sql
   * @throws SQLException
   */
  public void filing() throws SQLException {
    try {
      initializeConnection();
      Statement myStmt1 = dbConnect.createStatement();
      myStmt1.executeUpdate("DROP TABLE IF EXISTS FILING");

      Statement myStmt2 = dbConnect.createStatement();
      myStmt2.executeUpdate("CREATE TABLE FILING ( ID char(4) not null, Type varchar(25), Rails char(1), Drawers char(1), Cabinet char(1), Price integer, ManuID char(3), primary key (ID), foreign key (ManuID) references MANUFACTURER(ManuID) ON UPDATE CASCADE )");

      Statement myStmt3 = dbConnect.createStatement();
      myStmt3.executeUpdate("INSERT INTO FILING (ID, Type, Rails, Drawers, Cabinet, Price, ManuID) VALUES ('F001', 'Small', 'Y', 'Y', 'N', 50, '005'), ('F002', 'Medium', 'N', 'N', 'Y', 100, '004'), ('F003', 'Large', 'N', 'N', 'Y', 150, '002'), ('F004', 'Small', 'N', 'Y', 'Y', 75, '004'), ('F005', 'Small', 'Y', 'N', 'Y', 75, '005'), ('F006', 'Small', 'Y', 'Y', 'N', 50, '005'), ('F007', 'Medium', 'N', 'Y', 'Y', 150, '002'), ('F008', 'Medium', 'Y', 'N', 'N', 50, '005'), ('F009', 'Medium', 'Y', 'Y', 'N', 100, '004'), ('F010', 'Large', 'Y', 'N', 'Y', 225, '002'), ('F011', 'Large', 'N', 'Y', 'Y', 225, '005'), ('F012', 'Large', 'N', 'Y', 'N', 75, '005'), ('F013', 'Small', 'N', 'N', 'Y', 50, '002'), ('F014', 'Medium', 'Y', 'Y', 'Y', 200, '002'), ('F015', 'Large', 'Y', 'N', 'N', 75, '004')");

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
  /**
   * Checks if the constructor initializes all the values correctly 
   * CASE: The connection is established successfully
   * @throws IOException
   */
  @Test
  public void testConstructor() throws IOException {
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    Assert.assertTrue("DBURL is wrong", testObject.getDburl().equals(DBURL));
    assertTrue("USERNAME is wrong", testObject.getUsername().equals(USERNAME));
    assertTrue("PASSWORD is wrong", testObject.getPassword().equals(PASSWORD));
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayTraditionalDesk() throws IOException, SQLException {
    String type = "Traditional";
    String category = "Desk";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    desk();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "D0890",
        "N",
        "N",
        "Y",
        "25"
      },
      {
        "D4231",
        "N",
        "Y",
        "Y",
        "50"
      },
      {
        "D8675",
        "Y",
        "Y",
        "N",
        "75"
      },
      {
        "D9352",
        "Y",
        "N",
        "Y",
        "75"
      }
    };
    desk();
    assertEquals("databaseToArray does not return the expected 2D Array for Traditional Desk", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayStandingDesk() throws IOException, SQLException {
    String type = "Standing";
    String category = "Desk";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "D1927",
        "Y",
        "N",
        "Y",
        "200"
      },
      {
        "D2341",
        "N",
        "Y",
        "N",
        "100"
      },
      {
        "D3820",
        "Y",
        "N",
        "N",
        "150"
      },
      {
        "D4438",
        "N",
        "Y",
        "Y",
        "150"
      },
      {
        "D9387",
        "Y",
        "Y",
        "N",
        "250"
      }
    };
    desk();
    assertEquals("databaseToArray does not return the expected 2D Array for Standing Desk", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayAdjustableDesk() throws IOException, SQLException {
    String type = "Adjustable";
    String category = "Desk";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "D1030",
        "N",
        "Y",
        "N",
        "150"
      },
      {
        "D2746",
        "Y",
        "N",
        "Y",
        "250"
      },
      {
        "D3682",
        "N",
        "N",
        "Y",
        "50"
      },
      {
        "D4475",
        "N",
        "Y",
        "Y",
        "200"
      },
      {
        "D5437",
        "Y",
        "N",
        "N",
        "200"
      },
      {
        "D7373",
        "Y",
        "Y",
        "N",
        "350"
      }
    };
    desk();
    assertEquals("databaseToArray does not return the expected 2D Array for Adjustable Desk", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArraySmallFiling() throws IOException, SQLException {
    String type = "Small";
    String category = "Filing";
    filing();
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "F001",
        "Y",
        "Y",
        "N",
        "50"
      },
      {
        "F004",
        "N",
        "Y",
        "Y",
        "75"
      },
      {
        "F005",
        "Y",
        "N",
        "Y",
        "75"
      },
      {
        "F006",
        "Y",
        "Y",
        "N",
        "50"
      },
      {
        "F013",
        "N",
        "N",
        "Y",
        "50"
      }
    };
    filing();
    assertEquals("databaseToArray does not return the expected 2D Array for Small Filing", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayMediumFiling() throws IOException, SQLException {
    String type = "Medium";
    String category = "Filing";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "F002",
        "N",
        "N",
        "Y",
        "100"
      },
      {
        "F007",
        "N",
        "Y",
        "Y",
        "150"
      },
      {
        "F008",
        "Y",
        "N",
        "N",
        "50"
      },
      {
        "F009",
        "Y",
        "Y",
        "N",
        "100"
      },
      {
        "F014",
        "Y",
        "Y",
        "Y",
        "200"
      }
    };
    filing();
    assertEquals("databaseToArray does not return the expected 2D Array for Medium Filing", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayLargeFiling() throws IOException, SQLException {
    String type = "Large";
    String category = "Filing";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "F003",
        "N",
        "N",
        "Y",
        "150"
      },
      {
        "F010",
        "Y",
        "N",
        "Y",
        "225"
      },
      {
        "F011",
        "N",
        "Y",
        "Y",
        "225"
      },
      {
        "F012",
        "N",
        "Y",
        "N",
        "75"
      },
      {
        "F015",
        "Y",
        "N",
        "N",
        "75"
      }
    };
    filing();
    assertEquals("databaseToArray does not return the expected 2D Array for Large Filing", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayTaskChair() throws IOException, SQLException {
    String type = "Task";
    String category = "Chair";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    chair();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "C0914",
        "N",
        "N",
        "Y",
        "Y",
        "50"
      },
      {
        "C1148",
        "Y",
        "N",
        "Y",
        "Y",
        "125"
      },
      {
        "C3405",
        "Y",
        "Y",
        "N",
        "N",
        "100"
      }
    };
    chair();
    assertEquals("databaseToArray does not return the expected 2D Array for Task Chair", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayMeshChair() throws IOException, SQLException {
    String type = "Mesh";
    String category = "Chair";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "C0942",
        "Y",
        "N",
        "Y",
        "Y",
        "175"
      },
      {
        "C6748",
        "Y",
        "N",
        "N",
        "N",
        "75"
      },
      {
        "C8138",
        "N",
        "N",
        "Y",
        "N",
        "75"
      },
      {
        "C9890",
        "N",
        "Y",
        "N",
        "Y",
        "50"
      }
    };
    chair();
    assertEquals("databaseToArray does not return the expected 2D Array for Mesh Chair", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayKneelingChair() throws IOException, SQLException {
    String type = "Kneeling";
    String category = "Chair";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "C1320",
        "Y",
        "N",
        "N",
        "N",
        "50"
      },
      {
        "C3819",
        "N",
        "N",
        "Y",
        "N",
        "75"
      }
    };
    chair();
    assertEquals("databaseToArray does not return the expected 2D Array for Kneeling Chair", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayErgonomicChair() throws IOException, SQLException {
    String type = "Ergonomic";
    String category = "Chair";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "C4839",
        "N",
        "N",
        "N",
        "Y",
        "50"
      },
      {
        "C5409",
        "Y",
        "Y",
        "Y",
        "N",
        "200"
      },
      {
        "C5789",
        "Y",
        "N",
        "N",
        "Y",
        "125"
      }
    };

    assertEquals("databaseToArray does not return the expected 2D Array for Ergonomic Chair", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayExecutiveChair() throws IOException, SQLException {
    String type = "Executive";
    String category = "Chair";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "C2483",
        "Y",
        "Y",
        "N",
        "N",
        "175"
      },
      {
        "C5784",
        "Y",
        "N",
        "N",
        "Y",
        "150"
      },
      {
        "C7268",
        "N",
        "N",
        "Y",
        "N",
        "75"
      }
    };
    chair();
    assertEquals("databaseToArray does not return the expected 2D Array for Executive Chair", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayDeskLamp() throws IOException, SQLException {
    String type = "Desk";
    String category = "Lamp";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    lamp();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "L013",
        "Y",
        "N",
        "18"
      },
      {
        "L112",
        "Y",
        "N",
        "18"
      },
      {
        "L132",
        "Y",
        "N",
        "18"
      },
      {
        "L208",
        "N",
        "Y",
        "2"
      },
      {
        "L342",
        "N",
        "Y",
        "2"
      },
      {
        "L564",
        "Y",
        "Y",
        "20"
      },
      {
        "L649",
        "Y",
        "N",
        "18"
      }
    };
    lamp();
    assertEquals("databaseToArray does not return the expected 2D Array for Desk Lamp", result, expected);
  }
  
  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArraySwingArmLamp() throws IOException, SQLException {
    String type = "Swing Arm";
    String category = "Lamp";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "L053",
        "Y",
        "N",
        "27"
      },
      {
        "L096",
        "N",
        "Y",
        "3"
      },
      {
        "L487",
        "Y",
        "N",
        "27"
      },
      {
        "L879",
        "N",
        "Y",
        "3"
      }
    };
    lamp();
    assertEquals("databaseToArray does not return the expected 2D Array for Swing Arm Lamp", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided.
   * This 2D String is then matched with our expected output to complete the testing.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayStudyLamp() throws IOException, SQLException {
    String type = "Study";
    String category = "lamp";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = {
      {
        "L223",
        "N",
        "Y",
        "2"
      },
      {
        "L928",
        "Y",
        "Y",
        "10"
      },
      {
        "L980",
        "N",
        "Y",
        "2"
      },
      {
        "L982",
        "Y",
        "N",
        "8"
      }
    };
    lamp();
    assertEquals("databaseToArray does not return the expected 2D Array for Study Lamp", result, expected);
  }

  /**
   * This test calls the databaseToArray method by using the type and category provided, it then returns a 2D String array containing all the IDs,
   * Y/N fields and the price from the category table, where the Type is the type provided. This 2D String is then matched with our
   * expected output to complete the testing.
   * 
   * SPECIAL CASE: In this case there are no records in the DESK Table, therefore databaseToArray will return a 2D String array of length 0.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDatabaseToArrayEmptyTable() throws IOException, SQLException {
    emptyDesk();
    String type = "Traditional";
    String category = "desk";
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    String[][] result = testObject.databaseToArray(type, category);
    String[][] expected = new String[0][0];
    desk();
    assertEquals("databaseToArray does not return the expected 2D Array for Traditional Desk", result.length, expected.length);
  }

  /**
   * This test calls the calculation method by using the 2D Array containing all the records for
   * a particlar category and type and the number of furnitures requested,
   * it then returns a 1D String array containing all the IDs and the price for the cheapest combination.
   * This 1D String is then matched with our expected output to complete the testing.
   * CASE: There is enough stock to fill the whole order. Furnitures Requested: 1
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testCalculationTraditionalDesk() throws IOException, SQLException {
    int numFur = 1;
    String[][] myArray = {
      {
        "D0890",
        "N",
        "N",
        "Y",
        "25"
      },
      {
        "D4231",
        "N",
        "Y",
        "Y",
        "50"
      },
      {
        "D8675",
        "Y",
        "Y",
        "N",
        "75"
      },
      {
        "D9352",
        "Y",
        "N",
        "Y",
        "75"
      }
    };
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    desk();
    String[] result = testObject.calculation(myArray, numFur);
    String[] expected = {
      "D0890",
      "D8675",
      "100.0"
    };
    desk();
    assertEquals("calculation does not return the expected 1D Array", expected, result);
  }

  /**
   * This test calls the calculation method by using the 2D Array containing all the records for
   * a particlar category and type and the number of furnitures requested,
   * it then returns a 1D String array containing all the IDs and the price for the cheapest combination.
   * This 1D String is then matched with our expected output to complete the testing.
   * CASE: There is enough stock to fill the whole order. Furnitures Requested: 2
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testCalculationTwoTraditionalDesk() throws IOException, SQLException {
    int numFur = 2;
    String[][] myArray = {
      {
        "D0890",
        "N",
        "N",
        "Y",
        "25"
      },
      {
        "D4231",
        "N",
        "Y",
        "Y",
        "50"
      },
      {
        "D8675",
        "Y",
        "Y",
        "N",
        "75"
      },
      {
        "D9352",
        "Y",
        "N",
        "Y",
        "75"
      }
    };
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    desk();
    String[] result = testObject.calculation(myArray, numFur);
    String[] expected = {
      "D4231",
      "D8675",
      "D9352",
      "200.0"
    };
    desk();
    assertEquals("calculation does not return the expected 1D Array", expected, result);
  }

  /**
   * This test calls the calculation method by using the 2D Array containing all the records for
   * a particlar category and type and the number of furnitures requested,
   * it then returns a 1D String array containing all the IDs and the price for the cheapest combination.
   * This 1D String is then matched with our expected output to complete the testing.
   * CASE: There is not enough stock to fill the whole order. Furnitures Requested: 3
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testCalculationThreeTraditionalDesk() throws IOException, SQLException {
    int numFur = 3;
    String[][] myArray = {
      {
        "D0890",
        "N",
        "N",
        "Y",
        "25"
      },
      {
        "D4231",
        "N",
        "Y",
        "Y",
        "50"
      },
      {
        "D8675",
        "Y",
        "Y",
        "N",
        "75"
      },
      {
        "D9352",
        "Y",
        "N",
        "Y",
        "75"
      }
    };
    FinalProject testObject = new FinalProject(DBURL, USERNAME, PASSWORD);
    testObject.initializeConnection();
    desk();
    String[] result = testObject.calculation(myArray, numFur);
    String[] expected = new String[0];
    desk();
    assertEquals("calculation does not return the expected 1D Array", expected, result);
  }

  /**
   * Tests if the database gets updated by removing the record(s) which match the ID(s) provided in the array returned by calculation method.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testDeleteRecord() throws IOException, SQLException {
    String[] myArray = {
      "L982",
      "L223",
      "10"
    };
    String type = "Study";
    String category = "lamp";
    FinalProject testObj = new FinalProject(DBURL, USERNAME, PASSWORD);
    boolean temp = testObj.initializeConnection();
    testObj.deleteRecord(myArray, category);
    String[][] result = testObj.databaseToArray(type, category);
    String[][] expected = {
      {
        "L928",
        "Y",
        "Y",
        "10"
      },
      {
        "L980",
        "N",
        "Y",
        "2"
      }
    };
    lamp();
    assertEquals("deleteRecord() does not remove the appropriate records. Records with IDs L928 and L980 should have been removed", result, expected);
  }

  /**
   * Tests if all elements in a 2D int Array are 1, returns true if so, otherwise returns false.
   * CASE: All elements in the 2D int Array are 1.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testAreAllOneTrue() throws IOException, SQLException {
    int[][] flag = {
      {
        1,
        1,
        1
      },
      {
        1,
        1,
        1
      },
      {
        1,
        1,
        1
      }
    };

    FinalProject testObj = new FinalProject(DBURL, USERNAME, PASSWORD);
    boolean temp = testObj.initializeConnection();
    boolean result = testObj.areAllOne(flag);
    boolean expected = true;
    assertEquals("areAllOne() does not return true. It should, as all elements in the flag array are 1", result, expected);
  }

  /**
   * Tests if all elements in a 2D int Array are 1, returns true if so, otherwise returns false.
   * CASE: All elements in the 2D int Array are not 1.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testAreAllOneFalse() throws IOException, SQLException {
    int[][] flag = {
      {
        1,
        1,
        1
      },
      {
        1,
        0,
        1
      },
      {
        1,
        0,
        0
      }
    };

    FinalProject testObj = new FinalProject(DBURL, USERNAME, PASSWORD);
    boolean temp = testObj.initializeConnection();
    boolean result = testObj.areAllOne(flag);
    boolean expected = false;
    assertEquals("areAllOne() does not return false. It should, as all elements in the flag array are not 1", result, expected);
  }

  /**
   * Tests if the inputHandling() method correctly deals with inputs by the user that are invalid.
   * CASE: The category entered is invalid.
   * In this case, inputHandling() should throw an IllegalArgumentException with the message "The category entered is not valid".
   * @throws SQLException
   */
  @Test
  public void testInputHandlingInvalidCategory() throws SQLException{
    String category = "notValid";
    String type = "Task";
    String item = "1";
    FinalProject testObj = new FinalProject(DBURL, USERNAME, PASSWORD);
    boolean temp = testObj.initializeConnection();
    chair();
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> testObj.inputHandling(category, type, item));
    assertEquals("The correct exception along with message has not been thrown when the category entered is invalid", exception.getMessage(), "The category entered is not valid");
  }

  /**
   * Tests if the inputHandling() method correctly deals with inputs by the user that are invalid.
   * CASE: The type entered is not valid.
   * In this case, inputHandling() should throw an IllegalArgumentException with the message the type entered is not valid.
   * @throws SQLException
   */
  @Test
  public void testInputHandlingInvalidType() throws SQLException{
    String category = "chair";
    String type = "notValid";
    String item = "1";
    FinalProject testObj = new FinalProject(DBURL, USERNAME, PASSWORD);
    boolean temp = testObj.initializeConnection();
    chair();
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> testObj.inputHandling(category, type, item));
    assertEquals("The correct exception along with message has not been thrown when the type entered is not valid",
     exception.getMessage(), "The type entered is not valid");
  }

  /**
   * Tests if the inputHandling() method correctly deals with inputs by the user that are invalid.
   * CASE: The item value entered is 0.
   * In this case, inputHandling() should throw an IllegalArgumentException with the message "Number of furnitures ordered must be greater than or equal to 1".
   * @throws SQLException
   */
  @Test
  public void testInputHandlingInvalidItem() throws SQLException{
    String category = "chair";
    String type = "Task";
    String item = "0";
    FinalProject testObj = new FinalProject(DBURL, USERNAME, PASSWORD);
    boolean temp = testObj.initializeConnection();
    chair();
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> testObj.inputHandling(category, type, item));
    assertEquals("The correct exception along with message has not been thrown when the number of furnitures to be purchased is 0",
     exception.getMessage(), "Number of furnitures ordered must be greater than or equal to 1");
  }

  /**
   * Tests if the inputHandling() method correctly deals with inputs by the user that are invalid.
   * CASE: The item value entered is not a valid. One cannot purchase 0.5 of an item.
   * In this case, inputHandling() should throw an IllegalArgumentException with the message "The input for number of furnitures requested is not valid".
   * @throws SQLException
   */
  @Test
  public void testInputHandlingInvalidItemInteger() throws SQLException{
    String category = "chair";
    String type = "Task";
    String item = "0.5";
    FinalProject testObj = new FinalProject(DBURL, USERNAME, PASSWORD);
    boolean temp = testObj.initializeConnection();
    chair();
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> testObj.inputHandling(category, type, item));
    assertEquals("The correct exception along with message has not been thrown when the number of furnitures to be purchased is not valid",
     exception.getMessage(), "The input for number of furnitures requested is not valid");
  }

  /**
   * Tests if the orderUnfilled() method correctly deals with input String category and outputs the right message depending on the MANUFACTURER table.
   * CASE: The category is "chair"
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testOrderUnfilled() throws IOException, SQLException{
    String category = "chair";
    FinalProject testObj = new FinalProject(DBURL, USERNAME, PASSWORD);
    boolean temp = testObj.initializeConnection();
    chair();
    String result = testObj.orderUnfilled(category);
    String expected = "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Chairs R Us, Furniture Goods, Fine Office Supplies.";
    assertEquals("orderUnfilled() does not output & return the correct message when the whole order cannot be filled", expected, result);
  }

  /**
   * Tests if the fileControl() method correctly creates the expected orderform for the arguments provided.
   * CASE: The category is "chair", The type is "Task", The number of items is 1.
   * @throws IOException
   * @throws SQLException
   */
  @Test
  public void testFileControl() throws IOException, SQLException{
    String category = "chair";
    String type = "Task";
    int item  = 1;
    String[] itemIdPrice = {"C0914", "C3405", "150.0"};
    FinalProject testObj = new FinalProject(DBURL, USERNAME, PASSWORD);
    boolean temp = testObj.initializeConnection();
    chair();
    testObj.fileControl(type, category, item, itemIdPrice);
    String result = new String(Files.readAllBytes(Paths.get("orderform.txt")));
    String expected = "Furniture Order Form\n\nFaculty Name:\nContact:\nDate\n\nOriginal Request: task chair, 1\n\nItems Ordered\nID: C0914\nID: C3405\n\nTotal Price: $150.0";
    assertEquals("fileControl() does not create the expected orderform for the arguments provided", expected, result);
  }

  /**
   * This method establishes a connection between the java file and the database
   */
  public void initializeConnection() {
    try {
      dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
