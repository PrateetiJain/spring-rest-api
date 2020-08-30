package com.app.blog.service;

import com.app.blog.model.Post;
import com.app.blog.model.PostTag;
import com.app.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public boolean existById(Long postId) {
        return postRepository.existsById(postId);
    }

    @Override
    public void deletePost(Long postId) {
         this.postRepository.deleteById(postId);
    }

    @Override
    public List<Post> getAllPostById(List<Long> listPostId) {

        return this.postRepository.findAllById(listPostId);
    }

    @Override
    public Post getPostById(Long postId){
        return this.postRepository.findAllById(postId);
    }

    @Override
    public void savePost(Post post) {
        this.postRepository.save(post);
    }


    @Override
    public Page<Post> findPage(String keyword, Date date, String author, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
        if(keyword !=null  && date !=null && author!=null){
            return postRepository.searchAndFilter(keyword,date,author,pageable);
        }

        if(keyword != null){
            return postRepository.findAllByKeyword(keyword,pageable);}

        return  this.postRepository.findAll(pageable);
    }
}
