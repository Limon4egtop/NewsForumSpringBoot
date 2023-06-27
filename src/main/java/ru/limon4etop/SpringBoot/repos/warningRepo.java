package ru.limon4etop.SpringBoot.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.limon4etop.SpringBoot.models.Warning;

import java.util.List;

@Repository
public interface warningRepo extends JpaRepository<Warning, Long> {
    List<Warning> findByUserGetNotification(String userId);
    List<Warning> findAll();
}
