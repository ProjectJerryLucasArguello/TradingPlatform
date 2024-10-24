package jla44.example.Trading.Platform.service;

import jla44.example.Trading.Platform.model.Order;
import jla44.example.Trading.Platform.model.User;
import jla44.example.Trading.Platform.model.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long money);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender,Wallet receiverWallet,Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;

}
