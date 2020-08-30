package com.app.blog.controller;

import com.app.blog.model.Comment;
import com.app.blog.model.Post;
import com.app.blog.model.PostTag;
import com.app.blog.model.Tag;
import com.app.blog.repository.PostRepository;
import com.app.blog.repository.TagRepository;
import com.app.blog.service.PostService;
import com.app.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public String viewHomePage(Model model, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "author", required = false) String author, @Param(value = "filterDate") Date filterDate) {
        return findPage(keyword, filterDate,author, 1, "title", "asc", model);
    }

    @GetMapping("/filterByDate")
    public String filterByDate(Model model, @RequestParam(value = "filterDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date filterDate) {
        List<Post> listPost = postRepository.getAllByDate(filterDate);
        model.addAttribute("listPost", listPost);
        return "home_page";
    }

    @GetMapping("/addPost")
    public String addPost(Model model) {
        Post post = new Post();
        Tag tags = new Tag();
        model.addAttribute("post", post);
        model.addAttribute("tags", tags);
        return "add_new_post";
    }


    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("post") Post post, @ModelAttribute("tags") String tags) {
        if (postService.existById(post.getId())) {
            post.setCreatedAt(post.getCreatedAt());
            post.setPublishedAt(post.getPublishedAt());
            post.setPublished(post.isPublished());
            post.setUpdatedAt(new Date());
        }
        post.setCreatedAt(new Date());
        post.setPublishedAt(new Date());
        post.setPublished(true);
        String[] allTags = tags.split(",");
        Set<PostTag> postTagsSet = null;
        for (String s : allTags) {
            if (tagRepository.tagExist(s)) {
                tagsService.findByTagName(s).setTagUpdatedAt(new Date());
            } else {
                Tag tag = new Tag();
                tag.setTagName(s);
                tag.setTagCreatedAt(new Date());
                PostTag postTag = new PostTag(post, tag);
                post.addPostTag(postTag);
            }
        }
        postService.savePost(post);
        return "redirect:/";
    }


    @GetMapping("/page/{pageNo}")
    public String findPage(@RequestParam(value = "keyword", required = false) String keyword,@RequestParam(value = "filterDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date filterDate,@RequestParam(value = "author", required = false) String author, @PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField, @RequestParam("sortDirection") String sortDirection, Model model) {
        final int PAGE_SIZE = 9;
        Page<Post> page = postService.findPage(keyword,filterDate, author,pageNo, PAGE_SIZE, sortField, sortDirection);
        List<Post> listPost = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("listPost", listPost);
        model.addAttribute("keyword", keyword);
        model.addAttribute("filterDate",filterDate);
        model.addAttribute("author",author);
        return "home_page";

    }

    @PostMapping("/readBlog")
    public String readBlog(@RequestParam(name = "id") long postId, Model model) {
        Post post1 = postService.getPostById(postId);
        Set<PostTag> postTagSet = post1.getPostTags();
        String tags = " ";
        for (PostTag postTag : postTagSet) {
            tags = tags + "," + postTag.getTag().getTagName();
        }
        model.addAttribute("post", post1);
        Comment comment = new Comment();
        model.addAttribute("tags", tags);
        model.addAttribute("comment", comment);
        return "readBlog";
    }

    @GetMapping("/deleteBlog")
    public String deleteBlog(@RequestParam(name = "id") long postId) {
        postService.deletePost(postId);
        return "redirect:/";
    }

    @GetMapping("/updateForm")
    public ModelAndView showFormForUpdate(@RequestParam(value = "id") long postId) {
        ModelAndView modelAndView = new ModelAndView("update_post");
        Post post = postService.getPostById(postId);
        Set<PostTag> postTagSet = post.getPostTags();
        String tags = "";
        for (PostTag postTag : postTagSet) {
            tags = tags + postTag.getTag().getTagName() + ",";
        }
        modelAndView.addObject("post", post);
        modelAndView.addObject("tags", tags);
        return modelAndView;
    }
}

