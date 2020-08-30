package com.app.blog.service;

import com.app.blog.model.Post;
import com.app.blog.model.Tag;

import java.util.List;
import java.util.Optional;


public interface TagsService {

    void saveTags(Tag tags);
    Tag findByTagName(String tagName);
    List<Tag> findAll();
    //List<Post> findAllByTagsName(String search);
    Optional<Tag> findTagById(Long tagId);
}
