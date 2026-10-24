import java.sql.*;
import java.io.File;
import java.util.Scanner;

public class L11_MSaccess {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:ucanaccess://" +
            new File("ICE_PUST.accdb").getAbsolutePath()
            + ";newDatabaseVersion=V2010";

        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

        try (Connection con = DriverManager.getConnection(url);
             Scanner sc = new Scanner(System.in)) {

            ResultSet t = con.getMetaData().getTables(
                null, null, "STUDENT", new String[]{"TABLE"});

            if (!t.next()) {
                con.createStatement().executeUpdate(
                    "CREATE TABLE STUDENT ([Name] TEXT(100), " +
                    "Email TEXT(100), Phone TEXT(30))");
                System.out.println("Student table created.");
            }
            t.close();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO STUDENT ([Name], Email, Phone) VALUES (?, ?, ?)");

            System.out.print("Enter name: ");
            ps.setString(1, sc.nextLine());

            System.out.print("Enter email: ");
            ps.setString(2, sc.nextLine());

            System.out.print("Enter phone: ");
            ps.setString(3, sc.nextLine());

            ps.executeUpdate();
            System.out.println("Record inserted successfully.");

            ResultSet rs = con.createStatement().executeQuery(
                "SELECT [Name], Email, Phone FROM STUDENT");

            while (rs.next())
                System.out.println(rs.getString(1) + " | " +
                    rs.getString(2) + " | " + rs.getString(3));

            rs.close();
            ps.close();
        }
    }
}

// search - ucanaccess github download

// javac -cp ".;ucanaccess-5.2.0-uber.jar" L11_MSaccess.java
// java -cp ".;ucanaccess-5.2.0-uber.jar" L11_MSaccess