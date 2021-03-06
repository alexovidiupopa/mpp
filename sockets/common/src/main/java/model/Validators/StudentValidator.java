package model.Validators;

import model.Exceptions.ValidatorException;
import model.Student;

public class StudentValidator implements Validator<Student> {

    /**
     * Validates a given student entity.
     * @param entity - non-null.
     * @throws ValidatorException if entity is invalid, meaning it doesn't have: group between [1,7], non-null serial number and non-null name.
     */
    @Override
    public void validate(Student entity) throws ValidatorException {
        String exceptionMsg="";
        if(entity.getSerialNumber().isEmpty())
            exceptionMsg+="Serial number cannot be empty.";
        if (entity.getName().isEmpty())
            exceptionMsg+="Name cannot be empty.";
        if (entity.getGroup()<=0 || entity.getGroup()>7)
            exceptionMsg+="Group must be between [1,7].";
        if(!exceptionMsg.isEmpty())
            throw new ValidatorException(exceptionMsg);

    }

}
