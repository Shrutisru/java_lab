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

            if (ch == 4) {
                ResultSet rs = con.createStatement().executeQuery(
                    "SELECT * FROM student");
                while (rs.next())
                    System.out.println(rs.getInt("id") + "  "
                        + rs.getString("name") + "  "
                        + rs.getString("email"));
                rs.close();
                continue;
            }

            if (ch < 1 || ch > 4) {
                System.out.println("Invalid choice.");
                continue;
            }

            System.out.print(ch == 1 ? "ID: " :
                ch == 2 ? "Enter ID to delete: " : "Enter ID to edit: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = ch == 1 ? "INSERT INTO student VALUES(?,?,?)" :
                         ch == 2 ? "DELETE FROM student WHERE id=?" :
                         "UPDATE student SET name=?, email=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            if (ch == 2) {
                ps.setInt(1, id);
            } else {
                if (ch == 1) ps.setInt(1, id);

                System.out.print(ch == 1 ? "Name: " : "New name: ");
                String name = sc.nextLine();
                System.out.print(ch == 1 ? "Email: " : "New email: ");
                String email = sc.nextLine();

                ps.setString(ch == 1 ? 2 : 1, name);
                ps.setString(ch == 1 ? 3 : 2, email);
                if (ch == 3) ps.setInt(3, id);
            }

            int n = ps.executeUpdate();
            System.out.println(ch == 1 ? "Record inserted." :
                n == 0 ? "Record not found." :
                ch == 2 ? "Record deleted." : "Record updated.");
            ps.close();
        }

        con.close();
        sc.close();
    }
}

/*

CREATE DATABASE labdb;
USE labdb;

CREATE TABLE student (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(100)
);

*/
// mysql community download ---- for mysql
// mysql community connector download ---- for connector driver

// javac -cp ".;mysql-connector-j-26.10.0.jar" L12_MySQLCRUD.java

// java -cp ".;mysql-connector-j-26.10.0.jar" L12_MySQLCRUD