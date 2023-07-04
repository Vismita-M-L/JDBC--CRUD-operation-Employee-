package com.techlabs.jdbcemployee;
	import java.sql.*;
	import java.util.ArrayList;
	import java.util.List;
import java.util.Scanner;

	public class Department {
	    private static int DEPTNO;
	    private static String DNAME;
	    private static String LOC;
	    
		public Department() {
			super();
			this.DEPTNO = DEPTNO;
			this.DNAME = DNAME;
			this.LOC = LOC;
		}
		
		public int getDEPTNO() {
			return DEPTNO;
		}
		public void setDEPTNO(int dEPTNO) {
			DEPTNO = dEPTNO;
		}
		public String getDNAME() {
			return DNAME;
		}
		public void setDNAME(String dNAME) {
			DNAME = dNAME;
		}
		public String getLOC() {
			return LOC;
		}
		public void setLOC(String lOC) {
			LOC = lOC;
		}


		// Create
	    public void saveToDatabase(Connection connection) throws SQLException {
	        PreparedStatement preparedstatement = connection.prepareStatement("INSERT INTO Department (DEPTNO, DNAME, LOC) " +
	                "VALUES (?, ?, ?)");
	        preparedstatement.setInt(1, DEPTNO);
	        preparedstatement.setString(2, DNAME);
	        preparedstatement.setString(3, LOC);

	        preparedstatement.executeUpdate();

	        preparedstatement.close();
	    }

	    // Read
	    public static List<Department> getAllDepartments(Connection connection) throws SQLException {
	        List<Department> departments = new ArrayList<>();
	        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Department");
	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            Department department = new Department();
	            department.setDEPTNO(resultSet.getInt("DEPTNO"));
	            department.setDNAME(resultSet.getString("DNAME"));
	            department.setLOC(resultSet.getString("LOC"));

	            departments.add(department);
	        }

	        resultSet.close();
	        preparedStatement.close();

	        return departments;
	    }
	    public static void updateInDatabase(Connection connection) throws SQLException {
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("Enter Department details: Name, Location, Department Number to update");
	        String DNAME = scanner.next();
	        String LOC= scanner.next();
	        int DEPTNO = scanner.nextInt();

	        PreparedStatement preparedstatement = connection.prepareStatement("UPDATE Department SET DNAME = ?, LOC = ? " +
	                "WHERE DEPTNO = ?");
	        preparedstatement.setString(1, DNAME);
	        preparedstatement.setString(2, LOC);
	        preparedstatement.setInt(3, DEPTNO);

	        preparedstatement.executeUpdate();
	        System.out.println("Department record updated successfully.");

	        preparedstatement.close();
	    }
	   

	        public static void deleteFromDatabase(Connection connection) throws SQLException {
	            Scanner scanner = new Scanner(System.in);
	            System.out.println("Enter Department number to delete Department:");
	            int DEPTNO = scanner.nextInt();

	            PreparedStatement preparedstatement = connection.prepareStatement("DELETE FROM Department WHERE DEPTNO = ?");
	            preparedstatement.setInt(1, DEPTNO);

	            preparedstatement.executeUpdate();

	            preparedstatement.close();
	        }

			@Override
			public String toString() {
				return "Department [getDEPTNO()=" + getDEPTNO() + ", getDNAME()=" + getDNAME() + ", getLOC()="
						+ getLOC() + "]";
			}
	        

	        
	    }




	    
	



