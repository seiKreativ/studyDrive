package data.exam;

public class Exam {

	private int semester, leistungsPunkte, id; 
	private double note; 
	private String name;
	
	
	public Exam(int semester, String name, int leistungsPunkte, double note) throws IllegalInputException {
			this.setName(name);
			this.setSemester(semester); 
			this.setLeistungsPunkte(leistungsPunkte); 
			this.setNote(note);
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
		/*try {
			ExamContainer container = ExamContainer.instance();
			for (Exam e : container) {
				if (e.getName().equals(name2))
						throw new IllegalInputException("name already exists");
			}
		} catch (StoreException e) {
			
		}*/
		if (name2.length() < 1) {
			throw new IllegalInputException("Name not correct");
		}
		this.name = name2; 
	}

	public String getName(){ return this.name; }
	public int getLeistungpunkte(){ return this.leistungsPunkte; }
	public double getNote() { return this.note; }
	public int getSemester() { return this.semester; }
	
	@Override
	public String toString() {
		return "Exam [semester=" + semester + ", leistungsPunkte=" + leistungsPunkte + ", note=" + note
				+ ", name=" + name + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false; 
		} else if (!(o instanceof Exam)) {
			return false; 
		} 
		Exam e = (Exam) o; 
		if (e.getLeistungpunkte() == this.getLeistungpunkte() && e.getNote() == this.getNote()  && e.getName() == this.getName()  && e.getSemester() == this.getSemester()) {
			return true; 
		}
		return false;
	}
}