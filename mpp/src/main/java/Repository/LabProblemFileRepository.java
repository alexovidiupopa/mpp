package Repository;

import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Student;
import Model.Validators.Validator;

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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LabProblemFileRepository extends MemoryRepository<Long, LabProblem> {

    private String fileName;

    public LabProblemFileRepository(Validator<LabProblem> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    /**
     * Reads the lab problems from the lab problems file into memory.
     */
    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));
                Long id = Long.valueOf(items.get(0));
                String description = items.get(1);
                int score = Integer.parseInt(items.get(2));
                LabProblem problem = new LabProblem(id,description,score);
                try {
                    super.add(problem);
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
    public Optional<LabProblem> add(LabProblem entity) throws ValidatorException, IOException {
        Optional<LabProblem> optional = super.add(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveAllToFile();
        return Optional.empty();
    }

    @Override
    public Optional<LabProblem> delete(Long id) throws IOException {
        Optional<LabProblem> optional = super.delete(id);
        if (!optional.isPresent())
            return Optional.empty();
        saveAllToFile();
        return optional;
    }

    @Override
    public Optional<LabProblem> update(LabProblem entity) throws ValidatorException, IOException {
        Optional<LabProblem> optional = super.update(entity);
        if(optional.isPresent())
            return optional;
        saveAllToFile();
        return Optional.empty();
    }

    /**
     * Saves all the current entities in the repository to the file.
     */
    private void saveAllToFile() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.fileName)));
        String content = StreamSupport.stream(super.getAll().spliterator(), false)
                .map(e -> Long.toString(e.getId()) + "," + e.getDescription() + "," + Integer.toString(e.getScore()) + "\n")
                .reduce("", (s, e) -> s+e);
        bw.write(content);
        bw.close();
    }

    /**
     * Saves the given LabProblem into file.
     * @param entity - valid LabProblem object.
     */
    private void saveToFile(LabProblem entity) {
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    "\n" + entity.getId() + ","  + entity.getDescription() + "," + entity.getScore());
            bufferedWriter.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}