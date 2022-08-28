package com.interact.interact.controller;

import com.interact.interact.config.AppConstants;
import com.interact.interact.dto.ApiResponse;
import com.interact.interact.dto.PostDto;
import com.interact.interact.dto.PostResponse;
import com.interact.interact.repository.PostRepository;
import com.interact.interact.service.FileService;
import com.interact.interact.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostController {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/images";

    @Autowired
    private FileService fileService;

    @Autowired
    private PostService postService;

    @Value("${project.image}")
    private  String path;

    @PostMapping("/user/{userId}/category/{categoryId}")
    private ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long userId, @PathVariable() Long categoryId){
        PostDto createdPost  = this.postService.createPost(postDto,userId,categoryId);
        //log.info(String.valueOf(createdPost));
        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    private ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Long postId){
        PostDto postDto1 = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(postDto1,HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    private ResponseEntity<PostDto> getPostById(@PathVariable("postId") Long postId){
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    private ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Long postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully",200,true),HttpStatus.OK);
    }

    @GetMapping("/")
    private ResponseEntity<PostResponse>getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue =AppConstants.SORT_DIR,required = false) String sortDir
    ){
        PostResponse posts = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/category/{catId}")
    private ResponseEntity<List<PostDto>> getPostsByCategory(
            @PathVariable("catId") Long catId,
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
        ){
        List<PostDto> posts = this.postService.getPostByCategory(catId);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    private ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Long userId){
        List<PostDto> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public  ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable("keywords") String keywords){
        List<PostDto> postDos = this.postService.searchPosts(keywords);
        return ResponseEntity.ok(postDos);
    }

    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("postId") Long postId
    ) throws IOException {
        log.info("Directory ====>>>>"+UPLOAD_DIRECTORY);
        PostDto postDto = this.postService.getPostById(postId);
           String fileName = this.fileService.uploadImage(UPLOAD_DIRECTORY,image);
           postDto.setImageUrl(fileName);
           PostDto updatedPost = this.postService.updatePost(postDto,postId);

           log.info(String.valueOf(postId));
           log.info(fileName);
        return ResponseEntity.ok(updatedPost);
    }
    
    @GetMapping(value = "/images/download/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response
                              ) throws IOException {
        InputStream ressources = this.fileService.getResource(UPLOAD_DIRECTORY,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(ressources,response.getOutputStream());
    }

}
