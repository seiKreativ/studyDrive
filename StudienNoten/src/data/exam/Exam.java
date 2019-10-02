package data.exam;

public class Exam {

	private Lecture lecture;
	private double note;
	
	public Exam(Lecture lecture, double note) throws IllegalInputException {
			this.setLecture(lecture);
			this.setNote(note);
	}

	private void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}


	private void setNote(double note2) throws IllegalInputException {
		if (note2 < 1.0 || note2 > 5.0) {
			throw new IllegalInputException("Grade not correct");
		}
		this.note = note2; 
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
		if (e.getLeistungpunkte() == this.getLeistungpunkte() && e.getNote() == this.getNote()  && e.getName().equals(this.getName()) && e.getSemester() == this.getSemester()) {
			return true; 
		}
		return false;
	}
}