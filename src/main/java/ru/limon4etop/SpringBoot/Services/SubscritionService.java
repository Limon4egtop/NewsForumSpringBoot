package ru.limon4etop.SpringBoot.Services;

import ru.limon4etop.SpringBoot.models.Subscription;

import java.util.List;

public interface SubscritionService {
    void saveSubscritionData(final Subscription subscription);
    void deleteSubscrition(final Subscription subscription);
    Subscription findSubscrByFollowUserIdAndSubscrUserId(final String followUserId,
                                                         final String subscrUserId);
    List<Subscription> getUserByFollowId(final String userId);
}
