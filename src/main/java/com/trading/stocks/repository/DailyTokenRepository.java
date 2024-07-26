package com.trading.stocks.repository;

import com.trading.stocks.model.DailyToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyTokenRepository extends CrudRepository<DailyToken, Long> {
    @Query("select c from DailyToken c where c.tradingDateTime = :name")
    List<DailyToken> findByTradingDateTime(@Param("name") String tradingDateTime);
}
