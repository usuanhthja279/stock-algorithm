package com.trading.stocks.util;

import com.trading.stocks.model.TradingSecrets;
import com.upstox.ApiException;
import com.upstox.api.TokenResponse;
import io.swagger.client.api.LoginApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

@Slf4j
public class TokenUtil {

    public static String getTokenFromUpstox(TradingSecrets tradingSecrets) {
        log.info("Request: {}", tradingSecrets.toString());
        String loginUri = "https://api.upstox.com/v2/login/authorization/dialog?" +
                "response_type=code" +
                "&client_id=" + tradingSecrets.getUpstoxClientId() +
                "&redirect_uri=" + tradingSecrets.getUpstoxRedirectUrl() +
                "&state=" + tradingSecrets.getUpstoxClientSecret();

        String codeFromUpstox = AutomationUtil.getCodeFromUpstox(loginUri);

        LoginApi apiInstance = new LoginApi();
        String apiVersion = "2.0";
        String clientId = tradingSecrets.getUpstoxClientId();
        String clientSecret = tradingSecrets.getUpstoxClientSecret();
        String redirectUri = tradingSecrets.getUpstoxRedirectUrl();
        String grantType = "authorization_code";
        try {
            TokenResponse result = apiInstance.token(apiVersion, codeFromUpstox, clientId, clientSecret, redirectUri, grantType);
            log.info("{}", result);
            return result.getAccessToken();
        } catch (ApiException e) {
            System.err.println("Exception when calling LoginApi#token");
            log.error("{}", Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e.getMessage());
        }
    }
}
