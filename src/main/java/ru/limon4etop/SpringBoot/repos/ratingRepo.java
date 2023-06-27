package ru.limon4etop.SpringBoot.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.limon4etop.SpringBoot.models.Rating;
import ru.limon4etop.SpringBoot.models.UserData;

import java.util.List;

@Repository
public interface ratingRepo extends JpaRepository<Rating, Long> {
    List<Rating> findAll();
    Rating findById(Integer id);
    Rating findByUserIdAndAddUserId(String userId, String addUserId);
    List<Rating> findByUserId(String userId);
}
