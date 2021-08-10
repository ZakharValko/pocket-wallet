package ua.zakharvalko.springbootdemo.domain.specification;

import org.springframework.data.jpa.domain.Specification;
import ua.zakharvalko.springbootdemo.domain.Operation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OperationSpecifications implements Specification<Operation> {

    private final List<SearchCriteria> list;

    public OperationSpecifications() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Operation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(criteriaBuilder.equal(
                        root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.JOIN)) {
                predicates.add(criteriaBuilder.equal(
                        root.join(criteria.getKey().split("/")[0]).join(criteria.getKey().split("/")[1]).get("id"), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.AFTER)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), (Date)criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.BEFORE)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), (Date)criteria.getValue()));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
