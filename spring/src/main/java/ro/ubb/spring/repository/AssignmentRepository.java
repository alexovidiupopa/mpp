package ro.ubb.spring.repository;

import ro.ubb.spring.model.Assignment;
import ro.ubb.spring.utils.Pair;

public interface AssignmentRepository extends RepositoryInterface<Assignment, Pair<Long, Long>> {
}
