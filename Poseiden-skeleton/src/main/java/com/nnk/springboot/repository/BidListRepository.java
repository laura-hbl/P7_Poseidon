package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository interface that provides methods that permit interaction with database bid_list table.
 *
 * @author Laura Habdul
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {
}
