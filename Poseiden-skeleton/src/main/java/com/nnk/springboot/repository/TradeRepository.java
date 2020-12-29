package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository interface that provides methods that permit interaction with database trade table.
 *
 * @author Laura Habdul
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
