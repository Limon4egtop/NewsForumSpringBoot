package ru.limon4etop.SpringBoot.Services;

import ru.limon4etop.SpringBoot.models.Rating;

import java.util.List;

public interface RatingService {
    void saveRatingData(final Rating rating);
    Rating findByUserIdAndAddUserId(final String userId,
                                    final String addUserId);

    List<Rating> findByUserId(final String userId);

}
