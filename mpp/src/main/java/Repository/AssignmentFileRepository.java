package Repository;

import Model.Exceptions.ValidatorException;
import Model.Assignment;
import Model.Validators.Validator;
import Utils.Pair;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class AssignmentFileRepository extends MemoryRepository<Pair<Long, Long>, Assignment> {

    protected String fileName;

    public AssignmentFileRepository(Validator<Assignment> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        try {
            this.loadData();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the assignments from the assignments file into memory.
     */
    protected void loadData() throws IOException, SAXException, ParserConfigurationException {
        Path path = Paths.get(fileName);
        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));
                long studentId = Long.parseLong(items.get(0));
                long problemId = Long.parseLong(items.get(1));
                double grade = Double.parseDouble(items.get(2));
                Assignment assignment;
                if(grade == -1)
                    assignment = new Assignment(new Pair<>(studentId, problemId));
                else
                    assignment = new Assignment(new Pair<>(studentId, problemId), grade);
                try {
                    super.add(assignment);
                }
                catch (ValidatorException | IOException | TransformerException | SAXException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Assignment> add(Assignment entity) throws ValidatorException, IOException, ParserConfigurationException, TransformerException, SAXException {
        Optional<Assignment> optional = super.add(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveAllToFile();
        return Optional.empty();
    }

    @Override
    public Optional<Assignment> delete(Pair<Long,Long> id) throws IOException, TransformerException, ParserConfigurationException {
        Optional<Assignment> optional = super.delete(id);
        if (!optional.isPresent())
            return Optional.empty();
        saveAllToFile();
        return optional;
    }

    @Override
    public Optional<Assignment> update(Assignment entity) throws ValidatorException, IOException, TransformerException, ParserConfigurationException {
        Optional<Assignment> optional = super.update(entity);
        if(optional.isPresent())
            return optional;
        saveAllToFile();
        return Optional.empty();
    }


    /**
     * Saves all the current entities in the repository to the file.
     */
    protected void saveAllToFile() throws IOException, ParserConfigurationException, TransformerException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.fileName)));
        String content = StreamSupport.stream(super.getAll().spliterator(), false)
                .map(e -> {
                    if(e.getGrade() != null)
                        return e.getId().getFirst() + "," + e.getId().getSecond() + "," + e.getGrade() + "\n";
                    else
                        return e.getId().getFirst() + "," + e.getId().getSecond() + "," + "-1\n";
                })
                .reduce("", (s, e) -> s+e);
        bw.write(content);
        bw.close();
    }

}


