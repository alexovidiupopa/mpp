package ro.ubb.spring.model;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class LabProblem extends BaseEntity<Long> {

    private String description;
    private int score;

    public LabProblem(Long _id, String description, int score) {
        super();
        super.setId(_id);
        this.description = description;
        this.score = score;
    }

    public LabProblem(String description, int score) {
        this.description = description;
        this.score = score;
    }

    public LabProblem() {

    }

    @Override
    public String toString() {
        return "LabProblem{" +
                "description='" + description + '\'' +
                ", score=" + score +
                '}' + super.toString();
    }

    /**
     * Gets the description.
     * @return problem description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the object's description to the given parameter value.
     * @param description - new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the score.
     * @return problem score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the object's score to the given parameter value.
     * @param score - new score
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabProblem that = (LabProblem) o;
        return score == that.score &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, score);
    }

}
