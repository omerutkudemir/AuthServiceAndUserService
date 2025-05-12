package com.poslifayproject.poslifay.controller;

import com.poslifayproject.poslifay.dto.NewsDto;
import com.poslifayproject.poslifay.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/generally_news")
    public ResponseEntity<List<NewsDto>>  getGenarallyPageNews()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(newsService.getGenaralyPageNews(username));
    }

    @GetMapping("/fallowing_news")
    public ResponseEntity<List<NewsDto>> getFallowingNews(String userToken)
    {

    }
    @GetMapping("/by_source_news")
    public ResponseEntity<List<NewsDto>> getNewsBySource(String userToken)
    {

    }
    @GetMapping("/by_category_news")
    public ResponseEntity<List<NewsDto>> getNewsByCategory(String userToken)
    {

    }
}
