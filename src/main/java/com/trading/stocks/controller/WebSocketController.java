package com.trading.stocks.controller;

import com.trading.stocks.dto.Token;
import com.upstox.ApiClient;
import com.upstox.Configuration;
import com.upstox.auth.OAuth;
import com.upstox.feeder.MarketDataStreamer;
import com.upstox.feeder.constants.Mode;
import io.vertx.core.json.JsonObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/")
public class WebSocketController {

    @PostMapping("/start/web-socket")
    public String startWebSocket(@RequestBody Token token) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        Set<String> instrumentKeys = new HashSet<>();
        instrumentKeys.add("NSE_EQ|INE002A01018");

        OAuth oAuth = (OAuth) defaultClient.getAuthentication("OAUTH2");
        oAuth.setAccessToken(token.getToken());

        final MarketDataStreamer marketDataStreamer = new MarketDataStreamer(defaultClient, instrumentKeys, Mode.FULL);

        marketDataStreamer.setOnMarketUpdateListener(marketUpdate -> {
            System.out.println(LocalDateTime.now() + " " + JsonObject.mapFrom(marketUpdate));

        });
        marketDataStreamer.connect();

        return "WebSocket started successfully";
    }
}
