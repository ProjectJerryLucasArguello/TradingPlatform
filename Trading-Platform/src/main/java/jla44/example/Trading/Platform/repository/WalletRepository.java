package jla44.example.Trading.Platform.repository;

import jla44.example.Trading.Platform.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Wallet findByUserId(Long userId);
}
