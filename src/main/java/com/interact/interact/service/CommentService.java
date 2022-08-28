package com.interact.interact.service;

import com.interact.interact.dto.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId);
    void deleteComment(Long commentId);
}
