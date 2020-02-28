package Model.Validators;

import Model.Exceptions.ValidatorException;
import Model.Student;

public class StudentValidator implements Validator<Student> {

    @Override
    public void validate(Student entity) throws ValidatorException {
        if (entity.getSerialNumber().isEmpty() || entity.getGroup()<=0 || entity.getGroup()>7 || entity.getName().isEmpty())
            throw new ValidatorException("Student details incorrect.");
    }

}
