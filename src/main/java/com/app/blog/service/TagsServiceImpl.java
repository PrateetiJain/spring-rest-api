package com.app.blog.service;

import com.app.blog.model.Post;
import com.app.blog.model.Tag;
import com.app.blog.repository.PostRepository;
import com.app.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void saveTags(Tag tags) {
        this.tagRepository.save(tags);
    }

    @Override
    public Tag findByTagName(String tagName) {
        return  this.tagRepository.tagByName(tagName);
    }

    @Override
    public List<Tag> findAll() {
        return this.tagRepository.findAll();
    }

//    @Override
//    public List<Post> findAllByTagsName(String searchValue) {
//        List<Tag> listTags = findAll();
//        List<Post> listPostId = new ArrayList<>();
//        for(int i=0;i<listTags.size();i++){
//            String[] arr  =  listTags.get(i).getTagName().split(",");
//            for(int j=0;j<arr.length;j++){
//                if(arr[j].equalsIgnoreCase(searchValue)){
//                   listPostId = listTags.get(i).getPost();
//                }
//            }
//        }
//        listPostId = listTags.get(0).getPost();
//        return listPostId ;
//    }

    @Override
    public Optional<Tag> findTagById(Long tagId) {
       return this.tagRepository.findById(tagId);
    }
}
