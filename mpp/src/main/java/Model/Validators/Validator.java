package Model.Validators;

import Model.Exceptions.ValidatorException;

public interface Validator<T> {

    /**
     * Validates a given entity.
     * @param entity - non-null
     * @throws ValidatorException if entity is not valid
     */
    void validate(T entity) throws ValidatorException;

}
