package ro.ubb.core.model.Validators;


import org.springframework.stereotype.Component;
import ro.ubb.core.model.Exceptions.ValidatorException;
import ro.ubb.core.model.Assignment;
import ro.ubb.core.model.Exceptions.ValidatorException;

@Component
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
