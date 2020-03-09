package Model;

import Utils.Pair;

import java.util.Objects;

public class Assignment extends BaseEntity<Pair<Long, Long>> {

    Double grade;

    public Assignment(Pair<Long, Long> _id) {
        super(_id);
    }

    public Assignment(Pair<Long, Long> _id, double grade) {
        super(_id);
        this.grade = grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Double getGrade(){
        return this.grade;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment assignment = (Assignment) o;
        return  (this.grade == assignment.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId().getFirst(), super.getId().getSecond(), this.grade);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "studentId='" + super.getId().getFirst() + '\'' +
                ", problemId='" + super.getId().getSecond() + '\'' +
                ", grade=" + this.grade +
                "} " + super.toString();
    }

}
