package com.app.blog.service;

import com.app.blog.model.Post;
import com.app.blog.model.PostTag;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PostService {

     boolean existById(Long postId);
     void deletePost(Long postId);
    List<Post> getAllPostById(List<Long> listPostId);
     void savePost(Post post);
     Post getPostById(Long postId);
     Page<Post> findPage(String keyword,Date date,String author, int pageNo, int pageSize, String sortField, String sortDirection);
}
