package ru.finashka.service;

import ru.finashka.entity.Card;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserActivityService {
    private List<Card> userCards = new ArrayList<>();

    public UserActivityService() {
        // remove it please in future
        userCards.add(new Card("Test title", "Test desct", LocalDateTime.now(), LocalDateTime.now()));
    }

    public void addUserCard(Card card) {
        userCards.add(card);
    }

    public List<Card> getUserCards() {
        return userCards;
    }

    public void removeCard(Card card) {
        userCards.remove(card);
    }
}
