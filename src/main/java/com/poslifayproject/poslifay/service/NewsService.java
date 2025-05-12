package com.poslifayproject.poslifay.service;

import com.poslifayproject.poslifay.dto.NewsDto;
import com.poslifayproject.poslifay.model.News;
import com.poslifayproject.poslifay.model.Users;
import com.poslifayproject.poslifay.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final UserService userService;
    public NewsService(NewsRepository newsRepository, UserService userService) {
        this.newsRepository = newsRepository;
        this.userService = userService;
    }

    public List<NewsDto> getGenaralyPageNews(String username)//hem takip ettiklerinden hemde ilgi alanlarından haber getirecek
    {
        Users users =userService.getUserForServiceRequirements(username);
    }
    public List<NewsDto> getFallowingNews()//sadece takip ettiklerinden haber getirecek
    {

    }

    public List<NewsDto> getNewsBySource()
    {

    }
    public List<NewsDto> getNewsByCategory()
    {

    }

    public News getNewsForServiceRequirements(Long newsId)// servisin geri kalanında kullanmamız için news dondecek like ve commentde column ataması yapcağız
    {
        return newsRepository.findById(newsId).orElseThrow(()->new RuntimeException("Böyle bir haber yok kaldırılmış olabilir"));
    }

}
