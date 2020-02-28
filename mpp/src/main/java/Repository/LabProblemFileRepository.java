package Repository;

import Model.LabProblem;
import Model.Validators.Validator;

public class LabProblemFileRepository extends MemoryRepository<Long, LabProblem> {

    private String fileName;

    public LabProblemFileRepository(Validator<LabProblem> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
    }

}
