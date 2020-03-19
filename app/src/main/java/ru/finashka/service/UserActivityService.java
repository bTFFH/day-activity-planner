package ru.finashka.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.finashka.entity.Card;

public class UserActivityService {
    private List<Card> userCards = new ArrayList<>();

    public UserActivityService() {
        // remove it please in future
        userCards.add(new Card("Test title", "Test desct", new Date(), new Date()));
    }

    public void addUserCard(Card card) {
        userCards.add(card);
    }

    public List<Card> getUserCards() {
        return userCards;
    }
}
