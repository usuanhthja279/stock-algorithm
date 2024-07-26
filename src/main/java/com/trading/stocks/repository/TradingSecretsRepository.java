package com.trading.stocks.repository;

import com.trading.stocks.model.TradingSecrets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingSecretsRepository extends JpaRepository<TradingSecrets, Integer> {
    @Query("select c from TradingSecrets c")
    public TradingSecrets findFirst();
}
