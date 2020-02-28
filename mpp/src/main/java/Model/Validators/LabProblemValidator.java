package Model.Validators;

import Model.Exceptions.ValidatorException;
import Model.LabProblem;

public class LabProblemValidator implements Validator<LabProblem> {

    @Override
    public void validate(LabProblem entity) throws ValidatorException {
        if(entity.getNumber()<=0 || entity.getDescription().isEmpty() || entity.getDescription().length()>1000 || entity.getScore()<=0)
            throw new ValidatorException("Lab problem details incorrect correct.");

    }

}
