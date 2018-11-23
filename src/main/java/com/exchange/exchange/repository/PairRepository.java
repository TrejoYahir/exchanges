package com.exchange.exchange.repository;

import com.exchange.exchange.model.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PairRepository extends JpaRepository<Pair, Long> {
    List<Pair> findAllByExchange(Long exchange);
}