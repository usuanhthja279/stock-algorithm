package com.trading.stocks.controller;

import com.trading.stocks.dto.Login;
import com.trading.stocks.service.TradingTokenService;
import com.trading.stocks.util.AutomationUtil;
import com.upstox.ApiException;
import com.upstox.api.TokenResponse;
import io.swagger.client.api.LoginApi;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/login/v1")
@Slf4j
public class LoginController {
    private final TradingTokenService tradingTokenService;

    public LoginController(TradingTokenService tradingTokenService) {
        this.tradingTokenService = tradingTokenService;
    }

    @PostMapping(value = "/get-access", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAccess(@RequestBody Login login) {
        log.info("Request: {}", login.toString());
        String loginUri = "https://api.upstox.com/v2/login/authorization/dialog?" +
                "response_type=code" +
                "&client_id=" + login.getClientId() +
                "&redirect_uri=" + login.getRedirectUri() +
                "&state=" + login.getClientSecret();
        try {
            HttpResponse<String> response = Unirest.get(loginUri)
                    .asString();
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            log.error("Error while getting access", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(value = "/user-login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> LoginUser(@RequestBody Login login) {
        LoginApi apiInstance = new LoginApi();
        String apiVersion = "2.0";
        String clientId = login.getClientId();
        String redirectUri = login.getRedirectUri();
        String state = "state_example"; // String |
        String scope = "scope_example"; // String |


        try {
            apiInstance.authorize(clientId, redirectUri, apiVersion, state, scope);
            log.info("Successfully logged in");
            return ResponseEntity.ok("Successfully logged in");
        } catch (ApiException e) {
            System.err.println("Exception when calling LoginApi#token");
            log.error("{}", Arrays.toString(e.getStackTrace()));
            return ResponseEntity.status(e.getCode()).body(e.getResponseBody());
        }
    }

    @GetMapping(value = "/get-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getApplicationToken() {
        String token = tradingTokenService.insertTradingToken();
        return ResponseEntity.ok(token);
    }
}
