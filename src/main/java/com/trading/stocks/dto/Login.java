package com.trading.stocks.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Login {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
