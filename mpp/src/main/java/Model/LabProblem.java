package Model;

import java.util.Objects;

public class LabProblem extends BaseEntity<Long> {
    private int number;
    private String description;
    private int score;

    public LabProblem(Long _id, int number, String description, int score) {
        super(_id);
        this.number = number;
        this.description = description;
        this.score = score;
    }

    public LabProblem() {
    }

    public LabProblem(int number, String description, int score) {
        this.number = number;
        this.description = description;
        this.score = score;
    }

    @Override
    public String toString() {
        return "LabProblem{" +
                "number=" + number +
                ", description='" + description + '\'' +
                ", score=" + score +
                '}' + super.toString();
    }

    /**
     * Gets the number.
     * @return problem number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the object's number to the given parameter value.
     * @param number - new number
     */
    public void setNumber(int number) {
        this.number = number;
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
        return number == that.number &&
                score == that.score &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, description, score);
    }

}
