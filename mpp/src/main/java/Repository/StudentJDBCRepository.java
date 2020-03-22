package Repository;

import Model.Exceptions.ValidatorException;
import Model.Student;
import Model.Validators.Validator;
import Utils.Sort;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Optional;

public class StudentJDBCRepository extends DatabaseRepository<Long, Student> {
    public StudentJDBCRepository(Validator<Student> validator, String dbCredentialsFilename) {
        super(validator, dbCredentialsFilename);
    }

    @Override
    public Iterable<Student> findAll(Sort sort) {
        return null;
    }

    @Override
    public Optional<Student> add(Student entity) throws ValidatorException, IOException, TransformerException, SAXException, ParserConfigurationException {
        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Long aLong) throws IOException, TransformerException, ParserConfigurationException {
        return Optional.empty();
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException, IOException, TransformerException, ParserConfigurationException {
        return Optional.empty();
    }

    @Override
    public Optional<Student> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Student> getAll() {
        return null;
    }
}
