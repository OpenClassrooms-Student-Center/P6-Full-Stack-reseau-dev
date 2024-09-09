package com.openclassrooms.p6.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.p6.model.Subscriptions;
import com.openclassrooms.p6.repository.SubscriptionRepository;

import lombok.Data;

/**
 * Service class for managing subscriptions.
 */
@Data
@Service
public class SubscriptionsService {

    /**
     * Subscriptions repository to interact with the database and perform CRUD
     * operations.
     */
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    /**
     * Returns an {@link Optional} containing the {@link Subscriptions} entity
     * identified by the given id.
     *
     * @param id Identifier of the desired {@link Subscriptions} entity.
     * @return {@link Optional}<{@link Subscriptions}&gt; wrapper around the queried
     *         entity.
     */
    public Optional<Subscriptions> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    /**
     * Creates a new {@link Subscriptions} entity and persists it to the database.
     * Sets default values for the isSubscribed attribute.
     *
     * @param userId  Unique identifier of the user related to the subscription.
     * @param themeId Unique identifier of the theme related to the subscription.
     * @return Created and persisted {@link Subscriptions} entity.
     */
    public Subscriptions createSubscription(Long userId, Long themeId) {
        Subscriptions subscription = new Subscriptions();
        subscription.setUserId(userId);
        subscription.setThemeId(themeId);
        subscription.setIsSubscribed(true);

        return subscriptionRepository.save(subscription);
    }

    /**
     * Updates the value of the isSubscribed attribute for the provided
     * {@link Subscriptions} entity and saves it to the database.
     *
     * @param subscription Target {@link Subscriptions} entity whose isSubscribed
     *                     attribute should be modified.
     * @param isSubscribed Desired value for the isSubscribed attribute.
     */
    public void updateThemeSubscription(Subscriptions subscription, boolean isSubscribed) {
        subscription.setIsSubscribed(isSubscribed);

        subscriptionRepository.save(subscription);
    }

    /**
     * Queries all {@link Subscriptions} entities associated with the given user
     * identifier.
     *
     * @param userId Identifier of the user to search for.
     * @return All {@link Subscriptions} entities matched by the query.
     */
    public Iterable<Subscriptions> findAllUserSubscriptions(Long userId) {
        return subscriptionRepository.findAllByUserId(userId);
    }
}