package com.interact.interact.repository;
import com.interact.interact.entity.Comment;
import com.interact.interact.entity.Post;
import com.interact.interact.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

//    List<Comment> findAllByUser(User user);
}