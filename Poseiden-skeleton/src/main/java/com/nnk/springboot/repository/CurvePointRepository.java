package com.nnk.springboot.repository;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository interface that provides methods that permit interaction with database curve_point table.
 *
 * @author Laura Habdul
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
}
