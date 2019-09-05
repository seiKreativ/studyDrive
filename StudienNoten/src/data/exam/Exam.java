package data.exam;

import gui.mainFrame.IllegalInputException;

public class Exam {
	static private int idCounter = 0; 
	private int semester, leistungsPunkte, id; 
	private double note; 
	private String name;
	private String matrNr; 
	
	
	public Exam(int semester, String name, int leistungsPunkte, double note) throws IllegalInputException {
			this.setName(name);
			this.setSemester(semester); 
			this.setLeistungsPunkte(leistungsPunkte); 
			this.setNote(note); 
			this.id = idCounter++; 
			this.setMatrNr(matrNr); 
		
	}
	
	private void setMatrNr(String matrNr2) throws IllegalInputException {
		// TODO Auto-generated method stub
		if (!matrNr2.matches("[0-9]^10)")) {
			throw new IllegalInputException("MatrNr nicht korrekt"); 
		}
		this.matrNr = matrNr2; 
	}

	private void setNote(double note2) throws IllegalInputException {
		// TODO Auto-generated method stub
		if (note2 < 1.0 || note2 > 5.0) {
			throw new IllegalInputException("Note ist nicht korrekt"); 
		}
		this.note = note2; 
	}

	private void setLeistungsPunkte(int leistungsPunkte2) throws IllegalInputException{
		// TODO Auto-generated method stub
		if (leistungsPunkte2 < 2 || leistungsPunkte > 30) {
			throw new IllegalInputException("Leistungspunkte sind nicht korrekt");
		}
		this.leistungsPunkte = leistungsPunkte2; 
	
	}

	private void setSemester(int semester2) throws IllegalInputException{
		// TODO Auto-generated method stub
		if (semester2 < 1 || semester2 > 8) {
			throw new IllegalInputException("Semester ist nicht korrekt");
		}
		this.semester = semester2; 
	}

	private void setName(String name2) throws IllegalInputException{
		// TODO Auto-generated method stub
		// hier noch schauen ob name in der user liste ist 
		if (name2.length() < 1) {
			throw new IllegalInputException("Name nicht gültig"); 
		}
		this.name = name2; 
	}

	public String getName(){ return this.name; }
	public int getLeistungpunkte(){ return this.leistungsPunkte; }
	public double getNote() { return this.note; }
	public int getSemester() { return this.semester; }
	public int getId() {return this.id; }
}