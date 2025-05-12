package com.poslifayproject.poslifay.repository;

import com.poslifayproject.poslifay.model.Like;
import com.poslifayproject.poslifay.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like,Long > {
    Like findByUsers(Users users);

    List<Like> findAllByUsers(Users users);
    @Query("SELECT n FROM likes n WHERE n NOT IN (SELECT l.news FROM Like l WHERE l.user.username = :username)")
    List<Like> findNewsNotLikedByUser(@Param("username") String username);

    @Query("""
    SELECT l FROM Like l
    WHERE l.user.age BETWEEN :minAge AND :maxAge
    AND l.user.sex = :sex
""")
    List<Like> findLikeSmilarAgeAndSameSex(@Param("minAge") short minAge,
                                           @Param("maxAge") short maxAge,
                                           @Param("sex") boolean sex);
    @Query("""
    SELECT l FROM Like l
    WHERE l.user.age BETWEEN :minAge AND :maxAge
    AND l.user.sex = :sex
    AND l NOT IN (
        SELECT l2 FROM Like l2 WHERE l2.user.username = :username
    )
""")
    List<Like> findNotLikedBySimilarAgeAndSex(@Param("minAge") short minAge,
                                              @Param("maxAge") short maxAge,
                                              @Param("sex") boolean sex,
                                              @Param("username") String username);
}
