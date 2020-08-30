package com.app.blog.service;

import com.app.blog.model.PostTag;
import com.app.blog.model.Tag;
import com.app.blog.repository.PostTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostTagServiceImpl implements PostTagService {

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    PostService postService;


}
