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
@Table(name = "trading_secrets", schema = "secrets")
@Setter
@Getter
@ToString
public class TradingSecrets {
    @Id
    private int id;
    @Column(name = "upstox_client_id")
    private String upstoxClientId;
    @Column(name = "upstox_client_secret")
    private String upstoxClientSecret;
    @Column(name = "upstox_redirect_url")
    private String upstoxRedirectUrl;
}
