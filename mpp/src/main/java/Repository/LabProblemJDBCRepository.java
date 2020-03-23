package Repository;

import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Validators.Validator;
import Utils.Sort;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Optional;

public class LabProblemJDBCRepository extends DatabaseRepository<Long, LabProblem> {
    public LabProblemJDBCRepository(Validator<LabProblem> validator, String dbCredentialsFilename) {
        super(validator, dbCredentialsFilename);
    }

    @Override
    public Iterable<LabProblem> findAll(Sort sort) {
        return null;
    }

    @Override
    public Optional<LabProblem> add(LabProblem entity) throws ValidatorException, IOException, TransformerException, SAXException, ParserConfigurationException {
        return Optional.empty();
    }

    @Override
    public Optional<LabProblem> delete(Long aLong) throws IOException, TransformerException, ParserConfigurationException {
        return Optional.empty();
    }

    @Override
    public Optional<LabProblem> update(LabProblem entity) throws ValidatorException, IOException, TransformerException, ParserConfigurationException {
        return Optional.empty();
    }

    @Override
    public Optional<LabProblem> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<LabProblem> getAll() {
        return null;
    }
}
