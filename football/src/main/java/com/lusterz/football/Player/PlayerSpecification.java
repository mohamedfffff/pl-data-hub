package com.lusterz.football.Player;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerSpecification {
    public static Specification<Player> filter(
            String name,
            String nation,
            String position,
            String team
    ) {
        return (Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"));
            }
            if (nation != null && !nation.isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("nation")),
                        nation.toLowerCase()));
            }
            if (position != null && !position.isEmpty()) {
                predicates.add(cb.equal(
                        cb.lower(root.get("position")),
                        position.toLowerCase()));
            }
            if (team != null && !team.isEmpty()) {
                predicates.add(cb.equal(
                        cb.lower(root.get("team")),
                        team.toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
