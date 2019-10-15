package data.exam.exam;

import data.exam.IllegalInputException;
import data.exam.lecture.Lecture;

public class Exam {

	private Lecture lecture;
	private double note;
	private double anteil = 1;
	
	public Exam(Lecture lecture, double note) throws IllegalInputException {
			this.setLecture(lecture);
			this.setNote(note);
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}


	private void setNote(double note2) throws IllegalInputException {
		if (note2 < 1.0 || note2 > 5.0) {
			throw new IllegalInputException("Grade not correct");
		}
		this.note = note2; 
	}

	public void setAnteil(double anteil) throws IllegalInputException {
		if (1 <= anteil && anteil <= 0)
			throw new IllegalInputException("Anteil in Prozent zwischen 1 und 0");
		this.anteil = anteil;
	}

	public double getAnteil() {
		return this.anteil;
	}
	public String getName(){ return lecture.getName(); }
	public int getLeistungpunkte(){ return lecture.getLeistungpunkte(); }
	public int getSemester() { return lecture.getSemester(); }

	public Lecture getLecture() { return this.lecture; };
	public double getNote() { return this.note; }
	
	@Override
	public String toString() {
		return "Exam [semester=" + lecture.getSemester() + ", leistungsPunkte=" + lecture.getLeistungpunkte() + ", note=" + note
				+ ", name=" + lecture.getName() + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false; 
		} else if (!(o instanceof Exam)) {
			return false; 
		} 
		Exam e = (Exam) o; 
		if (e.getLecture().equals(this.getLecture()) && e.getNote() == this.getNote()) {
			return true; 
		}
		return false;
	}
}