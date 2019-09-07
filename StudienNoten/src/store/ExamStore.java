package store;

import java.sql.*;

import data.exam.Exam;
import data.exam.ExamAlreadyExistsException;
import data.exam.ExamContainer;
import data.exam.IllegalInputException;

public class ExamStore implements DataManagement {

	/*
	is just use my databse, that can be changed easily. The database has to have the
	tables "Users" and "Exams" with the attributs (which can be changed to if necessary)
	Users: "username" (varchar(20)) and "password" (varchar(20)), primary key: username
	Exams: "id" (int), "name" (varchar(30)), "semester" (int), "leistungspunkte" (int), "note" (double), "username" (varchar(20)), primary key: username, id
	*/

	private final static String connection = "jdbc:mysql://www.remotemysql.com:3306/Rjb9OP2iXT";
	private final static String user = "Rjb9OP2iXT";
	private final static String passworddatabase = "qlff07GmU1";
	private final static String driverDB = "com.mysql.cj.jdbc.Driver";
	private Connection con = null;
	private static ExamStore unique;
    private String username;

	private ExamStore() throws StoreException{
		try {
			Class.forName(driverDB);
			con = DriverManager.getConnection(connection, user, passworddatabase);
		} catch (SQLException | ClassNotFoundException e) {
			throw new StoreException("Connection failed: " + e.getMessage(), e);
		}
	}

	public static ExamStore instance() throws StoreException {
		if (unique == null)
			unique = new ExamStore();
		return unique;
	}

	@Override
	public void load(ExamContainer container) throws StoreException {
		try (Statement abfrage = con.createStatement()){
			String befehl = "select * from Exams where username = '" + username + "';";
			ResultSet ergebnis = abfrage.executeQuery(befehl);
			while (ergebnis.next()) {
				try {
					Exam e = new Exam(ergebnis.getInt("semester"), ergebnis.getString("name"), ergebnis.getInt("leistungspunkte"), ergebnis.getDouble("note"));
					container.linkExamLoading(e);
				} catch (IllegalInputException | SQLException | ExamAlreadyExistsException e) {
					throw new StoreException("Loading failed: " + e.getMessage(), e);
				}
			}
		} catch (SQLException e) {
			throw new StoreException("Error while loading: " + e.getMessage(), e);
		}
	}

	@Override
	public void newUser(String username, String password) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			/*
			checking, if the username alredy exists
			 */
			String befehl = "select * from Users;";
			ResultSet ergebnis = abfrage.executeQuery(befehl);
			while (ergebnis.next()) {
				if (ergebnis.getString("username").equals(username))
					throw new StoreException("Username alredy exists", null);
			}
			/*
			adding user
			 */
			String befehl2 = "INSERT INTO Users VALUES ('" + username + "','" + password + "');";
			abfrage.executeUpdate(befehl2);
			this.username = username;
		} catch (SQLException e1) {
			throw new StoreException("Error: " + e1.getMessage(), e1);
		}
	}

	@Override
	public void setUser(String username, String password) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
		/*
		checking, if the user exists and setting the username
		 */
		String befehl = "select * from Users;";
		ResultSet ergebnis = abfrage.executeQuery(befehl);
		boolean tmp = false;
		while (ergebnis.next()) {
			if (ergebnis.getString("username").equals(username) && ergebnis.getString("password").equals(password)) {
				this.username = username;
				tmp = true;
			}
		}
		if (!tmp)
			throw new StoreException("Username or password wrong", null);
		} catch (SQLException e1) {
			throw new StoreException("Error while adding exam " + e1.getMessage(), e1);
		}
	}

	@Override
	public void add(Exam e) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "INSERT INTO Exams VALUES ('" + e.getId() + "','" + e.getName() + "', '" + e.getSemester() + "','" + e.getLeistungpunkte() + "', '" + e.getNote() + "', '" + username + "');";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e1) {
			throw new StoreException("Error while adding exam " + e1.getMessage(), e1);
		}
	}

    public void delete(Exam e) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "DELETE FROM Exams WHERE username = '" + username + "' and id = '" + e.getId() + "';";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e1) {
			throw new StoreException("Error while deleting exam " + e1.getMessage(), e1);
		}
    }

    @Override
    public void modify(Exam eold, Exam enew) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "update Exams set name = '" + enew.getName() + "', semester = '" + enew.getSemester() + "', leistungspunkte = '" + enew.getLeistungpunkte() + "', note = '" + enew.getNote() + "' " +
					"WHERE username = '" + username + "' and id = '" + eold.getId() + "';";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e) {
			throw new StoreException("Error while modifying exam " + e.getMessage(), e);
		}
    }

	@Override
	public void close() throws StoreException {
		try {
			if (con != null)
				con.close();
			unique = null;
		} catch (SQLException e) {
			unique = null;
			throw new StoreException("Error while closing: " + e.getMessage(), e);
		}
	}

}