package com.trading.stocks.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Table(name = "daily_tokens", schema = "secrets")
@Setter
@Getter
@ToString
public class DailyToken {
    @Id
    private int id;
    @Column(name = "tokens_val")
    private transient String tokenValue;
    @Column(name = "trading_date_time")
    private Timestamp tradingDateTime;
}
