package Repository;

import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Validators.Validator;
import Utils.Sort;
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

public class LabProblemJDBCRepository extends DatabaseRepository<Long, LabProblem> {
    public LabProblemJDBCRepository(Validator<LabProblem> validator, String dbCredentialsFilename) {
        super(validator, dbCredentialsFilename);
    }

    @Override
    public Iterable<LabProblem> findAll(Sort sort) {
        return null;
    }

    @Override
    public Optional<LabProblem> add(LabProblem entity) throws ValidatorException, IOException, TransformerException, SAXException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        Connection connection = dbConnection();
        long id = entity.getId();
        String desc = entity.getDescription();
        int score = entity.getScore();
        String sql = "insert into Problems(id, description, score) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,id);
        preparedStatement.setString(2,desc);
        preparedStatement.setInt(3,score);
        try{
            preparedStatement.executeUpdate();
            return Optional.empty();
        }
        catch (SQLException se){
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public Optional<LabProblem> delete(Long id) throws IOException, TransformerException, ParserConfigurationException, SQLException {
        Connection connection = dbConnection();
        String sql = "delete from Problems where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,id);
        try{
            Optional<LabProblem> deleted = this.findById(id);
            preparedStatement.executeUpdate();
            return deleted;
        }
        catch (SQLException se){
            return Optional.empty();
        }
    }

    @Override
    public Optional<LabProblem> update(LabProblem entity) throws ValidatorException, IOException, TransformerException, ParserConfigurationException, SQLException {
        validator.validate(entity);
        Connection connection = dbConnection();
        long id = entity.getId();
        String desc = entity.getDescription();
        int score = entity.getScore();
        String sql = "update Problems set description=?, score=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(3,id);
        preparedStatement.setString(1,desc);
        preparedStatement.setInt(2,score);
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
    public Optional<LabProblem> findById(Long aLong) throws SQLException {
        LabProblem problem = null;
        Connection connection = dbConnection();
        String sql = "select * from Problems where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,aLong);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            problem = new LabProblem(resultSet.getLong("id"),resultSet.getString("description"),resultSet.getInt("score"));
        }
        return Optional.ofNullable(problem);
    }

    @Override
    public Iterable<LabProblem> getAll() throws SQLException {
        Set<LabProblem> problems = new HashSet<>();
        Connection connection = dbConnection();
        String sql = "select * from Problems";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Long id = resultSet.getLong("id");
            String desc = resultSet.getString("description");
            int score = resultSet.getInt("score");
            problems.add(new LabProblem(id,desc,score));
        }
        return problems;
    }
}
