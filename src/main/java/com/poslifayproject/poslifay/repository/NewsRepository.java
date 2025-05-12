package com.poslifayproject.poslifay.repository;

import com.poslifayproject.poslifay.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {

}
