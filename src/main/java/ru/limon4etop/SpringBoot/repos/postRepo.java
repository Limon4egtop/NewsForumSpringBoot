package ru.limon4etop.SpringBoot.repos;

import javax.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.limon4etop.SpringBoot.models.Post;

import java.util.List;

@Repository
public interface postRepo extends JpaRepository<Post, Long> {
    List<Post> findAll();
    List<Post> findAllByOrderByDateCreateDesc();
    List<Post> findByNewsCategoryOrderByDateCreate(String newsCategory);
    List<Post> findByNewsCategoryAndUserIdOrderByDateCreate(String newsCategory, String userId);
    Post findById(Integer id);
    List<Post> findByUserId(String userId);
}
