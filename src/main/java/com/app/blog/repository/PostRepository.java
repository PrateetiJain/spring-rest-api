package com.app.blog.repository;

import com.app.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

     Post findAllById(Long postId);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %?1%" +
            "OR p.excerpt LIKE %?1%" +
            "OR p.author LIKE %?1%" +
            "OR p.content LIKE %?1%" )
    Page<Post> findAllByKeyword(String keyword, Pageable pageable);

//   @Query(value = "SELECT * from (post p where ((:author = '') is true or p.author) AND " +
//    "((:publishedAt = '') is true or to_char(p.published_at, 'YYYY-MM-DD') = :publishedAt)" +
//    "(SELECT t.id FROM tags t WHERE t.tagName IN (:tagName)))) AS post", countQuery = "select count(*) from post", nativeQuery = true)
//    Page<Post> filter(@Param("author") String author, @Param("publishedAt") String publishedAt, @Param("tagNames") List<String> tagNames, Pageable pageable);


    @Query("SELECT p FROM Post p WHERE p.createdAt = :date")
    List<Post> getAllByDate(Date date);

    @Query("SELECT p FROM Post p WHERE p.author LIKE %?1%")
    List<Post> getAllByAuthor(String author);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %?1%" +
            "OR p.excerpt LIKE %?1%" +
            "OR p.author LIKE %?1%" +
            "OR p.content LIKE %?1%" )
    Page<Post> searchAndFilter(String keyword, Date date, String author, Pageable pageable);

//   @Query("Select p from Post p where ((:author = '') is true or p.author LIKE %?1%) AND " +
//   "((:publishedAt = '') is true or to_char(p.published_at,'YYYY-MM-DD') = :publishedAt)")
//    Page<Post> filter(@Param("author") String author, @Param("publishedAt") String publishedAt, @Param("tagName") String tagName, Pageable pageable);
}
