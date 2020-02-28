package Model.Validators;

import Model.Exceptions.ValidatorException;

public interface Validator<T> {

    void validate(T entity) throws ValidatorException;

}
