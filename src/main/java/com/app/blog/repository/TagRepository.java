package com.app.blog.repository;

import com.app.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    @Query("select case when count(c)> 0 then true else false end from Tag c where lower(c.tagName) like lower(:tagName)")
    boolean tagExist(@Param("tagName")String tagName);

    @Query("select t from Tag t where t.tagName = :tagName")
    Tag tagByName(String tagName);
}
