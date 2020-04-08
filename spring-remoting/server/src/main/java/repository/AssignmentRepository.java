package repository;

import model.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import utils.Pair;
import utils.Sort;

import java.util.List;
import java.util.Optional;

public class AssignmentRepository implements RepositoryInterface<Pair<Long, Long>, Assignment> {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Optional<Assignment> add(Assignment entity) {
        String sql = "insert into assignments (sid, aid, grade) values (?,?,?)";
        if(jdbcOperations.update(sql, entity.getId().getFirst(), entity.getId().getSecond(), entity.getGrade()) > 0)
            return Optional.empty();
        else
            return Optional.of(entity);
    }

    @Override
    public Optional<Assignment> delete(Pair<Long, Long> longLongPair) {
        String sql = "delete from assignments where sid=? and aid=?";
        Optional<Assignment> entity = this.findById(longLongPair);
        if(jdbcOperations.update(sql, longLongPair.getFirst(), longLongPair.getSecond()) == 0)
            return Optional.empty();
        else
            return entity;
    }

    @Override
    public Optional<Assignment> update(Assignment entity) {
        String sql = "update assignments set grade=? where sid=? and aid=?";
        if(jdbcOperations.update(sql, entity.getGrade(), entity.getId().getFirst(), entity.getId().getSecond()) > 0)
            return Optional.empty();
        else
            return Optional.of(entity);
    }

    @Override
    public Optional<Assignment> findById(Pair<Long, Long> longLongPair) {
        String sql = "select * from assignments where sid=" + longLongPair.getFirst() + "and aid="+ longLongPair.getSecond();
        List<Assignment> results = jdbcOperations.query(sql, (rs, rowNum) -> {
            long studentId = rs.getLong("sid");
            long problemId = rs.getLong("aid");
            double grade = rs.getLong("grade");
            return new Assignment(new Pair<>(studentId, problemId), grade);
        });
        if(results.size() == 0)
            return Optional.empty();
        else
            return Optional.of(results.get(0));
    }

    @Override
    public Iterable<Assignment> getAll() {
        String sql = "select * from assignments";
        return jdbcOperations.query(sql, (rs, rowNum) -> {
            long studentId = rs.getLong("sid");
            long problemId = rs.getLong("aid");
            double grade = rs.getLong("grade");
            return new Assignment(new Pair<>(studentId, problemId), grade);
        });
    }

    @Override
    public Iterable<Assignment> getAll(Sort sort)  {
        return sort.sort(this.getAll());
    }
}
