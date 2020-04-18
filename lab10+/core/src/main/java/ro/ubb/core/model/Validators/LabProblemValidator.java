package ro.ubb.core.model.Validators;

import org.springframework.stereotype.Component;
import ro.ubb.core.model.Exceptions.ValidatorException;
import ro.ubb.core.model.LabProblem;

@Component
public class LabProblemValidator implements Validator<LabProblem> {

    /**
     * Validates a given LabProblem entity.
     * @param entity - non-null
     * @throws ValidatorException if entity is invalid, meaning it havs: non-null description, description length less than 1000 or non-negative score.
     */
    @Override
    public void validate(LabProblem entity) throws ValidatorException {
        String exceptionMsg = "";
        if (entity.getDescription().isEmpty())
            exceptionMsg+="Empty description.";
        if(entity.getDescription().length()>1000)
            exceptionMsg+="Description length too large.";
        if(entity.getScore()<=0)
            exceptionMsg+="Cannot have a negative score.";
        if (!exceptionMsg.isEmpty())
            throw new ValidatorException(exceptionMsg);
    }

}
