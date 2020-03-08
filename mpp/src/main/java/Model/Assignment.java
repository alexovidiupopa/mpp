package Model;

import Utils.Pair;

public class Assignment extends BaseEntity<Pair<Long, Long>> {

    Double grade;

    public Assignment(Pair<Long, Long> _id) {
        super(_id);
    }

    public Assignment(Pair<Long, Long> _id, double grade) {
        super(_id);
        this.grade = grade;
    }

    public void setGrade(double grade){
        this.grade = grade;
    }

    public Double getGrade(){
        return this.grade;
    }

}
