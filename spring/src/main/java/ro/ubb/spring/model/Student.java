package ro.ubb.spring.model;

import javax.persistence.Entity;

@Entity
public class Student extends BaseEntity<Long>{

    private String serialNumber;
    private String name;
    private int groupNumber;

    public Student(String serialNumber, String name, int group) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.groupNumber = group;
    }

    public Student(long id,String serialNumber, String name, int group) {
        super();
        super.setId(id);
        this.serialNumber = serialNumber;
        this.name = name;
        this.groupNumber = group;
    }

    public Student() {

    }

    /**
     * Gets the serial number.
     * @return int - serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the object's serial number to the given parameter value.
     * @param serialNumber - new serial number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Gets the name.
     * @return String - name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the object's name to the given parameter value.
     * @param name - new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the group.
     * @return int - group
     */
    public int getGroup() {
        return groupNumber;
    }

    /**
     * Sets the object's group to the given parameter value.
     * @param group - new group
     */
    public void setGroup(int group) {
        this.groupNumber = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (groupNumber != student.groupNumber) return false;
        if (!serialNumber.equals(student.serialNumber)) return false;
        return name.equals(student.name);
    }

    @Override
    public int hashCode() {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + groupNumber;
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", group=" + groupNumber +
                "} " + super.toString();
    }

}
