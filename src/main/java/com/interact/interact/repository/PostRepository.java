package com.interact.interact.repository;

import com.interact.interact.entity.Category;
import com.interact.interact.entity.Post;
import com.interact.interact.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Transactional()
    @Query(
           value = "select * from posts where title like :key",
            nativeQuery = true,
            name = "titleSearch"
    )
    List<Post> findByTitlesContaining(@Param("key") String keyword);
}
