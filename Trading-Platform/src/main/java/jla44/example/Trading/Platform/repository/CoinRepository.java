package jla44.example.Trading.Platform.repository;

import jla44.example.Trading.Platform.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin,Integer> {

}
