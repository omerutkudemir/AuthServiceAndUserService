package com.poslifayproject.poslifay.service;

import com.poslifayproject.poslifay.dto.CommentReq;
import com.poslifayproject.poslifay.model.Comment;
import com.poslifayproject.poslifay.model.News;
import com.poslifayproject.poslifay.model.Users;
import com.poslifayproject.poslifay.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final NewsService newsService;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, NewsService newsService, UserService userService) {
        this.commentRepository = commentRepository;
        this.newsService = newsService;
        this.userService = userService;
    }

    public Boolean commentNews(CommentReq commentReq, String username)
    {
        try {
            Users users =userService.getUserForServiceRequirements(username);
            News news = newsService.getNewsForServiceRequirements(commentReq.getNewsId());
            Comment userComment = new Comment(users,news,commentReq.getParentCommentId(),commentReq.getText());
            commentRepository.save(userComment);

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public Boolean deleteComment(Long commentId, String username) {
        try {
            commentRepository.deleteById(commentId);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
