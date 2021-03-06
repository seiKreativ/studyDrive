package store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;

import data.exam.IllegalInputException;
import data.exam.exam.Exam;
import data.exam.exam.ExamContainer;
import data.exam.lecture.Lecture;
import data.exam.lecture.LectureContainer;
import data.exam.sheet.Sheet;
import data.exam.sheet.SheetContainer;

public class UserInformationStore implements DataManagement {

	private final static String connection = "jdbc:mysql://www.remotemysql.com:3306/oUyCsXhXyi";
	private final static String user = "oUyCsXhXyi";
	private final static String passworddatabase = "IzgttKiqZr";
	private final static String driverDB = "com.mysql.cj.jdbc.Driver";
	private Connection con = null;
	private static UserInformationStore unique;
    private String email;
    private String password;

	private UserInformationStore() throws StoreException{
		try {
			Class.forName(driverDB);
			con = DriverManager.getConnection(connection, user, passworddatabase);
		} catch (SQLException | ClassNotFoundException e) {
			throw new StoreException("Connection failed: " + e.getMessage(), e);
		}
	}

	public static UserInformationStore instance() throws StoreException {
		if (unique == null)
			unique = new UserInformationStore();
		return unique;
	}

	private static String hashPassword(String password_plaintext) {
		int workload = 10;
		String salt = BCrypt.gensalt(workload);

		return(BCrypt.hashpw(password_plaintext, salt));
	}

