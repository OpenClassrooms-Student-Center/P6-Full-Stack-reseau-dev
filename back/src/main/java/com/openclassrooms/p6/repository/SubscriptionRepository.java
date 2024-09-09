package com.openclassrooms.p6.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.p6.model.Subscriptions;

public interface SubscriptionRepository extends JpaRepository<Subscriptions, Long> {
    Iterable<Subscriptions> findAllByUserId(Long userId);
}
