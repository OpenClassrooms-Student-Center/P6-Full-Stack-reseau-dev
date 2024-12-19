package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Optional<Subscription> findByTopicIdAndUserId(Long topicId, Long userId);
    List<Subscription> findByUserId(Long userId);
}
