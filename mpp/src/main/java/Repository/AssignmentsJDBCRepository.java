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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AssignmentsJDBCRepository extends DatabaseRepository<Pair<Long,Long>, Assignment> {
    public AssignmentsJDBCRepository(Validator<Assignment> validator, String dbCredentialsFilename) {
        super(validator, dbCredentialsFilename);
    }

    @Override
    public Iterable<Assignment> findAll(Sort sort) {
        return null;
    }

    @Override
    public Optional<Assignment> add(Assignment entity) throws ValidatorException, IOException, TransformerException, SAXException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        Connection connection = dbConnection();
        Pair<Long, Long> id = entity.getId();
        Double grade = entity.getGrade();
        String sql = "insert into problemsdb.db_schema.assignments(sid, aid) values (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,id.getFirst());
        preparedStatement.setLong(2,id.getSecond());
        try{
            preparedStatement.executeUpdate();
            return Optional.empty();
        }
        catch (SQLException se){
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public Optional<Assignment> delete(Pair<Long, Long> longLongPair) throws IOException, TransformerException, ParserConfigurationException, SQLException {
        Connection connection = dbConnection();
        String sql = "delete from problemsdb.db_schema.assignments where sid=? and aid=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,longLongPair.getFirst());
        preparedStatement.setLong(2,longLongPair.getSecond());
        try{
            Optional<Assignment> deleted = this.findById(longLongPair);
            preparedStatement.executeUpdate();
            return deleted;
        }
        catch (SQLException se){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Assignment> update(Assignment entity) throws ValidatorException, IOException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        Connection connection = dbConnection();
        Pair<Long, Long> id = entity.getId();
        Double grade = entity.getGrade();
        String sql = "update problemsdb.db_schema.assignments set grade=? where sid=? and aid=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1,grade);
        preparedStatement.setLong(2,id.getFirst());
        preparedStatement.setLong(3,id.getSecond());
        try{
            int affected = preparedStatement.executeUpdate();
            if (affected==0)
                return Optional.ofNullable(entity);
            return Optional.empty();
        }
        catch (SQLException se){
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public Optional<Assignment> findById(Pair<Long, Long> longLongPair) throws SQLException {
        Assignment assignment = null;
        Connection connection = dbConnection();
        String sql = "select * from problemsdb.db_schema.assignments where sid=? and aid=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,longLongPair.getFirst());
        preparedStatement.setLong(2,longLongPair.getSecond());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            assignment = new Assignment(new Pair<>(resultSet.getLong("sid"),resultSet.getLong("aid")),resultSet.getDouble("grade"));
        }
        return Optional.ofNullable(assignment);
    }

    @Override
    public Iterable<Assignment> getAll() throws SQLException {
        Set<Assignment> assignments = new HashSet<>();
        Connection connection = dbConnection();
        String sql = "select * from problemsdb.db_schema.assignments";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Long sid = resultSet.getLong("sid");
            Long aid = resultSet.getLong("aid");
            Double grade = resultSet.getDouble("grade");
            assignments.add(new Assignment(new Pair<>(sid, aid), grade));
        }
        return assignments;
    }
}
