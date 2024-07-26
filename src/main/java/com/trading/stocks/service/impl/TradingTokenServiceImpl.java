package com.trading.stocks.service.impl;

import com.trading.stocks.model.TradingSecrets;
import com.trading.stocks.repository.DailyTokenRepository;
import com.trading.stocks.repository.TradingSecretsRepository;
import com.trading.stocks.service.TradingTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradingTokenServiceImpl implements TradingTokenService {
    private final DailyTokenRepository dailyTokenRepository;
    private final TradingSecretsRepository tradingSecretsRepository;

    public TradingTokenServiceImpl(DailyTokenRepository dailyTokenRepository, TradingSecretsRepository tradingSecretsRepository) {
        this.dailyTokenRepository = dailyTokenRepository;
        this.tradingSecretsRepository = tradingSecretsRepository;
    }

    @Override
    public String getLatestToken() {
        return "";
    }

    @Override
    public String insertTradingToken() {
        TradingSecrets tradingSecrets = tradingSecretsRepository.findFirst();
        log.info("TradingSecrets: {}", tradingSecrets.toString());
        return tradingSecrets.toString();
    }
}
