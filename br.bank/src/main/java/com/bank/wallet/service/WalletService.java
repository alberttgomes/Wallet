package com.bank.wallet.service;

import com.bank.wallet.Wallet;
import com.bank.wallet.constants.WalletType;
import com.bank.wallet.excepiton.WalletException;
import com.bank.wallet.persistence.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(Wallet wallet) {
        _validate(wallet);

        return walletRepository.save(wallet);
    }

    public void deleteWallet(long walletId) {
        walletRepository.findById(walletId).ifPresent(
                walletRepository::delete);
    }

    public List<Wallet> getAllWallets() {
        Iterable<Wallet> iterable = walletRepository.findAll();

        List<Wallet> walletList = new ArrayList<>();

        int index = 0;

        iterable.forEach(
                wallet -> walletList.add(index + 1,  wallet)
        );

        return walletList;
    }

    public Wallet getWalletById(long id) {
        Optional<Wallet> optionalWallet = walletRepository.findById(id);

        if (optionalWallet.isPresent()) {
            return optionalWallet.get();
        }
        else {
            throw new WalletException(
                    "Wallet not found with id: %s".formatted(id));
        }
    }

    private void _validate(Wallet wallet) throws WalletException {
        try {
            if (wallet.type() == WalletType.INACTIVE) {
                throw new WalletException(
                        "Invalid wallet type %s".formatted(wallet.type()));
            }

            boolean matches = _isMatches(wallet);

            if (!matches) {
                throw new WalletException(
                        "Invalid fields patterns %s".formatted(wallet));
            }

            if (!_passwordPolicy(wallet.password())) {
                throw new WalletException(
                        ("Invalid password policy. The password must be the pattern: " +
                                "[aaAbc#123] %s").formatted(wallet));
            }
        }
        catch (RuntimeException runtimeException) {
            throw new WalletException(runtimeException.getMessage());
        }
    }

    private static boolean _passwordPolicy(String password) {
        return password.matches("[a-zA-Z0-9]{6,16}");
    }

    private static boolean _isMatches(Wallet wallet) {
        boolean matches = wallet.email().matches(
                "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+]]");

        if (!matches) {
            throw new WalletException(
                    "Invalid email address to wallet owner %s"
                            .formatted(wallet.email()));
        }

        matches = wallet.fullName().matches("[A-z]");

        if (!matches) {
            throw new WalletException(
                    "Invalid full name to wallet owner %s".formatted(
                            wallet.fullName()));
        }

        // checking pattern to count id
        matches = wallet.countId().matches("([a-z]+-[0-9]+[a-z])");

        if (!matches) {
            throw new WalletException(
                    "Invalid count id wallet owner %s".formatted(
                            wallet.countId()));
        }

        return matches;
    }

    private final WalletRepository walletRepository;

}
