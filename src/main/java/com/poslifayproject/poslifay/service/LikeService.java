package com.poslifayproject.poslifay.service;

import com.poslifayproject.poslifay.dto.LikeReq;
import com.poslifayproject.poslifay.model.Like;
import com.poslifayproject.poslifay.model.News;
import com.poslifayproject.poslifay.model.Users;
import com.poslifayproject.poslifay.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final NewsService newsService;
    private final UserService userService;
    private final LikeRepository likeRepository;
    public LikeService(NewsService newsService, UserService userService, LikeRepository likeRepository) {
        this.newsService = newsService;
        this.userService = userService;
        this.likeRepository = likeRepository;
    }

    public Boolean likeNews(LikeReq likeReq, String username)// like yoksa like atar varsa kaldırır
    {
        try {

            Users users =userService.getUserForServiceRequirements(username);
            Like isThere =likeRepository.findByUsers(users);
            if(isThere==null)
            {
                News news = newsService.getNewsForServiceRequirements(likeReq.getNewsId());
                Like userLike = new Like(users,news);
                likeRepository.save(userLike);
            }
            else
            {
                likeRepository.delete(isThere);
            }

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public List<Like> getLikeByUser(Users users)
    {
        return likeRepository.findAllByUsers(users);
    }

    public List<Like> getNotLikeByUser(Users user) {

        return likeRepository.findNewsNotLikedByUser(user.getUsername());
    }



    public List<Like> getNotLikeSmilarAgeAndSameSex(String userName, short age, boolean sex) {
        short minAge = (short) (age - 5);
        short maxAge = (short) (age + 5);
        return likeRepository.findNotLikedBySimilarAgeAndSex(minAge, maxAge, sex, userName);
    }

    public List<Like> getLikeSmilarAgeAndSameSex(short age, boolean sex) {
        short minAge = (short) (age - 5);
        short maxAge = (short) (age + 5);
        return likeRepository.findLikeSmilarAgeAndSameSex(minAge, maxAge, sex);
    }
}
