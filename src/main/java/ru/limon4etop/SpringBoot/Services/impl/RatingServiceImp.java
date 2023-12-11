package ru.limon4etop.SpringBoot.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.limon4etop.SpringBoot.Services.RatingService;
import ru.limon4etop.SpringBoot.models.Rating;
import ru.limon4etop.SpringBoot.repos.ratingRepo;

import java.util.List;

@Service
public class RatingServiceImp implements RatingService {
    private ratingRepo ratingRepo;

    @Autowired
    public RatingServiceImp(ratingRepo ratingRepo) {
        this.ratingRepo = ratingRepo;
    }

    @Override
    public void saveRatingData(Rating rating) {
        this.ratingRepo.save(rating);
    }

    @Override
    public Rating findByUserIdAndAddUserId(String userId, String addUserId) {
        return this.ratingRepo.findByUserIdAndAddUserId(userId, addUserId);
    }

    @Override
    public List<Rating> findByUserId(String userId) {
        return this.ratingRepo.findByUserId(userId);
    }
}
