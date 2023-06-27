package ru.limon4etop.SpringBoot.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.limon4etop.SpringBoot.models.UserData;

import java.util.List;

@Repository
public interface userRepo extends JpaRepository<UserData, Long> {
    UserData findUserById(String id);
    UserData findUserByEmail(String email);
    UserData findUserByFirstName(String name);
    List<UserData> findAll();
}
