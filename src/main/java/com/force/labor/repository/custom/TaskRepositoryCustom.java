package com.force.labor.repository.custom;

import com.force.labor.domain.Task;
import com.force.labor.dto.FindTasksDTO;
import com.force.labor.dto.Sort;
import com.force.labor.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.SortOrder;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryCustom {

    private final EntityManager entityManager;

    public List<Task> find(FindTasksDTO.TCriteria criteria, Sort sort) throws EntityNotFoundException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> query = criteriaBuilder.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);

        List<Predicate> predicates = new ArrayList<>();

        ofNullable(criteria).ifPresent(tcriteria->{
            ofNullable(tcriteria.getIds()).ifPresent(option -> {
                CriteriaBuilder.In<BigInteger> inClause = criteriaBuilder.in(root.get("id"));
                option.forEach(inClause::value);
                predicates.add(inClause);
            });
            ofNullable(tcriteria.getEstimatedTimeInHours())
                    .ifPresent(option -> predicates.add(criteriaBuilder.greaterThan(root.get("estimatedTimeInHours"), option)));
            ofNullable(tcriteria.getDoneInPercents())
                    .ifPresent(option -> predicates.add(criteriaBuilder.greaterThan(root.get("doneInPercents"), option)));
            ofNullable(tcriteria.getDifficultyLevel())
                    .ifPresent(option -> predicates.add(criteriaBuilder.greaterThan(root.get("difficultyLevel"), option)));
            ofNullable(tcriteria.getPriority())
                    .ifPresent(option -> predicates.add(criteriaBuilder.greaterThan(root.get("priority"), option)));
            ofNullable(tcriteria.getCreatedSince())
                    .ifPresent(option -> predicates.add(criteriaBuilder.greaterThan(root.get("created"), option)));
            ofNullable(tcriteria.getCreatedBefore())
                    .ifPresent(option -> predicates.add(criteriaBuilder.lessThan(root.get("created"), option)));
            ofNullable(tcriteria.getTaskCostGreaterThan())
                    .ifPresent(option -> predicates.add(criteriaBuilder.lessThan(root.get("taskCost"), option)));
            ofNullable(tcriteria.getTaskCostLessThan())
                    .ifPresent(option -> predicates.add(criteriaBuilder.lessThan(root.get("taskCost"), option)));
        });

        ofNullable(sort)
                .ifPresent(sortingStrategy -> {
                    if (Arrays.stream(TaskDTO.class.getDeclaredFields())
                            .map(Field::getName)
                            .collect(Collectors.toList()).contains(sort.getSortBy())) {
                        if (sort.getType().equals(SortOrder.ASCENDING)) {
                            query.orderBy(criteriaBuilder.asc(root.get(sort.getSortBy())));
                        } else if (sort.getType().equals(SortOrder.DESCENDING)) {
                            query.orderBy(criteriaBuilder.desc(root.get(sort.getSortBy())));
                        }
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(
                                "Incorrect sorting strategy passed! Task does not contain field %s", sort.getSortBy()));
                    }
                });
        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}
