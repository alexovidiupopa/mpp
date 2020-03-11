package Repository;

import Model.Exceptions.ValidatorException;
import Model.Assignment;
import Model.Validators.Validator;
import Utils.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class AssignmentFileRepository extends MemoryRepository<Pair<Long, Long>, Assignment> {

    private String fileName;

    public AssignmentFileRepository(Validator<Assignment> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        this.loadData();
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
                double grade = Double.parseDouble(items.get(2));
                Assignment assignment = null;
                if(grade == -1)
                    assignment = new Assignment(new Pair<>(studentId, problemId));
                else
                    assignment = new Assignment(new Pair<>(studentId, problemId), grade);
                try {
                    super.add(assignment);
                }
                catch (ValidatorException | IOException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Assignment> add(Assignment entity) throws ValidatorException, IOException {
        Optional<Assignment> optional = super.add(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveAllToFile();
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

    /**
     * Saves all the current entities in the repository to the file.
     */
    private void saveAllToFile() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.fileName)));
        String content = StreamSupport.stream(super.getAll().spliterator(), false)
                .map(e -> {
                    if(e.getGrade() != null)
                        return Long.toString(e.getId().getFirst()) + "," + Long.toString(e.getId().getSecond()) + "," + Double.toString(e.getGrade()) + "\n";
                    else
                        return Long.toString(e.getId().getFirst()) + "," + Long.toString(e.getId().getSecond()) + "," + "-1\n";
                })
                .reduce("", (s, e) -> s+e);
        bw.write(content);
        bw.close();
    }

}
