package data.exam.lecture;

import data.exam.IllegalInputException;

public class Lecture {

    private int semester, leistungsPunkte;
    private String name;
    private int anzahlExams = 1;

    public Lecture(int semester, String name, int leistungsPunkte) throws IllegalInputException {
        this.setName(name);
        this.setSemester(semester);
        this.setLeistungsPunkte(leistungsPunkte);
    }

    public void setLeistungsPunkte(int leistungsPunkte2) throws IllegalInputException{
        if (leistungsPunkte2 < 2 || leistungsPunkte > 30) {
            throw new IllegalInputException("Leistungspunkte not correct");
        }
        this.leistungsPunkte = leistungsPunkte2;
    }

    public void setSemester(int semester2) throws IllegalInputException{
        if (semester2 < 1 || semester2 > 12) {
            throw new IllegalInputException("Semester not correct");
        }
        this.semester = semester2;
    }

    public void setName(String name2) throws IllegalInputException{
        if (name2.length() < 1 || name2.contains("(")) {
            throw new IllegalInputException("Mindestens zwei Buchstaben, keine Klammern");
        }
        this.name = name2;
    }

    public void setAnzahlExams(int anzahlExams) {
        this.anzahlExams = anzahlExams;
    }

    public int getAnzahlExams() {
        return anzahlExams;
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
        } else if (!(o instanceof Lecture)) {
            return false;
        }
        Lecture e = (Lecture) o;
        if (e.getName().equals(this.getName()) && e.getSemester() == this.getSemester()) {
            return true;
        }
        return false;
    }

}