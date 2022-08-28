package com.interact.interact.impl;

import com.interact.interact.dto.PostDto;
import com.interact.interact.dto.PostResponse;
import com.interact.interact.entity.Category;
import com.interact.interact.entity.Post;
import com.interact.interact.entity.User;
import com.interact.interact.exception.ResourceNotFoundException;
import com.interact.interact.repository.CategoryRepository;
import com.interact.interact.repository.PostRepository;
import com.interact.interact.repository.UserRepository;
import com.interact.interact.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long catId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> {return new ResourceNotFoundException("User","userId",userId);});
        //log.info(String.valueOf(user));
        Category category = this.categoryRepository.findById(catId).orElseThrow(() -> {return new ResourceNotFoundException("Category","categoryId",catId);});
        //log.info(String.valueOf(category));
        Post post = this.modelMapper.map(postDto,Post.class);
        //log.info(String.valueOf(post));
        post.setImageUrl("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post createdPost = this.postRepository.save(post);
        //log.info(String.valueOf(post));
        return this.modelMapper.map(createdPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> {return new ResourceNotFoundException("Post","postId",postId);});
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageUrl(postDto.getImageUrl());
        //Category category = this.categoryRepository.findById(postDto.getCategory().getCategoryId()).orElseThrow(() -> {return new ResourceNotFoundException("Category","categoryId",postDto.getCategory().getCategoryId());});
        //post.setCategory(category);
        return this.modelMapper.map(this.postRepository.save(post),PostDto.class);

    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post =  this.postRepository.findById(postId).orElseThrow(() -> {return new ResourceNotFoundException("Post","postId",postId);});
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> {return new ResourceNotFoundException("Post","postId",postId);});
        this.postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable  p = PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePosts = this.postRepository.findAll(p);
        List<Post> posts = pagePosts.getContent();
//        List<Post> posts = this.postRepository.findAll();
        //log.info(Arrays.toString(posts.toArray()));
        List<PostDto> postDtos = posts.stream().map((post)->{return this.modelMapper.map(post,PostDto.class);}).collect(Collectors.toList());
        return new PostResponse(postDtos,pagePosts.getNumber(),pagePosts.getSize(),pagePosts.getTotalElements(),pagePosts.getTotalPages(),pagePosts.isLast());
    }

    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> {return new ResourceNotFoundException("User","userId",userId);});
        List<Post> posts = this.postRepository.findByUser(user);
        return posts.stream().map(
                    (post)->{return this.modelMapper.map(post,PostDto.class);}
                ).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByCategory(Long catId) {
        Category category = this.categoryRepository.findById(catId).orElseThrow(() -> {return new ResourceNotFoundException("Category","categoryId",catId);});
        //Pageable p = PageRequest.of(pageNumber,pageSize);
        List<Post> posts = this.postRepository.findByCategory(category);

        return posts.stream().map((post)->{
                    return  this.modelMapper.map(post,PostDto.class);
                }).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyWords) {
        List<Post> posts = this.postRepository.findByTitlesContaining("%"+keyWords+"%");
        return posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }
}
