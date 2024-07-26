package com.trading.stocks.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "upstox_user", schema = "secrets")
@Setter
@Getter
@ToString
public class UpstoxUser {
    @Id
    private int id;
    @Column(name = "username")
    private transient String userName;
    @Column(name = "pass_key")
    private transient String passKey;
}
