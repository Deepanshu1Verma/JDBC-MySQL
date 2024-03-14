import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class CreateDB extends Employee {
    public static void main(String[] args) throws Exception {

        String url = "jdbc:mysql://localhost:3306";
        String databsaeName = "company";
        String userName = "root";
        String password = "1928";
        String tableName = "employee";

        try (Connection connection = DriverManager.getConnection(url, userName, password);
                Statement statement = connection.createStatement()) {

            String dbQuery = "CREATE DATABASE " + databsaeName;

            statement.executeUpdate(dbQuery);
            JOptionPane.showMessageDialog(null, statement, dbQuery, 0);

            // Use the database
            String useDB = "USE " + databsaeName;
            statement.executeUpdate(useDB);

            String tbleQur = "CREATE TABLE " + tableName
                    + " (name varchar(40), phone int UNIQUE, salary int, id int PRIMARY KEY);";
            statement.executeUpdate(tbleQur);
            statement.close();
            JOptionPane.showMessageDialog(null, statement, tableName, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