	@Override
	public void newUser(String name, String email, String password) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			/*
			checking, if the username alredy exists
			 */
			String befehl = "select email from users;";
			ResultSet ergebnis = abfrage.executeQuery(befehl);
			while (ergebnis.next()) {
				if (ergebnis.getString("email").equals(email))
					throw new StoreException("Email alredy registert", null);
			}
			/*
			adding user
			 */
			String hashed_password = UserInformationStore.hashPassword(password);
			String befehl2 = "INSERT INTO users (name, email, password) VALUES ('" + name + "','" + email + "','" + hashed_password + "');";
			this.email = email;
			this.password = password;
			abfrage.executeUpdate(befehl2);
		} catch (SQLException e1) {
			throw new StoreException("Error: " + e1.getMessage(), e1);
		}
	}

	@Override
	public void setUser(String email, String password) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
		/*
		checking, if the user exists and setting the username
		 */
			String befehl = "select email, password from users;";
			ResultSet ergebnis = abfrage.executeQuery(befehl);
			boolean tmp = false;
			while (ergebnis.next()) {
				if (ergebnis.getString("email").equals(email) && BCrypt.checkpw(password, ergebnis.getString("password"))) {
					this.email = email;
					this.password = password;
					tmp = true;
				}
			}
			if (!tmp)
				throw new StoreException("Username or password wrong", null);
		} catch (SQLException e1) {
			throw new StoreException("Error while setting user " + e1.getMessage(), e1);
		}
	}

	@Override
	public void setNewMail(String mail) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl2 = "update users set email = '" + mail + "' where email = '" + email + "';";
			abfrage.executeUpdate(befehl2);
			this.email = mail;
		} catch (SQLException e1) {
			throw new StoreException("Error while setting new email " + e1.getMessage(), e1);
		}
	}

	@Override
	public boolean checkMailAreadyExists(String email) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "select email from users;";
			ResultSet ergebnis = abfrage.executeQuery(befehl);
			boolean tmp = false;
			while (ergebnis.next()) {
				if (ergebnis.getString("email").equals(email)) {
					tmp = true;
				}
			}
			return tmp;
		} catch (SQLException e1) {
			throw new StoreException("Error while setting user " + e1.getMessage(), e1);
		}
	}

	@Override
	public void setActivated() throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl2 = "update users set status = 1 where email = '" + email + "';";
			abfrage.executeUpdate(befehl2);
		} catch (SQLException e1) {
			throw new StoreException("Error while setting status " + e1.getMessage(), e1);
		}
	}

	@Override
	public boolean checkEmail(String email) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			/*
			checking, if the email exists
			 */
			String befehl = "select distinct email from users;";
			ResultSet ergebnis = abfrage.executeQuery(befehl);
			boolean tmp = false;
			while (ergebnis.next()) {
				if (ergebnis.getString("email").equals(email)) {
					tmp = true;
					this.email = email;
					break;
				}
			}
			return tmp;
		} catch (SQLException e1) {
			throw new StoreException("Error: " + e1.getMessage(), e1);
		}
	}

	@Override
	public String getUserEmail() throws StoreException {
		return this.email;
	}

	@Override
	public String getUserName() throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl1 = "select distinct name from users where email = '" + email + "';";
			ResultSet ergebnis1 = abfrage.executeQuery(befehl1);
			ergebnis1.next();
			return ergebnis1.getString("name");
		} catch (SQLException e1) {
			throw new StoreException("Error while getting user " + e1.getMessage(), e1);
		}
	}

	@Override
	public String getUsercode() throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl1 = "select distinct code from users where email = '" + email + "';";
			ResultSet ergebnis1 = abfrage.executeQuery(befehl1);
			ergebnis1.next();
			return ergebnis1.getString("code");
		} catch (SQLException e1) {
			throw new StoreException("Error while getting code " + e1.getMessage(), e1);
		}
	}
	

	public String getUserDate() throws StoreException {
		String pattern = "MM/dd/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		try (Statement abfrage = con.createStatement()) {
			String befehl1 = "select distinct createdAt from users where email = '" + email + "';";
			ResultSet ergebnis1 = abfrage.executeQuery(befehl1);
			ergebnis1.next();
			return df.format(ergebnis1.getDate("createdAt"));
		} catch (SQLException e1) {
			throw new StoreException("Error while getting code " + e1.getMessage(), e1);
		}
	}

	@Override
	public String getPassword() throws StoreException {
		return this.password;
	}

	@Override
	public void changePasswort(String newPassword) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String password = UserInformationStore.hashPassword(newPassword);
			String befehl = "update users set password = '" + password + "' where email = '" + email + "';";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e1) {
			throw new StoreException("Error: " + e1.getMessage(), e1);
		}
	}

	@Override
	public void deleteUser(LectureContainer c1, ExamContainer c2, SheetContainer c3) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			if (c2.getSize() != 0) {
				String befehl = "DELETE FROM exams WHERE username = '" + email + "';";
				abfrage.executeUpdate(befehl);
			}
			if (c1.getSize() != 0) {
				String befehl1 = "DELETE FROM userlecture WHERE email = '" + email + "';";
				abfrage.executeUpdate(befehl1);
			}
			if (c3.getSize() != 0) {
				String befehl2 = "DELETE FROM usersheets WHERE email = '" + email + "';";
				abfrage.executeUpdate(befehl2);
			}
			String befehl3 = "DELETE FROM users WHERE email = '" + email + "';";
			abfrage.executeUpdate(befehl3);
		} catch (SQLException e1) {
			throw new StoreException("Error while deleting user " + e1.getMessage(), e1);
		}
	}

	@Override
	public boolean checkAccountIsActivated(String email) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			this.email = email;
			String befehl = "select status from users where email = '" + email + "';";
			ResultSet ergebnis = abfrage.executeQuery(befehl);
			ergebnis.next();
			int status = ergebnis.getInt("status");
			if (status == 1)
				return true;
			else
				return false;
		} catch (SQLException e1) {
			throw new StoreException("Error: " + e1.getMessage(), e1);
		}
	}

	@Override
	public void setActivationCode(String code) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "update users set code = '" + code + "' where email = '" + email + "';";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e1) {
			throw new StoreException("Error: " + e1.getMessage(), e1);
		}
	}

	@Override
	public boolean checkActivationCode(String code) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "select code from users where email = '" + email + "';";
			ResultSet ergebnis = abfrage.executeQuery(befehl);
			ergebnis.next();
			return code.equals(ergebnis.getString("code"));
		} catch (SQLException e1) {
			throw new StoreException("Error: " + e1.getMessage(), e1);
		}
	}
 
	@Override
	public void load(LectureContainer lectures, ExamContainer container, SheetContainer sheets) throws StoreException {
		try (Statement abfrage = con.createStatement()){
			String befehl = "SELECT distinct lecture, semester, credits FROM userlecture WHERE email = '" + email + "'";
			ResultSet ergebnis = abfrage.executeQuery(befehl); 
			while (ergebnis.next()) {
				try {
					Lecture l = new Lecture(ergebnis.getInt("semester"), ergebnis.getString("lecture"), ergebnis.getInt("credits"));
					lectures.linkLectureLoading(l);
				} catch (IllegalInputException | SQLException e) {
					throw new StoreException("Loading failed: " + e.getMessage(), e);
				}
			}
			String befehl2 = "SELECT distinct name, mark, semester FROM exams WHERE username = '" + email + "'";
			ResultSet ergebnis2 = abfrage.executeQuery(befehl2);
			while (ergebnis2.next()) {
				Lecture tmp = lectures.getLectureByName(ergebnis2.getString("name"), ergebnis2.getInt("semester"));
				container.linkExamLoading(new Exam(tmp, ergebnis2.getDouble("mark")));
			}
			String befehl3 = "SELECT distinct lecture, number, points, maxPoints, semester, id FROM usersheet WHERE email = '" + email + "'";
			ResultSet ergebnis3 = abfrage.executeQuery(befehl3);
			while (ergebnis3.next()) {
				Lecture tmp = lectures.getLectureByName(ergebnis3.getString("lecture"), ergebnis3.getInt("semester"));
				Sheet s = new Sheet(tmp, ergebnis3.getInt("number"), ergebnis3.getDouble("points"), ergebnis3.getDouble("maxPoints"), ergebnis3.getInt("id"));
				sheets.linkSheetLoading(s);
			}
		} catch (SQLException | IllegalInputException e) {
			throw new StoreException("Error while loading: " + e.getMessage(), e);
		}
	}

	@Override
	public void addSheet(Sheet s) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "INSERT INTO usersheet VALUES ('" + email + "','" + s.getName() + "', " + s.getNumber() + ", " + s.getPoints() + ", " + s.getMaxPoints() + ", " + s.getSemester() + ", " + s.getType() + ");";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e1) {
			throw new StoreException("Error while adding sheet " + e1.getMessage(), e1);
		}
	}

	@Override
	public void deleteSheet(Sheet s) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "DELETE FROM usersheet WHERE email = '" + email + "' AND semester = " + s.getSemester() + " AND lecture = '" + s.getName() + "' AND id = " + s.getType() + ";";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e1) {
			throw new StoreException("Error while deleting lecture " + e1.getMessage(), e1);
		}
	}

	@Override
	public void modifySheet(Sheet sold, Sheet snew) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "UPDATE usersheet set semester = " + snew.getSemester() + ", lecture = '" + snew.getName() + "', id = " + snew.getType() + ", points = " + snew.getPoints() + ", maxPoints = " + snew.getMaxPoints() +
					" WHERE email = '" + email + "' AND semester = " + sold.getSemester() + " AND lecture = '" + sold.getName() + "' AND id = " + sold.getType() + ";";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e1) {
			throw new StoreException("Error while adding sheet " + e1.getMessage(), e1);
		}
	}

	@Override
	public void addExam(Exam e) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "INSERT INTO exams VALUES (" + e.getSemester() + ",'" + e.getName() + "', "+ e.getNote() + ", '" + email + "', " + e.getAnteil() + ");";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e1) {
			throw new StoreException("Error while adding exam " + e1.getMessage(), e1);
		}
	}

	@Override
    public void deleteExam(Exam e) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "DELETE FROM exams WHERE username = '" + email + "' AND semester = " + e.getSemester() + " AND name = '" + e.getName() + "';";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e1) {
			throw new StoreException("Error while deleting exam " + e1.getMessage(), e1);
		}
    }

    @Override
    public void modifyExam(Exam eold, Exam enew) throws StoreException {
		try (Statement abfrage = con.createStatement()) {
			String befehl = "update exams set mark = " + enew.getNote() + ", semester = " + enew.getSemester() + ", name = '" + enew.getName() + "', anteil =  " + enew.getAnteil() + " " +
					"WHERE username = '" + email + "' AND semester = " + eold.getSemester() + " AND name = '"+ eold.getName() + "';";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e) {
			throw new StoreException("Error while modifying exam " + e.getMessage(), e);
		}
    }

	@Override
	public void addLecture(Lecture e) throws StoreException{
		try (Statement abfrage = con.createStatement()) {
			String befehl = "INSERT INTO userlecture VALUES ('" + email + "','" + e.getName() + "', " + e.getSemester() + ", " + e.getLeistungpunkte() + ", " + e.getAnzahlExams() + ");";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e1) {
			throw new StoreException("Error while adding lecture " + e1.getMessage(), e1);
		}
	}

	@Override
	public void deleteLecture(Lecture e) throws StoreException{
		try (Statement abfrage = con.createStatement()) {
			String befehl = "DELETE FROM userlecture WHERE email = '" + email + "' AND semester = " + e.getSemester() + " AND lecture = '" + e.getName() + "';";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e1) {
			throw new StoreException("Error while deleting lecture " + e1.getMessage(), e1);
		}
	}

	@Override
	public void modifyLecture(Lecture eold, Lecture enew) throws StoreException{
		try (Statement abfrage = con.createStatement()) {
			String befehl = "update userlecture set credits = " + enew.getLeistungpunkte() + ", semester = " + enew.getSemester() + ", lecture = '" + enew.getName() + "', countExams = " + enew.getAnzahlExams() + " " +
					"WHERE email = '" + email + "' AND semester = " + eold.getSemester() + " AND lecture = '"+ eold.getName() + "';";
			abfrage.executeUpdate(befehl);
		} catch (SQLException e) {
			throw new StoreException("Error while modifying lecture " + e.getMessage(), e);
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
