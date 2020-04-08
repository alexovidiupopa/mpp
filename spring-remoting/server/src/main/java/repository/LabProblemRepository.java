package repository;

import model.LabProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import utils.Sort;

import java.util.List;
import java.util.Optional;

public class LabProblemRepository implements RepositoryInterface<Long,LabProblem> {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Optional<LabProblem> add(LabProblem entity) {
        String sql = "insert into problems (id, description, score) values (?,?,?)";
        if(jdbcOperations.update(sql, entity.getId(), entity.getDescription(), entity.getScore()) > 0)
            return Optional.empty();
        else
            return Optional.of(entity);
    }

    @Override
    public Optional<LabProblem> delete(Long aLong) {
        String sql = "delete from problems where id=?";
        Optional<LabProblem> entity = this.findById(aLong);
        if(jdbcOperations.update(sql, aLong) == 0)
            return Optional.empty();
        else
            return entity;
    }

    @Override
    public Optional<LabProblem> update(LabProblem entity) {
        String sql = "update assignments set description=?, score=? where id=?";
        if(jdbcOperations.update(sql, entity.getDescription(), entity.getScore(), entity.getId()) > 0)
            return Optional.empty();
        else
            return Optional.of(entity);
    }

    @Override
    public Optional<LabProblem> findById(Long aLong) {
        String sql = "select * from problems where id=" + aLong;
        List<LabProblem> results = jdbcOperations.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String description = rs.getString("description");
            int score = rs.getInt("score");
            return new LabProblem(id, description, score);
        });
        if(results.size() == 0)
            return Optional.empty();
        else
            return Optional.of(results.get(0));
    }

    @Override
    public Iterable<LabProblem> getAll() {
        String sql = "select * from problems";
        return jdbcOperations.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String description = rs.getString("description");
            int score = rs.getInt("score");
            return new LabProblem(id, description, score);
        });
    }

    @Override
    public Iterable<LabProblem> getAll(Sort sort)   {
        return sort.sort(this.getAll());
    }
}
