package com.walking.project_walking.repository;

import com.walking.project_walking.domain.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findByBoardId(Long boardId, Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.boardId = ?1 AND " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', ?2, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', ?3, '%')) OR " +
            "p.userId = ?4)")
    Page<Posts> searchPosts(Long boardId, String title, String content, Long userId, Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.weightValue >= :threshold")
    List<Posts> findHotPosts(@Param("threshold") double threshold);

    List<Posts> findByUserId(Long userId);

    @Query("SELECT COUNT(p) FROM Posts p WHERE p.boardId = :boardId AND " +
            "(:title IS NULL OR p.title LIKE %:title%) AND " +
            "(:content IS NULL OR p.content LIKE %:content%) AND " +
            "(:userId IS NULL OR p.userId = :userId)")
    long countBySearchCriteria(@Param("boardId") Long boardId,
                                @Param("title") String title,
                                @Param("content") String content,
                                @Param("userId") Long userId);
}