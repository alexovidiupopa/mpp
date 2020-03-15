package Repository;

import Model.Student;
import Model.Validators.Validator;
import Model.Exceptions.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
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

public class StudentFileRepository extends MemoryRepository<Long, Student> {

    protected String fileName;

    public StudentFileRepository(Validator<Student> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the students from the students file into memory.
     */
    protected void loadData() throws IOException, SAXException, ParserConfigurationException {
        Path path = Paths.get(fileName);
        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));
                Long id = Long.valueOf(items.get(0));
                String serialNumber = items.get(1);
                String name = items.get((2));
                int group = Integer.parseInt(items.get(3));
                Student student = new Student(serialNumber, name, group);
                student.setId(id);
                try {
                    super.add(student);
            }
                catch (ValidatorException | NumberFormatException | IOException | TransformerException | SAXException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
            });
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Student> add(Student entity) throws ValidatorException, IOException, ParserConfigurationException, TransformerException, SAXException {
        Optional<Student> optional = super.add(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveAllToFile();
        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Long id) throws IOException, TransformerException, ParserConfigurationException {
        Optional<Student> optional = super.delete(id);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        saveAllToFile();
        return optional;
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException, IOException, TransformerException, ParserConfigurationException {
        Optional<Student> optional = super.update(entity);
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
                .map(e -> e.getId() + "," + e.getSerialNumber() + "," + e.getName() + "," + e.getGroup() + "\n")
                .reduce("", (s, e) -> s+e);
        bw.write(content);
        bw.close();
    }

}