package ru.limon4etop.SpringBoot.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.limon4etop.SpringBoot.Services.SubscritionService;
import ru.limon4etop.SpringBoot.models.Subscription;
import ru.limon4etop.SpringBoot.repos.subscriptionRepo;

import java.util.List;

@Service
public class SubscritionServiceImp implements SubscritionService {
    private subscriptionRepo subscriptionRepo;

    @Autowired
    public SubscritionServiceImp(final subscriptionRepo subscriptionRepo) {
        this.subscriptionRepo = subscriptionRepo;
    }

    @Override
    public void saveSubscritionData(final Subscription subscription) {
        this.subscriptionRepo.save(subscription);
    }

    @Override
    public void deleteSubscrition(Subscription subscription) {
        this.subscriptionRepo.delete(subscription);
    }

    @Override
    public Subscription findSubscrByFollowUserIdAndSubscrUserId(String followUserId, String subscrUserId) {
        return this.subscriptionRepo.findByFollowUserIdAndSubscribeUserId(followUserId, subscrUserId);
    }

    @Override
    public List<Subscription> getUserByFollowId(String userId) {
        return this.subscriptionRepo.findByFollowUserId(userId);
    }
}
