package com.techlabs.jdbcemployee;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

	public class Employee {
	    private static int EMPNO;
	    private static String ENAME;
	    private static String JOB;
	    private static String MANAGER;
	    private static Date HIREDATE;
	    private static double SAL;
	    private static double COMM;
	    private static int DEPTNO;
	   
		public Employee() {
			this.EMPNO = EMPNO;
			this.ENAME = ENAME;
			this.JOB = JOB;
			this.MANAGER = MANAGER;
			this.HIREDATE = HIREDATE;
			this.DEPTNO = DEPTNO;
		}
		    public int getEMPNO() {
		        return EMPNO;
		    }
		    public void setEMPNO(int EMPNO) {
		        this.EMPNO = EMPNO;
		    }
		    public String getENAME() {
		        return ENAME;
		    }
		    public void setENAME(String ENAME) {
		        this.ENAME = ENAME;
		    }
		    public String getJOB() {
		        return JOB;
		    }
		    public void setJOB(String JOB) {
		        this.JOB = JOB;
		    }
		    public String getMANAGER() {
		        return MANAGER;
		    }
		    public void setMANAGER(String MANAGER) {
		        this.MANAGER = MANAGER;
		    }
		    public Date getHIREDATE() {
		        return HIREDATE;
		    }
		    public void setHIREDATE(Date HIREDATE) {
		        this.HIREDATE = HIREDATE;
		    }
		    public double getSAL() {
		        return SAL;
		    }
		    public void setSAL(double SAL) {
		        this.SAL = SAL;
		    }
		    public double getCOMM() {
		        return COMM;
		    }
		    public void setCOMM(double COMM) {
		        this.COMM = COMM;
		    }
		    public int getDEPTNO() {
		        return DEPTNO;
		    }
		    public void setDEPTNO(int DEPTNO) {
		        this.DEPTNO = DEPTNO;
		    }

		// Create
	    public void saveToDatabase(Connection connection) throws SQLException {
	         PreparedStatement preparedstatement = connection.prepareStatement("INSERT INTO Employee (EMPNO, ENAME, JOB, MANAGER, HIREDATE, SAL, COMM, DEPTNO) " +
	                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

	        preparedstatement.setInt(1, EMPNO);
	        preparedstatement.setString(2, ENAME);
	        preparedstatement.setString(3, JOB);
	        preparedstatement.setString(4, MANAGER);
	        preparedstatement.setDate(5, HIREDATE);
	        preparedstatement.setDouble(6, SAL);
	        preparedstatement.setDouble(7, COMM);
	        preparedstatement.setInt(8, DEPTNO);

	        preparedstatement.executeUpdate();
	        System.out.println("Record saved to database");

	        preparedstatement.close();
	    }

	    // Read
	    public static List<Employee> getAllEmployees(Connection connection) throws SQLException {
	        List<Employee> employees = new ArrayList<>();
	        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Employee");
	        ResultSet resultSet = preparedStatement.executeQuery();

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
	        preparedStatement.close();

	        return employees;
	    }


	    // Update
	    public static void updateInDatabase(Connection connection) throws SQLException {
	    	Scanner scanner = new Scanner(System.in);
	        System.out.println("Enter Employee details (Employee Number ,Employee Name,Job,Manager,Hiredate,Salary,Commision,Department Number to update");
	        int EMPNO =scanner.nextInt();
	        String ENAME = scanner.next();
	        String JOB = scanner.next();
	        String MANAGER= scanner.next();
	        String hireDateStr = scanner.next();
	        double SAL = scanner.nextDouble();
	        Double COMM = scanner.nextDouble();
	        int DEPTNO = scanner.nextInt();
	    
	        Date hireDate = null;
	        try {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            java.util.Date utilDate = dateFormat.parse(hireDateStr);
	            hireDate = new Date(utilDate.getTime());
	        } catch (ParseException e) {
	            System.out.println("Invalid date format. Please enter the hire date in yyyy-MM-dd format.");
	            return;
	        }
	        
	        
	        
	        PreparedStatement preparedstatement = connection.prepareStatement("UPDATE Employee SET EMPNO=? , ENAME = ?, JOB = ?, MANAGER = ?, HIREDATE = ?, SAL = ?, COMM = ?, DEPTNO = ? " +
	                "WHERE EMPNO = ?");
	        preparedstatement.setInt(1, EMPNO);
	        preparedstatement.setString(2, ENAME);
	        preparedstatement.setString(3, JOB);
	        preparedstatement.setString(4, MANAGER);
	        preparedstatement.setDate(5,HIREDATE);
	        preparedstatement.setDouble(6, SAL);
	        preparedstatement.setDouble(7, COMM);
	        preparedstatement.setInt(8, DEPTNO);

	        preparedstatement.executeUpdate();
	        System.out.println("Employee record updated successfully.");

	        preparedstatement.close();
	    }

	    // Delete
	    
	    public  static void deleteFromDatabase(Connection connection) throws SQLException {
	    	Scanner scanner = new Scanner(System.in);
	    	System.out.println("Enter Employee Number to delete employee:");
	    	int empNo = scanner.nextInt();
	        PreparedStatement preparedstatement = connection.prepareStatement("DELETE FROM Employee WHERE EMPNO = ?");
	        preparedstatement.setInt(1, EMPNO);

	        preparedstatement.executeUpdate();
	        System.out.println("Employee record deleted successfully.");

	        preparedstatement.close();
	    }
		@Override
		public String toString() {
			return "Employee [getEMPNO()=" + getEMPNO() + ", getENAME()=" + getENAME() + ", getJOB()=" + getJOB()
					+ ", getMANAGER()=" + getMANAGER() + ", getHIREDATE()=" + getHIREDATE() + ", getSAL()=" + getSAL()
					+ ", getCOMM()=" + getCOMM() + ", getDEPTNO()=" + getDEPTNO() + "]";
		}
		
	}
	


