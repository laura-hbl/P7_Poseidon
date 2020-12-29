package com.nnk.springboot.repository;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository interface that provides methods that permit interaction with database rule_name table.
 *
 * @author Laura Habdul
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
