package com.poslifayproject.poslifay.controller;

import com.poslifayproject.poslifay.dto.CommentReq;
import com.poslifayproject.poslifay.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/doComment")
    public ResponseEntity<Boolean> commentNews(@RequestBody CommentReq commentReq)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(commentService.commentNews(commentReq , username));
    }
    @DeleteMapping("deleteComment/{id}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable Long id)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(commentService.deleteComment(id , username));
    }


}
