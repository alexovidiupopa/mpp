package ro.ubb.spring.model;

import ro.ubb.spring.utils.Pair;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Assignment extends BaseEntity<Pair<Long, Long>> {

    private Double grade;

    public Assignment(Pair<Long, Long> _id) {
        super();
        super.setId(_id);
    }

    public Assignment(Pair<Long, Long> _id, double grade) {
        //super();
        super.setId(_id);
        this.grade = grade;
    }

    public Assignment() {

    }

    @Override
    public Pair<Long, Long> getId(){
        return super.getId();
    }

    /**
     * Sets the object's grade to the given parameter value.
     * @param grade - new grade
     */
    public void setGrade(Double grade) {
        this.grade = grade;
    }

    /**
     * Gets the grade.
     * @return Double - grade
     */
    public Double getGrade(){
        return this.grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment assignment = (Assignment) o;
        if(super.getId().equals(assignment.getId()))
            return (this.grade == ((Assignment) o).getGrade() || this.grade.equals(assignment.grade));
        else
            return false;
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
