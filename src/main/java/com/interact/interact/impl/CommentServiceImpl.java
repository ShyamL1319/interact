package com.interact.interact.impl;

import com.interact.interact.dto.CommentDto;
import com.interact.interact.entity.Comment;
import com.interact.interact.entity.Post;
import com.interact.interact.exception.ResourceNotFoundException;
import com.interact.interact.repository.CommentRepository;
import com.interact.interact.repository.PostRepository;
import com.interact.interact.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> {return  new ResourceNotFoundException("Post","postID",postId);
        });
        Comment comment = this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepository.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->{return new ResourceNotFoundException("Comment","commentId",commentId);});
        this.commentRepository.delete(comment);
    }
}
