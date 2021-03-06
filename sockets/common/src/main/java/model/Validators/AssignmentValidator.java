package model.Validators;


import model.Assignment;
import model.Exceptions.ValidatorException;

public class AssignmentValidator implements Validator<Assignment> {

    /**
     * Validates a given student entity.
     * @param entity - non-null.
     * @throws ValidatorException if entity is invalid, meaning it doesn't have: group between [1,7], non-null serial number and non-null name.
     */
    @Override
    public void validate(Assignment entity) throws ValidatorException {
        if (false)
            throw new ValidatorException("Student details incorrect.");
    }

}
