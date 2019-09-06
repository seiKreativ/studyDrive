package data.exam;

import store.StoreException;

public class Exam {

	static private int idCounter = 0; 
	private int semester, leistungsPunkte, id; 
	private double note; 
	private String name;
	
	
	public Exam(int semester, String name, int leistungsPunkte, double note) throws IllegalInputException {
			this.setName(name);
			this.setSemester(semester); 
			this.setLeistungsPunkte(leistungsPunkte); 
			this.setNote(note); 
			this.id = idCounter++;
	}
	

	private void setNote(double note2) throws IllegalInputException {
		if (note2 < 1.0 || note2 > 5.0) {
			throw new IllegalInputException("Grade not correct");
		}
		this.note = note2; 
	}

	private void setLeistungsPunkte(int leistungsPunkte2) throws IllegalInputException{
		if (leistungsPunkte2 < 2 || leistungsPunkte > 30) {
			throw new IllegalInputException("Leistungspunkte not correct");
		}
		this.leistungsPunkte = leistungsPunkte2;
	}

	private void setSemester(int semester2) throws IllegalInputException{
		if (semester2 < 1 || semester2 > 12) {
			throw new IllegalInputException("Semester not correct");
		}
		this.semester = semester2; 
	}

	private void setName(String name2) throws IllegalInputException{
		try {
			ExamContainer container = ExamContainer.instance();
			for (Exam e : container) {
				if (e.getName().equals(name2))
						throw new IllegalInputException("name already exists");
			}
		} catch (StoreException e) {
			/*
			there won't by an exception because the ExamContainer unique alredy exists
			 */
		}
		if (name2.length() < 1) {
			throw new IllegalInputException("Name not correct");
		}
		this.name = name2; 
	}

	public String getName(){ return this.name; }
	public int getLeistungpunkte(){ return this.leistungsPunkte; }
	public double getNote() { return this.note; }
	public int getSemester() { return this.semester; }
	public int getId() {return this.id; }
}