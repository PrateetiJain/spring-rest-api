package com.app.blog.controller;

import com.app.blog.model.Comment;
import com.app.blog.model.Post;
import com.app.blog.model.Tag;
import com.app.blog.service.CommentService;
import com.app.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @PostMapping("/commentForm")
    public ModelAndView commentForm(Model model, @RequestParam(value = "id")long postId){
        ModelAndView modelAndView = new ModelAndView("comment");
        Comment comment = new Comment();
        Post post = postService.getPostById(postId);
        model.addAttribute("comments",comment);
        model.addAttribute("post",post);
        return modelAndView;
    }

    @PostMapping("/saveComment")
    public String saveComment(@ModelAttribute("comments") Comment comment,@RequestParam(value = "id") long postId, Model model){
        Post post = postService.getPostById(postId);
        System.out.println("comment id is = "+comment.getCommentId());
        System.out.println("created at = "+  comment.getCommentCreatedAt());
        System.out.println("ans is = "+ commentService.commentExist(comment.getCommentId()));
        if(commentService.commentExist(comment.getCommentId())){
            comment.setCommentUpdatedAt(new Date());
            System.out.println("created at = "+comment.getCommentCreatedAt());
            comment.setCommentCreatedAt(comment.getCommentCreatedAt());
        }
        else {
            comment.setCommentCreatedAt(new Date());
        }
        comment.setPost(post);
        commentService.saveComment(comment);
        model.addAttribute("post",post);
        return "/readBlog";
    }

    @GetMapping("/deleteComment/{commentId}/{postId}")
    public String deleteComment(@PathVariable(value = "commentId")long commentId,@PathVariable(value = "postId")Long postId,Model model) {
        commentService.deleteCommentById(commentId);
        Post post = postService.getPostById(postId);
        model.addAttribute("post",post);
        return "/readBlog";
    }

    @GetMapping("/editComment/{commentId}/{postId}")
    public ModelAndView editComment(@PathVariable(value = "commentId")long commentId,@PathVariable(value = "postId")Long postId){
        ModelAndView modelAndView = new ModelAndView("edit_comment");
        Comment comment = commentService.findCommentById(commentId);
        Post post = postService.getPostById(postId);
        modelAndView.addObject("post",post);
        modelAndView.addObject("comments",comment);
        return modelAndView;
    }
}
