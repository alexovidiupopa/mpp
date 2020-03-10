package Repository;

import Model.Exceptions.ValidatorException;
import Model.Assignment;
import Model.Validators.Validator;
import Utils.Pair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AssignmentFileRepository extends MemoryRepository<Pair<Long, Long>, Assignment> {

    private String fileName;

    public AssignmentFileRepository(Validator<Assignment> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
    }

    /**
     * Reads the assignments from the assignments file into memory.
     */
    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));
                long studentId = Long.parseLong(items.get(0));
                long problemId = Long.parseLong(items.get(1));
                Assignment assignment = new Assignment(new Pair<>(studentId, problemId));
                try {
                    super.add(assignment);
                }
                catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Assignment> add(Assignment entity) throws ValidatorException {
        Optional<Assignment> optional = super.add(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    /**
     * Saves the given LabProblem into file.
     * @param entity - valid LabProblem object.
     */
    private void saveToFile(Assignment entity) {
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + ","  + entity.getGrade());
            bufferedWriter.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
