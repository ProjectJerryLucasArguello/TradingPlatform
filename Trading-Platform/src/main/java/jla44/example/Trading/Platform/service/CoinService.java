package jla44.example.Trading.Platform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jla44.example.Trading.Platform.model.Coin;
import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page) throws Exception;

    String getMarketChart(String coinId, int days) throws Exception;

    String getCoinDetails(String coinId) throws Exception;

    Coin findById(String coinId);

    String searchCoin(String keyword);

    String getTop50CoinsByMarketCapRank();

    String GetTreadingCoins();
}
