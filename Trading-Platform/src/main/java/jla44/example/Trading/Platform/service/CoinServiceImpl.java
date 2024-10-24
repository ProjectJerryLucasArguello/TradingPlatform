package jla44.example.Trading.Platform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jla44.example.Trading.Platform.model.Coin;
import jla44.example.Trading.Platform.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CoinServiceImpl implements CoinService{

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Coin> getCoinList(int page) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page="+page;

        RestTemplate restTemplate = new RestTemplate();

        try{
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            HttpEntity<String> response =restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            List<Coin> coinList = objectMapper.readValue(response.getBody(),
                    new TypeReference<List<Coin>>() {});
            return coinList;
        } catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getMarketChart(String coinId, int days) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/"+coinId+"/market_chart?vs_currency=usd&days="+days;

        RestTemplate restTemplate = new RestTemplate();

        try{
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            HttpEntity<String> response =restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getCoinDetails(String coinId) throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/"+coinId;

        RestTemplate restTemplate = new RestTemplate();

        try{
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            HttpEntity<String> response =restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            JsonNode jsonNode =objectMapper.readTree(response.getBody());

            Coin coin= new Coin();
            //4:18:39 keep note of this possible error may arise later
            coin.setId(Long.valueOf(jsonNode.get("id").asText()));
            coin.setName(jsonNode.get("name").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());
            coin.setImage(jsonNode.get("image").get("large").asText());

            JsonNode marketData=jsonNode.get("market_data");

            //4:20:... note the casting we had done
            coin.setCurrentPrice(BigDecimal.valueOf(marketData.get("current_price").get("usd").asDouble()));
            coin.setMarketCap(BigDecimal.valueOf(marketData.get("market_cap").get("usd").asLong()));
            coin.setMarketCapRank(marketData.get("market_cap_rank").asInt());
            coin.setTotalVolume(BigDecimal.valueOf(marketData.get("total_volume").get("usd").asLong()));
            coin.setHigh24h(BigDecimal.valueOf(marketData.get("high_24h").get("usd").asDouble()));
            coin.setLow24h(BigDecimal.valueOf(marketData.get("low_24h").get("usd").asDouble()));
            coin.setPriceChange24h(BigDecimal.valueOf(marketData.get("price_change2_4h").get("usd").asDouble()));
            coin.setPriceChangePercentage24h(BigDecimal.valueOf(marketData.get("price_change2_24h").get("usd").asDouble()));

            coin.setMarketCapChange24h(BigDecimal.valueOf(marketData.get("market_cap_change_24h").asLong()));

            coin.setMarketCapChangePercentage24h(BigDecimal.valueOf(marketData.get("market_cap_change_percent_24h").asLong()));

            coin.setTotalSupply(BigDecimal.valueOf(marketData.get("total_supply").get("usd").asLong()));

            coinRepository.save(coin);
            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Coin findById(String coinId) throws Exception {
        Optional<Coin> optionalCoin=coinRepository.findById(coinId);
        if(optionalCoin.isEmpty()) throw new Exception("coin new found");

        return optionalCoin.get();
    }

    @Override
    public String searchCoin(String keyword) throws Exception {
        String url="https://api.coingecko.com/api/v3/search?query"+keyword;

        RestTemplate restTemplate = new RestTemplate();

        try{
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            HttpEntity<String> response =restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getTop50CoinsByMarketCapRank() throws Exception {
        String url="https://api.coingecko.com/api/v3/coins/markets/vs_currency=usd&per_page=50&page=1";

        RestTemplate restTemplate = new RestTemplate();

        try{
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            HttpEntity<String> response =restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String GetTreadingCoins() throws Exception {
        String url="https://api.coingecko.com/api/v3/search/treading";

        RestTemplate restTemplate = new RestTemplate();

        try{
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            HttpEntity<String> response =restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e){
            throw new Exception(e.getMessage());
        }
    }
}
