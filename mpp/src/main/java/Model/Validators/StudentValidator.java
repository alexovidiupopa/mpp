package Model.Validators;

import Model.Exceptions.ValidatorException;
import Model.Student;

public class StudentValidator implements Validator<Student> {
    /**
     * Validates a given student entity.
     * @param entity - non-null.
     * @throws ValidatorException if entity is invalid, meaning it doesn't have: group between [1,7], non-null serial number and non-null name.
     */
    @Override
    public void validate(Student entity) throws ValidatorException {
        if (entity.getSerialNumber().isEmpty() || entity.getGroup()<=0 || entity.getGroup()>7 || entity.getName().isEmpty())
            throw new ValidatorException("Student details incorrect.");

    }

}
