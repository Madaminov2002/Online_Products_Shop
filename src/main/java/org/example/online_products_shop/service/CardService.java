package org.example.online_products_shop.service;

import java.math.BigDecimal;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.example.online_products_shop.domain.Card;
import org.example.online_products_shop.domain.User;
import org.example.online_products_shop.dto.CardDto;
import org.example.online_products_shop.repository.CardRepository;
import org.example.online_products_shop.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public Card dtoToEntity(CardDto cardDto) {
        /**
         * temporarily adding money to the card
         */
        double sum = new Random().nextDouble(10.00,1000.00);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        return Card.builder()
                .number(cardDto.getCardNumber())
                .sum(BigDecimal.valueOf(sum))
                .user(user)
                .build();
    }

    public Card save(CardDto cardDto) {
        return cardRepository.save(dtoToEntity(cardDto));
    }

    public Card showCard() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByEmail(email).getId();
        return cardRepository.findCardByUserId(userId);
    }
}
