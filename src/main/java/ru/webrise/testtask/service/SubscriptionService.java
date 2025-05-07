package ru.webrise.testtask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.webrise.testtask.entity.Subscription;
import ru.webrise.testtask.entity.User;
import ru.webrise.testtask.repository.SubscriptionRepository;
import ru.webrise.testtask.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public Subscription createSubscription(Long userId, Subscription subscriptionRequest) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        Subscription subscription = new Subscription();
        subscription.setName(subscriptionRequest.getName());
        subscription.setDateSubscription(subscriptionRequest.getDateSubscription());
        subscription.setDateUnsubscription(subscriptionRequest.getDateUnsubscription());
        subscription.setUser(user);

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        log.info("Adding subscription for user with id: {}", userId);

        return savedSubscription;
    }

    public List<Subscription> getUserSubscriptions(Long userId) {
        if (!userRepository.existsById(userId)) {
            return null;
        }
        log.info("Find subscriptions for user with id: {}", userId);
        return subscriptionRepository.findAllByUserId(userId);
    }

    public Boolean deleteSubscription(Long userId, Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElse(null);
        if (subscription == null || !subscription.getUser().getId().equals(userId)) {
            return false;
        }
        log.info("Deleting subscription with id: {} for user with id: {}", subscriptionId, userId);
        subscriptionRepository.delete(subscription);
        return true;
    }

    public List<Map<String, Object>> getTop3PopularSubscriptions() {
        log.info("Fetching top 3 popular subscriptions");
        return subscriptionRepository.findTop3PopularSubscriptions().stream()
                .map(result -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", result[0]);
                    map.put("count", result[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }
}
