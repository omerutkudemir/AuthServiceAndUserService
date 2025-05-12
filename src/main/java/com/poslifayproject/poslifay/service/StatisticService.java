package com.poslifayproject.poslifay.service;

import com.poslifayproject.poslifay.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// istatistik alırken yorumları da kullanabiliriz ama şimdilik onunla uğraşmıycam(sonra tarrışıcaz)
@Service
public class StatisticService {
    private final LikeService likeService;
    private final UserService userService;
    public StatisticService(LikeService likeService, UserService userService) {
        this.likeService = likeService;
        this.userService = userService;
    }

    public Map<Category, Double> getLikeCategoryAndSourceForNewsStatistic(String userName)//kullanıcının beğendiği haberlerin kategorisinin yüzdeliklerini döner
    {

        Users user = userService.getUserForServiceRequirements(userName);
        List<Like> likedBySmilarAgeAndSameSex=likeService.getLikeSmilarAgeAndSameSex(user.getAge(),user.getSex());
        List<News> likedNewsBySmilarAgeAndSameSex=likedBySmilarAgeAndSameSex.stream().map(StatisticService::extractNews).collect(Collectors.toList());
        List<Category> likedNewsCategoryBySmilarAgeAndSameSex =likedNewsBySmilarAgeAndSameSex.stream().map(StatisticService::extractCategory).collect(Collectors.toList());
        List<Source>  likedNewsSourceBySmilarAgeAndSameSex = likedNewsBySmilarAgeAndSameSex.stream().map(StatisticService::extractSource).collect(Collectors.toList());

        List<Like> notLikedBySmilarAgeAndSameSex=likeService.getNotLikeSmilarAgeAndSameSex(userName,user.getAge(),user.getSex());
        List<News> notLikedNewsBySmilarAgeAndSameSex=notLikedBySmilarAgeAndSameSex.stream().map(StatisticService::extractNews).collect(Collectors.toList());
        List<Category> notlikedNewsCategoryBySmilarAgeAndSameSex =notLikedNewsBySmilarAgeAndSameSex.stream().map(StatisticService::extractCategory).collect(Collectors.toList());
        List<Source>  notLikedNewsSourceBySmilarAgeAndSameSex = notLikedNewsBySmilarAgeAndSameSex.stream().map(StatisticService::extractSource).collect(Collectors.toList());

        List<Like> likedByUser=likeService.getLikeByUser(user);
        List<News> likedNewsByUserList= likedByUser.stream().map(StatisticService::extractNews).collect(Collectors.toList());
        List<Like> notLikedByUserList=likeService.getNotLikeByUser(user);

        List<News> notLikedNewsByUserList= notLikedByUserList.stream().map(StatisticService::extractNews).collect(Collectors.toList());

        List<Category>  categoryByNotLikedNews = notLikedNewsByUserList.stream().map(StatisticService::extractCategory).collect(Collectors.toList());
        List<Source>  sourceByNotLikedNews = notLikedNewsByUserList.stream().map(StatisticService::extractSource).collect(Collectors.toList());

        List<Category>  categoryByLikedNews = likedNewsByUserList.stream().map(StatisticService::extractCategory).collect(Collectors.toList());
        List<Source>  sourceByLikedNews = likedNewsByUserList.stream().map(StatisticService::extractSource).collect(Collectors.toList());


    }





    private static Source extractSource(News news) {
        return news.getSource();
    }


    private static Category extractCategory(News news) {
        return news.getCategory();
    }

    private static News extractNews(Like like) {
        return like.getNews();
    }
}
