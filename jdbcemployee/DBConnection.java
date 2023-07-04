package com.techlabs.jdbcemployee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBConnection {
	private static DBConnection Dbconnection;
    private static Connection connection;
    
    private DBConnection() {
    }
    
    public static synchronized DBConnection getDBConnection() {
        if ( Dbconnection == null) {
        	 Dbconnection = new DBConnection();
        }
        return  Dbconnection;
    }
    
    public Connection connect() throws SQLException {
        try {
            // Registering the drivers
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/organizationdb", "root", "vismita2000");
            System.out.println("Connection established successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
    
    
    public void createOrganizationDb() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS organizationdb");
            preparedStatement.executeUpdate();
            System.out.println(" Organizationdb created successfully");
            preparedStatement.close();
        } finally {
            connection.close();
        }
    }


    public void createDepartmentTable() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Department (" +
                    "DEPTNO INT PRIMARY KEY," +
                    "DNAME VARCHAR(14)," +
                    "LOC VARCHAR(13)" +
                    ")");
            preparedStatement.executeUpdate();
            System.out.println("DepartmentTable created successfully");
            preparedStatement.close();
        } finally {
            connection.close();
        }
    }

    public void createEmployeeTable() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Employee (" +
                    "EMPNO INT PRIMARY KEY," +
                    "ENAME VARCHAR(10)," +
                    "JOB VARCHAR(9)," +
                    "MANAGER INT," +
                    "HIREDATE DATE," +
                    "SAL DECIMAL(10,2)," +
                    "COMM DECIMAL(10,2)," +
                    "DEPTNO INT" +
                    ")");
            preparedStatement.executeUpdate();
            System.out.println("Employee Table created  successfully");
            preparedStatement.close();
        } finally {
            connection.close();
        }
    }

    public void insertDepartment(Connection connection, int DEPTNO, String DNAME, String LOC) throws SQLException {
        PreparedStatement preparedstatement = connection.prepareStatement("INSERT INTO Department (DEPTNO, DNAME, LOC) VALUES (?, ?, ?)");
        preparedstatement.setInt(1, DEPTNO);
        preparedstatement.setString(2, DNAME);
        preparedstatement.setString(3, LOC);
        preparedstatement.executeUpdate();
        preparedstatement.close();
    }

    public void insertEmployee(Connection connection, int EMPNO, String ENAME, String JOB, Integer MANAGER,String HIREDATE, double SAL, Double COMM, int DEPTNO) throws SQLException {
        PreparedStatement preparedstatement = connection.prepareStatement("INSERT INTO Employee (EMPNO, ENAME, JOB, MANAGER, HIREDATE, SAL, COMM, DEPTNO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        preparedstatement.setInt(1, EMPNO);
        preparedstatement.setString(2, ENAME);
        preparedstatement.setString(3, JOB);
        if (MANAGER != null) {
            preparedstatement.setInt(4, MANAGER);
        } else {
            preparedstatement.setNull(4, java.sql.Types.INTEGER);
        }
        preparedstatement.setDate(5, Date.valueOf(HIREDATE));
        preparedstatement.setDouble(6, SAL);
        if (COMM != null) {
            preparedstatement.setDouble(7, COMM);
        } else {
            preparedstatement.setNull(7, java.sql.Types.DOUBLE);
        }
        preparedstatement.setInt(8, DEPTNO);
        preparedstatement.executeUpdate();
        preparedstatement.close();
    }
    public List<Employee> getEmployeesByDepartment(Connection connection, String departmentName) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT e.* FROM Employee e INNER JOIN Department d ON e.DEPTNO = d.DEPTNO WHERE d.DNAME = ?");
        statement.setString(1, departmentName);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Employee employee = new Employee();
            employee.setEMPNO(resultSet.getInt("EMPNO"));
            employee.setENAME(resultSet.getString("ENAME"));
            employee.setJOB(resultSet.getString("JOB"));
            employee.setMANAGER(resultSet.getString("MANAGER"));
            employee.setHIREDATE(resultSet.getDate("HIREDATE"));
            employee.setSAL(resultSet.getDouble("SAL"));
            employee.setCOMM(resultSet.getDouble("COMM"));
            employee.setDEPTNO(resultSet.getInt("DEPTNO"));
          
            employees.add(employee);
        }

        resultSet.close();
        statement.close();

        return employees;
    }

    public double getEmployeeCommission(Connection connection, int empNo) throws SQLException {
        double commission = 0.0;
        PreparedStatement statement = connection.prepareStatement("SELECT COMM FROM Employee WHERE EMPNO = ?");
        statement.setInt(1, empNo);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            commission = resultSet.getDouble("COMM");
        }

        resultSet.close();
        statement.close();

        return commission;
    }

    public void updateEmployeeCommission(Connection connection, int empNo, double newCommission) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE Employee SET COMM = ? WHERE EMPNO = ?");
        statement.setDouble(1, newCommission);
        statement.setInt(2, empNo);

        statement.executeUpdate();
        statement.close();
    }
    public int deleteEmployeesWithSalaryLessThan(Connection connection, double threshold) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Employee WHERE SAL < ?");
        statement.setDouble(1, threshold);
        int count = statement.executeUpdate();
        statement.close();
        return count;
    }
    public List<Employee> getAllEmployees(Connection connection) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "SELECT * FROM Employee");

        while (resultSet.next()) {
            Employee employee = new Employee();
            employee.setDEPTNO(resultSet.getInt("EMPNO"));
            employee.setENAME(resultSet.getString("ENAME"));
            employee.setJOB(resultSet.getString("JOB"));
            employee.setMANAGER(resultSet.getString("MANAGER"));
            employee.setHIREDATE(resultSet.getDate("HIREDATE"));
            employee.setSAL(resultSet.getDouble("SAL"));
            employee.setCOMM(resultSet.getDouble("COMM"));
            employee.setDEPTNO(resultSet.getInt("DEPTNO"));
            employees.add(employee);
        }

        resultSet.close();
        statement.close();

        return employees;
    }

    public List<Department> getAllDepartments(Connection connection) throws SQLException {
        List<Department> departments = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT * FROM department"
        );
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Department department = new Department();
            department.setDEPTNO(resultSet.getInt("DeptNo"));
            department.setDNAME(resultSet.getString("dName"));
            department.setLOC(resultSet.getString("Loc"));
        

            departments.add(department);
        }

        resultSet.close();
        preparedStatement.close();

        return departments;
    }
}
    
       

        
   


