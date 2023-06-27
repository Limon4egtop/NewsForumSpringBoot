package ru.limon4etop.SpringBoot.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.limon4etop.SpringBoot.models.Comment;

@Repository
public interface commentRepo extends JpaRepository<Comment, Long> {

}
