package com.poslifayproject.poslifay.repository;

import com.poslifayproject.poslifay.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
