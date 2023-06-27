package ru.limon4etop.SpringBoot.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.limon4etop.SpringBoot.models.Rating;
import ru.limon4etop.SpringBoot.models.Subscription;

import java.util.List;

@Repository
public interface subscriptionRepo extends JpaRepository<Subscription, Long> {
    Subscription findByFollowUserIdAndSubscribeUserId(String fUserId, String sUserId);
    List<Subscription> findByFollowUserId(String fUserId);
}
