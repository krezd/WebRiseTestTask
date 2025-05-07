package ru.webrise.testtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.webrise.testtask.entity.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByUserId(long userId);

    @Query("SELECT s.name, COUNT(s) as count FROM subscriptions s GROUP BY s.name ORDER BY count DESC LIMIT 3")
    List<Object[]> findTop3PopularSubscriptions();
}
