package com.force.labor.repository.custom;

import com.force.labor.domain.Employee;
import com.force.labor.dto.EmployeeDTO;
import com.force.labor.dto.FindEmployeeDTO;
import com.force.labor.dto.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
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
public class EmployeeRepositoryCustom {

    private final EntityManager entityManager;

    public List<Employee> find(FindEmployeeDTO.ECriteria criteria, Sort sort) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        List<Predicate> predicates = new ArrayList<>();

        ofNullable(criteria.getIds()).ifPresent(option -> {
            CriteriaBuilder.In<BigInteger> inClause = criteriaBuilder.in(root.get("id"));
            option.forEach(inClause::value);
            predicates.add(inClause);
        });

        ofNullable(criteria.getFirstName())
                .ifPresent(option -> predicates.add(criteriaBuilder.equal(root.get("firstName"), option)));
        ofNullable(criteria.getLastName())
                .ifPresent(option -> predicates.add(criteriaBuilder.equal(root.get("lastName"), option)));
        ofNullable(criteria.getGrade())
                .ifPresent(option -> predicates.add(criteriaBuilder.equal(root.get("grade"), option)));
        ofNullable(criteria.getPassport())
                .ifPresent(option -> predicates.add(criteriaBuilder.equal(root.get("passport"), option)));
        ofNullable(criteria.getSalaryPerHourGreaterThan())
                .ifPresent(option -> predicates.add(criteriaBuilder.greaterThan(root.get("salaryPerHour"), option)));
        ofNullable(criteria.getSalaryPerHourLessThan())
                .ifPresent(option -> predicates.add(criteriaBuilder.lessThan(root.get("salaryPerHour"), option)));

        ofNullable(sort)
                .ifPresent(sortingStrategy -> {
                    if (Arrays.stream(EmployeeDTO.class.getDeclaredFields())
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
