package repository;

import model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import utils.Sort;

import java.util.List;
import java.util.Optional;

public class StudentRepository implements RepositoryInterface<Long, Student> {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Optional<Student> add(Student entity) {
        String sql = "insert into students (id, serialNumber, name, groupNumber) values (?,?,?,?)";
        if(jdbcOperations.update(sql, entity.getId(), entity.getSerialNumber(), entity.getName(), entity.getGroup()) > 0)
            return Optional.empty();
        else
            return Optional.of(entity);
    }

    @Override
    public Optional<Student> delete(Long aLong) {
        String sql = "delete from students where id=?";
        Optional<Student> entity = this.findById(aLong);
        if(jdbcOperations.update(sql, aLong.longValue()) == 0)
            return Optional.empty();
        else
            return entity;

    }

    @Override
    public Optional<Student> update(Student entity) {
        String sql = "update students set serialNumber=?, name=?, groupNumber=? where id=?";
        if(jdbcOperations.update(sql,  entity.getSerialNumber(), entity.getName(), entity.getGroup(),entity.getId()) > 0)
            return Optional.empty();
        else
            return Optional.of(entity);
    }

    @Override
    public Optional<Student> findById(Long aLong) {
        String sql = "select * from students where id=" + aLong;
        List<Student> results = jdbcOperations.query(sql,(rs, rowNum) -> {
            long id = rs.getLong("id");
            String serialNumber = rs.getString("serialNumber");
            String name = rs.getString("name");
            int group = rs.getInt("groupNumber");
            return new Student(id, serialNumber, name, group);
        });
        if(results.size() == 0)
            return Optional.empty();
        else
            return Optional.of(results.get(0));
    }

    @Override
    public Iterable<Student> getAll() {
        String sql = "select * from students";
        return jdbcOperations.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String serialNumber = rs.getString("serialNumber");
            String name = rs.getString("name");
            int group = rs.getInt("groupNumber");
            return new Student(id, serialNumber, name, group);
        });
    }

    @Override
    public Iterable<Student> getAll(Sort sort) {
        return sort.sort(this.getAll());
    }
}
