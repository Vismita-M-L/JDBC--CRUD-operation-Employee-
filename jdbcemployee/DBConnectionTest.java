package com.techlabs.jdbcemployee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DBConnectionTest {

		    private static final Scanner scanner = new Scanner(System.in);

		    public static void main(String[] args) {
		        try {
		            
		            DBConnection dbConnection = DBConnection.getDBConnection();
		            Connection connection = dbConnection.connect();
		            
		            dbConnection.createOrganizationDb();
		            dbConnection.createDepartmentTable();
		            dbConnection.createEmployeeTable();
		            
		            //Department.updateInDatabase(connection);
		            //Department.deleteFromDatabase(connection);
		          
		            //Employee.updateInDatabase(connection);
		            //Employee.deleteFromDatabase(connection);
		            
		            
		            int choice;
		            do {
		                System.out.println("1. Insert Records into Department");
		                System.out.println("2. Insert Records into Employee");
		                System.out.println("3. Display Employees in a Department");
		                System.out.println("4. Update Employee Records");
		                System.out.println("5. Insert New Employee in HR Department");
		                System.out.println("6. Delete Employees with Salary Less Than");
		                System.out.println("7. Display All Employees");
		                System.out.println("8. Display All Departments");
		                System.out.println("9. Exit");
		                System.out.print("Enter your choice: ");
		                choice = scanner.nextInt();
		                scanner.nextLine(); // Consume the newline character

		                switch (choice) {
		                    case 1:
		                        insertDepartment(dbConnection, connection);
		                        break;
		                    case 2:
		                        insertEmployee(dbConnection, connection);
		                        break;
		                    case 3:
		                        displayEmployeesInDepartment(dbConnection, connection);
		                        break;
		                    case 4:
		                        updateEmployeeCommission(dbConnection, connection);
		                        break;
		                    case 5:
		                        insertNewEmployeeInHRDepartment(dbConnection, connection);
		                        break;
		                    case 6:
		                        deleteEmployeesWithSalaryLessThan(dbConnection, connection);
		                        break;
		                    case 7:
		                        displayAllEmployees(dbConnection, connection);
		                        break;
		                    case 8:
		                        displayAllDepartments(dbConnection, connection);
		                        break;
		                    case 9:
		                        System.out.println("Exiting...");
		                        break;
		                    default:
		                        System.out.println("Invalid choice. Please try again.");
		                        break;
		                }
		            } while (choice != 9);
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            scanner.close();
		        }
		    }
		    private static void insertDepartment(DBConnection dbConnection, Connection connection) throws SQLException {
		        System.out.println("Enter department details:");
		        System.out.print("Department Number: ");
		        int DEPTNO = scanner.nextInt();
		        scanner.nextLine(); // Consume the newline character
		        System.out.print("Department Name: ");
		        String DNAME = scanner.nextLine();
		        System.out.print("Department Location: ");
		        String LOC = scanner.nextLine();

		        dbConnection.insertDepartment(connection, DEPTNO, DNAME, LOC);
		        System.out.println("Department inserted successfully.");
		    }

		    private static void insertEmployee(DBConnection dbConnection, Connection connection) throws SQLException {
		        System.out.println("Enter employee details:");
		        System.out.print("Employee Number: ");
		        int EMPNO = scanner.nextInt();
		        scanner.nextLine(); 
		        System.out.print("Employee Name: ");
		        String ENAME = scanner.nextLine();
		        System.out.print("Job: ");
		        String JOB = scanner.nextLine();
		        System.out.print("Manager ID: ");
		        Integer MANAGER = scanner.nextInt();
		        scanner.nextLine(); 
		        System.out.print("Hire Date (YYYY-MM-DD): ");
		        String HIREDATE = scanner.nextLine();
		        System.out.print("Salary: ");
		        double SAL = scanner.nextDouble();
		        scanner.nextLine();
		        System.out.print("Commission: ");
		        Double COMM = scanner.nextDouble();
		        scanner.nextLine();
		        System.out.print("Department Number: ");
		        int DEPTNO = scanner.nextInt();
		        scanner.nextLine();

		        dbConnection.insertEmployee(connection, EMPNO, ENAME, JOB, MANAGER, HIREDATE, SAL, COMM, DEPTNO);
		        System.out.println("Employee inserted successfully.");
		    }

		    private static void displayEmployeesInDepartment(DBConnection dbConnection, Connection connection) throws SQLException {
		        System.out.print("Enter department name: ");
		        String DNAME = scanner.nextLine();

		       
		        List<Employee> employees = dbConnection.getEmployeesByDepartment(connection, DNAME);

		        if (employees.isEmpty()) {
		            System.out.println("No employees found in the specified department.");
		        } else {
		            System.out.println("Employees in the specified department:");
		            for (Employee employee : employees) {
		                System.out.println(employee);
		            }
		        }
		    }
		    private static void updateEmployeeCommission(DBConnection dbConnection, Connection connection) throws SQLException {
		        System.out.println("Enter employee number: ");
		        int EMPNO = scanner.nextInt();
		        scanner.nextLine();

		        double currentCommission = dbConnection.getEmployeeCommission(connection, EMPNO);
		        double newCommission = currentCommission * 1.2;
		        dbConnection.updateEmployeeCommission(connection, EMPNO, newCommission);

		        System.out.println("Employee records updated. Commission increased by 20%.");
		    }
		    private static void insertNewEmployeeInHRDepartment(DBConnection dbConnection, Connection connection) throws SQLException {
		        System.out.println("Enter employee details:");
		        System.out.print("Employee Number: ");
		        int EMPNO = scanner.nextInt();
		        scanner.nextLine(); 
		        System.out.print("Employee Name: ");
		        String ENAME = scanner.nextLine();
		        System.out.print("Job: ");
		        String JOB = scanner.nextLine();
		        System.out.print("Manager ID: ");
		        Integer MANAGER = scanner.nextInt();
		        scanner.nextLine(); 
		        System.out.print("Hire Date (YYYY-MM-DD): ");
		        String HIREDATE = scanner.nextLine();
		        System.out.print("Salary: ");
		        double SAL = scanner.nextDouble();
		        scanner.nextLine();
		        System.out.print("Commission: ");
		        Double COMM = scanner.nextDouble();
		        scanner.nextLine();
		        System.out.print("Department Number: ");
		        int DEPTNO = scanner.nextInt();
		        scanner.nextLine(); 

		      
		        dbConnection.insertEmployee(connection, EMPNO, ENAME, JOB, MANAGER, HIREDATE, SAL, COMM, DEPTNO);

		        System.out.println("Employee inserted successfully.");
		    }

		    private static void deleteEmployeesWithSalaryLessThan(DBConnection dbConnection, Connection connection) throws SQLException {
		        System.out.print("Enter the salary threshold: ");
		        double threshold = scanner.nextDouble();
		        scanner.nextLine(); 
		        int count = dbConnection.deleteEmployeesWithSalaryLessThan(connection, threshold);

		        System.out.println(count + " employee(s) deleted successfully.");
		    }

		    private static void displayAllEmployees(DBConnection dbConnection, Connection connection) throws SQLException {
		       
		        List<Employee> employees = dbConnection.getAllEmployees(connection);

		        if (employees.isEmpty()) {
		            System.out.println("No employees found.");
		        } else {
		            System.out.println("All Employees:");
		            for (Employee employee : employees) {
		                System.out.println(employee);
		            }
		        }
		    }

		    private static void displayAllDepartments(DBConnection dbConnection, Connection connection) throws SQLException {
		      
		        List<Department> departments = dbConnection.getAllDepartments(connection);

		        if (departments.isEmpty()) {
		            System.out.println("No departments found.");
		        } else {
		            System.out.println("All Departments:");
		            for (Department department : departments) {
		                System.out.println(department);
		            }
		        }
		    }


		    

		         
		            
		       
		
		   
		}
		
	
		

	
    
		

	


