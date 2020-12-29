package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository interface that provides methods that permit interaction with database rating table.
 *
 * @author Laura Habdul
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
}
