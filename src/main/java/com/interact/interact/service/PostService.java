package com.interact.interact.service;

import com.interact.interact.dto.PostDto;
import com.interact.interact.dto.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto createPost(PostDto postDto,Long userId, Long catId);
    PostDto updatePost(PostDto postDto,Long postId);
    PostDto getPostById(Long postId);
    void deletePost(Long postId);
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    List<PostDto> getPostsByUser(Long userId);

    List<PostDto> getPostByCategory(Long  categoryId);

    List<PostDto> searchPosts(String keyWords);
}
