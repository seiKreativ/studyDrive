package data.exam.lecture;

import data.exam.IllegalInputException;
import data.exam.exam.Exam;

public class Lecture {

    private int semester, leistungsPunkte;
    private String name;

    public Lecture(int semester, String name, int leistungsPunkte) throws IllegalInputException {
        this.setName(name);
        this.setSemester(semester);
        this.setLeistungsPunkte(leistungsPunkte);
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
    public int getSemester() { return this.semester; }

    @Override
    public String toString() {
        return "Exam [semester=" + semester + ", leistungsPunkte=" + leistungsPunkte + ", name=" + name + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof Exam)) {
            return false;
        }
        Exam e = (Exam) o;
        if (e.getLeistungpunkte() == this.getLeistungpunkte() && e.getName().equals(this.getName()) && e.getSemester() == this.getSemester()) {
            return true;
        }
        return false;
    }
}