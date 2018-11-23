package com.exchange.exchange.repository;

import com.exchange.exchange.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    List<Exchange> findAllByCreatorId(Long creatorId);
}