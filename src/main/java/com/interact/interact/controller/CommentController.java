package com.interact.interact.controller;

import com.interact.interact.dto.ApiResponse;
import com.interact.interact.dto.CommentDto;
import com.interact.interact.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}")
    private ResponseEntity<CommentDto> createComment(@RequestBody() CommentDto comment, @PathVariable("postId") Long postId){
      CommentDto commentDto =   this.commentService.createComment(comment,postId);
      return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/{commentId}")
    private ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId")Long commentId){
        this.commentService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse("Comment Deleted successfully",200,true));
    }
}
