package data.exam;

public class Sheet {

    private Lecture lecture;
    private double points, maxPoints;
    private int number;

    public Sheet(Lecture lecture, int number, double points, double maxPoints) throws IllegalInputException {
        this.setLecture(lecture);
        this.setNumber(number);
        this.setPoints(points);
        this.setMaxPoints(maxPoints);
    }

    private void setPoints(double points) throws IllegalInputException {
        if (points < 0 )
            throw new IllegalInputException("Points must be positive");
        this.points = points;
    }

    private void setNumber(int number) throws IllegalInputException {
        if (number < 0 )
            throw new IllegalInputException("Number must be positive");
        this.number = number;
    }

    private void setMaxPoints(double maxPoints) throws IllegalInputException {
        if (maxPoints < 0)
            throw new IllegalInputException("Points must be positive");
        this.maxPoints = maxPoints;
    }

    private void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public String getName(){ return lecture.getName(); }
    public int getLeistungpunkte(){ return lecture.getLeistungpunkte(); }
    public int getSemester() { return lecture.getSemester(); }

    public Lecture getLecture() { return this.lecture; };
    public int getNumber() { return this.number; };
    public double getPoints() { return this.points; };
    public double getMaxPoints() { return this.maxPoints; };

    @Override
    public String toString() {
        return "Sheet [semester=" + lecture.getSemester() + ", name=" + lecture.getName() + ", number=" + this.getNumber() + ", points=" + this.getPoints()
                + ", maxPoints=" + this.getMaxPoints() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof Exam)) {
            return false;
        }
        Sheet e = (Sheet) o;
        if (e.getPoints() == this.getPoints() && e.getMaxPoints() == this.getMaxPoints() && e.getNumber() == this.getNumber() && e.getName().equals(this.getName()) && e.getSemester() == this.getSemester()) {
            return true;
        }
        return false;
    }
}