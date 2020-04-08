package repository;

import model.Exceptions.ValidatorException;
import model.Student;
import model.Validators.Validator;
import utils.Sort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class StudentJDBCRepository extends DatabaseRepository<Long, Student> {

    public StudentJDBCRepository(Validator<Student> validator, String dbCredentialsFilename) {
        super(validator, dbCredentialsFilename);
    }

    @Override
    public Iterable<Student> getAll(Sort sort) throws SQLException, ClassNotFoundException {
        return sort.sort(this.getAll());
    }

    @Override
    public Optional<Student> add(Student entity) throws ValidatorException, SQLException {
        super.validator.validate(entity);
        Connection connection = dbConnection();
        long id = entity.getId();
        String sn = entity.getSerialNumber();
        String name = entity.getName();
        int group = entity.getGroup();
        String sql = "INSERT INTO Students(id, serialNumber, name, groupNumber) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, sn);
        preparedStatement.setString(3, name);
        preparedStatement.setInt(4, group);
        try{
            preparedStatement.executeUpdate();
            return Optional.empty();
        }
        catch (SQLException se){
            return Optional.of(entity);
        }
    }

    @Override
    public Optional<Student> delete(Long id) throws SQLException {
        Connection connection = dbConnection();
        String sql = "DELETE FROM Students WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,id);
        try{
            Optional<Student> deleted = this.findById(id);
            preparedStatement.executeUpdate();
            return deleted;
        }
        catch (SQLException se){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException, SQLException {
        super.validator.validate(entity);
        Connection connection = dbConnection();
        long id = entity.getId();
        String sn = entity.getSerialNumber();
        String name = entity.getName();
        int group = entity.getGroup();
        String sql = "UPDATE Students set serialNumber=?, name=?, groupNumber=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, sn);
        preparedStatement.setLong(4,id);
        preparedStatement.setString(2,name);
        preparedStatement.setInt(3,group);
        try{
            int affected = preparedStatement.executeUpdate();
            if (affected==0)
                return Optional.of(entity);
            else
                return Optional.empty();
        }
        catch (SQLException se){
            return Optional.of(entity);
        }
    }

    @Override
    public Optional<Student> findById(Long aLong) throws SQLException {
        Student student = null;
        Connection connection = dbConnection();
        String sql = "SELECT * FROM Students WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,aLong);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            student = new Student(resultSet.getLong("id"),resultSet.getString("serialNumber"),resultSet.getString("name"),resultSet.getInt("groupNumber"));
        }
        return Optional.ofNullable(student);
    }

    @Override
    public Iterable<Student> getAll() throws SQLException {
        Set<Student> studentSet = new HashSet<>();
        Connection connection = dbConnection();
        String sql = "SELECT * FROM Students";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            studentSet.add(new Student(resultSet.getLong("id"), resultSet.getString("serialNumber"), resultSet.getString("name"), resultSet.getInt("groupNumber")));
        }
        return studentSet;
    }

}
