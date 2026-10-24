import java.sql.*;
import java.util.Scanner;

public class L12_MySQLCRUD {
    public static void main(String[] args) throws Exception {
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/labdb", "root", "2300");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1.Insert  2.Delete  3.Edit  4.Show  5.Exit");
            System.out.print("Choose: ");
            int ch = sc.nextInt();
            sc.nextLine();

            if (ch == 5) break;

            if (ch == 1) {
                System.out.print("ID: ");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();

                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO student VALUES(?,?,?)");
                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, email);
                ps.executeUpdate();
                System.out.println("Record inserted.");
            }

            else if (ch == 2) {
                System.out.print("Enter ID to delete: ");
                int id = sc.nextInt();

                PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM student WHERE id=?");
                ps.setInt(1, id);
                System.out.println(ps.executeUpdate() > 0
                    ? "Record deleted." : "Record not found.");
            }

            else if (ch == 3) {
                System.out.print("Enter ID to edit: ");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.print("New name: ");
                String name = sc.nextLine();
                System.out.print("New email: ");
                String email = sc.nextLine();

                PreparedStatement ps = con.prepareStatement(
                    "UPDATE student SET name=?, email=? WHERE id=?");
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setInt(3, id);
                System.out.println(ps.executeUpdate() > 0
                    ? "Record updated." : "Record not found.");
            }

            else if (ch == 4) {
                ResultSet rs = con.createStatement().executeQuery(
                    "SELECT * FROM student");

                while (rs.next())
                    System.out.println(rs.getInt("id") + "  "
                        + rs.getString("name") + "  "
                        + rs.getString("email"));
            }

            else {
                System.out.println("Invalid choice.");
            }
        }

        con.close();
        sc.close();
    }
}

// javac -cp ".;mysql-connector-j-26.10.0.jar" L12_MySQLCRUD.java

// java -cp ".;mysql-connector-j-26.10.0.jar" L12_MySQLCRUD
