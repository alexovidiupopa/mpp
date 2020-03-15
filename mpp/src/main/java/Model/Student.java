package Model;

public class Student extends BaseEntity<Long>{

    private String serialNumber;
    private String name;
    private int group;

    public Student(String serialNumber, String name, int group) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.group = group;
    }

    public Student(long id,String serialNumber, String name, int group) {
        super(id);
        this.serialNumber = serialNumber;
        this.name = name;
        this.group = group;
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
        return group;
    }

    /**
     * Sets the object's group to the given parameter value.
     * @param group - new group
     */
    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (group != student.group) return false;
        if (!serialNumber.equals(student.serialNumber)) return false;
        return name.equals(student.name);
    }

    @Override
    public int hashCode() {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + group;
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", group=" + group +
                "} " + super.toString();
    }

}
