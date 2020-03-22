package Repository;

import Model.Assignment;
import Model.Exceptions.ValidatorException;
import Model.Validators.Validator;
import Utils.Sort;
import Utils.Pair;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Optional;

public class AssignmentsJDBCRepository extends DatabaseRepository<Pair<Long,Long>, Assignment> {
    public AssignmentsJDBCRepository(Validator<Assignment> validator, String dbCredentialsFilename) {
        super(validator, dbCredentialsFilename);
    }

    @Override
    public Iterable<Assignment> findAll(Sort sort) {
        return null;
    }

    @Override
    public Optional<Assignment> add(Assignment entity) throws ValidatorException, IOException, TransformerException, SAXException, ParserConfigurationException {
        return Optional.empty();
    }

    @Override
    public Optional<Assignment> delete(Pair<Long, Long> longLongPair) throws IOException, TransformerException, ParserConfigurationException {
        return Optional.empty();
    }

    @Override
    public Optional<Assignment> update(Assignment entity) throws ValidatorException, IOException, TransformerException, ParserConfigurationException {
        return Optional.empty();
    }

    @Override
    public Optional<Assignment> findById(Pair<Long, Long> longLongPair) {
        return Optional.empty();
    }

    @Override
    public Iterable<Assignment> getAll() {
        return null;
    }
}
