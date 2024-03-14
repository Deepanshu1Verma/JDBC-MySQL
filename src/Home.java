import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

import com.mysql.cj.jdbc.DatabaseMetaData;

@SuppressWarnings("resource")
public class Home extends Employee {
    public static void main(String[] args) throws Exception {
        try {
            String url = "jdbc:mysql://localhost:3306";
            String databsaeName = "company";
            String userName = "root";
            String password = "1928";
            String tableName = "employee";

            try (Connection connection = DriverManager.getConnection(url, userName, password);
                    Statement statement = connection.createStatement()) {

                // Use the database
                String useDB = "USE " + databsaeName;
                statement.executeUpdate(useDB);

                // Check if the table already exists
                DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                ResultSet tables = metaData.getTables(null, null, tableName, null);
                if (!tables.next()) {
                    // Table does not exist, create it
                    String tbleQuery = "CREATE TABLE " + tableName
                            + " (name varchar(40), phone int UNIQUE, salary int, id int PRIMARY KEY);";
                    statement.executeUpdate(tbleQuery);
                } else {
                    ;
                }

                // Asking for the operation from C/U/R/D
                try (Scanner userWish = new Scanner(System.in)) {

                    Employee employeeObject1 = new Employee();

                    String intro = "Select one of the following options for performing operation: \n1. Adding an employee. \n 2. Reading about employee. \n 3. Updating about an employee. \n 4. Deleting an employee \nYour Choice:";
                    System.out.print(intro);

                    String userChoice = userWish.nextLine(); // Read user input

                    if (userChoice.equals("1")) {

                        // Showing to imput user details
                        System.out.print(
                                "Enter the details of employee as follows to insert the details about him/her:\nEnter Name:");

                        // Getting the details of the employee
                        try (Scanner newEmpName = new Scanner(System.in)) {
                            employeeObject1.setName(newEmpName.nextLine());

                            System.out.print("Enter Phone:");
                            try (Scanner newEmpPhone = new Scanner(System.in)) {
                                employeeObject1.setPhone(newEmpPhone.nextInt());

                                System.out.print("Enter Salary:");
                                try (Scanner newEmpSalary = new Scanner(System.in)) {
                                    employeeObject1.setSalary(newEmpSalary.nextInt());

                                    System.out.print("Enter ID:");
                                    try (Scanner newEmpID = new Scanner(System.in)) {
                                        employeeObject1.setId(newEmpID.nextInt());

                                        // Check if the ID already exists in the table
                                        String checkIdQuery = "SELECT * FROM " + tableName + " WHERE id = "
                                                + employeeObject1.getId() + ";";

                                        try (ResultSet idResultSet = statement.executeQuery(checkIdQuery)) {
                                            if (idResultSet.next()) {
                                                // ID is already existing
                                                System.out.println("Error: The ID '" + employeeObject1.getId()
                                                        + "' already exists in the table. Please choose a valid ID.");
                                            } else {

                                                // Check if the phone already exists in the table
                                                String checkPhoneQuery = "SELECT * FROM " + tableName
                                                        + " WHERE phone = " + employeeObject1.getPhone() + ";";
                                                try (ResultSet phoneResultSet = statement
                                                        .executeQuery(checkPhoneQuery)) {
                                                    if (phoneResultSet.next()) {

                                                        // Phone is already existing
                                                        System.out.println("Error: The phone '"
                                                                + employeeObject1.getPhone()
                                                                + "' already exists in the table. Please use a different phone.");
                                                    } else {

                                                        // Both ID and Phone are unique, proceed with the insertion
                                                        // getting SQL query ready
                                                        String insertionQuery = "INSERT INTO " + tableName
                                                                + " (name, phone, salary, id) VALUES ('"
                                                                + employeeObject1.getName() + "', "
                                                                + employeeObject1.getPhone() + ", "
                                                                + employeeObject1.getSalary() + ", "
                                                                + employeeObject1.getId() + ");";

                                                        // Executeing the SQL insertion query
                                                        statement.executeUpdate(insertionQuery);
                                                        System.out.print("Successfully entered the user details");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    else if (userChoice.equals("2")) {
                        // An object of employee class
                        Employee employeeObject2 = new Employee();

                        // Asking for the ID of employee

                        System.out.print("Enter the ID of employee to get the details about him/her:\nEnter ID:");

                        // Getting the name of the employee
                        try (Scanner EmpName = new Scanner(System.in)) {
                            employeeObject2.setId(EmpName.nextInt());

                            // getting SQL query ready
                            String retrievalQuery = "SELECT * FROM " + tableName + " where id  = "
                                    + employeeObject2.getId() + ";";

                            try (ResultSet resultSet = statement.executeQuery(retrievalQuery)) {
                                if (resultSet.next()) {

                                    // Setting the propertis of employyee object
                                    employeeObject2.setName(resultSet.getString("name"));
                                    employeeObject2.setPhone(resultSet.getInt("phone"));
                                    employeeObject2.setSalary(resultSet.getInt("salary"));
                                    employeeObject2.setId(resultSet.getInt("id"));

                                    // Displaying the details of employee searched using the eemployee object
                                    System.out.println("Employee Details:");
                                    System.out.println("Name: " + employeeObject2.getName());
                                    System.out.println("Phone: " + employeeObject2.getPhone());
                                    System.out.println("Salary: " + employeeObject2.getSalary());
                                    System.out.println("ID: " + employeeObject2.getId());
                                } else {
                                    System.out.println("Employee with ID '" + employeeObject2.getId() + "' not found.");
                                }
                            }
                        }
                    }

                    else if (userChoice.equals("3")) {
                        Employee employeeObject3 = new Employee();
                        // Updating the details of the employee in database

                        System.out.print("Enter the ID of employee to update the details about him/her:\nEnter ID:");

                        // Getting the ID of the employee
                        try (Scanner EmpName = new Scanner(System.in)) {
                            employeeObject3.setId(EmpName.nextInt());

                            // Check if the ID doesn't exist in table
                            String checkIdQuery = "SELECT * FROM " + tableName + " WHERE id = "
                                    + employeeObject3.getId() + ";";

                            try (ResultSet idResultSet = statement.executeQuery(checkIdQuery)) {
                                if (!idResultSet.next()) {
                                    // ID is not existing
                                    System.out.println("Error: The ID '" + employeeObject3.getId()
                                            + "' doesn't exist in the table. Please enter a valid ID.");
                                } else {

                                    // Displaying the choice for updataion
                                    System.out.print(
                                            "Press the accordingly for updating by your choice:\n1. Name\n2. Phone\n3. Salary\n4. ID\n\n Enter Choice:");
                                    try (Scanner updationChoice = new Scanner(System.in)) {
                                        String updationCh = updationChoice.nextLine();

                                        if (updationCh.equals("1")) {

                                            // Updating name
                                            System.out.print("Enter new name:");
                                            try (Scanner NewName = new Scanner(System.in)) {
                                                employeeObject3.setName((NewName.nextLine()));

                                                // getting SQL query ready
                                                String updateQuery = "UPDATE " + tableName + " SET name = '"
                                                        + employeeObject3.getName() + "' WHERE id = "
                                                        + employeeObject3.getId()
                                                        + ";";

                                                statement.executeUpdate(updateQuery);
                                                System.out.println("Successfully updated name for employee with ID'"
                                                        + employeeObject3.getId() + "'.");
                                            }
                                        } else if (updationCh.equals("2")) {

                                            // Updating Phone
                                            System.out.print("Enter new phone:");
                                            try (Scanner NewPhone = new Scanner(System.in)) {
                                                employeeObject3.setPhone((NewPhone.nextInt()));

                                                // Check if the phone already exists in the table
                                                String checkPhQuery = "SELECT * FROM " + tableName
                                                        + " WHERE phone = " + employeeObject3.getPhone() + ";";
                                                try (ResultSet phoneResultSet = statement
                                                        .executeQuery(checkPhQuery)) {
                                                    if (phoneResultSet.next()) {
                                                        // phone is already used
                                                        System.out.print(
                                                                "The phone number already exists in the table. Please use a different phone number");
                                                    } else {
                                                        // getting SQL query ready
                                                        String updateQuery = "UPDATE " + tableName + " SET phone = "
                                                                + employeeObject3.getPhone() + " WHERE id = "
                                                                + employeeObject3.getId()
                                                                + ";";

                                                        statement.executeUpdate(updateQuery);
                                                        System.out.println(
                                                                "Successfully updated phone for employee with ID'"
                                                                        + employeeObject3.getId() + "'.");
                                                    }
                                                }
                                            }
                                        } else if (updationCh.equals("3")) {
                                            // Updating Salary
                                            System.out.print("Enter new salary:");
                                            try (Scanner NewSalary = new Scanner(System.in)) {
                                                employeeObject3.setSalary((NewSalary.nextInt()));

                                                // getting SQL query ready
                                                String updateQuery = "UPDATE " + tableName + " SET salary = "
                                                        + employeeObject3.getSalary() + " WHERE id = "
                                                        + employeeObject3.getId()
                                                        + ";";

                                                statement.executeUpdate(updateQuery);
                                                System.out.println("Successfully updated salary for employee with ID'"
                                                        + employeeObject3.getId() + "'.");
                                            }
                                        } else if (updationCh.equals("4")) {
                                            // Updating ID
                                            System.out.print("Enter new ID:");
                                            try (Scanner NewId = new Scanner(System.in)) {
                                                Integer newID = NewId.nextInt();
                                                // Check if the ID already exists in the table
                                                String checkIDQuery = "SELECT * FROM " + tableName
                                                        + " WHERE id = " + newID + ";";
                                                try (ResultSet phoneResultSet = statement
                                                        .executeQuery(checkIDQuery)) {
                                                    if (phoneResultSet.next()) {
                                                        // ID is already existing
                                                        System.out.print(
                                                                "The ID already exists in the table. Please choose a valid ID.");
                                                    } else {
                                                        // getting SQL query ready
                                                        String updateQuery = "UPDATE " + tableName + " SET id = "
                                                                + newID + " WHERE id = "
                                                                + employeeObject3.getId()
                                                                + ";";

                                                        statement.executeUpdate(updateQuery);
                                                        System.out
                                                                .println("Successfully updated ID for employee with ID'"
                                                                        + employeeObject3.getId() + "'.");
                                                    }
                                                }
                                            }
                                        } else {
                                            System.out.print("You did not enter valid choice.");
                                        }
                                    }
                                }
                            }
                        }
                    }

                    else if (userChoice.equals("4")) {
                        // Deleting the details of the employee from database

                        Employee employeeObject4 = new Employee();

                        System.out.print("Enter the ID of employee to delete the details about him/her:\nEnter ID:");

                        // Getting the name of the employee
                        try (Scanner EmpId = new Scanner(System.in)) {
                            employeeObject4.setId(EmpId.nextInt());
                            // Check if the ID does not exists in the table
                            String checkIDQuery = "SELECT * FROM " + tableName
                                    + " WHERE id = " + employeeObject4.getId() + ";";
                            try (ResultSet idResultSet = statement
                                    .executeQuery(checkIDQuery)) {
                                if (!idResultSet.next()) {
                                    // ID is not existing
                                    System.out.print(
                                            "The ID does not exist in the table. Please choose a valid ID.");
                                } else {
                                    // getting SQL query ready
                                    String deleteQuery = "DELETE FROM " + tableName + " WHERE id = '"
                                            + employeeObject4.getId() + "';";

                                    statement.executeUpdate(deleteQuery);

                                    System.out.println("Successfully deleted all records for employee '"
                                            + employeeObject4.getId() + "'.");
                                }
                            }
                        }
                    }

                    else {
                        System.out.println("You did not enter your choice perfectly");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // End of try-with-resources for Scanner
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}