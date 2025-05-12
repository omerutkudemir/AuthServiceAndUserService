package com.poslifayproject.poslifay.controller;

import com.poslifayproject.poslifay.dto.LikeReq;
import com.poslifayproject.poslifay.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("like_news")
    public ResponseEntity<Boolean> likeNews(@RequestBody LikeReq likeReq)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(likeService.likeNews(likeReq,username));

    }
}
