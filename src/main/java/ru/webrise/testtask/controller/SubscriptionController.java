package ru.webrise.testtask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webrise.testtask.entity.Subscription;
import ru.webrise.testtask.service.SubscriptionService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{userId}/subscriptions")
    public ResponseEntity<Subscription> addSubscription(
            @PathVariable Long userId,
            @RequestBody Subscription subscription) {
        Subscription createdSubscription = subscriptionService.createSubscription(userId, subscription);
        if (createdSubscription == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(createdSubscription);
    }

    @GetMapping("/users/{userId}/subscriptions")
    public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable Long userId) {
        List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(userId);
        if (subscriptions == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/users/{userId}/subscriptions/{subscriptionId}")
    public ResponseEntity<?> deleteSubscription(
            @PathVariable Long userId,
            @PathVariable Long subscriptionId) {
        if(!subscriptionService.deleteSubscription(userId, subscriptionId)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<Map<String, Object>>> getTop3PopularSubscriptions() {
        List<Map<String, Object>> topSubscriptions = subscriptionService.getTop3PopularSubscriptions();
        return ResponseEntity.ok(topSubscriptions);
    }
}
