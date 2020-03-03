package Model.Validators;

import Model.Exceptions.ValidatorException;
import Model.LabProblem;

public class LabProblemValidator implements Validator<LabProblem> {

    /**
     * Validates a given LabProblem entity.
     * @param entity - non-null
     * @throws ValidatorException if entity is invalid, meaning it havs: non-null description, description length less than 1000 or non-negative score.
     */
    @Override
    public void validate(LabProblem entity) throws ValidatorException {
        if(entity.getDescription().isEmpty() || entity.getDescription().length()>1000 || entity.getScore()<=0)
            throw new ValidatorException("Lab problem details incorrect correct.");
    }

}
