package org.example.online_products_shop.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.Card;
import org.example.online_products_shop.domain.Product;
import org.example.online_products_shop.domain.SalesHistory;
import org.example.online_products_shop.domain.User;
import org.example.online_products_shop.exception.IsNotEnoughMoneyException;
import org.example.online_products_shop.exception.ProductNotFoundException;
import org.example.online_products_shop.repository.AvailableProductRepository;
import org.example.online_products_shop.repository.CardRepository;
import org.example.online_products_shop.repository.ProductRepository;
import org.example.online_products_shop.repository.SalesHistoryRepository;
import org.example.online_products_shop.repository.ShopRepository;
import org.example.online_products_shop.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesHistoryService {
    private final SalesHistoryRepository salesHistoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CardRepository cardRepository;
    private final AvailableProductRepository availableProductRepository;
    private final ShopRepository shopRepository;

    public SalesHistory save(Long productId, Long shopId, Integer count) {
        Integer password = new Random().nextInt(100000, 1000000);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Product product = checkBalance(productId, shopId, count);

        User user = userRepository.findByEmail(email);

        return salesHistoryRepository.save(
                SalesHistory.builder()
                        .checkPassword(password.toString())
                        .user(user)
                        .product(product)
                        .shop(shopRepository.findById(shopId).get())
                        .build()
        );
    }

    private Product checkBalance(Long productId, Long shopId, Integer count) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }
        BigDecimal price = BigDecimal.valueOf(availableProductRepository.findPriceByProductId(productId, shopId));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Card cardByUserId = cardRepository.findCardByUserId(userRepository.findByEmail(email).getId());

        if (cardByUserId.getSum().compareTo(price.multiply(BigDecimal.valueOf(count))) >= 0) {
            updateCardSumAndCountProduct(userRepository.findByEmail(email).getId(), price, productId, shopId);
            return product.get();
        }
        throw new IsNotEnoughMoneyException();
    }

    private void updateCardSumAndCountProduct(Long userId, BigDecimal price, Long productId, Long shopId) {
        cardRepository.updateCardSumByUserId(userId, price);
        availableProductRepository.updateCountByProductIdAndShopID(productId, shopId);
    }
}
